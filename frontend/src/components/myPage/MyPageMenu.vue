<template>
  <div class="menu">
    <ul>
        <li class="mypage-btn" @click="goTo('MyNotification')">알림설정한 책</li>
        <li class="mypage-btn" @click="goTo('mypainting')">그림장</li>
        <li class="mypage-btn" @click="goTo('StoryListView')">동화책</li>
        <li class="mypage-btn" @click="goTo('MyReview')">작성한 서평</li>
        <li class="mypage-btn" @click="goTo('#')">탈퇴하기</li>
      </ul>
  </div>
</template>

<script>
export default {
  name: 'MyPageMenu',
  methods: {
    goTo (componentName) {
      this.$emit('goTo', componentName)
    }
  }
}
</script>

<style scoped>
.menu {
  background-color: var(--main-yellow);
  width: 243px;
  height: 475px;
  float: left;
  border: 5px solid white;
  border-radius: 15px;
  box-shadow: 2px 4px 4px 0 rgba(0, 0, 0, 0.15);
}

.mypage-btn {
  width: 149px;
  height: 36px;
  border-radius: 30px;
  background-color: white;
  margin-top: 10px;
  font-weight: 800;
  color: var(--ae-red);
  cursor: pointer;
}
</style>
