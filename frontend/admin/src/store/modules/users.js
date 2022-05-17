import axios from "axios";

const state = {
  users: [],
  result: null,
};

const getters = {
  getUsers: (state) => state.users,
  getResult: (state) => state.result,
};

const actions = {
  fetchUsers: (context, searchFilterDto) => {
    const queryParam = { ...searchFilterDto };
    queryParam.roles = searchFilterDto.roles.reduce((f, s) => `${f},${s}`);
    axios
      .get(`/users/search-filter`, { params: queryParam })
      .then((response) => {
        context.commit("setUsers", response.data);
      })
      .catch((error) => {
        console.log(error);
        context.commit("setResult", { label: "fetch", ok: false });
      });
  },
  createUser: (context, CreateUserDto) => {
    const queryParam = { ...CreateUserDto };
    queryParam.roles = CreateUserDto.roles.reduce((f, s) => `${f},${s}`);
    axios
      .post(`/users/new-user`, CreateUserDto)
      .then((response) => {
        context.commit("setUsers", response.data);
      })
      .catch((error) => {
        console.log(error);
        context.commit("setResult", { label: "create", ok: false });
      });
  },
};

const mutations = {
  setUsers: (state, users) => {
    state.users = users;
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
