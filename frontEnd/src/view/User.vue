<template>
  <div class="pl-main">
    <div class="pl-content">
      <div class="pl-title"><strong>QUẢN LÍ TÀI KHOẢN</strong></div>
      <br />
      <div class="pl-ele">
        <div class="pl-table">
          <div class="pl-table__content">
            <tr>
              <th>
                <div>
                  <el-form :inline="true" class="demo-form-inline">
                    <el-form-item>
                      <el-input
                        v-model="filter.name"
                        placeholder="Nhập tên hoặc email"
                        style="width: 400px"
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

            <form id="form" label-width="100px">
              <table>
                <thead>
                  <tr>
                    <th>STT</th>
                    <th>Họ và tên</th>
                    <th>Email</th>
                    <!-- <th>Mã nhân viên</th> -->
                    <th>Quyền Lãnh Đạo</th>
                    <th>Quyền Admin</th>
                    <th>Quyền Nhân Viên</th>
                    <th>Trạng thái</th>
                    <th>Thao tác</th>
                    <th>Khóa</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(item, index) in listUser" :key="index">
                    <td>{{ index + 1 }}</td>
                    <td>{{ item.name }}</td>
                    <td>{{ item.userName }}</td>
                    <!-- <td>{{ item.code }}</td> -->

                    <td>
                      <template>
                        <div v-for="(roles, index) in item.roles" :key="index">
                          <el-checkbox
                            v-if="roles.id === 2"
                            checked
                          />
                        </div>
                      </template>
                    </td>

                    <td>
                      <template>
                        <div v-for="(roles, index) in item.roles" :key="index">
                          <div  v-if="roles.id === 3">
                            <el-checkbox
                            checked
                          />
                          </div>
                      
                          
                        </div>
                      </template>
                    </td>

                    <td>
                      <template>
                        <div v-for="(roles, index) in item.roles" :key="index">
                          <el-checkbox
                            v-if="roles.id === 1"
                            checked
                          />
                        </div>
                      </template>
                    </td>

                    <td style="text-align: center">
                      <div v-if="item.status == 0">Đang làm việc</div>
                      <div v-if="item.status == 1">Nghỉ việc</div>
                    </td>

                    <td>
                      <span
                        class="icon-edit"
                        @click="showEditForm(item, index)"
                      >
                        <i class="fa fa-key"></i> </span
                      >&ensp;

                      <span
                        class="icon-user"
                        @click="showEditRole(item, index)"
                      >
                        <i class="fa fa-user"></i>
                      </span>
                    </td>

                    <span class="icon-delete">
                      <span>
                        <i
                          v-if="item.status == 0"
                          class="fa fa-unlock"
                          @click="deleteUser(item.id, index)"
                        ></i>
                      </span>
                      <span>
                        <i
                          v-if="item.status == 1"
                          @click="unlockUser(item.id)"
                          class="fa fa-lock"
                        ></i>
                      </span>
                    </span>
                  </tr>
                </tbody>
              </table>
            </form>
          </div>
        </div>
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
      width="600px"
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
                <el-checkbox   label="Quyền Lãnh Đạo" size="large"  />
              </div>
            </el-form-item>
            <el-form-item>
              <div>
                <el-checkbox  label="Quyền Admin" size="large" />
              </div>
            </el-form-item>
            <el-form-item>
              <div>
                <el-checkbox  label="Quyền Nhân Sự" size="large" />
              </div>
            </el-form-item>
          </el-col>
        
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button
          class="hr-detail__button"
          round
          @click="editRole(edit.id, edit)"
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
      name: "",
      userName: "",
      status: "",
      code: "",
      roles: "",
      newPassword: null,
      rePassword: null,
      // role1: "ROLE_USER",
      // role2: "ROLE_MODERATOR",
      // role3: "ROLE_ADMIN",
      // Chucvu: "",
      userId: 0,
      isDuplicate: false,
      centerDialogVisible: false,
      user: {},
      filter: {
        name: null,
      },

      edit: {
        id: "",
        newpassword: "",
        rePassword: "",
      },
      isShowEdit: false,
      isShowEditRole: false,
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
      this.edit = item;
      this.isShowEditRole = true;
    },
    checkpass() {
      if (this.newPassword === this.rePassword) {
        this.isDuplicate = true;
      } else {
        this.isDuplicate = false;
      }
    },

    getAllUser() {
      new UserService().getAllUser(null).then((res) => {
        this.listUser = res.data;
        console.log(this.listUser);
      });
    },

    editRole(editID,editObject){
        new UserService().getRole(editID, editObject).then((res) => {
          // console.log(res);
          this.$notify({
            title: "Success",
            message: "Phân quyền thành công",
            type: "success",
          });
        });
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
          // console.log(res);

          this.getAllUser();
          this.$notify({
            title: "Success",
            message: "Đổi mật khẩu thành công",
            type: "success",
          });
        });

        this.isShowEdit = false;
        // add.name = "";
        // add.status = 0;
      }
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

  // onFind() {
  //   console.log(this.text);
  //   UserService.getFind(this.text)
  //     .then((response) => {
  //       this.user = response.data;
  //       this.item = response.data.totalPages;
  //       this.itemCount = response.data.totalElements;
  //       console.log(response.data);
  //     })
  //     .catch((e) => {
  //       console.log(e);
  //     });
  // },

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
  margin-left: 10px;
  margin-right: 10px;
  z-index: 2;
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
  background-color: pink;
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
  color: #f00 !important;
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
  box-shadow: 1px 1px 4px rgba(0, 0, 0, 0.15);
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
</style>
    