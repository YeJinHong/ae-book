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
        page: 1,
        size: 8,
        sort: 'id'
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
