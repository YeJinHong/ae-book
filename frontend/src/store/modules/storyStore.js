import { searchStory } from '@/api/story'

const story = {
  namespaced: true,

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
