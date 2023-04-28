<template>
  <div>
    <h1>이미지 업로드해서 스케치로 바꾸기</h1>
    <span>아직 안됨.. 하지만 이미지 업로드하면 미리보기 가능 !!</span>
    <div class="image-container">
      <input type="file" @change="onFileChange" />
      <img :src="imageUrl" v-if="imageUrl" />
    </div>
    <button>돌아가기</button>
    <button>저장하기</button>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
const paintingStore = 'paintingStore'

export default {
  name: 'GenerateSketcheView',
  data () {
    return {
      imageUrl: ''
    }
  },
  computed: {
    ...mapState(paintingStore, ['paintingList'])
  },
  methods: {
    ...mapActions(paintingStore, ['getPaintingList']),
    onFileChange (e) {
      const file = e.target.files[0]
      this.previewImage(file)
    },
    previewImage (file) {
      const reader = new FileReader()
      reader.readAsDataURL(file)
      reader.onload = e => {
        this.imageUrl = e.target.result
      }
    }
  }
}
</script>

<style>

</style>
