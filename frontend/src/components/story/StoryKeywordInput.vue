<template>
  <div>
    <input type="text" v-model="inputValue" />
    <button @click="sendInputValue">전송</button>
    <div>{{storyResult}}</div>
    <div>{{ inputValue }}</div>
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
      storyResult: ''
    }
  },
  methods: {
    sendInputValue () {
      console.log(typeof this.inputValue)
      axios
        .post(`http://127.0.0.1:8000/stories/gpt`, {
          text: this.inputValue
        })
        .then(result => {
          console.log(result)
          this.storyResult = result.data
        })
        .catch(err => {
          console.log(err)
        })
    }
  }
}
</script>
