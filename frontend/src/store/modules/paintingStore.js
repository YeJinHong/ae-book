import { savePainting, getPaintingList } from '@/api/painting'

const paintingStore = {
  namespaced: true,
  state: {
    painting: null,
    paintingList: []
  },
  getters: {
    getPainting: state => {
      return state.painting
    },
    getPaintingList: state => {
      return state.paintingList
    }
  },
  mutations: {
    SET_PAINTING: (state, data) => {
      state.painting = data
    },
    SET_PAINTING_LIST: (state, data) => {
      state.paintingList = data
    },
    RESET_PAINTING_LIST (state) {
      state.paintingList = []
    }
  },
  actions: {
    async savePainting ({ commit }, formdata) {
      await savePainting(formdata)
        .then(({ data }) => {
          commit('SET_PAINTING', data.result)
          console.log(data.result)
        })
        .catch(error => {
          console.log(error)
        })
    },
    async getPaintingList ({ commit }, request) {
      await getPaintingList(request)
        .then(({ data }) => {
          console.log(data.result.content)
          commit('SET_PAINTING_LIST', data.result.content)
        })
        .catch(error => {
          console.log(error)
        })
    }
  }
}

export default paintingStore
