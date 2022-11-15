<template>
  <div class="pl-main">
    <div class="pl-content">
      <div class="pl-title"><strong>QUẢN LÍ TÀI KHOẢN</strong></div>

      <div class="pl-table">
        <el-form :inline="true" class="demo-form-inline">
          <el-form-item>
            <el-input v-model="keyWord" placeholder="Nhập tên hoặc nhập email" style="width: 400px">
              <i slot="prefix" class="el-input__icon el-icon-search"></i>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="warning" @click="onFind()">Tìm kiếm</el-button>
          </el-form-item>
        </el-form>
        <div class="pl-table__content">
          <div class="user-table__content">
            <table>
              <thead>
                <tr class="user-table__row">
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
                    <span class="icon-user" @click="showEditRole(item, index)">
                      <i class="fa fa-user"></i>
                    </span>
                  </td>

                  <span class="icon-edit">
                    <span class="icon-role" @click="showEditForm(item, index)">
                      <i class="fa fa-key"></i>
                    </span>
                  </span>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <el-pagination style="text-align: right" background layout="prev, pager, next" :page-count="count"
          :page-size="pageSize" :page-sizes="pageSizes" @current-change="handlePageChange">
        </el-pagination>
      </div>
    </div>

    <!-- Khóa tài khoản -->
    <el-dialog title="Xác nhận" :visible.sync="centerDialogVisible" width="50%" center>
      <span>Bạn có chắc chắn muốn khóa tài khoản này?</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="centerDialogVisible = false">Hủy</el-button>
        <el-button type="primary" @click="showDeleteDialog(idDelete)">Xác nhận</el-button>
      </span>
    </el-dialog>

    <!-- Cập nhật thông tin tài khoản -->
    <el-dialog title="Cập nhật thông tin tài khoản" :visible.sync="isShowEdit" width="500px" label-width="100px"
      top="5vh">
      <el-form :model="edit" ref="edit" label-width="120px" label-position="top">
        <el-row>
          <el-col>
            <el-form-item label="Email" prop="name">
              <el-input type="text" v-model="edit.userName" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col>
            <el-form-item label="Mật khẩu mới" prop="password">
              <el-input type="password" @input="checkpass" v-model="newPassword"></el-input>
            </el-form-item>
          </el-col>
          <el-col>
            <el-form-item label="Xác nhận lại mật khẩu" prop="password">
              <el-input type="password" @input="checkpass" v-model="rePassword"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer" style="text-align: center">
        <el-button class="hr-detail__button" round @click="editUser(edit.id, edit)">Cập nhật
        </el-button>
      </span>
    </el-dialog>

    <!-- Cập nhật phân quyền -->
    <el-dialog title="Cập nhật phân quyền" :visible.sync="isShowEditRole" width="400px" label-width="100px" top="5vh">
      <el-form :model="editRole" ref="edit" label-width="120px" label-position="top">
        <el-row>
          <el-col>
            <el-form-item style="text-align: left" label="Email" prop="name">
              <el-input type="text" v-model="edit.userName" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col>
            <el-form-item>
              <div>
                <el-checkbox v-model="editRole.editRoleModer" label="Quyền Lãnh Đạo" size="large" />
              </div>
            </el-form-item>
            <el-form-item>
              <div>
                <el-checkbox v-model="editRole.editRoleAdmin" label="Quyền Admin" size="large" />
              </div>
            </el-form-item>
            <el-form-item>
              <div>
                <el-checkbox v-model="editRole.editRoleUser" label="Quyền Nhân Sự" size="large" />
              </div>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button class="hr-detail__button" round @click="editURole(edit.id, editRole)">Cập nhật
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
    selected() { },
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
            callback: () => { },
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
      
<style scoped>
@import "@/assets/css/user/user.css"
</style>
      