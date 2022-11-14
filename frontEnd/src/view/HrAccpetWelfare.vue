<template>
  <div class="pl-body">
    <div class="pl-content">
      <div class="pl-title"><strong>DANH SÁCH XÉT DUYỆT</strong></div>
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
    <el-dialog title="Lịch xử xét duyệt" :visible.sync="isHistory" width="53%" :before-close="handleClose">
      <span>Lịch sử xét duyệt của nhân viên {{ staff.name }}</span>
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
            <td>{{ index + 1 }}</td>
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
    <el-dialog :visible.sync="isWelfare" width="1000px" label-width="100px" top="5vh" left="150px"
      title="Danh sách phúc lợi đăng ký" boder="">
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
            <td>{{ index + 1 }}</td>
            <td style="text-align: left;">{{ item.name }}</td>
            <td style="text-align: right;">{{ formatCurrency(item.price) }} </td>
            <td>{{ item.quantity }}</td>
            <td>{{ formatCurrency(item.price * item.quantity) }} </td>
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
          this.loading()
        }
      } catch (error) {
        this.errorMessage = error
      }
    },
    handleReturn(id, index) {
      try {
        StaffService.ReturnRegisterWelfare(id)
        this.listHistory.splice(index, 1);
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
</style>