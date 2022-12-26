<template>
  <div>
    <div class="row ">
      <div class="st-title"><span class="ml-2"><Strong><i class="el-icon-money"></i> Chi phí</Strong></span></div>
      <hr>
      <div class="col-12 cost">
        <h5><Strong>Tổng chi phí hỗ trợ phúc lợi: {{formatCurrency(money)}} </Strong></h5>
      </div>
      <div class="col-12 cart-department">
        <div class="cart-department-bottom" v-for="x in costs" :key="x.name">
          <div class="card cart-body-department" style="width: 18rem;">
            <img  class="card-img-top">
            <div class="card-body">
              <h5 class="card-title">{{x.name}}</h5>
              <p class="card-text">{{x.quantity}} nhân viên</p>
              <h6><Strong>Tổng chi phí: {{formatCurrency(x.total)}}</Strong></h6>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>

import StaffService from "@/service/hrService";
import _ from 'lodash'
export default {
  data() {
    return{
      costs: [],
      money: ''
    }
  },
  methods: {
    formatCurrency(value) {
      return Intl.NumberFormat("vi-VN", {
        style: "currency",
        currency: "VND",
      }).format(value);
    }
  },
  mounted(){
    StaffService.GetCost()
        .then(response => {
          this.costs = response.data
          this.money = _.sumBy(response.data, 'total')
        })
  }  
}
</script>
<style>
.cost{
  background: rgba(226, 196, 196, 0.13);

}
.cart-department{
  height:220px;
  width:300px;
  padding:15px;
  overflow-x:auto;
  background: rgba(226, 196, 196, 0.13);

}
.cart-body-department{
   float: left;
   line-height: 40px;
   margin-left: 18px;
}
.cart-department-bottom{
  width: 5000px;
}
.cart-body-department:hover{
    border: 1px solid;
    padding: 7px;
    box-shadow: 10px 10px rgb(98, 97, 97);
}

</style>
