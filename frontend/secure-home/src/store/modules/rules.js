import axios from "axios";

const state = {
    result: null
};

const getters = {
    getResult: (state) => state.result
};

const actions = {
    createRule: (context, rule) => {
        axios
            .post(`/rule`, rule)
            .then(() => {
                context.commit("setResult", { label: "create", ok: true });
            })
            .catch((error) => {
                console.log(error);
                context.commit("setResult", { label: "create", ok: false });
            });
    },

};
const mutations = {
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
