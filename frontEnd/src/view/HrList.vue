  <template >
  <div>
    <div class="hr-list" v-if="hasRole">
      <div class="hr-content">
        <div class="hr-title"><strong> QUẢN LÝ NHÂN VIÊN </strong></div>
        <div class="hr-info mt-2">
          <div class="hr-table">
            <el-form
              :inline="true"
              class="demo-form-inline d-flex justify-content-between"
            >
              <span v-if="staffStatus === 0">
                <el-form-item>
                  <el-input
                    style="width: 300px"
                    v-model="text"
                    placeholder="Nhập tên/ mã nhân viên/ phòng ban"
                  >
                    <i slot="prefix" class="el-input__icon el-icon-search"></i>
                  </el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="warning" @click="onFind"
                    >Tìm kiếm
                  </el-button>
                </el-form-item>

                <el-form-item v-if="selected.length !== 0">
                  <el-button class="btn btn-danger" @click="handlDeletes">
                    <i class="el-icon-lock"></i> Khóa
                  </el-button>
                </el-form-item>
              </span>

              <span>
                <el-form-item>
                  <el-button
                    type="erorr"
                    class="btn btn-danger"
                    @click="showAddForm"
                  >
                    <i class="el-icon-plus"></i> Thêm mới
                  </el-button>

                  <button
                    type="erorr"
                    class="btn btn-success"
                    @click.prevent="showImportForm"
                    style="margin: 10px"
                  >
                    <i class="fas fa-file-excel"></i>
                    <span class="pl-3"><Strong>Nhập từ Excel</Strong></span>
                  </button>
                </el-form-item>
              </span>
            </el-form>

            <div class="hr-table__content">
              <div v-if="staffStatus === 0">
                <table>
                  <thead>
                    <tr>
                      <th><input type="checkbox" v-model="selectAll" /></th>
                      <th width="50px">Khóa</th>
                      <th width="70px">STT</th>
                      <th width="200px">Họ và tên</th>
                      <th width="200px">Mã nhân viên</th>
                      <th width="240px">Phòng ban</th>
                      <!-- <th wtdth="80px">Số tiền hỗ trợ</th> -->
                      <th width="170x">Trạng thái</th>
                      <th width="80px">Chi tiết</th>
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
                          required
                        />
                      </td>
                      <td>
                        <!-- <span class="icon-delete" @click="deleteStaff(item.id)">
                        <i class="fa fa-trash" aria-hidden="true"></i>
                      </span> -->
                        <span
                          v-if="
                            item.status == 0 ||
                            item.status == 2 ||
                            item.status == 3
                          "
                        >
                          <el-button
                            @click="deleteStaff(item.id, index)"
                            type="danger"
                            icon="el-icon-lock"
                            circle
                          >
                          </el-button>
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
                      <td style="text-align: left">{{ item.name }}</td>
                      <td style="text-align: left">{{ item.code }}</td>
                      <td style="text-align: left">
                        {{ item.department.name }}
                      </td>
                      <!-- <td style="text-align: right;">{{formatCurrency(item.welfareMoney)}}</td> -->
                      <td style="text-align: left">
                        <div style="color: seagreen" v-if="item.status == 0">
                          Đang hoạt động
                        </div>
                        <div style="color: red" v-if="item.status == 1">
                          Tạm ngưng
                        </div>
                        <div style="color: goldenrod" v-if="item.status == 2">
                          Đang xét duyệt tiền
                        </div>
                        <div style="color: goldenrod" v-if="item.status == 3">
                          Chưa cập nhật tiền
                        </div>
                      </td>
                      <td>
                        <span class="icon-edit" @click="showInfo(item)">
                          <i class="fa fa-circle-info"></i>
                          <i class="el-icon-caret-right"></i>
                        </span>
                      </td>
                    </tr>
                  </tbody>
                </table>
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
              <div v-if="staffStatus === 1">
                <table>
                  <thead>
                    <tr>
                      <th><input type="checkbox" v-model="selectAll2" /></th>
                      <th width="70px">STT</th>
                      <th width="200px">Họ và tên</th>
                      <th width="200px">Mã nhân viên</th>
                      <th width="240px">Phòng ban</th>
                      <!-- <th wtdth="80px">Số tiền hỗ trợ</th> -->
                      <th width="170x">Trạng thái</th>
                      <th width="80px">Chi tiết</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(item, index) in staffs2.content" :key="index">
                      <td>
                        <input
                          v-if="item.status === 3"
                          type="checkbox"
                          :value="item.id"
                          v-model="selected"
                          :ref="item.id"
                          required
                        />
                        <input
                          v-if="item.status === 2"
                          type="checkbox"
                          :value="item.id"
                          v-model="selected"
                          :ref="item.id"
                          disabled
                        />
                      </td>
                      <td>{{ index + 1 }}</td>
                      <td style="text-align: left">{{ item.name }}</td>
                      <td style="text-align: left">{{ item.code }}</td>
                      <td style="text-align: left">
                        {{ item.department.name }}
                      </td>
                      <!-- <td style="text-align: right;">{{formatCurrency(item.welfareMoney)}}</td> -->
                      <td style="text-align: left">
                        <div v-if="item.status == 2">Đang chờ duyệt</div>
                        <div v-if="item.status == 3">Nhân viên mới</div>
                      </td>
                      <td>
                        <span class="icon-edit" @click="showInfo(item)">
                          <i class="fa fa-circle-info"></i>
                          <i class="el-icon-caret-right"></i>
                        </span>
                      </td>
                    </tr>
                  </tbody>
                </table>
                <el-pagination
                  style="text-align: right"
                  background
                  layout="prev, pager, next"
                  :page-count="count2"
                  :page-size="pageSize2"
                  :page-sizes="pageSizes2"
                  @current-change="handlePageChange2"
                  hide-on-single-page
                >
                </el-pagination>
              </div>
            </div>
          </div>
          <transition name="slideLeft" v-if="isShow">
            <div class="hr-table__detail" ref="detail">
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

              <br />
              <br />
              <div style="text-align: center">
                <el-button
                  @click="getEdit(form.id)"
                  slot="reference"
                  style="text-align: center; font: 1em sans-serif"
                  class="btn btn-danger"
                >
                  Sửa thông tin
                </el-button>
                <el-button
                  v-if="form.status === 0 || form.status === 1"
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
        <!-- dialog them moi excel -->
        <el-dialog
          :visible.sync="isShowImport"
          width="500px"
          label-width="100px"
          top="5vh"
          title="temp"
        >
          <span slot="title" class="title-dialog">
            <Strong>Nhập danh sách nhân viên</Strong></span
          >
          <div class="row">
            <div class="upload-container">
              <div class="border-container">
                <p>
                  Để sử dụng chức năng này, vui lòng tải xuống
                  <a href="/files/staff/default_template.xlsx" download
                    ><u>mẫu</u></a
                  >
                  này và nhập chính xác thông tin vào mẫu. Bạn cũng có thể sử
                  dụng
                  <a href="/files/staff/staff_template.xlsx" download
                    ><u> mẫu</u></a
                  >
                  ví dụ để kiểm tra.
                </p>
                <p style="padding-bottom: 20px">
                  Kéo và thả tệp vào đây hoặc
                  <input
                    type="file"
                    name="file"
                    id="fileInput"
                    ref="file"
                    @change="handleFilesUpload"
                  />
                </p>
                <p class="text-center mt-0 mb-2">
                  <el-button
                    type="erorr"
                    class="btn btn-danger"
                    @click="submitFiles"
                    v-if="isShowSubmitFiles"
                  >
                    Nhập file
                  </el-button>
                </p>
              </div>
            </div>
          </div>
        </el-dialog>
        <!-- dialog cap nhat tien PL -->

        <el-dialog
          :visible.sync="isUpdateMoney"
          width="500px"
          label-width="100px"
          top="5vh"
          left="150px"
          title="temp"
        >
          <span slot="title" class="title-dialog">
            <Strong>Số tiền hỗ trợ</Strong></span
          >
          <div class="row">
            <form name="form-updateMoney">
              <div class="row">
                <div>
                  <div class="mb-3">
                    <label class="form-label"
                      ><Strong>Số tiền hỗ trợ phúc lợi:</Strong></label
                    >
                    <input
                      v-on:blur="keyupUpdateMoney"
                      @keydown="keyupUpdateMoney"
                      v-model="moneyUpdate"
                      required
                      id="moneyUpdate"
                      name="moneyUpdate"
                      type="text"
                      class="form-control"
                      placeholder="Tiền hỗ trợ phúc lợi"
                    />
                  </div>
                </div>
              </div>
              <div style="text-align: center">
                <button
                  @click.prevent="handlUpdateMoney()"
                  class="btn btn-danger"
                  style="width: 100px"
                >
                  <strong>Gửi</strong>
                </button>
              </div>
            </form>
          </div>
        </el-dialog>
        <!-- dialog update -->
        <el-dialog
          :visible.sync="isShowUpdate"
          width="900px"
          label-width="100px"
          top="5vh"
          left="150px"
          title="temp"
        >
          <span class="title-dialog" slot="title"
            ><strong>Sửa thông tin nhân viên</strong></span
          >
          <div class="row">
            <form name="form-update">
              <div class="row">
                <div class="col-6">
                  <div>
                    <div class="mb-3">
                      <label class="form-label"
                        ><strong>Mã nhân viên:</strong></label
                      >
                      <input
                        v-model="staffUpdate.code"
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
                        v-model="staffUpdate.name"
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
                        id="email"
                        v-model="staffUpdate.email"
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
                      <label class="form-label"
                        ><Strong>Ngày sinh(năm / tháng / ngày):</Strong></label
                      >
                      <input
                        v-model="staffUpdate.date"
                        id="date"
                        name="date"
                        required
                        type="date"
                        class="form-control"
                        placeholder="YYYY/MM/DD"
                      />
                    </div>
                  </div>

                  <div>
                    <div class="mb-3">
                      <label class="form-label"
                        ><Strong>Số tiền hỗ trợ phúc lợi:</Strong></label
                      >
                      <input
                        @keyup="keyupStaffUpdate"
                        v-model="staffUpdate.welfareMoney"
                        @blur="keyupStaffUpdate"
                        required
                        id="welfareMoney"
                        name="welfareMoney"
                        type="text"
                        class="form-control"
                        placeholder="Tiền hỗ trợ phúc lợi"
                        disabled
                      />
                    </div>
                  </div>

                  <div>
                    <div class="mb-3">
                      <label class="form-label"
                        ><Strong>Phòng ban:</Strong>
                      </label>
                      <select
                        v-model="staffUpdate.department"
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
          title="temp"
        >
          <span
            slot="title"
            style="
              color: red;
              font-size: 30px;
              text-align: center;
              display: block;
            "
            ><Strong>Thêm mới nhân viên</Strong></span
          >
          <div class="row">
            <form name="myForm">
              <div class="row">
                <div class="col-6">
                  <div>
                    <div class="mb-3">
                      <label class="form-label">
                        <strong
                          >Mã nhân viên
                          <span style="color: red">(*)</span>:</strong
                        >
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
                        ><Strong
                          >Họ tên nhân viên
                          <span style="color: red">(*)</span>:</Strong
                        ></label
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
                        <Strong
                          >Email <span style="color: red">(*)</span>:
                        </Strong>
                      </label>
                      <input
                        id="email"
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
                      <label class="form-label"
                        ><Strong
                          >Ngày sinh
                          <span style="color: red">(*)</span>:</Strong
                        ></label
                      >
                      <input
                        v-model="staff.date"
                        id="date"
                        name="date"
                        required
                        type="date"
                        value="staff.date"
                        class="form-control"
                        placeholder="YYYY/DD/MM"
                      />
                    </div>
                  </div>
                  <!-- 
                  <div>
                    <div class="mb-3">
                      <label class="form-label"><Strong>Số tiền hỗ trợ phúc lợi <span
                            style="color: red;"></span>:</Strong></label>
                      <input v-model="staff.welfareMoney" required v-on:blur="keyupStaff" @keyup="keyupStaff" 
                        id="welfareMoney" name="welfareMoney" type="hidden"    class="form-control"
                        placeholder="Tiền hỗ trợ phúc lợi" /> 
                    </div>
                  </div> -->

                  <div>
                    <div class="mb-3">
                      <label class="form-label"
                        ><Strong
                          >Chọn phòng ban
                          <span style="color: red">(*)</span>:</Strong
                        >
                      </label>
                      <select
                        style="height: 36px"
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
              <div style="text-align: center">
                <button
                  @click.prevent="create"
                  class="btn btn-danger"
                  style="margin: 10px"
                >
                  <strong>Thêm mới</strong>
                </button>
              </div>
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
          title="temp"
          boder=""
        >
          <span slot="title" class="title-dialog"
            ><strong>Phúc lợi đang hưởng</strong>
          </span>
          <div label-width="120px" class="pl-table__content">
            <h6>
              Nhân viên: {{ staff.name }} / Tổng tiền hỗ trợ phúc lợi:
              <strong>{{ formatCurrency(staff.welfareMoney) }}</strong>
              / Số tiền đang sử dụng:
              <strong>{{ formatCurrency(Money1 + Money2) }}</strong>
              <span v-if="staff.welfareMoney < Money1 + Money2">
                <Strong style="color: red">&nbsp;(Lỗi!) </Strong>
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

                  <td colspan="3" style="font-weight: bold; text-align: center">
                    Phúc Lợi Chung
                  </td>
                  <td>
                    <strong>{{ formatCurrency(Money1) }}</strong>
                  </td>
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

                  <td colspan="3" style="font-weight: bold; text-align: center">
                    Phúc Lợi Cá Nhân Hóa
                  </td>
                  <td>
                    <strong>{{ formatCurrency(Money2) }}</strong>
                  </td>
                </tr>
                <tr v-for="(item, index) in welfares" :key="index">
                  <td style="font-weight: bold">{{ index + 1 }}.</td>
                  <td>{{ item.name }}</td>
                  <td>{{ formatCurrency(item.price) }}</td>
                  <td>
                    {{ item.quantity }}
                  </td>
                  <td>{{ formatCurrency(item.price * item.quantity) }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </el-dialog>
      </div>
    </div>
    <Error401 v-if="!hasRole"></Error401>
  </div>
</template>
  <script>
  /* eslint-disable */
import moment from "moment";
import StaffService from "@/service/hrService";
import WelfareApi from "../service/phucLoiService";
import Error401 from "./401-error.vue";
let welfareApi = new WelfareApi();
import _ from "lodash";
export default {
  name: "HrList",
  components: {Error401 },
  computed: {
    selectAll: {
      get: function () {
        return this.staffs.content
          ? this.selected.length == this.staffs.content.length
          : false;
      },
      set: function (value) {
        var selected = [];

        if (value) {
          this.staffs.content.forEach(function (staff) {
            selected.push(staff.id);
          });
        }
        this.selected = selected;
      },
    },
    selectAll2: {
      get: function () {
        return this.staffs2.content
          ? this.selected.length == this.staffs2.content.length
          : false;
      },
      set: function (value) {
        var selected = [];

        if (value) {
          this.staffs2.content.forEach(function (staff) {
            selected.push(staff.id);
          });
        }
        this.selected = selected;
      },
    },
  },
  data() {
    return {
      componentKey: 0,
      fullscreenLoading: false,
      hasRole: false,
      staffs: [],
      staffs2: [],
      welfares: [],
      welfaresMoney: "",
      gerenalWelfares: [],
      Money1: "",
      Money2: "",
      departments: [],
      text: "",
      staffId: "",
      content: "",
      count: 0,
      pageSize: 10,
      pageSizes: [2, 4, 6],
      count2: 0,
      pageSize2: 10,
      pageSizes2: [2, 4, 6],
      multipleSelection: [],
      staff: {
        code: "",
        name: "",
        welfareMoney: "",
        date: "",
        email: "",
        department: "",
      },
      staffUpdate: {
        code: "",
        name: "",
        welfareMoney: "",
        date: "",
        email: "",
        department: "",
      },
      staffStatus: 0,
      isShowselected: false,
      selected: [],
      isShowAdd: false,
      isShowNewStaff: false,
      isShowImport: false,
      isShowUpdate: false,
      isWelfare: false,
      isUpdateMoney: false,
      isShowSubmitFiles: false,
      code: [],
      email: [],
      code2: [],
      email2: [],
      price: [],
      value: "",
      isShow: false,
      showDialogAdd: true,
      status: true,
      moneyUpdate: "",
      ischeckcode: true,
      ischeckName: true,
      ischeckEmail: true,
      ischeckDate: true,
      ischeckMoney: true,
      ischeckDepartment: true,
      user: {},
    };
  },
  // created(){
  //   this.staff.date = this.formatDate(this.staff.date)
  // },
  methods: {
    addCommas(nStr) {
      var x, x1, x2;

      nStr += "";
      x = nStr.split(".");
      x1 = x[0];
      x2 = x.length > 1 ? "," + x[1] : "";
      var rgx = /(\d+)(\d{3})/;
      while (rgx.test(x1)) {
        x1 = x1.replace(rgx, "$1" + "," + "$2");
      }
      return x1 + x2;
    },
    isNumber: function (evt) {
      evt = evt ? evt : window.event;
      var charCode = evt.which ? evt.which : evt.keyCode;
      if (
        charCode > 31 &&
        (charCode < 48 || charCode > 57) &&
        charCode !== 46
      ) {
        evt.preventDefault();
      } else {
        return true;
      }
    },
    keyupStaff() {
      this.staff.welfareMoney = this.addCommas(
        this.staff.welfareMoney.replace(/,/g, "")
      );
      this.$emit("input", parseInt(this.staff.welfareMoney.replace(/,/g, "")));
    },
    keyupStaffUpdate() {
      this.staffUpdate.welfareMoney = this.addCommas(
        this.staffUpdate.welfareMoney.replace(/,/g, "")
      );
      this.$emit(
        "input",
        parseInt(this.staffUpdate.welfareMoney.replace(/,/g, ""))
      );
    },
    keyupUpdateMoney() {
      this.moneyUpdate = this.addCommas(this.moneyUpdate.replace(/,/g, ""));
      this.$emit("input", parseInt(this.staff.welfareMoney.replace(/,/g, "")));
    },
    showupdateForm() {
      this.isShowUpdate = true;
    },
    showAddForm() {
      this.isShowAdd = true;
    },
    showNewStaffForm() {
      this.isShowNewStaff = true;
    },
    showImportForm() {
      this.isShowImport = true;
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
      this.retrieveStaff();
    },
    handlePageChange2(value) {
      this.page = value;
      this.retrieveStaff2();
    },
    handleSelectionChange(val) {
      let ids = [];
      val.forEach((v) => {
        ids.push(v.id);
      });
      this.multipleSelection = [...ids];
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
      }, 100);
    },
    formatDate(value) {
      if (value) {
        return moment(String(value)).format("YYYY-MM-DD");
      }
    },
    isValidDate(dateString) {
      var regEx = /^\d{4}\/\d{2}\/\d{2}$/;

      if (!dateString.match(regEx)) return false; // Invalid format
      var d = new Date(dateString);
      var dNum = d.getTime();
      if (!dNum && dNum !== 0) return false; // NaN value, Invalid date
      return d.toISOString().slice(0, 10) === dateString;
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
          this.hasRole =true;
          // this.staffs.content =
          this.count = response.data.totalPages;
          this.itemCount = response.data.totalElements;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    retrieveStaff2() {
      const params = this.getRequestParams(this.page, this.pageSize);
      StaffService.getAll2(params)
        .then((response) => {
          this.staffs2 = response.data;
          this.hasRole =true;
          // this.staffs.content =
          this.count2 = response.data.totalPages;
          this.itemCount2 = response.data.totalElements;
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
        })
        .catch((e) => {
          console.log(e);
        });
    },

    getDepartments() {
      StaffService.getDepartments().then((response) => {
        this.departments = response.data;
      });
    },
    handleShow(id) {
      this.isWelfare = true;
      this.staffId = id;
      welfareApi.getAllWelfareWithQuantity(id).then((response) => {
        this.welfares = response.data;
        this.Money2 = 0;
        for (let item of this.welfares) {
          this.Money2 += item.price * item.quantity;
        }
      });
      StaffService.getGeneralWelfare().then((response) => {
        this.gerenalWelfares = response.data;
        this.Money1 = _.sumBy(response.data, "price");
      });

      StaffService.getStaff(id).then((response) => {
        this.staff = response.data;
      });
      this.status = false;
    },

    getEdit(id) {
      this.retrieveStaff();
      this.showupdateForm();
      StaffService.getEmail2(id).then((response) => {
        this.email2 = response.data;
      });
      StaffService.getCode2(id).then((response) => {
        this.code2 = response.data;
      });
      StaffService.getStaff(id).then((response) => {
        this.staffUpdate = response.data;
        this.staffUpdate.welfareMoney = this.addCommas(
          response.data.welfareMoney
        );
        this.staffUpdate.date = this.formatDate(response.data.date);
      });
    },
    handlDeletes() {
      this.$confirm(
        "Bạn có chắc sẽ khóa những nhân viên này không. Continue?",
        "Warning",
        {
          confirmButtonText: "Có",
          cancelButtonText: "Hủy",
          type: "warning",
        }
      )
        .then(() => {
          StaffService.deletes(this.selected);
          this.loading();
          this.$message({
            type: "success",
            message: "Khóa thành công!",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "Đã hủy khóa!",
          });
        });
    },
    loading() {
      const loading = this.$loading({
        lock: true,
        text: "Loading",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)",
      });
      setTimeout(() => {
        loading.close();
        this.$router.go();
      }, 1200);
    },
    handlUpdateMoney() {
      let money = this.moneyUpdate.replace(/,/g, "");
      let x = document.forms["form-updateMoney"]["moneyUpdate"].value;
      if (x == "") {
        this.$notify({
          title: "Warning",
          message: "Tổng tiền không được để trống!",
          type: "warning",
        });
        document.getElementById("moneyUpdate").focus();
        return false;
      }
      if (money < 0) {
        this.$notify({
          title: "Warning",
          message: "Tổng tiền phải là số dương!",
          type: "warning",
        });
        document.getElementById("moneyUpdate").focus();
        return false;
      }
      if (isNaN(money)) {
        this.$notify({
          title: "Warning",
          message: "Tổng tiền phải là số!",
          type: "warning",
        });
        document.getElementById("moneyUpdate").focus();
        return false;
      }
      this.$confirm(
        "Bạn có chắc sẽ cập nhật tiền cho những nhân viên này không?",
        "Warning",
        {
          confirmButtonText: "Có",
          cancelButtonText: "Hủy",
          type: "warning",
        }
      )
        .then(() => {
          var username = this.user.userName;
          StaffService.updateMoney2(money, this.selected, username).then(
            (response) => {
              console.log(response.data);
              this.retrieveStaff();
              this.retrieveStaff2();
              this.selected = [];
              this.isUpdateMoney = false;
            }
          );
          this.$message({
            type: "success",
            message: "Đã gửi yêu cầu!",
          });
        })
        .catch(() => {
          this.loading();
          this.$message({
            type: "info",
            message: "Hủy!",
          });
        });
    },

    unlockStaff(id) {
      StaffService.unlookStaff(id)
        .then((response) => {
          console.log(response);
        })
        .catch((e) => {
          console.log(e);
        });
      // this.loading();
      this.$notify({
        title: "Success",
        message: "Mở khóa thành công!",
        type: "success",
      });

      this.loading();
    },
    deleteStaff(id) {
      this.$confirm(
        "Bạn có chắc sẽ khóa nhân viên này không. Continue?",
        "Warning",
        {
          confirmButtonText: "Có",
          cancelButtonText: "Hủy",
          type: "warning",
        }
      )
        .then(() => {
          StaffService.deleteStaff(id);
          this.loading();
          this.$message({
            type: "success",
            message: "lock up completed",
          });
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
    create() {
      let moneyStaff = this.staff.welfareMoney.replace(/,/g, "");
      var data = {
        code: this.staff.code,
        name: this.staff.name,
        email: this.staff.email,
        date: this.staff.date,
        department: this.staff.department,
        welfareMoney: moneyStaff,
      };
      let x = document.forms["myForm"]["code"].value;
      if (x == "") {
        this.$notify({
          title: "Warning",
          message: "Mã nhân viên không được để trống!",
          type: "warning",
        });
        document.getElementById("code").focus();
        this.ischeckName = true;
        this.ischeckEmail = true;
        this.ischeckDate = true;
        this.ischeckDepartment = true;
        this.ischeckMoney = true;
        this.ischeckcode = false;

        return false;
      }
      let code2 = document.forms["myForm"]["code"].value;
      if (this.code.includes(code2)) {
        this.$notify({
          title: "Warning",
          message: "Mã nhân viên đã tồn tại!",
          type: "warning",
        });
        document.getElementById("code").focus();
        this.ischeckName = true;
        this.ischeckEmail = true;
        this.ischeckDate = true;
        this.ischeckDepartment = true;
        this.ischeckMoney = true;
        this.ischeckcode = false;
        return false;
      }
      let name = document.forms["myForm"]["name"].value;
      if (name == "") {
        this.$notify({
          title: "Warning",
          message: "Họ tên nhân viên không được để trống!",
          type: "warning",
        });
        document.getElementById("name").focus();

        this.ischeckcode = true;
        this.ischeckEmail = true;
        this.ischeckDate = true;
        this.ischeckDepartment = true;
        this.ischeckMoney = true;
        this.ischeckName = false;

        return false;
      }
      if (!isNaN(name)) {
        this.$notify({
          title: "Warning",
          message: "Họ tên nhân viên không được để số!",
          type: "warning",
        });
        document.getElementById("name").focus();
        this.ischeckEmail = true;
        this.ischeckDate = true;
        this.ischeckDepartment = true;
        this.ischeckMoney = true;
        this.ischeckName = false;
        return false;
      }

      var re =
        /^[\sa-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s\\W|_]+$/;
      if (!re.test(document.forms["myForm"]["name"].value)) {
        this.$notify({
          title: "Warning",
          message: "Tên không được có số và ký tự đặc biệt!",
          type: "warning",
        });
        document.getElementById("name").focus();
        this.ischeckcode = true;
        this.ischeckEmail = true;
        this.ischeckDate = true;
        this.ischeckDepartment = true;
        this.ischeckMoney = true;
        this.ischeckName = false;
        return false;
      }
      let email = document.forms["myForm"]["email"].value;
      if (email == "") {
        this.$notify({
          title: "Warning",
          message: "Email không được để trống!",
          type: "warning",
        });
        document.getElementById("email").focus();
        this.ischeckcode = true;
        this.ischeckName = true;
        this.ischeckDate = true;
        this.ischeckDepartment = true;
        this.ischeckMoney = true;
        this.ischeckEmail = false;

        return false;
      }

      if (this.email.includes(email)) {
        this.$notify({
          title: "Warning",
          message: "Email này đã tồn tại!",
          type: "warning",
        });
        document.getElementById("email").focus();
        this.ischeckcode = true;
        this.ischeckName = true;
        this.ischeckDate = true;
        this.ischeckDepartment = true;
        this.ischeckMoney = true;
        this.ischeckEmail = false;

        return false;
      }
      let date = document.forms["myForm"]["date"].value;
      if (date == "") {
        this.$notify({
          title: "Warning",
          message: "Ngày sinh không được để trống!",
          type: "warning",
        });
        document.getElementById("date").focus();
        this.ischeckcode = true;
        this.ischeckName = true;
        this.ischeckDepartment = true;
        this.ischeckMoney = true;
        this.ischeckEmail = true;
        this.ischeckDate = false;

        return false;
      }
      // let welfareMoney = document.forms["myForm"]["welfareMoney"].value;
      // if (welfareMoney == "") {
      //   this.$notify({
      //     title: "Warning",
      //     message: "Số tiền hỗ trợ không được để trống!",
      //     type: "warning",
      //     size: 100,
      //   });
      //   document.getElementById("welfareMoney").focus();
      //   this.ischeckcode = true;
      //   this.ischeckName = true;
      //   this.ischeckDepartment = true;
      //   this.ischeckMoney = true;
      //   this.ischeckEmail = true;
      //   this.ischeckDate = false;

      //   return false;
      // }
      // if (isNaN(moneyStaff)) {
      //   this.$notify({
      //     title: "Warning",
      //     message: "Số tiền hỗ trợ phải là số!",
      //     type: "warning",
      //     size: 100,
      //   });
      //   document.getElementById("welfareMoney").focus();
      //   this.ischeckcode = true;
      //   this.ischeckName = true;
      //   this.ischeckDepartment = true;
      //   this.ischeckEmail = true;
      //   this.ischeckDate = true;
      //   this.ischeckMoney = false;

      //   return false;
      // }
      // if (moneyStaff < 0) {
      //   this.$notify({
      //     title: "Warning",
      //     message: "Số tiền hỗ trợ phải là số dương!",
      //     type: "warning",
      //     size: 100,
      //   });
      //   document.getElementById("welfareMoney").focus();
      //   this.ischeckcode = true;
      //   this.ischeckName = true;
      //   this.ischeckDepartment = true;
      //   this.ischeckEmail = true;
      //   this.ischeckDate = true;
      //   this.ischeckMoney = false;

      //   return false;
      // }
      let department = document.forms["myForm"]["department"].value;
      if (department == "") {
        this.$notify({
          title: "Warning",
          message: "phòng ban không được để trống!",
          type: "warning",
          size: 100,
        });
        document.getElementById("department").focus();
        this.ischeckcode = true;
        this.ischeckName = true;
        this.ischeckEmail = true;
        this.ischeckDate = true;
        this.ischeckMoney = true;
        this.ischeckDepartment = false;
        return false;
      } else {
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
            this.ischeckcode = true;
            this.ischeckName = true;
            this.ischeckEmail = true;
            this.ischeckDate = true;
            this.ischeckDepartment = true;
            this.ischeckMoney = true;
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
      let moneyStaff = this.staffUpdate.welfareMoney.replace(/,/g, "");

      let x = document.forms["form-update"]["code"].value;
      if (x == "") {
        this.$notify({
          title: "Warning",
          message: "Mã nhân viên không được để trống!",
          type: "warning",
        });
        document.getElementById("code").focus();
        return false;
      }
      let code2 = document.forms["form-update"]["code"].value;
      if (this.code2.includes(code2)) {
        this.$notify({
          title: "Warning",
          message: "Mã nhân viên đã tồn tại!",
          type: "warning",
        });
        document.getElementById("code").focus();
        return false;
      }
      let name = document.forms["form-update"]["name"].value;
      if (name == "") {
        this.$notify({
          title: "Warning",
          message: "Họ tên nhân viên không được để trống!",
          type: "warning",
        });
        document.getElementById("name").focus();
        return false;
      }
      let email = document.forms["form-update"]["email"].value;
      if (email == "") {
        this.$notify({
          title: "Warning",
          message: "Email không được để trống!",
          type: "warning",
        });
        document.getElementById("email").focus();
        return false;
      }
      let email2 = document.forms["form-update"]["email"].value;
      if (this.email2.includes(email2)) {
        this.$notify({
          title: "Warning",
          message: "Email nhân viên này đã tồn tại!",
          type: "warning",
        });
        document.getElementById("email").focus();
        return false;
      }
      let date = document.forms["form-update"]["date"].value;
      if (date == "") {
        this.$notify({
          title: "Warning",
          message: "Ngày sinh không được để trống!",
          type: "warning",
        });
        document.getElementById("date").focus();
        return false;
      }
      let welfareMoney = document.forms["form-update"]["welfareMoney"].value;
      if (welfareMoney == "") {
        this.$notify({
          title: "Warning",
          message: "Số tiền hỗ trợ không được để trống!",
          type: "warning",
          size: 100,
        });
        document.getElementById("welfareMoney").focus();
        return false;
      }
      if (moneyStaff < 0) {
        this.$notify({
          title: "Warning",
          message: "Số tiền hỗ trợ phải là số dương!",
          type: "warning",
          size: 100,
        });
        document.getElementById("welfareMoney").focus();
        return false;
      }
      if (isNaN(moneyStaff)) {
        this.$notify({
          title: "Warning",
          message: "Số tiền hỗ trợ phải là số!",
          type: "warning",
          size: 100,
        });
        document.getElementById("welfareMoney").focus();
        return false;
      }
      let department = document.forms["form-update"]["department"].value;
      if (department == "") {
        this.$notify({
          title: "Warning",
          message: "phòng ban không được để trống!",
          type: "warning",
          size: 100,
        });
        document.getElementById("department").focus();
        return false;
      } else {
        this.staffUpdate.welfareMoney = moneyStaff;
        StaffService.updateStaff(id, this.staffUpdate)
          .then((response) => {
            console.log(response.data);
            this.retrieveStaff();
            this.$notify({
              title: "Success",
              message: "Cập nhật thông tin nhân viên thành công",
              type: "success",
            });
            this.reset();
            this.form = this.staffUpdate;
            this.form.welfareMoney = this.formatCurrency(
              this.form.welfareMoney
            );
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
    },
    handleFilesUpload(object) {
      if (
        object.target.files[0].type != "application/vnd.ms-excel" &&
        object.target.files[0].type !=
          "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
      ) {
        this.$notify({
          title: "Warning",
          message: "File không đúng định dạng!",
          type: "warning",
        });
        this.isShowSubmitFiles = false;
        return false;
      }
      this.isShowSubmitFiles = true;
    },
    submitFiles() {
      const file = this.$refs.file.files[0];
      var formData = new FormData();
      formData.append("file", file);
      StaffService.ImportExcel(formData)
        .then((response) => {
          console.log(response.data);
          this.$notify({
            title: "Success",
            message: "Import thành công",
            type: "success",
          });
          this.isShowImport = false;
          this.$refs.file.value = "";
          this.retrieveStaff();
        })
        .catch((e) => {
          console.log(e);
          this.$notify({
            title: "Warning",
            message: "Import thất bại",
            type: "warning",
          });
        });
    },
  },
  mounted() {
    // this.staff.date = this.formatDate(this.staff.date)
    this.retrieveStaff();
    this.getDepartments();
    this.listCode();
    this.listEmail();
    this.formatCurrency();

    StaffService.getUser().then((response) => {
      this.user = response.data;
    });
  },
};
</script>
  <style scoped>
@import "@/assets/css/hr/list.css";

.upload-container {
  background-color: rgb(239, 239, 239);
  border-radius: 6px;
  padding: 10px;
}

.border-container {
  border: 5px dashed rgba(198, 198, 198, 0.65);
  padding: 0 10px;
}

.border-container p {
  color: rgba(19, 15, 64, 0.85);
  font-weight: 600;
  font-size: 1.05em;
  letter-spacing: -1px;
  margin-top: 30px;
  margin-bottom: 0;
  opacity: 0.65;
}

.btnUpload {
  width: auto;
  height: 50px;
  background-color: #92c3f9;
  border: none;
  outline: none;
  color: black;
  font-weight: 600;
  cursor: pointer;
  text-align: center;
  margin-top: 40px;
}

.btnUpload:hover {
  border: none;
  outline: none;
  background-color: #0088ff;
  color: #fff;
}
</style>