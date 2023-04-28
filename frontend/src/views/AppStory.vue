<template>
  <div>
    <header-navbar></header-navbar>
    <div>This is Story page</div>
    <router-view></router-view>
  </div>
</template>

<script>
import HeaderNavbar from '@/components/HeaderNavbar'

export default {
  name: 'AppStory',
  components: {
    HeaderNavbar
  }
}
</script>

<style scoped></style>
