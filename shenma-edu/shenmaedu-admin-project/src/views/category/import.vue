<template>
  <div class="app-container">
    <el-card>
      <!--头部-->
      <el-header style="font-size: 16px;margin-left:-25px;margin-top: -5px">导入分类</el-header>
      <el-form label-width="120px">
        <el-form-item label="信息描述">
          <el-tag type="info">excel模板说明</el-tag>
          <el-tag>
            <i class="el-icon-download"></i>
            <a :href="'/static/课程分类.xlsx'">下载模板</a>
          </el-tag>
        </el-form-item>

        <el-form-item label="选择Excel">
          <el-upload
                      class="upload-demo"
                      ref="fileUpload"
                      :auto-upload="false"
                      :on-success="fileUploadSuccess"
                      :on-error="fileUploadError"
                      :limit=limitNum
                      action="http://localhost:8001/shenma/category/import"
                      drag
                      accept=".xlsx"
                      name="file">
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击获取文件</em></div>
            <div class="el-upload__tip" slot="tip" style="color: red;" >*只能上传一个excel文件,后缀名格式为xxx.xlsx</div>
          </el-upload>
          <el-button  style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
    export default {
        name: "import",
        data(){
          return{
            limitNum:1,
          }
        },
        created() {
        },
        methods:{
          //提交上传
          submitUpload(){
              this.$refs.fileUpload.submit()
          },
          //上传成功
          fileUploadSuccess(response) {
            if(response.success){
                this.$message({
                  type:'success',
                  message:'导入成功'
                })
            }else {
              this.$message({
                type:'error',
                message:response.message
              })
            }
          },
          //上传失败
          fileUploadError(response) {
              this.$message({
                type:'error',
                message:'上传失败'
              })

          }
        }
    }
</script>

<style scoped>

</style>
