import Vue from "vue";
import Vuex from "vuex";
import certificates from "./modules/certificates.js";
import csr from "./modules/csr.js";
import users from "./modules/users.js"
import authentication from "./modules/authetication.js"

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    certificates,
    csr,
    users,
    authentication
  },
});
