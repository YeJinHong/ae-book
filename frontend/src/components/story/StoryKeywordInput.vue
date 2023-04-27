<template>
  <div>
    <input type="text" v-model="inputValue" />
    <button @click="sendInputValue">전송</button>
    <StoryChatGptView :storyResult="storyResult"></StoryChatGptView>
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
      axios
        .post(`/stories/gpt`, { value: this.inputValue })
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
