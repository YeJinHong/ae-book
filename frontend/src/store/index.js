import Vue from 'vue'
import Vuex from 'vuex'
import bookStore from '@/store/modules/bookStore.js'
import paintingStore from '@/store/modules/paintingStore.js'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    bookStore,
    paintingStore
  }
})

export default store
