<template>
  <div>
    <h1>동화책 목록</h1>
    <router-link to="/story/keyword"><button class="ae-btn btn-red">동화 만들러 가기</button></router-link>
    <ModalView :modalShow="isModalVisible" @close-modal="closeModal">
      <story-detail-view/>
      <story-modal-button @close="closeModal"></story-modal-button>
    </ModalView>
    <div v-if="isLoginUser === false" class="story-container">
      로그인한 유저만 확인 가능합니다.
    </div>
    <div v-else class="story-container">
      <div v-for="story in storyList" :key="story.id" @click="showModal(painting.id)">
        <list-item
            :item="story"
          >
        </list-item>
      </div>
    </div>
    <div class="pagination-container">
      <pagination :pageSetting="storyPageSetting" @paging="paging"></pagination>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import ListItem from '../common/list/ListItem.vue'
import Pagination from '../common/Pagination.vue'
import StoryModalButton from '../story/StoryModalButton.vue'
import StoryDetailView from '@/components/story/StoryDetailView.vue'
import ModalView from '@/components/common/ModalView.vue'
const storyStore = 'storyStore'

export default {
  name: 'StoryListView2',
  data () {
    return {
      request: null,
      isModalVisible: false,
      isLoginUser: false
    }
  },
  components: {
    ListItem,
    Pagination,
    StoryModalButton,
    StoryDetailView,
    ModalView
  },
  computed: {
    ...mapState(storyStore, ['storyList', 'storyPageSetting'])
  },
  mounted () {
    if (sessionStorage.getItem('isLoginUser') === 'true') {
      this.isLoginUser = true
      this.getStoryList()
    }
  },
  methods: {
    ...mapActions(storyStore, ['getStoryList', 'getStoryDetail']),
    onClickList (type) {
      this.request = {
        type: type
      }
      this.getStoryList(this.request)
    },
    paging (page) {
      this.request['page'] = page - 1
      this.getStoryList(this.request)
    },
    showModal (paintingId) {
      this.getStoryDetail(paintingId)
      this.isModalVisible = true
    },
    closeModal () {
      this.isModalVisible = false
    }
  }
}
</script>

<style scoped>

h1 {
  font-weight: 800;
  margin: 10px 0px;
}
.story-container{
  margin: auto;
  margin-top: 30px;
  width: 1000px;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
}

.pagination-container {
  display:flex;
  justify-content: center;
}

.disabled{
  background-color: lightgray;
}
</style>
