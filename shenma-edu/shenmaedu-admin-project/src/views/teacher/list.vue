<template>
  <div class="app-container">
    <!--搜索-->
    <el-card>
      <!--头部-->
      <el-header style="font-size: 16px;margin-left:-25px;margin-top: -5px">讲师列表</el-header>
      <!--查询表单-->
      <el-form label-width="80px">
        <el-row :gutter="1">
          <el-col :span="6">
            <el-form-item label="姓名:">
              <el-input v-model="searchObj.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="职业:">
              <el-input v-model="searchObj.career"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="等级:">
              <el-select v-model="searchObj.level" placeholder="请选择">
                <el-option
                  v-for="item in levels"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="2">
            <el-form-item>
              <el-button type="primary" @click="search" icon="el-icon-search" style="padding:12px;">查 询</el-button>

            </el-form-item>
          </el-col>
          <el-col :span="2">
            <el-form-item>
              <el-button type="info" @click="clearForm">清 空</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!--表格数据显示-->
      <el-card class="box-card">
        <el-table
          @selection-change="handleSelectionChange"
          :data="list"
          border
          fit
          highlight-current-row
          element-loading-text="数据加载中"
          empty-text="暂无数据显示"
          style="width: 100%">
          <!--复选框-->
          <el-table-column type="selection" width="55px"></el-table-column>
          <el-table-column
            align="center"
            label="序号"
            width="80"
          >
            <template slot-scope="scope">
              {{ scope.$index +1}}
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            label="姓名"
            width="100"
            prop="name"
          >
          </el-table-column>
          <el-table-column
            align="center"
            label="水平"
            width="80"
            prop="level"
            :formatter="formatLevel"
          >

          </el-table-column>
          <el-table-column
            align="center"
            label="介绍"
            prop="intro"
          >
          </el-table-column>
          <el-table-column
            align="center"
            label="职业"
            width="180"
            prop="career"
          >
          </el-table-column>
          <el-table-column
            align="center"
            label="创建时间"
            width="180"
            prop="createTime"
          >
          </el-table-column>
          <el-table-column label="操作" align="center" width="200px">
            <template slot-scope="scope">
              <el-button
                size="mini"
                icon="el-icon-edit"
                type="primary"
                @click="searchById(scope.row.id),dialogFormVisible=true">编 辑
              </el-button>
              <el-button
                size="mini"
                type="danger"
                icon="el-icon-delete"
                @click="handleDelete(scope.row.id)">删 除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
      <!--批量删除按钮-->
      <div style="position: absolute;margin-top: 20px">
        <el-button type="primary" @click="delAllSelection" :disabled="disabled">批量删除</el-button>
        <el-tooltip placement="top">
          <div slot="content">删除的信息不能恢复</div>
          <i class="el-icon-warning" style="width: 3px;height: 3px"></i>
        </el-tooltip>
      </div>
      <!--分页插件-->
      <el-pagination
        style="margin-top: 20px"
        align="right"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="page"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
      <!--修改内容一个弹窗-->
      <el-dialog title="修改页面" :visible.sync="dialogFormVisible" width="650px">
        <el-form :model="updateForm" label-width="100px" :rules="formRules" ref="formRef">
          <!--    <el-input v-model="searchObj.id" type="hidden" autocomplete="off" ></el-input>-->
          <el-form-item label="讲师名:" prop="name">
            <el-input v-model="updateForm.name" name="teacher" autocomplete="off" style="width: 400px"></el-input>
          </el-form-item>
          <el-form-item label="头像:">
            <el-upload
              class="avatar-uploader"
              action="http://localhost:8001/shenma/oss/upload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
              <img v-if="imageUrl" :src="imageUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="等级:">
            <el-select v-model="updateForm.level" placeholder="请选择">
              <el-option
                v-for="item in levels"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="职业:">
            <el-input v-model="updateForm.career" autocomplete="off" style="width: 400px"></el-input>
          </el-form-item>
          <el-form-item label="排序:">
            <el-input v-model="updateForm.sort" autocomplete="off" type="number" :min="0" name="sort"
                      style="width: 400px" placeholder="必须填数字"></el-input>
          </el-form-item>
          <el-form-item label="介绍:">
            <el-input
              type="textarea"
              :rows="2"
              style="width: 500px"
              placeholder="请输入内容"
              v-model="updateForm.intro">
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="handleEdit()">确 定</el-button>

          </el-form-item>
        </el-form>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
  import {deleteTeacher, getTeacherById, getTeacherPageList, updateTeacher,delAllSelection} from "../../api/teacher";

  export default {
    data() {
      return {
        formRules: {
          name: [
            {required: true, message: '讲师名不能为空！', trigger: 'blur'}
          ],
        },
        levels: [
          {
            "label": '初级讲师',
            "value": 0
          },
          {
            "label": '中级讲师',
            "value": 1
          },
          {
            "label": '高级讲师',
            "value": 2
          }
        ],
        list: [],
        page: 1,
        size: 10,
        total: 0,
        searchObj: {},
        updateForm: {},
        dialogFormVisible: false,
        imageUrl: '',
        multipleSelection:'',
        ids:[],
        disabled:true
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      fetchData() {
        getTeacherPageList(this.page, this.size, this.searchObj).then(response => {
          this.list = response.data.records
          this.total = response.data.total
        }).catch(error => {
          this.message.error(error);
        })
      },
      handleSizeChange(val) {
        this.size = val
        this.fetchData();
      },
      handleCurrentChange(val) {
        this.page = val
        this.fetchData();
      },
      searchById(id) {
        getTeacherById(id).then(response => {
          this.updateForm = response.data.record
          this.imageUrl = this.updateForm.avatar
        }).catch(error => {
        })
      },
      clearForm() {
        this.searchObj = {};
      },
      search() {
        this.page = 1
        this.size = 10
        this.fetchData();
      },
      formatLevel: function (row, column, cellValue) {
        if (cellValue === 0) {
          return '初级讲师'
        } else if (cellValue === 1) {
          return '中级讲师'
        } else if (cellValue === 2) {
          return '高级讲师'
        }
      },
      handleEdit() {
        this.$refs.formRef.validate(valid => {
          if (valid) {
            this.updateForm.avatar = this.imageUrl
            updateTeacher(this.updateForm).then(response => {
                if (response.success) {
                  this.$message.success("更新成功")
                  this.updateForm = {}
                  this.fetchData()
                }
              }
            ).catch(error => {
              this.$message.error("更新失败")
            })
            this.dialogFormVisible = false
          } else {
            this.dialogFormVisible = true
          }
        })
      },
      //获取选中记录的值
      handleSelectionChange(val){
        this.multipleSelections = val
        if(this.multipleSelections.length > 0){
          this.disabled = false
        }else{
          this.disabled = true
        }
      },
      //批量删除
      delAllSelection(){
        //清空ids中之前的值
        this.ids = []
        const length = this.multipleSelections.length
        for(let i = 0; i < length; i++){
          this.ids.push(this.multipleSelections[i].id)
        }
        this.$confirm('此操作会删除选中记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
          }).then(() => {
            console.log(this.ids)
              delAllSelection(this.ids).then(response=>{
                  this.$message({
                    type:'success',
                    message:'删除成功'
                  })
                this.fetchData()
              })
          }).catch(error => {
            this.$message({
              type: 'info',
              message: '删除失败'
            });

        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        })
      },
      handleDelete(id){
        //进行判断是否删除
        this.$confirm('此操作会删除选中的一条记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteTeacher(id).then(response => {

            if (response.success) {
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.fetchData()
            }
          }).catch(error => {
            this.$message({
              type: 'info',
              message: '删除失败'
            });
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      handleAvatarSuccess(res, file) {
        this.imageUrl = res.data.imageUrl;
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      }
    }
  }
</script>
<style scoped>
  .box-card {
    width: 100%;
    margin-top: 10px;
  }

  .el-row {
    margin-top: 20px;
  }

  .avatar-uploader {
    width: 150px;
    height: 150px;
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 150px;
    height: 150px;
    line-height: 150px;
    text-align: center;
  }

  .avatar {
    width: 150px;
    height: 150px;
    display: block;
  }
</style>
