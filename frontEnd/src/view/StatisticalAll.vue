<template>
    <div>
        <h1 class="hr-title"><Strong>THỐNG KÊ</Strong></h1>
      <div class="row">
        <div class="col-6" style="height: 1000px;">
          <StatisticalStaff></StatisticalStaff>
        </div>
        <div class="col-6">
            <div class="col-12" style="margin-bottom: 20px;">
                <div style="height: 300px;">
                    <statistical-cost></statistical-cost>
                </div>
            </div>
            <div class="col-12">
                <StatisticPage></StatisticPage>
            </div>

         
        </div>
      </div>
    </div>
</template>
  
<script>
import StatisticalCost from './StatisticalCost.vue';
  import StatisticalStaff from './StatisticalStaff.vue';
  import StatisticPage from './StatisticPage.vue';
  
  export default {
      components: { StatisticalStaff, StatisticPage, StatisticalCost }
  }
</script>
  