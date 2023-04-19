<template>
  <div>
  <header-navbar></header-navbar>
  <div> This is Painting Page</div>
  <router-view></router-view>
</div>
</template>

<script>
import HeaderNavbar from '@/components/HeaderNavbar'

export default {
  name: 'AppPainting',
  components: {
    HeaderNavbar
  }
}

</script>

<style scoped>
</style>
