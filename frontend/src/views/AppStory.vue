<template>
  <div>
    <header-navbar></header-navbar>
    <div>This is Story page</div>
    <router-view></router-view>
    <StoryKeywordInput/>
  </div>
</template>

<script>
import HeaderNavbar from '@/components/HeaderNavbar'
import StoryKeywordInput from '@/components/story/StoryKeywordInput'

export default {
  name: 'AppStory',
  components: {
    HeaderNavbar,
    StoryKeywordInput
  }
}
</script>

<style scoped></style>
