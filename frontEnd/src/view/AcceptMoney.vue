<template>
  <div class="accept-money">
    <div class="money-title">
      <span><strong>DANH SÁCH XÉT DUYỆT TIỀN PHÚC LỢI</strong></span>
    </div>
    <div class="money-table">
      <tr width="100%">
        <th>
          <div>
            <el-form :inline="true" class="demo-form-inline">
              <el-form-item>
                <el-input
                  v-model="keyWord"
                  placeholder="Nhập mã nhân viên"
                  style="width: 500px"
                >
                  <i slot="prefix" class="el-input__icon el-icon-search"></i
                ></el-input>
              </el-form-item>

              <el-form-item>
                <el-button type="warning" @click="onFind()">Tìm kiếm</el-button>
              </el-form-item>

              <el-form-item>
                <el-button round @click="handleReturnAll()" class="returnMoney"
                  ><strong>Hoàn tác</strong>
                </el-button>
              </el-form-item>

              <el-form-item>
                <el-button round @click="handleSuccessAll()" class="acceptMoney"
                  ><strong>Chấp thuận</strong>
                </el-button>
              </el-form-item>

              <el-form-item>
                <el-button round @click="handleCancelAll()" class="cancelMoney"
                  ><strong>Hủy bỏ</strong>
                </el-button>
              </el-form-item>
            </el-form>
          </div>
        </th>
      </tr>
      <div class="money-table_content">
        <table :key="keyComponent">
          <thead>
            <tr class="money-table_row">
              <th width="50px">
                <input type="checkbox" v-model="selectAll" />
              </th>

              <th width="50px">STT</th>
              <th width="300px">Họ và tên</th>
              <th width="150px">Mã nhân viên</th>
              <th width="200px">Phòng ban</th>
              <th width="300px">
                Người khởi tạo
                <el-button @click="handleSort()">
                  <i class="fa fa-caret-down" aria-hidden="true"></i>
                </el-button>
              </th>
              <th width="150px">Số tiền ban đầu</th>
              <th width="150px">Số tiền thay đổi</th>
              <th width="150px">Tiền chênh lệch</th>

              <th width="150px">
                <el-dropdown @command="handleCommand">
                  <span class="el-dropdown-link" style="color: black">
                    Trạng thái
                    <i class="fa fa-caret-down" aria-hidden="true"></i>
                  </span>
                  <template #dropdown>
                    <el-dropdown-menu>
                      <el-dropdown-item command="accept"
                        ><strong>Chấp Thuận</strong></el-dropdown-item
                      >
                      <el-dropdown-item command="cancel"
                        ><strong>Hủy Bỏ</strong></el-dropdown-item
                      >
                      <el-dropdown-item command="waiting"
                        ><strong>Chờ Duyệt</strong></el-dropdown-item
                      >
                    </el-dropdown-menu>
                  </template>
                </el-dropdown>
              </th>

              <th width="250px">Thao tác</th>
            </tr>
          </thead>

          <tbody class="body">
            <tr v-for="(item, index) in listUpdateMoneys.content" :key="index">
              <td>
                <input
                  type="checkbox"
                  :value="item.id"
                  :ref="item.id"
                  v-model="selected"
                />
              </td>
              <td>{{ index + 1 }}</td>
              <td style="text-align: left">{{ item.name }}</td>
              <td style="text-align: left">{{ item.code }}</td>
              <td style="text-align: left">{{ item.department }}</td>
              <td style="text-align: left">{{ item.nameAdmin }}</td>
              <td style="text-align: right">
                {{ formatCurrency(item.moneyOld) }}
              </td>
              <td style="text-align: right">
                {{ formatCurrency(item.moneyNew) }}
              </td>
              <td style="text-align: right">
                {{ formatCurrency(item.moneyNew - item.moneyOld) }}
              </td>
              <td style="text-align: center">
                <span v-show="item.status == 0">
                  <Strong style="color: orange">Chờ duyệt</Strong>
                </span>
                <span v-show="item.status == 2">
                  <Strong style="color: red">Hủy bỏ</Strong>
                </span>
                <span v-show="item.status == 1">
                  <Strong style="color: #5db830">Chấp thuận</Strong>
                </span>
              </td>

              <td style="text-align: center">
                <span v-if="item.status === 2 || item.status === 1">
                  <el-button
                    @click="handleReturn(item.id, index)"
                    type="warning"
                    icon="el-icon-refresh-left"
                    circle
                  ></el-button>
                </span>
                
                <span class="icon-submit" v-if="item.status === 0">
                  <el-button
                    @click="handleSuccess(item.id, index)"
                    type="success"
                    icon="el-icon-check"
                    circle
                  >
                  </el-button>
                  &ensp;
                  <el-button
                    @click="handleDelete(item.id, index)"
                    type="danger"
                    icon="el-icon-close"
                    circle
                  ></el-button>
                </span>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <!-- phân trang -->
    <el-pagination
      style="text-align: right"
      background
      layout="prev, pager, next"
      :page-count="count"
      :page-size="pageSize"
      :page-sizes="pageSizes"
      @current-change="handlePageChange"
      class="acceptmoney-pagination"
    >
    </el-pagination>
    
  </div>
</template>
  
  <script>
import AcceptMoneyService from "@/service/acceptMoney";

// import Paginate from "vuejs-paginate";
// Vue.component("paginate", Paginate);
export default {
  name: "AcceptMoney",
  computed: {
    selectAll: {
      get: function () {
        return this.listUpdateMoneys.content
          ? this.selected.length == this.listUpdateMoneys.content.length
          : false;
      },
      set: function (value) {
        var selected = [];
        if (value) {
          this.listUpdateMoneys.content.forEach(function (listUpdateMoney) {
            selected.push(listUpdateMoney.id);
          });
        }
        this.selected = selected;
      },
    },
  },
  mounted() {},
  data() {
    return {
      checkAll: false,
      listUpdateMoneys: [],
      keyWord: null,
      pageSize: 10,
      count: 0,
      selected: [],
      pageSizes: [2, 4, 6],
      isIndeterminate: true,
      content: "",
      keyComponent: 0
    };
  },
  methods: {

    handleSort() {
      this.listUpdateMoneys.content.sort((a, b) =>
        a.nameAdmin.localeCompare(b.nameAdmin)
      );
     
    },


    handleCommand(command) {
      if (command === "accept") {
        this.handlelistAccept();
      }

      if (command === "cancel") {
        this.handlelistCancel();
      }
      if (command === "waiting") {
        this.handlelistWaiting();
      }
    },

    handlelistAccept() {
      new AcceptMoneyService().getmoneyAccept().then((response) => {
        this.listUpdateMoneys.content= response.data;
      });
    },
    handlelistCancel() {
      new AcceptMoneyService().getmoneyCancel().then((response) => {
        this.listUpdateMoneys.content = response.data;
      });
    },

    handlelistWaiting() {
      new AcceptMoneyService().getmoneyWaiting().then((response) => {
        this.listUpdateMoneys.content = response.data;
      });
    },

    getAllMoneyUp() {
      const params = this.getRequestParams(
        this.page,
        this.pageSize,
        this.keyWord
      );
      // console.log(params);
      new AcceptMoneyService().getAllMoney(params).then((response) => {
        this.listUpdateMoneys = response.data;
        this.hasRole = true;
        this.count = response.data.totalPages;
        this.itemCount = response.data.totalElements;
      });
    },

    getRequestParams(page, pageSize, keyWord) {
      let params = {};
      if (page) {
        params["page"] = page - 1;
      }
      if (pageSize) {
        params["size"] = pageSize;
      }
      if (keyWord) {
        params["keyWord"] = keyWord;
      }
      return params;
    },

    handlePageChange(value) {
      this.page = value;
      this.getAllMoneyUp();
    },

    handleSuccess(id) {
      this.$confirm("Bạn muốn chấp nhận cho nhân viên này?", "Thông báo", {
        confirmButtonText: "OK",
        cancelButtonText: "Cancel",
        type: "success",
      })
        .then(() => {
          new AcceptMoneyService().Acceptmoney(id);
          
          // this.$router.go();
        })
        .catch(() => {
          this.$message({
            type: "success",
            message: "Đã chấp nhận",
            title: "success",
          });
          
        });
    },

    handleDelete(id) {
      this.$confirm("Bạn không chấp thuận cho nhân viên này?", "Thông báo", {
        confirmButtonText: "OK",
        cancelButtonText: "Cancel",
        type: "error",
      })
        .then(() => {
          new AcceptMoneyService().Cancelmoney(id);
          // this.loading();
          this.getAllMoneyUp();
        })
        .catch(() => {
          this.$message({
            type: "warning",
            message: "Đã hủy bỏ",
          });
        });
    },

    handleReturn(id) {
      this.$confirm(
        "Bạn muốn hoàn tác đánh giá cho nhân viên này?",
        "Thông báo",
        {
          confirmButtonText: "OK",
          cancelButtonText: "Cancel",
          type: "",
        }
      )
        .then(() => {
          new AcceptMoneyService().Returnmoney(id);
          // this.loading();
          this.getAllMoneyUp();
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "Đã hoàn tác",
          });
        });
    },

    handleSuccessAll() {
      this.$confirm("Bạn muốn chấp nhận những nhân viên này?", "Thông báo", {
        confirmButtonText: "OK",
        cancelButtonText: "Cancel",
        type: "success",
      })
        .then(() => {
          new AcceptMoneyService().AcceptAll(this.selected);
          this.getAllMoneyUp();
          this.$message({
            type: "success",
            message: "Đã chấp thuận",
          });
        })
        .catch(() => {
          this.$message({
            type: "warning",
            message: "Đã hủy bỏ",
          });
        });
    },

    handleReturnAll() {
      this.$confirm("Hoàn tác xét duyệt những nhân viên này?", "Thông báo", {
        confirmButtonText: "OK",
        cancelButtonText: "Cancel",
        type: "success",
      })
        .then(() => {
          new AcceptMoneyService().ReturnAll(this.selected);
          this.loading();
          this.$message({
            type: "success",
            message: "Đã hoàn tác",
          });
          //this.$router.go();
        })
        .catch(() => {
          this.$message({
            type: "warning",
            message: "Đã hủy bỏ",
          });
        });
    },

    handleCancelAll() {
      this.$confirm("Hủy xét duyệt những nhân viên này?", "Thông báo", {
        confirmButtonText: "OK",
        cancelButtonText: "Cancel",
        type: "success",
      })
        .then(() => {
          new AcceptMoneyService().CancelAll(this.selected);
          this.loading();
          this.$message({
            type: "success",
            message: "Đã chấp thuận",
          });
          //this.$router.go();
        })
        .catch(() => {
          this.$message({
            type: "warning",
            message: "Đã hủy bỏ",
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
      }, 100);
    },

    formatCurrency(value) {
      return Intl.NumberFormat("vi-VN", {
        currency: "VND",
      }).format(value);
    },
    onFind() {
      this.getAllMoneyUp();
    },
  },
  created() {
    this.getAllMoneyUp();
  },
};
</script>
  
  <style>
.money-title {
  text-align: center;
  font-size: 34px;
  font-weight: 500;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen,
    Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
  color: rgb(0, 0, 0);
  padding: 6px 0px;
}
.money-table {
  width: fit-content;
  margin-left: 200px;
  margin-right: 35px;
  overflow: auto;
}
.money-table_content {
  height: 70vh;
}
.money-table {
  text-align: center;
  padding: 0 50px;
  overflow: auto;
  height: 100%;
  margin: 20px 0px;
  overflow: auto;
}
.money-table table tbody tr:hover {
  background-color: rgb(255, 255, 255);
}
.money-table table tr {
  background: #f2e7ddf8;
}
.money-table table tr th {
  border-right: 1px solid #e4c9ac;
  padding: 10px;
  background-color: #fdf9f8;
  height: 40px;
  font-weight: 650;
}
.money-table table tbody tr {
  border-bottom: 1px solid #e4c9ac;
}
.money-table table thead tr {
  border-bottom: 1px solid #e4c9ac;
}
.acceptMoney {
  margin-left: 10px;
  background-color: #5db830;
  color: white;
}
.cancelMoney {
  margin-left: 10px;
  background-color: #f74444;
  color: white;
}
.returnMoney {
  margin-left: 800px;
  color: #fff;
  background-color: #e6a23c;
  border-color: #e6a23c;
}
.cancelMoney:hover {
  color: white;
  background-color: #bb2d3b;
}
.acceptMoney:hover {
  color: white;
  background-color: #4f9b29;
}
.returnMoney:hover {
  color: white;
  background-color: #c98b2f;
}
.money-table td {
  border: 1px solid #e4c9ac;
  padding: 10px;
}
.money-table {
  text-align: center;
  padding: 0 50px;
  overflow: auto;
  height: 100%;
  margin: 0px 0px;
}
.accept-money {
  background: linear-gradient(90deg, #fad1a5 0%, rgba(255, 255, 255, 0) 100%),
    #f3cce1;
}
.acceptmoney-pagination {
  padding: 12px 100px;
}
</style>