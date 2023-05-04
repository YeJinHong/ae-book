<template>
  <div>
    <h2>나의 동화 목록</h2>
    <ModalView :modalShow="isModalVisible" @close-modal="closeModal">
      <StoryDetailView />
    </ModalView>
    <div class="carousel-container">
      <StoryCarouselView :items="storyList" :chunkSize="3" @moveTo="showModal"></StoryCarouselView>
    </div>
  </div>
</template>

<script>
import { searchStory } from '@/api/story'
import ModalView from '@/components/common/ModalView'
import StoryDetailView from '@/components/story/StoryDetailView'
import { mapMutations, mapGetters } from 'vuex'
import StoryCarouselView from '@/components/story/StoryCarouselView'

const storyStore = 'storyStore'

export default {
  name: 'StoryListView',
  components: {
    ModalView,
    StoryDetailView,
    StoryCarouselView
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
