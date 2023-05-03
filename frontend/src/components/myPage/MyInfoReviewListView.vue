<template>
  <div>
    마이페이지 리뷰 리스트
    <!-- TODO: 정렬 -->
    <div
      class="list-group-item"
      v-for="review in reviewMyList"
      :key="review.id"
      >
      <div>
        {{ review.reviewerNickname }} |
        {{ review.content }} |
        {{ review.score }}
      </div>
    </div>
    <div class="pagination-container">
      <pagination :pageSetting="reviewMyPageSetting" @paging="paging"></pagination>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import Pagination from '../common/Pagination.vue'

const reviewStore = 'reviewStore'

export default {
  name: 'MyInfoReviewListView',
  components: {
    Pagination
  },
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

<style scoped></style>
