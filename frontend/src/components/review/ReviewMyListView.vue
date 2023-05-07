<template>
  <div>
    <div class="subject-line">
      <div class="subject">작성한 서평</div>
      <div class="gray-bar"></div>
    </div>
    <div
      class="list-group-item"
      v-for="review in reviewMyList"
      :key="review.id"
    >
      <review-my-item-view :review=review :page=request.page @paging="paging"></review-my-item-view>
    </div>
    <div class="pagination-container">
      <pagination :pageSetting="reviewMyPageSetting" @paging="paging"></pagination>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import Pagination from '../common/Pagination.vue'
import ReviewMyItemView from './ReviewMyItemView.vue'

const reviewStore = 'reviewStore'

export default {
  components: {
    Pagination,
    ReviewMyItemView
  },
  name: 'ReviewMyListView',
  data () {
    return {
      request: {
        page: 0,
        size: 3,
        sort: 'createdAt',
        direction: 'DESC'
      }
    }
  },
  methods: {
    ...mapActions(reviewStore, ['getReviewMyListAction']),
    paging (page) {
      this.request['page'] = page - 1
      this.getReviewMyListAction(this.request)
    }
  },
  computed: {
    ...mapState(reviewStore, ['reviewMyList', 'reviewMyPageSetting'])
  },
  mounted () {
    const request = {
      page: 0,
      size: 3,
      sort: 'createdAt',
      direction: 'DESC'
    }
    this.getReviewMyListAction(request)
  }
}
</script>

<style scoped>
.list-group-item {
  background: none;
  border: none;
  display: flex;
  justify-content: left;
  padding: 3px;
}
.pagination-container {
  display:flex;
  justify-content: left;
  margin-left: 230px
}
.subject{
  text-align: left;
  font-size: 24px;
  font-weight: 800;
  margin-right: 15px;
}
.subject-line{
  width: 1000px;
  justify-content: left;
  align-items: center;
}
.gray-bar {
  margin-top: 3px;
  margin-bottom: 20px;
  height: 2px;
  background-color: var(--stroke-gray);
  width: 70%;
}
</style>
