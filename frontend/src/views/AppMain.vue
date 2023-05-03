<template>
    <div>
    <header-navbar-main></header-navbar-main>
    <div>아동 도서 콘텐츠 서비스</div>
    <!-- TODO : 각기 다음에 필요한 컴포넌트는 components/{domain} 하위에 작성 -->
    <!-- TODO : 새로 들어온 책 콘텐츠 케러셀 컴포넌트-->
    <carousel :items="reviewMainList"></carousel>
    <!-- TODO : 우리 아이들 작품(만든 동화) 리스트 컴포넌트 -->
    <the-footer></the-footer>
  </div>
</template>

<script>
import HeaderNavbarMain from '@/components/HeaderNavbarMain'
import TheFooter from '@/components/TheFooter'
import Carousel from '@/components/CarouselView'
import { mapState, mapActions } from 'vuex'

const reviewStore = 'reviewStore'

export default {
  name: 'AppMain',
  components: {
    HeaderNavbarMain,
    TheFooter,
    Carousel
  },
  methods: {
    ...mapActions(reviewStore, ['getReviewMainListAction'])
  },
  computed: {
    ...mapState(reviewStore, ['reviewMainList'])
  },
  mounted () {
    this.getReviewMainListAction()
  }
}

</script>

<style scoped>
.main-review-container {
  border: 2px solid;
  border-color: darkmagenta;
  display: flex;  /* flexbox 적용 */
  flex-wrap: wrap; /* 라인을 넘어가면 다음 라인으로 이동 */
  justify-content: space-between; /* 요소 사이의 간격을 최대한 벌림 */
}
.main-review-item {
  border: 2px solid;
  border-color: rgba(0, 255, 76, 0.418);
  width: 30%; /* 각 항목의 너비 */
  margin-bottom: 20px; /* 각 항목의 아래쪽 여백 */
  padding: 10px; /* 각 항목의 안쪽 여백 */
  border: 1px solid gray; /* 경계선 */
}
</style>
