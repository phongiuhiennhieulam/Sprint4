<template>
  <div class="plHome-main">
    <div class="plHome-container">
      <div class="plHome-content">
        <div id="title">Danh Sách Phúc Lợi Của {{ staff.name }}</div>
        <div label-width="120px" class="plHome-table__content">
          <table>
            <thead>
              <tr>
                <th>STT</th>
                <th>Loại Phúc Lợi</th>
                <th>Tên phúc lợi</th>
                <th>Đơn giá(VNĐ)</th>
                <th>Số lượng</th>
                <th>Thành tiền(VNĐ)</th>
                <th>Thao tác</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td style="font-weight: bold">I</td>
                <td style="font-weight: bold">Phúc Lợi Chung</td>
                <td></td>
                <td></td>
                <td><strong>{{ listGeneral.length }}</strong></td>
                <!-- <td  style="font-weight: bold"><el-button slot="reference" class="btn btn-success"
                      >Được Duyệt</el-button
                    ></td> -->
                <td style="font-weight: bold">Tổng:&nbsp;{{ formatCurrency(sumListGeneral) }}</td>
                <td></td>
              </tr>
              <tr v-for="(item, index) in listGeneral" :key="index">
                <td style="font-weight: bold">{{ index + 1 }}.</td>
                <td></td>
                <td>{{ item.name }}</td>
                <td>{{ formatCurrency(item.price) }}</td>
                <td>1</td>
                <td>{{ formatCurrency(item.price) }}</td>
                <td>
                  <el-popover placement="right" width="250" trigger="hover" :content="item.text">
                    <el-button slot="reference" class="btn btn-warning">Xem mô tả</el-button>
                  </el-popover>
                </td>
              </tr>
              <tr>
                <td style="font-weight: bold">II</td>
                <td style="font-weight: bold">Phúc Lợi Cá Nhân Hóa</td>
                <td></td>
                <td></td>
                <td><strong>{{ listRegister.length }}</strong></td>
                <td style="font-weight: bold">Tổng:&nbsp;{{ formatCurrency(sumListRegister) }}</td>
                <td></td>
              </tr>
              <tr v-for="(item, index) in listRegister" :key="index">
                <td style="font-weight: bold">{{ index + 1 }}.</td>
                <td></td>
                <td>{{ item.name }}</td>
                <td>{{ formatCurrency(item.price) }}</td>
                <td>{{ item.quantity }}</td>
                <td>
                  <!-- <div v-if="item.status==2">
                    <el-button class="btn btn-info">Đang Chờ</el-button>
                  </div>
                  <div v-if="item.status==1 || item.status == 0">
                    <el-button :class="item.status==1?'btn btn-danger':'btn btn-success'">{{item.status == 1 ? 'Hủy Duyệt':'Đã Duyệt'}}</el-button>
                  </div> -->
                  {{ formatCurrency(item.quantity * item.price) }}
                </td>
                <td>
                  <el-popover placement="right" width="250" trigger="hover" :content="item.text">
                    <el-button slot="reference" class="btn btn-warning">Xem mô tả</el-button>
                  </el-popover>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <!-- <pre>{{listGeneral}}</pre>
        <pre>{{listRegister}}</pre> -->
      </div>

      <!-- <pre>{{ userName }}</pre>
    <pre>{{ idUser }}</pre> -->
    </div>
  </div>
</template>
  
<script>
/* eslint-disable */
import WelfareApi from "@/service/phucLoiService";
import StaffService from '../service/hrService'
let welfareApi = new WelfareApi();
export default {
  name: "PhucLoiHome",
  data() {
    return {
      listGeneral: [],
      listRegister: [],
      userName: "",
      idUser: "",
      text: "",
      sumListGeneral: 0,
      sumListRegister: 0,
      staff: {}
    };
  },
  methods: {
    formatCurrency(value) {
      return Intl.NumberFormat("vi-VN", {
        style: "currency",
        currency: "VND",
      }).format(value);
    },
    formatDate(timestamp) {
      const date = new Date(timestamp);
      let day = date.getDate();
      day = day < 10 ? `0${day}` : day;
      let month = date.getMonth() + 1;
      month = month < 10 ? `0${month}` : month;
      let year = date.getFullYear();
      return `${day}/${month}/${year}`;
    },
    async getIdUserName() {
      await welfareApi.findID(this.userName).then((res) => {
        // self.isLoaded = true;
        this.idUser = res.data;
        // console.log(res.data);
        // console.log(this.listGeneral);
        console.log(this.idUser);
      });
    },
    async getAllWelfare() {
      await welfareApi.getCurrentWelfareWithQuantity(this.idUser).then((res) => {
        this.listRegister = res.data;
        console.log(this.listRegister);
        // console.log(this.sumListRegister)
        for (let j = 0; j < this.listRegister.length; j++) {
          this.sumListRegister += this.listRegister[j].price * this.listRegister[j].quantity;
        }
        this.listRegister.sort(
          (firstItem, secondItem) => firstItem.status - secondItem.status
        );
      });
    },
    async getAllGeneralWelfares() {
      await welfareApi.getAllGeneralWelfare().then((res) => {
        // self.isLoaded = true;
        this.listGeneral = res.data;
        // console.log(res.data);
        // console.log(this.listGeneral);
        for (let i = 0; i < this.listGeneral.length; i++) {
          this.sumListGeneral += this.listGeneral[i].price;
        }
        console.log(this.sumListGeneral);
      });
    },
    getStaff(email) {
      StaffService.getStaffByEmail(email)
        .then(Response => {
          this.staff = Response.data
          console.log(this.staff);
        })
    }
  },

  created() {
    if (localStorage.getItem("user")) {
      this.userName = JSON.parse(localStorage.getItem("user")).userName;
      this.getStaff(this.userName)
      console.log(this.userName);
      welfareApi.findID(this.userName).then((res) => {
        this.idUser = res.data;
        console.log(this.idUser);
        this.getAllGeneralWelfares();
        this.getAllWelfare();
      });
    }
    console.log("before");
  },
};
</script>
  
<style scoped>
@import "@/assets/css/phucloi/home.css";
</style>
  