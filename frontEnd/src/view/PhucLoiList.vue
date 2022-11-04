<template>
  <div class="pl-main">
    <div class="pl-content">
      <div class="pl-title"><strong>DANH SÁCH PHÚC LỢI</strong></div>
      <div class="pl-ele">
        <div class="pl-button">
          <el-button class="pl-button__detail btn btn-danger" @click="showAddForm">{{ addText }}</el-button>
          <el-select v-model="value" placeholder="Loại phúc lợi" style="width: auto">
            <el-option label="Phúc Lợi Cá Nhân Hóa" :value="0"> </el-option>
            <el-option label="Phúc Lợi Chung" :value="1"> </el-option>
          </el-select>
        </div>
        <div class="pl-table">
          <div class="pl-table__content">
            <!-- <form id="form" label-width="120px"> -->
            <table>
              <thead>
                <tr>
                  <th>STT</th>
                  <th>Tên phúc lợi</th>
                  <th>Mô tả</th>
                  <th>Thành Tiền(VNĐ)</th>
                  <th>Thao tác</th>
                </tr>
              </thead>
              <tbody>
                <!-- <tr v-if="isShowAdd">
                    <td></td>
                    <td>
                      <input
                        type="text"
                        v-model="edit.name"
                        id="name"
                        name="name"
                        placeholder="Nhập tên phúc lợi"
                      />
                    </td>
                    <td>
                      <input
                        type="text"
                        v-model="edit.text"
                        id="text"
                        name="text"
                        placeholder="Nhập mô tả"
                      />
                    </td>
                    <td>
                      <input
                        type="text"
                        v-model="edit.price"
                        id="price"
                        name="price"
                        placeholder="Nhập tổng tiền"
                      />
                    </td>
                    <td>
                      <el-button round class="pl-button__detail" @click="Add()"
                        >Xác nhận</el-button
                      >
                      <el-button
                        round
                        class="pl-button__detail"
                        @click="Cancel()"
                        >Hủy</el-button
                      >
                    </td>
                  </tr> -->
                <tr v-for="(item, index) in list" :key="index">
                  <td>{{ index + 1 }}</td>
                  <td style="text-align: left">{{ item.name }}</td>
                  <td style="text-align: left">{{ item.text }}</td>
                  <td style="text-align: right">
                    {{ formatCurrency(item.price) }}
                  </td>
                  <td>
                    <span class="icon-edit" @click="showEditForm(item)">
                      <i class="fa fa-edit"></i> </span>&nbsp;<span class="icon-delete"
                      @click="showFormDelete(item.id)">
                      <i class="fa fa-trash"></i>
                    </span>
                  </td>
                </tr>
              </tbody>
            </table>
            <!-- </form> -->
          </div>
        </div>
      </div>
    </div>
    <el-dialog title="Xác nhận" :visible.sync="centerDialogVisible" width="30%" center>
      <span>Bạn có chắc chắn muốn xóa phúc lợi này?</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="centerDialogVisible = false">Hủy</el-button>
        <el-button type="primary" @click="showDeleteDialog(idDelete)">Xác nhận</el-button>
      </span>
    </el-dialog>
    <el-dialog :visible.sync="isShowEdit" width="600px" label-width="100px" top="5vh">
      <h3><strong>Thông tin phúc lợi</strong></h3>
      <el-form :model="edit" ref="edit" label-width="120px" label-position="top">
        <el-row>
          <el-col :span="6">
            <el-form-item label="Tên phúc lợi" prop="name">
              <el-input v-model="edit.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item label="Thành tiền" prop="address">
              <samp>formatCurrency(edit.price)</samp>
              <el-input type="number" v-model="edit.price"></el-input>
            </el-form-item>
          </el-col>
          <el-col>
            <el-form-item label="Loại" prop="code" v-if="value == 0">
              <el-select v-model="edit.isQuantity">
                <el-option type="text" :value="true" label="Chỉ 1"></el-option>
                <el-option type="text" :value="false" label="Chọn số lượng"></el-option>
              </el-select>

            </el-form-item>
          </el-col>
          <el-col>
            <el-form-item label="Mô tả" prop="code">
              <el-input type="textarea" v-model="edit.text"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button class="hr-detail__button" round @click="editWel(edit)">Cập nhật
        </el-button>
      </span>
    </el-dialog>
    <el-dialog :visible.sync="isShowAdd" width="600px" label-width="100px" top="5vh" left="150px">
      <h3><strong>Thêm mới phúc lợi</strong></h3>
      <el-form :model="add" ref="add" label-width="120px" label-position="top">
        <el-row>
          <el-col :span="9">
            <el-form-item label="" class="el-item">
              <samp>Tên phúc lợi</samp>
              <el-input v-model="add.name" type="text"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="11" :offset="4">
            <el-form-item label="" class="el-item">
              <samp>Thành tiền: {{ formatCurrency(add.price) }}</samp>
              <el-input type="number" v-model="add.price"></el-input>
            </el-form-item>
          </el-col>
          <br>
          <el-col>
            <el-form-item prop="code" v-if="value == 0">
              <el-select v-model="add.isQuantity">
                <el-option type="text" :value="true" label="Chỉ 1"></el-option>
                <el-option type="text" :value="false" label="Chọn số lượng"></el-option>
              </el-select>

            </el-form-item>
          </el-col>
          <el-col>
            <el-form-item prop="code" class="el-item">
              <samp>Mô tả</samp>
              <el-input type="textarea" v-model="add.text"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: center">
        <el-button class="btn btn-danger" round @click="Add(add)">Thêm mới
        </el-button>
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
      value: 0,
      isShowEdit: false,
      isShowDialog: false,
      isShowAdd: false,
      idDelete: "",
      edit: {
        id: "",
        name: "",
        text: "",
        price: "",
        isQuantity: false,
      },
      add: {
        id: "",
        name: "",
        text: "",
        price: "",
        isQuantity: false
      },
      list: [],
      centerDialogVisible: false,
      addText: "Thêm mới phúc lợi cá nhân hóa"
    };
  },
  watch: {
    value: function (newVal) {
      console.log(newVal);
      if (newVal === 0) {
        this.getAllWelfare();
        this.addText = "Thêm mới phúc lợi cá nhân hóa";
      } else {
        this.getAllGeneralWelfares();
        this.addText = "Thêm mới phúc lợi chung";
      }
    },
    deep: true,
  },
  methods: {
    showAddForm() {
      this.edit = {};
      this.isShowAdd = true;
    },
    change(event) {
      console.log(event.target.value);
    },
    Add(add) {
      let isValidate = true;
      if (!(add.name && add.text && add.price)) {
        this.$alert(
          "Thông tin đầu vào không hợp lệ vui lòng nhập đủ thông tin!",
          "Thông tin không hợp lệ",
          {
            confirmButtonText: "OK",
            callback: () => { },
          }
        );
        isValidate = false;
      }
      if (isValidate) {
        //let form = document.querySelector("#form");
        //console.log(form);
        const qs = require("qs");
        if (this.value === 0) {
          welfareApi.createWelfare(qs.stringify(add)).then((res) => {
            welfareApi.getAllWelfare().then((res) => {
              // self.isLoaded = true;
              this.list = res.data;
              this.$notify({
                title: "Success",
                message: "Thêm mới phúc lợi thành công",
                type: "success",
              });
            });
            const myTimeout = setTimeout(myGreeting, 100);

            function myGreeting() {
              window.location.reload();
            }
          });
        } else {
          welfareApi.createGeneralWelfare(qs.stringify(add)).then((res) => {
            welfareApi.getAllGeneralWelfare().then((res) => {
              // self.isLoaded = true;
              this.list = res.data;
              this.$notify({
                title: "Success",
                message: "Thêm mới phúc lợi thành công",
                type: "success",
              });
              const myTimeout = setTimeout(myGreeting, 100);

              function myGreeting() {
                window.location.reload();
              }
            });
          });
        }
        this.isShowAdd = false;
        add.name = "";
        add.price = "";
        add.text = "";
      }
    },
    Cancel() {
      this.isShowAdd = false;
    },
    showEditForm(item) {
      this.isShowEdit = false;
      setTimeout(() => {
        this.edit = item;
        if (item.isQuantity === false) this.label = "Chọn số lượng";
        else this.label = "Chỉ 1";
        this.isShowEdit = true;
      }, 100);
    },
    showFormDelete(id) {
      this.idDelete = id;
      // alert(id);
      this.centerDialogVisible = true;
    },
    showDeleteDialog(item) {
      if (this.value === 0) {
        welfareApi.deleteWelfare(item).then((res) => {
          if (res.status == 200) {
            const index = this.list.findIndex((a) => a.id == item);
            // alert(index);
            this.list.splice(index, 1);
            this.$notify({
              title: "Success",
              message: "Xóa phúc lợi thành công",
              type: "success",
            });
            const myTimeout = setTimeout(myGreeting, 100);

            function myGreeting() {
              window.location.reload();
            }
          }
        });
      } else {
        // alert(item);
        welfareApi.deleteGeneralWelfare(item).then((res) => {
          if (res.status == 200) {
            const index = this.list.findIndex((a) => a.id == item);
            // alert(index);
            this.list.splice(index, 1);
            this.list.splice(index, 1);
            this.$notify({
              title: "Success",
              message: "Xóa phúc lợi thành công",
              type: "success",
            });
          }
        });
      }

      this.centerDialogVisible = false;
    },
    editWel(edit) {
      let isValidate = true;
      if (!(edit.name && edit.text && edit.price)) {
        this.$alert(
          "Thông tin đầu vào không hợp lệ vui lòng nhập đủ thông tin!",
          "Thông tin không hợp lệ",
          {
            confirmButtonText: "OK",
            callback: () => { },
          }
        );
        isValidate = false;
      }
      if (isValidate) {
        const qs = require("qs");
        if (this.value === 0)
          welfareApi.updateWelfare(edit.id, qs.stringify(edit));
        else welfareApi.updateGeneralWelfare(edit.id, qs.stringify(edit));
        this.isShowEdit = false;
        this.$notify({
          title: "Success",
          message: "Sửa thông tin phúc lợi thành công",
          type: "success",
        });
        const myTimeout1 = setTimeout(myGreeting1, 200);

        function myGreeting1() {
          window.location.reload();
        }
      }
    },
    getAllWelfare() {
      welfareApi.getAllWelfare().then((res) => {
        // self.isLoaded = true;
        this.list = res.data;
        console.log(res.data);
        console.log(this.list);
      });
    },
    getAllGeneralWelfares() {
      welfareApi.getAllGeneralWelfare().then((res) => {
        // self.isLoaded = true;
        this.list = res.data;
        console.log(res.data);
        console.log(this.list);
      });
    },
    formatCurrency(value) {
      return Intl.NumberFormat("vi-VN", {
        style: "currency",
        currency: "VND",
      }).format(value);
    },
  },
  created() {
    console.log("created");
    // const self = this;
    this.getAllWelfare();
  },
};
</script>

<style scoped>
.el-item {
  color: #f00 !important;
  font-size: 22px;
  font-weight: 600;
  line-height: 31px;
  letter-spacing: 0em;
  text-align: center;
  line-height: 0px;
  margin-bottom: 6px;
}

.pl-title {
  text-align: center;
  font-size: 34px;
  font-weight: 600;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen,
    Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
  background: rgba(255, 255, 255, 0.13);
  padding: 6px 0px;
}

.pl-table {
  text-align: center;
  margin-left: 200px;
  margin-right: 200px;
  z-index: 2;
}

.pl-table__content {
  height: 600px;
  overflow: scroll;
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
  position: sticky;
  top: 0;
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
  margin-bottom: 10px;
}

.pl-button__detail {
  color: white !important;
  font-size: 14px !important;
  font-weight: 600 !important;
  text-align: center;
  margin-right: 10px;
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

.hr-detail__title {
  color: #000;
  border-bottom: 2px solid #f004;
  margin: auto;
}
</style>
