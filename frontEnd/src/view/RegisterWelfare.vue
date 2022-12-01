<template>
  <div class="registerWelfare-body">
    <div class="registerWelfare-content">
      <div class="registerWelfare-title"><strong>ĐĂNG KÍ PHÚC LỢI CÁ NHÂN</strong></div>
      <div class="registerWelfare-ele">
        <div class="registerWelfare-table">
          <div class="money">
            <div>
              Tiền đã đăng kí:&nbsp;<span style="color: green">{{
                  formatCurrency(sum)
              }}</span>
            </div>
            <div>
              Tổng tiền được đăng ký:&nbsp;<span style="color: red">{{
                  formatCurrency(total)
              }}&nbsp;</span>(Đã trừ tiền phúc lợi chung)
            </div>
            <div>
              Tiền còn lại:&nbsp;<span style="color: green">{{
                  formatCurrency(total - totalMoney)
              }}&nbsp;<span style="color: red" v-show="sum > total">(Lỗi)</span></span>
            </div>
          </div>
          <div class="registerWelfare-table__content">
            <form id="form" label-width="120px" class="scroll-table">
              <table>
                <thead>
                  <tr>
                    <th>Chọn</th>
                    <th>Tên phúc lợi</th>
                    <th>Mô tả</th>
                    <th>Đơn giá</th>
                    <th>Số lượng</th>
                    <th>Thành Tiền</th>
                    <!-- <th>Thao tác</th> -->
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="item in list" :key="item.id">
                    <!-- <td>{{ index + 1 }}</td> -->
                    <td>
                      <input type="checkbox" :value="item" v-model="selected" :ref="item.id"
                        :disabled="disableHandler(item, listQuantity[item.id])" />
                    </td>

                    <td>{{ item.name }}</td>
                    <td style="text-align: left">{{ item.text }}</td>
                    <td>{{ formatCurrency(item.price) }}</td>
                    <td>
                      <el-input-number v-model="listQuantity[item.id]" :disabled="isDisableNumberic(item)"
                        :max="preventAdd(item)" :min="1" v-show="!item.isQuantity" @change="handleChange"
                        @blur="handleBlur(item.id)">
                      </el-input-number>
                      <span v-show="item.isQuantity">Chỉ chọn 1</span>
                    </td>
                    <td style="text-align: right">
                      {{ formatCurrency(item.price * listQuantity[item.id]) }}
                    </td>
                  </tr>
                </tbody>
              </table>
            </form>
          </div>
        </div>
        <div class="registerWelfare-button">
          <el-popover placement="right" :width="220" trigger="hover" content="Đăng kí các phúc lợi đã tích ở trên">
            <template #reference>
              <el-button class="registerWelfare-button__detail btn btn-danger" @click="showDialogRegister()">Đăng Kí
                Phúc Lợi
              </el-button>
              <el-button class="registerWelfare-button__detail btn btn-danger" @click="toggleRegisterStatus()">Trạng
                thái
                đăng kí
              </el-button>
            </template>
          </el-popover>
        </div>
      </div>
    </div>
    <el-dialog title="Xác nhận" :visible.sync="centerDialogVisible" width="30%" center>
      <div style="text-align: center; margin-top: 10px">
        Bạn có chắc chắn muốn chọn các phúc lợi trên?
      </div>
      <div style="text-align: center; margin-top: 10px; color: red">
        Phúc lợi chỉ được đăng kí 1 lần 1 năm
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="centerDialogVisible = false">Hủy</el-button>
        <el-button type="primary" @click.prevent="registerWelfares" style="background-color: red; border: none">Xác
          nhận
        </el-button>
      </span>
    </el-dialog>
    <el-dialog title="Xác nhận" :visible.sync="dialogVisibleCheckNull" width="25%" center>
      <div style="text-align: center; margin-top: 5px">
        Bạn chưa chọn bất kì phúc lợi nào!!!
      </div>
      <div style="text-align: center; margin-top: 10px; color: red">
        Hãy đăng kí phúc lợi trước
      </div>
      <span slot="footer" class="dialog-footer">
        <!-- <el-button @click="centerDialogVisible = false">Hủy</el-button> -->
        <el-button type="primary" @click.prevent="dialogVisibleCheckNull = false"
          style="background-color: red; border: none">OK
        </el-button>
      </span>
    </el-dialog>
    <el-dialog :visible.sync="isShowRegisterStatus" width="900px" label-width="100px" top="5vh" left="150px"
      title="Trạng thái các phúc lợi đăng kí" boder="">
      <div label-width="120px" class="registerStatus-table__content">
        <table>
          <thead>
            <tr>
              <!-- <th colspan="1">STT</th> -->
              <th>Tên phúc lợi</th>
              <th>Đơn giá</th>
              <th>Số lượng</th>
              <th>Thành tiền</th>
              <th>Trạng Thái</th>
            </tr>
          </thead>
          <tbody>
            <!-- <tr>
              <td style="font-weight: bold">I</td>
              <td colspan="3" style="font-weight: bold; text-align: center;">Phúc Lợi Chung</td>
              <td><strong>2</strong></td>
            </tr> -->
            <!-- <tr>
              <td style="font-weight: bold">1.</td>
              <td>test</td>
              <td>1212</td>
              <td>1</td>
              <td>
                12
              </td>
            </tr> -->
            <!-- <tr>
             <td style="font-weight: bold">I</td> 
               <td></td>
              <td colspan="3" style="font-weight: bold;  text-align: center;">Phúc Lợi Cá Nhân Hóa</td>
              <td><strong>2</strong></td> -->
            <!-- </tr> -->
            <tr v-for="item in statusRegister" :key="item.id">
              <!-- <td style="font-weight: bold">1.</td> -->
              <td>{{ item.name }}</td>
              <td>{{ item.price }}</td>
              <td>{{ item.quantity }}</td>
              <td>
                {{ formatCurrency(item.price * item.quantity) }}
              </td>
              <td v-if="item.status == 2">Chờ duyệt</td>
              <td v-if="item.status == 1">Hủy bỏ</td>
            </tr>
          </tbody>
        </table>
      </div>
    </el-dialog>
  </div>
</template>
  
<script>
/* eslint-disable */
import WelfareApi from "@/service/phucLoiService";

let welfareApi = new WelfareApi();
export default {
  name: "PhucLoiList",
 
  data() {
    return {
      selected: [],
      list: [],
      userName: "",
      sum: 0,
      userId: 0,
      centerDialogVisible: false,
      dialogVisibleCheckNull: false,
      total: 0,
      listQuantity: [],
      nameStaff: "",
      listOnlyOne: [],
      newVal: '',
      oldVal: '',
      isShowRegisterStatus: false,
      statusRegister: []
    };
  },
  computed: {
    totalMoney() {
      if (this.selected.length > 0) {
        return (
          this.selected
            .map((x) => x.price * this.listQuantity[x.id])
            .reduce((total, x) => total + x) + this.sum
        );
      } else {
        return this.sum;
      }
    },
  },

  methods: {
    toggleRegisterStatus() {
      this.isShowRegisterStatus = !this.isShowRegisterStatus;
    },
    handleChange(newVal, oldVal) {
      this.newVal = newVal
      this.oldVal = oldVal
    },
    handleBlur(itemId) {
      const self = this;
      if (self.newVal === undefined || self.total - self.totalMoney < 0) {
        self.listQuantity[itemId] = self.oldVal;
        self.$forceUpdate();
      }
    },
    preventAdd(item) {
      if (this.total - this.totalMoney < 0) {
        return this.listQuantity[item.id] - 1;
      } else {
        return this.listQuantity[item.id] + 1;
      }
    },
    showDialogRegister() {
      if (this.selected.length === 0) {
        this.dialogVisibleCheckNull = true;
      } else {
        this.centerDialogVisible = true;
      }
    },
    isDisableNumberic(item) {
      return (
        this.selected.findIndex((x) => x.id == item.id) == -1 || item.isQuantity
      );
    },
    disableHandler(item, quantity) {
      return (
        (this.totalMoney + item.price * quantity > this.total &&
          !this.selected.includes(item)) || this.listOnlyOne.includes(item.id)
      );
    },
    formatCurrency(value) {
      return Intl.NumberFormat("vi-VN", {
        style: "currency",
        currency: "VND",
      }).format(value);
    },
    async registerWelfares() {
      let object = {
        id: this.userId,
        list: [],
      };

      for (let i = 0; i < this.selected.length; i++) {
        let welfare_object = {
          id: "",
          quantity: "",
        };
        //id
        welfare_object.id = this.selected[i].id;
        

        //so luong
        welfare_object.quantity = this.listQuantity[this.selected[i].id];
        

        //in ra object
        object.list.push(welfare_object);
        
      }
      

      const res = await welfareApi.registerWelfare(object);
      
      if (res.status == 201) {
        this.sum = this.totalMoney;
        this.selected = [];
        this.totalMoney = 0;
        this.$notify({
          title: "Success",
          message: "Đăng kí phúc lợi thành công",
          type: "success",
        });
        const myTimeout1 = setTimeout(myGreeting1, 100);

        function myGreeting1() {
          window.location.reload();
        }
      }
      if (res.status == 200) {
        this.sum = this.totalMoney;
        this.selected = [];
        this.totalMoney = 0;
        this.$notify.info({
          title: "Error",
          message: "Đăng kí phúc lợi thất bại",
        });
        const myTimeout = setTimeout(myGreeting, 100);

        function myGreeting() {
          window.location.reload();
        }
      }
      this.centerDialogVisible = false;
    },
  },
  created() {
    // for(var i = this.list[0].id; i <= this.list.length; i++){
    //     this.listQuantity[i] = 1;
    // }
    if (localStorage.getItem("user")) {
      this.userName = JSON.parse(localStorage.getItem("user")).userName;

    }
    welfareApi.getAllWelfareByStatus().then((res) => {
      this.list = res.data;
      // console.log(this.list[0].id)
      // console.log(this.list[this.list.length - 1].id)
      for (
        var i = this.list[0].id;
        i <= this.list[this.list.length - 1].id;
        i++
      ) {
        this.listQuantity[i] = 1;
      }
    });
    welfareApi.findID(this.userName).then((x) => {
      this.userId = x.data;
      welfareApi.getAllWelfareWithQuantity(x.data).then((x) => {
        x.data.forEach((item) => {
          if (item.status !== 1) {
            this.sum += item.price * item.quantity;
          }
        });
      });
      welfareApi.getOnlyOneWelfareOfUser(this.userId).then((x) => {
        this.listOnlyOne = x.data;
        
      });
      welfareApi.getStatusAcceptWelfareOfUser(this.userId).then((x) => {
        this.statusRegister = x.data;
      });
    });
    welfareApi.getTotalMoney(this.userName).then((x) => {
      this.total = x.data;
    
      welfareApi.getTotalMoneyGeneralWelfare().then((res) => {
        
        this.total = this.total - res.data;
        
      });
    });
  }

};
</script>
  
<style scoped>
@import "@/assets/css/welfare/register.css";

.registerStatus-table__content table {
  width: 100%;
  border-collapse: collapse;
  max-height: 600px;
}

.registerStatus-table__content table tr {
  background: rgba(217, 217, 217, 0.13);
}

.registerStatus-table__content table tr th {
  border-right: 1px solid #c7c7c7;
  padding: 14px;
}

.registerStatus-table__content table tr td {
  padding: 20px;
  line-height: 30px;
  height: 30px;
}

.registerStatus-table__content table thead tr {
  background-color: #d9d9d9;
  position: sticky;
  top: 0;
  z-index: 10;
}

.registerStatus-table__content table thead th {
  font-size: 14px;
  font-weight: 600;
}

.registerStatus-table__content table tr td {
  border-right: 1px solid #c7c7c7;
}

.registerStatus-table__content table tbody tr {
  border-bottom: 1px solid #c7c7c7;
}

.registerStatus-table__content table tbody tr:hover {
  background-color: pink;
}
</style>
  