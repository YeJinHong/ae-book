<template>
  <div>
    <h2>나의 동화 목록</h2>
    <div v-for="story in storyList" :key="story.storyId">
      <h3 @click="showModal(story.storyId)">{{ story.title }}</h3>
      <p>{{ story.content }}</p>
      <img v-bind:src="story.imgUrl" alt="story image" />
      <p>작성자: {{ story.storyAuthorNickname }}</p>
      <p>작성일: {{ story.createdAt }}</p>
    </div>
    <ModalView :modalShow="isModalVisible" @close-modal="closeModal">
      <StoryDetailView />
    </ModalView>
  </div>
</template>

<script>
import { searchStory } from '@/api/story'
import ModalView from '@/components/common/ModalView'
import StoryDetailView from '@/components/story/StoryDetailView'
import { mapMutations } from 'vuex'

export default {
  name: 'StoryListView',
  components: {
    ModalView,
    StoryDetailView
  },
  data () {
    return {
      page: {
        page: 0,
        size: 8,
        sort: 'id'
      },
      storyList: [],
      isModalVisible: false
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
  },
  methods: {
    ...mapMutations('story', ['setStoryId', 'clearStoryId']),
    showModal (storyId) {
      this.isModalVisible = true
      this.setStoryId(storyId)
    },
    closeModal () {
      this.isModalVisible = false
      this.clearStoryId()
    }
  }
}
</script>

<style></style>
