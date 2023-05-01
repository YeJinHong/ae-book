<template>
  <div class="modal-overlay">
    <b-modal v-model="modalShow" @hidden="onHidden"> </b-modal>
  </div>
</template>

<script>

export default {
  name: 'ModalView',
  props: {
    modalShow: {
      type: Boolean,
      required: true
    }
  },
  data () {
    return {

    }
  },
  methods: {
    onHidden () {
      this.$emit('close-modal')
    }
  }
}
</script>

<style scoped>
</style>
