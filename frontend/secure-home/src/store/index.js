import Vue from "vue";
import Vuex from "vuex";
import authentication from "./modules/authetication.js"
import realestate from "./modules/realestate.js"
import users from "./modules/users.js"
import devices from './modules/devices.js'

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    authentication,
    realestate,
    users,
    devices
  },
});
