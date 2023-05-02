import Vue from 'vue'
import Router from 'vue-router'
import AppMain from '@/views/AppMain'
import AppStory from '@/views/AppStory'
import AppPainting from '@/views/AppPainting'
import AppBook from '@/views/AppBook'
import AppMyPage from '@/views/AppMyPage'
import AppUser from '@/views/AppUser'
import AppReview from '@/views/AppReview'

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
      component: AppStory,
      children: [
        {
          path: 'list',
          name: 'StoryListView',
          component: () => import('@/components/story/StoryListView')
        },
        {
          path: 'keyword',
          name: 'StoryKeywordInput',
          component: () => import('@/components/story/StoryKeywordInput')
        }
      ]
    },
    {
      path: '/painting',
      name: 'Painting',
      component: AppPainting,
      redirect: '/painting/list',
      children: [
        {
          path: 'board',
          name: 'paintingboard',
          component: () => import('@/components/painting/PaintingBoardView')
        },
        {
          path: 'list',
          name: 'paintinglist',
          component: () => import('@/components/painting/PaintingListView')
        },
        {
          path: 'generate',
          name: 'generatesketche',
          component: () => import('@/components/painting/GenerateSketcheView')
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
          component: () => import('@/components/myPage/MyInfoReviewListView')
        },
        {
          path: 'info',
          name: 'MyInfo',
          component: () => import('@/components/myPage/MyInfo')
        },
        {
          path: 'modify',
          name: 'MyInfoModify',
          component: () => import('@/components/myPage/MyInfoModify')
        },
        {
          path: 'painting',
          name: 'mypainting',
          component: () => import('@/components/myPage/MyPaintingListView')
        }
      ]
    },
    {
      path: '/user',
      name: 'User',
      component: AppUser,
      children: [
        {
          path: 'login',
          name: 'Login',
          component: () => import('@/components/user/Login')
        },
        {
          path: 'oauth',
          name: 'Oauth',
          component: () => import('@/components/user/Oauth')
        }
      ]

    },
    {
      path: '/review',
      name: 'Review',
      component: AppReview,
      children: [
        {
          path: 'create',
          name: 'ReviewCreate',
          component: () => import('@/components/review/ReviewCreateView')
        },
        {
          path: 'detail/:reviewId',
          name: 'ReviewDetail',
          component: () => import('@/components/review/ReviewDetailView'),
          props: true
        },
        {
          path: 'modify',
          name: 'ReviewModify',
          component: () => import('@/components/review/ReviewModifyView')
        }
      ]
    }
  ]
})
