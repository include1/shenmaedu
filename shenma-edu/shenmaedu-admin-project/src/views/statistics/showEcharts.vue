<template>
  <div class="app-container">
    <el-card>
      <el-header style="font-size: 16px;margin-left:-25px;margin-top: -5px">图表显示</el-header>

      <!--设置查询条件-->
      <el-form :model="searchObj" status-icon label-width="90px">
        <!--类型-->
        <el-row :gutter="1">
          <el-col :span="6">
            <el-form-item label="类型：">
              <el-select v-model="searchObj.type" clearable placeholder="请选择" clearable style="width: 180px">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <!--开始时间-->
          <el-col :span="5">
            <el-form-item label="选择时间：">
              <el-date-picker
                style="width: 185px"
                v-model="searchObj.begin"
                type="date"
                placeholder="选择开始日期"
                value-format="yyyy-MM-dd">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <!--结束时间-->

          <el-col :span="5">
          <el-form-item label="至">
            <el-date-picker
              style="width: 185px"
              v-model="searchObj.end"
              type="date"
              placeholder="选择结束日期"
              value-format="yyyy-MM-dd">
            </el-date-picker>
          </el-form-item>
          </el-col>
          <el-col :span="5">
          <el-form-item>
            <el-button type="primary" @click="search()">生成图表</el-button>
          </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!--图表-->
      <div id="chart" style="width: 100%;height:450px;margin-top: 10px"></div>
    </el-card>
  </div>
</template>

<script>
import echarts from 'echarts'
import {showData} from '@/api/statistics'
  export default {
    name: "echars11",
    data() {
      return {
        options:[
          {label:'每日注册数统计',value:'register_num'},
          {label:'每日登录数统计',value:'login_num'},
          {label:'每日视频数统计',value:'video_view_num'},
          {label:'每日课程数统计',value:'course_num'}
        ],
        searchObj: {
          type: 'register_num',
          begin: '2020-01-01',
          end: '2020-04-01'
        },
        xData:[],
        yData:[],
        titleData:[],
        series:['日期']
      }
    },
    created() {
        this.initData()
    },
    methods: {
      initData() {
          this.search()
      },
      search() {
        showData(this.searchObj.type,this.searchObj.begin,this.searchObj.end).then(response=>{
            this.xData = response.data.map.dateList
            this.yData = response.data.map.numberList
            for(let i = 0; i < this.options.length; i++){
                if(this.searchObj.type === this.options[i].value){
                  this.series[0] = this.options[i].label
                }
            }
            if(this.xData.length !== 0){
              this.initEcharts();
            }else{
              this.$message({
                type:'error',
                message:'该范围没有数据'
              })
            }
        })

      },
      initEcharts() {

        let myChart = echarts.init(document.getElementById('chart'));
        let option = {
          tooltip: {//鼠标移动观看数据
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              crossStyle: {
                color: '#999'
              }
            }
          },
          toolbox: {//工具栏
            feature: {
              dataView: {show: true, readOnly: false},
              magicType: {show: true, type: ['line', 'bar']},
              restore: {show: true},
              saveAsImage: {show: true}
            }
          },
          legend: {
            data: this.series
          },
          xAxis:{
              data:this.xData
            },
          yAxis: {
              type: 'value',
              name: '数量',
              data:this.yData
            },
          series:[
            {
              name: this.series[0],
              type: 'line',
              data: this.yData
            }],
            dataZoom:[{//数据刻度尺
              show: true,
              height: 30,
              xAxisIndex: [0],
              bottom: 30,
              start: 10,
              end: 80,
              handleIcon: 'path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z',
              handleSize: '110%',
              handleStyle: {
                color: '#d3dee5'
              },
              textStyle: {
                color: '#fff'
              },
              borderColor: '#90979c'
              },
              {
                type: 'inside',
                show: true,
                height: 15,
                start: 1,
                end: 35
              }]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
      }
    }
  }
</script>

<style scoped>

</style>
