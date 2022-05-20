import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const $ = window.$;

const routes = [
  {
    path: "/auth",
    name: "Auth",
    component: () => import("@/pages/LoginPage.vue"),
    meta: {
      layout: "AuthLayout",
    },
  },
  {
    path: "/",
    name: "Home",
    component: () => import("@/pages/HomePage.vue"),
    meta: {
      layout: "AppLayoutMain",
    },
  },
  {
    path: "*",
    name: "catchAll",
    component: () => import("@/pages/HomePage.vue"),
    meta: {
      layout: "AppLayoutMain",
    },
  },
];

const router = new VueRouter({
  // mode: "history",
  base: process.env.BASE_URL,
  routes,
});

router.afterEach((to, from) => {
  console.log(to + from);
  setTimeout(() => {
    $(".selectpicker").selectpicker("refresh");
  }, 100);
});

export default router;
