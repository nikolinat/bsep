import Vue from 'vue'
import Vuex from 'vuex'
import csr from './modules/csr'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    csr
  }
});