import { searchStory, searchDetailStory, registerStory } from '@/api/story'

const story = {
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
    async getStoryList ({commit}, request) {
      await searchStory(request)
        .then(({data}) => {
          commit('SET_PAGE_SETTING', data.result)
          commit('SET_LIST', data.result.content)
        })
        .catch(error => {
          console.log(error)
        })
    },
    async getStoryDetail ({ commit }, request) {
      await searchDetailStory(request)
        .then(({ data }) => {
          commit('SET_STORY', data.result)
        })
        .catch(error => {
          alert(error)
        })
    },
    async saveStory ({ commit }, request) {
      await registerStory(request)
        .then(({ data }) => {
          commit('SET_STORY', data.result)
          console.log(data.result)
        })
        .catch(error => {
          console.log(error)
        })
    }
  }

}

export default story
