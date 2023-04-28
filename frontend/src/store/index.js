import Vue from 'vue'
import Vuex from 'vuex'
import bookStore from '@/store/modules/bookStore.js'
import reviewStore from './modules/reviewStore'
import userStore from './modules/userStore'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    bookStore,
    reviewStore,
    userStore
  }
})

export default store
