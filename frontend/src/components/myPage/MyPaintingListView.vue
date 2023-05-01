<template>
  <div>
    <h1>그림장</h1>
    <ModalView v-if="isModalViewd" @close-model="isModalViewd=false">
    <Content/>
    </ModalView>
    <div
        v-for="painting in paintingList"
        :key="painting.id"
        @click="isModalViewd=true"
      >
      <img v-bind:src="painting.fileUrl" class="painting-image">
      {{ painting.title }}
    </div>
</div>

</template>

<script>
import { mapActions, mapState } from 'vuex'
import Content from './contents/Painting'
import ModalView from './ModalView.vue'
const paintingStore = 'paintingStore'

export default {
  name: 'MyPaintingListView',
  components: {
    Content,
    ModalView
  },
  data () {
    return {
      isModalViewd: false
    }
  },
  computed: {
    ...mapState(paintingStore, ['paintingList'])
  },
  mounted () {
    this.getPaintingList('COLOR')
  },
  methods: {
    ...mapActions(paintingStore, ['getPaintingList'])
  }
}
</script>

<style>

</style>
