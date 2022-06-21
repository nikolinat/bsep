import axios from "axios";

const state = {
  devicesMessages: null,
  result: null,
};

const getters = {
  getDevicesMessages: (state) => state.devicesMessages,
  getResult: (state) => state.result,
};

const actions = {
  fetchDevicesMessagesForRealEstate: (context, realestateId) => {
    axios.get('/device-message/by-real-estate/' + realestateId)
    .then(response => {
        context.commit("setDevicesMessages", response.data);
    })
    .catch(error => {
        context.commit('setResult', {
            label: 'fetch',
            ok: false,
            message: error.response.data.message
        });
    }); 
    },
    filterSearchDevicesMessages: (context, {realestateId, searchFilterDeviceMessagesDto}) => {
        axios.get('/device-message/search-filter/' + realestateId, { params: searchFilterDeviceMessagesDto })
        .then(response => {
            context.commit("setDevicesMessages", response.data);
        })
        .catch(error => {
            context.commit('setResult', {
                label: 'fetch',
                ok: false,
                message: error.response.data.message
            });
        }); 
    }
};

const mutations = {
  setDevicesMessages: (state, devicesMessages) => {
    state.devicesMessages = devicesMessages;
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
