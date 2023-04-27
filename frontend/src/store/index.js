import Vue from 'vue'
import Vuex from 'vuex'
import bookStore from '@/store/modules/bookStore.js'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    bookStore
  }
})

export default store
