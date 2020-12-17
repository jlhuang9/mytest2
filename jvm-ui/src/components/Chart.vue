<template>
  <div class="hello">
    <el-form :inline="true" :rules="rules" ref="formInline" :model="formInline" class="demo-form-inline">
      <el-form-item label="name" prop="name">
        <el-select
          v-model="formInline.name"
          filterable
          remote
          reserve-keyword
          placeholder="name words"
          :remote-method="remoteMethod"
          :no-data-text="noDateString"
          :loading="loading">
          <el-option
            v-for="item in options"
            :key="item.name"
            :label="item.name"
            :value="item.name">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="date range" prop="dateRange">
        <el-date-picker
          v-model="formInline.dateRange"
          type="daterange"
          align="right"
          unlink-panels
          range-separator="-"
          start-placeholder="start date"
          end-placeholder="end date"
          :picker-options="pickerOptions">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">search</el-button>
      </el-form-item>
    </el-form>

    <el-row>
      <el-col :span="12">
        <transition enter-active-class="animated bounceInUp">
          <section class="record-chart">
            <el-title-header :title="$route.meta.title"></el-title-header>

            <div id="chart" style="height: 500px;"></div>
          </section>
        </transition>
      </el-col>
      <el-col :span="12">
        <transition enter-active-class="animated bounceInUp">
          <section class="record-chart">
            <el-title-header :title="$route.meta.title"></el-title-header>
            <div id="chart2" style="height: 500px;"></div>
          </section>
        </transition>
      </el-col>
    </el-row>


    <div>This is just a demo ,Because I have no idea how to display Jenkins data</div>
  </div>
</template>

<script>
  import taskApi from "../ajax/taskApi";

  export default {
    name: "Chart",
    data() {
      return {
        pickerOptions: {
          shortcuts: [{
            text: 'Last week',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: 'Last month',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }]
        },
        rules: {
          name: [{required: true, message: 'name is required', trigger: 'change' }],
          dateRange: [{type: "array", required: true, message: 'date range is required', trigger: 'change'}]
        },
        loading: false,
        noDateString: "no data",
        options: [],
        formInline: {
          dateRange: null,
          name: ""
        },
        dataArray: [],
        taskArray: [],
        templateChat: {
          color: ['#3398DB'],
          title: {
            text: '',
            left: 'center',
            align: 'right'
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
              type: 'line'        // 默认为直线，可选为：'line' | 'shadow'
            }
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: [
            {
              type: 'category',
              data: ['08-07', '08-08', '08-09', '08-10', '12-11', '12-12', '12-13'],
              axisTick: {
                alignWithLabel: true
              }
            }
          ],
          yAxis: [
            {
              type: 'value'
            }
          ],
          series: [
            {
              name: 'total',
              type: 'line',
              barWidth: '60%',
              data: [10, 52, 200, 334, 390, 330, 220]
            }
          ]
        }
      };
    },
    methods: {
      test() {
        taskApi.query({}, data => {
          debugger
        });
      },

      remoteMethod(name) {
        let query = {
          name: name
        };
        taskApi.getTopWorkspaces(query, result => {
          this.options = result ? result : [];
        })
      },
      onSubmit() {
        this.$refs["formInline"].validate((valid) => {
          if (valid) {
            let query = {
              type: 2,
              name: this.formInline.name
            };
            this.toDateArrayRange();
            taskApi.getTasks(query, result => {
              if (result) {
                this.taskArray = result;
                this.refresh();
              }
            });
          } else {
            console.log('error submit!!');
            return false;
          }
        });

      },
      debug() {
        debugger;
      },
      refresh() {
        let taskArray = this.taskArray;
        let dateCount = {};
        let numberArray = [];
        let duration = [];
        for (let i = 0; i < taskArray.length; i++) {
          let date = new Date(taskArray[i].timestamp);
          let dateStr = this.getDateStr(date);
          dateCount[dateStr] = dateCount[dateStr] ? dateCount[dateStr] + 1 : 1;
          numberArray.push(taskArray[i].number);
          duration.push(taskArray[i].duration ? taskArray[i].duration : 0);
        }

        let dataArray = this.dataArray;
        let countArray = [];
        let tempCount;
        for (let i = 0; i < dataArray.length; i++) {
          tempCount = dateCount[dataArray[i]] ? dateCount[dataArray[i]] : 0;
          countArray.push(tempCount)
        }
        this.templateChat.title.text = "Build times";
        this.templateChat.xAxis[0].data = dataArray;
        this.templateChat.series[0].data = countArray;
        this.templateChat.series[0].name = "count";
        this.templateChat.series[0].type = "line";
        this.$echarts.init(document.querySelector('#chart'));
        let myChart = this.$echarts.init(document.querySelector('#chart'));
        myChart.setOption(this.templateChat);
        this.templateChat.title.text = "Build duration";
        this.templateChat.xAxis[0].data = numberArray;
        this.templateChat.series[0].data = duration;
        this.templateChat.series[0].name = "duration(ms)";
        this.templateChat.series[0].type = "bar";
        let myChart2 = this.$echarts.init(document.querySelector('#chart2'));
        myChart2.setOption(this.templateChat);

      },
      getDateStr(date) {
        return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
      },
      toDateArrayRange() {
        let startTime = this.formInline.dateRange[0];
        let endTime = this.formInline.dateRange[1];
        let startDate = new Date(startTime.getFullYear(), startTime.getMonth(), startTime.getDate());
        let endDate = new Date(endTime.getFullYear(), endTime.getMonth(), endTime.getDate());
        let tempDate = startDate;
        let timeArray = [];
        while (tempDate <= endDate) {
          timeArray.push(this.getDateStr(tempDate));
          tempDate = new Date(tempDate.getTime() + 24 * 60 * 60 * 1000);
        }
        this.dataArray = timeArray;
      },
    },
    mounted() {
      // this.$nextTick(() => {
      //   this.initChart();
      // })
    }

  }
</script>

<style scoped>

</style>
