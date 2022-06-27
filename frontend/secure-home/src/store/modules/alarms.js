import axios from "axios";

const state = {
    alarm: null,
    result: null,
    alarms: null
};

const getters = {
    getAlarm: (state) => state.alarm,
    getResult: (state) => state.result,
    getAlarms: (state) => state.alarms,
};

const actions = {
    createAlarm: (context, alarm) => {
        axios
            .post(`/alarm`, alarm)
            .then((response) => {
                context.commit("setAlarm", response.data);
                context.commit("setResult", { label: "create", ok: true });
            })
            .catch((error) => {
                console.log(error);
                context.commit("setResult", { label: "create", ok: false });
            });
    },
    fetchAlarmsForRealEstate: (context, realestateId) => {
        axios.get('/alarm/' + realestateId)
        .then(response => {
            context.commit("setAlarms", response.data);
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
    setAlarm: (state, alarm) => {
        state.alarm = alarm;
    },

    setAlarms: (state, alarms) => {
        state.alarms = alarms;
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
