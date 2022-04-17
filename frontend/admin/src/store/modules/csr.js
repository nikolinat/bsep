import axios from "axios";

const state = {
  csr: [],
  result: null,
};

const getters = {
  getAllCsr: (state) => state.allCsr,
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
  createCsr: (context, csr) => {
    axios
      .post(`api/v1/csr`, csr)
      .then((resp) => {
        console.log(resp);
        context.commit("setResult", {
          label: "create",
          ok: true,
          message: "Successfuly created.",
        });
      })
      .catch((err) => {
        context.commit("setResult", {
          label: "create",
          ok: false,
          message: err.response.data.ErrorMessage,
        });
      });
  },
};

export default {
  state: state,
  getters: getters,
  actions: actions,
  mutations: mutations,
  namespaced: true,
};

const mutations = {
  setAllCsr: (state, allCsr) => {
    state.allCsr = allCsr;
  },
  setResult: (state, result) => {
    state.result = result;
  },
};
