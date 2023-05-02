<template>
  <div>
    <header-navbar></header-navbar>
    <div>This is MyPage</div>
     <router-link :to="{ name: 'MyReview'}">마이 서평</router-link>
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
