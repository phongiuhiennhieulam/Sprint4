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

            <!-- <el-form-item>
              <el-button round @click="handleReturnAll()" class="returnMoney"
                ><strong>Hoàn tác</strong>
              </el-button>
            </el-form-item> -->
          </el-form>
        </div>
      </th>
    </tr>
    <div class="staff-table">
      <div class="staff-table_content">
        <table class="staffs-table">
          <thead>
            <tr class="money-table_row">
              <th width="50px">
                <input type="checkbox" v-model="selectAll" />
              </th>

              <th width="50px">STT</th>
              <th width="300px">Họ và tên</th>
              <th width="200px">Mã nhân viên</th>
              <th width="300px">Phòng ban</th>
              <th width="250px">Tiền Phúc Lợi</th>
              <th width="150px">Trạng thái</th>
              <th width="250px">Thao tác</th>
            </tr>
          </thead>

          <tbody class="body">
            <tr v-for="(item, index) in listNewStaffs.content" :key="index">
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
              <td style="text-align: left">{{ item.department.name }}</td>
              <td style="text-align: right">
                {{ formatCurrency(item.welfareMoney) }}
              </td>

              <td style="text-align: center">
                <span v-show="item.status == 2">
                  <Strong style="color: orange">Chờ duyệt</Strong>
                </span>
                <span v-show="item.status == 1">
                  <Strong style="color: red">Hủy bỏ</Strong>
                </span>
                <span v-show="item.status == 0">
                  <Strong style="color: #5db830">Chấp thuận</Strong>
                </span>
              </td>

              <td style="text-align: center">
                <span class="icon-submit" v-if="item.status === 2">
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
        <el-pagination
          style="text-align: right"
          background
          layout="prev, pager, next"
          :page-count="count"
          :page-size="pageSize"
          :page-sizes="pageSizes"
          @current-change="handlePageChange"
          class="newstaffs-pagination"
        >
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import AcceptMoneyService from "@/service/acceptMoney";
export default {
  name: "NewStaffsList",
  computed: {
    selectAll: {
      get: function () {
        return this.listNewStaffs.content
          ? this.selected.length == this.listNewStaffs.content.length
          : false;
      },
      set: function (value) {
        var selected = [];
        if (value) {
          this.listNewStaffs.content.forEach(function (listNewStaff) {
            selected.push(listNewStaff.id);
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
      listNewStaffs: [],
      keyWord: null,
      pageSize: 9,
      count: 0,
      selected: [],
      pageSizes: [2, 4, 6],
      isIndeterminate: true,
      content: "",
      keyComponent: 0,
    };
  },
  methods: {
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
    getNewStaff() {
      const params = this.getRequestParams(
        this.page,
        this.pageSize,
        this.keyWord
      );
      new AcceptMoneyService().getNewStaffs(params).then((response) => {
        this.listNewStaffs = response.data;
        console.log(response);
        this.hasRole = true;
        this.count = response.data.totalPages;
        this.itemCount = response.data.totalElements;
      });
    },
    handlePageChange(value) {
      this.page = value;
      this.getNewStaff();
    },
    handleSuccess(id) {
      this.$confirm("Bạn muốn chấp nhận cho nhân viên này?", "Thông báo", {
        confirmButtonText: "OK",
        cancelButtonText: "Cancel",
        type: "success",
      }).then(() => {
        new AcceptMoneyService()
          .AcceptStaff(id)
          .then(() => {
            this.getNewStaff();
            this.$message({
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
    handleDelete(id) {
      this.$confirm("Bạn muốn chấp nhận cho nhân viên này?", "Thông báo", {
        confirmButtonText: "OK",
        cancelButtonText: "Cancel",
        type: "success",
      }).then(() => {
        new AcceptMoneyService()
          .CancelStaff(id)
          .then(() => {
            this.getNewStaff();
            this.$message({
              type: "success",
              message: "Đã hủy bỏ",
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
          .AcceptAllStaff(this.selected)
          .then(() => {
            this.getNewStaff();
            this.$message({
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

    handleCancelAll() {
      this.$confirm("Hủy xét duyệt những nhân viên này?", "Thông báo", {
        confirmButtonText: "OK",
        cancelButtonText: "Cancel",
        type: "success",
      }).then(() => {
        new AcceptMoneyService()
          .CancelAllStaff(this.selected)
          .then(() => {
            this.getNewStaff();
            this.$message({
              type: "success",
              message: "Đã Hủy Bỏ",
              title: "Thông báo",
            });
          })
          .catch((error) => {
            console.log(error);
          });
      });
    },
    onFind() {
      this.getNewStaff();
    },
    formatCurrency(value) {
      return Intl.NumberFormat("vi-VN", {
        currency: "VND",
      }).format(value);
    },
  },
  created() {
    this.getNewStaff();
  },
};
</script>

<style>
.staff-table_content {
  margin-left: 60px;
}

.staff-table {
  width: fit-content;
  padding: 0 95px 100px 100px;
  overflow: auto;
  height: 100%;
  margin: 0px 0px;
}
.staff-table table tbody tr:hover {
  background-color: rgb(255, 255, 255);
}
.staff-table table tr {
  background: #f2e7ddf8;
}
.staff-table table tr th {
  border-right: 1px solid #e4c9ac;
  padding: 10px;
  background-color: #fdf9f8;
  height: 40px;
  font-weight: 650;
}
.staff-table table tbody tr {
  border-bottom: 1px solid #e4c9ac;
}
.staff-table table thead tr {
  border-bottom: 1px solid #e4c9ac;
}
.newstaffs-pagination {
  padding: 15px 100px;
}
.staff-table td {
  border: 1px solid #e4c9ac;
  padding: 10px;
}
</style>