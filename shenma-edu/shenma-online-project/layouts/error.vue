<template>
    <div class="container">
       <div class="error-img">
         <img src="../assets/404_images/404.png">
         <div class="shouye"><a href="/">返回首页</a></div>
       </div>

    </div>

</template>

<script>
    export default {
        name: "error"
    }
</script>

<style scoped>
  .shouye a{
    display: inline-block;
    width: 150px;
    height: 35px;
    font-size: 16px;
    line-height: 35px;
    border-radius: 30px;
    color: black;
    background: #41B883;
    opacity: 0.7;
  }
  a:hover{
    opacity: 1;
    color: white;
    text-decoration: none;
  }
  .shouye{
    text-align: center;
    width: 200px;
    position: absolute;
    bottom: 100px;
    right: 20px;
  }
  .error-img img{
    width: 100%;
    height: 100%;
  }
  .error-img{
    width: 70%;
    height: 100%;
    margin: 0 auto;
    position: relative;
  }
  .container{
    width: 100%;
    height: 500px;

  }
</style>
