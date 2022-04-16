import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/auth',
    name: 'Auth',
    component: () => import('@/pages/LoginPage.vue'),
    meta: {
      layout: 'AuthLayout'
    }
  },
  {
    path: '/',
    name: 'Home',
    component: () => import('@/pages/HomePage.vue'),
    meta: {
      layout: 'AppLayoutMain'
    }
  },
  {
    path: '/csr',
    name: 'CreateCSR',
    component: () => import('@/pages/CreateCSRPage.vue'),
    meta: {
      layout: 'AppLayoutMain'
    }
  },
    {
    path: '/create-certificate',
    name: 'CreateCertificate',
    component: () => import('@/pages/CreateCertificatePage.vue'),
    meta: {
      layout: 'AppLayoutMain'
    }
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})


export default router
