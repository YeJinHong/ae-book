<template>
<div>
  <input type="text" v-model="painting.title">
  <br>
  <button class="ae-btn btn-red" @click="updatePaintingTitle({paintingId: painting.id, title: {title:painting.title}})">수정</button>
  <img v-bind:src="painting.fileUrl">
</div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
const paintingStore = 'paintingStore'

export default {
  name: 'PaintingDetailView',
  data () {
    return {

    }
  },
  computed: {
    ...mapState(paintingStore, ['painting'])
  },
  methods: {
    ...mapActions('paintingStore', ['updatePaintingTitle'])
  }
}
</script>

<style>

</style>
