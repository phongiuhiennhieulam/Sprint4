<template>
  <div class="pl-body">
    <div class="pl-content">
      <div class="hr-title"><strong>DANH SÁCH XÉT DUYỆT</strong></div>
      <br>
    
      <div class="pl-ele">
        <div class="pl-table">
          <div class="pl-table__content">
            <form id="form" label-width="100px">
              <el-button style="float: left; margin-bottom: 5px;" @click="handShowHistory()" type="warning"><i class="el-icon-s-order"><Strong>Lịch sử</Strong></i>
              </el-button>
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
                  <tr v-for="(item, index) in list" :key="item.id">
                    <td width="9%">{{ index + 1 }}</td>
                    <td style="text-align: left;">{{ item.name }}</td>
                    <td style="text-align: left;">{{ item.code }}</td>
                    <td>
                      <el-button @click="handShow(item.id)" type="warning"><strong>Danh sách xét duyệt</strong>
                      </el-button>
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

    <el-dialog title="temp" :visible.sync="isHistory" width="59%" :before-close="handleClose">
      <span slot="title" class="title-dialog"><strong>Lịch xử xét duyệt</strong> </span>
      <div label-width="120px" class="hrAccW-table__content">
        <table>
          <thead>
            <tr>
              <th width="9%">STT</th>
              <th>Mã NV</th>
              <th>Họ tên NV</th>
              <th width="20%">Tên phúc lợi</th>
              <th width="12%">Đơn giá</th>
              <th width="10%">Số lượng</th>
              <th width="12%">Tổng tiền</th>
              <th wtdth="10%">Trạng thái</th>
              <th width="13%">Hoàn tác</th>
            </tr>
          </thead>
          <tr v-for="(item, index) in listHistory" :key="item.name">
            <td>{{ index + 1 }}</td>
            <td style="text-align: left;">{{ item.code }}</td>
            <td style="text-align: left;">{{ item.userName }}</td>
            <td style="text-align: left;">{{ item.name }}</td>
            <td style="text-align: right;">{{ formatCurrency(item.price) }} </td>
            <td>{{ item.quantity }}</td>
            <td style="text-align: right;">{{ formatCurrency(item.price * item.quantity) }} </td>
            <td>
              <span v-show="item.status == 0">
                <Strong style="color: seagreen;">Đã xét duyệt</Strong>
              </span>
              <span v-show="item.status == 1">
                <Strong style="color: red">Đã hủy</Strong>
              </span>
            </td>
            <td>
              <el-button @click="handleReturn(item.id, index)" type="warning" icon="el-icon-refresh-left" circle>
              </el-button>
            </td>
          </tr>
        </table>
      </div>
    </el-dialog>
    <!-- dialog xet duyet -->
    <el-dialog :visible.sync="isWelfare" width="1000px" label-width="100px" top="5vh" left="150px" title="temp"
      boder="">
      <span slot="title" class="title-dialog"><strong>Danh sách phúc lợi đăng ký</strong> </span>
      <div class="row">
        <div class="col-6">
          <strong>
            <h5>Nhân viên: {{ staff.name }}</h5>
          </strong>
        </div>
        <!-- <div class="col-6" style="text-align: right; margin-bottom: 5px;">
          <el-button @click="handShowHistory()" type="warning"><strong><i class="el-icon-s-order"></i> Lịch
              sử</strong></el-button>
        </div> -->
      </div>
      <div label-width="120px" class="hrAccW-table__content">
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
          <tr v-for="(item, index) in listRegister" :key="item.id">
            <td>{{ index + 1 }}</td>
            <td style="text-align: left;">{{ item.name }}</td>
            <td style="text-align: right;">{{ formatCurrency(item.price) }} </td>
            <td>{{ item.quantity }}</td>
            <td>{{ formatCurrency(item.price * item.quantity) }} </td>
            <td>
              <div class="d-flex">
                <el-button @click="handleSuccess(item.id, index)" type="success" icon="el-icon-check" circle>
                </el-button>
                <el-button @click="handleDelete(item.id, index)" type="danger" icon="el-icon-close" circle></el-button>
              </div>
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
      idDelete: "",
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
    handleDelete(id, index) {
      try {
        StaffService.DeleteRegisterWelfare(id)
        this.listRegister.splice(index, 1);
        this.$notify.info({
          title: 'notification',
          message: 'Từ chối thành công'
        });
        if (this.listRegister.length < 1) {
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

      
    handleSuccess(id, index) {
      try {
        StaffService.SuccessRegisterWelfare(id)
        this.listRegister.splice(index, 1);
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
    handShowHistory() {
      this.isHistory = true
      welfareApi.getHistoryAcceptWelfareOfUser()
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
</style>