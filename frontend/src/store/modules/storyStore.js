import { searchMyStory, registerStory, searchStoryList } from '@/api/story'

const storyStore = {
  namespaced: true,

  state: {
    storyId: null,
    storyPageSetting: null,
    storyList: [],
    story: null
  },

  mutations: {
    setStoryId (state, id) {
      state.storyId = id
    },
    clearStoryId (state) {
      state.storyId = null
    },
    SET_PAGE_SETTING: (state, data) => {
      const { pageable, last, first, totalPages, size, totalElements, numberOfElements, empty } = data
      state.storyPageSetting = { pageable, last, first, totalPages, size, totalElements, numberOfElements, empty }
    },
    SET_LIST: (state, data) => {
      state.storyList = data
    },
    SET_STORY: (state, data) => {
      state.story = data
    }
  },
  getters: {
    getStoryId: state => {
      return state.storyId
    }
  },

  actions: {
    async getMyStoryList ({commit}, request) {
      await searchMyStory(request)
        .then(({data}) => {
          console.log(data)
          commit('SET_PAGE_SETTING', data.result)
          commit('SET_LIST', data.result.content)
        })
        .catch(error => {
          console.error(error)
        })
    },
    async getStoryList ({commit}, request) {
      await searchStoryList(request)
        .then(({data}) => {
          console.log(data)
          commit('SET_PAGE_SETTING', data.result)
          commit('SET_LIST', data.result.content)
        })
        .catch(error => {
          console.error(error)
        })
    },
    async saveStory ({ commit }, request) {
      await registerStory(request)
        .then(({ data }) => {
          commit('SET_STORY', data.result)
        })
        .catch(error => {
          console.error(error)
        })
    }
  }
}

export default storyStore
