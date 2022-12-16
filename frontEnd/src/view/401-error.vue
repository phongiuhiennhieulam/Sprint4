<template>
    <div class="error-middle" v-if="!hasRole">
      <strong class="fa fa-times-circle"> Error 401 - Unauthorized</strong
      ><br />
      _________________________________________<br />
      <h3>Bạn không có quyền truy cập vào đường dẫn này.</h3>
      <h3>You are not authorized to access this link.</h3>
    </div>
</template>
<script></script>
<style>
.error-middle{
    font-size: xx-large;
    text-align: center;
    margin-top: 100px;
}
.error-middle h3{
    font-size: large;
    padding-top: 6px;
    font-style: italic;
}
.fa{
    letter-spacing: 3px;
}
</style>