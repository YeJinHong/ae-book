<template>
  <div>
    <h1>동화 키워드 입력</h1>
    <input type="text" v-model="inputValue" />
    <button @click="sendInputValue" class="ae-btn btn-red">전송</button>
  </div>
</template>

<script>
export default {
  name: 'StroyKeywordInputView',
  data () {
    return {
      inputValue: ''
    }
  },
  methods: {
    sendInputValue () {
      this.$router.push({ name: 'StoryPaintingBoard', params: { inputValue: this.inputValue } })
    }
  }
}
</script>
<style scoped>
h1 {
  font-weight: 800;
  margin: 10px 0px;
}
</style>
