import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main-page',
      component: () => import('../views/MainPageView.vue'),
    },
    {
      path: '/manage/employee',
      name: 'manage-employee',
      component: () => import('../views/ManageEmployeeView.vue'),
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue'),
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('../views/RegisterView.vue'),
    },
    {
      path: '/manage/games',
      name : 'manage-games',
      component: () => import('../views/ManageGamesView.vue'),
    },
    {
      path: '/manage/categories',
      name : 'manage-categories',
      component: () => import('../views/ManageCategoriesView.vue'),
    },
    {
      path: '/home',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue'),
    },

  ],
})

export default router
