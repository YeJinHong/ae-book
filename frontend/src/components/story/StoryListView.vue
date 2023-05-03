<template>
  <div>
    <h2>나의 동화 목록</h2>
    <div horizontal v-for="story in storyList" :key="story.storyId">
      <h3 @click="showModal(story.storyId)">{{ story.title }}</h3>
      <p>{{ story.content }}</p>
      <img v-bind:src="story.imgUrl" alt="story image" />
      <p>작성자: {{ story.storyAuthorNickname }}</p>
      <p>작성일: {{ story.createdAt }}</p>
    </div>
    <ModalView :modalShow="isModalVisible" @close-modal="closeModal">
      <StoryDetailView />
    </ModalView>
    <div class="carousel-container">
      <img-carousel-view :items="storyList" :chunkSize="3" @moveTo="showModal"></img-carousel-view>
    </div>
  </div>
</template>

<script>
import { searchStory } from '@/api/story'
import ModalView from '@/components/common/ModalView'
import StoryDetailView from '@/components/story/StoryDetailView'
import { mapMutations, mapGetters } from 'vuex'
import ImgCarouselView from '@/components/common/list/ImgCarouselView.vue'

const storyStore = 'storyStore'

export default {
  name: 'StoryListView',
  components: {
    ModalView,
    StoryDetailView,
    ImgCarouselView
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
  computed: {
    ...mapGetters(storyStore, ['getStoryId'])
  },
  methods: {
    ...mapMutations(storyStore, ['setStoryId', 'clearStoryId']),
    showModal (storyId) {
      this.setStoryId(storyId)
      console.log(storyId)
      this.isModalVisible = true
    },
    closeModal () {
      this.isModalVisible = false
      this.clearStoryId()
    }
  }
}
</script>

<style></style>
