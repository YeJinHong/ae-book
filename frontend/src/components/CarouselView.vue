Copy code
<template>
<!-- 상단 페이지 넘김 버튼 -->
  <div>
    <div class="my-controls">
      <button @click="prev" class="carousel-btn">&lt;</button>
      <button @click="next" class="carousel-btn right">></button>
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
      img-height="300"
      style="text-shadow: 1px 1px 2px #333;"
      @sliding-start="onSlideStart"
      @sliding-end="onSlideEnd"
    >
    <!-- 여기에 보여줄 리스트 넣으면 됨 -->
    <!-- 캐러셀 한 페이지에 N개의 요소를 보여주려고 chunk,,, chunkeItems()에서 chunkSize 변경 가능 -->
      <template v-for="(itemsChunk, index) in chunkedItems">
        <!-- 캐러셀 배경 필요 ... 현재는 img-blank -->
        <b-carousel-slide :key="index" img-blank img-alt="Blank image">
          <div class="d-flex">
            <!-- key를 요소에 맞춰 설정 -->
            <div v-for="item in itemsChunk" :key="item.id" class="chunk w-50">
              <!-- 리뷰를 나타내는 내용 추가 -->
              <h3>{{ item.reviewerNickname }}</h3>
              <h2>{{ item.content }}</h2>
              <h2>{{ item.score }}</h2>
              <router-link :to="{ name: 'BookDetail', params: { isbn: item.isbn } }">
                책 보러가기
              </router-link>
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
    items: {
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
    chunkedItems () {
      // 리뷰 리스트를 3개씩 자르기
      const chunkSize = 3
      return this.items.reduce((resultArray, item, index) => {
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

.my-controls {
  width: 1000px;
  margin: auto;
  display: flex;
  justify-content: right;
  margin-bottom: 20px;
}

.carousel-btn{
  width: 40px;
  height: 40px;
  border-radius: 100%;
  color: var(--ae-navy);
  border: 1px solid #E5E3DA;
  background-color: white;
  margin-right: 5px;
}

.right{
  border: 1px solid var(--ae-red);
  background-color: var(--ae-red);
  color: white;
}
</style>
