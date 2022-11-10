<template>
    <div class="pl-main">
      <div class="pl-content">
        <div class="pl-title"><strong>QUẢN LÍ TÀI KHOẢN</strong></div>
        <br />
  
        <div class="pl-ele">
          <div class="pl-table">
            <div class="pl-table__content">
              <tr width="100%">
                <th>
                  <div>
                    <el-form :inline="true" class="demo-form-inline">
                      <el-form-item>
                        <el-input
                          v-model="keyWord"
                          placeholder="Nhập tên hoặc nhập email"
                          style="width: 400px"
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
                      <th>STT</th>
                      <th>Họ và tên</th>
                      <th>Email</th>
                      <th>Quyền Admin</th>
                      <th>Quyền Lãnh Đạo</th>
                      <th>Quyền Nhân Viên</th>
                      <th>Phân Quyền</th>
                      <th>Đổi mật khẩu</th>
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
                            <el-checkbox v-if="roles.id === 1" checked disabled />
                          </div>
                        </template>
                      </td>
  
                      <td>
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
            </div>
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
  
      <!-- Khóa tài khoản -->
      <el-dialog
        title="Xác nhận"
        :visible.sync="centerDialogVisible"
        width="50%"
        center
      >
        <span>Bạn có chắc chắn muốn khóa tài khoản này?</span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="centerDialogVisible = false">Hủy</el-button>
          <el-button type="primary" @click="showDeleteDialog(idDelete)"
            >Xác nhận</el-button
          >
        </span>
      </el-dialog>
  
      <!-- Cập nhật thông tin tài khoản -->
      <el-dialog
        title="Cập nhật thông tin tài khoản"
        :visible.sync="isShowEdit"
        width="500px"
        label-width="100px"
        top="5vh"
      >
        <el-form
          :model="edit"
          ref="edit"
          label-width="120px"
          label-position="top"
        >
          <el-row>
            <el-col>
              <el-form-item label="Email" prop="name">
                <el-input
                  type="text"
                  v-model="edit.userName"
                  :disabled="true"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col>
              <el-form-item label="Mật khẩu mới" prop="password">
                <el-input
                  type="password"
                  @input="checkpass"
                  v-model="newPassword"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col>
              <el-form-item label="Xác nhận lại mật khẩu" prop="password">
                <el-input
                  type="password"
                  @input="checkpass"
                  v-model="rePassword"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <span slot="footer" class="dialog-footer" style="text-align: center">
          <el-button
            class="hr-detail__button"
            round
            @click="editUser(edit.id, edit)"
            >Cập nhật
          </el-button>
        </span>
      </el-dialog>
  
      <!-- Cập nhật phân quyền -->
      <el-dialog
        title="Cập nhật phân quyền"
        :visible.sync="isShowEditRole"
        width="400px"
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
              <el-form-item style="text-align: left" label="Email" prop="name">
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
                    v-model="editRole.editRoleUser"
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
            class="hr-detail__button"
            round
            @click="editURole(edit.id, editRole)"
            >Cập nhật
          </el-button>
        </span>
      </el-dialog>
    </div>
  </template>
    
    <script>
  /* eslint-disable */
  import UserService from "@/service/userService";
  let userService = new UserService();
  export default {
    name: "UserList",
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
          editRoleUser: false,
          editRoleAdmin: false,
          editRoleModer: false,
        },
      };
    },
    computed: {},
    watch: {
      selected() {},
    },
  
    methods: {
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
        this.editRole.editRoleUser = false;
        // console.log(item)
        for (let i = 0; i < item.roles.length; i++) {
          if (item.roles[i].id == 1) {
            this.editRole.editRoleUser = true;
          } else if (item.roles[i].id == 2) {
            this.editRole.editRoleModer = true;
          } else if (item.roles[i].id == 3) {
            this.editRole.editRoleAdmin = true;
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
  
      retrieveUser() {
        const params = this.getRequestParams(
          this.page,
          this.pageSize,
          this.keyWord
        );
        UserService.getAll(params)
          .then((response) => {
            this.content.users = response.data;
            this.count = response.data.totalPages;
            this.itemCount = response.data.totalElements;
          })
          .catch((e) => {
            //console.log(e);
          });
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
        if (this.editRole.editRoleUser) {
          let object1 = {
            id: 1,
            name: "ROLE_USER",
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
            const myTimeout = setTimeout(myGreeting, 100);
  
            function myGreeting() {
              window.location.reload();
            }
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
  .pl-title {
    text-align: center;
    font-size: 34px;
    font-weight: 600;
    font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen,
      Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
    background: rgba(255, 255, 255, 0.13);
    padding: 6px 0px;
  }
  .pl-body {
    background: linear-gradient(90deg, #e4c9ac 0%, rgba(255, 255, 255, 0) 100%),
      #e3c1d3;
    width: 100%;
    height: 100%;
  }
  .money {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
    margin-bottom: 15px;
  }
  .money div {
    font-family: "Poppins", sans-serif;
    font-style: normal;
    font-weight: 600;
    font-size: 20px;
    line-height: 139%;
  }
  .pl-table {
    text-align: center;
    margin-left: 35px;
    margin-right: 35px;
    z-index: 2;
  }
  .pl-table__content {
    text-align: center;
    padding: 0 50px;
    overflow: auto;
    height: 100%;
    margin: 20px 0px;
  }
  .pl-table__content table {
    width: 100%;
    border-collapse: collapse;
  }
  .pl-table__content table tr {
    background: #f2e7ddf8;
  }
  .pl-table__content table tr th {
    border-right: 1px solid #e4c9ac;
    padding: 14px;
  }
  .pl-table__content table tr td {
    padding: 20px;
    line-height: 30px;
    height: 30px;
  }
  .pl-table__content table thead tr {
    background-color: #fdf9f8;
  }
  .pl-table__content table thead th {
    font-size: 14px;
    font-weight: 600;
  }
  .pl-table__content table tbody tr {
    border-bottom: 1px solid #e4c9ac;
  }
  .pl-table__content table tr td {
    border-right: 1px solid #e4c9ac;
  }
  .pl-table__content table tbody tr:hover {
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
    border-bottom: 1px solid #000;
  }
  /* input::-webkit-outer-spin-button,
      input::-webkit-inner-spin-button {
        -webkit-appearance: none;
        margin: 0;
      } */
  /* [placeholder]:focus::-webkit-input-placeholder {
        transition: text-indent 0.4s 0.4s ease;
        text-indent: -100%;
        opacity: 1;
      } */
  .pl-button {
    margin-left: 200px;
    margin-top: 30px;
  }
  .pl-button__detail {
    color: white !important;
    font-size: 14px !important;
    font-weight: 600 !important;
  }
  .pl-button__detail:hover {
    background-color: rgba(255, 0, 0, 0.1) !important;
    border-color: rgba(255, 0, 0, 0.1) !important;
  }
  .pl-button__detail:focus {
    background-color: rgba(255, 0, 0, 0.2) !important;
    border-color: rgba(255, 0, 0, 0.2) !important;
  }
  .pl-button__detail:active {
    background-color: rgba(255, 0, 0, 0.3) !important;
    border-color: rgba(255, 0, 0, 0.3) !important;
  }
  .el-form-item__label {
    color: rgb(0, 0, 0) !important;
    font-size: 22px;
    font-weight: 600;
    line-height: 31px;
    letter-spacing: 0em;
    text-align: left;
    line-height: 0px;
    margin-bottom: 6px;
  }
  
  .el-form-item__content input {
    border-radius: 8px !important;
    box-shadow: 1px 1px 4px rgba(255, 0, 0, 0.15);
    border: 1px solid #23282b8f;
  }
  
  .el-dialog__title {
    margin-left: 56px;
    font-size: 25px;
    font-weight: 700;
    line-height: 35px;
    letter-spacing: 0em;
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
    color: #000000;
    cursor: not-allowed;
    font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen,
      Ubuntu, Cantarell, "Poppins,sans-serif", sans-serif;
      
    font-size: 15px;
  }
  .us-table_content {
    overflow: auto;
    height: 100vh;
  }
  .pl-table__content table tr td {
    padding: 18px;
    line-height: 15px;
    height: 30px;
  }
  .pl-table__content {
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
    padding: 0px 140px;
    color: #000000;
    font-weight: 700;
  }
  .el-form--label-top .el-form-item__label {
      float: none;
      display: inline-block;
      text-align: left;
      padding: 0 9px 17px;
  }
  .el-dialog__title {
      margin-left: 56px;
      font-size: 25px;
      font-weight: 700;
      line-height: 35px;
      letter-spacing: 0em;
      color: red;
      text-align: left;
  }
  .el-input__inner {
      border-radius: 0px !important;
      font-family: "Poppins,sans-serif";
      background-blend-mode: saturation;
      font-style: normal;
      font-weight: 600;
      font-size: 20;
  }
  </style>
      