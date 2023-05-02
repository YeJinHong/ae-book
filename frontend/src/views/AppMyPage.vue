<template>
  <div>
    <header-navbar></header-navbar>
    <div>This is MyPage</div>
    <my-page-menu @goTo="goTo"></my-page-menu>
     <router-link :to="{ name: 'MyReview'}">마이 서평</router-link>
    <router-view></router-view>
  </div>
</template>

<script>
import HeaderNavbar from '../components/HeaderNavbar.vue'
import MyPageMenu from '../components/myPage/MyPageMenu.vue'

export default {
  name: 'AppMyPage',
  components: {
    HeaderNavbar,
    MyPageMenu
  },
  methods: {
    goTo (componentName) {
      this.$router.push({ name: componentName })
    }
  }
}
</script>

<style>

</style>
