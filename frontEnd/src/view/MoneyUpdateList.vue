<template>
  <div>
    <tr>
      <th>
        <div>
          <el-form :inline="true" class="input">
            <el-form-item>
              <el-input
                v-model="keyWord"
                placeholder="Nhập mã nhân viên"
                style="width: 350px"
              >
                <i slot="prefix" class="el-input__icon el-icon-search"></i
              ></el-input>
            </el-form-item>

            <el-form-item>
              <el-button type="warning" @click="onFind()">Tìm kiếm</el-button>
            </el-form-item>

            <el-form-item v-if="selected.length !== 0">
              <el-button round @click="handleSuccessAll()" class="acceptMoney"
                ><strong>Chấp thuận</strong>
              </el-button>
            </el-form-item>

            <el-form-item v-if="selected.length !== 0">
              <el-button round @click="handleCancelAll()" class="cancelMoney"
                ><strong>Hủy bỏ</strong>
              </el-button>
            </el-form-item>

            <el-form-item v-if="selected.length !== 0">
              <el-button round @click="handleReturnAll()" class="returnMoney"
                ><strong>Hoàn tác</strong>
              </el-button>
            </el-form-item>
          </el-form>
        </div>
      </th>
    </tr>

    <div class="money-table">
      <div class="money-table_content">
        <table>
          <thead>
            <tr class="money-table_row">
              <th width="50px">
                <input type="checkbox" v-model="selectAll" />
              </th>

              <th width="50px">STT</th>
              <th width="300px">Họ và tên</th>
              <th width="150px">Mã nhân viên</th>
              <th width="250px">Phòng ban</th>
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
                      <el-dropdown-item command="accept" :value="1"
                        ><strong>Chấp Thuận</strong></el-dropdown-item
                      >
                      <el-dropdown-item command="cancel" :value="2"
                        ><strong>Hủy Bỏ</strong></el-dropdown-item
                      >
                      <el-dropdown-item command="waiting" :value="3"
                        ><strong>Chờ Duyệt</strong></el-dropdown-item
                      >
                      <el-dropdown-item command="all" :value="0"
                        ><strong>Tất cả</strong></el-dropdown-item
                      >
                    </el-dropdown-menu>
                  </template>
                </el-dropdown>
              </th>

              <th width="200px">Thao tác</th>
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
                <span v-if="item.status === 1 || item.status === 2">
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
    </div>
  </div>
</template>

<script>
import AcceptMoneyService from "@/service/acceptMoney";
export default {
  name: "MoneyupdateList",
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
    checkSelected: {
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
      listMoneyAccpet: [],
      listMoneyCancel: [],
      listMoneyWaiting: [],
      listNewStaffs: [],
      keyWord: null,
      pageSize: 9,
      count: 0,
      page: 0,
      selected: [],
      pageSizes: [2, 4, 6],
      isIndeterminate: true,
      content: "",
      keyComponent: 0,
      checkpage: false,
      checkpage2: false,
      checkpage3: false,
      status: null,
    };
  },
  methods: {
    handleSort() {
      this.listUpdateMoneys.content.sort((a, b) =>
        a.nameAdmin.localeCompare(b.nameAdmin)
      );
    },
    handleCommand(command) {
      this.status = command;
      if (command === "accept") {
        this.handlelistAccept();
        this.checkpage = true;
        this.checkpage2 = false;
        this.checkpage3 = false;
      }
      if (command === "cancel") { 
        this.handlelistCancel();
        this.checkpage = false
        this.checkpage2 = true
        this.checkpage3 = false
      }
      if (command === "waiting") {
        this.handlelistWaiting();
        this.checkpage = false
        this.checkpage2 = false
        this.checkpage3 = true
      }
      if (command === "all") {
        this.handlelistAll();
        this.checkpage = false
      }
    },
    handlelistAccept() {
      new AcceptMoneyService().getmoneyAccept().then((response) => {
        this.listUpdateMoneys  = response.data;
        this.hasRole = true;
        this.count = response.data.totalPages;
        this.itemCount = response.data.totalElements;
      });
    },
    handlelistCancel() {
      new AcceptMoneyService().getmoneyCancel().then((response) => {
        this.listUpdateMoneys = response.data;
        this.hasRole = true;
        this.count = response.data.totalPages;
        this.itemCount = response.data.totalElements;
      });
    },

    handlelistWaiting() {
      new AcceptMoneyService().getmoneyWaiting().then((response) => {
        this.listUpdateMoneys = response.data;
        this.hasRole = true;
        this.count = response.data.totalPages;
        this.itemCount = response.data.totalElements;
      });
    },
    handlelistAll() {
      new AcceptMoneyService().getAllMoney().then((response) => {
        this.listUpdateMoneys = response.data;
        this.hasRole = true;
        this.count = response.data.totalPages;
        this.itemCount = response.data.totalElements;
      });
    },
    getmoneyAccept() {
      const params = this.getRequestParams(
        this.page,
        this.pageSize,
        this.keyWord
      );
      // console.log(params);
      new AcceptMoneyService()
        .getmoneyAccept(params)
        .then((response) => {
          this.listUpdateMoneys = response.data;
          this.hasRole = true;
          this.count = response.data.totalPages;
          this.itemCount = response.data.totalElements;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getmoneyCancel() {
      const params = this.getRequestParams(
        this.page,
        this.pageSize,
        this.keyWord
      );
      // console.log(params);
      new AcceptMoneyService()
        .getmoneyCancel(params)
        .then((response) => {
          this.listUpdateMoneys = response.data;
          this.hasRole = true;
          this.count = response.data.totalPages;
          this.itemCount = response.data.totalElements;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getmoneyWaiting() {
      const params = this.getRequestParams(
        this.page,
        this.pageSize,
        this.keyWord
      );
      // console.log(params);
      new AcceptMoneyService()
        .getmoneyWaiting(params)
        .then((response) => {
          this.listUpdateMoneys = response.data;
          this.hasRole = true;
          this.count = response.data.totalPages;
          this.itemCount = response.data.totalElements;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getAllMoneyUp() {
      const params = this.getRequestParams(
        this.page,
        this.pageSize,
        this.keyWord
      );
      // console.log(params);
      new AcceptMoneyService()
        .getAllMoney(params)
        .then((response) => {
          this.listUpdateMoneys = response.data;
          this.hasRole = true;
          this.count = response.data.totalPages;
          this.itemCount = response.data.totalElements;
        })
        .catch((error) => {
          console.log(error);
        });
    },

    getRequestParams(page, pageSize = 9, keyWord) {
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
      if (this.checkpage === true) {
        this.getmoneyAccept();
      }else if(this.checkpage2 === true){
        this.getmoneyCancel();
      }else if(this.checkpage3 === true){
        this.getmoneyWaiting();
      }else{
        this.getAllMoneyUp()
      }
      
      
    },

    handleSuccess(id) {
      this.$confirm("Bạn muốn chấp nhận cho nhân viên này?", "Thông báo", {
        confirmButtonText: "OK",
        cancelButtonText: "Cancel",
        type: "success",
      }).then(() => {
        new AcceptMoneyService()
          .Acceptmoney(id)
          .then(() => {
            this.getAllMoneyUp();
            this.$notify({
              type: "success",
              message: "Đã chấp nhận",
              title: "Thông báo",
            });
          })
          .catch((error) => {
            console.log(error);
          });
      });
    },

    handleDelete(id) {
      this.$confirm("Bạn không chấp thuận cho nhân viên này?", "Thông báo", {
        confirmButtonText: "OK",
        cancelButtonText: "Cancel",
        type: "error",
      }).then(() => {
        new AcceptMoneyService()
          .Cancelmoney(id)
          .then(() => {
            this.getAllMoneyUp();
            this.$notify({
              type: "warning",
              message: "Đã Hủy Bỏ",
              title: "Thông báo",
            });
          })
          .catch((error) => {
            console.log(error);
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
      ).then(() => {
        new AcceptMoneyService()
          .Returnmoney(id)
          .then(() => {
            this.getAllMoneyUp();
            this.$notify({
              type: "warning",
              message: "Đã hoàn tác",
              title: "Thông báo",
            });
          })
          .catch((error) => {
            console.log(error);
          });
      });
    },
    handleSuccessAll() {
      this.$confirm("Bạn muốn chấp nhận những nhân viên này?", "Thông báo", {
        confirmButtonText: "OK",
        cancelButtonText: "Cancel",
        type: "success",
      }).then(() => {
        new AcceptMoneyService()
          .AcceptAll(this.selected)
          .then(() => {
            this.getAllMoneyUp();
            this.$notify({
              type: "success",
              message: "Đã chấp thuận",
              title: "Thông báo",
            });
          })
          .catch((error) => {
            console.log(error);
          });
      });
    },

    handleReturnAll() {
      this.$confirm("Hoàn tác xét duyệt những nhân viên này?", "Thông báo", {
        confirmButtonText: "OK",
        cancelButtonText: "Cancel",
        type: "success",
      }).then(() => {
        new AcceptMoneyService()
          .ReturnAll(this.selected)
          .then(() => {
            this.getAllMoneyUp();
            this.$notify({
              type: "warning",
              message: "Đã hoàn tác",
              title: "Thông báo",
            });
          })
          .catch((error) => {
            console.log(error);
          });
      });
    },

    handleCancelAll() {
      this.$confirm("Hủy xét duyệt những nhân viên này?", "Thông báo", {
        confirmButtonText: "OK",
        cancelButtonText: "Cancel",
        type: "success",
      }).then(() => {
        new AcceptMoneyService()
          .CancelAll(this.selected)
          .then(() => {
            this.getAllMoneyUp();
            this.$notify({
              type: "warning",
              message: "Đã Hủy Bỏ",
              title: "success",
            });
          })
          .catch((error) => {
            console.log(error);
          });
      });
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
/* .money-table_content {
  height: 70vh;
} */
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
.acceptmoney-pagination {
  padding: 15px 100px;
}
.money-table td {
  border: 1px solid #e4c9ac;
  padding: 10px;
}
.money-table {
  text-align: center;
  padding: 0 10px 50px 0px;
  overflow: hidden;
  height: 100%;
}
.money-table_row {
  height: 50px;
}
.fa {
  size: small;
}
</style>