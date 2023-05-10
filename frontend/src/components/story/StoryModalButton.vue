<template #modal-footer>
  <div class="modal-footer">
    <!-- Emulate built in modal footer ok and cancel button actions -->
    <b-button size="sm" variant="danger" @click="deleteStoryById()">
      삭제
    </b-button>
    <b-button @click="playAudio(stop)" size="sm"><img src="../../assets/images/icons/play.png" width="20" height="20"></b-button>
    <b-button @click="stopAudio(stop)" size="sm" variant="primary"><img src="../../assets/images/icons/pause.png" width="20" height="20"></b-button>
    <b-button @click="restartAudio()" size="sm" variant="warning"><img src="../../assets/images/icons/redo.png" width="20" height="20"></b-button>
    <b-button @click="closeModal" size="sm" variant="dark">닫기</b-button>
  </div>
</template>
<script>

import { deleteStory } from '@/api/story'
import { mapGetters } from 'vuex'

const storyStore = 'storyStore'

export default {

  components: {
  },
  data () {
    return {
      audio: Object,
      stop: true
    }
  },
  props: {
    voiceUrl: String
  },

  mounted () {
    this.audio = new Audio(this.voiceUrl)
  },

  methods: {
    closeModal () {
      this.audio.pause()
      this.$emit('close')
    },

    deleteStoryById () {
      this.storyId = this.getStoryId
      deleteStory(this.storyId)
        .then(response => {
          if (response.data.resultCode === 200) {
            alert('정상적으로 삭제했습니다.')
            this.closeModal()
          }
        })
        .catch(error => {
          alert('정상적으로 삭제하지 못했습니다. ' + error)
        })
    },

    playAudio (stop) {
      if (stop) {
        this.audio.play()
        this.stop = !stop
      }
    },
    stopAudio (stop) {
      if (stop === false) {
        this.audio.pause()
        this.stop = !stop
      }
    },
    restartAudio () {
      this.audio.pause()
      this.audio.currentTime = 0
      this.audio.play()
      this.stop = !stop
    }

  },

  computed: {
    ...mapGetters(storyStore, ['getStoryId'])
  }
}
</script>

<style>

</style>
