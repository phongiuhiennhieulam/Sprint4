<template>
  <div class="department-list">
    <div class="department-content">
      <div class="department-title"><strong>QUẢN LÝ PHÒNG BAN</strong></div>
      <br />
      <div class="row">
        <div class="col-4" style="margin-left: 35px"></div>
        <div class="col-2" style="margin-left: 332px"></div>
        <div class="col-6"></div>
      </div>
      <div class="department-info">
        <div class="department-table">
          <div class="department-selected"></div>
          <table>
            <thead>
              <tr width="90%">
                <th>
                  <div>
                    <el-form :inline="true" class="input-find">
                      <el-form-item>
                        <el-input v-model="keyWord" placeholder="Nhập tên phòng ban" style="width: 300px">
                          <i slot="prefix" class="el-input__icon el-icon-search"></i>
                        </el-input>
                      </el-form-item>
                      <el-form-item>
                        <el-button type="warning" @click="onFind()">Tìm kiếm</el-button>
                      </el-form-item>
                    </el-form>
                  </div>
                </th>

                <th width="10%">
                  <div>
                    <el-form :inline="true" class="demo-form-inline">
                      <el-form-item>
                        <el-button type="erorr" class="btn btn-danger" @click="showAddForm">Thêm mới</el-button>
                      </el-form-item>
                    </el-form>
                  </div>
                </th>
              </tr>
            </thead>
          </table>
          <div class="department-table__content">
            <table>
              <thead>
                <tr>
                  <th width="90px">TT</th>
                  <th width="500px">Tên phòng ban</th>
                  <!-- <th width="200x">Trạng thái</th> -->
                  <th width="200px">Thao tác</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(item, index) in listDepartments.content" :key="index">
                  <td>{{ index + 1 }}</td>
                  <td style="text-align: left">{{ item.name }}</td>
                  <td>
                    <span class="icon-edit" @click="showEditForm(item)">
                      <i class="fa fa-edit"></i> </span>
                  </td>
                </tr>
              </tbody>
            </table>

            <el-pagination style="text-align: right" background layout="prev, pager, next" :page-count="count"
              :page-size="pageSize" :page-sizes="pageSizes" @current-change="handlePageChange">
            </el-pagination>
          </div>
        </div>
        <div class="department-image"></div>
      </div>
    </div>

    <el-dialog style="text-align: center" title="Thêm mới phòng ban" :visible.sync="isShowAdd" width="600px"
      label-width="100px" top="5vh" left="150px">
      <el-form :model="add" ref="add" label-width="120px" label-position="top">
        <el-row>
          <el-col>
            <el-form-item style="text-align: left" label="Tên phòng ban" prop="name" class="el-item">
              <el-input type="text" v-model="add.name" required placeholder="Tên Phòng Ban"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: center">
        <el-button class="department-detail__button" round @click="addDepartMent(add)">Thêm mới
        </el-button>
      </div>
    </el-dialog>

    <el-dialog style="text-align: center" title="Chỉnh sửa thông tin phòng ban" :visible.sync="isShowEdit" width="600px"
      label-width="100px" top="5vh" left="150px">
      <el-form :model="edit" ref="add" label-width="120px" label-position="top">
        <el-row>
          <el-col>
            <el-form-item style="text-align: left" label="Tên phòng ban" prop="name" class="el-item">
              <el-input type="text" v-model="edit.name" required placeholder="Tên Phòng Ban"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: center">
        <el-button class="department-detail__button" round @click="editDepartMent(edit.id, edit)">Cập nhật
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import DepartmentApi from "@/service/departmentService";
export default {
  name: "DepartmentList",
  data() {
    return {
      listDepartments: [],
      name: "",
      status: true,
      content: "",
      keyWord: "",
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
    showAddForm() {
      this.isShowAdd = true;
    },
    showEditForm(item) {
      this.isShowEdit = true;
      this.edit = item;
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
        this.count = res.data.totalPages;
        this.itemCount = res.data.totalElements;
      });
    },

    editDepartMent(editId, editObject) {
      let isValidate = true;
      if (!editObject.name) {
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
        });

        this.isShowAdd = false;
        // add.name = "";
        // add.status = 0;
      }
    },

    addDepartMent(add) {
      let isValidate = true;
      if (!add.name) {
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
      if (isValidate) {
        new DepartmentApi().createDepartment(add).then((res) => {
          console.log(res);
          // self.isLoaded = true;
          this.getAllDepartments();
          this.$notify({
            title: "Success",
            message: "Thêm mới Phòng Ban thành công",
            type: "success",
          });
        });

        this.isShowAdd = false;
        add.name = "";
        add.status = 0;
      }
    },

    onFind() {
      this.getAllDepartments();
    },
  },

  created() {
    this.getAllDepartments();
  },
};
</script>
      
<style scoped>
@import "@/assets/css/department/department.css";
</style>