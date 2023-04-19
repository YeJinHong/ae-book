import Vue from 'vue'
import Router from 'vue-router'
import AppMain from '@/views/AppMain'
import AppStory from '@/views/AppStory'
import AppPainting from '@/views/AppPainting'
import AppSearchBook from '@/views/AppSearchBook'
import AppSearchBookByPicture from '@/views/AppSearchBookByPicture'

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
      component: AppPainting
    },
    {
      path: '/searchbook',
      name: 'SearchBook',
      component: AppSearchBook
    },
    {
      path: '/searchbookbypicture',
      name: 'SearchBookByPicture',
      component: AppSearchBookByPicture
    }
  ]
})
