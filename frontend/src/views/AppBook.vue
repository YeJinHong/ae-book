<template>
  <div>
    <header-navbar></header-navbar>
    <div> This is Book page</div>
    <router-view></router-view>
  </div>
</template>

<script>
import HeaderNavbar from '@/components/HeaderNavbar'

export default {
  name: 'AppBook',
  components: {
    HeaderNavbar
  }
}
</script>

<style scoped>
</style>
