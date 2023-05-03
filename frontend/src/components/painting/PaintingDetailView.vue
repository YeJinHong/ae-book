<template>
<div>
  <h1>{{ painting.title }}</h1>
  <img v-bind:src="painting.fileUrl">
</div>
</template>

<script>
import { mapState } from 'vuex'
const paintingStore = 'paintingStore'

export default {
  name: 'PaintingDetailView',
  computed: {
    ...mapState(paintingStore, ['painting'])
  }
}
</script>

<style>

</style>
