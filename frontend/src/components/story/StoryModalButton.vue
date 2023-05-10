<template #modal-footer>
  <div class="modal-footer">
    <!-- Emulate built in modal footer ok and cancel button actions -->
    <b-button size="sm" variant="success" @click="updateTitle()">
      수정
    </b-button>
    <b-button size="sm" variant="danger" @click="deleteStoryById()">
      삭제
    </b-button>
  </div>
</template>
<script>

import { deleteStory, updateStoryTitle } from '@/api/story'
import { mapGetters } from 'vuex'

const storyStore = 'storyStore'

export default {

  components: {
  },
  data () {
    return {

    }
  },
  props: {
    title: String
  },
  methods: {
    closeModal () {
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
    updateTitle () {
      this.storyId = this.getStoryId
      this.request = {
        'title': this.title
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
  },

  computed: {
    ...mapGetters(storyStore, ['getStoryId'])
  }
}
</script>

<style>

</style>
