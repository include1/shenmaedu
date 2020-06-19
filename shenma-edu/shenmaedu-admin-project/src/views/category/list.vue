<template>
  <div class="app-container">
    <!--过滤输入框-->
    <el-input
      placeholder="输入关键字进行过滤"
      style="margin-bottom: 20px"
      v-model="filterText">
    </el-input>
    <el-card>
      <el-header style="font-size: 16px;margin-left:-25px;margin-top: -5px">分类列表</el-header>
      <el-button
        type="primary"
        size="mini"
        style="margin-bottom: 20px"

        @click="addOneCategory(),dialogFormVisible=true,category={}">
        添加一级分类
      </el-button>
      <!--树节点，显示课程分类内容-->
      <el-tree
        class="filter-tree"
        :data="categoryList"
        :props="defaultProps"
        :filter-node-method="filterNode"
        :expand-on-click-node="false"
        default-expand-all
        accordion
        ref="tree">
          <span class="custom-tree-node" slot-scope="{ node, data }">
          <span>{{ node.label}}</span>
              <el-button
                type="text"
                size="mini"
                v-if="node.level == 1"
                @click="appendTwoCategory(data.id),dialogFormVisible=true"
              >
                  &nbsp;添加二级分类
              </el-button>
              <el-button
                type="text"
                size="mini"
                @click="() => remove(node, data)">
                  &nbsp;删除
              </el-button>

        </span>
      </el-tree>
    </el-card>
    <!--添加分类窗口-->

    <el-dialog :title="formTitle" :visible.sync="dialogFormVisible" @close="clear">
      <el-form :model="category" :rules="rules" ref="formRef">
        <el-form-item style="display:none">
          <el-input v-model="category.parentId" type="hidden" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="分类名" prop="title">
          <el-input v-model="category.title" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="append()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import {addOneCategory, addTwoCategory, deleteCategory, getAllCategory} from "../../api/category";

  export default {
    name: "list",
    watch: {
      filterText(val) {
        this.$refs.tree.filter(val);
      }
    },
    data() {
      return {
        defaultProps: {
          children: 'subCategoryList',
          label: 'title'
        },
        categoryList: [],
        category: {},
        filterText: '',
        dialogFormVisible: false,
        formTitle: '',
        rules: {
          title: [
            {required: true, message: '请输入分类名', trigger: 'blur'}
          ]
        }
      }
    },

    created() {
      this.getAllCategory();
    },
    methods: {
      filterNode(value, data) {
        this.$alert(value)
        if (!value) return true;
        return data.title.indexOf(value) !== -1;
      },
      //获取分类
      getAllCategory() {
        getAllCategory().then(response => {
          this.categoryList = response.data.categorys
        })
      },
      //节点过滤器
      filterNode(value, data) {
        if (!value) return true;
        return data.title.indexOf(value) !== -1;
      },
      //添加
      addOneCategory() {
        this.formTitle = '添加一级分类'
        this.category = {}
      },
      appendTwoCategory(id) {
        this.formTitle = '添加二级分类'
        this.category.parentId = id;
      },
      append() {
        this.$refs.formRef.validate(valid => {
          if (valid) {
            if (this.category.parentId == '' || this.category.parentId == null) {
              this.category.parentId = '0'
              addOneCategory(this.category).then(response => {
                if (response.success) {
                  this.$message({
                    type: 'success',
                    message: '添加成功'
                  })
                  this.getAllCategory()
                }
              })
            } else {
              addTwoCategory(this.category).then(response => {
                if (response.success) {
                  this.$message({
                    type: 'success',
                    message: '添加成功'
                  })
                  this.getAllCategory()
                }
              })
            }
            this.dialogFormVisible = false
          } else {
            this.dialogFormVisible = true
          }
        })
      },
      //清空
      clear() {
        this.category = {}
      },
      //移除
      remove(node, data) {
        this.$confirm('此操作将删除该分类, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteCategory(data.id).then(response => {
            if (response.success) {
              this.$message({
                type: 'success',
                message: '删除成功'
              })
              this.getAllCategory()
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })


      }
    }
  }
</script>

<style scoped>

</style>
