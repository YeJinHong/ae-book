<template>
  <div>
    <h1>그림 목록</h1>
    <button @click="getPaintingList('LINE')">선화</button>
    <button @click="getPaintingList('COLOR')">그림</button>
    <router-link to="/painting/generate"><button>선화 만들기</button></router-link>
    <div
        v-for="painting in paintingList"
        :key="painting.id"
      >
      <img v-bind:src="painting.fileUrl" class="painting-image" />
      {{ painting.title }}
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
const paintingStore = 'paintingStore'

export default {
  name: 'PaintingListView',
  computed: {
    ...mapState(paintingStore, ['paintingList'])
  },
  methods: {
    ...mapActions(paintingStore, ['getPaintingList'])
  }
}
</script>

<style>

</style>
