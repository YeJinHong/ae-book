import { savePainting, getPaintingList, convertSketch } from '@/api/painting'
import { getPaintingDetail } from '../../api/painting'

const paintingStore = {
  namespaced: true,
  state: {
    painting: null,
    paintingList: [],
    sketch: ''
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
    },
    SET_SKETCH: (state, data) => {
      state.sketch = 'data:image/jpeg;base64,' + data
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
    },
    async convertSketch ({ commit }, request) {
      await convertSketch(request)
        .then(({ data }) => {
          // console.log(data)
          commit('SET_SKETCH', data)
        })
        .catch(error => {
          console.log(error)
        })
    },
    async getPaintingDetail ({ commit }, request) {
      await getPaintingDetail(request)
        .then(({ data }) => {
          console.log(data.result)
          commit('SET_PAINTING', data.result)
        })
        .catch(error => {
          console.log(error)
        })
    }
  }
}

export default paintingStore
