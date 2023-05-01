import { searchStory } from '@/api/story'

const story = {
  namespaced: true,

  state: {
    storyId: null
  },

  mutations: {
    setStoryId (state, id) {
      console.log(id)
      state.storyId = id
    },
    clearStoryId (state) {
      state.storyId = null
    }
  },
  getters: {
    getStoryId: state => {
      return state.storyId
    }
  },

  actions: {
    getStoryList (pagination) {
      searchStory(pagination).then(({data}) => {
        console.log(data.result)
      }).catch(error => {
        console.log(error)
      })
    }
  }
}

export default story
