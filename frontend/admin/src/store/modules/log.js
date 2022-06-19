import axios from "axios";

const state = {
  logs: [],
  result: null,
};

const getters = {
  getLogs: (state) => state.logs,
  getResult: (state) => state.result,
};

const actions = {
  fetchLogs: (context) => {
    axios
      .get(`/log`)
      .then((response) => {
        context.commit("setLogs", response.data);
      })
      .catch((error) => {
        console.log(error);
        context.commit("setResult", { label: "fetch", ok: false });
      });
  },
  searchAndFilterLogs: (context, logSearchDto) => {
    axios
      .get(`/log/search-filter`, { params: logSearchDto })
      .then((response) => {
        context.commit("setLogs", response.data);
      })
      .catch((error) => {
        context.commit("setResult", { label: "fetch", ok: false, message: error.response.data.message });
      });
  }
};

const mutations = {
  setLogs: (state, logs) => {
    state.logs = logs;
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
