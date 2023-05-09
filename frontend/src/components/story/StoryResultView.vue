<template>
  <div>
    <label class="title">제목 : </label>
    <input type="text" v-model="title">
    <button @click="playAudio" class="ae-btn btn-red">오디오재생</button>
    <button @click="onSaveClick" class="ae-btn btn-red">동화 저장</button>
    <div class="container">
      <div class="container-left">
        <canvas id="canvas"
          ref="canvas"
          width="500"
          height="500"
        ></canvas>
      </div>
      <div class="container-right">
        {{ story }}
      </div>

    </div>
  </div>
  </template>

<script>
import { mapActions } from 'vuex'
const storyStore = 'storyStore'

export default {
  name: 'StoryResultView',
  data () {
    return {
      title: '',
      painting: '',
      story: null,
      voiceBlob: {},
      audio: Object
    }
  },
  created () {
    this.painting = this.$route.params.painting
    this.story = this.$route.params.story
    this.voiceBlob = this.$route.params.voiceBlob
  },
  mounted () {
    this.canvas = this.$refs.canvas
    this.ctx = this.canvas.getContext('2d')
    let image = new Image()
    image.src = this.painting
    image.onload = function () {
      this.ctx.drawImage(image, 0, 0, 500, 500)
    }.bind(this)
  },
  methods: {
    ...mapActions(storyStore, ['saveStory']),
    playAudio () {
      // let sound = 'http://localhost:8000/static/sound/' + this.sound
      // // const binaryString = this.sound.slice(2)
      // // const buffer = new ArrayBuffer(binaryString.length)
      // // const bytes = new Uint8Array(buffer)
      // // for (let i = 0; i < binaryString.length; i++) {
      // //   bytes[i] = binaryString.charCodeAt(i)
      // // }
      // // const blob = new Blob([buffer], { type: 'audio/wav' })
      // // const audio = new Audio(URL.createObjectURL(blob))
      // const audio = new Audio(sound)
      var blobURL = window.URL.createObjectURL(this.voiceBlob)
      this.audio = new Audio(blobURL)
      this.audio.play()
    },
    canvasToFile (canvas, milliseconds) {
      // canvas -> dataURL
      let imgBase64 = canvas.toDataURL('image/png')

      const byteString = atob(imgBase64.split(',')[1])
      const ab = new ArrayBuffer(byteString.length)
      const ia = new Uint8Array(ab)
      for (let i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i)
      }
      const blob = new Blob([ab], { type: 'image/png' })

      // blob -> file
      const paintingFile = new File([blob], 'story_painting_' + milliseconds + '.png', { type: 'image/png' })

      return paintingFile
    },
    voiceBlobToFile (voiceBlob, milliseconds) {
      return new File([voiceBlob], 'story_voice_' + milliseconds + '.wav', { type: 'audio/wav' })
    },
    onSaveClick () { // 임시 저장. (api 확인 XXXX)
      if (this.title.trim() === '') {
        alert('제목을 입력해주세요.')
        return
      }
      let milliseconds = new Date().getMilliseconds()
      const paintingFile = this.canvasToFile(this.canvas, milliseconds)
      const voiceFile = this.voiceBlobToFile(this.voiceBlob, milliseconds)

      let data = {
        title: this.title,
        content: this.story
      }

      let formData = new FormData()
      formData.append('voiceFile', voiceFile)
      formData.append('imageFile', paintingFile)
      formData.append('data', new Blob([JSON.stringify(data)], {type: 'application/json'}))

      this.saveStory(formData)
        .then(
          alert('동화 저장에 성공했습니다.')
        )
        .catch(error => {
          alert('동화 저장에 실패했습니다.' + error)
        })

      this.audio.pause()
      this.$router.push('/story/list')
    }
  }
}
</script>

<style scoped>
.container {
  padding: 0 100px;
  display: flex;
  flex-direction: row;
}

.container-left {
  width: 60%;
  margin-right: 30px;
}

.container-right {
  width: 40%;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
}

#canvas {
    border-radius: 15px;
    box-shadow: 0px 1px 10px 0.1px rgba(0, 0, 0, 0.3);
    float: left;
}

.title {
  font-size: 35px;
  font-weight: 800;
}

</style>
