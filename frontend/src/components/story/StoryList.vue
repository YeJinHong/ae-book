<template>
  <div>
    <h2>나의 동화 목록</h2>
    <div v-for="story in storyList" :key="story.storyId">
      <h3>{{ story.title }}</h3>
      <p>{{ story.content }}</p>
      <img v-bind:src="story.imgUrl" alt="story image" />
      <p>작성자: {{ story.storyAuthorNickname }}</p>
      <p>작성일: {{ story.createdAt }}</p>
    </div>
  </div>
</template>

<script>
import { searchStory } from '@/api/story'

export default {
  name: 'StroyList',
  data () {
    return {
      page: {
        page: 0,
        size: 8,
        sort: 'id'
      },
      storyList: []
    }
  },
  mounted () {
    searchStory(this.page)
      .then(response => {
        this.storyList = response.data.result.content
      })
      .catch(error => {
        alert('정상적으로 조회하지 못했습니다. ' + error)
      })
  }
}
</script>

<style></style>
