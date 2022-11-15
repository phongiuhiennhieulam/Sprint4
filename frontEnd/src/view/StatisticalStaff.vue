<template>
  <div class="row pl-body">
    <div class="col-2">
      <el-row>
          <h5 style="text-align: center;" class="title-sidebar"><Strong>Danh sách phúc lợi</Strong></h5>
          <el-menu
            class="el-menu-vertical-demo"
            @open="handleOpen"
            @close="handleClose"
            background-color="#f3f4f5"
            text-color="#000"
            active-text-color="#000">
            <el-submenu index="1">
              <template slot="title">
                <Strong>Chọn tên phúc lợi</Strong>
              </template>
              <el-menu-item-group>
                <el-menu-item index="1-1" v-for="x in x" :key="x.name">{{x.name}}</el-menu-item>
              </el-menu-item-group>
            </el-submenu>
          </el-menu>
      </el-row>
    </div>
    <div class="col-1"></div>
    <div class="col-9">
      <div >
        <div class="pl-content">
          <div class="hr-title"><strong >DANH SÁCH XÉT DUYỆT</strong></div>
          <div class="pl-ele">
            <div class="pl-table">
              <div class="pl-table__content">
                <form id="form" label-width="100px">
                  <table>
                    <thead>
                      <tr>
                        <th>STT</th>
                        <th>Họ tên </th>
                        <th>Mã nhân viên</th>
                        <th>Số lượng đăng ký </th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr v-for="(item, index) in list" :key="index">
                        <td width="9%">{{ index + 1}}</td>
                        <td style="text-align: left;">{{ item.name }}</td>
                        <td style="text-align: left;">{{ item.code }}</td>
                        <td>
                          <el-button @click="handShow(item.id)" type="warning"><strong>Danh sách xét duyệt</strong></el-button>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  data() {
    return{
      x:[ 
          {
          "name": "1",
        },
        {
          "name": "name2",
        },
        {
          "name": "name3",
        },
    ]
    }
  },
  methods: {
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      }
    }

}
</script>

<style>
.title-sidebar{
  text-align: center;
  font-size: 28px;
  font-weight: 600;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen,
  Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
  /* background: rgba(223, 9, 109, 0.13); */
  padding-bottom: 10px;
  padding-top: 20px;

  
}
.sidebar{
  background: #692530;
  text-align: center;
}
.pl-title {
  text-align: center;
  font-size: 34px;
  font-weight: 600;
  font-family: "Poppins";
  background: rgba(255, 255, 255, 0.13);
  padding: 6px 0px;
}
.pl-body {
  background: linear-gradient(90deg, #e4c9ac 0%, rgba(255, 255, 255, 0) 100%),
    #e3c1d3;
  width: 100%;
  height: 100%;
}
.pl-table {
  text-align: center;
  z-index: 2;
}
.pl-table__content table {
  width: 100%;
  border-collapse: collapse;
}
.pl-table__content table tr {
  background: #f2e7ddf8;
}
.pl-table__content table tr th {
  border-right: 1px solid #e4c9ac;
  padding: 14px;
}
.pl-table__content table tr td {
  padding: 20px;
  line-height: 30px;
  height: 30px;
}
.pl-table__content table thead tr {
  background-color: #fdf9f8;
}
.pl-table__content table thead th {
  font-size: 14px;
  font-weight: 600;
}
.pl-table__content table tbody tr {
  border-bottom: 1px solid #e4c9ac;
}
.pl-table__content table tr td {
  border-right: 1px solid #e4c9ac;
}
.pl-table__content table tbody tr:hover {
  background-color: #ffc0cb;
}
.icon-delete {
  font-size: 20px;
  color: coral;
  cursor: pointer;
}
.icon-edit {
  font-size: 20px;
  color: greenyellow;
  cursor: pointer;
}
input,
textarea {
  outline: none;
  padding: 20px;
  width: 60%;
  background: transparent;
  border: none;
  border-bottom: 1px solid #000;
}
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}
[placeholder]:focus::-webkit-input-placeholder {
  transition: text-indent 0.4s 0.4s ease;
  text-indent: -100%;
  opacity: 1;
}
.pl-button {
  margin-left: 200px;
  margin-top: 30px;
}
.pl-button__detail {
  color: #f00 !important;
  font-size: 14px !important;
  font-weight: 600 !important;
}
.pl-button__detail:hover {
  background-color: rgba(255, 0, 0, 0.1) !important;
  border-color: rgba(255, 0, 0, 0.1) !important;
}
.pl-button__detail:focus {
  background-color: rgba(255, 0, 0, 0.2) !important;
  border-color: rgba(255, 0, 0, 0.2) !important;
}
.pl-button__detail:active {
  background-color: rgba(255, 0, 0, 0.3) !important;
  border-color: rgba(255, 0, 0, 0.3) !important;
}
.el-form-item__label {
  color: #f00 !important;
  font-size: 22px;
  font-weight: 600;
  line-height: 31px;
  letter-spacing: 0em;
  text-align: left;
  line-height: 0px;
  margin-bottom: 6px;
}

.el-form-item__content input {
  border-radius: 8px !important;
  box-shadow: 1px 1px 4px rgba(0, 0, 0, 0.15);
  border: 1px solid #23282b8f;
}

.el-dialog__title {
  margin-left: 56px;
  font-size: 25px;
  font-weight: 700;
  line-height: 35px;
  letter-spacing: 0em;
  text-align: left;
}

.el-dialog__headerbtn i {
  font-size: 24px;
  font-weight: 700;
  color: #f00 !important;
}
</style>