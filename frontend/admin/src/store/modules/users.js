import axios from "axios";

const state = {
  users: [],
  result: null,
  user: null
};

const getters = {
  getUsers: (state) => state.users,
  getResult: (state) => state.result,
  getUser: (state) => state.user
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
    queryParam.roles = queryParam.roles.length > 0? queryParam.roles.reduce((f, s) => `${f},${s}`) : "";
    axios
      .post(`/users/new-user`, CreateUserDto, { withCredentials: true })
      .then((response) => {
        context.commit("setUsers", response.data);
        context.commit("setResult", { label: "create", ok: true, message: "User created." });
      })
      .catch((error) => {
        const message = error.response.data.message !== undefined ? error.response.data.message : error.response.data.errorMessage;
        context.commit("setResult", { label: "create", ok: false, message: message });
      });
  },
  updateUser: (context, UpdateUserDto) => {
    // const queryParam = { ...UpdateUserDto };
    // queryParam.roles = queryParam.roles.length > 0? queryParam.roles.reduce((f, s) => `${f},${s}`) : "";
    axios
      .put(`/users/update-user`, UpdateUserDto)
      .then((response) => {
        context.commit("setUsers", response.data);
        context.commit("setResult", {label: "update", ok: true, message: "User updated successfuly."})
      })
      .catch((error) => {
        context.commit("setResult", { label: "update", ok: false, message: error.response.data.errorMessage });
      });
  },
  deleteUser: (context, username) => {
    axios
      .delete(`/users/delete-user/${username}`)
      .then(() => {
        context.commit("setResult", {label: "delete", ok: true, message: "User deleted successfuly."});
      })
      .catch((error) => {
        context.commit("setResult", { label: "delete", ok: false, message: error.response.data.errorMessage });
      });
  },
  fetchUser: (context, id) => {
    axios.get(`/users/` + id)
    .then(response => {
      context.commit("setUser", response.data);
    })
    .catch(error => {
      context.commit("setResult", {label: "fetchUser", ok: false, message: error.response.data.errorMessage })
    })
  }
};

const mutations = {
  setUsers: (state, users) => {
    state.users = users;
  },
  setResult: (state, result) => {
    state.result = result;
  },
  setUser: (state, user) => {
    state.user = user;
  }
};

export default {
  state: state,
  getters: getters,
  actions: actions,
  mutations: mutations,
  namespaced: true,
};
