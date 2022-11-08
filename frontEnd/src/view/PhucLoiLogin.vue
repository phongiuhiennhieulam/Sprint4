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

<style >
.login-page {
  background: url('@/assets/image/vmg-2192.jpg');
  background-repeat: round;
  background-size: auto;
  width: 100%;
  height: 100%;

}

.login {
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 482px;
  height: 582px;
  margin: 0 auto;
  padding: 40px;
  background: #e3edfd;
  border-radius: 12px;
  box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.25);
  position: absolute;
  right: 0;
  left: 0;
  transform: translateY(15%);
}

.login-title {
  font-size: 35px !important;
  font-weight: 400;
  text-align: center;
  margin-bottom: 40px;
}

.login-content {
  margin: 40px 0px;
  text-align: center;
  margin-top: 18px;

}

.hr-detail__button {
  margin-top: 70px !important;
  margin: 100% auto;
  background-color: #f00 !important;
  color: #fff !important;
  font-size: 14px !important;
  font-weight: 600 !important;
}

.hr-detail__button:hover {
  background-color: #f00 !important;
  color: #fff !important;
  font-size: 14px !important;
  font-weight: 600 !important;
}

.hr-detail__button:focus {
  background-color: #f00 !important;
  color: #fff !important;
  font-size: 14px !important;
  font-weight: 600 !important;
}

.hr-detail__button:active {
  background-color: #f00 !important;
  border-color: #f00 !important;
  color: #fff !important;
  font-size: 14px !important;
  font-weight: 600 !important;
}

input {
  outline: none;
  border: none;
  border-bottom: 1px solid black;
  padding-top: 15px !important;
  padding-bottom: 15px !important;
}

.btn {
  padding: 1px 10px 10px 10px;
  background-color: #f00;
  border: none;
  border-radius: 30%;
  cursor: pointer;
}
</style>
