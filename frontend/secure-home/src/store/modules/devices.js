import axios from "axios";

const state = {
  device: null,
  result: null,
};

const getters = {
  getDevice: (state) => state.device,
  getResult: (state) => state.result,
};

const actions = {
  createDevice: (context, device) => {
    axios
      .post(`/device`, device)
      .then((response) => {
        context.commit("setDevice", response.data);
        context.commit("setResult", { label: "create", ok: true });
      })
      .catch((error) => {
        console.log(error);
        context.commit("setResult", { label: "create", ok: false });
      });
  },

};
const mutations = {
  setDevice: (state, device) => {
    state.device = device;
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
