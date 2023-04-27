<template>
  <div>
    <header-navbar></header-navbar>
    <div>리뷰 페이지임다 -_-a;;;;</div>
    <router-link :to="{ name: 'Review'}">
      review
    </router-link>
    <router-link :to="{ name: 'ReviewCreate'}">
      create
    </router-link>
    <router-link :to="{ name: 'ReviewDetail'}">
      detail
    </router-link>
    <router-link :to="{ name: 'ReviewModify'}">
      modify
    </router-link>
    <router-view></router-view>
  </div>
</template>

<script>
import HeaderNavbar from '../components/HeaderNavbar.vue'

export default {
  name: 'AppReview',
  components: {
    HeaderNavbar
  }

}
</script>

<style>

</style>
