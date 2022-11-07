<template>
  <div class="hr-list">
    <div class="hr-content">
      <div class="hr-title"><strong> QUẢN LÝ NHÂN VIÊN</strong></div>
      <br />
      <div class="row">
        <div class="col-4" style="margin-left: 35px">
        </div>
        <div class="col-2" style="margin-left: 332px">
        </div>
        <div class="col-6"></div>
      </div>
      <div class="hr-info">
        <div class="hr-table">
          <div class="hr-selected"></div>
          <table>
              <thead>
                <tr width="90%">
                  <th >     
                    <div>
                      <el-form :inline="true" class="demo-form-inline">
                          <el-form-item>
                            <el-input style="width: 300px;"
                              v-model="text" 
                              placeholder="Nhập tên/ mã nhân viên/ phòng ban">
                              <i slot="prefix" class="el-input__icon el-icon-search"></i>
                            </el-input>
                            <el-button class="btn btn-warning" style="color: white; margin-left: 10px;" @click="onFind()">
                              Tìm kiếm
                            </el-button>
                          </el-form-item>
                    </el-form>
                    </div>  
                  </th>                                                  
                  <th width="10%">
                    <div>
                      <el-form :inline="true" class="demo-form-inline">
                          <el-form-item>
                            <el-button class="btn btn-danger" @click="handlDeletes">
                             <i class="el-icon-lock"></i> Khóa
                            </el-button>
                          </el-form-item>
                    </el-form>
                    </div>  
                  </th>
                  
                  <th width="10%">
                    <div>
                      <el-form :inline="true" class="demo-form-inline">
                          <el-form-item>
                            <el-button type="erorr" class="btn btn-danger" @click="showAddForm">
                              <i class="el-icon-plus"></i> Thêm mới</el-button>
                          </el-form-item>
                    </el-form>
                    </div>  
                  </th>
                </tr>
              </thead>
            </table>
          <div class="hr-table__content">
            <table>
              <thead>
                <tr>
                  <th>Chọn</th>
                  <th width="50px">Khóa</th>
                  <th width="70px">STT</th>
                  <th width="200px">Họ và tên</th>
                  <th width="200px">Mã nhân viên</th>
                  <th width="240px">Phòng ban</th>
                  <th width="170x">Trạng thái</th>
                  <th width="50px">infor</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(item, index) in staffs.content" :key="index">
                  <td>
                    <input
                        type="checkbox"
                        :value="item.id"
                        v-model="selected"
                        :ref="item.id"
                      />
                  </td>
                  
                  <td>
                    <!-- <span class="icon-delete" @click="deleteStaff(item.id)">
                      <i class="fa fa-trash" aria-hidden="true"></i>
                    </span> -->
                    <span  v-if="item.status == 0">
                      <el-button
                        @click="deleteStaff(item.id, index)"
                        type="danger"
                        icon="el-icon-lock" 
                        circle
                      ></el-button>
                    </span>
                    <span v-if="item.status == 1">
                      <el-button
                        @click="unlockStaff(item.id)"
                        type="warning"
                        icon="el-icon-unlock" 
                        circle
                      ></el-button>
                    </span>
                  </td>
                  <td>{{ index + 1 }}</td>
                  <td style="text-align: left;">{{ item.name }}</td>
                  <td style="text-align: left;">{{ item.code }}</td>
                  <td style="text-align: left;">{{ item.department.name }}</td>
                  <td style="text-align: left;">
                    <div v-if="item.status == 0">Đang làm việc</div>
                    <div v-if="item.status == 1">Nghỉ làm</div>
                  </td>
                  <td>
                    <span class="icon-edit" @click="showInfo(item)">
                      <i class="fa fa-circle-info"></i>
                    </span>
                  </td>
                </tr>
              </tbody>
            </table>
            <!-- infor -->
            <el-pagination
              style="text-align: right"
              background
              layout="prev, pager, next"
              :page-count="count"
              :page-size="pageSize"
              :page-sizes="pageSizes"
              @current-change="handlePageChange"
              hide-on-single-page
            >
            </el-pagination>

          </div>
        </div>
        <transition name="slideLeft" v-if="isShow">
          <div class="hr-table__detail"  ref="detail">
            <div class="hr-detail__header">
              <span class="hr-detail__title">Thông tin nhân viên</span>
              <span class="hr-detail__close" @click="isShow = false">
                <i class="fa fa-times" aria-hidden="true"></i>
              </span>
            </div>
            <div class="hr-detail__body">
              <ul>
                <li>
                  <span class="hr-detail__label"
                    >Mã nhân viên: <Strong>{{ form.code }}</Strong></span
                  >
                </li>
                <li>
                  <span class="hr-detail__label"
                    >Họ tên: <Strong>{{ form.name }}</Strong></span
                  >
                </li>
                <li>
                  <span class="hr-detail__label"
                    >Ngày sinh: <Strong>{{ form.date }}</Strong></span
                  >
                </li>
                <li>
                  <span class="hr-detail__label"
                    >Email: <Strong>{{ form.email }}</Strong></span
                  >
                </li>
                <li>
                  <span class="hr-detail__label"
                    >Phòng ban:
                    <Strong>{{ form.department.name }}</Strong></span
                  >
                </li>
                <li>
                  <span class="hr-detail__label"
                    >Tổng tiền hỗ trợ phúc lợi:
                    <Strong>{{ form.welfareMoney }} </Strong></span
                  >
                </li>
              </ul>
            </div>
            <br>
            <br>
            <div style="text-align: center;">
                <el-button
                  @click="getEdit(form.id)"
                  slot="reference"
                  style="text-align: center; font: 1em sans-serif"
                  class="btn btn-danger"
                >
                  Sửa thông tin
                </el-button>
                <el-button
                  @click="handleShow(form.id)"
                  slot="reference"
                  style="text-align: center; font: 1em sans-serif"
                  class="btn btn-danger"
                >
                  Kiểm tra phúc lợi
                </el-button>
             
            </div>
          </div>
        </transition>
      </div>
      <div class="hr-image"></div>

<!-- dialog update -->
    <el-dialog
      :visible.sync="isShowUpdate"
      width="900px"
      label-width="100px"
      top="5vh"
      left="150px"
      title="Sửa thông tin nhân viên"
    >
      <div class="row">
        <form id="form-staff2">
          <div class="row">
            <div class="col-6">
              <div>
                <div class="mb-3">
                <label class="form-label"><strong>Mã nhân viên:</strong></label>
                <input
                  v-model="staff.code"
                  type="text"
                  id="code"
                  name="code"
                  class="form-control"
                  required
                  placeholder="Mã nhân viên"
                  />
                </div>
                <div v-show="showValidateNullCode" style="color: red">
                  Mã nhân viên không được để trống
                </div>
              </div>

              <div>
                <div class="mb-3">
                <label class="form-label"
                  ><Strong>Họ tên nhân viên:</Strong></label
                >
                <input
                  v-model="staff.name"
                  type="text"
                  id="name"
                  name="name"
                  class="form-control"
                  required
                  placeholder="Họ tên nhân viên"
                />
                </div>
              </div>
              <div>
                <div class="mb-3">
                <label class="form-label"><Strong>Email:</Strong></label>
                <input
                  name="email"
                  v-model="staff.email"
                  type="email"
                  required
                  class="form-control"
                  placeholder="Email nhân viên"
                />
                </div>
                <div v-show="showValidateNullEmail" style="color: red">
                 Email nhân viên không được để trống
                </div>
              </div>
            </div>

            <div class="col-6">
              <div>
                <div class="mb-3">
                  <label class="form-label"><Strong>Ngày sinh(năm / tháng / ngày):</Strong></label>
                  <input
                    v-model="staff.date"
                    id="date"
                    name="date"
                    required
                    type="text"
                    class="form-control"
                    placeholder="YYYY/MM/DD"
                  />
                </div>
                <div v-show="showValidateNullDate" style="color: red">
                  Ngày sinh nhân viên không được để trống
                </div>
              </div>  

              <div>
                <div class="mb-3">
                  <label class="form-label"
                    ><Strong>Số tiền hỗ trợ phúc lợi:   <span v-if="!staff.welfareMoney==''">
                    {{formatCurrency(staff.welfareMoney)}}
                  </span></Strong></label
                  >
                  <input
                    v-model="staff.welfareMoney"
                    required
                    id="welfareMoney"
                    name="welfareMoney" 
                    type="text"
                    class="form-control"
                    placeholder="Tiền hỗ trợ phúc lợi"
                  />
                </div>
                <div v-show="showValidateNullMoney" style="color: red">
                  Tiền hỗ trợ nhân viên không được để trống
                </div>
              </div> 

              <div>
                  <div class="mb-3">
                  <label class="form-label"><Strong>Phòng ban:</Strong> </label>
                  <select style="height: 55px;"
                    v-model="staff.department"
                    placeholder="Chọn phòng ban"
                    class="form-control"
                    name="department"
                    id="department"
                  >
                    <option v-for="x in departments" :value="x" :key="x.id">
                      {{ x.name }}
                    </option>
                  </select>
                  </div>
                  <div v-show="showValidateNullDepartment" style="color: red">
                    Phòng ban không được để trống
                  </div>
              </div>
            </div>
          </div>

              <div style="text-align: center">
                <button @click.prevent="update(form.id)" class="btn btn-danger">
                  <strong>Cập nhật</strong>
                </button>
              </div>
            </form>

      </div>
    </el-dialog>
 <!-- dialog create -->
    <el-dialog
      :visible.sync="isShowAdd"
      width="900px"
      label-width="100px"
      top="5vh"
      left="150px"
      title="Thêm mới nhân viên"
    >
      <!-- <div class="row">
        <form id="form-staff">
          <div class="row">
            <div class="col-6">
              <div>
                <div class="mb-3">
                <label class="form-label">
                  <strong v-if="showValidateCode" style="color: red">
                    Mã nhân viên đã tồn tại, vui lòng nhập lại!
                  </strong>
                  <strong v-else>Mã nhân viên:</strong>
                </label>
                <input
                  v-model="staff.code"
                  type="text"
                  id="code"
                  name="code"
                  class="form-control"
                  required
                  placeholder="Mã nhân viên"
                  />
                </div>             
              </div>

              <div>
                <div class="mb-3">
                <label class="form-label"
                  ><Strong>Họ tên nhân viên:</Strong></label
                >
                <input
                  v-model="staff.name"
                  type="text"
                  id="name"
                  name="name"
                  class="form-control"
                  required
                  placeholder="Họ tên nhân viên"
                />
                </div>
              </div>
              <div>
                <div class="mb-3">
                <label class="form-label">
                  <strong v-if="showValidateEmail" style="color: red">
                    Email đã tồn tại, vui lòng nhập lại!
                  </strong>  
                  <Strong v-else>Email: </Strong>
                </label>
                <input
                  name="email"
                  v-model="staff.email"
                  type="email"
                  required
                  class="form-control"
                  placeholder="Email nhân viên"
                />
                </div>
              </div>
            </div>

            <div class="col-6">
              <div>
                <div class="mb-3">
                  <label class="form-label"><Strong>Ngày sinh( năm / tháng / ngày):</Strong></label>
                  <input
                    v-model="staff.date"
                    name="date"
                    required
                    type="date"
                    value="staff.date"
                    class="form-control"
                    placeholder="YYYY/DD/MM"
                  />
                </div>
              </div>  

              <div>
                <div class="mb-3">
                  <label class="form-label"
                    ><Strong>Số tiền hỗ trợ phúc lợi:   <span v-if="!staff.welfareMoney==''">
                    {{formatCurrency(staff.welfareMoney)}}
                  </span></Strong></label
                  >
                  <input
                    v-model="staff.welfareMoney"
                    required
                    id="welfareMoney"
                    name="welfareMoney" 
                    type="number"
                    class="form-control"
                    placeholder="Tiền hỗ trợ phúc lợi"
                  />
                </div>
              </div> 

              <div>
                  <div class="mb-3">
                  <label class="form-label"><Strong>Chọn phòng ban:</Strong> </label>
                  <select style="height: 55px;"
                    v-model="staff.department"
                    placeholder="Chọn phòng ban"
                    class="form-control"
                    name="department"
                    id="department"
                    required
                  >
                    <option v-for="x in departments" :value="x.id" :key="x.id">
                      {{ x.name }}
                    </option>
                  </select>
                  </div>
              </div>
            </div>
          </div>
          <div style="text-align: center">
                <button @click.prevent="create" class="btn btn-danger">
                  <strong>Thêm mới</strong>
                </button>
          </div>
              <div v-if="staff.date.match(/^\d{4}\/\d{2}\/\d{2}$/)" style="text-align: center">
                <button @click.prevent="create" class="btn btn-danger">
                  <strong>Thêm mới</strong>
                </button>
              </div>
              <div v-else style="text-align: center">
                <button class="btn btn-danger">
                  <strong>Thêm mới</strong>
                </button>
              </div>
              <button style="text-align: center" @click.prevent="create" class="btn btn-danger">
                  <strong>Thêm mới</strong>
              </button>
            </form>
      </div> -->
      
      <div class="row">
        <form name="myForm">
          <div class="row">
            <div class="col-6">
              <div>
                <div class="mb-3">
                <label class="form-label">
                  <strong v-if="showValidateCode" style="color: red">
                    Mã nhân viên đã tồn tại, vui lòng nhập lại!
                  </strong>
                  <strong v-else>Mã nhân viên:</strong>
                </label>
                <input
                  v-model="staff.code"
                  type="text"
                  id="code"
                  name="code"
                  class="form-control"
                  required
                  placeholder="Mã nhân viên"
                  />
                </div>             
              </div>

              <div>
                <div class="mb-3">
                <label class="form-label"
                  ><Strong>Họ tên nhân viên:</Strong></label
                >
                <input
                  v-model="staff.name"
                  type="text"
                  id="name"
                  name="name"
                  class="form-control"
                  required
                  placeholder="Họ tên nhân viên"
                />
                </div>
              </div>
              <div>
                <div class="mb-3">
                <label class="form-label">
                  <strong v-if="showValidateEmail" style="color: red">
                    Email đã tồn tại, vui lòng nhập lại!
                  </strong>  
                  <Strong v-else>Email: </Strong>
                </label>
                <input
                  name="email"
                  v-model="staff.email"
                  type="email"
                  required
                  class="form-control"
                  placeholder="Email nhân viên"
                />
                </div>
              </div>
            </div>

            <div class="col-6">
              <div>
                <div class="mb-3">
                  <label class="form-label"><Strong>Ngày sinh:</Strong></label>
                  <input
                    v-model="staff.date"
                    name="date"
                    required
                    type="date"
                    value="staff.date"
                    class="form-control"
                    placeholder="YYYY/DD/MM"
                  />
                </div>
              </div>  

              <div>
                <div class="mb-3">
                  <label class="form-label"
                    ><Strong>Số tiền hỗ trợ phúc lợi:   <span v-if="!staff.welfareMoney==''">
                    {{formatCurrency(staff.welfareMoney)}}
                  </span></Strong></label
                  >
                  <input
                    v-model="staff.welfareMoney"
                    required
                    id="welfareMoney"
                    name="welfareMoney" 
                    type="number"
                    class="form-control"
                    placeholder="Tiền hỗ trợ phúc lợi"
                  />
                </div>
              </div> 

              <div>
                  <div class="mb-3">
                  <label class="form-label"><Strong>Chọn phòng ban:</Strong> </label>
                  <select style="height: 55px;"
                    v-model="staff.department"
                    placeholder="Chọn phòng ban"
                    class="form-control"
                    name="department"
                    id="department"
                    required
                  >
                    <option v-for="x in departments" :value="x" :key="x.id">
                      {{ x.name }}
                    </option>
                  </select>
                  </div>
              </div>
            </div>
          </div>
              <button style="text-align: center" @click.prevent="create" class="btn btn-danger">
                  <strong>Thêm mới</strong>
              </button>
        </form>
      </div>
    </el-dialog>
 <!-- dialog xem phuc loi nhan vien -->
    <el-dialog
      :visible.sync="isWelfare"
      width="900px"
      label-width="100px"
      top="5vh"
      left="150px"
      title="Phúc lợi đang hưởng"
      boder=""
    >
    <div label-width="120px" class="pl-table__content">
      <h6>Nhân viên: {{staff.name}} / Tổng tiền hỗ trợ phúc lợi: <strong>{{formatCurrency(staff.welfareMoney)}}</strong>
         / Số tiền đang sử dụng: <strong>{{formatCurrency(Money1 + Money2)}}</strong> 
        <span v-if="staff.welfareMoney < (Money1 + Money2)">
          <Strong style="color: red;">&nbsp;(Lỗi!) </Strong>
        </span>
        <span v-else></span>
        </h6>
          <table>
            <thead>
              <tr>
                <th colspan="1">STT</th>
                <th>Tên phúc lợi</th>
                <th>Đơn giá</th>
                <th>Số lượng</th>
                <th>Thành tiền</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td style="font-weight: bold">I</td>
                <td colspan="3" style="font-weight: bold; text-align: center;">Phúc Lợi Chung</td>
                <td><strong>{{formatCurrency(Money1)}}</strong></td>
              </tr>
              <tr v-for="(item, index) in gerenalWelfares" :key="index">
                <td style="font-weight: bold">{{ index + 1 }}.</td>
                <td>{{ item.name }}</td>
                <td>{{ formatCurrency(item.price) }}</td>
                <td>1</td>
                <td>
                  {{ formatCurrency(item.price) }}
                </td>
              </tr>
              <tr>
                <td style="font-weight: bold">II</td>
                <td colspan="3" style="font-weight: bold;  text-align: center;">Phúc Lợi Cá Nhân Hóa</td>
                <td><strong>{{formatCurrency(Money2)}}</strong></td>
              </tr>
              <tr v-for="(item, index) in welfares" :key="index">
                <td style="font-weight: bold">{{ index + 1 }}.</td>
                <td>{{ item.name }}</td>
                <td>{{ formatCurrency(item.price) }}</td>
                <td>
                  {{item.quantity}}
                </td>
                <td>{{formatCurrency(item.price*item.quantity)}}</td>
              </tr>
            </tbody>
          </table>
        </div>  
    </el-dialog>
    </div>
  </div>
</template>
<script>
import moment from "moment";
import StaffService from "@/service/hrService";
import WelfareApi from "../service/phucLoiService";
let welfareApi = new WelfareApi();
import _ from 'lodash'
export default {
  name: "HrList",
  data() {
    return {
      staffs: [],
      welfares: [],
      welfaresMoney: '',
      gerenalWelfares: [],
      Money1: '',
      Money2: '',
      departments: [],
      text: "",
      staffId: "",
      content: "",
      count: 0,
      pageSize: 10,
      pageSizes: [2, 4, 6],
      multipleSelection: [],
      staff: {
        code: "",
        name: "",
        welfareMoney: "",
        date: "",
        email: "",
        department: "",
      },
      selected: [],
      isShowAdd: false,
      isShowUpdate: false,
      isWelfare: false,
      code: [],
      email: [],
      price: [],
      value: "",
      isShow: false,
      showDialogAdd: true,
      status: true,
      showValidateCode: false,
      showValidateEmail: false,
      showValidateNullCode: false,
      showValidateNullName: false,
      showValidateNullEmail: false,
      showValidateNullDate: false,
      showValidateNullDate2: false,
      showValidateNullMoney: false,
      showValidateNullDepartment: false,
     
    };
  },
  // created(){
  //   this.staff.date = this.formatDate(this.staff.date)
  // },
  methods: {
    showupdateForm() {
      this.isShowUpdate = true;
    },
    showAddForm() {
      this.isShowAdd = true;
    },
    showWelfareForm() {
      this.isWelfare = true;
    },
    getRequestParams(page, pageSize) {
      let params = {};
      if (page) {
        params["page"] = page - 1;
      }
      if (pageSize) {
        params["size"] = pageSize;
      }
      return params;
    },
    handlePageChange(value) {
      this.page = value;
      console.log(this.page);
      this.retrieveStaff();
    },
    handleSelectionChange(val) {
      let ids = [];
      val.forEach((v) => {
        ids.push(v.id);
      });
      this.multipleSelection = [...ids];
      console.log(ids);
      console.log(this.multipleSelection);
    },
    showDialog() {
      this.showDialogAdd = true;
      this.isShow = false;
    },
    showInfo(item) {
      this.isShow = false;
      setTimeout(() => {
        this.form = item;
        this.form.date = this.formatDate(item.date);
        this.form.welfareMoney = item.welfareMoney.toLocaleString("vi", {
          style: "currency",
          currency: "VND",
        });
        this.isShow = true;
        console.log(item.welfareMoney);
      }, 100);
    },
    formatDate(value) {
      if (value) {
        return moment(String(value)).format("YYYY-MM-DD");
      }
    },
    isValidDate(dateString) {
      var regEx = /^\d{4}\/\d{2}\/\d{2}$/;
      if(!dateString.match(regEx)) return false;  // Invalid format
      var d = new Date(dateString);
      var dNum = d.getTime();
      if(!dNum && dNum !== 0) return false; // NaN value, Invalid date
      return d.toISOString().slice(0,10) === dateString;
    },
    formatCurrency(value) {
      return Intl.NumberFormat("vi-VN", {
        style: "currency",
        currency: "VND",
      }).format(value);
    },
    formatPrice(str) {
      return str
        .split("")
        .reverse()
        .reduce((prev, next, index) => {
          return (index % 3 ? next : next + ",") + prev;
        });
    },
    reset() {
      this.staff.code = "";
      this.staff.date = "";
      this.staff.department = "";
      this.staff.email = "";
      this.staff.name = "";
      this.staff.welfareMoney = "";
    },
    retrieveStaff() {
      const params = this.getRequestParams(this.page, this.pageSize);
      StaffService.getAll(params)
        .then((response) => {
          this.staffs = response.data;
          // this.staffs.content =
          this.count = response.data.totalPages;
          this.itemCount = response.data.totalElements;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    
    onFind() {
      console.log(this.text);
      StaffService.getFind(this.text)
        .then((response) => {
          this.staffs = response.data;
          this.count = response.data.totalPages;
          this.itemCount = response.data.totalElements;
          console.log(response.data);
        })
        .catch((e) => {
          console.log(e);
        });
    },

    getDepartments() {
      StaffService.getDepartments().then((response) => {
        this.departments = response.data;
        console.log(response.data);
      });
    },
    handleShow(id) {
      this.isWelfare = true
      this.staffId = id;
      console.log(id);
      welfareApi.getAllWelfareWithQuantity(id).then((response) => {
        this.welfares = response.data;
        this.Money2  = 0;
        for(let item of this.welfares){
          this.Money2 += item.price * item.quantity
        }

      });
      StaffService.getGeneralWelfare().then((response) => {
        this.gerenalWelfares = response.data;
        this.Money1 = _.sumBy(response.data, 'price')
      });
      StaffService.getStaff(id)
      .then((response) => {
        this.staff = response.data;
      });
      this.status = false;
    },

    getEdit(id) {
      this.showupdateForm()
      StaffService.getStaff(id).then((response) => {
        this.staff = response.data;
        this.staff.date = this.formatDate(response.data.date);
        console.log(id);
      });
    },
    handlDeletes () {
      this.$confirm(
        "Bạn có chắc sẽ khóa những nhân viên này không. Continue?",
        "Warning",
        {
          confirmButtonText: "OK",
          cancelButtonText: "Cancel",
          type: "warning",
        }
      )
      .then(() => {
          StaffService.deletes(this.selected)
          this.$message({
            type: "success",
            message: "lock up completed",
          });
          this.$router.go()
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "lock up canceled",
          });
        });
      
    },
    unlockStaff(id){
      StaffService.unlookStaff(id);
      this.$notify({
            title: "Success",
            message: "Mở khóa thành công!",
            type: "success",
          });
      this.$router.go()
    

    },
    deleteStaff(id) {
      this.$confirm(
        "Bạn có chắc sẽ khóa nhân viên này không. Continue?",
        "Warning",
        {
          confirmButtonText: "OK",
          cancelButtonText: "Cancel",
          type: "warning",
        }
      )
        .then(() => {
          StaffService.deleteStaff(id);
          this.$message({
            type: "success",
            message: "lock up completed",
          });
          this.$router.go()
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "lock up canceled",
          });
        });
    },

    codeExists(text) {
      return this.code.some(function (el) {
        return el.text === text;
      });
    },

    checkCode() {
      console.log(this.staff.code);
      if (this.code.includes(this.staff.code)) {
        this.showValidateCode = true;
      } else {
        this.showValidateCode = false;
      }
      if (this.email.includes(this.staff.email)) {
        this.showValidateEmail = true;
      } else {
        this.showValidateEmail = false;
      }
      if (this.staff.code === "") {
        this.showValidateNullCode = true;
      } else {
        this.showValidateNullCode = false;
      }
      if (this.staff.name === "") {
        this.showValidateNullName = true;
      } else {
        this.showValidateNullName = false;
      }
      if (this.staff.email === "") {
        this.showValidateNullEmail = true;
      } else {
        this.showValidateNullEmail = false;
      }
      if (this.staff.date === "") {
        this.showValidateNullDate = true;
      } else {
        this.showValidateNullDate = false;
      }
      if (!this.staff.date.match(/^\d{4}\/\d{2}\/\d{2}$/)) {
        this.showValidateNullDate2 = true;
      } else {
        this.showValidateNullDate2 = false;
      }
      if (this.staff.department === "") {
        this.showValidateNullDepartment = true;
      } else {
        this.showValidateNullDepartment = false;
      }
      if (this.staff.welfareMoney === "") {
        this.showValidateNullMoney = true;
      } else {
        this.showValidateNullMoney = false;
      }
      
    },

    listCode() {
      StaffService.getCode()
        .then((response) => {
          this.code = response.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    listEmail() {
      StaffService.getEmail()
        .then((response) => {
          this.email = response.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    // create() {
    //   if (this.code.includes(this.staff.code)){
    //     this.showValidateCode = true;
    //     this.$notify({
    //           title: "Warning",
    //           message: "Bạn nhập sai thông tin",
    //           type: "warning",
    //         });
    //   }
    //   else if (this.email.includes(this.staff.email)) {
    //           this.showValidateCode = false;
    //           this.showValidateEmail = true;
    //           this.$notify({
    //                 title: "Warning",
    //                 message: "Bạn nhập sai thông tin",
    //                 type: "warning",
    //               });
    //     }
 
    //   else {
    //     this.showValidateCode = false;
    //     this.showValidateEmail = false;
    //     let form = document.querySelector("#form-staff");
    //     let formdata = new FormData(form);
    //     // moment(String(value)).format("YYYY/MM/DD");
    //     StaffService.createStaff(formdata)
    //       .then((response) => {
    //         console.log(response.data);
    //         this.retrieveStaff();
    //         this.$notify({
    //           title: "Success",
    //           message: "Thêm mới nhân viên thành công",
    //           type: "success",
    //         });
    //         this.reset();
    //       })
    //       .catch((e) => {
    //         console.log(e);
    //         this.$notify({
    //           title: "Warning",
    //           message: "Bạn nhập sai thông tin",
    //           type: "warning",
    //         });
    //       });

    //   }
    // },
    create() {
      var data = {
          code: this.staff.code,
          name: this.staff.name,
          email: this.staff.email,
          date: this.staff.date,
          department: this.staff.department,
          welfareMoney: this.staff.welfareMoney,
        }
      let x = document.forms["myForm"]["code"].value;
      if (x == "") {
        this.$notify({
                    title: "Warning",
                    message: "Mã nhân viên không được để trống!",
                    type: "warning",
                    size: 100,
                  });
        return false;
      }  
      else if (this.code.includes(this.staff.code)){
        this.showValidateCode = true;
        this.$notify({
              title: "Warning",
              message: "Bạn nhập sai thông tin",
              type: "warning",
            });
      }
      else if (this.email.includes(this.staff.email)) {
              this.showValidateCode = false;
              this.showValidateEmail = true;
              this.$notify({
                    title: "Warning",
                    message: "Bạn nhập sai thông tin",
                    type: "warning",
                  });
        }
 
      else {
       
        this.showValidateCode = false;
        this.showValidateEmail = false;
     
        // moment(String(value)).format("YYYY/MM/DD");
        StaffService.createStaff2(data)
          .then((response) => {
            console.log(response.data);
            this.retrieveStaff();
            this.$notify({
              title: "Success",
              message: "Thêm mới nhân viên thành công",
              type: "success",
            });
            this.reset();
          })
          .catch((e) => {
            console.log(e);
            this.$notify({
              title: "Warning",
              message: "Bạn nhập sai thông tin",
              type: "warning",
            });
          });

      }
    },
    update(id) {
      let form = document.querySelector("#form-staff2");
      let formdata = new FormData(form);

      StaffService.updateStaff(id, formdata)
        .then((response) => {
          console.log(response.data);
          this.retrieveStaff();
          this.$notify({
            title: "Success",
            message: "Cập nhật thông tin nhân viên thành công",
            type: "success",
          });
          this.reset();
        })
        .catch((e) => {
          console.log(e);
          this.$notify({
            title: "Warning",
            message: "Bạn nhập sai thông tin",
            type: "warning",
          });
        });
    },
    validateForm() {
      let x = document.forms["myForm"]["code"].value;
      if (x == "") {
        this.$notify({
                    title: "Warning",
                    message: "Mã nhân viên không được để trống!",
                    type: "warning",
                    size: 100,
                  });
        return false;
      }  
    }
   
  },  
  mounted() {
    // this.staff.date = this.formatDate(this.staff.date)
    this.retrieveStaff();
    this.getDepartments();
    this.listCode();
    this.listEmail();
  },
};
</script>

<style>
.hr-image {
  background-image: url("@/assets/image/profiledata.svg");
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  width: 600px;
  height: 600px;
  position: absolute;
  bottom: 0;
  right: 0;
}
.hr-list {
  background: linear-gradient(90deg, #e4c9ac 0%, rgba(255, 255, 255, 0) 100%),
    #e3c1d3;
  width: 100%;
  height: 100%;
}
.hr-info {
  display: flex;
}
.hr-title {
  text-align: center;
  font-size: 34px;
  font-weight: 600;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen,
    Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
  background: rgba(255, 255, 255, 0.13);
  padding: 6px 0px;
}
.icon-delete {
  cursor: pointer;
}
.icon-delete i {
  color: #f00;
}
.icon-edit {
  cursor: pointer;
}
.icon-edit i {
  color: #409eff;
}
.hr-table {
  width: fit-content;
  margin-left: 35px;
  margin-right: 35px;
  z-index: 2;
}
.hr-table__content table {
  width: 100%;
  border-collapse: collapse;
}
.hr-table__content table tr {
  background: #f2e7ddf8;
  text-align: center;
}
.hr-table__content table tr th {
  border-right: 1px solid #e4c9ac;
  padding: 14px;
}
.hr-table__content table tr td {
  padding: 8px;
  height: 30px;
  line-height: 30px;
}
.hr-table__content table thead tr {
  background: #fdf9f8;
}
.hr-table__content table thead th {
  font-weight: 600;
  font-size: 14px;
}
.hr-table__content table tbody tr {
  border-bottom: 1px solid #94806af8;
}
.hr-table__content table tbody tr:hover {
  background: #fdf9f8;
}
.hr-table__content table tbody tr:last-child {
  border-bottom: none;
}
.hr-info {
  display: flex;
}
.hr-table__detail {
  width: 200px;
  height: 500px;
  background: #fff;
  border-radius: 30px;
  flex-direction: column;
  flex: 0.7;
  z-index: 2;
}
.hr-detail__header {
  padding: 24px;
  font-size: 24px;
  font-weight: 700;
  display: flex;
  justify-content: space-between;
}
.hr-detail__title {
  color: #000;
  border-bottom: 2px solid #f004;
}
.hr-detail__close {
  color: #f00;
  cursor: pointer;
}
.hr-detail__body {
  flex: 1;
}
.hr-detail__body ul {
  margin: 0;
}
.hr-detail__body ul li {
  padding: 8px 0px;
}
.hr-detail__footer {
  display: flex;
  justify-content: flex-end;
}
.hr-detail__button {
  color: #f00 !important;
  font-size: 14px !important;
  font-weight: 600 !important;
}
.hr-detail__button:hover {
  background-color: rgba(255, 0, 0, 0.1) !important;
  border-color: rgba(255, 0, 0, 0.1) !important;
}
.hr-detail__button:focus {
  background-color: rgba(255, 0, 0, 0.2) !important;
  border-color: rgba(255, 0, 0, 0.2) !important;
}
.hr-detail__button:active {
  background-color: rgba(255, 0, 0, 0.3) !important;
  border-color: rgba(255, 0, 0, 0.3) !important;
}
.hr-function {
  margin-top: 40px;
}
.el-select {
  width: 100%;
}
.el-input__inner {
  border-radius: 0px !important;
  font-family: "Poppins,sans-serif";
  background-blend-mode: saturation;
  font-style: normal;
  font-weight: 600;
  font-size: 20;
}
.el-form-item__label {
  color: #f00 !important;
  font-size: 20px;
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
.pl-table__content table {
  width: 100%;
  border-collapse: collapse;
}
.pl-table__content table tr {
  background: rgba(217, 217, 217, 0.13);
}
.pl-table__content table tr th {
  border-right: 1px solid #c7c7c7;
  padding: 14px;
}
.pl-table__content table tr td {
  padding: 20px;
  line-height: 30px;
  height: 30px;
}
.pl-table__content table thead tr {
  background-color: #d9d9d9;
  position: sticky;
  top: 0;
}
.pl-table__content table thead th {
  font-size: 14px;
  font-weight: 600;
}
.pl-table__content table tr td {
  border-right: 1px solid #c7c7c7;
}
.pl-table__content table tbody tr {
  border-bottom: 1px solid #c7c7c7;
}
.pl-table__content table tbody tr:hover {
  background-color: pink;
}

</style>