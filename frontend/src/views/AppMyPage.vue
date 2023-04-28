<template>
  <div>
    <header-navbar></header-navbar>
    <div>This is MyPage</div>
    <my-info></my-info>
    <router-view></router-view>
  </div>
</template>

<script>
import HeaderNavbar from '../components/HeaderNavbar.vue'
import MyInfo from '../components/myPage/MyInfo.vue'

export default {
  name: 'AppMyPage',
  components: {
    HeaderNavbar,
    MyInfo
  }
}
</script>

<style>

</style>
