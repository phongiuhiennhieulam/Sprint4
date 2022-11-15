<template>
  <div class="plList-main">
    <!-- Khu vực hiển thị thông tin phúc lợi -->
    <div class="plList-content">
      <div class="plList-title"><strong>DANH SÁCH PHÚC LỢI</strong></div>
      <div class="plList-button">
        <el-button class="plList-button__detail btn btn-danger" @click="showAddForm">{{ addText }}</el-button>
        <el-select v-model="value" placeholder="Loại phúc lợi" style="width: auto">
          <el-option label="Phúc Lợi Cá Nhân Hóa" :value="0"> </el-option>
          <el-option label="Phúc Lợi Chung" :value="1"> </el-option>
        </el-select>
      </div>
      <div class="plList-table">
        <div class="plList-table__content">
          <!-- <form id="form" label-width="120px"> -->
          <table>
            <thead>
              <tr>
                <th>STT</th>
                <th>Tên phúc lợi</th>
                <th>Mô tả</th>
                <th>Thành Tiền(VNĐ)</th>
                <th>Trạng thái</th>
                <th>Thao tác</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item, index) in list" :key="index">
                <td>{{ index + 1 }}</td>
                <td style="text-align: left">{{ item.name }}</td>
                <td style="text-align: left">{{ item.text }}</td>
                <td style="text-align: right">
                  {{ formatCurrency(item.price) }}
                </td>
                <td>
                  <span v-if="item.status == 1">
                    <el-button type="danger">Vô hiệu lực</el-button>
                  </span>
                  <span v-if="item.status == 0">
                    <el-button type="success">Có hiệu lực</el-button>
                  </span>
                </td>
                <td>
                  <span class="icon-edit" @click="showEditForm(item)">
                    <i class="fa fa-edit"></i> </span>&nbsp;
                  <span v-if="item.status == 1">
                    <el-popover placement="right" width="160" trigger="hover" content="Mở khóa phúc lợi">
                      <el-button @click="statusWelfare(item.id, 0)" type="success" icon="el-icon-unlock"
                        slot="reference" style="padding:3px 3px 3px 3px"></el-button>
                    </el-popover>

                  </span>
                  <span v-if="item.status == 0">
                    <el-popover placement="right" width="160" trigger="hover" content="Khóa phúc lợi">
                      <el-button @click="statusWelfare(item.id, 1)" type="danger" icon="el-icon-lock" slot="reference"
                        style="padding:3px 3px 3px 3px"></el-button>
                    </el-popover>
                  </span>
                </td>
              </tr>
            </tbody>
          </table>
          <!-- </form> -->
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
    <!-- Khu vực hiển thị thêm mới phúc lợi -->
    <el-form :model="add" ref="addValidateForm" label-width="120px" class="demo-dynamic" :rules="rules">
      <el-dialog :visible.sync="isShowAdd" width="600px" label-width="100px" top="5vh" left="150px"
        :title="titleFormName">
        <div class="row">
          <div class="col-12">
            <div class="mb-3">
              <label class="form-label">
                <strong>Tên phúc lợi<span style="color:red;">&nbsp;
                    (*)</span></strong>
              </label>
              <el-form-item prop="name" label-width="0px">
                <el-input v-model="add.name" type="text"></el-input>
              </el-form-item>
            </div>
          </div>
          <div class="col-12">
            <div class="row">
              <div class="mb-3 col-6">
                <label class="form-label">
                  <strong>Thành tiền:&nbsp;{{ formatCurrency(add.price) }}<span style="color:red;">&nbsp;
                      (*)</span></strong>
                </label>
                <el-form-item prop="price" label-width="0px">
                  <el-input type="number" :min="0" v-model.number="add.price"></el-input>
                </el-form-item>
              </div>
              <div class="mb-3 col-6" v-if="value == 0">
                <label class="form-label">
                  <strong>Loại phúc lượng<span style="color:red;">&nbsp;
                      (*)</span></strong>
                </label>
                <el-select v-model="add.isQuantity">
                  <el-option type="text" :value="true" label="Chỉ 1"></el-option>
                  <el-option type="text" :value="false" label="Chọn số lượng"></el-option>
                </el-select>
              </div>
            </div>
          </div>
          <div class="col-12">
            <label class="form-label">
              <strong>Mô tả<span style="color:red;">&nbsp;
                  (*)</span></strong>
            </label>
            <el-form-item prop="text" label-width="0px">
              <el-input type="textarea" v-model="add.text"></el-input>
            </el-form-item>

          </div>
        </div>
        <div slot="footer" class="dialog-footer" style="text-align: center">
          <el-button class="btn btn-danger" round @click="submitForm('addValidateForm')">{{ buttonName }}
          </el-button>
        </div>
      </el-dialog>
    </el-form>
  </div>
</template>

<script>
/* eslint-disable */
import WelfareApi from "@/service/phucLoiService";
import format from "@/utils/format"
let welfareApi = new WelfareApi();
export default {
  name: "PhucLoiList",
  data() {
    return {
      value: 0,
      isShowDialog: false,
      isShowAdd: false,
      idDelete: "",
      add: {
        id: "",
        name: "",
        text: "",
        price: "",
        isQuantity: false
      },
      formMode: '',
      list: [],
      listAllWelfare: [],
      nameWelfare: [],
      centerDialogVisible: false,
      addText: "Thêm mới phúc lợi cá nhân hóa",
    };
  },
  computed: {
    rules() {
      return {
        name: [{ validator: this.validateName, trigger: ['blur', 'change'] }],
        price: [{ validator: this.validatePrice, trigger: ['blur', 'change'] }],
        text: [{ validator: this.validateText, trigger: ['blur', 'change'] }]
      }
    },
    titleFormName() {
      return this.formMode === 'edit' ? 'Cập nhật phúc lợi' : 'Thêm mới phúc lợi';
    },
    buttonName() {
      return this.formMode === 'edit' ? 'Cập nhật' : 'Thêm mới';
    }
  },
  watch: {
    value: function (newVal) {
      if (newVal === 0) {
        this.getAllWelfare();
        this.addText = "Thêm mới phúc lợi cá nhân hóa";
      } else {
        this.getAllGeneralWelfares();
        this.addText = "Thêm mới phúc lợi chung";
      }
    },
    isShowAdd(value) {
      if (!value) {
        this.add = {
          id: "",
          name: "",
          text: "",
          price: "",
          isQuantity: false
        }
        this.resetForm('addValidateForm');
      }
    }
  },
  methods: {
    isDuplicateName(value) {
      if (this.formMode === 'edit') {
        return this.list.filter((x) => format.removeVietnameseTones(x.name).toLowerCase() === (format.removeVietnameseTones(value).toLowerCase()) && x.id !== this.add.id).length > 0
      } else {
        return this.list.filter((x) => format.removeVietnameseTones(x.name).toLowerCase() === (format.removeVietnameseTones(value).toLowerCase())).length > 0
      }
    },
    validateName(rule, value, callback) {
      if (value === '') {
        callback(new Error('Tên phúc lợi không được để trống'))
      } else if (value.match("[0-9]")) {
        callback(new Error('Tên phúc lợi không được chứa số'))
      } else if (this.isDuplicateName(value)) {
        callback(new Error('Tên phúc lợi đã tồn tại'))
      } else {
        callback()
      }
    },
    validateText(rule, value, callback) {
      if (value === '') {
        callback(new Error('Mô tả không được để trống'))
      } else {
        callback()
      }
    },
    validatePrice(rule, value, callback) {
      if (value === '') {
        callback(new Error('Số tiền không hợp lệ'))
      } else if (value <= 0) {
        callback(new Error('Số tiền phải lớn hơn 0'))
      } else {
        callback()
      }
    },
    submitForm(formName) {
      const self = this;
      let isValid = true
      self.$refs[formName].validate((valid) => {
        if (!valid) isValid = false;
      })
      if (isValid) {
        const qs = require("qs");
        if (self.formMode === 'edit') {
          if (this.value === 0) {
            welfareApi.updateWelfare(self.add.id, qs.stringify(self.add)).then((res) => {
              welfareApi.getAllWelfare().then((res) => {
                // self.isLoaded = true;
                this.list = res.data;
                this.$notify({
                  title: "Success",
                  message: "Cập nhật phúc lợi thành công",
                  type: "success",
                });
                this.list = res.data;
              });
            });
          }
          else {
            welfareApi.updateGeneralWelfare(self.add.id, qs.stringify(self.add)).then((res) => {
              welfareApi.getAllGeneralWelfare().then((res) => {
                // self.isLoaded = true;
                this.list = res.data;
                this.$notify({
                  title: "Success",
                  message: "Cập nhật phúc lợi thành công",
                  type: "success",
                });
                this.list = res.data;
              });
            });;
          }
        } else {
          if (self.value === 0) {
            welfareApi.createWelfare(qs.stringify(self.add)).then((res) => {
              welfareApi.getAllWelfare().then((res) => {
                // self.isLoaded = true;
                self.list = res.data;
                self.$notify({
                  title: "Success",
                  message: "Thêm mới phúc lợi thành công",
                  type: "success",
                });
              });
              self.list = res.data;
            });
          } else {
            welfareApi.createGeneralWelfare(qs.stringify(self.add)).then((res) => {
              welfareApi.getAllGeneralWelfare().then((res) => {
                // self.isLoaded = true;
                self.list = res.data;
                self.$notify({
                  title: "Success",
                  message: "Thêm mới phúc lợi thành công",
                  type: "success",
                });
                self.list = res.data;
              });
            });
          }
        }
        self.isShowAdd = false;
        self.resetForm(formName)
      }
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    statusWelfare(id, status) {
      let object = {
        itemID: id,
        status: status,
        kindOfWelfare: this.value
      }
      welfareApi.changeStatusWelfare(object).then((res) => {
        if (this.value === 0) {
          welfareApi.getAllWelfare().then((res) => {
            // self.isLoaded = true;
            this.list = res.data;
          });
        } else {
          welfareApi.getAllGeneralWelfare().then((res) => {
            // self.isLoaded = true;
            this.list = res.data;
          });
        }
      });;
    },
    showAddForm() {
      this.isShowAdd = true;
      this.formMode = 'add'
    },
    showEditForm(item) {
      setTimeout(() => {
        this.add = JSON.parse(JSON.stringify(item)); //clone item object
        this.formMode = 'edit'
        if (item.isQuantity === false) this.label = "Chọn số lượng";
        else this.label = "Chỉ 1";
        this.isShowAdd = true;
      }, 100);
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
    getAllWelfare() {
      welfareApi.getAllWelfare().then((res) => {
        // self.isLoaded = true;
        this.list = res.data;
      });
    },
    getAllGeneralWelfares() {
      welfareApi.getAllGeneralWelfare().then((res) => {
        // self.isLoaded = true;
        this.list = res.data;
      });
    },
    setUpListNameWelfare() {
      welfareApi.getAllWelfare().then((res) => {
        this.listAllWelfare = res.data;
        this.listAllWelfare.forEach(element => {
          this.nameWelfare.push(element.name);
        });
      });
      welfareApi.getAllGeneralWelfare().then((res) => {
        this.listAllWelfare = res.data;
        this.listAllWelfare.forEach(element => {
          this.nameWelfare.push(element.name);
        });
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
    this.setUpListNameWelfare();
  },
};
</script>

<style scoped>
@import "@/assets/css/phucloi/list.css";
</style>
