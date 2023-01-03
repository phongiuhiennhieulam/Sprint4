<template>
  <div class="pl-body">
    <div class="pl-content">
      <div class="hr-title"><strong>DANH SÁCH XÉT DUYỆT</strong></div>
      <br>
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
            <!-- <b-form-file @change="readFiles($event)"
                   v-model="file" class="mb-2"></b-form-file> -->
            <input type="file" @change="readFiles($event)">
            <button @click="showfile()">show </button>
          </div>
        </div>
      </div>
      <tr v-for="x in items" :key="x.ok">
        {{ x.ok }}
      </tr>
    </div>
    <!-- dialog lịch sử xet duyet -->

    <el-dialog title="temp" :visible.sync="isHistory" width="59%" :before-close="handleClose">
      <span slot="title" class="title-dialog"><strong>Lịch sử xét duyệt</strong> </span>
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
              <th width="15%">Trạng thái</th>
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
              <span style="color: seagreen;" v-if="item.status==0">
                <Strong>Chấp nhận </Strong>
              </span>
              <span style="color: red;" v-if="item.status==1">
                <Strong>Từ chối </Strong>
              </span>
              <span style="color: goldenrod;" v-if="item.status==2">
                <Strong>Đang chờ </Strong>
              </span>
            </td>
            <td>
              <div class="d-flex" v-if="item.status==2">
                <el-button @click="handleSuccess(item.id, staff.id)" type="success" icon="el-icon-check" circle>
                </el-button>
           
                  <el-button @click="handleDelete(item.id, staff.id)" type="danger" icon="el-icon-close" circle></el-button>
              </div>
              <div class="d-flex" v-if="item.status==0||item.status==1">
                  <el-button @click="handleReturn(item.id, staff.id)" type="warning" icon="el-icon-refresh-left" circle> </el-button>
              </div>
            </td>
          </tr>
        </table>
        <div style="text-align: center; margin-top: 5px;">
          <el-button @click="ok()" type="danger" circle>
          <i class="
                    el-icon-folder-checked"></i> <Strong> OK </Strong>       
        </el-button>
        </div>


      
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
const ExcelJS = require('exceljs')

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
      isHistory: false,
      items: [],
      file: null,
      data: [
        {
          ok: '1',
          name: 2
        },
        {
          ok: '1',
          name: 2
        }
      ]

    };
  },
  methods: {
    readFiles(event) {
      const reader = new FileReader()
      reader.onloadend = async() => {
        const workbook = new ExcelJS.Workbook()
        await workbook.xlsx.load(reader.result)
        const worksheet = workbook.getWorksheet('SheetJS')
        try {
          worksheet.eachRow((row, index) => {
            if (index > 2){
              const mark = {
              stt: row.getCell('A').value,
              ok: row.getCell('B').value
            }
            this.items.push(mark)
            }
          })
        } catch (error) {
          console.log(error)
        }
      }
      reader.readAsArrayBuffer(event.target.files[0])
    },
    showfile(){
      console.log(this.items)
      console.log(this.data)

    },
    showAddForm() {
      this.edit = {};
      this.isShowAdd = true;
    },
    handleDelete(id, idStaff) {
      try {
        StaffService.DeleteRegisterWelfare(id)
        .then((response)=>{
              console.log(response.data);
              this.handShow(idStaff);
            })   
        this.$notify.info({
          title: 'notification',
          message: 'Từ chối thành công'
        });
      } catch (error) {
        this.errorMessage = error
      }
    },

      ok(){
        this.$confirm(
          "Bạn có ok? danh sách này?",
          "Warning",
          {
            confirmButtonText: "Có",
            cancelButtonText: "Hủy",
            type: "warning",
          }
        )
          .then(() => {
            this.isWelfare = false    
            this.getAll()  
            this.$message({
              type: "success",
              message: "ok!",
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "!",
            });
          });
      },
    handleSuccess(id,idStaff) {
      try {
        StaffService.SuccessRegisterWelfare(id)
        .then((response)=>{
              console.log(response.data);
              this.handShow(idStaff);
            })       
        this.$notify({
          title: 'Success',
          message: 'Xét duyệt',
          type: 'success'
        });
      } catch (error) {
        this.errorMessage = error
      }

    },
    handleReturn(id,idStaff) {
      try {
        StaffService.ReturnRegisterWelfare(id)
        .then((response)=>{
              console.log(response.data);
              this.handShow(idStaff);
            })   
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