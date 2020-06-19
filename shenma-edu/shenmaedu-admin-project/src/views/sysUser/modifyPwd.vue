<template>
  <div class="app-container">
    <el-card>
      <el-header style="font-size: 30px;margin-left:-25px;margin-top: -5px">修改密码</el-header>

      <!--修改密码的表单-->
      <el-form  :model="sysUserForm" status-icon :rules="rules" ref="sysUserForm"  class="demo-ruleForm" label-width="30%">
        <el-form-item label="用户名" prop="username">
          <el-input type="text" v-model="sysUserForm.username" autocomplete="off" disabled style="width:500px"></el-input>
        </el-form-item>
        <el-form-item label="旧密码" prop="oldPassword">
          <el-input type="password" v-model="sysUserForm.oldPassword" autocomplete="off" style="width:500px"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="password">
          <el-input type="password" v-model="sysUserForm.password" autocomplete="off" style="width:500px"></el-input>
        </el-form-item>
        <el-form-item label="确认新密码" prop="rePassword">
          <el-input type="password" v-model="sysUserForm.rePassword" autocomplete="off" style="width:500px"></el-input>
        </el-form-item>
        <el-form-item style="margin-top: 30px">
          <el-button type="primary" @click="submitForm('sysUserForm')">提交</el-button>
          <el-button @click="resetForm('sysUserForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
  import {mapGetters} from "vuex";
  import {updatePwd} from  '@/api/sysUser'
  export default {
    name: "modifyPwd",
    computed: {
      ...mapGetters([
        'name'
      ])
    },
    data(){
      //旧密码
      var validateOldPass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入旧密码'));
        } else {
          if (this.sysUserForm.password !== '') {
            this.$refs.sysUserForm.validateField('password');
          }
          callback();
        }
      };
      //新密码
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.sysUserForm.rePassword !== '') {
            this.$refs.sysUserForm.validateField('rePassword');
          }
          callback();
        }
      };
      //再次输入新密码，校验
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.sysUserForm.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return{
        sysUserForm: {
          oldPassword:'',
          password: '',
          rePassword: ''
        },
        rules: {
          oldPassword: [
            { validator: validateOldPass, trigger: 'blur' }

          ],
          password: [
            { validator: validatePass, trigger: 'blur' },
            {min:6,max:15,message:'密码的个数在6到15之间',trigger: 'blur'}
          ],
          rePassword: [
            { validator: validatePass2, trigger: 'blur' },
            {min:6,max:15,message:'密码的个数在6到15之间',trigger: 'blur'}
          ]
        }
      };
    },
    created() {
      this.sysUserForm.username = this.name
    },
    methods:{
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
              //提交密码并修改
              updatePwd(this.sysUserForm).then(response =>{
                if(response.success){
                  this.$message({
                    type:'success',
                    message:'修改密码成功'
                  })
                }
                //重置表单
                this.$refs[formName].resetFields();
              })

          } else {
            //字段不符合格则无法提交
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
  }
</script>

<style scoped>
.demo-ruleForm{
  margin-top: 30px;
}
</style>
