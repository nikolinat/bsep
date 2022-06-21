import Vue from "vue";
import Vuex from "vuex";
import authentication from "./modules/authetication.js"
import realestate from "./modules/realestate.js"
import users from "./modules/users.js"
import devices from './modules/devices.js'
<<<<<<< HEAD
import alarms from './modules/alarms.js'
=======
import deviceMessage from './modules/deviceMessage.js'
>>>>>>> f8928872a38e61753873e31eb2a8255c0c20a586

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    authentication,
    realestate,
    users,
    devices,
<<<<<<< HEAD
    alarms
=======
    deviceMessage
>>>>>>> f8928872a38e61753873e31eb2a8255c0c20a586
  },
});
