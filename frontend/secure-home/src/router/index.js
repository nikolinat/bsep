import Vue from "vue";
import VueRouter from "vue-router";
import { Roles } from "../constants.js";
import { getRoleFromToken } from "../utils/token.js";

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
      role: [Roles.ROLE_ADMIN, Roles.ROLE_HOUSE_OWNER, Roles.ROLE_TENANT],
    },
  },
  {
    path: "/real-estates",
    name: "RealEstates",
    component: () => import("@/pages/RealEstatesPage.vue"),
    meta: {
      layout: "AppLayoutMain",
      role: [Roles.ROLE_ADMIN],
    },
  },
  {
    path: "/owner-real-estates",
    name: "RealEstatesOwnerPage",
    component: () => import("@/pages/RealEstatesOwnerPage.vue"),
    meta: {
      layout: "AppLayoutMain",
      role: [Roles.ROLE_HOUSE_OWNER, Roles.ROLE_TENANT]
    },
  },
  {
    path: "/tenant-real-estates",
    name: "RealEstatesTenantPage",
    component: () => import("@/pages/RealEstatesTenantPage.vue"),
    meta: {
      layout: "AppLayoutMain",
      role: [Roles.ROLE_HOUSE_OWNER, Roles.ROLE_TENANT]
    },
  },
  {
    path: "/new-user",
    name: "CreateUsersPage",
    component: () => import("@/pages/CreateUserPage.vue"),
    meta: {
      layout: "AppLayoutMain",
      role: [Roles.ROLE_ADMIN],
    },
  },
  {
    path: "/update-user/:id",
    name: "UpdateUserPage",
    component: () => import("@/pages/UpdateUserPage.vue"),
    meta: {
      layout: "AppLayoutMain",
      role: [Roles.ROLE_ADMIN],
    },
  },
  {
    path: "/users",
    name: "UsersPage",
    component: () => import("@/pages/UsersPage.vue"),
    meta: {
      layout: "AppLayoutMain",
      role: [Roles.ROLE_ADMIN],
    },
  },
  {
    path: "/devices/:id",
    name: "ViewDevicesPage",
    component: () => import("@/pages/ViewDevicesPage.vue"),
    meta: {
      layout: "AppLayoutMain",
      role: [Roles.ROLE_ADMIN, Roles.ROLE_HOUSE_OWNER, Roles.ROLE_TENANT],
    },
  },
  {
    path: "/reports/:id",
    name: "ViewReportsPage",
    component: () => import("@/pages/ViewReportsPage.vue"),
    meta: {
      layout: "AppLayoutMain",
      role: [Roles.ROLE_ADMIN, Roles.ROLE_HOUSE_OWNER],
    },
  },
  {
    path: "/alarms/:id",
    name: "ViewAlarmsPage",
    component: () => import("@/pages/ViewAlarmsPage.vue"),
    meta: {
      layout: "AppLayoutMain",
      role: [Roles.ROLE_ADMIN, Roles.ROLE_HOUSE_OWNER, Roles.ROLE_TENANT]
    },
  },
  {
    path: "/rule",
    name: "RulePage",
    component: () => import("@/pages/RulePage.vue"),
    meta: {
      layout: "AppLayoutMain",
      role: [Roles.ROLE_ADMIN, Roles.ROLE_HOUSE_OWNER]
    },
  },
  {
    path: "/log-rule",
    name: "LogRulePage",
    component: () => import("@/pages/LogRulePage.vue"),
    meta: {
      layout: "AppLayoutMain",
      role: [Roles.ROLE_ADMIN]
    },
  },
  {
    path: "*",
    name: "catchAll",
    component: () => import("@/pages/HomePage.vue"),
    meta: {
      layout: "AppLayoutMain",
      role: [Roles.ROLE_ADMIN, Roles.ROLE_HOUSE_OWNER, Roles.ROLE_TENANT],
    },
  },
];

const router = new VueRouter({
  // mode: "history",
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  const { role } = to.meta;
  if (role) {
    const userRole = getRoleFromToken();
    if (role.length && !role.some(r=> userRole.indexOf(r) >= 0)) {
      return next({ path: "auth" });
    }
  }
  next();
});

router.afterEach(() => {
  setTimeout(() => {
    $(".selectpicker").selectpicker("refresh");
  }, 100);
});

export default router;
