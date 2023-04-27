import Vue from 'vue'
import Router from 'vue-router'
import AppMain from '@/views/AppMain'
import AppStory from '@/views/AppStory'
import AppPainting from '@/views/AppPainting'
import AppBook from '@/views/AppBook'
import AppMyPage from '@/views/AppMyPage'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Main',
      component: AppMain
    },
    {
      path: '/story',
      name: 'Story',
      component: AppStory
    },
    {
      path: '/painting',
      name: 'Painting',
      component: AppPainting,
      children: [
        {
          path: 'board',
          name: 'paintingboard',
          component: () => import('@/components/painting/PaintingBoardView')
        }
      ]
    },
    {
      path: '/book',
      name: 'Book',
      component: AppBook,
      children: [
        {
          path: 'search',
          name: 'BookSearch',
          component: () => import('@/components/book/BookSearchView')
        },
        {
          path: 'searchbypicture',
          name: 'BookSearchByPicture',
          component: () => import('@/components/book/BookSearchByPictureView')
        },
        {
          path: 'detail/:isbn',
          name: 'BookDetail',
          component: () => import('@/components/book/BookDetailView'),
          props: true
        }
      ]
    },
    {
      path: '/mypage',
      name: 'MyPage',
      component: AppMyPage,
      children: [
        {
          path: 'review',
          name: 'MyReview',
          component: () => import('@/components/myPage/MyReviewListView')
        }
      ]
    }
  ]
})
