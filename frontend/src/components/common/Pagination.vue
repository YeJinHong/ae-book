<template>
    <div>
    <div class="page-list">
      <ul>
        <li
        class="pagebtn pre"
        :class="{ disabled: pageSetting.first }"
        @click="sendPage(pageSetting.pageable.pageNumber)"
      >◀</li>
        <li
          class="pagebtn"
          :class="{ active: page-1 === pageSetting.pageable.pageNumber }"
          v-for="page in computedPages"
          :key="page"
          @click="sendPage(page)"
        >
          {{ page }}
        </li>
        <li
        class="pagebtn next"
        :class="{ disabled: pageSetting.last }"
        @click="sendPage(pageSetting.pageable.pageNumber+2)"
      >▶</li>
      </ul>

    </div>
  </div>
</template>

<script>
export default {
  name: 'Pagination',
  props: ['pageSetting'],
  computed: {
    computedPages () {
      const currentPage = this.pageSetting.pageable.pageNumber + 1
      const totalPages = this.pageSetting.totalPages

      if (totalPages <= 10) {
      // 전체 페이지 수가 10 이하일 경우
        console.log('1')
        return Array.from({ length: totalPages }, (v, i) => i + 1)
      } else if (currentPage <= 10) {
      // 현재 페이지가 10 이하일 경우
        console.log('2')
        return Array.from({ length: 10 }, (v, i) => i + 1)
      } else if (currentPage > totalPages - 10) {
      // 현재 페이지가 끝에서 10 이하일 경우
        console.log('3')
        return Array.from({ length: totalPages % 10 }, (v, i) => Math.floor(totalPages / 10) * 10 + i + 1)
      }
    }
  },
  methods: {
    sendPage (page) {
      this.$emit('paging', page)
    }
  }
}
</script>

<style scoped>
</style>
