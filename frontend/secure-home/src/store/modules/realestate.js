import axios from "axios";

const state = {
  realEstates: null,
  result: null,
};

const getters = {
  getRealEstates: (state) => state.realEstates,
  getResult: (state) => state.result,
};

const actions = {
  fetchRealEstates: (context) => {
    axios
      .get(`/realestates`)
      .then((response) => {
        context.commit("setRealEstates", response.data);
      })
      .catch((error) => {
        console.log(error);
        context.commit("setResult", { label: "fetch", ok: false });
      });
  },
  updateRealEstate: (context, updateRealEstateDto) => {
    axios
      .put(`/realestates`, updateRealEstateDto)
      .then(() => {
        //context.commit("setRealEstates", response.data);
        context.commit("setResult", { label: "update", ok: true, message: "Successfully added owner/tenant." });
      })
      .catch((error) => {
        console.log(error.response.data)
        context.commit("setResult", { label: "update", ok: false, message: error.response.data.message });
      });
  },

};
const mutations = {
  setRealEstates: (state, realEstates) => {
    state.realEstates = realEstates;
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
