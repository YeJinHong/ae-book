<template>
    <div>
    <div class="page-list">
      <button
        class="pagebtn pre"
        v-if="!pageSetting.first"
        @click="sendPage(pageSetting.pageNumber-1)"
      >이전</button>
      <ul>
        <li
          class="pagebtn"
          :class="{ active: page === pageSetting.pageNumber }"
          v-for="page in pageSetting.totalPages"
          :key="page"
          @click="sendPage(page)"
        >
          {{ page }}
        </li>
      </ul>
      <button
        class="pagebtn next"
        v-if="!pageSetting.last"
        @click="sendPage(pageSetting.pageNumber+1)"
      >다음</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Pagination',
  props: ['pageSetting'],
  mounted () {
    console.log(this.pageSetting)
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
