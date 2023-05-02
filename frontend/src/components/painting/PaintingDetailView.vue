<template>
<div>
  <h1>{{ painting.title }}</h1>
  <img v-bind:src="painting.fileUrl">
  <button class="ae-btn">닫기</button>
  <button class="ae-btn btn-navy">삭제</button>
  <button class="ae-btn btn-red">다운로드</button>
</div>
</template>

<script>
import { mapState } from 'vuex'
const paintingStore = 'paintingStore'

export default {
  name: 'PaintingDetailView',
  computed: {
    ...mapState(paintingStore, ['painting'])
  },
  methods: {
  }
}
</script>

<style>

</style>
