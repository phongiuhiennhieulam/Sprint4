<template>
  <div class="plList-main">
    <!-- Khu vực hiển thị thông tin phúc lợi -->
    <div class="plList-content">
      <div class="plList-title"><strong>QUẢN LÍ PHÚC LỢI</strong></div>
      <MediaQueryProvider :queries="$options.queries">
        <MatchMedia v-slot="{ desktop }">
          <div v-if="desktop">
            <div class="plList-button">
              <el-button class="plList-button__detail btn btn-danger" @click="showAddForm">{{ addText }}</el-button>
              <el-select v-model="value" placeholder="Loại phúc lợi" style="width: auto;">
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
                      <th>Loại phúc lợi</th>
                      <!-- <th>Trạng thái</th> -->
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
                      <td v-if="item.isQuantity">
                        Chỉ chọn 1

                      </td>
                      <td v-if="!item.isQuantity">
                        Chọn số lượng

                      </td>
                      <td v-if="!(listWelfareWaitToUpdate.includes(item.id))" style="color: seagreen;">Đang được sử dụng
                      </td>
                      <td v-if="(listWelfareWaitToUpdate.includes(item.id))" style="color: goldenrod;">Chờ duyệt thay
                        đổi</td>
                      <!-- <td>
                    <span v-if="item.status == 1">
                      <el-button type="danger">Vô hiệu lực</el-button>
                    </span>
                    <span v-if="item.status == 0">
                      <el-button type="success">Có hiệu lực</el-button>
                    </span>
                  </td> -->
                      <td>
                        <el-popover placement="left" width="160" trigger="hover" content="Sửa phúc lợi">
                          <el-button @click="showEditForm(item)" icon="fa fa-edit" slot="reference"
                            style="padding:3px 3px 3px 3px; margin-right: 8px;width:30%;height:100%"
                            v-if="!(listWelfareWaitToUpdate.includes(item.id))"></el-button>
                        </el-popover>

                        <el-popover placement="right" width="160" trigger="hover" content="Xóa phúc lợi">
                          <el-button @click="confirmDeleteDialog(item.id)" type="danger" icon="fa fa-trash"
                            slot="reference" style="padding:3px 3px 3px 3px;width:30%;height:100%"></el-button>
                        </el-popover>
                        <!-- <span v-if="item.status == 0">
                      <el-popover placement="right" width="160" trigger="hover" content="Khóa phúc lợi">
                        <el-button @click="statusWelfare(item.id, 1)" type="danger" icon="el-icon-lock" slot="reference"
                          style="padding:3px 3px 3px 3px"></el-button>
                      </el-popover>
                    </span> -->
                        <!-- <span v-if="item.status == 1">
                      <el-popover placement="right" width="160" trigger="hover" content="Mở khóa phúc lợi">
                        <el-button @click="statusWelfare(item.id, 0)" type="success" icon="el-icon-unlock"
                          slot="reference" style="padding:3px 3px 3px 3px"></el-button>
                      </el-popover>
                    </span> -->
                      </td>
                    </tr>
                  </tbody>
                </table>
                <!-- </form> -->
              </div>
            </div>
            <div>
              <el-dialog title="Xác nhận" :visible.sync="centerDialogVisible" width="30%" center>
                <div style="text-align: center;"><span>Bạn có chắc chắn muốn xóa?</span></div>
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
                            <strong>Thành tiền: </strong>
                          </label>
                          <el-form-item prop="price" label-width="0px">
                            <el-input type="text" v-model="add.price" @blur="keyUpMoney" @input="keyUpMoney"></el-input>
                          </el-form-item>
                        </div>
                        <div class="mb-3 col-6" v-if="value == 0">
                          <label class="form-label">
                            <strong>Loại phúc lợi<span style="color:red;">&nbsp;
                                (*)</span></strong>
                          </label>
                          <el-select v-model="add.isQuantity">
                            <el-option type="text" :value="true" label="Chỉ 1"></el-option>
                            <el-option type="text" :value="false" label="Chọn số lượng"></el-option>
                          </el-select>
                        </div>
                        <div class="mb-3 col-6" v-if="(value == 1)">
                          <label class="form-label">
                            <strong>Năm<span style="color:red;">&nbsp;
                                (*)</span></strong>
                          </label>
                          <el-form-item prop="year" label-width="0px">
                            <el-input v-model="add.year" type="number"></el-input>
                          </el-form-item>
                        </div>
                      </div>
                    </div>
                    <div class="col-12" v-if="(value == 0)">
                      <label class="form-label">
                        <strong>Năm<span style="color:red;">&nbsp;
                            (*)</span></strong>
                      </label>
                      <el-form-item prop="year" label-width="0px">
                        <el-input type="number" v-model="add.year"></el-input>
                      </el-form-item>

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
                    <!-- <el-input type="hidden" v-model="add.idStaff"></el-input> -->
                    <!-- <pre>{{add.idStaff}}</pre> -->
                  </div>
                  <div slot="footer" class="dialog-footer" style="text-align: center">
                    <el-button class="btn btn-danger" round @click="submitForm('addValidateForm')">{{ buttonName }}
                    </el-button>
                  </div>
                </el-dialog>
              </el-form>
            </div>
          </div>
          <div v-else>
            <div class="plList-button">
              <el-button class="plList-button__detail btn btn-danger" @click="showAddForm">{{ addText }}</el-button>
              <el-select v-model="value" placeholder="Loại phúc lợi">
                <el-option label="Phúc Lợi Cá Nhân Hóa" :value="0"> </el-option>
                <el-option label="Phúc Lợi Chung" :value="1"> </el-option>
              </el-select>
            </div>
            <div class="plList-table__content">
              <div v-for="(item, index) in list" :key="index" class="plList-item">
                <div class="plList-item_content">
                  <div><strong>Số thứ tự:</strong> {{ index + 1 }}</div>
                  <div><strong>Tên phúc lợi:</strong> {{ item.name }}</div>
                  <div><strong>Mô tả:</strong> {{ item.text }}</div>
                  <div>
                    <strong>Thành Tiền(VNĐ):</strong> {{ formatCurrency(item.price) }}
                  </div>
                </div>
                <div class="plList-item_action">
                  <el-popover placement="left" width="160" trigger="hover" content="Sửa phúc lợi">
                    <el-button @click="showEditForm(item)" icon="fa fa-edit" slot="reference"
                      style="padding:3px 3px 3px 3px; margin-right: 3px;"></el-button>
                  </el-popover>
                  <el-popover placement="right" width="160" trigger="hover" content="Xóa phúc lợi">
                    <el-button @click="confirmDeleteDialog(item.id)" type="danger" icon="fa fa-trash" slot="reference"
                      style="padding:3px 3px 3px 3px"></el-button>
                  </el-popover>
                </div>
              </div>
              <el-dialog title="Xác nhận" :visible.sync="centerDialogVisible" width="90%" center>
                <div style="text-align: center;"><span>Bạn có chắc chắn muốn xóa?</span></div>
                <span slot="footer" class="dialog-footer">
                  <el-button @click="centerDialogVisible = false">Hủy</el-button>
                  <el-button type="primary" @click="showDeleteDialog(idDelete)">Xác nhận</el-button>
                </span>
              </el-dialog>
              <!-- Khu vực hiển thị thêm mới phúc lợi -->
              <el-form :model="add" ref="addValidateForm" label-width="120px" class="demo-dynamic" :rules="rules">
                <el-dialog :visible.sync="isShowAdd" width="90%" label-width="100px" top="5vh" left="150px"
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
                      <div class="mb-3">
                        <label class="form-label">
                          <strong>Thành tiền:&nbsp;{{ formatCurrency(add.price) }}<span style="color:red;">&nbsp;
                              (*)</span></strong>
                        </label>
                        <el-form-item prop="price" label-width="0px">
                          <el-input type="text" v-model="add.price" @blur="keyUpMoney" @input="keyUpMoney"></el-input>
                        </el-form-item>
                      </div>
                    </div>
                    <div class="col-12">
                      <div class="mb-3" v-if="value == 0">
                        <label class="form-label">
                          <strong>Loại phúc lợi<span style="color:red;">&nbsp;
                              (*)</span></strong>
                        </label>
                        <el-select v-model="add.isQuantity">
                          <el-option type="text" :value="true" label="Chỉ 1"></el-option>
                          <el-option type="text" :value="false" label="Chọn số lượng"></el-option>
                        </el-select>
                      </div>
                      <div class="mb-3" v-if="(value != 0)">
                        <label class="form-label">
                          <strong>Năm<span style="color:red;">&nbsp;
                              (*)</span></strong>
                        </label>
                        <el-form-item prop="year" label-width="0px">
                          <el-input v-model="add.year" type="text"></el-input>
                        </el-form-item>
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
                    <!-- <el-input type="hidden" v-model="add.idStaff"></el-input> -->
                  </div>
                  <div slot="footer" class="dialog-footer" style="text-align: center">
                    <el-button class="btn btn-danger" round @click="submitForm('addValidateForm')">{{ buttonName }}
                    </el-button>
                  </div>
                </el-dialog>
              </el-form>
            </div>
          </div>
        </MatchMedia>
      </MediaQueryProvider>
    </div>

  </div>
</template>
  
<script>
/* eslint-disable */
import WelfareApi from "@/service/phucLoiService";
import format from "@/utils/format"
let welfareApi = new WelfareApi();
import { MediaQueryProvider } from "vue-component-media-queries";
import { MatchMedia } from "vue-component-media-queries";
import Error401 from "./401-error.vue";

const queries = {
  mobile: '(max-width: 760px)',
  desktop: '(min-width: 761px)'
};
export default {
  name: "PhucLoiList",
  queries,
  data() {
    return {
      value: 0,
      hasRole: false,
      isShowDialog: false,
      isShowAdd: false,
      idDelete: "",
      add: {
        id: "",
        name: "",
        text: "",
        price: "",
        isQuantity: false,
        year: "",
        idStaff: ""
      },
      idStaff: "",
      formMode: '',
      list: [],
      listAllWelfare: [],
      nameWelfare: [],
      centerDialogVisible: false,
      addText: "Thêm mới phúc lợi cá nhân hóa",
      userName: "",
      duplicateName: false,
      listCheck: [],
      listWelfareWaitToUpdate: []
    };
  },

  components: {
    MediaQueryProvider, MatchMedia
  },
  computed: {
    rules() {
      return {
        name: [{ validator: this.validateName, trigger: ['blur', 'change'] }],
        price: [{ validator: this.validatePrice, trigger: ['blur', 'change'] }],
        text: [{ validator: this.validateText, trigger: ['blur', 'change'] }],
        year: [{ validator: this.validateYear, trigger: ['blur', 'change'] }]
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
          year: "",
          isQuantity: false
        }
        this.resetForm('addValidateForm');
      }
    },

    add: {
      handler: function (val) {
        if (val.name) {
          const objectFounded = this.isDuplicateName(val.name);
          if (objectFounded.length > 0) {
            this.duplicateName = true;
            if (val.year && Number(val.year) !== objectFounded[0].year) {
              this.duplicateName = false;
              this.$refs.addValidateForm.clearValidate('name');
            } else {
              this.duplicateName = true;
              this.$refs.addValidateForm.validateField('name');
            }
          } else {
            this.duplicateName = false;
            this.$refs.addValidateForm.clearValidate('name');
          }
        }
      },
      deep: true
    },

  },
  methods: {
    addCommas(nStr) {
      var x, x1, x2;
      nStr += '';
      x = nStr.split('.');
      x1 = x[0];
      x2 = x.length > 1 ? ',' + x[1] : '';
      var rgx = /(\d+)(\d{3})/;
      while (rgx.test(x1)) {
        x1 = x1.replace(rgx, '$1' + ',' + '$2');
      }
      return x1 + x2;
    },
    keyUpMoney() {
      this.add.price = this.add.price.replace(/[^0-9.,]/g, '')
      this.add.price = this.addCommas(this.add.price.replace(/,/g, ''));
    },
    isDuplicateName(name) {
      if (this.formMode === 'edit') {
        return this.listCheck.filter((x) => format.removeVietnameseTones(x.name).toLowerCase().trim() === (format.removeVietnameseTones(name).toLowerCase().trim()) && x.id !== this.add.id)
      } else {
        return this.listCheck.filter((x) => format.removeVietnameseTones(x.name).toLowerCase().trim() === (format.removeVietnameseTones(name).toLowerCase().trim()))
      }
    },
    validateName(rule, value, callback) {

      if (value.trim() === '') {
        callback(new Error('Tên phúc lợi không được để trống'))
      } else if (value.match("[0-9]")) {
        callback(new Error('Tên phúc lợi không được chứa số'))
      } else if (this.duplicateName) {
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
    validateYear(rule, value, callback) {
      if (value === '') {
        callback(new Error('Năm không được để trống'))
      } else if (value < 0) {
        callback(new Error('Năm không được nhỏ hơn 0'))
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
      self.add.idStaff = this.idStaff;

      if (isValid) {
        const qs = require("qs");
        self.add.price = parseInt(self.add.price.replace(/,/g, ''))
        self.add.name = self.add.name.trim();
        if (self.formMode === 'edit') {
          // if (this.value === 0) {
          //   welfareApi.updateWelfare(self.add.id, qs.stringify(self.add)).then((res) => {
          //     welfareApi.getAllWelfare().then((res) => {
          //       // self.isLoaded = true;
          //       this.list = res.data;
          //       this.$notify({
          //         title: "Success",
          //         message: "Đã gửi yêu cầu cập nhật thành công!!\nXin chờ xét duyệt",
          //         type: "success",
          //       });
          //       this.list = res.data;
          //     });
          //   });
          // }
          // else {
          //   welfareApi.updateGeneralWelfare(self.add.id, qs.stringify(self.add)).then((res) => {
          //     welfareApi.getAllGeneralWelfare().then((res) => {
          //       // self.isLoaded = true;
          //       this.list = res.data;
          //       this.$notify({
          //         title: "Success",
          //         message: "Đã gửi yêu cầu cập nhật thành công!!\nXin chờ xét duyệt",
          //         type: "success",
          //       });
          //       this.list = res.data;
          //     });
          //   });;
          // }
          welfareApi.updateWelfare(self.add.id, this.value, qs.stringify(self.add)).then((res) => {
            this.$notify({
              title: "Success",
              message: "Đã gửi yêu cầu cập nhật thành công!!\nXin chờ xét duyệt",
              type: "success",
            });
          });
        } else {
          // if (self.value === 0) {
          //   welfareApi.createWelfare(qs.stringify(self.add)).then((res) => {
          //     welfareApi.getAllWelfare().then((res) => {
          //       // self.isLoaded = true;
          //       self.list = res.data;
          //       self.$notify({
          //         title: "Success",
          //         message: "Đã gửi yêu cầu thêm mới thành công!!\nXin chờ xét duyệt",
          //         type: "success",
          //       });
          //     });
          //     self.list = res.data;
          //   });
          // } else {
          //   welfareApi.createGeneralWelfare(qs.stringify(self.add)).then((res) => {
          //     welfareApi.getAllGeneralWelfare().then((res) => {
          //       // self.isLoaded = true;
          //       self.list = res.data;
          //       self.$notify({
          //         title: "Success",
          //         message: "Đã gửi yêu cầu thêm mới thành công!!\nXin chờ xét duyệt",
          //         type: "success",
          //       });
          //       self.list = res.data;
          //     });
          //   });
          // }

          welfareApi.createWelfare(this.value, qs.stringify(self.add)).then((res) => {
            self.$notify({
              title: "Success",
              message: "Đã gửi yêu cầu thêm mới thành công!!\nXin chờ xét duyệt",
              type: "success",
            });
          });
        }
        // welfareApi.getAllWelfareWaitToUpdate().then((res) => {
        //   this.listWelfareWaitToUpdate = res.data;
        //   console.log(this.listWelfareWaitToUpdate);
        // });

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
        item.price = this.addCommas(item.price);
        this.add = JSON.parse(JSON.stringify(item)); //clone item object
        this.formMode = 'edit'
        if (item.isQuantity === false) this.label = "Chọn số lượng";
        else this.label = "Chỉ 1";
        this.isShowAdd = true;
      }, 100);
    },
    confirmDeleteDialog(itemId) {
      this.centerDialogVisible = true;
      this.idDelete = itemId;
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
          }
        });
      } else {
        // alert(item);
        welfareApi.deleteGeneralWelfare(item).then((res) => {
          if (res.status == 200) {
            const index = this.list.findIndex((a) => a.id == item);
            // alert(index);
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
        this.hasRole =true;
        this.listCheck = res.data;
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
      if (typeof (value) == 'string') {
        value = value.replaceAll(',', '');
      }
      return Intl.NumberFormat("vi-VN", {
        style: "currency",
        currency: "VND",
      }).format(value);
    },
    getAllWelfareWaitToUpdate() {
      welfareApi.getAllWelfareWaitToUpdate().then((res) => {
        this.listWelfareWaitToUpdate = res.data;
        console.log(this.listWelfareWaitToUpdate);
      });
    }
  },
  created() {
    // const self = this;
    this.getAllWelfare();
    this.setUpListNameWelfare();
    if (localStorage.getItem("user")) {
      // this.getStaff(this.userName)
      welfareApi.findID(JSON.parse(localStorage.getItem("user")).userName).then((res) => {
        this.idStaff = res.data;
      });
    }


    this.getAllWelfareWaitToUpdate();

  },
};
</script>
  
<style>
@import "@/assets/css/phucloi/list.css";

.demo-dynamic input {
  border-radius: 8px !important;
  box-shadow: 1px 1px 4px rgb(0 0 0 / 15%);
  border: 1px solid #23282b8f;
}

.demo-dynamic .el-select {
  width: 100%;
}

@media only screen and (max-width: 760px) {
  .plList-button {
    margin: 10px 10px 20px 10px;
  }

  .plList-button button {
    width: 100%;
    margin-bottom: 8px;
  }

  .plList-button .el-select input {
    border-radius: 8px;
    width: 100%;
    text-align: center;
  }

  .plList-table__content {
    padding: 0 8px;
    height: 648px;
  }

  .plList-item_action i {
    font-size: 24px !important;
  }

  .plList-item_content {
    flex: 1;
    padding-right: 12px;
  }

  .plList-item {
    display: flex;
    justify-content: space-between;
    padding: 8px;
    border: 1px solid #6f6f6f;
    margin-bottom: 12px;
    border-radius: 12px;
  }

  .plList-item_action {
    display: flex;
    align-items: center;
    justify-content: center;

  }

  .plList-table {
    margin: 0;
  }
}
</style>