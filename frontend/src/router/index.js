import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import PaintingBoardView from '../views/PaintingBoardView.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/painting',
      name: 'PaintingBoard',
      component: PaintingBoardView
    }
  ]
})
