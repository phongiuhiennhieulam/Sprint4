<template>
    <div>
      <!-- dialog xet duyet -->
        <span slot="title" class="title-dialog"><strong>Danh sách phúc lợi đăng ký</strong> </span>
        {{dialongKey}}
        <div class="row">
          <div class="col-6">
            <strong>
              <h5>Nhân viên: {{ staff.name }}</h5>
            </strong>
          </div>
          <div class="col-6" style="text-align: right; margin-bottom: 5px;">
            <el-button @click="handShowHistory(staff.id)" type="warning"><strong><i class="el-icon-s-order"></i> Lịch
                sử</strong></el-button>
          </div>
        </div>
        <div label-width="120px" class="hrAccW-table__content">
          <table class="text-table">
            <thead>
              <tr>
                <th width="9%">STT</th>
                <th>Tên phúc lợi </th>
                <th width="14%">Đơn giá</th>
                <th width="8%">Số lượng</th>
                <th width="14%">Tổng tiền</th>
                <th width="16%">Trạng thái</th>
                <th width="12%">Thao tác</th>
              </tr>
            </thead>
            <tr v-for="(item, index) in listRegister" :key="item.id">
              <td>{{ index + 1 }}</td>
              <td style="text-align: left;">{{ item.name }}</td>
              <td style="text-align: right;">{{ formatCurrency(item.price) }} </td>
              <td>{{ item.quantity }}</td>
              <td  style="text-align: right;">{{ formatCurrency(item.price * item.quantity) }} </td>
              <td>
                <div :key="dialongKey">
                  <span v-if="item.status==0" style="color: seagreen;">
                    Đã duyệt
                  </span>
                  <span v-if="item.status==1" style="color: red;">
                    Đã hủy
                  </span>
                  <span v-if="item.status==2">
                    Đang chờ
                  </span>
                </div>
              </td>
              <td>
                <div class="d-flex">
                  <el-button @click="handleSuccess(item.id, index)" type="success" icon="el-icon-check" circle></el-button>
                  <el-button @click="handleDelete(item.id, index)" type="danger" icon="el-icon-close" circle></el-button>
                  <el-button @click="handleReturn(item.id, index)" type="warning" icon="el-icon-refresh-left" circle></el-button>
                </div>
              </td>
            </tr>
          </table>
        </div>
    </div>
</template>
  
<script>
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
        idDelete: "",
        isWelfare: false,
        staff: {},
        listRegister: [],
        listHistory: [],
        isHistory: false,
        dialongKey: 0
      };
    },
    methods: {
      showAddForm() {
        this.edit = {};
        this.isShowAdd = true;
      },
      loadingComponent(){
        this.dialongKey += 1;
      },
      handleDelete(id) {
        try {
          StaffService.DeleteRegisterWelfare(id)
          this.$notify.info({
            title: 'notification',
            message: 'Từ chối thành công'
          });
          if (this.listRegister.length < 1) {
            this.$confirm(
              "Bạn có chắc sẽ chốt danh sách này không. Continue?",
              "Warning!",
              {
                confirmButtonText: "Có",
                cancelButtonText: "Hủy",
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
      handleSuccess(id) {
        try {
          StaffService.SuccessRegisterWelfare(id)
          this.loadingComponent()
          this.$notify({
            title: 'Success',
            message: 'Xét duyệt',
            type: 'success'
          });
          if (this.listRegister.length < 1) {
            this.$confirm(
              "Bạn có chắc sẽ chốt danh sách này không. Continue?",
              "Warning!",
              {
                confirmButtonText: "Có",
                cancelButtonText: "Hủy",
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
      handleReturn(id, index) {
        try {
          StaffService.ReturnRegisterWelfare(id)
          this.listHistory.splice(index, 1);
          // this.loading()
          this.$notify({
            title: 'Success',
            message: 'Hoàn tác thành công',
            type: 'success'
          });
        } catch (error) {
          this.errorMessage = error
        }
      },
      handShow2(id) {
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
            this.listStaff = _.unionBy(this.list, 'staff.id')
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
      loading() {
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
  
    created() {
      this.getAll()
    },
    mounted() {
      this.getAll()
    }
  };
  </script>
  <style scoped>
  @import "@/assets/css/hr/accept.css";
  .hrAccW-table__content table {
    width: 100%;
    border-collapse: collapse;
    max-height: 600px;
  }
  
  .hrAccW-table__content table tr {
    background: rgba(217, 217, 217, 0.13);
  }
  
  .hrAccW-table__content table tr th {
    border-right: 1px solid #c7c7c7;
    padding: 14px;
    white-space: nowrap;
  }
  
  .hrAccW-table__content table tr td {
    padding: 20px;
    line-height: 30px;
    height: 30px;
    white-space: nowrap;
  }
  
  .hrAccW-table__content table thead tr {
    background-color: #d9d9d9;
    position: sticky;
    top: 0;
  }
  
  .hrAccW-table__content table thead th {
    font-size: 14px;
    font-weight: 600;
    
  }
  
  .hrAccW-table__content table tr td {
    border-right: 1px solid #c7c7c7;
  }
  
  .hrAccW-table__content table tbody tr {
    border-bottom: 1px solid #c7c7c7;
  }
  
  .hrAccW-table__content table tbody tr:hover {
    background-color: pink;
  }
  .text-table  th{
   font-size: 35px;
  }
  .text-table  td{
   font-size: 19px;
  }
  </style>
  