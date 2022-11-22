<template>
  <div>
    <div class="de-list" v-if="hasRole">
      <div class="de-content">
        <div class="de-title"><strong>QUẢN LÝ PHÒNG BAN</strong></div>
        <br />

        <div class="de-info">
          <div class="col-3"></div>
          <div class="col-6">
            <table>
              <tr width="90%">
                <th>
                  <div>
                    <el-form :inline="true" class="input-find">
                      <el-form-item>
                        <el-input
                          v-model="keyWord"
                          placeholder="Nhập tên phòng ban"
                          style="width: 400px"
                        >
                          <i
                            slot="prefix"
                            class="el-input__icon el-icon-search"
                          ></i>
                        </el-input>
                      </el-form-item>
                      <el-form-item>
                        <el-button type="warning" @click="onFind()"
                          >Tìm kiếm</el-button
                        >
                      </el-form-item>
                    </el-form>
                  </div>
                </th>

                <th width="10%">
                  <div>
                    <el-form :inline="true" class="demo-form-inline">
                      <el-form-item>
                        <el-button
                          type="erorr"
                          class="btn btn-danger"
                          @click="showAddForm"
                        >
                          <i class="el-icon-plus"></i> Thêm mới
                        </el-button>
                      </el-form-item>
                    </el-form>
                  </div>
                </th>
              </tr>
            </table>
            <div class="de-table__content">
              <table>
                <thead>
                  <tr class="de-table_row">
                    <th width="100px">STT</th>
                    <th width="600px">Tên phòng ban</th>
                    <th width="200px">Thao tác</th>
                  </tr>
                </thead>
                <tbody>
                  <tr
                    v-for="(item, index) in listDepartments.content"
                    :key="index"
                  >
                    <td>{{ index + 1 }}</td>
                    <td style="text-align: left">{{ item.name }}</td>
                    <td>
                      <span class="icon-edit" @click="showEditForm(item)">
                        <i class="fa fa-edit"></i>
                      </span>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div class="col-3"></div>
            <!-- phân trang -->
            <el-pagination
              style="text-align: right"
              background
              layout="prev, pager, next"
              :page-count="count"
              :page-size="pageSize"
              :page-sizes="pageSizes"
              @current-change="handlePageChange"
              class="de-page"
            >
            </el-pagination>
          </div>
          <div class="department-image"></div>
        </div>
      </div>
      <!-- Thêm mới phòng ban -->
      <el-dialog
        style="text-align: center"
        title="Thêm mới phòng ban"
        :visible.sync="isShowAdd"
        width="600px"
        label-width="100px"
        top="5vh"
        left="150px"
      >
        <el-form
          :model="add"
          ref="add"
          label-width="120px"
          label-position="top"
        >
          <ValidationObserver ref="observer">
            <div class="row">
              <div class="mb-3 col-12">
                <ValidationProvider
                  v-slot="{ errors }"
                  name="userName"
                  :rules="{
                    required: true,
                  }"
                >
                  <el-form-item
                    style="text-align: left"
                    prop="name"
                    :error="messageError(addDepartment.name, errors[0])"
                  >
                    <Strong
                      >Tên phòng ban<span style="color: red">(*)</span>:</Strong
                    >
                    <el-input
                      type="text"
                      v-model="add.name"
                      @input="checkDuplicate"
                    ></el-input>
                  </el-form-item>
                </ValidationProvider>
              </div>
            </div>
          </ValidationObserver>
        </el-form>
        <div slot="footer" class="dialog-footer" style="text-align: center">
          <el-button class="de-detail__button" round @click="addDepartMent(add)"
            >Thêm mới
          </el-button>
        </div>
      </el-dialog>
      <!-- Chỉnh sửa phòng ban -->
      <el-dialog
        style="text-align: center"
        title="Chỉnh sửa thông tin phòng ban"
        :visible.sync="isShowEdit"
        width="600px"
        label-width="100px"
        top="5vh"
        left="150px"
      >
        <el-form
          :model="edit"
          ref="add"
          label-width="120px"
          label-position="top"
        >
          <ValidationObserver ref="observer">
            <el-form
              :model="add"
              ref="add"
              label-width="120px"
              label-position="top"
            >
              <div class="row">
                <div class="mb-3 col-12">
                  <ValidationProvider
                    v-slot="{ errors }"
                    name="userName"
                    :rules="{
                      required: true,
                    }"
                  >
                    <el-form-item
                      style="text-align: left"
                      prop="name"
                      :error="messageError(addDepartment.name, errors[0])"
                    >
                      <Strong
                        >Tên phòng ban
                        <span style="color: red">(*)</span> :</Strong
                      >
                      <el-input
                        type="text"
                        v-model="departmentName"
                        required
                        placeholder="Tên Phòng Ban"
                      ></el-input>
                    </el-form-item>
                  </ValidationProvider>
                </div>
              </div>
            </el-form>
          </ValidationObserver>
        </el-form>
        <div slot="footer" class="dialog-footer" style="text-align: center">
          <el-button
            class="de-detail__button"
            round
            @click="editDepartMent(edit.id, edit)"
            ><strong>Cập nhật</strong>
          </el-button>
        </div>
      </el-dialog>
    </div>
    <div class="error-middle" v-if="!hasRole">
      <strong class="fa fa-times-circle"> Error 401 - Unauthorized</strong
      ><br />
      _________________________________________<br />
      <h3>Bạn không có quyền truy cập vào đường dẫn này.</h3>
      <h3>You are not authorized to access this link.</h3>
    </div>
  </div>
</template>

<script>
import { ValidationObserver, ValidationProvider } from "vee-validate";
import DepartmentValidate from "@/utils/DepartmentValidate";
import DepartmentApi from "@/service/departmentService";
export default {
  name: "DepartmentList",
  components: { ValidationObserver, ValidationProvider },
  mixins: [DepartmentValidate],
  data() {
    return {
      departmentName: "",
      listDepartments: [],
      name: "",
      status: true,
      content: "",
      keyWord: "",
      hasRole: false,
      count: 0,
      pageSize: 10,
      pageSizes: [2, 4, 6],
      add: {
        name: "",
        status: 0,
      },
      edit: {
        id: "",
        name: "",
      },
      isShowAdd: false,
      isShowEdit: false,
    };
  },

  computed: {},

  methods: {
    // submit() {
    //   this.editDepartMent(this.edit.id, this.edit);
    // },
    showAddForm() {
      this.isShowAdd = true;
    },
    showEditForm(item) {
      this.isShowEdit = true;
      this.edit = item;
      this.departmentName = item.name;
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
      console.log(this.page);
      this.getAllDepartments();
    },

    retrieveDepartment() {
      const params = this.getRequestParams(
        this.page,
        this.pageSize,
        this.keyWord
      );
      DepartmentApi.getAll(params)
        .then((response) => {
          this.departments = response.data;
          this.count = response.data.totalPages;
          this.itemCount = response.data.totalElements;
        })
        .catch((e) => {
          console.log(e);
        });
    },

    getAllDepartments() {
      const params = this.getRequestParams(
        this.page,
        this.pageSize,
        this.keyWord
      );

      console.log(params);
      new DepartmentApi().getDepartment(params).then((res) => {
        this.listDepartments = res.data;
        this.hasRole =true;
        this.count = res.data.totalPages;
        this.itemCount = res.data.totalElements;
      });
    },

    editDepartMent(editId, editObject) {
      editObject.name = this.departmentName;
      let isValidate = true;
      if (!editObject.name) {
        this.$alert("Tên phòng ban không được phép để trống", "Nhập lại", {
          confirmButtonText: "OK",
          callback: () => {},
        });
        this.isShowEdit = false;
      }
      if (isValidate) {
        new DepartmentApi().updateDepartment(editId, editObject).then((res) => {
          console.log(res);
          // self.isLoaded = true;
          this.getAllDepartments();
          this.$notify({
            title: "Success",
            message: "Cập nhật Phòng Ban thành công",
            type: "success",
          });
          this.getAllDepartments();
        });
        this.isShowEdit = false;
      }
    },

    addDepartMent(add) {
      let isValidate = true;
      if (!add.name) {
        this.$alert("Tên phòng ban không được phép để trống", "Nhập lại", {
          confirmButtonText: "OK",
          callback: () => {},
        });
        isValidate = false;
      }
      if (isValidate) {
        new DepartmentApi().createDepartment(add).then((res) => {
          console.log(res);
          this.getAllDepartments();
          this.$notify({
            title: "Success",
            message: "Thêm mới Phòng Ban thành công",
            type: "success",
          });
        });
        this.isShowAdd = false;
        add.name = "";
      }

      // if(add.name.includes(this.departmentName)){
      //     this.$notify({
      //       title: "Warning",
      //     message: "Mã nhân viên đã tồn tại!",
      //     type: "warning",
      //     });
      //     return false;
      // }

      // else{
      //   this.$notify({
      //       title: "Warning",
      //       message: "Tên phòng ban không được phép để trống",
      //       type: "Warning",
      //     });
      // }
    },

    onFind() {
      this.getAllDepartments();
    },
    checkDuplicate() {
      //alert(this.add.name);
    },

    createDepartment() {},
  },

  created() {
    this.getAllDepartments();
  },
  checkDuplicate() {
    alert(this.departmentName);
  },
};
</script>
      
<style>
.de-list {
  background: linear-gradient(90deg, #e4c9ac 0%, rgba(255, 255, 255, 0) 100%),
    #e3c1d3;
  width: 100%;
  height: 100%;
}

.de-info {
  display: flex;
}

.de-title {
  text-align: center;
  font-size: 34px;
  font-weight: 600;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen,
    Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
  background: rgba(255, 255, 255, 0.13);
  padding: 6px 0px;
}

.icon-delete {
  cursor: pointer;
}

.icon-delete i {
  color: #f00;
}

.icon-edit {
  cursor: pointer;
}

.icon-edit i {
  color: #409eff;
}

.de-table {
  width: fit-content;
  margin-left: 200px;
  margin-right: 35px;
  z-index: 2;
}

.de-table_content table {
  width: 100%;
  border-collapse: collapse;
}

.de-table__content table thead tr {
  background: #fdf9f8;
  line-height: 15px;
}

.de-table__content table thead th {
  font-weight: 600;
  font-size: 14px;
}

.de-table__content table tbody tr:hover {
  background: #fdf9f8;
}

.de-table__content table tbody tr:last-child {
  border-bottom: none;
}

.de-info {
  display: flex;
}

.de-table__detail {
  width: 200px;
  height: 500px;
  background: #fff;
  border-radius: 30px;
  flex-direction: column;
  flex: 0.7;
  z-index: 2;
}

.de-detail__header {
  padding: 24px;
  font-size: 24px;
  font-weight: 700;
  display: flex;
  justify-content: space-between;
}

.de-detail__title {
  color: #000;
  border-bottom: 2px solid #f004;
}

.de-detail__close {
  color: #f00;
  cursor: pointer;
}

.de-detail__body {
  flex: 1;
}

.de-detail__footer {
  display: flex;
  justify-content: flex-end;
  padding: 28px 28px;
}

.de-detail__button {
  background-color: #dc3545;
  color: #fff !important;
  font-size: 14px !important;
  font-weight: 600 !important;
}

.de-detail__button:focus {
  background-color: rgba(255, 0, 0, 0.2) !important;
  border-color: rgba(255, 0, 0, 0.2) !important;
}

.de-detail__button:active {
  background-color: rgba(255, 0, 0, 0.3) !important;
  border-color: rgba(255, 0, 0, 0.3) !important;
}

/* .el-input__inner {
  border-radius: 0px !important;
  background-blend-mode: saturation;
  font-style: normal;
  font-size: 14;
} */

.el-form-item__label {
  color: #5e5252 !important;
  font-size: 15px;
  font-weight: 600;
  letter-spacing: 0em;
  text-align: left;
  line-height: 0px;
  margin-bottom: 0px;
}

.el-form-item__content input {
  border-radius: 8px !important;
  box-shadow: 1px 1px 4px rgba(0, 0, 0, 0.15);
  border: 1px solid #23282b8f;
}

.el-dialog__title {
  margin-left: 10px;
  font-size: 25px;
  font-weight: 700;
  line-height: 48px;
  letter-spacing: 0em;
  text-align: left;
  color: red;
}

.el-dialog__headerbtn i {
  font-size: 24px;
  font-weight: 700;
  color: #f00 !important;
}

.de-table__content table {
  width: 100%;
  border-collapse: collapse;
}

.de-table__content table tr {
  background: rgba(217, 217, 217, 0.13);
}

.de-table__content table tr th {
  border-right: 1px solid #e4c9ac;
  padding: 14px;
}

.de-table__content table tr td {
  padding: 20px;
  height: 30px;
}

.de-table__content table thead tr {
  position: sticky;
  top: 0;
}

.de-table__content table thead th {
  font-size: 14px;
  font-weight: 600;
}

.de-table__content table tr td {
  border-right: 1px solid #e4c9ac;
}

.de-table__content table tbody tr {
  padding: 18px;
  line-height: 15px;
  height: 30px;
  border-bottom: 1px solid #94806af8;
}

.de-table_content table tbody tr:hover {
  background-color: rgb(255, 255, 255);
}

.de-button {
  margin-left: 200px;
  margin-top: 30px;
}

.de-button__detail:hover {
  background-color: rgba(255, 0, 0, 0.1) !important;
  border-color: rgba(255, 0, 0, 0.1) !important;
}

.de-button__detail:focus {
  background-color: rgba(255, 0, 0, 0.2) !important;
  border-color: rgba(255, 0, 0, 0.2) !important;
}

.de-button__detail:active {
  background-color: rgba(255, 0, 0, 0.3) !important;
  border-color: rgba(255, 0, 0, 0.3) !important;
}

.el-pagination {
  white-space: nowrap;
  padding: 15px 0px;
  color: #303133;
  font-weight: 700;
}

/* .de-table__content {
  overflow: auto;
  
} */
.el-button.is-round {
  border-radius: 20px;
  padding: 12px 23px;
  color: white;
  background-color: #dc3545;
}

.el-button.is-round:hover {
  color: white;
  background-color: #bb2d3b;
}

.de-table__content table tr {
  background: #f2e7ddf8;
  text-align: center;
}
.error-middle {
  font-size: xx-large;
  text-align: center;
  margin-top: 100px;
}
.error-middle h3 {
  font-size: large;
  padding-top: 6px;
  font-style: italic;
}
.fa {
  letter-spacing: 3px;
}
</style>