<template>
  <div>
    <header-navbar></header-navbar>
    <div>This is MyPage</div>
    <router-view></router-view>
  </div>
</template>

<script>
import HeaderNavbar from '../components/HeaderNavbar.vue'

export default {
  name: 'AppMyPage',
  components: {
    HeaderNavbar
  }
}
</script>

<style>

</style>
