import axios from "axios";

const state = {
    validCertificates: null,
    result: null
};

const getters = {
    getValidCertificates: state => state.validCertificates,
    getResult: state => state.result
};

const actions = {
    fetchValidCertificates: (context) => {
        axios.get(`/`)
        .then(response => {
            context.commit('setValidCertificates', response.data);
        })
        .catch(error => {
            console.log(error);
            context.commit('setResult', { label: 'fetch', ok: false });
        });
    },

    revokeCertificate: (context, certificate, reason) => {
        axios.put(`/certificate/${reason}`, certificate)
        .then(response => {
            console.log(response);
            context.commit('setResult', {
                label: 'update',
                ok: true,
                message: `You have successfully revoke a certificate.`
            });
        })
        .catch(error => {
            context.commit('setResult', {
                label: 'update',
                ok: false,
                message: error.response.data.ErrorMessage
                
            });
        });
    },
   
};

const mutations = {
    setValidCertificates: (state, validCertificates) => {
        state.validCertificates = validCertificates;
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