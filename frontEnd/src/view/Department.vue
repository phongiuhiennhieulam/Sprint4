<template>
    <div class="hr-list">
      <div class="hr-content">
        <div class="hr-title"><strong>QUẢN LÝ PHÒNG BAN</strong></div>
        <br />
        <div class="row">
          <div class="col-4" style="margin-left: 35px"></div>
          <div class="col-2" style="margin-left: 332px"></div>
          <div class="col-6"></div>
        </div>
        <div class="hr-info">
          <div class="hr-table">
            <div class="hr-selected"></div>
            <table>
              <thead>
                <tr width="90%">
                  <th>
                    <div>
                      <el-form :inline="true" class="input-find">
                        <el-form-item>
                          <el-input
                            v-model="text"
                            placeholder="Nhập tên phòng ban"
                            style="width: 300px"
                          ></el-input>
                        </el-form-item>
                        <el-form-item  >
                          <el-button  type="warning" @click="onFind()"
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
                            >Thêm mới</el-button
                          >
                        </el-form-item>
                      </el-form>
                    </div>
                  </th>
                </tr>
              </thead>
            </table>
            <div class="hr-table__content">
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
                  <tr v-for="(item, index) in listDepartments" :key="index">
                    <td>{{ index + 1 }}</td>
                    <td style="text-align: left">{{ item.name }}</td>
                    <!-- <td style="text-align: left">
                      <div v-if="item.status == 0">Đang hoạt động</div>
                      <div v-if="item.status == 1">Dừng hoạt động</div>
                    </td> -->
                    <td>
                      <span class="icon-edit" @click="showEditForm(item)">
                        <i class="fa fa-edit"></i> </span
                      >&nbsp;
  
                      <!-- <span
                        class="icon-delete"
                      >
                      <i v-if="item.status == 0" class="fa fa-unlock"></i>
                      <i v-if="item.status == 1" i class="fa fa-lock"></i>
                    </span> -->
                    </td>
                  </tr>
                </tbody>
              </table>
              <el-pagination
                style="text-align: right"
                background
                layout="prev, pager, next"
                :page-count="count"
                :page-size="pageSize"
                :page-sizes="pageSizes"
                hide-on-single-page
              >
              </el-pagination>
            </div>
          </div>
          <div class="hr-image"></div>
        </div>
      </div>
  
      <el-dialog
        style="text-align: center"
        title="Thêm mới phòng ban"
        :visible.sync="isShowAdd"
        width="600px"
        label-width="100px"
        top="5vh"
        left="150px"
      >
        <el-form :model="add" ref="add" label-width="120px" label-position="top">
          <el-row>
            <el-col>
              <el-form-item
                style="text-align: left"
                label="Tên phòng ban"
                prop="name"
                class="el-item"
              >
                <el-input
                  type="text"
                  v-model="add.name"
                  required
                  placeholder="Tên Phòng Ban"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer" style="text-align: center">
          <el-button class="hr-detail__button" round @click="addDepartMent(add)"
            >Thêm mới
          </el-button>
        </div>
      </el-dialog>
  
      <el-dialog
        style="text-align: center"
        title="Chỉnh sửa thông tin phòng ban"
        :visible.sync="isShowEdit"
        width="600px"
        label-width="100px"
        top="5vh"
        left="150px"
      >
        <el-form :model="edit" ref="add" label-width="120px" label-position="top">
          <el-row>
            <el-col>
              <el-form-item
                style="text-align: left"
                label="Tên phòng ban"
                prop="name"
                class="el-item"
              >
                <el-input
                  type="text"
                  v-model="edit.name"
                  required
                  placeholder="Tên Phòng Ban"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer" style="text-align: center">
          <el-button class="hr-detail__button" round @click="editDepartMent(edit.id,edit)"
            >Cập nhật
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
        filter: {
          name: null,
        },
        text: "",
        listDepartments: [],
        name: "",
        status: true,
        count: 0,
        pageSize: 10,
        pageSizes: [2, 4, 6],
        multipleSelection: [],
  
        add: {
          name: "",
          status: 0
        },
        edit: {
          id:"",
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
  
      getRequestParams(page, pageSize) {
        let params = {};
        if (page) {
          params["page"] = page - 1;
        }
        if (pageSize) {
          params["size"] = pageSize;
        }
        return params;
      },
  
      getAllDepartments() {
        new DepartmentApi().getDepartment().then((res) => {
          console.log(res.data);
          this.listDepartments = res.data;
        });
      },
  
      editDepartMent(editId,editObject) {
        let isValidate = true;
        if (!editObject.name) {
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
        if (isValidate) {
          new DepartmentApi().updateDepartment(editId,editObject).then((res) => {
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
              callback: () => {},
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
  
      // retrieveStaff() {
      //   const params = this.getRequestParams(this.page, this.pageSize);
      //   StaffService.getAll(params)
      //     .then((response) => {
      //       this.staffs = response.data;
      //       // this.staffs.content =
      //       this.count = response.data.totalPages;
      //       this.itemCount = response.data.totalElements;
      //     })
      //     .catch((e) => {
      //       console.log(e);
      //     });
      // },
  
      onFind() {
        console.log(this.text);
        DepartmentApi.getFind(this.text)
          .then((response) => {
            this.departments = response.data;
            this.count = response.data.totalPages;
            this.itemCount = response.data.totalElements;
            console.log(response.data);
          })
          .catch((e) => {
            console.log(e);
          });
      },
    },
  
    created() {
      this.getAllDepartments();
    },
  };
  </script>
    
    <style>
  .hr-image {
    background-image: url("@/assets/image/profiledata.svg");
    background-repeat: no-repeat;
    background-size: cover;
    background-position: center;
    width: 600px;
    height: 600px;
    position: absolute;
    bottom: 0;
    right: 0;
  }
  .hr-list {
    background: linear-gradient(90deg, #e4c9ac 0%, rgba(255, 255, 255, 0) 100%),
      #e3c1d3;
    width: 100%;
    height: 100%;
  }
  .hr-info {
    display: flex;
  }
  .hr-title {
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
  .hr-table {
    width: fit-content;
    margin-left: 35px;
    margin-right: 35px;
    z-index: 2;
  }
  .hr-table__content table {
    width: 100%;
    border-collapse: collapse;
  }
  .hr-table__content table tr {
    background: #f2e7ddf8;
    text-align: center;
  }
  .hr-table__content table tr th {
    border-right: 1px solid #e4c9ac;
    padding: 14px;
  }
  .hr-table__content table tr td {
    padding: 8px;
    height: 30px;
    line-height: 30px;
  }
  .hr-table__content table thead tr {
    background: #fdf9f8;
  }
  .hr-table__content table thead th {
    font-weight: 600;
    font-size: 14px;
  }
  .hr-table__content table tbody tr {
    border-bottom: 1px solid #94806af8;
  }
  .hr-table__content table tbody tr:hover {
    background: #fdf9f8;
  }
  .hr-table__content table tbody tr:last-child {
    border-bottom: none;
  }
  .hr-info {
    display: flex;
  }
  .hr-table__detail {
    width: 200px;
    height: 500px;
    background: #fff;
    border-radius: 30px;
    flex-direction: column;
    flex: 0.7;
    z-index: 2;
  }
  .hr-detail__header {
    padding: 24px;
    font-size: 24px;
    font-weight: 700;
    display: flex;
    justify-content: space-between;
  }
  .hr-detail__title {
    color: #000;
    border-bottom: 2px solid #f004;
  }
  .hr-detail__close {
    color: #f00;
    cursor: pointer;
  }
  .hr-detail__body {
    flex: 1;
  }
  .hr-detail__body ul {
    margin: 0;
  }
  .hr-detail__body ul li {
    padding: 8px 0px;
  }
  .hr-detail__footer {
    display: flex;
    justify-content: flex-end;
    padding: 28px 28px;
  }
  .hr-detail__button {
    color: #f00 !important;
    font-size: 14px !important;
    font-weight: 600 !important;
  }
  .hr-detail__button:hover {
    background-color: rgba(255, 0, 0, 0.1) !important;
    border-color: rgba(255, 0, 0, 0.1) !important;
  }
  .hr-detail__button:focus {
    background-color: rgba(255, 0, 0, 0.2) !important;
    border-color: rgba(255, 0, 0, 0.2) !important;
  }
  .hr-detail__button:active {
    background-color: rgba(255, 0, 0, 0.3) !important;
    border-color: rgba(255, 0, 0, 0.3) !important;
  }
  .hr-function {
    margin-top: 40px;
  }
  .el-select {
    width: 100%;
  }
  .el-input__inner {
    border-radius: 0px !important;
    font-family: "Poppins,sans-serif";
    background-blend-mode: saturation;
    font-style: normal;
    font-weight: 600;
    font-size: 20;
  }
  .el-form-item__label {
    color: #f00 !important;
    font-size: 20px;
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
  .pl-table__content table {
    width: 100%;
    border-collapse: collapse;
  }
  .pl-table__content table tr {
    background: rgba(217, 217, 217, 0.13);
  }
  .pl-table__content table tr th {
    border-right: 1px solid #c7c7c7;
    padding: 14px;
  }
  .pl-table__content table tr td {
    padding: 20px;
    line-height: 30px;
    height: 30px;
  }
  .pl-table__content table thead tr {
    background-color: #d9d9d9;
    position: sticky;
    top: 0;
  }
  .pl-table__content table thead th {
    font-size: 14px;
    font-weight: 600;
  }
  .pl-table__content table tr td {
    border-right: 1px solid #c7c7c7;
  }
  .pl-table__content table tbody tr {
    border-bottom: 1px solid #c7c7c7;
  }
  .pl-table__content table tbody tr:hover {
    background-color: pink;
  }
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
  
  </style>