<template>
  <div class="review-item" :style="{ height: isExpanded ? 'auto' : '170px' }">
    <div>
      <div class="item-gruop-1">
        <div class="item-user-info">
          <img class="item-user-img" :src=review.reviewerImg />
          <div>
            <div class="user-nickname">
            {{ review.reviewerNickname }}
            </div>
            <div class="user-updated-at">
            {{ review.updatedAt.slice(0, 10) }}
          </div>
          </div>
        </div>
        <div v-if="userInfo.userId == review.reviewerId" class='btn-gruop'>
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
        <div
          v-show="!isModify"
          class="item-content"
          ref="itemContent"
        >
          <p v-show="!isTruncated && !isExpanded">{{ review.content }} </p>
          <p v-show="isTruncated && !isExpanded">
            {{ truncatedContent }}
            <button class="more-content orange-btn" @click="expandContent">
            더보기
          </button></p>
          <p v-show="isTruncated && isExpanded">
            {{ review.content }}
            <button class="more-content orange-btn" @click="shrinkContent">
            닫기
          </button>
          </p>
        </div>
        </div>
        <textarea v-show="isModify"
          id="reviewContent"
          class="item-modify"
          rows="3"
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
      userInfo: JSON.parse(sessionStorage.getItem('userInfo')),

      // 더보기
      isTruncated: false,
      isExpanded: false,
      truncatedContent: ''
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
      if (!this.isModify) {
        this.isModify = true
      }
    },
    cancleModify () {
      this.isModify = false
    },
    modifyScore (newScore) {
      this.updateScore = newScore
    },
    async deleteReview () {
      if (confirm('리얼루다가 삭제하시것슴니까 ?!?!?!?!!!?')) {
        await this.deleteReviewAction(this.review.id)
      }

      this.$emit('paging', this.page + 1)
    },
    check (index) {
      this.review.score = index
    },

    // 더보기
    truncateContent () {
      if (this.getContentLength() < 116) {
        return
      }
      this.isTruncated = true
      this.truncatedContent = this.review.content.slice(0, 115) + '...'
    },
    expandContent () {
      this.isExpanded = true
    },
    shrinkContent () {
      this.isExpanded = false
    },
    getContentLength () {
      return this.review.content.length
    }
  },
  mounted () {
    this.truncateContent()
  }
}
</script>

<style>
.item-content.is-expanded {
  height: auto !important;
  overflow: visible !important;
}
.item-content {
  padding: 10px 0px;
  text-align: left;
  padding: 6px 12px;
  font-size: 1em;
  width: 632px;
  /* height: 73px; */
  /* height: 80px; */
  overflow: hidden;
  display: flex;
}
.more-content {
  border: none;
}
.item-user-img {
  display: flex;
  width: 50;
  height: 50px;
  border-radius: 30px;
  margin-right: 20px;
}
.user-nickname {
  color: var(--ae-navy);
  font-weight: bold;
  font-size: 1.0em;
  margin-top: 6px;
  text-align: left;
}
.user-updated-at {
  font-size: 0.2em;
  color: var(--font-gray);
  text-align: left;
}
.item-gruop-1 {
  display: flex;
  align-items: center;
  margin-left: 13px;
  margin-bottom: 5px;
}
.item-user-info {
  display: flex;
  justify-content: flex-start;
  margin-right: 10px;
}
.item-group-2 {
  margin-left: 5px;
}
.item-modify {
  width: 637px;
  margin-left: 15px;
  margin-top: 3px;
  font-size: 1em;
  resize: none;
  height: auto;
}
.item-score {
  display: flex;
  margin-left: auto;
}
.review-item {
  background-color: white;
  padding : 20px 30px;
  border: 0.5px solid var(--stroke-gray);
  height: 180px;
  width: 700px;
  border-radius: 45px;
}
.btn-gruop {
  display: flex;
  justify-content: flex-start;
  margin-bottom: 16px;
}
.orange-btn {
  color: var(--ae-red);;
  border: none;
  background: none;
  font-weight: bold;
  font-size: 0.9em;
}
</style>
