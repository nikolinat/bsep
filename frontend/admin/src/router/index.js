import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

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
    path: "/valid-certificates",
    name: "ValidCertificates",
    component: () => import("@/pages/ValidCertificatesPage.vue"),
    meta: {
      layout: "AppLayoutMain",
    },
  },
  {
    path: "/tmp",
    name: "Tmp",
    component: () => import("@/pages/tmp.vue"),
    meta: {
      layout: "AppLayoutDefault",
    },
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
