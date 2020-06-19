<template>
  <div class="app-container">
    <el-card>
      <el-header align="middle" style="font-size:1.5em;">发布课程</el-header>
      <!--步骤条-->
      <el-steps :active="active" align-center>
        <el-step title="填写课程信息"></el-step>
        <el-step title="填写课程大纲"></el-step>
        <el-step title="课程发布"></el-step>
      </el-steps>
      <!--章节的详细信息-->
      <div class="chapter-container">
        <!--添加章节按钮-->
        <el-button type="primary" size="mini" @click="openOneChapterAdd">添加一级章节</el-button>
        <!--具体章节-->
        <div class="chapter">
          <ul v-for="chapter in chapterList" class="ul-father">
            <li class="li-father">{{chapter.name}}
              <!--添加按钮-->
              <div class="Btn">
                <el-button type="text" @click="openTwoChapterAdd(chapter.id)">添加二级章节</el-button>
                <el-button type="text" @click="openOneChapterUpdate(chapter.id)">修改</el-button>
                <el-button type="text" @click="oneChapterDelete(chapter.id)">删除</el-button>
              </div>
              <ul v-for="video in chapter.videoList" class="ul-sub">
                <li>
                  {{video.title}}
                  <!--添加按钮-->
                  <div class="Btn">
                    <el-button type="text" @click="openTwoChapterUpdate(video.id)">修改</el-button>
                    <el-button type="text" @click="twoChapterDelete(video.id)">删除</el-button>
                  </div>
                </li>
              </ul>
            </li>
          </ul>
        </div>
      </div>
      <!--添加章节弹窗-->
      <el-dialog :title="chapterTitle" :visible.sync="dialogChapterVisible">
        <el-form :model="chapter" ref="chapterRef" :rules="chapterRules" label-width="100px">
          <el-form-item label="章节名称" prop="name">
            <el-input v-model="chapter.name"></el-input>
          </el-form-item>
          <el-form-item label="排序" prop="sort">
            <el-input v-model="chapter.sort" type="number" min="0"></el-input>
          </el-form-item>
          <el-form-item label="是否免费">
            <el-switch
              v-model="chapter.free"
              active-color="#13ce66"
              inactive-color="#ff4949"
              :active-value="0"
              :inactive-value="1"
            >
            </el-switch>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogChapterVisible = false">取 消</el-button>
          <el-button type="primary" @click="oneChapterSaveOrUpdate">确 定</el-button>
        </div>
      </el-dialog>
      <!--添加视频弹窗-->
      <el-dialog :title="videoTitle" :visible.sync="dialogVideoVisible">
        <el-form :model="video" label-width="120px" :rules="videoRules" ref="videoRef">
          <el-form-item label="视频标题" prop="title">
            <el-input v-model="video.title" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="视频封面" required>
            <el-upload
              action="http://localhost:8001/shenma/oss/upload?host=video"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeImageUpload">
              <img v-if="imageUrl" :src="imageUrl" class="img">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="视频时长(分钟)" prop="time">
            <el-input type="number" min="0" v-model="video.time" style="width: 200px"/>
          </el-form-item>
          <el-form-item label="上传视频" required>
            <el-upload
              action="http://localhost:8002/videoservice/vod/upload"
              :before-upload="beforeVideoUpload"
              :before-remove="beforeRemove"
              :on-remove="handleRemove"
              :on-exceed="handleExceed"
              :on-success="handleSuccess"
              drag
              :limit="limit"
              :file-list="fileList"
            >
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">
                将视频拖到此处，或
                <em>点击上传</em>
              </div>
              <div slot="tip" class="el-upload__tip" style="color: red">*上传文件大小不能超过 1G</div>
            </el-upload>
            <!--进度条-->
            <el-progress
              v-show="showProgress"
              :text-inside="true"
              :stroke-width="30"
              style="height:20px"
              :percentage="progress"
            ></el-progress>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogVideoVisible = false">取 消</el-button>
          <el-button type="primary"@click="twoChapterSaveOrUpdate">确 定</el-button>
        </div>
      </el-dialog>
      <!--提交按钮-->
      <el-footer>
        <el-button type="primary" style="margin-right: 60px" @click="preview">上一步
        </el-button>
        <el-button type="primary" @click="next">下一步
        </el-button>
      </el-footer>
    </el-card>
  </div>
</template>

<script>
  import {
    getChaptersByCourseId,
    addCourseChapter,
    modifyCourseChapter,
    deleteChapter,
    getChapterById
  } from '@/api/chapter'
  import {addVideo, modifyVideo, removeVideo, getVideoById,removeAliyunVideo} from '@/api/video'

  export default {
    name: "chapter",
    data() {

      return {

        videoId:'',
        chapterId: '',
        courseId: '',
        chapterTitle: '',
        videoTitle: '',
        imageUrl: '',//上传图片的地址
        active: 2,
        dialogChapterVisible: false,
        dialogVideoVisible: false,
        chapter: {
          free: 0
        },
        courseChapter: {},
        chapterList: [],
        //校验chapter
        chapterRules: {
          name: [
            {required: true, message: '请输入章节名', trigger: 'blur'}
          ],
          sort: [
            {required: true, message: '请输入排序', trigger: 'blur'}
          ]
        },
        //校验视频信息
        videoRules: {
          title: [
            {required: true, message: '视频标题为空，请填写完整', trigger: 'blur'}
          ],
          time: [
            {required: true, message: '请输入时长', trigger: 'blur'}
          ]
        },
        //上传视频定义的变量
        video: {},
        fileList: [],//文件列表
        showProgress: false,//进度条的显示
        dataObj: {}, //存签名信息
        progress: 0, //进度条数据
        limit: 1
      }
    },
    created() {
      if (this.$route.params && this.$route.params.id) {
        this.courseId = this.$route.params.id
      }
      this.fetchData()
    },
    methods: {
      fetchData() {
        getChaptersByCourseId(this.courseId).then(response => {
          if (response.success) {
            this.chapterList = response.data.chapterList
          }
        })
      },
      //修改或者添加--------------------一级章节
      oneChapterSaveOrUpdate() {
        this.$refs.chapterRef.validate(valid => {
          if (valid) {
            //判断id是否存在
            if (!this.chapterId) {//增加
              this.chapter.courseId = this.courseId
              addCourseChapter(this.chapter).then(response => {
                if (response.success) {
                  this.$message({
                    type: 'success',
                    message: '添加成功'
                  })
                  this.fetchData()
                } else {
                  this.$message({
                    type: 'error',
                    message: '添加失败'
                  })
                }
                this.dialogChapterVisible = false
              })
            } else {//修改
              modifyCourseChapter(this.chapter).then(response => {
                if (response.success) {
                  this.$message({
                    type: 'success',
                    message: '修改成功'
                  })
                  this.fetchData()
                } else {
                  this.$message({
                    type: 'error',
                    message: '修改失败'
                  })
                }
                this.dialogChapterVisible = false
              })
            }
          } else {
            this.$message({
              type: 'error',
              message: '请填写完整信息'
            })
            this.dialogChapterVisible = true
          }
        })
      },

      ////修改或者添加--------------------二级章节
      twoChapterSaveOrUpdate() {
        //校验
        this.$refs.videoRef.validate(valid => {
          if (valid) {
            if (!this.videoId) {//添加
              this.video.chapterId = this.chapterId;//章节id
              this.video.courseId = this.courseId//课程id
              this.video.coverImg = this.imageUrl;//添加封面
              addVideo(this.video).then(response => {
                if (response.success) {
                  this.$message({
                    type: 'success',
                    message: '二级章节添加成功'
                  })
                  this.fetchData()
                  this.dialogVideoVisible = false
                } else {
                  this.$message({
                    type: 'error',
                    message: '二级章节添加失败'
                  })
                  this.dialogVideoVisible = true
                }
              })
            } else {//修改
              this.video.coverImg = this.imageUrl
              modifyVideo(this.video).then(response =>{
                if(response.success){
                  this.$message({
                    type: 'success',
                    message: '二级章节更新成功'
                  })
                  this.dialogVideoVisible = false
                  this.fetchData()
                }else{
                  this.$message({
                    type: 'error',
                    message: '二级章节更新失败'
                  })
                  this.dialogVideoVisible = true
                }
              })
            }
          } else {
            this.$message({
              type: 'error',
              message: '请把信息填写完整'
            })
            this.dialogVideoVisible = true
          }
        })
      },
      //打开一级章节添加窗口
      openOneChapterAdd() {
        this.dialogChapterVisible = true
        this.chapterTitle = '添加一级章节'
        this.chapterId = '' //判断是不是为空
        this.chapter = {free: 0}
      },
      //打开一级章节更新窗口
      openOneChapterUpdate(id) {
        this.dialogChapterVisible = true
        this.chapterTitle = '修改一级章节'
        //通过id获取章节信息
        getChapterById(id).then(response => {
          this.chapter = response.data.chapter
          this.chapterId = this.chapter.id
        })
      },
      //打开添加二级章节窗口
        //添加
      openTwoChapterAdd(chapterId) {
        this.videoTitle = '添加二级章节'
        this.dialogVideoVisible = true
        this.chapterId = chapterId //二级章节所要的一级章节id
        this.imageUrl = ''
        this.videoId = ''
        this.video = {}
        this.fileList=[]
      },
        //修改
      openTwoChapterUpdate(videoId) {
        this.videoTitle = '修改二级章节'
        this.dialogVideoVisible = true
        //获取视频信息
        getVideoById(videoId).then(response =>{
          this.video = response.data.video
          this.imageUrl = this.video.coverImg //图片赋值
          this.videoId = this.video.id  //给videoId赋值
          this.fileList = [{name:this.video.videoOriginName}]//给文件名赋值
        })

      },
      //一级删除
      oneChapterDelete(id) {
        //进行判断是否删除
        this.$confirm('此操作会删除选中的一条记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteChapter(id).then(response => {
            if (response.success) {
              this.$message({
                type: 'success',
                message: '删除成功'
              })
              this.fetchData()
            } else {
              this.$message({
                type: 'error',
                message: '删除失败'
              })
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      //二级删除
      twoChapterDelete(id) {
        //进行判断是否删除
        this.$confirm('此操作会删除选中的一条记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          //二级章节信息删除
          removeVideo(id).then(response => {
            if (response.success) {
              this.$message({
                type: 'success',
                message: '删除成功'
              })
              this.fetchData()
            } else {
              this.$message({
                type: 'error',
                message: '删除失败'
              })
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      //下一步
      next() {
        this.$router.push('/course/release/' + this.courseId)
      },
      //上一步
      preview() {
        this.$router.push('/course/info/' + this.courseId)
      },
      //上传图片使用的方法
      handleAvatarSuccess(res, file) {
        //获取服务器中图片地址
        this.imageUrl = res.data.imageUrl
      },
      beforeImageUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      },
      //上传视频中的一些回调函数
      // 文件超出个数限制时的钩子
      handleExceed(files, fileList) {
        this.$message.warning(`每次只能上传 ${this.limit} 个文件`);
      },
      //移除视频专用
      handleRemove(file, fileList) {
        removeAliyunVideo(this.video.videoSourseId).then(response =>{
            this.$message({
              type:'success',
              message:'视频删除成功'
            })
            this.fileList = []
            this.video.videoSourseId = ''
        })
      },
      // 删除文件之前的钩子
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${file.name}？`);
      },
      // 文件上传成功时的钩子
      handleSuccess(response, file, fileList) {
        this.video.videoSourseId = response.data.videoId;
        //为文件名赋值
        this.video.videoOriginName = file.name
        this.$message("上传成功")
      },
      //文件上传前的校验
      beforeVideoUpload(file) {
        const isLt100M =
          file.size / 1024 / 1024 > 5 && file.size / 1024 / 1024 < 1024;
        const isLt30 = file.name.length < 30;
        if (["video/mp4"].indexOf(file.type) == -1) {
          this.$message.error("请上传正确的视频格式");
          return false;
        }
        if (!isLt100M) {
          this.$message.error("上传视频大小要在10MB~1GB之间哦!");
          return false;
        }
        if (!isLt30) {
          this.$message.error("上传视频文件名称长度必须要小于30个文字哦!");
          return false;
        }
      },

    }
  }

</script>

<style scoped>
  .el-footer {
    text-align: center;
    margin-top: 20px;
  }

  .chapter-container {
    margin: 0 auto;
    margin-top: 60px;
    width: 85%;
    position: relative;
  }

  .chapter {
  }

  ul {
    list-style-type: none;
  }

  ul li:hover {
    background-color: bisque;
  }

  .ul-father {
    font-size: 25px;
    margin-left: -40px;
  }

  .ul-father .li-father {
    box-shadow: 0 0 2px 2px darkgray;
    border: 1px solid darkgray;
    background-color: white;
    padding: 10px;
  }

  .ul-sub {
    margin-top: 10px;
    font-size: 16px;
    margin-left: 40px;
  }

  .ul-sub li {
    display: block;
    height: 40px;
    margin-top: 20px;
    line-height: 40px;
    box-shadow: 0 0 1px 1px #b3d4fc;
    text-indent: 1em;
  }

  .Btn {
    margin-top: -3px;
    display: inline-block;
    right: 40px;
    position: absolute;
    font-size: 25px;
  }

  .avatar-uploader-icon {
    border: 1px dashed skyblue;
    font-size: 28px;
    color: #8c939d;
    width: 200px;
    height: 200px;
    line-height: 200px;
    text-align: center;
  }

  .img {
    width: 200px;
    height: 200px;
  }

  .avatar-uploader-icon:hover {
    border: 1px dashed #1482f0;
  }
</style>
