<template>
  <div class="review-item">
    <div>
      <div class="item-gruop-1">
        <div class="item-user-info">
          <!-- TODO: 유저 프로필 사진 받아야 함 -->
          <!-- {{ review.reviewerProfileImg }} -->
          <div class="user-nickname">
          {{ review.reviewerNickname }}
          </div>
          <div class="user-updated-at">
          {{ review.updatedAt.slice(0, 10) }}
          </div>
        </div>
        <!-- TODO: 유저 ID 받아야 함 -->
        <!-- <div v-if="userInfo.userId == review.reviewerId" class='btn-gruop'> -->
        <div class='btn-gruop'>
          <div v-if="!isModify">
            <button class='orange-btn' @click="modifyReview">수정</button>
            <button class='orange-btn' @click="deleteReview">삭제</button>
          </div>
          <div v-if="isModify">
            <button class='orange-btn' @click="checkValue">완료</button>
            <button class='orange-btn' @click="cancleModify">취소</button>
          </div>
        </div>
        <div class="item-score">
          <review-score-view v-if="!isModify" :score=review.score />
          <review-modify-score-view v-if="isModify" :score=review.score :isModify=this.isModify @modify-score="modifyScore" />
        </div>
      </div>
      <div class="item-group-2">
        <div v-if="!isModify" class="item-content">
          {{ review.content }}
        </div>
        </div>
        <textarea v-if="isModify"
          id="reviewContent"
          class="form-control"
          style="width: 635.67px; height: 96px;"
          rows="4"
          v-model="updateContent">
        </textarea>
      </div>
    </div>
</template>

<script>
import { mapActions } from 'vuex'
import ReviewScoreView from './ReviewScoreView.vue'
import ReviewModifyScoreView from './ReviewModifyScoreView.vue'

const reviewStore = 'reviewStore'

export default {
  name: 'ReviewBookItemView',
  components: {
    ReviewScoreView,
    ReviewModifyScoreView
  },
  props: ['review', 'page'],
  data: function () {
    return {
      isModify: false,
      updateScore: this.review.score,
      updateContent: this.review.content,
      userInfo: JSON.parse(sessionStorage.getItem('userInfo'))
    }
  },
  methods: {
    ...mapActions(reviewStore, ['modifyReviewAction', 'deleteReviewAction']),
    async checkValue () {
      let err = true
      let msg = ''

      if (!this.updateContent) {
        msg = '내용을 입력해주세요'
        err = false
        this.$refs.reviewContent.focus()
      }

      // 에러가 있으면 메세지 찍기
      if (!err) alert(msg)
      // 만약, 내용이 다 입력되어 있다면 리뷰 수정 후 getReviewBookListAction(request)
      else {
        const payload = {
          reviewId: this.review.id,
          data: {
            content: this.updateContent,
            score: this.updateScore
          }
        }

        // 1. 댓글 수정
        await this.modifyReviewAction(payload)
        await this.$emit('paging', this.page + 1)

        // 2. 수정 반영해서 리스트 가져오기 : emit 완료보다 상태변경이 빨라서 딜레이 설정
        setTimeout(() => {
          this.isModify = false
        }, 400)
      }
    },
    modifyReview () {
      console.log('modifyComment : ' + this.review.id)
      if (!this.isModify) {
        this.isModify = true
      }
    },
    cancleModify () {
      this.isModify = false
    },
    modifyScore (newScore) {
      this.updateScore = newScore
      console.log('updateScroe : ' + this.updateScore)
    },
    async deleteReview () {
      console.log('?@#?!@#!@$?')
      if (confirm('리얼루다가 삭제하시것슴니까 ?!?!?!?!!!?')) {
        await this.deleteReviewAction(this.review.id)
      }

      this.$emit('paging', this.page + 1)
    },
    check (index) {
      console.log(index)
      this.review.score = index
    }
  }

}
</script>

<style>
.user-nickname {
  color: var(--ae-navy);
  font-weight: bold;
  font-size: 1.4em;
}
.user-updated-at {
  font-size: 0.5em;
}
.item-gruop-1 {
  display: flex;
  align-items: center;
  margin-left: 13px;
  /* justify-content: space-between; */
}
.item-user-info {
  /* display: flex; */
  justify-content: flex-start;
  margin-right: 10px;
}
.item-content {
  display: flex;
  padding: 10px 0px;
  text-align: left;
  padding: 6px 12px;
}
.item-score {
  display: flex;
  margin-left: auto;
}
.review-item {
  background-color: white;
  padding : 20px 30px;
  border: 0.5px solid rgb(200, 199, 199);
  height: 180px;
  width: 700px;
  border-radius: 45px;
}
.btn-gruop {
  display: flex;
  justify-content: flex-start;
}
.orange-btn {
  color: var(--ae-red);;
  border: none;
  background: none;
}
</style>
