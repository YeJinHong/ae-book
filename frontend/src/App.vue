<template>
  <div id="app">
    <router-view/>
    <the-footer></the-footer>
  </div>
</template>

<script>
import TheFooter from '@/components/TheFooter'
export default {
  name: 'App',
  components: {
    TheFooter
  }
}
</script>

<style>
</style>
