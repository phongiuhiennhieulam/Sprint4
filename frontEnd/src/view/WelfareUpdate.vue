<template>
  <div>
    <tr>
      <th>
        <div>
          <el-form :inline="true" class="input">
            <el-form-item>
              <el-input placeholder="Nhập tên người cập nhật" style="width: 350px">
                <i slot="prefix" class="el-input__icon el-icon-search"></i></el-input>
            </el-form-item>

            <el-form-item>
              <el-button type="warning">Tìm kiếm</el-button>
            </el-form-item>

            <el-form-item>
              <el-button round class="acceptMoney" @click="acceptAll"><strong>Chấp thuận</strong>
              </el-button>
            </el-form-item>

            <el-form-item>
              <el-button round class="cancelMoney" @click="denyAll"><strong>Hủy bỏ</strong>
              </el-button>
            </el-form-item>

          </el-form>
        </div>
      </th>
    </tr>
    <div class="welfare-table_content">
      <table>
        <thead>
          <tr class="welfare-table_row">
            <th width="50px">
              <input type="checkbox" @click="selectAll" v-model="allSelected" />
            </th>
            <th width="320px">Tên phúc lợi</th>
            <th width="120px">Năm</th>
            <th width="280px">Người cập nhật</th>
            <th width="130px">Loại phúc lợi</th>

            <th width="240px">Số tiền ban đầu</th>
            <th width="240px">Số tiền thay đổi</th>
            <th width="130px">Trạng thái</th>

            <th width="200px">Thao tác</th>
          </tr>
        </thead>

        <tbody class="welfare-body">
          <tr v-for="item in list" :key="item.id">
            <td>
              <input type="checkbox" v-model="listRequest" :value="item" />
            </td>
            <td style="text-align: left">{{ item.name }}</td>
            <td style="text-align: center">{{ item.year }}</td>
            <td style="text-align: center">{{ item.nameStaff }}</td>
            <td style="text-align: center;color:green;" v-if="item.is_general == 1">
              Chung
            </td>
            <td style="text-align: center;color:goldenrod;" v-if="item.is_general == 0">
              Cá nhân
            </td>
            <td style="text-align: center" v-if="item.price === null">
              Không có
            </td>
            <td style="text-align: center" v-if="item.price != null">
              {{ formatCurrency(item.price) }}
            </td>

            <td style="text-align: center">
              {{ formatCurrency(item.money_update) }}
            </td>
            <td style="text-align: center;color:green;" v-if="item.price === null">
              Thêm mới
            </td>
            <td style="text-align: center;color:goldenrod;" v-if="item.price != null">
              Chỉnh sửa
            </td>

            <td style="text-align: center">
              <span class="icon-submit">
                <el-button @click="handleSuccess(item)" type="success" icon="el-icon-check" circle>
                </el-button>
                &ensp;
                <el-button @click="handleDeny(item.id)" type="danger" icon="el-icon-close" circle></el-button>
              </span>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import WelfareApi from "@/service/phucLoiService";
let welfareApi = new WelfareApi();
export default {
  name: "WelfareUpdate",
  data() {
    return {
      list: [],
      dialogAccept: false,
      dialogDeny: false,
      allSelected: false,
      listRequest: [],
      listSubmit: []
    };
  },
  methods: {
    notifyAll(title,message,type) {
      return  this.$notify({
        title: title,
        message: message,
        type: type,
      });
    },
    confirmAll(message,type){
      return this.$confirm(message, "Xác nhận", {
          confirmButtonText: "OK",
          cancelButtonText: "Cancel",
          type: type,
        })
    },
    messageAll(title,message,type) {
      return this.$messageAll({
        title: title,
        message: message,
        type: type,
      });
    },
    denyAll() {
      if (this.listRequest.length == 0) {
        this.notifyAll("Lưu ý","Bạn chưa tích ô nào để hủy bỏ tất cả","info")
      } else {
        this.listSubmit = [];
        this.confirmAll("Bạn có chắc muốn hủy bỏ tất cả","success").then(() => {
          for (let item in this.listRequest) {
            this.listSubmit.push(this.listRequest[item].id)
          }
          welfareApi.denyAllWelfare(this.listSubmit).then(() => {
            welfareApi.getAllWelfareUpdate().then((res) => {
              this.list = res.data;

            });
            this.notifyAll("Thành công","Bạn đã từ chối thành công","success");
            this.allSelected = false
          }
          )
        })


      }
    },
    acceptAll() {
      if (this.listRequest.length == 0) {
        this.notifyAll("Lưu ý","Bạn chưa tích ô nào để chấp nhận tất cả","info")
      } else {
        this.confirmAll("Bạn có chắc muốn chấp nhận tất cả","success").then(() => {
          for (let item in this.listRequest) {
            let object = {
              id: this.listRequest[item].id,
              idWelfare: this.listRequest[item].id_welfare,
              name: this.listRequest[item].name,
              text: this.listRequest[item].text,
              moneyUpdate: this.listRequest[item].money_update,
              status: 0,
              isGeneral: this.listRequest[item].is_general,
              idStaff: this.listRequest[item].id_staff,
              year: this.listRequest[item].year,
              isQuantity: this.listRequest[item].is_quantity
            }
            this.listSubmit.push(object)
          }
          welfareApi.acceptAllWelfare(this.listSubmit).then(() => {
            welfareApi.getAllWelfareUpdate().then((res) => {
              this.list = res.data;

            });
            this.notifyAll("Thành công","Bạn đã xét duyệt thành công","success");
            this.allSelected = false
          }
          )
        })


      }
    },
    selectAll() {
      this.listRequest = [];
      if (!this.allSelected) {
        for (let item in this.list) {
          this.listRequest.push(this.list[item])
        }
      }
    },
    formatCurrency(value) {
      if (typeof value == "string") {
        value = value.replaceAll(",", "");
      }
      return Intl.NumberFormat("vi-VN", {
        style: "currency",
        currency: "VND",
      }).format(value);
    },
    openDialog(status) {
      if (status === 1) this.dialogDeny = !this.dialogDeny;
      else this.dialogAccept = !this.dialogAccept;
    },
    handleSuccess(item) {
      let object = {
        id: item.id,
        name: item.name,
        text: item.text,
        moneyUpdate: item.money_update,
        status: 0,
        isQuantity: item.is_quantity,
        year: item.year,
        isGeneral: item.is_general,
        idStaff: item.id_staff,
        idWelfare: item.id_welfare
      };
      console.log(item.is_general);
      console.log(item.money_update);
      this.confirmAll("Bạn muốn chấp nhận thay đổi này?","success").then(() => {
        if (item.price === null) {
          welfareApi
            .acceptCreateWelfare(object)
            .then(() => {
              // this.getAllMoneyUp();
              welfareApi.getAllWelfareUpdate().then((res) => {
                this.list = res.data;
              });
              this.messageAll("Thông báo","Đã chấp nhận","success")
            })
            .catch((error) => {
              console.log(error);
            });
        } else {
          welfareApi
            .acceptUpdateWelfare(item.id, object)
            .then(() => {
              // this.getAllMoneyUp();
              welfareApi.getAllWelfareUpdate().then((res) => {
                this.list = res.data;
              });
              this.messageAll("Thông báo","Đã chấp nhận","success")

            })
            .catch((error) => {
              console.log(error);
            });
        }
      });
    },
    handleDeny(id) {
      this.confirmAll("Bạn muốn từ chối thay đổi này?","success").then(() => {
        welfareApi
          .denyWelfareUpdate(id)
          .then(() => {
            welfareApi.getAllWelfareUpdate().then((res) => {
              this.list = res.data;
            });
            this.messageAll("Thông báo","Đã từ chối","success")
          })
          .catch((error) => {
            console.log(error);
          });
      });
    },
  },
  created() {
    welfareApi.getAllWelfareUpdate().then((res) => {
      this.list = res.data;
    });
  },
};
</script>

<style>
/* .welfare-table {
  width: fit-content;
  margin-left: 200px;
  margin-right: 35px;
  overflow: auto;
} */

.welfare-table_content {
  height: 70vh;
}

/* .welfare-table {
  text-align: center;
  padding: 0 50px;
  overflow: auto;
  height: 100%;
  margin: 20px 0px;
  overflow: auto;
} */

.welfare-table table tbody tr:hover {
  background-color: rgb(255, 255, 255);
}

.welfare-table table tr {
  background: #f2e7ddf8;
}

.welfare-table table tr th {
  border-right: 1px solid #e4c9ac;
  padding: 10px;
  background-color: #fdf9f8;
  height: 40px;
  font-weight: 650;
}

.welfare-table table tbody tr {
  border-bottom: 1px solid #e4c9ac;
}

.welfare-table table thead tr {
  border-bottom: 1px solid #e4c9ac;
}

.welfare-table td {
  border: 1px solid #e4c9ac;
  padding: 10px;
}

.welfare-table_content {
  text-align: center;
  padding: 0 0 0 60px;
  overflow: auto;
  height: 100%;
}

.welfare-body tr td {
  padding: 15px;
}
.welfare-table_content{
  height:620px;
  overflow: scroll;
}
.welfare-table_content table thead tr {
    background-color: #fdf9f8;
    position: sticky;
    top: 0;
}
</style>