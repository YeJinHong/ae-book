<template>
  <div>
    <b-input-group v-if="story">
      <template #prepend>
        <b-input-group-text>제목</b-input-group-text>
      </template>
      <b-form-input v-model="story.title" placeholder="Enter your name"></b-form-input>
      <template #append>
        <b-button @click="updateTitle()" variant="success" size="sm" >수정</b-button>
      </template>
    </b-input-group>
    <div class="container">
      <div class="container-left">
        <img v-bind:src="story.imgUrl" alt="story image" v-if="story" id="storyImg" />
      </div>
      <div class="container-right">
        {{ story.content }}
      </div>

    </div>
    <StoryModalButton :voiceUrl="story.voiceUrl" @close="closeModal"></StoryModalButton>
  </div>
</template>

<script>
import { searchDetailStory, updateStoryTitle } from '@/api/story'
import { mapGetters } from 'vuex'
import StoryModalButton from '@/components/story/StoryModalButton'
const storyStore = 'storyStore'

export default {
  name: 'StoryDetailView',
  components: {
    StoryModalButton
  },
  computed: {
    ...mapGetters(storyStore, ['getStoryId'])
  },

  data () {
    return {
      story: null
    }
  },
  mounted () {
    this.storyId = this.getStoryId

    searchDetailStory(this.storyId)
      .then(response => {
        this.story = response.data.result
      })
      .catch(error => {
        alert('정상적으로 조회하지 못했습니다. ' + error)
      })
  },
  methods: {
    closeModal () {
      this.$emit('close')
    },
    updateTitle () {
      this.storyId = this.getStoryId
      this.request = {
        'title': this.story.title
      }
      updateStoryTitle(this.storyId, this.request)
        .then(response => {
          if (response.data.resultCode === 200) {
            alert('정상적으로 수정했습니다.')
            this.closeModal()
          } else {
            alert('정상적으로 수정하지 못했습니다.')
          }
        }).catch(error => {
          alert('정상적으로 수정하지 못했습니다. ' + error)
        })
    }

  }

}
</script>

<style scoped>
.container {
  padding: 10px;
  display: flex;
  flex-direction: row;
}

.container-left {
  width: 50%;
  margin-left: 0px;
  margin-right: 30px;
}

.container-right {
  width: 50%;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
}

#storyImg{
  border-radius: 15px;
  box-shadow: 0px 1px 10px 0.1px rgba(0, 0, 0, 0.3);
  width: 100%;
  height: 95%;
}
</style>
