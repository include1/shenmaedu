<template>
  <!--个人账号-->
  <div class="main">
    <div class="container">
      <div class="cd-content">
        <!--左边部分-->
        <div class="cd-sidebar">
          <ul class="cd-sidebar__list">
            <li class="cd-sidebar__heading">
              个人中心
            </li>
            <li class="cd-sidebar__item ">
              <a href="/account/setting"  >个人信息</a>
            </li>
            <li class="cd-sidebar__item " >
              <a href="/account/course" >我的课程</a>
            </li>

            <li class="cd-sidebar__item ">
              <a href="/account/comment"   >我的评论</a>
            </li>
            <li class="cd-sidebar__item  " >
              <a href="/account/order"   >我的订单</a>
            </li>
            <li class="cd-sidebar__item  active"  >
              <a href="/account/password"  >修改密码</a>
            </li>

          </ul>
        </div>
        <!--右边部分-->
        <div class="cd-main">
          <!--个人信息-->
          <div class="cd-main__heading">
            <div class="cd-main__title">修改密码</div>
          </div>
          <div class="cd-main__body">
            <!--修改密码的表单-->
            <el-form  :model="sysUserForm" status-icon :rules="rules" ref="sysUserForm"  class="demo-ruleForm" label-width="30%">
              <el-form-item label="用户名" >
                <span>{{sysUserForm.username}}</span>
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
              <el-form-item >
                <el-button type="primary" @click="submitForm('sysUserForm')">提交</el-button>
                <el-button @click="resetForm('sysUserForm')">重置</el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import "~/assets/css/account.css"
  import user from '@/api/user'
  import Cookies from 'js-cookie'
    export default {
        layout:'default',
        name: "password",
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
              username:'',
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
                {min:5,max:15,message:'密码的个数在6到15之间',trigger: 'blur'}
              ],
              rePassword: [
                { validator: validatePass2, trigger: 'blur' },
                {min:5,max:15,message:'密码的个数在6到15之间',trigger: 'blur'}
              ]
            }
          }
        },
        mounted() {
          //通过id查询用信息
              this.getUserName()
        },
        methods:{
          getUserName(){
            let uId = Cookies.get('uId')
            let token = Cookies.get('token')
            if(uId && token){
              user.getUserInfoById(uId).then(response =>{
                console.log(response)
                this.sysUserForm.username = response.data.data.user.username
                console.log(response.data.data.user.username)
              })
            }else {
              window.location.href='/login'
            }
          },
          submitForm(formName) {
            this.$refs[formName].validate((valid) => {
              if (valid) {
                //提交密码并修改
                user.modifyPwd(this.sysUserForm).then(response =>{
                  console.log(response)
                  if(response.data.success){
                    this.$message({
                      type:'success',
                      message:'修改密码成功'
                    })
                    //重置表单
                    this.$refs[formName].resetFields();
                  }else {
                    this.$message({
                      type:'error',
                      message:response.data.message
                    })
                  }

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

</style>
