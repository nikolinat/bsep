import axios from "axios";
import {setToken, removeToken, getRoleFromToken} from '../../utils/token'
import { tryConnecting } from "../../utils/sockets.js";

const state = {
    result: null
};

const getters = {
    getResult: state => state.result
};

const actions = {
    authenticate: (context, credentials) => {
        axios.post('/auth/login', credentials)
        .then(response => {
            setToken(response.data.accessToken);
            
            context.commit('setResult', {
                label: 'authenticate',
                ok: true,
                message: ''
            });

            const role = getRoleFromToken();
            if(role.includes("ROLE_ADMIN")) {
                tryConnecting();
            }
        })
        .catch(error => {
            console.log(error.response.data.message);
            const message = error.response.data.errorMessage !== undefined? error.response.data.errorMessage : 'Bad credentials!';
            context.commit('setResult', {
                label: 'authenticate',
                ok: false,
                message: message
            });
        });        
    },

    logOut: (context) => {
        const token = sessionStorage.getItem('token');
        axios.post("/auth/logout", token)
        .then(response => {
            removeToken(token);
            context.commit('setResult', {
                label: 'logout',
                ok: true,
                message: response
            });
        })
        .catch(error => {
            context.commit('setResult', {
                label: 'logout',
                ok: false,
                message: error.response.data.errorMessage
            });
        })
    }
};

const mutations = {
    setResult: (state, response) => {
        state.result = response;
    }
};

export default {
    state: state,
    getters: getters,
    actions: actions,
    mutations: mutations,
    namespaced: true
};