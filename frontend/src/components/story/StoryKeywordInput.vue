<template>
  <div>
    <input type="text" v-model="inputValue" />
    <button @click="sendInputValue">전송</button>
    <div>{{storyResult}}</div>
    <button @click="playAudio(sound)">오디오 재생</button>
    <!-- <StoryChatGptView :storyResult="storyResult"></StoryChatGptView> -->
  </div>
</template>

<script>
import axios from 'axios'
import StoryChatGptView from './StoryChatGptView.vue'

export default {
  components: {
    StoryChatGptView
  },
  data () {
    return {
      inputValue: '',
      storyResult: '',
      rescode: '',
      sound: '/fast/sound/'
    }
  },
  methods: {
    playAudio (sound) {
      if (sound) {
        const audio = new Audio(sound)
        audio.play()
      }
    },
    sendInputValue () {
      console.log(typeof this.inputValue)
      axios
        .post(`/fast/stories/gpt`, {
          text: this.inputValue
        })
        .then(result => {
          this.storyResult = result.data.story
          axios({
            url: `/fast/stories/sound`,
            method: 'post',
            data: {
              data: this.storyResult
            }
          })
            .then(result => {
              console.log(result)
              this.rescode = result.data.rescode
              this.sound += result.data.sound
              console.log(this.sound)
            })
            .catch(err => {
              console.log(err)
            })
        })
        .catch(err => {
          console.log(err)
        })
    }
  }
}
</script>
