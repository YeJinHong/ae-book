<template>
  <div >
    <img class="user-img" :src=imgUrl />
  </div>
</template>

<script>
export default {
  name: 'ReviewUserProfileImageView',
  props: {
    imgUrl: {
      type: String,
      required: true
    }
  }
}
</script>

<style scoped>
.user-img {
  width: 50px;
  height: 50px;
  border-radius: 30px;
  margin-right: 20px;
}
</style>
