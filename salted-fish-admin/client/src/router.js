import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      redirect: 'login'
    },
    {
      path: '/index',
      name: 'Index',
      component: () => import('./views/Index.vue'),
      meta: { title: '首页' },
      redirect: '/home',
      children: [
        {
          path: '/home',
          name: 'home',
          component: () => import('./views/Home/Home'),
          meta: { title: '首页' }
        },
        {
          path: '/staff',
          name: 'staff',
          component: () => import('./views/Staff'),
          meta: { title: '后台用户信息列表' }
        },
        {
          path: '/userList',
          name: 'userList',
          component: () => import('./views/userList'),
          meta: { title: 'C端用户管理' }
        },
        {
          path: '/fundList',
          name: 'fundList',
          component: () => import('./views/fundmanagement/FundList'),
          meta: { title: '资金流水' }
        },
        {
          path: '/payList',
          name: 'payList',
          component: () => import('./views/fundmanagement/PayList'),
          meta: { title: '支付单据' }
        },
        {
          path: '/Infoshow',
          name: 'Infoshow',
          component: () => import('./views/information/InfoShow'),
          meta: { title: '个人信息' }
        },
        {
          path: '/editor',
          name: 'editor',
          component: () => import('./views/information/Editor'),
          meta: { title: '富文本编辑器' }
        },
        {
          path: '/markdown',
          name: 'markdown',
          component: () => import('./views/information/Markdown'),
          meta: { title: 'Markdown编辑器' }
        },
        {
          path: '/showFundArticle',
          name: 'showFundArticle',
          component: () =>
            import('./views/information/article/ShowFundArticle'),
          meta: { title: '文章列表' }
        },
        {
          path: '/chinaTouziList',
          name: 'chinaTouziList',
          component: () => import('./views/Investment/ChinaTouziList'),
          meta: { title: '省份投资' }
        },
        {
          path: '/chinaTabsList',
          name: 'ChinaTabsList',
          component: () => import('./views/Investment/ChinaTabsList'),
          meta: { title: '区域投资' }
        },
        {
          path: '/fundPosition',
          name: 'fundPosition',
          component: () => import('./views/capitalData/FundPosition'),
          meta: { title: '投资分布' }
        },
        {
          path: '/maplist',
          name: 'maplist',
          component: () => import('./views/MapList'),
          meta: { title: '地图展示' }
        },
        {
          path: '/categoryList',
          name: 'categoryList',
          component: () => import('./views/flower/categoryList'),
          meta: { title: '分类管理' }
        },
        {
          path: '/flowerList',
          name: 'flowerList',
          component: () => import('./views/flower/flowerList'),
          meta: { title: '商品审核列表' }
        },
        {
          path: '/theAgency',
          name: 'theAgency',
          component: () => import('./views/flower/theAgency'),
          meta: { title: '代理中心' }
        },
        {
          path: '/orderList',
          name: 'orderList',
          component: () => import('./views/fundmanagement/orderList'),
          meta: { title: '订单管理' }
        },
      ]
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('./views/register/Register')
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('./views/logo/Login')
    },
    {
      path: '/lock',
      name: 'lock',
      component: () => import('./views/Lock.vue')
    },
    {
      path: '*',
      name: 'Nofind',
      component: () => import('./views/404')
    }
  ]
  // mode: "history"
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const isLogin = !!localStorage.eleToken

  if (to.path === '/login' || to.path === '/register') {
    next()
  } else {
    isLogin ? next() : next('/login')
  }
})

export default router
