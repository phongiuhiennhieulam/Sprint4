import TheHeader from "./layout/TheHeader.vue";
import TheContent from "./layout/TheContent.vue";
export default {
  install(Vue) {
    Vue.component("TheHeader", TheHeader);
    Vue.component("TheContent", TheContent);
  },
};
