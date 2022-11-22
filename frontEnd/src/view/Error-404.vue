<template>
    <div class="error-middle">
	<strong class="fa fa-times-circle" > Error 404 - Page Not Found </strong><br>
    _________________________________________<br>
    <h3>Trang không được tìm thấy. Vui lòng kiểm tra lại đường dẫn.</h3>
    <h3>Page not found. Please check the link again.</h3>
    
</div>
</template>

<script>
    export default{
        data(){

        },
    }
</script>

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