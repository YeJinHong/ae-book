import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import ColoringBoardView from '../views/ColoringBoardView.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/coloring',
      name: 'ColoringBoard',
      component: ColoringBoardView
    }
  ]
})
