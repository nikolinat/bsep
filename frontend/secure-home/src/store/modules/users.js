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
        context.commit("setResult", { label: "fetch", ok: false, message: error.response.data.errorMessage });
      });
  },
  createUser: (context, CreateUserDto) => {
    const queryParam = { ...CreateUserDto };
    queryParam.roles = CreateUserDto.roles.reduce((f, s) => `${f},${s}`);
    axios
      .post(`/users/new-user`, CreateUserDto, { withCredentials: true })
      .then((response) => {
        context.commit("setUsers", response.data);
        context.commit("setResult", { label: "create", ok: true, message: "User created." });
      })
      .catch((error) => {
        console.log(error.response.data)
        context.commit("setResult", { label: "create", ok: false, message: error.response.data.errorMessage });
      });
  },
  updateUser: (context, UpdateUserDto) => {
    const queryParam = { ...UpdateUserDto };
    queryParam.roles = UpdateUserDto.roles.reduce((f, s) => `${f},${s}`);
    axios
      .put(`/users/update-user`, UpdateUserDto)
      .then((response) => {
        context.commit("setUsers", response.data);
      })
      .catch((error) => {
        context.commit("setResult", { label: "update", ok: false, message: error.response.data.errorMessage });
      });
  },
  deleteUser: (context, username) => {
    axios
      .delete(`/users/delete-user/${username}`)
      .then((response) => {
        context.commit("setUsers", response.data);
      })
      .catch((error) => {
        context.commit("setResult", { label: "delete", ok: false, messag: error.response.data.errorMessage });
      });
  },

  fetchOwnersAndTenants: (context) => {
    axios
      .get(`/users`)
      .then((response) => {
        context.commit("setUsers", response.data);
      })
      .catch((error) => {
        context.commit("setResult", { label: "fetchOwnersAndTenants", ok: false, message: error.response.data.errorMessage });
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
