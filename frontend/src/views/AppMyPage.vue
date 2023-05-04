<template>
  <div>
    <header-navbar></header-navbar>
    <div class="container">
    <div class="left">
      <my-page-menu @goTo="goTo"></my-page-menu>
    </div>
    <div class="right">
      <router-view></router-view>
    </div>
  </div>
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

<style scoped>
.container {
  display: flex;
  flex-direction: row;
  margin-top: 50px;
}

.left {
  width: 20%;
  background-color: #ccc;
  margin-right: 50px;
}

.right {
  width: 80%;
  background-color: #eee;
}
</style>
