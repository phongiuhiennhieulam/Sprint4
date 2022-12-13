<template>
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
                      <td>{{x.money}}</td>
                      <td>
                        <el-button @click="handleReturn(item.id, index)" type="warning" icon="el-icon-refresh-left" circle>
                        </el-button>
                      </td>
                    </tr>
                  </tbody>
                </table>
               <div style="margin-top: 10px;">
                <el-button class="btn btn-danger" v-loading.fullscreen.lock="fullscreenLoading"
                    @click="getPush()">
                    <i class="el-icon-top"></i> Gửi yêu cầu xét duyệt {{ok}}
                  </el-button>
               </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
</template>
      
  <script>
  import StaffService from "@/service/hrService"

  export default {
    name: "UserList",
    props: ["ok"],
    data() {

      return {
        isList: false,
        list: [],
        selected: []
      };
    },
    methods: {
      getAll() {
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
      }
    },
    created(){
      this.getAll()
    }
   
  };
  </script>
        
        <style>
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
        