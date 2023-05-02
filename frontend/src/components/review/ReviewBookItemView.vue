<template>
  <div class="review-item">
    <div class="line-1">
      <div class="item-user-info">
        <!-- TODO: 유저 프로필 사진 받아야 함 -->
        <!-- {{ review.reviewerProfileImg }} -->
      {{ review.reviewerNickname }} |
      </div>
      <div class='btn-group'>
        <button class='ae-btn btn-navy'>수정</button>
        <button class='ae-btn btn-red'>삭제</button>
      </div>
      <div class="item-score">
        <review-score-view :score=review.score />
      </div>
    </div>
    <div class="item-content">
      {{ review.content }} |
    </div>
  </div>
</template>

<script>
import ReviewScoreView from './ReviewScoreView.vue'

export default {
  name: 'ReviewBookItemView',
  components: {
    ReviewScoreView
  },
  props: ['review']

}
</script>

<style>
.line-1 {
  display: flex;
  align-items: center;
}
.item-user-info {
  display: flex;
  justify-content: flex-start;
}
.item-content {
  display: flex;
  padding: 10px 0px;
  text-align: left;
}
.item-scroe {
  display: flex;
  margin-left: auto;
  justify-content: right;
}
.review-item {
  padding : 20px 30px;
  border: 0.5px solid rgb(200, 199, 199);
  height: 180px;
  width: 700px;
  border-radius: 45px;
}
.btn-gruop {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}
</style>
