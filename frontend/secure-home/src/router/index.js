import Vue from "vue";
import VueRouter from "vue-router";
import {Roles} from '../constants.js';
import {getRoleFromToken} from '../utils/token.js'

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
      role: [Roles.ROLE_ADMIN, Roles.ROLE_HOUSE_OWNER, Roles.ROLE_TENANT]
    },
  },
  {
    path: "/real-estates",
    name: "RealEstates",
    component: () => import("@/pages/RealEstatesPage.vue"),
    meta: {
      layout: "AppLayoutMain",
      role: [Roles.ROLE_ADMIN]
    },
  },

  {
    path: "/new-user",
    name: "CreateUsersPage",
    component: () => import("@/pages/CreateUserPage.vue"),
    meta: {
      layout: "AppLayoutMain",
      role: [Roles.ROLE_ADMIN]
    },
  },
  {
    path: "/update-user/:id",
    name: "UpdateUserPage",
    component: () => import("@/pages/UpdateUserPage.vue"),
    meta: {
      layout: "AppLayoutMain",
      role: [Roles.ROLE_ADMIN]
    },
  },
  {
    path: "/users",
    name: "UsersPage",
    component: () => import("@/pages/UsersPage.vue"),
    meta: {
      layout: "AppLayoutMain",
      role: [Roles.ROLE_ADMIN]
    },
  },
  {
    path: "/devices/:id",
    name: "ViewDevicesPage",
    component: () => import("@/pages/ViewDevicesPage.vue"),
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
      role: [Roles.ROLE_ADMIN, Roles.ROLE_HOUSE_OWNER, Roles.ROLE_TENANT]
    },
  },
];

const router = new VueRouter({
  // mode: "history",
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  const { role} = to.meta;
	if(role){
		const userRole = getRoleFromToken();
		if(role.length && !role.includes(userRole)){
			return next({path: 'auth'});
		}

	}
	next();
});

router.afterEach(() => {
  setTimeout(() => {
    $('.selectpicker').selectpicker('refresh');
  }, 100);
});

export default router;
