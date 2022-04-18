import axios from "axios";

const state = {
  allCsr: [],
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
        context.commit("setAllCsr", response.data);
      })
      .catch((error) => {
        console.log(error);
        context.commit("setResult", { label: "fetch", ok: false });
      });
  },
  createCsr: (context, csr) => {
    axios
      .post(`/csr`, csr)
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
          message: err.response.data.errorMessage,
        });
      });
  },
  acceptCsr: (context, { csrId, certificate }) => {
    console.log(certificate);
    axios
      .put(`/csr/accept/` + csrId, certificate)
      .then((resp) => {
        console.log(resp);
        context.commit("setResult", {
          label: "accept",
          ok: true,
          message: "Successfuly created certificate.",
        });
      })
      .catch((err) => {
        context.commit("setResult", {
          label: "accept",
          ok: false,
          message: err.response.data.errorMessage,
        });
      });
  },

  declineCsr: (context, csr) => {
    axios
      .put(`/csr/decline/${csr.id}`, csr.reason)
      .then(() => {
        context.commit("setResult", {
          label: "decline",
          ok: true,
          message: "Successfuly decline certificate.",
        });
      })
      .catch((err) => {
        context.commit("setResult", {
          label: "decline",
          ok: false,
          message: err.response.data.ErrorMessage,
        });
      });
  },
};

const mutations = {
  setAllCsr: (state, allCsr) => {
    state.allCsr = allCsr;
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
