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
    path: "/valid-certificates",
    name: "ValidCertificates",
    component: () => import("@/pages/ValidCertificatesPage.vue"),
    meta: {
      layout: "AppLayoutMain",
    },
  },
  {
    path: "/revoked-certificates",
    name: "RevokedCertificates",
    component: () => import("@/pages/RevokedCertificatesPage.vue"),
    meta: {
      layout: "AppLayoutMain",
    },
  },
  {
    path: "/csr",
    name: "CreateCSR",
    component: () => import("@/pages/CreateCSRPage.vue"),
    meta: {
      layout: "AppLayoutMain",
    },
  },
  {
    path: "/create-certificate/:id",
    name: "CreateCertificate",
    component: () => import("@/pages/CreateCertificatePage.vue"),
    meta: {
      layout: "AppLayoutMain",
    },
  },
  {
    path: "/csr-preview",
    name: "CSRPage",
    component: () => import("@/pages/CSRPage.vue"),
    meta: {
      layout: "AppLayoutMain",
    },
  },
  {
    path: "/email-verification/:token",
    name: "EmailVerificationPage",
    component: () => import("@/pages/EmailVerificationPage.vue"),
    meta: {
      layout: "AuthLayout",
    },
  },
];

const router = new VueRouter({
  mode: "history",
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
