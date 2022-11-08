import Vue from "vue";
import App from "./App.vue";
import router from './router'
import store from './store'
import components from "./components";
import "@/assets/css/main.css";
import ElementUI from "element-ui";
import locale from 'element-ui/lib/locale/lang/en'
import "element-ui/lib/theme-chalk/index.css";
import VueSessionStorage from 'vue-sessionstorage'
Vue.config.productionTip = false;

Vue.use(components); 
Vue.use(VueSessionStorage)



Vue.use(ElementUI, { locale });

new Vue({
  store,
  router,
  render: (h) => h(App)
}).$mount("#app");
