<template>
  <div>
    <div class="accept-money" v-if="hasRole">
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
    <Error401 v-if="!hasRole"></Error401>
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
    
  },
  components: {
    MoneyUpdateList,
    NewStaffsList,
    WelfareUpdate,
    Error401,
  },
  mounted() {},
  data() {
    return {
      checkAll: false,
      listUpdateMoneys: [],
      listNewStaffs: [],
      hasRole: true,
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
  },
  created() {
    // this.getAllMoneyUp();
    this.value = this.options[0].value;
    this.selectApprove(this.value)
    console.log(11,this.value)
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
  height: 100vh;
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