<template>
  <div>사진(ISBN)으로 책 검색
    <div class="image-container">
      <input type="file" @change="onFileChange" />
      <img :src="imageUrl" v-if="imageUrl" />
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import { getISBNfromPicture } from '@/api/book.js'
import ModalView from '@/components/common/ModalView.vue'
const bookStore = 'bookStore'

export default {
  name: 'BookSearchByPictureView',
  data () {
    return {
      imageUrl: ''
    }
  },
  components: {
    ModalView
  },
  computed: {
    ...mapState(bookStore, ['book'])
  },
  methods: {
    ...mapActions(bookStore, ['getBookDetail']),
    async getISBNfromPicture (request) {
      await getISBNfromPicture(request)
        .then(({ data }) => {
          console.log('성공')
          console.log(data)
          // 근데 만약.. ISBN을 잘 읽어도 DB에 존재하지 않는다면 에러 404..
          this.$router.push(`/book/detail/${data.data}`)
        })
        .catch(error => {
          alert('ISBN이 올바르지 않습니다.')
          console.log(error)
        })
    },
    onFileChange (e) {
      const file = e.target.files[0]
      this.previewImage(file)
      const formData = new FormData()
      formData.append('image', file)
      this.getISBNfromPicture(formData)
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

<style scoped>
</style>
