import axios from "axios";

const state = {
  device: null,
  result: null,
  devices: null,
  filteredDevices: null,
};

const getters = {
  getDevice: (state) => state.device,
  getResult: (state) => state.result,
  getDevices: (state) => state.devices,
  getFilteredDevices: (state) => state.filteredDevices,
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
  fetchDevicesForRealEstate: (context, realestateId) => {
    axios
      .get("/device/" + realestateId)
      .then((response) => {
        context.commit("setDevices", response.data);
      })
      .catch((error) => {
        context.commit("setResult", {
          label: "fetch",
          ok: false,
          message: error.response.data.message,
        });
      });
  },

  fetchReportForDevices: (context, reportFilterDto) => {
    const queryParam = { ...reportFilterDto };
    console.log(queryParam);
    axios
      .get("/device/devices", { params: queryParam })
      .then((response) => {
        console.log(response);
        context.commit("setFilteredDevices", response.data);
      })
      .catch((error) => {
        context.commit("setResult", {
          label: "fetch",
          ok: false,
          message: error.response.data.message,
        });
      });
  },
};
const mutations = {
  setDevice: (state, device) => {
    state.device = device;
  },

  setDevices: (state, devices) => {
    state.devices = devices;
  },
  setFilteredDevices: (state, filteredDevices) => {
    state.filteredDevices = filteredDevices;
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
