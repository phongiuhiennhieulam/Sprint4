<template>
  <div>
    <select name="LeaveType" @change="onChange($event)" class="form-control" v-model="key">
   <option value="1">Annual Leave/ Off-Day</option>
   <option value="2">On Demand Leave</option>
</select>
  </div>
</template>

<script>
export default {
  data() {
    return {
      key: ""
    };
  },
  methods: {
        onChange(event) {
            console.log(event.target.value)
        }
    }
}
</script>

<style>

</style>