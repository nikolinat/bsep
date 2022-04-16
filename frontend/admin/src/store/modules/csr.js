import axios from "axios";

const state = {
    allCsr: [],
    result: null
};

const getters = {
    getAllCsr: state => state.allCsr,
    getResult: state => state.result
};

const actions = {
    createCsr: (context, csr) => {
        axios.post(`api/v1/csr`, csr)
        .then(resp => {
            console.log(resp)
            context.commit('setResult', {label: 'create', ok: true, message: 'Successfuly created.'});
        })
        .catch(err => {
            context.commit('setResult', {label: 'create', ok: false, message: err.response.data.ErrorMessage});
        })
    }
};

const mutations = {
    setAllCsr: (state, allCsr) => {
        state.allCsr = allCsr;
    },
    setResult: (state, result) => {
        state.result = result;
    }
};

export default {
    state: state,
    getters: getters,
    actions: actions,
    mutations: mutations,
    namespaced: true
};