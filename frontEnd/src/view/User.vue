<template>
  <div class="us-main">
    <div class="us-content">
      <div class="us-title"><strong>QUẢN LÍ TÀI KHOẢN</strong></div>
      <br />

      <div class="us-ele">
        <div class="us-table">
          <div class="us-table__content">
            <tr width="100%">
              <th>
                <div>
                  <el-form :inline="true" class="demo-form-inline">
                    <el-form-item>
                      <el-input
                        v-model="keyWord"
                        placeholder="Nhập tên hoặc nhập email"
                        style="width: 500px"
                      >
                        <i
                          slot="prefix"
                          class="el-input__icon el-icon-search"
                        ></i
                      ></el-input>
                    </el-form-item>
                    <el-form-item>
                      <el-button type="warning" @click="onFind()"
                        >Tìm kiếm</el-button
                      >
                    </el-form-item>
                  </el-form>
                </div>
              </th>
            </tr>

            <div class="us-table_content">
              <table>
                <thead>
                  <tr class="us-table_row">
                    <th width="20px">STT</th>
                    <th width="400px">Họ và tên</th>
                    <th width="440px">Email</th>
                    <th width="150px">Quyền Lãnh Đạo</th>
                    <th width="150px">Quyền Admin</th>
                    <th width="150px">Quyền Nhân Sự</th>
                    <th width="150px">Phân Quyền</th>
                    <th width="150px">Đổi mật khẩu</th>
                  </tr>
                </thead>

                <tbody>
                  <tr v-for="(item, index) in listUser.content" :key="index">
                    <td style="width: 50px">{{ index + 1 }}</td>
                    <td style="text-align: left">{{ item.name }}</td>
                    <td style="text-align: left">{{ item.userName }}</td>

                    <td>
                      <template>
                        <div v-for="(roles, index) in item.roles" :key="index">
                          <el-checkbox v-if="roles.id === 2" checked disabled />
                        </div>
                      </template>
                    </td>

                    <td>
                      <template>
                        <div v-for="(roles, index) in item.roles" :key="index">
                          <div v-if="roles.id === 3">
                            <el-checkbox checked disabled />
                          </div>
                        </div>
                      </template>
                    </td>

                    <td>
                      <template>
                        <div v-for="(roles, index) in item.roles" :key="index">
                          <el-checkbox v-if="roles.id === 4" checked disabled />
                        </div>
                      </template>
                    </td>

                    <td v-if="currentUser.userName == item.userName">
                      <span class="icon-user">
                        <i class="fa fa-user" style="color: gray"></i>
                      </span>
                    </td>

                    <td v-if="currentUser.userName != item.userName">
                      <span
                        class="icon-user"
                        @click="showEditRole(item, index)"
                      >
                        <i class="fa fa-user"></i>
                      </span>
                    </td>

                    <span class="icon-edit">
                      <span
                        class="icon-role"
                        @click="showEditForm(item, index)"
                      >
                        <i class="fa fa-key"></i>
                      </span>
                    </span>
                  </tr>
                </tbody>
              </table>
            </div>
            <!-- phân trang -->
            <el-pagination
              style="text-align: right"
              background
              layout="prev, pager, next"
              :page-count="count"
              :page-size="pageSize"
              :page-sizes="pageSizes"
              @current-change="handlePageChange"
            >
            </el-pagination>
          </div>
        </div>
      </div>
    </div>

    <!-- Cập nhật thông tin tài khoản -->
    <el-dialog
      title="Cập nhật thông tin tài khoản"
      :visible.sync="isShowEdit"
      width="500px"
      label-width="100px"
      top="5vh"
    >
      <ValidationObserver ref="observer" v-slot="{ handleSubmit }">
        <el-form
          :model="edit"
          ref="edit"
          label-width="120px"
          label-position="top"
        >
          <el-row>
            <el-col>
              <el-form-item prop="name">
                <strong>Email:</strong>
                <el-input
                  type="text"
                  v-model="edit.userName"
                  :disabled="true"
                ></el-input>
              </el-form-item>
            </el-col>

            <el-col>
              <ValidationProvider
                v-slot="{ errors }"
                name="userName"
                :rules="{
                  required: true,
                  regex: /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,20}$/,
                }"
              >
                <el-form-item
                  prop="password"
                  :error="messageError(loginFields.password, errors[0])"
                >
                  <strong>Mật khẩu mới:</strong>
                  <el-input
                    type="password"
                    @input="checkpass"
                    v-model="newPassword"
                    v-validate="'required'"
                    placeholder="Mật khẩu mới"
                  ></el-input>
                </el-form-item>
              </ValidationProvider>
            </el-col>

            <el-col>
              <ValidationProvider
                v-slot="{ errors }"
                name="userName"
                :rules="{
                  required: true,
                  regex: /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,20}$/,
                }"
              >
                <el-form-item
                  prop="password"
                  :error="messageError(loginFields.password, errors[0])"
                >
                  <strong>Xác nhận lại mật khẩu:</strong>
                  <el-input
                    type="password"
                    @input="checkpass"
                    v-model="rePassword"
                    placeholder="Xác nhận mật khẩu"
                  ></el-input>
                </el-form-item>
              </ValidationProvider>
            </el-col>
          </el-row>
        </el-form>

        <span slot="footer" class="dialog-footer" style="text-align: center">
          <el-button
            class="us-detail__button"
            round
            @click="handleSubmit(submit)"
            ><strong>Cập nhật</strong>
          </el-button>
        </span>
      </ValidationObserver>
    </el-dialog>

    <!-- Cập nhật phân quyền -->
    <el-dialog
      title="Cập nhật phân quyền"
      :visible.sync="isShowEditRole"
      width="500px"
      label-width="100px"
      top="5vh"
    >
      <el-form
        :model="editRole"
        ref="edit"
        label-width="120px"
        label-position="top"
      >
        <el-row>
          <el-col>
            <el-form-item style="text-align: left" prop="name">
              <strong>Email:</strong>
              <el-input
                type="text"
                v-model="edit.userName"
                :disabled="true"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col>
            <el-form-item>
              <div>
                <el-checkbox
                  v-model="editRole.editRoleModer"
                  label="Quyền Lãnh Đạo"
                  size="large"
                />
              </div>
            </el-form-item>
            <el-form-item>
              <div>
                <el-checkbox
                  v-model="editRole.editRoleAdmin"
                  label="Quyền Admin"
                  size="large"
                />
              </div>
            </el-form-item>
            <el-form-item>
              <div>
                <el-checkbox
                  v-model="editRole.editRolePersonnel"
                  label="Quyền Nhân Sự"
                  size="large"
                />
              </div>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button
          class="us-detail__button"
          round
          @click="editURole(edit.id, editRole)"
          ><strong>Cập nhật</strong>
        </el-button>
      </span>
    </el-dialog>
  </div>
</template>
    
    <script>
/* eslint-disable */
import { ValidationObserver, ValidationProvider } from "vee-validate";
import BaseValidate from "@/utils/BaseValidate";
import UserService from "@/service/userService";
let userService = new UserService();
export default {
  name: "UserList",
  components: { ValidationObserver, ValidationProvider },
  mixins: [BaseValidate],
  data() {
    return {
      listUser: [],
      roles: [],
      name: "",
      userName: "",
      status: "",
      code: "",
      keyWord: "",
      newPassword: null,
      rePassword: null,
      content: "",
      staff: "",
      userId: 0,
      pageSize: 10,
      count: 0,
      pageSizes: [2, 4, 6],
      isDuplicate: false,
      centerDialogVisible: false,
      user: {},
      filter: {
        name: null,
      },

      listUser: {
        name: "",
        userName: "",
        roles: "",
      },

      edit: {
        id: "",
        newpassword: "",
        rePassword: "",
      },
      isShowEdit: false,
      isShowEditRole: false,
      editRole: {
        editRolePersonnel: false,
        editRoleAdmin: false,
        editRoleModer: false,
      },
    };
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
  },
  watch: {
    selected() {},
  },

  methods: {
    submit() {
      this.editUser(this.edit.id, this.edit);
    },
    showEditForm(item, index) {
      this.edit = item;
      this.isShowEdit = true;
    },
    showDeleteDialog() {
      this.centerDialogVisible = true;
    },
    showEditRole(item, index) {
      this.editRole.editRoleAdmin = false;
      this.editRole.editRoleModer = false;
      this.editRole.editRolePersonnel = false;
      this.editRole.editRoleUser = false;
      // console.log(item)
      for (let i = 0; i < item.roles.length; i++) {
        if (item.roles[i].id == 4) {
          this.editRole.editRolePersonnel = true;
        } else if (item.roles[i].id == 2) {
          this.editRole.editRoleModer = true;
        } else if (item.roles[i].id == 3) {
          this.editRole.editRoleAdmin = true;
        } else if (item.roles[i].id == 1) {
          this.editIdRoleUser = true;
        }
      }
      this.edit = item;
      this.isShowEditRole = true;
    },

    getRequestParams(page, pageSize, keyWord) {
      let params = {};
      if (page) {
        params["page"] = page - 1;
      }
      if (pageSize) {
        params["size"] = pageSize;
      }
      if (keyWord) {
        params["keyWord"] = keyWord;
      }
      return params;
    },

    handlePageChange(value) {
      this.page = value;
      //console.log(this.page);
      this.getAllUser();
    },

    

    getAllUser() {
      const params = this.getRequestParams(
        this.page,
        this.pageSize,
        this.keyWord
      );
      console.log(params);
      new UserService().getAllUser(params).then((response) => {
        this.listUser = response.data;
        console.log(response);
        this.count = response.data.totalPages;
        this.itemCount = response.data.totalElements;
      });
    },

    checkpass() {
      if (this.newPassword === this.rePassword) {
        this.isDuplicate = true;
      } else {
        this.isDuplicate = false;
      }
    },

    editUser(editId, editObject) {
      let isValidate = true;
      if (!editObject.passWord && !editObject.rePassWord) {
        this.$alert(
          "Thông tin đầu vào không hợp lệ vui lòng nhập đủ thông tin!",
          "Thông tin không hợp lệ",
          {
            confirmButtonText: "OK",
            callback: () => {},
          }
        );
        isValidate = false;
      }
      if (this.isDuplicate) {
        editObject.passWord = this.newPassword;
        new UserService().updateUser(editId, editObject).then((res) => {
          //console.log(res);

          this.getAllUser();
          this.$notify({
            title: "Thành công",
            message: "Đổi mật khẩu thành công",
            type: "success",
          });
        });

        this.isShowEdit = false;
      } else {
        this.$notify({
          title: " Thất bại",
          message: "Mật khẩu không khớp, đổi mật khẩu thất bại",
          type: "error",
        });
      }
    },

    editURole(editIdRoleUser, role) {
      console.log(editIdRoleUser);
      console.log(role);
      let list = [];
      if (this.editRole.editRolePersonnel) {
        let object1 = {
          id: 4,
          name: "ROLE_PERSONNEL",
        };
        list.push(object1);
      }
      if (this.editRole.editRoleAdmin) {
        let object3 = {
          id: 3,
          name: "ROLE_ADMIN",
        };
        list.push(object3);
      }
      if (this.editRole.editRoleModer == true) {
        let object2 = {
          id: 2,
          name: "ROLE_MODERATOR",
        };
        list.push(object2);
      }
      let object5 = {
        id: 1,
        name: "ROLE_USER",
      };
      list.push(object5);

      this.$confirm(
        "Bạn chắc chắn chắn muốn phân quyền cho tài khoản này?",
        "Warning",
        {
          confirmButtonText: "OK",
          cancelButtonText: "Cancel",
          type: "warning",
        }
      ).then(() => {
        new UserService().getRole(editIdRoleUser, list).then((response) => {
          this.roles = response.data;
          this.$notify({
            type: "success",
            message: "Phân quyền thành công",
            title: "success",
          });
          this.getAllUser();
          const myTimeout = setTimeout(myGreeting, 100);
          // const myTimeout = setTimeout(myGreeting, 100);

          // function myGreeting() {
          //   window.location.reload();
          // }
        });
        this.isShowEditRole = false;
      });
    },

    deleteUser(id) {
      this.$confirm(
        "Bạn có chắc sẽ khóa tài khoản của nhân viên này không. Tiếp tục?",
        "Warning",
        {
          confirmButtonText: "OK",
          cancelButtonText: "Cancel",
          type: "warning",
        }
      )
        .then(() => {
          userService.deleteUser(id);
          this.$message({
            type: "success",
            message: "Khóa thành công",
          });
          this.$router.go();
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "Đã hủy bỏ",
          });
        });
    },

    unlockUser(id) {
      userService.unlockUser(id);
      this.$notify({
        title: "Success",
        message: "Mở khóa thành công!",
        type: "success",
      });
      this.$router.go();
    },
    onFind() {
      this.getAllUser();
    },
    // if(isValidate){
    //   const qs = require("qs");
    //   if (this.value === 0)
    //   UserService.updateUser(item.passWord, qs.stringify(item));
    //   else UserService.updateUser(item.passWord, qs.stringify(item));
    //   this.isShowEdit = false;
    //   this.$notify({
    //           title: "Success",
    //           message: "Cập nhật thông tin tài khoản thành công",
    //           type: "success",
    //         });
    // }
  },

  created() {
    this.getAllUser();
  },
};
</script>
      
      <style>
.us-title {
  text-align: center;
  font-size: 34px;
  font-weight: 600;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen,
    Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
  background: rgba(255, 255, 255, 0.13);
  padding: 6px 0px;
}
.us-body {
  background: linear-gradient(90deg, #e4c9ac 0%, rgba(255, 255, 255, 0) 100%),
    #e3c1d3;
  width: 100%;
  height: 100%;
}
.us-table {
  text-align: center;
  margin-left: 35px;
  margin-right: 35px;
  z-index: 2;
}
.us-table__content {
  text-align: center;
  padding: 0 50px;
  height: 100%;
  margin: 20px 0px;
}
.us-table__content table {
  width: 100%;
  border-collapse: collapse;
}
.us-table__content table tr {
  background: #f2e7ddf8;
}
.us-table__content table tr th {
  border-right: 1px solid #e4c9ac;
  padding: 14px;
}
.us-table__content table tr td {
  padding: 20px;
  line-height: 30px;
  height: 30px;
}
.us-table__content table thead tr {
  background-color: #fdf9f8;
}
.us-table__content table thead th {
  font-size: 14px;
  font-weight: 600;
}
.us-table__content table tbody tr {
  border-bottom: 1px solid #e4c9ac;
}

.us-table__content table tbody tr:hover {
  background-color: rgb(255, 255, 255);
}
.icon-delete {
  font-size: 20px;
  color: coral;
  cursor: pointer;
}
.icon-edit {
  font-size: 20px;
  color: greenyellow;
  cursor: pointer;
}
/* input, */
textarea {
  outline: none;
  padding: 20px;
  width: 60%;
  background: transparent;
  border: none;
  border-bottom: 1px solid #212529;
}

.us-button {
  margin-left: 200px;
  margin-top: 30px;
}
.us-button__detail {
  color: white !important;
  font-size: 14px !important;
  font-weight: 600 !important;
}
.us-button__detail:hover {
  background-color: rgba(255, 0, 0, 0.1) !important;
  border-color: rgba(255, 0, 0, 0.1) !important;
}
.us-button__detail:focus {
  background-color: rgba(255, 0, 0, 0.2) !important;
  border-color: rgba(255, 0, 0, 0.2) !important;
}
.us-button__detail:active {
  background-color: rgba(255, 0, 0, 0.3) !important;
  border-color: rgba(255, 0, 0, 0.3) !important;
}
.el-form-item__label {
  color: rgb(0, 0, 0) !important;
  font-size: 18px;
  font-weight: 600;
  line-height: 31px;
  letter-spacing: 0em;
  text-align: left;
  line-height: 0px;
  margin-bottom: -10px;
}

.el-form-item__content input {
  border-radius: 8px !important;
  box-shadow: 1px 1px 4px rgba(255, 0, 0, 0.15);
  border: 1px solid #23282b8f;
}

.el-dialog__title {
  margin-left: 65px;
  font-size: 25px;
  font-weight: 700;
  line-height: 35px;
  letter-spacing: 0em;
  text-align: left;
  margin-left: 56px;
  font-size: 25px;
  font-weight: 700;
  line-height: 35px;
  letter-spacing: 0em;
  color: rgb(255, 0, 0);
  text-align: left;
}

.el-dialog__headerbtn i {
  font-size: 24px;
  font-weight: 700;
  color: #f00 !important;
}
.fa-key:before {
  color: red;
}
.fa-unlock:before {
  color: red;
}
.fa-lock:before {
  color: #e6a23c;
}
.el-checkbox__input.is-disabled.is-checked .el-checkbox__inner::after {
  border-color: #ffffff;
}
.el-checkbox__input.is-disabled.is-checked .el-checkbox__inner {
  background-color: #1d78ff;
  border-color: #1a57e5;
}
.el-input.is-disabled .el-input__inner {
  background-color: #f5f7fa;
  border-color: #e4e7ed;
  color: #212529;
  cursor: not-allowed;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen,
    Ubuntu, Cantarell, "Poppins,sans-serif", sans-serif;

  font-size: 15px;
}
.us-table_content {
  overflow: auto;
  height: 100vh;
}
.us-table__content table tr td {
  padding: 18px;
  line-height: 20px;
  height: 30px;

  border-right: 1px solid #e4c9ac;
}
.us-table__content {
  text-align: center;
  padding: 0 50px;
  overflow: auto;
  height: 100%;
  margin: 0px 20px;
}
.us-table_content {
  overflow: auto;
  height: 65vh;
}
.el-pagination {
  white-space: nowrap;
  color: #000000;
  font-weight: 700;
}
.el-form--label-top .el-form-item__label {
  float: none;
  display: inline-block;
  text-align: left;
  padding: 0 9px 17px;
}


</style>
      