import axios from "axios";
import { getIdFromToken } from "../../utils/token";

const state = {
  realEstates: null,
  result: null,
  realEstate: null
};

const getters = {
  getRealEstates: (state) => state.realEstates,
  getResult: (state) => state.result,
  getRealEstate: (state) => state.realEstate
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

  removeTenant: (context, removeTenantDto) => {
    axios
      .put(`/realestates/remove-tenant/${removeTenantDto.id}/${removeTenantDto.realEstateId}`)
      .then((response) => {
        context.commit("setResult", { label: "remove", ok: true, message: "Successfully removed owner/tenant." });
        context.commit("setRealEstate", response.data);
      })
      .catch((error) => {
        console.log(error.response.data)
        context.commit("setResult", { label: "remove", ok: false, message: error.response.data.message });
      });
  },

  fetchRealEstatesByOwner: (context) => {
    const userId = getIdFromToken();
    axios
      .get(`/realestates/by-owner/` + userId)
      .then((response) => {
        context.commit("setRealEstates", response.data);
      })
      .catch((error) => {
        console.log(error);
        context.commit("setResult", { label: "fetch", ok: false });
      });
  },

  fetchRealEstatesByTenant: (context) => {
    const userId = getIdFromToken();
    axios
      .get(`/realestates/by-tenant/` + userId)
      .then((response) => {
        context.commit("setRealEstates", response.data);
      })
      .catch((error) => {
        console.log(error);
        context.commit("setResult", { label: "fetch", ok: false });
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
  setRealEstate: (state, realEstate) => {
    state.realEstate = realEstate;
  },

};

export default {
  state: state,
  getters: getters,
  actions: actions,
  mutations: mutations,
  namespaced: true,
};
