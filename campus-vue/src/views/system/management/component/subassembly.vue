<template>
  <div>
    <!-- 基础信息卡片 -->
    <el-card class="form-card">
      <template #header>
        <div class="card-header">基础信息</div>
      </template>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="活动标题" prop="eventName">
              <el-input v-model="form.eventName" placeholder="请输入活动标题"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="活动加分" prop="mark">
              <el-input v-model="form.mark" placeholder="请输入活动加分"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>

    <!-- 活动描述卡片 -->
    <el-card class="form-card">
      <template #header>
        <div class="card-header">活动描述</div>
      </template>
      <el-form :model="form" :rules="rules" label-width="120px">
        <el-form-item label="活动简介" prop="eventIntro">
          <editor v-model="form.eventIntro" :min-height="192" />
        </el-form-item>
        <el-form-item label="注意事项" prop="precautions">
          <editor v-model="form.precautions" :min-height="192" />
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 媒体资源卡片 -->
    <el-card class="form-card">
      <template #header>
        <div class="card-header">媒体资源</div>
      </template>
      <el-form :model="form" :rules="rules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="活动图片" prop="eventPic">
              <ImageUpload @input="receiveDataFromChild" :value='fileList' />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="活动附件" prop="attachments">
              <FileUpload @input='receiveDataFrom'></FileUpload>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>

    <!-- 时间设置卡片 -->
    <el-card class="form-card">
      <template #header>
        <div class="card-header">时间设置</div>
      </template>
      <el-form :model="form" :rules="rules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="开始时间" prop="startTime">
              <el-date-picker v-model="form.startTime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"
                type="datetime" placeholder="选择开始日期" default-time='00:00:00'>
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结束时间" prop="endTime">
              <el-date-picker v-model="form.endTime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"
                type="datetime" placeholder="选择结束日期" default-time='23:59:59'>
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>

    <!-- 高级设置卡片 -->
    <el-card class="form-card">
      <template #header>
        <div class="card-header">高级设置</div>
      </template>
      <el-form :model="form" :rules="rules" label-width="120px">
        <el-row :gutter="20">
          <!-- <el-col :span="12">
            <el-form-item label="级别类型" prop="rank">
              <el-radio-group v-model="form.rank">
                <el-radio :label="0">校级</el-radio>
                <el-radio :label="1">班级</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col> -->
          <el-col :span="12">
            <el-form-item label="所有年级" prop="gradeIds">
              <el-select v-model="form.gradeIds" multiple collapse-tags placeholder="请选择">
                <el-option v-for="item in enabledDeptOptions" :key="item.id" :label="item.label" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="活动类型" prop="eventType">
              <el-select v-model="form.eventType" placeholder="请选择活动类型">
                <el-option v-for="(option, index) in eventTypeOptions" :key="index" :label="option.label"
                  :value="option.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="活动标签" prop="eventTag">
              <el-radio-group v-model="form.eventTag">
                <el-radio :label="0">学科活动</el-radio>
                <el-radio :label="1">跨学科活动</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
 <el-col :span="12">
            <el-form-item label="活动标签类型" prop="eventTagType">
              <el-select v-model="form.eventTagType" placeholder="请选择活动标签类型">
                <el-option v-for="(option, index) in eventTagTypeOptions" :key="index" :label="option.label"
                  :value="option.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>

        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="活动类别" prop="eventCategory">
              <el-radio-group v-model="form.eventCategory">
                <el-radio :label="0">打卡类</el-radio>
                <el-radio :label="1">记录类</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.eventCategory == 0">
            <el-form-item label="打卡类周期" prop="eventCycle">
              <el-radio-group v-model="form.eventCycle">
                <el-radio :label="0">天</el-radio>
                <el-radio :label="1">周</el-radio>
                <el-radio :label="2">月</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="是否上传记录" prop="records">
              <el-radio-group v-model="form.records">
                <el-radio :label="0">否</el-radio>
                <el-radio :label="1">是</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否可见" prop="visible">
              <el-radio-group v-model="form.visible">
                <el-radio :label="0">否</el-radio>
                <el-radio :label="1">是</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>

    <!-- 奖章选择卡片 -->
    <el-card class="form-card">
      <template #header>
        <div class="card-header">活动奖章选择</div>
      </template>
      <div class="medal-container">
        <div v-for="(item, index) in medal" :key="index" class="medal-item"
          :class="{ 'selected': form.medalId === item.eventMedalId }" @click="selectMedal(item.eventMedalId)">
          <img :src="item.firstImage" alt="奖章图片" style="width: 50px; height: 50px; object-fit: cover;">
        </div>
      </div>
    </el-card>

    <!-- 提交按钮 -->
    <el-form :model="form" :rules="rules" label-width="120px">
      <el-form-item>
        <el-button type="primary" @click="onSubmit">立即创建</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import ImageUpload from "./ImageUpload.vue"
  import FileUpload from "./FileUpload.vue"

  import {
    listMedal
  } from "@/api/system/medal";
  import {
    systemevents
  } from "@/api/system/management";
  import {
    deptTreeSelect
  } from "@/api/system/user";

  export default {
    components: {
      ImageUpload,
      FileUpload
    },
    props: [
      'detail'
    ],
    data() {
      return {
        form: {
          eventName: '',
          eventIntro: '',
          precautions: '',
          mark: '',
          startTime: '',
          endTime: '',
          rank: 0,
          eventType: '',
          eventTagType:'',
          eventTag: 0,
          eventCategory: 0,
          eventCycle: 0,
          records: 0,
          visible: 0,
          medalId: '',
          eventPic: [],
          attachments: [],
          gradeIds: ''
        },
        eventTagTypeOptions:[{
          label: '品德发展',
          value: '0'
        },{
          label: '学业发展',
          value: '1'
        },{
          label: '身心发展',
          value: '2'
        },{
          label: '艺术素养',
          value: '3'
        },{
          label: '劳动与社会实践',
          value: '4'
        }],
        eventTypeOptions: [{
            label: '爱党爱国',
            value: '0'
          },
          {
            label: '队团活动',
            value: '1'
          },
          {
            label: '爱护公物',
            value: '2'
          },
          {
            label: '责任担当',
            value: '3'
          },
          {
            label: '文明朴素',
            value: '4'
          },
          {
            label: '尊老爱幼',
            value: '5'
          },
          {
            label: '自立勤快',
            value: '6'
          },
          {
            label: '学习方法',
            value: '7'
          },
          {
            label: '发明创造',
            value: '8'
          },
          {
            label: '问题解决',
            value: '9'
          },
          {
            label: '阅读习惯',
            value: '10'
          },
          {
            label: '动手动脑',
            value: '11'
          },
          {
            label: '卫生健康与安全',
            value: '12'
          },
          {
            label: '意志坚强',
            value: '13'
          },
          {
            label: '积极阳光',
            value: '14'
          },
          {
            label: '人际关系',
            value: '15'
          },
          {
            label: '艺术实践',
            value: '16'
          },
          {
            label: '劳动观念',
            value: '17'
          },
          {
            label: '家庭劳动',
            value: '18'
          },
          {
            label: '环保意识',
            value: '19'
          }
        ],
        fileList: [],
        medal: [],
        baseUrl: process.env.VUE_APP_BASE_API,
        rules: {
          eventName: [{
            required: true,
            message: '活动标题为必填项',
            trigger: 'blur'
          }],
          mark: [{
            required: true,
            message: '分数为必填项',
            trigger: 'blur'
          }],
          eventIntro: [{
            required: true,
            message: '活动简介为必填项',
            trigger: 'blur'
          }],
          precautions: [{
            required: true,
            message: '注意事项为必填项',
            trigger: 'blur'
          }],
          eventPic: [{
            required: true,
            message: '活动图片为必填项',
            trigger: 'change'
          }],
          attachments: [{
            required: true,
            message: '活动附件为必填项',
            trigger: 'change'
          }],
          startTime: [{
            required: true,
            message: '开始时间为必填项',
            trigger: 'change'
          }],
          endTime: [{
            required: true,
            message: '结束时间为必填项',
            trigger: 'change'
          }],
          gradeIds: [{
            required: true,
            message: '请选择班级',
            trigger: 'change'
          }],
          rank: [{
            required: true,
            message: '级别类型为必填项',
            trigger: 'change'
          }],
          eventType: [{
            required: true,
            message: '活动类型为必填项',
            trigger: 'change'
          }],
          eventTag: [{
            required: true,
            message: '活动标签为必填项',
            trigger: 'change'
          }],
          eventCategory: [{
            required: true,
            message: '活动类别为必填项',
            trigger: 'change'
          }],
          eventCycle: [{
            required: true,
            message: '打卡类周期为必填项',
            trigger: 'change'
          }],
          records: [{
            required: true,
            message: '是否上传记录为必填项',
            trigger: 'change'
          }],
          visible: [{
            required: true,
            message: '是否可见为必填项',
            trigger: 'change'
          }],
          medalId: [{
            required: true,
            message: '活动奖章为必填项',
            trigger: 'change'
          }],
          eventTagType:[{
            required: true,
            message: '活动标签类型为必填项',
            trigger: 'change'
          }]
        },
        enabledDeptOptions: [],
        props: {
          multiple: true,
          label: 'label',
          value: 'id',
          disabled: 'disabled'
        },
      }
    },
    mounted() {
      this.getList()
      this.getDeptTree()
      if (this.detail) {
        this.fileList.push(this.detail.image)
        this.form.eventPic = this.fileList
        this.form.eventName = this.detail.title
        this.form.eventIntro = this.detail.intro
        this.form.precautions = this.detail.notice
      }
    },
    methods: {
      setDefaultTime(field) {
                  const currentDate = new Date();
                  const year = currentDate.getFullYear();
                  const month = String(currentDate.getMonth() + 1).padStart(2, '0');
                  const day = String(currentDate.getDate()).padStart(2, '0');
                  const time = '23:59:59';
                  const defaultDateTime = `${year}-${month}-${day} ${time}`;
                  this.form[field] = defaultDateTime;
              },
      getDeptTree() {
        deptTreeSelect().then(response => {
          this.enabledDeptOptions = response.data
          // console.log(response,'responseresponse')
          // this.enabledDeptOptions = this.filterDisabledDept(JSON.parse(JSON.stringify(response.data)));

        });
      },
      // 过滤禁用的班级
      // filterDisabledDept(deptList) {
      //   return deptList.filter(dept => {
      //     if (dept.disabled) {
      //       return false;
      //     }
      //     if (dept.children && dept.children.length) {
      //       dept.children = this.filterDisabledDept(dept.children);
      //     }
      //     return true;
      //   });
      // },
      getList() {
        let obj = {
          pageNum: 1,
          pageSize: 10
        }
        listMedal(obj).then(response => {
          this.medal = response.rows
          for (let i = 0; i < this.medal.length; i++) {
            this.medal[i].firstImage = this.baseUrl + this.medal[i].url.split(',')[0];
          }
          // console.log(this.medal)
        });
      },
      receiveDataFrom(data) {
        this.form.attachments = []
        this.form.attachments.push(data)
      },
      receiveDataFromChild(data) {
        // console.log(data.split(','))
        // return

        // this.form.eventPic = []
        this.form.eventPic = data.split(',')
      },
      onSubmit() {
        // console.log(this.form)
        this.$refs.form.validate((valid) => {
          if (valid) {
            this.form.attachments = this.form.attachments.join(',')
            this.form.eventPic = this.form.eventPic.join(',')
            systemevents(this.form).then(response => {
              this.$modal.msgSuccess("添加成功");
              this.$emit("input", false);
            });
          } else {
            // console.log('表单验证失败');
            return false;
          }
        });
      },
      selectMedal(id) {
        this.form.medalId = id;
      }
    }
  }
</script>

<style scoped>
  .form-card {
    margin-bottom: 20px;
  }

  .card-header {
    font-size: 16px;
    font-weight: bold;
  }

  .medal-container {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
  }

  .medal-item {
    padding: 5px;
    border: 1px solid #ccc;
    border-radius: 4px;
    cursor: pointer;
  }

  .medal-item.selected {
    border-color: #409eff;
    background-color: #ecf5ff;
  }
</style>
