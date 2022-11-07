<template>
  <div>
    <H1 style="text-align: center; font-family: system-ui, -apple-system, BlinkMacSystemFont,
    'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif"><strong>Thống kê</strong></H1>
  <div class="container py-5 h-100" >
    <div class="row d-flex justify-content-center align-items-center h-100 ">
      <div class="col" >
        <div class="card bg-pink">
          <div class="card-body p-4">

            <div class="row">

              <div class="col-lg-7">
                <h3 class="mb-3"><Strong>Thống kê chi phí</Strong></h3>
                <hr>
                <h4>Tổng số tiền hỗ trợ phúc lợi trong năm nay: <strong>123123</strong> </h4>

                <div class="card mb-3" v-for="x in departments" :key="x.id">
                  <div class="card-body">
                    <div class="d-flex justify-content-between">
                      <div class="d-flex flex-row align-items-center">
                        <div class="ms-3">
                          <h5>{{x.name}}</h5>
                          <p class="small mb-0">Có  nhân viên</p>
                        </div>
                      </div>
                      <div class="d-flex flex-row align-items-center">
                        <div style="width: 200px;">
                          <h5 class="fw-normal mb-0"> Tổng chi phi hỗ trợ: </h5>
                        </div>
                        <div style="width: 200px;">
                          <h4 class="mb-0"><strong>12312321</strong></h4>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>     

              </div>
              <div class="col-lg-5">
                <div class="card bg-infeo  rounded-3">
                  <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                      <h3 class="mb-0"><strong>Mức độ sử dụng các phúc lợi</strong></h3>   
                    </div>
                    <form class="mt-4">
                     <h5>
                      <ul style="text">
                        <li>1. Phúc lợi: A có 88 lượt sử dụng</li>
                        <li>2. Phúc lợi: B có 60 lượt sử dụng</li>
                        <li>3. Phúc lợi: C có 55 lượt sử dụng</li>
                        <li>4. Phúc lợi: D có 15 lượt sử dụng</li>
                      </ul>
                     </h5>

                    </form>
                    <hr class="my-4">
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

</template>

<script>
import StaffService from '../service/hrService'
// import _ from 'lodash'

export default {
  name: 'MyCheckBox',
  data() {
    return {
    departments: [],
    staffs: []
    };
  },
  methods: {
    getDepartment() {
      StaffService.getDepartments()
      .then(response => {
          this.departments = response.data;
          console.log(this.departments.id)
        })
    },
    getStaff(id) {
      StaffService.getStaffByDepartment(id)
            .then(response => {
              this.staffs = response.data
              console.log(this.staffs);
              return this.staffs;
        })
         
    },
 
    formatCurrency(value) {
      return Intl.NumberFormat("vi-VN", {
        style: "currency",
        currency: "VND",
      }).format(value);
    },
  },
  created() {
    this.getDepartment()
  }

}
</script>

<style>

</style>