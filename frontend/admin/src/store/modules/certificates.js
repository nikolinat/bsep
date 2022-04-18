import axios from "axios";

const state = {
    validCertificates: null,
    revokedCertificates: null,
    result: null
};

const getters = {
    getValidCertificates: state => state.validCertificates,
    getRevokedCertificates: (state) => state.revokedCertificates,
    getResult: state => state.result
};

const actions = {
    fetchValidCertificates: (context) => {
        axios.get(`/certificate`)
        .then(response => {
            context.commit('setValidCertificates', response.data);
        })
        .catch(error => {
            console.log(error);
            context.commit('setResult', { label: 'fetch', ok: false });
        });
    },

    fetchRevokedCertificates: (context) => {
      axios
        .get(`/certificate/revoked`)
        .then((response) => {
          context.commit("setRevokedCertificates", response.data);
        })
        .catch((error) => {
          console.log(error);
          context.commit("setResult", { label: "fetch", ok: false });
        });
    },

    revokeCertificate: (context, revokedCertificate) => {
        axios.put(`/certificate/${revokedCertificate.reason}`, revokedCertificate.certificate)
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

  setRevokedCertificates: (state, revokedCertificates) => {
    state.revokedCertificates = revokedCertificates;
  },

  setResult: (state, result) => {
    state.result = result;
  },
};

export default {
  state: state,
  getters: getters,
  actions: actions,
  mutations: mutations,
  namespaced: true,
};
