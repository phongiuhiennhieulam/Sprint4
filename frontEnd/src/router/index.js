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
    meta: { requiresAuth: true }
  },
  {
    path: "/phucloi",
    name: "PhucLoiList",
    component: () => import("@/view/PhucLoiList.vue"),
    meta: { requiresAuth: true }
  },
  {
    path:'/auth-redirect', redirect: '/welcome_page'
  },
  {
    path: "/erorr-404",
    name: "Erorrpage",
    component: () => import("@/view/404-error.vue"),
  },
  {
    path:'*', redirect: '/erorr-404'
  },
  {
    path: "/accept_money",
    name: "AcceptMoney",
    component: () => import("@/view/AcceptMoney.vue"),
    meta: { requiresAuth: true }
  },

  
  {
    path: "/moneyupdate",
    name: "MoneyupdateList",
    component: () => import("@/view/MoneyUpdateList.vue"),
  },
  {
    path: "/newstaffs",
    name: "NewStaffsList",
    component: () => import("@/view/NewStaffsList.vue"),
  },
  

  {
    path: "/welfare-update",
    name: "WelfareUpdate",
    component: () => import("@/view/WelfareUpdate.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/thongke",
    name: "thongke",
    component: () => import("@/view/StatisticalAll.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/thongke2",
    name: "thongke2",
    component: () => import("../view/StatisticalCost.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/hr",
    name: "hr",
    component: () => import("../view/HrList.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/newStaff",
    name: "NewStaffs",
    component: () => import("../view/StaffComponent.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/user",
    name: "UserList",
    component: () => import("../view/User.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/department",
    name: "DepartmentList",
    component: () => import("../view/Department.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/statistical",
    name: "Thống kê",
    component: () => import("@/view/StatisticPage.vue"),
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
