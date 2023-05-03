<template>
    <div>
    <header-navbar-main></header-navbar-main>
    <!-- 메인 메뉴 -->
    <div class="menu-container">
      <!-- <router-link :to="{ name: '' }"> -->
        <div class="main-menu first">
          <div class="menu-text">내가<br>만드는<br>동화</div>
        </div>
      <!-- </router-link> -->
      <router-link :to="{ name: 'Painting' }">
        <div class="main-menu second">
          <div class="menu-text">색칠공부</div>
        </div>
      </router-link>
      <router-link :to="{ name: 'BookSearchByPicture' }">
        <div class="main-menu third">
          <div class="menu-text">사진으로<br>도서 검색</div>
        </div>
      </router-link>
      <router-link :to="{ name: 'BookSearch' }">
        <div class="main-menu fourth">
          <div class="menu-text">제목으로<br>도서 검색</div>
        </div>
      </router-link>
    </div>
    <!-- /메인 메뉴 -->
    <div class="clear"></div>
    <div class="subject-line"><div class="subject">새로 들어온 책</div><div class="red-bar"></div></div>
    <div class="carousel-container">
      <book-carousel-view :items="bookList" :chunkSize="4"></book-carousel-view>
    </div>
    <!-- TODO : 각기 다음에 필요한 컴포넌트는 components/{domain} 하위에 작성 -->
    <!-- TODO : 새로 들어온 책 콘텐츠 케러셀 컴포넌트-->
    <div class="subject-line"><div class="subject">최신 리뷰</div><div class="red-bar"></div></div>
    <carousel :items="reviewMainList"></carousel>
    <!-- TODO : 우리 아이들 작품(만든 동화) 리스트 컴포넌트 -->
    <div class="subject-line"><div class="subject">우리 아이들 작품</div><div class="red-bar"></div></div>
    <div class="carousel-container">
      <painting-carousel-view :items="mainPaintingList" :chunkSize="4"></painting-carousel-view>
    </div>
    <the-footer></the-footer>
  </div>
</template>

<script>
import HeaderNavbarMain from '@/components/HeaderNavbarMain'
import TheFooter from '@/components/TheFooter'
import Carousel from '@/components/CarouselView'
import BookCarouselView from '@/components/common/list/BookCarouselView.vue'
import PaintingCarouselView from '@/components/common/list/PaintingCarouselView.vue'
import { mapState, mapActions } from 'vuex'

const reviewStore = 'reviewStore'
const bookStore = 'bookStore'
const paintingStore = 'paintingStore'

export default {
  name: 'AppMain',
  components: {
    HeaderNavbarMain,
    TheFooter,
    Carousel,
    BookCarouselView,
    PaintingCarouselView
  },
  methods: {
    ...mapActions(reviewStore, ['getReviewMainListAction']),
    ...mapActions(bookStore, ['getNewBookList']),
    ...mapActions(paintingStore, ['getNewPainting'])
  },
  computed: {
    ...mapState(reviewStore, ['reviewMainList']),
    ...mapState(bookStore, ['bookList']),
    ...mapState(paintingStore, ['mainPaintingList'])
  },
  mounted () {
    this.getReviewMainListAction()
    this.getNewBookList()
    this.getNewPainting()
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

.carousel-container {
  margin: auto;
  height: 500px;
  width: 1000px;
}

.menu-container{
  width: 1000px;
  margin: auto;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 30px;
}

.main-menu{
  width: 230px;
  height: 340px;
  background-color: red;
  margin: 7px;
  border-radius: 50px 10px 50px 10px;
  border: 10px solid white;
  position:relative;
  text-align: left;
}

.main-menu.first {
  background-color: var(--menu-blue);
}

.main-menu.second {
  background-color: var(--menu-red);
}

.main-menu.third {
  background-color: var(--menu-yellow);
}

.main-menu.fourth {
  background-color: var(--menu-green);
}

.menu-text{
  position: absolute;
  color: white;
  font-size: 32px;
  font-weight: 800;
  text-shadow: 0px 2px 4px rgba(0, 0, 0, 0.25);
  margin: 30px;
}

.clear{
  clear:both;
}

.subject{
  text-align: left;
  font-size: 24px;
  font-weight: 800;
  margin-right: 15px;
}

.subject-line{
  width: 1000px;
  margin: auto;
  margin-top: 100px;
  display: flex;
  justify-content: left;
  align-items: center;
}

.red-bar {
  height:2px;
  background-color: var(--ae-red);
  width:141px;
}
</style>
