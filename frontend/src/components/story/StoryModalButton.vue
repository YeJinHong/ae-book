<template #modal-footer>
  <div class="modal-footer">
    <!-- Emulate built in modal footer ok and cancel button actions -->
    <b-button size="sm" variant="success" @click="ok()">
      OK
    </b-button>
    <b-button size="sm" variant="danger" @click="cancel()">
      Cancel
    </b-button>
    <!-- Button with custom close trigger value -->
    <b-button size="sm" variant="outline-secondary">
      Forget it
    </b-button>
  </div>
</template>
<script>
export default {
  props: {
    modalShow: {
      type: Boolean,
      required: true
    }
  },
  components: {
  },
  data () {
    return {

    }
  },
  methods: {
    onHidden () {
      this.$emit('close-modal')
    }
  },
  watch: {
    modalShow (newValue) {
      this.visible = newValue
    }
  },
  computed: {
    visible: {
      get () {
        return this.modalShow
      },
      set (newValue) {
        this.$emit('update:modalShow', newValue)
      }
    }
  }
}
</script>

<style>

</style>
