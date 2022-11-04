import Vue from "vue";
import VueRouter from "vue-router";
import store from '../store'

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    alias: '/login',
    name: "Login",
    component: () => import("../view/PhucLoiLogin.vue"),
  },
  {
    path: "/phucloi",
    name: "PhucLoiList",
    component: () => import("@/view/PhucLoiList.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/hr",
    name: "HrList",
    component: () => import("@/view/HrList.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/register_welfare",
    name: "RegisterWelfare",
    component: () => import("@/view/RegisterWelfare.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/check",
    name: "MyCheckBox",
    component: () => import("@/view/MyCheckBox.vue"),
  },
  {
    path: "/home_welfare",
    name: "HomeWelfare",
    component: () => import("@/view/PhucLoiHome.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/xetduyet",
    name: "xetduyet",
    component: () => import("../view/HrAccpetWelfare.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/welcome_page",
    name: "WelcomePage",
    component: () => import("../view/WelcomePage.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: '/logout',
    name: 'logout',
    beforeEnter(to, from, next) {
      store.dispatch('logout').then(() => {
        if (this.$store.state.auth.status.loggedIn) {
          return next('/')
        }
        location.reload()
      }).catch(reason => {
        console.log(reason)
      })
    }
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});
router.beforeEach(async (to, from, next) => {
  const isLogged = store.state.auth.status.loggedIn;
  // next-line: check if route ("to" object) needs authenticated
  if (to.matched.some((record) => record.meta.requiresAuth) && !isLogged) {
    next('/login');
  } else next();
});

export default router;
