Copy code
<template>
<!-- 상단 페이지 넘김 버튼 -->
  <div>
     <div class="my-controls">
      <button @click="prev">Previous</button>
      <button @click="next">Next</button>
    </div>
    <!-- 캐러셀 창 설정 -->
    <b-carousel
      ref="myCarousel"
      id="carousel-1"
      v-model="slide"
      :interval="4000"
      indicators
      background="#FFE5AB"
      img-width="1024"
      img-height="480"
      style="text-shadow: 1px 1px 2px #333;"
      @sliding-start="onSlideStart"
      @sliding-end="onSlideEnd"
    >
    <!-- 여기에 보여줄 리스트 넣으면 됨 -->
    <!-- 캐러셀 한 페이지에 N개의 요소를 보여주려고 chunk,,, chunkedReviews()에서 chunkSize 변경 가능 -->
      <template v-for="(reviewsChunk, index) in chunkedReviews">
        <!-- 캐러셀 배경 필요 ... 현재는 img-blank -->
        <b-carousel-slide :key="index" img-blank img-alt="Blank image">
          <div class="d-flex">
            <div v-for="review in reviewsChunk" :key="review.reviewId" class="chunk w-50">
              <!-- 리뷰를 나타내는 내용 추가 -->
              <h3>{{ review.reviewerNickname }}</h3>
              <h2>{{ review.content }}</h2>
              <h2>{{ review.score }}</h2>
            </div>
          </div>
        </b-carousel-slide>
      </template>
    </b-carousel>
  </div>
</template>

<script>
export default {
  name: 'Carousel',
  props: {
    reviews: {
      type: Array,
      required: true
    }
  },
  data () {
    return {
      slide: 0,
      sliding: null
    }
  },
  methods: {
    selectReview (review) {
      this.$emit('input', review)
    },
    onSlideStart (slide) {
      this.sliding = true
    },
    onSlideEnd (slide) {
      this.sliding = false
    },
    prev () {
      this.$refs.myCarousel.prev()
    },
    next () {
      this.$refs.myCarousel.next()
    }
  },
  computed: {
    chunkedReviews () {
      // 리뷰 리스트를 3개씩 자르기
      const chunkSize = 3
      return this.reviews.reduce((resultArray, item, index) => {
        const chunkIndex = Math.floor(index / chunkSize)
        if (!resultArray[chunkIndex]) {
          resultArray[chunkIndex] = [] // start a new chunk
        }
        resultArray[chunkIndex].push(item)
        return resultArray
      }, [])
    }
  }
}
</script>
<style scoped>
.chunk {
  border: 2px solid;
  border-color: darkmagenta;
  background-color: white;
}
</style>
