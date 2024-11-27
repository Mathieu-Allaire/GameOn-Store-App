import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main-page",
      component: () => import("../views/MainPageView.vue"),
    },
    {
      path: "/login",
      name: "login",
      component: () => import("../views/LoginView.vue"),
    },
    {
      path: "/manage/employee",
      name: "manage-employee",
      component: () => import("../views/ManageEmployeeView.vue"),
    },
    {
      path: "/register",
      name: "register",
      component: () => import("../views/RegisterView.vue"),
    },
    {
      path: "/manage/games",
      name: "manage-games",
      component: () => import("../views/ManageGamesView.vue"),
    },
    {
      path: "/manage/categories",
      name: "manage-categories",
      component: () => import("../views/ManageCategoriesView.vue"),
    },
    {
      path: "/manage/requests",
      name: "manage-requests",
      component: () => import("../views/ManageGameRequestView.vue"),
    },
    {
      path:'/orders',
      name : 'orders',
      component: () => import('../views/CustomerOrderView.vue'),
    },
    {
      path: '/cart',
      name: 'cart',
      component: () => import('../views/CartView.vue'),
    },
    {
      path: '/customers',
      name: 'customers',
      component: () => import('../views/CustomersView.vue')
    },
    {
      path:'/orders',
      name : 'orders',
      component: () => import('../views/CustomerOrderView.vue'),
    },
    {
      path: '/cart',
      name: 'cart',
      component: () => import('../views/CartView.vue'),
    },
    {
      path: '/customers',
      name: 'customers',
      component: () => import('../views/CustomersView.vue')
    },
    {
      path: '/home',
      name: 'home',
      component: HomeView,
    },
    {
      path: "/home/:search",
      name: "home-search",
      component: HomeView,
    },
    {
      path: "/about",
      name: "about",
      component: () => import("../views/AboutView.vue"),
    },
    {
      path: "/debug",
      name: "debug",
      component: () => import("../views/debug.vue"),
    },
    {
      path: "/home/:gameNameNoSpace",
      name: "Game Information",
      component: () => import("../views/GameView.vue"),
    },
  ],
});

export default router;
