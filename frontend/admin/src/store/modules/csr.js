import axios from "axios";

const state = {
  csr: null,
  result: null,
};

const getters = {
  getCsr: (state) => state.csr,
  getResult: (state) => state.result,
};

const actions = {
  fetchCsr: (context) => {
    axios
      .get(`/csr`)
      .then((response) => {
        context.commit("setCsr", response.data);
      })
      .catch((error) => {
        console.log(error);
        context.commit("setResult", { label: "fetch", ok: false });
      });
  },
};

const mutations = {
  setCsr: (state, csr) => {
    state.csr = csr;
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
