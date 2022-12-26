<template>
  <div class="login-page">
    <div class="login">
      <div class="login-title">
        <h1>Quản lý phúc lợi</h1>
      </div>
      <div class="login-content">
        <form name="form" @submit.prevent="handleLogin">
          <div class="form-group">
            <input v-model="user.userName" type="email" class="form-control" name="username" placeholder="Nhập email"
              required />
          </div>
          <br />
          <div class="form-group">
            <input v-model="user.passWord" type="password" class="form-control" name="password"
              placeholder="Nhập password" required />
          </div>
          <br />
          <div class="form-group" id="bt">
            <button class="btn btn-danger btn-block" :disabled="loading">
              <span v-show="loading" class="spinner-border spinner-border-sm"></span>
              <span>Đăng nhập</span>
            </button>
          </div>
          <!-- <div class="form-group">
          <div v-if="message" class="alert alert-danger" role="alert">{{message}}</div>
        </div> -->
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import User from "../models/user.js";
export default {
  name: "PhucLoiLogin",
  data() {
    return {
      user: new User("", ""),
      loading: false,
      login: {
        username: "",
        password: "",
      },
    };
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  created() {
    if (this.loggedIn) {
      this.$router.push("/welcome_page");
    }
  },
  methods: {
    onReset() {
      this.$refs.login.resetFields();
    },
    handleLogin() {
      this.$store.dispatch("auth/login", this.user).then(
        () => {
          this.$notify({
            title: "Success",
            message: "Đăng nhập thành công!",
            type: "success",
          });
          this.$router.push("/welcome_page");
        },
        (error) => {
          this.loading = false;
          this.message =
            (error.response && error.response.data) ||
            error.message ||
            error.toString();
          this.$notify.error({
            title: "Error",
            message: "Sai thông tin đăng nhập!",
          });
        }
      );
    },

  },
};
</script>

<style scoped>
@import "@/assets/css/phucloi/login.css";
</style>
