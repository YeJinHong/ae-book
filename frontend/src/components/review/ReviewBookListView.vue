<template>
  <div>
    <div
      class="list-group-item"
      v-for="review in reviewBookList"
      :key="review.id"
    >
      <div>
        {{ review.reviewerNickname }} |
        {{ review.content }} |
        {{ review.score }}
      </div>
    </div>
    <div class="pagination-container">
      <pagination :pageSetting="reviewBookPageSetting" @paging="paging"></pagination>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import Pagination from '../common/Pagination.vue'

const reviewStore = 'reviewStore'

export default {
  name: 'ReviewBookListView',
  components: {
    Pagination
  },
  props: ['isbn'],
  data () {
    return {
      request: {
        isbn: this.isbn,
        page: 0,
        size: 3,
        sort: 'id',
        direction: 'DESC'
      }
    }
  },
  methods: {
    ...mapActions(reviewStore, ['getReviewBookListAction', 'getReviewBookPageAction']),
    paging (page) {
      this.request['page'] = page - 1
      this.getReviewBookListAction(this.request)
    }
  },
  computed: {
    ...mapState(reviewStore, ['reviewBookList', 'reviewBookPageSetting']),
    ...mapGetters(reviewStore, ['getReviewBookList'])
  },
  mounted () {
    const request = {
      isbn: this.isbn,
      page: 0,
      size: 3,
      sort: 'id',
      direction: 'DESC'
    }
    this.getReviewBookListAction(request)
  }
}
</script>

<style scoped>
.pagination-container {
  display:flex;
  justify-content: center;
}
</style>
