<template>
  <div class="pl-body">
    <div class="pl-content">
      <div class="hr-title"><strong >DANH SÁCH XÉT DUYỆT</strong></div>
      <div class="pl-ele">
        <div class="pl-table">
          <div class="pl-table__content">
            <form id="form" label-width="100px">
              <table>
                <thead>
                  <tr>
                    <th>STT</th>
                    <th>Họ tên </th>
                    <th>Mã nhân viên</th>
                    <th>Danh sách đăng ký </th>
                   
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(item, index) in list" :key="index">
                    <td width="9%">{{ index + 1}}</td>
                    <td style="text-align: left;">{{ item.name }}</td>
                    <td style="text-align: left;">{{ item.code }}</td>
                    <td>
                      <el-button @click="handShow(item.id)" type="warning"><strong>Danh sách xét duyệt</strong></el-button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </form>
          </div>
        </div>
      </div>
    </div>
    <!-- dialog lịch sử xet duyet -->
    <el-dialog
      title="temp"
      :visible.sync="isHistory"
      width="53%"
      :before-close="handleClose">
      <span slot="title" class="title-dialog"><strong>Lịch xử xét duyệt</strong> </span>
      <span>Lịch sử xét duyệt của nhân viên {{staff.name}}</span>
      <div label-width="120px" class="pl-table__content">
      <table>
          <thead>
                  <tr>
                    <th width="9%">STT</th>
                    <th width="20%">Tên phúc lợi</th>
                    <th width="12%">Đơn giá</th>
                    <th width="10%">Số lượng</th>
                    <th width="12%">Tổng tiền</th>
                    <th wtdth="10%">Trạng thái</th>
                    <th width="13%">Hoàn tác</th>
                  </tr>
          </thead>
          <tr v-for="(item, index) in listHistory" :key="index">
              <td>{{ index + 1}}</td>
              <td style="text-align: left;">{{ item.name }}</td>
              <td style="text-align: right;">{{ formatCurrency(item.price) }} </td>
              <td>{{item.quantity}}</td>
              <td style="text-align: right;">{{ formatCurrency(item.price*item.quantity) }} </td>
              <td>
                <span v-show="item.status == 0">
                  <Strong style="color: seagreen;">Đã xét duyệt</Strong>
                </span>
                <span  v-show="item.status == 1">
                  <Strong style="color: red">Đã hủy</Strong>
                </span>
              </td>
              <td>
                <el-button @click="handleReturn(item.id, index)" type="warning" icon="el-icon-refresh-left" circle></el-button>
              </td>
          </tr> 
      </table>    
    </div>
    </el-dialog>
    <!-- dialog xet duyet -->
    <el-dialog
      :visible.sync="isWelfare"
      width="1000px"
      label-width="100px"
      top="5vh"
      left="150px"
      title="temp"
      boder=""
    >
    <span slot="title" class="title-dialog"><strong>Danh sách phúc lợi đăng ký</strong> </span>
    <div class="row">
      <div class="col-6">
        <strong><h5>Nhân viên: {{staff.name}}</h5></strong>
      </div>
      <div class="col-6" style="text-align: right; margin-bottom: 5px;">
        <el-button @click="handShowHistory(staff.id)" type="warning"><strong><i class="el-icon-s-order"></i> Lịch sử</strong></el-button>
      </div>
    </div>
    <div label-width="120px" class="pl-table__content">
      <table>
          <thead>
                  <tr>
                    <th width="9%">STT</th>
                    <th>Tên phúc lợi </th>
                    <th width="12%">Đơn giá</th>
                    <th width="10%">Số lượng</th>
                    <th width="15%">Tổng tiền</th>
                    <th width="15%">Thao tác</th>
                  </tr>
          </thead>
          <tr v-for="(item, index) in listRegister" :key="index">
              <td>{{ index + 1}}</td>
              <td style="text-align: left;">{{ item.name }}</td>
              <td style="text-align: right;">{{ formatCurrency(item.price) }} </td>
              <td>{{item.quantity}}</td>
              <td>{{ formatCurrency(item.price*item.quantity) }} </td>
              <td>
                <el-button @click="handleSuccess(item.id, index)" type="success" icon="el-icon-check" circle></el-button>
                <el-button @click="handleDelete(item.id, index)" type="danger" icon="el-icon-close" circle></el-button>
              </td>
          </tr> 
      </table>    
    </div>  
    </el-dialog>
  </div>
</template>

<script>
/* eslint-disable */
import StaffService from "../service/hrService";
import WelfareApi from "@/service/phucLoiService";
let welfareApi = new WelfareApi();
import _ from 'lodash'
export default {
  name: "PhucLoiList",
  data() {
    return {
      value: "",
      isShowEdit: false,
      isShowDialog: false,
      isShowAdd: false,
      list: [],
      listStaff: [],
      idDelete:"",
      isWelfare: false,
      staff: {},
      listRegister: [],
      listHistory: [],
      isHistory: false
      
    };
  },
  methods: {
    showAddForm() {
      
      this.edit = {};
      this.isShowAdd = true;
    },
    handleDelete (id,index) {
        try {
          StaffService.DeleteRegisterWelfare(id)
          this.listRegister.splice(index,1);
          this.$notify.info({
          title: 'notification',
          message: 'Từ chối thành công'
        });
        if(this.listRegister.length < 1){
          this.$confirm(
            "Bạn có chắc sẽ chốt danh sách này không. Continue?",
            "Warning!",
          {
            confirmButtonText: "OK",
            cancelButtonText: "Cancel",
            type: "warning",
        }
      ).then(() => {
        this.loading()
      })
        }
        } catch (error) {
          this.errorMessage = error
        }
    },
    handleSuccess (id,index) {

        try {
          StaffService.SuccessRegisterWelfare(id)
          this.listRegister.splice(index,1);
          this.$notify({  
          title: 'Success',
          message: 'Xét duyệt',
          type: 'success'
        });
        if(this.listRegister.length < 1){
          this.$confirm(
            "Bạn có chắc sẽ chốt danh sách này không. Continue?",
            "Warning!",
          {
            confirmButtonText: "OK",
            cancelButtonText: "Cancel",
            type: "warning",
        }
          ).then(() => {
            this.loading()
          })
        }
        } catch (error) {
          this.errorMessage = error
        }
        
    },
    handleReturn (id,index) {
        try {
          StaffService.ReturnRegisterWelfare(id)
          this.listHistory.splice(index,1);
          this.loading()
          this.$notify({  
          title: 'Success',
          message: 'Hoàn tác thành công',
          type: 'success'
        });
        } catch (error) {
          this.errorMessage = error
        }
    },
    handShow(id) {
      this.isWelfare = true
      welfareApi.getAcceptWelfareOfUser(id)
        .then((response) => {
          this.listRegister = response.data;
          console.log(response.data);
      });
      StaffService.getStaff(id)
      .then((response) => {
          this.staff = response.data;
      });
    },
    handShowHistory(id) {
      this.isHistory = true
      welfareApi.getHistoryAcceptWelfareOfUser(id)
        .then((response) => {
          this.listHistory = response.data;
          console.log(response.data);
      });
      StaffService.getStaff(id)
      .then((response) => {
          this.staff = response.data;
      });
    },
    getAll() {
      StaffService.getRegisterWelfare()
      .then(response => {
        this.list = response.data
        console.log(this.list)
        this.listStaff =  _.unionBy (this.list, 'staff.id')
        console.log(this.listStaff)
      
      })
      .catch(e => {
          console.log(e)
        })
    },
    formatCurrency(value) {
      return Intl.NumberFormat("vi-VN", {
        style: "currency",
        currency: "VND",
      }).format(value);
    },
    loading(){
      const loading = this.$loading({
          lock: true,
          text: 'Loading',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
        setTimeout(() => {
          loading.close();
          this.$router.go()
        }, 1200);
    },

  },
 
  created(){
    this.getAll()
  },
  mounted(){
    this.getAll()
  
  }
  
};
</script>l

<style>
.pl-title {
  text-align: center;
  font-size: 34px;
  font-weight: 600;
  font-family: "Poppins";
  background: rgba(255, 255, 255, 0.13);
  padding: 6px 0px;
}
.pl-body {
  background: linear-gradient(90deg, #e4c9ac 0%, rgba(255, 255, 255, 0) 100%),
    #e3c1d3;
  width: 100%;
  height: 100%;
}
.pl-table {
  text-align: center;
  margin-left: 200px;
  margin-right: 200px;
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
input,
textarea {
  outline: none;
  padding: 20px;
  width: 60%;
  background: transparent;
  border: none;
  border-bottom: 1px solid #000;
}
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}
[placeholder]:focus::-webkit-input-placeholder {
  transition: text-indent 0.4s 0.4s ease;
  text-indent: -100%;
  opacity: 1;
}
.pl-button {
  margin-left: 200px;
  margin-top: 30px;
}
.pl-button__detail {
  color: #f00 !important;
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
</style>