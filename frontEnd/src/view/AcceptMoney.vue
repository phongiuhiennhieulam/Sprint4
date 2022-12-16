<template>
    <div class="accept-money" >
      <div class="money-title">
        <span><strong>DANH SÁCH XÉT DUYỆT TIỀN PHÚC LỢI</strong></span>
      </div>
      <el-select
        v-model="value"
        placeholder="Loại xét duyệt"
        class="moneyup-drop"
        @change="selectApprove($event)"
      >
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
          :disabled="item.disabled"
        />
      </el-select>
      <div class="money-table">
        <div class="accept-component">
          <MoneyUpdateList v-if="check === 1"></MoneyUpdateList>
          <NewStaffsList v-if="check === 2"></NewStaffsList>
          <WelfareUpdate v-if="check === 3"></WelfareUpdate>
        </div>
      </div>
    </div>
</template>
  
  <script>
  /* eslint-disable */
import AcceptMoneyService from "@/service/acceptMoney";
import MoneyUpdateList from "./MoneyUpdateList.vue";
import WelfareUpdate from "./WelfareUpdate.vue";
import NewStaffsList from "./NewStaffsList.vue";
import Error401 from "./401-error.vue";
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
  components: {
    MoneyUpdateList,
    NewStaffsList,
    WelfareUpdate,
  },
  mounted() {},
  data() {
    return {
      checkAll: false,
      listUpdateMoneys: [],
      listNewStaffs: [],
      keyWord: null,
      pageSize: 10,
      count: 0,
      selected: [],
      pageSizes: [2, 4, 6],
      isIndeterminate: true,
      content: "",
      keyComponent: 0,
      check: null,
      value: null,
      options: [
        {
          value: "MoneyUpdateList",
          label: "Xét duyệt tiền phúc lợi tháng sau",
        },
        {
          value: "NewStaffsList",
          label: "Xét duyệt cho nhân viên mới",
        },
        {
          value: "WelfareUpdate",
          label: "Xét duyệt phúc lợi",
        },
      ],
    };
  },
  methods: {
    selectApprove(event) {
      if (event === "MoneyUpdateList") {
        this.check = 1;
      }
      if (event === "NewStaffsList") {
        this.check = 2;
      }
      if (event === "WelfareUpdate") {
        this.check = 3;
      }
    },

    handlelistAccept() {
      new AcceptMoneyService().getmoneyAccept().then((response) => {
        this.listUpdateMoneys.content = response.data;
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
          this.loading();
          this.getAllMoneyUp();
        })
        .catch(() => {
          this.$notify({
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
          this.loading();
          this.getAllMoneyUp();
        })
        .catch(() => {
          this.$notify({
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
          this.loading();
          this.getAllMoneyUp();
        })
        .catch(() => {
          this.$notify({
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
          this.loading();
          this.getAllMoneyUp();
          this.$notify({
            type: "success",
            message: "Đã chấp thuận",
          });
        })
        .catch(() => {
          this.$notify({
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
          this.$notify({
            type: "success",
            message: "Đã hoàn tác",
          });
          //this.$router.go();
        })
        .catch(() => {
          this.$notify({
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
          this.$notify({
            type: "success",
            message: "Đã chấp thuận",
          });
          //this.$router.go();
        })
        .catch(() => {
          this.$notify({
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
  },
  created() {
    // this.getAllMoneyUp();
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
.acceptMoney {
  margin-left: 710px;
  background-color: #5db830;
  color: white;
}
.cancelMoney {
  margin-left: 10px;
  background-color: #f74444;
  color: white;
}
.returnMoney {
  margin-left: 10px;
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
.accept-money {
  background: linear-gradient(90deg, #fad1a5 0%, rgba(255, 255, 255, 0) 100%),
    #f3cce1;
  height: 100%;
}
.moneyup-drop {
  width: 260px;
  margin-left: 830px;
}
.accept-component {
  margin-left: 20px;
}
.input {
  margin-left: 160px;
}
</style>