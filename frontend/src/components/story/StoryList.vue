<template>
  <div></div>
</template>

<script>
import { searchStory } from '@/api/story'

export default {
  name: 'StroyList',
  data () {
    return {
      page: {
        page: 0,
        size: 1,
        sort: ['']
      },
      storyList: []
    }
  },
  mounted () {
    searchStory(this.page)
      .then(response => {
        this.storyList = response.data
        console.log(this.storyList)
      })
      .catch(error => {
        console.log(error)
      })
  }
}
</script>

<style></style>
