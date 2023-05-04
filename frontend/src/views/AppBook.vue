<template>
  <div>
    <header-navbar></header-navbar>
    <router-view></router-view>
    <the-footer></the-footer>
  </div>
</template>

<script>
import TheFooter from '@/components/TheFooter'
import HeaderNavbar from '@/components/HeaderNavbar'

export default {
  name: 'AppBook',
  components: {
    HeaderNavbar,
    TheFooter
  }
}
</script>

<style scoped>
</style>
