<template>
  <div class="staff-main">
    <div class="staff-content">
      <div class="staff-title">
        <strong>QUẢN LÝ TIỀN HỖ TRỢ PHÚC LỢI NHÂN VIÊN </strong>
      </div>
      <br />

      <div class="staff-ele">
        <div class="staff-table">
          <div class="staff-table__content">
            <tr class="staff-header-table">
              <th>
                  <el-form :inline="true" class="demo-form-inline">
                    <el-form-item>
                      <el-input
                       
                        placeholder="Nhập tên hoặc mã nhân viên "
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
                        ><strong>Tìm kiếm</strong></el-button
                      >
                    </el-form-item>  
                  </el-form>
              </th>
              <th>
                  <el-form :inline="true" class="demo-form-inline" style="margin-left: 428px;">
                    <span v-if="isCreate">
                      <el-form-item>
                        <el-input
                        id="moneyUpdate" name="moneyUpdate"
                        placeholder="Số tiền hỗ trợ phúc lợi"
                        style="width: 200px"  v-on:blur="keyupUpdateMoney" 
                        @keydown="keyupUpdateMoney" 
                        v-model="moneyUpdate" required
                      >
                      ></el-input>

                      <el-button style="margin-left: 6px;" type="danger"  v-loading.fullscreen.lock="fullscreenLoading"
                      @click.prevent="handlUpdateMoney2()">
                        <strong>Thêm <i class="el-icon-right"></i></strong>
                      </el-button>
                    </el-form-item>
                    
                    <el-form-item>
                      <el-badge :value="list.length" class="item" type="warning">
                        <el-button type="danger" @click="showList()"
                        >
                            <Strong>
                              <i class="el-icon-document-copy"></i> Danh sách
                          </Strong>
                        </el-button>
                      </el-badge>
                     </el-form-item> 

                    </span>
                    <span v-if="isUpdate">
                      <el-form-item name="form-updateMoney">
                      <el-input
                        id="moneyUpdate" name="moneyUpdate"
                        placeholder="Số tiền hỗ trợ phúc lợi"
                        style="width: 200px"  v-on:blur="keyupUpdateMoney" 
                        @keydown="keyupUpdateMoney" 
                        v-model="moneyUpdate" required
                      >
                      ></el-input>

                      <el-button style="margin-left: 6px;" type="danger"  v-loading.fullscreen.lock="fullscreenLoading"
                      @click.prevent="handlUpdateMoney()">
                        <i class="el-icon-refresh"></i> <strong>Cập nhật tiền hỗ trợ</strong>
                      </el-button>
                    </el-form-item>
                   
                      
                  
                    </span>
                  </el-form>
              </th>
            </tr>

            <div class="staff-table_content">
              <table>
                <thead>
                  <tr class="staff-table_row">
                    <th  v-if="isCreate||isUpdate"><input type="checkbox" v-model="selectAll" /></th>
                    <th >STT</th>
                    <th >Họ và tên</th>
                    <th >Mã nhân viên</th>
                    <th >Phòng ban</th>
                    <th >Tiền đang hưởng</th>
                      <th>
                        <el-dropdown @command="handleCommand" v-model="status">
                          <span class="el-dropdown-link" style="color: black">
                            <span v-if="isCreate">
                              Chưa cập nhật tiền
                            </span>
                            <span v-if="isUpdate">
                              Đã cập nhật tiền
                            </span>
                            <span v-if="!isUpdate && !isCreate && !isAll">
                              Chờ xét duyệt
                            </span>
                            <span v-if="isAll">
                              Tất cả
                            </span>
                          
                            <i class="fa fa-caret-down" aria-hidden="true"></i>
                          </span>
                          <template #dropdown>
                            <el-dropdown-menu>
                              <el-dropdown-item command="accept" :value="3"
                                ><strong>Chưa cập nhật tiền</strong></el-dropdown-item
                              >
                              <el-dropdown-item command="cancel" :value="0"
                                ><strong>Đã cập nhật tiền</strong></el-dropdown-item
                              >
                              <el-dropdown-item command="waiting" :value="2"
                                ><strong>Chờ Duyệt</strong></el-dropdown-item
                              >
                              <el-dropdown-item command="all" :value="4"
                                ><strong>Tất cả</strong></el-dropdown-item
                              >
                            </el-dropdown-menu>
                          </template>
                        </el-dropdown>
                    </th>
                  </tr>
                </thead>

                <tbody>
                  <tr v-for="(x, index) in staffs.content" :key="x.id">
                    <td v-if="isCreate||isUpdate">
                      <input
                        type="checkbox"
                        :value="x.id"
                        v-model="selected"
                        :ref="x.id"
                        required
                      />
                    </td>
                    <td>{{index + 1}}</td>
                    <td>{{x.name}}</td>
                    <td>{{x.code}}</td>
                    <td>{{x.department.name}}</td>
                    <td>{{formatCurrency(x.welfareMoney)}}</td>
                    <td>
                      <span v-if="x.status===0 || x.status ===1" style="color: seagreen;">
                        Đã cập nhật tiền
                      </span>
                      <span v-if="x.status===3" style="color: red; text-decoration: underline;" @click="goCretae()">
                        Chưa cập nhật tiền
                      </span>
                      <span v-if="x.status===2" style="color: darkgoldenrod;">
                        Đang chờ duyệt 
                      </span>
                    </td>


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
<!-- dialog -->
    <el-dialog :visible.sync="isList" width="1000px"  label-width="50px" top="5vh" left="150px" title="Danh sách nhân viên xét duyệt tiền phúc lợi"
          boder="">
          <div >
      <div class="staff2-content">
        <div class="staff2-ele">
          <div class="staff2-table">
            <div class="staff2-table__content">
              <div class="">
                <table>
                  <thead>
                    <tr class="staff2-table_row">
                      <th >STT</th>
                      <th >Họ và tên</th>
                      <th >Mã nhân viên</th>
                      <th >Phòng ban</th>
                      <th >Số tiền hỗ trợ</th>
                      <th>Hoàn tác</th>
                    </tr>
                  </thead>
                  
                  <tbody>
                    <tr v-for="(x, index) in list" :key="x.id">
                      <td>{{index +1}}</td>
                      <td>{{x.name}}</td>
                      <td>{{x.code}}</td>
                      <td>{{x.department}}</td>
                      <td>{{formatCurrency(x.money)}}</td>
                      <td>
                        <el-button @click.prevent="getReturn(x.id)" type="warning" icon="el-icon-refresh-left" circle>
                        </el-button>
                      </td>
                    </tr>
                  </tbody>
                </table>
               <div style="margin-top: 10px;">
                <el-button class="btn btn-danger" v-loading.fullscreen.lock="fullscreenLoading"
                    @click="getPush()">
                    <i class="el-icon-top"></i> Gửi yêu cầu xét duyệt
                  </el-button>
               </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    </el-dialog>
  </div>
</template>
    
<script>
/* eslint-disable */
import StaffService from "@/service/hrService"

export default {
  name: "UserList",
  data() {
    return {
      isList: false,
      staffs: [],
      count: 0,
      pageSize: 10,
      pageSizes: [2, 4, 6],
      status: '',
      isCreate: false,
      isUpdate: false,
      selected: [],
      moneyUpdate: '',
      isAll: true,
      user: {},
      ok: 'hehe',
        list: [],
        selected: []
  }
},
 
  methods: {
    addCommas(nStr) {
        var x, x1, x2;

        nStr += '';
        x = nStr.split('.');
        x1 = x[0];
        x2 = x.length > 1 ? ',' + x[1] : '';
        var rgx = /(\d+)(\d{3})/;
        while (rgx.test(x1)) {
          x1 = x1.replace(rgx, '$1' + ',' + '$2');
        }
        return x1 + x2;
      },
    keyupUpdateMoney() {
        this.moneyUpdate = this.addCommas(this.moneyUpdate.replace(/,/g, ''));
        this.$emit("input", parseInt(this.staff.welfareMoney.replace(/,/g, '')))
      },
      formatCurrency(value) {
      return Intl.NumberFormat("vi-VN", {
        style: "currency",
        currency: "VND",
      }).format(value);
    },
    showList(){
      this.isList = true;
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
      handlePageChange(value) {
        this.page = value;
        this.getAll();
      },
      handleCommand(command) {
      if (command === "accept") {
        this.retrieveStaff(3);
        this.isCreate = true
        this.isUpdate = false
        this.isAll = false
      }

      if (command === "cancel") {
        this.retrieveStaff(0);
        this.isUpdate = true
        this.isCreate = false
        this.isAll = false


      }
      if (command === "waiting") {
        this.retrieveStaff(2);
        this.isUpdate = false
        this.isCreate = false
        this.isAll = false

      }
      if (command === "all") {
        this.getAll();
        this.isUpdate = false
        this.isCreate = false
        this.isAll = true
      }
    },
   goCretae(){
    this.retrieveStaff(3);
    this.isCreate = true
    this.isAll = false
    this.isUpdate = false

    this.isList = false

   },
    retrieveStaff(a) {
        const params = this.getRequestParams(this.page, this.pageSize);
        StaffService.getAll2(params, a)
          .then((response) => {
            this.staffs = response.data;
            // this.staffs.content =
            this.count = response.data.totalPages;
            this.itemCount = response.data.totalElements;
          })
          .catch((e) => {
            console.log(e);
          });
      },
      getAll() {
        const params = this.getRequestParams(this.page, this.pageSize);
        StaffService.getAll(params)
          .then((response) => {
            this.staffs = response.data;
            // this.staffs.content =
            this.count = response.data.totalPages;
            this.itemCount = response.data.totalElements;
          })
          .catch((e) => {
            console.log(e);
          });
      },
      handlUpdateMoney() {
        let money = this.moneyUpdate.replace(/,/g, '');
        console.log(money)
        if (money === "") {
          this.$notify({
            title: "Warning",
            message: "Tổng tiền không được để trống!",
            type: "warning",
          });
          return false;
        }
        if (money < 0) {
          this.$notify({
            title: "Warning",
            message: "Tổng tiền phải là số dương!",
            type: "warning",
          });
          return false;
        }
        if (isNaN(money)) {
          this.$notify({
            title: "Warning",
            message: "Tổng tiền phải là số!",
            type: "warning",
          });
          return false;
        }
        if (this.selected.length==0) {
          this.$notify({
            title: "Warning",
            message: "Bạn chưa chọn nhân viên nào!",
            type: "warning",
          });
          return false;
        }
        this.$confirm(
          "Bạn có chắc sẽ cập nhật tiền cho những nhân viên này không?",
          "Warning",
          {
            confirmButtonText: "Có",
            cancelButtonText: "Hủy",
            type: "warning",
          }
        )
          .then(() => {
            var username = this.user.userName;
            StaffService.updateMoney2(money, this.selected, username)
            .then((response)=>{
              console.log(response.data);
              this.retrieveStaff(0)
              this.selected = [];
              this.moneyUpdate = ''

            })          
            this.$notify({
            title: "success",
            message: "Gửi yêu cầu thành công!",
            type: "warning",
          });

          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "Lỗi hệ thống!",
            });
          });
      },
      handlUpdateMoney2() {
        let money = this.moneyUpdate.replace(/,/g, '');
        console.log(money)
        if (money === "") {
          this.$notify({
            title: "Warning",
            message: "Tổng tiền không được để trống!",
            type: "warning",
          });
          return false;
        }
        if (money < 0) {
          this.$notify({
            title: "Warning",
            message: "Tổng tiền phải là số dương!",
            type: "warning",
          });
          return false;
        }
        if (isNaN(money)) {
          this.$notify({
            title: "Warning",
            message: "Tổng tiền phải là số!",
            type: "warning",
          });
          return false;
        }
        if (this.selected.length==0) {
          this.$notify({
            title: "Warning",
            message: "Bạn chưa chọn nhân viên nào!",
            type: "warning",
          });
          return false;
        }
        this.$confirm(
          "Bạn có chắc sẽ cập nhật tiền cho những nhân viên này không?",
          "Warning",
          {
            confirmButtonText: "Có",
            cancelButtonText: "Hủy",
            type: "warning",
          }
        )
          .then(() => {
            var username = this.user.userName;
            StaffService.updateMoney3(money, this.selected, username)
            .then((response)=>{
              console.log(response.data);
              this.retrieveStaff(3)
              this.getAll2()
              this.selected = [];
              this.moneyUpdate = ''
              

            })          
            this.$message({
              type: "success",
              message: "Đã gửi yêu cầu!",
            });

          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "Lỗi hệ thống!",
            });
          });
      },
      getAll2() {
        this.$emit('clicked-something')
        StaffService.getOder()
          .then((response) => {
            this.list = response.data;
            // this.staffs.content =
            console.log(this.list);

          })
          .catch((e) => {
            console.log(e);
          });
      },
      getPush(){
       
        this.$confirm(
          "Bạn có chắc sẽ gửi danh sách này không?",
          "Warning",
          {
            confirmButtonText: "Có",
            cancelButtonText: "Hủy",
            type: "warning",
          }
        )
          .then(() => {
            var selected = [];
          this.list.forEach(function (x) {
            selected.push(x.id);
          });
        this.selected = selected;
        StaffService.push(this.selected)
            .then((response)=>{
              console.log(response.data);
              this.getAll2()
              this.getAll()

            })          
            this.$message({
              type: "success",
              message: "Đã gửi yêu cầu!",
            });

          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "Lỗi hệ thống!",
            });
          });
      },
      getReturn(id){
       this.$confirm(
         "Bạn có chắc sẽ hoàn tác không",
         "Warning",
         {
           confirmButtonText: "Có",
           cancelButtonText: "Hủy",
           type: "warning",
         }
       )
        .then(() => {
       StaffService.retunrOder(id)
           .then((response)=>{
             console.log(response.data);
             this.getAll2()
             this.goCretae()

           })          
           this.$message({
             type: "success",
             message: "Hoàn tác thành công!",
           });
         })
         .catch(() => {
           this.$message({
             type: "info",
             message: "Lỗi hệ thống!",
           });
         });
     }
      
  },
  created(){
    this.getAll();
    this.getAll2();
    StaffService.getUser()
        .then((response) => {
          this.user = response.data;
        })  
       
  },
  computed: {
      selectAll: {
        get: function () {
          return this.staffs.content ? this.selected.length == this.staffs.content.length : false;
        },
        set: function (value) {
          var selected = [];

          if (value) {
            this.staffs.content.forEach(function (staff) {
              selected.push(staff.id);
            });
          }
          this.selected = selected;

        }
      }
    }
};
</script>
      
      <style>
.staff-title {
  text-align: center;
  font-size: 34px;
  font-weight: 600;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen,
    Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
  background: rgba(255, 255, 255, 0.13);
  padding: 6px 0px;
}
.staff-body {
  background: linear-gradient(90deg, #e4c9ac 0%, rgba(255, 255, 255, 0) 100%),
    #e3c1d3;
  width: 100%;
  height: 100%;
}
.staff-table {
  text-align: center;
  margin-left: 35px;
  margin-right: 35px;
  z-index: 2;
}
.staff-table__content {
  text-align: center;
  padding: 0 50px;
  height: 100%;
  margin: 20px 0px;
}
.staff-table__content table {
  width: 100%;
  border-collapse: collapse;
}
.staff-table__content table tr {
  background: #f2e7ddf8;
}
.staff-table__content table tr th {
  border-right: 1px solid #e4c9ac;
  padding: 14px;
}
.staff-table__content table tr td {
  padding: 20px;
  line-height: 30px;
  height: 30px;
}
.staff-table__content table thead tr {
  background-color: #fdf9f8;
}
.staff-table__content table thead th {
  font-size: 14px;
  font-weight: 600;
}
.staff-table__content table tbody tr {
  border-bottom: 1px solid #e4c9ac;
}

.staff-table__content table tbody tr:hover {
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

.staff-button {
  margin-left: 200px;
  margin-top: 30px;
}
.staff-button__detail {
  color: white !important;
  font-size: 14px !important;
  font-weight: 600 !important;
}
.staff-button__detail:hover {
  background-color: rgba(255, 0, 0, 0.1) !important;
  border-color: rgba(255, 0, 0, 0.1) !important;
}
.staff-button__detail:focus {
  background-color: rgba(255, 0, 0, 0.2) !important;
  border-color: rgba(255, 0, 0, 0.2) !important;
}
.staff-button__detail:active {
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
.staff-table_content {
  overflow: auto;
  height: 100vh;
}
.staff-table__content table tr td {
  padding: 18px;
  line-height: 20px;
  height: 30px;

  border-right: 1px solid #e4c9ac;
}
.staff-table__content {
  text-align: center;
  padding: 0 50px;
  overflow: auto;
  height: 100%;
  margin: 0px 20px;
}
.staff-table_content {
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
.staff-header-table th{
  margin-top: 20px;
}
.demo-form-inline{
  width: 100% !important;
  margin-top: 20px;

}
.staff-full{
  width: 100% !important;
}
.staff2-title {
    text-align: center;
    font-size: 34px;
    font-weight: 600;
    font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen,
      Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
    background: rgba(255, 255, 255, 0.13);
    padding: 6px 0px;
  }
  .staff2-body {
    background: linear-gradient(90deg, #e4c9ac 0%, rgba(255, 255, 255, 0) 100%),
      #e3c1d3;
    width: 100%;
    height: 100%;
  }
  .staff2-table {
    text-align: center;
    margin-left: 35px;
    margin-right: 35px;
    z-index: 2;
  }
  .staff2-table__content {
    text-align: center;
    padding: 0 50px;
    height: 100%;
  }
  .staff2-table__content table {
    width: 100%;
    border-collapse: collapse;
  }
  .staff2-table__content table tr {
    background: #f2e7ddf8;
  }
  .staff2-table__content table tr th {
    border-right: 1px solid #e4c9ac;
    padding: 14px;
  }
  .staff2-table__content table tr td {
    padding: 20px;
    line-height: 30px;
    height: 30px;
  }
  .staff2-table__content table thead tr {
    background-color: #fdf9f8;
  }
  .staff2-table__content table thead th {
    font-size: 14px;
    font-weight: 600;
  }
  .staff2-table__content table tbody tr {
    border-bottom: 1px solid #e4c9ac;
  }
  
  .staff2-table__content table tbody tr:hover {
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
  
  .staff2-button {
    margin-left: 200px;
    margin-top: 30px;
  }
  .staff2-button__detail {
    color: white !important;
    font-size: 14px !important;
    font-weight: 600 !important;
  }
  .staff2-button__detail:hover {
    background-color: rgba(255, 0, 0, 0.1) !important;
    border-color: rgba(255, 0, 0, 0.1) !important;
  }
  .staff2-button__detail:focus {
    background-color: rgba(255, 0, 0, 0.2) !important;
    border-color: rgba(255, 0, 0, 0.2) !important;
  }
  .staff2-button__detail:active {
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
 
  .staff2-table__content table tr td {
    padding: 18px;
    line-height: 20px;
    height: 30px;
  
    border-right: 1px solid #e4c9ac;
  }
  .staff2-table__content {
    text-align: center;
    padding: 0 50px;
    overflow: auto;
    height: 100%;
    margin: 0px 20px;
  }
  .staff2-table_content {
    overflow: auto;
    height: 200vh;
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
  .staff2-header-table{
  
    margin-top: 20px;
  }
</style>
      