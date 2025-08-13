<template>
  <div class="app-container">
    <el-form :model="queryParamsOne" ref="queryForm" size="small" :inline="true" label-width="68px">
      <el-form-item label="活动名称" prop="eventName">
        <el-input v-model="queryParamsOne.eventName" placeholder="请输入活动名称"></el-input>
      </el-form-item>
      <el-form-item label="活动级别" prop="rank">
        <el-select v-model="queryParamsOne.rank" placeholder="请选择活动级别">
          <el-option v-for="item in rankOptions" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="活动状态" prop="status">
        <el-select v-model="queryParamsOne.status" placeholder="请选择活动状态">
          <el-option v-for="item in statusOptions" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>


    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">添加活动</el-button>
      </el-col>
    </el-row>

    <el-dialog title="选择模版" :visible.sync="open" width="90%" append-to-body>
      <div class="template-dialog">
        <!-- 筛选条件 -->
        <div class="filter-section">
          <div class="filter-group">
            <div class="filter-label">模版类型：</div>
            <div class="filter-options">
              <span v-for="item in templateTypes" :key="item.value"
                :class="['filter-item', { 'active': templateType === item.value }]" @click="templateType = item.value">
                {{ item.label }}
              </span>
            </div>
          </div>
          <div class="filter-group">
            <div class="filter-label">活动类型：</div>
            <div class="filter-options">
              <span v-for="item in activityTypes" :key="item.value"
                :class="['filter-item', { 'active': activityType === item.value }]" @click="activityType = item.value">
                {{ item.label }}
              </span>
            </div>
          </div>
        </div>
        <!-- 模版列表 -->
        <el-row :gutter="20" class="template-list">
          <el-col v-for="item in templateList" :key="item.id" :span="4" class="template-item"
            @click.native="handleTemplateClick(item)">
            <div class="template-card">
              <el-image :src="item.img" fit="cover" class="template-image"></el-image>
              <!-- <el-image :src="require('@/assets/image/'+item.image)" fit="cover" class="template-image"></el-image> -->
              <h3 class="template-title">{{ item.title }}</h3>
              <div class="template-tags">
                <el-tag>{{item.tags}}</el-tag>
                <!-- <el-tag v-for="(tag, index) in item.tags" :key="index" size="mini" class="tag-item">
                  {{ tag }}
                </el-tag> -->
              </div>
            </div>
          </el-col>
        </el-row>
        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
          @pagination="getList" />
      </div>
    </el-dialog>

    <el-table v-loading="loading" :data="managementList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="活动封面图" align="center" prop="eventPic">
        <template slot-scope="scope">
          <el-image :src="baseUrl + scope.row.eventPic[0]" :preview-src-list="getPreviewList(scope.row.eventPic[0])"
            style="width: 60px;margin: 5px;" :lazy="true"></el-image>
        </template>
      </el-table-column>
      <el-table-column label="活动名称" align="center" prop="eventName" />
      <el-table-column label="活动类型" align="center" prop="eventType">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.eventType == '0'">爱党爱国</el-tag>
          <el-tag v-if="scope.row.eventType == '1'">队团活动</el-tag>
          <el-tag v-if="scope.row.eventType == '2'">爱护公物</el-tag>
          <el-tag v-if="scope.row.eventType == '3'">责任担当</el-tag>
          <el-tag v-if="scope.row.eventType == '4'">文明朴素</el-tag>
          <el-tag v-if="scope.row.eventType == '5'">尊老爱幼</el-tag>
          <el-tag v-if="scope.row.eventType == '6'">自立勤快</el-tag>
          <el-tag v-if="scope.row.eventType == '7'">学习方法</el-tag>
          <el-tag v-if="scope.row.eventType == '8'">发明创造</el-tag>
          <el-tag v-if="scope.row.eventType == '9'">问题解决</el-tag>
          <el-tag v-if="scope.row.eventType == '10'">阅读习惯</el-tag>
          <el-tag v-if="scope.row.eventType == '11'">动手动脑</el-tag>
          <el-tag v-if="scope.row.eventType == '12'">卫生健康与安全</el-tag>
          <el-tag v-if="scope.row.eventType == '13'">意志坚强</el-tag>
          <el-tag v-if="scope.row.eventType == '14'">积极阳光</el-tag>
          <el-tag v-if="scope.row.eventType == '15'">人际关系</el-tag>
          <el-tag v-if="scope.row.eventType == '16'">艺术实践</el-tag>
          <el-tag v-if="scope.row.eventType == '17'">劳动观念</el-tag>
          <el-tag v-if="scope.row.eventType == '18'">家庭劳动</el-tag>
          <el-tag v-if="scope.row.eventType == '19'">环保意识</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="活动级别" align="center" prop="mark">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.rank == '0'">校级</el-tag>
          <el-tag v-if="scope.row.rank == '1'">班级</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="活动时间" align="center" prop="startTime" show-overflow-tooltip>
        <template slot-scope="scope">
          {{scope.row.startTime}} - {{scope.row.endTime}}
        </template>
      </el-table-column>
      <el-table-column label="活动状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status == 0" type="info">未开始</el-tag>
          <el-tag v-if="scope.row.status == 1">进行中</el-tag>
          <el-tag v-if="scope.row.status == 2" type="danger">已结束</el-tag>
        </template>
      </el-table-column>
      <!--  <el-table-column label="学生参与度" align="center" prop="eventMedalName" /> -->
      <el-table-column label="创建时间" align="center" prop="createTime" show-overflow-tooltip/>
      <!-- <el-table-column label="图片" align="center" prop="url">
        <template slot-scope="scope">
          <el-image
                   v-for="(item, index) in scope.row.urlimg"
                   :key="index"
                   :src="baseUrl + item"
                   :preview-src-list="getPreviewList(scope.row.urlimg)"
                   style="width: 60px;margin: 5px;"
                   :lazy="true"
                 ></el-image>
        </template>
      </el-table-column> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width='200'>
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button size="mini" type="text" icon="el-icon-info" @click="handledetails(scope.row)">活动详情</el-button>
          <!-- <el-button size="mini" type="text" icon="el-icon-upload2">参与结果上传</el-button>  -->
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>


    <el-drawer size="50%" title="添加活动" :visible.sync="openCom" :before-close="handleClose">
      <subassembly :detail='detail' @input='subassemblyclick' v-if='openCom'></subassembly>
    </el-drawer>

    <el-dialog title="修改活动时间" :visible.sync="openEndTime" width="500px" append-to-body>
      <el-form ref="formEndTime" :model="formEndTime" :rules="rulesEndTime" label-width="120px">
        <el-form-item label="活动结束时间" prop="endTime">
          <el-date-picker v-model="formEndTime.endTime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"
            type="datetime" placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="openEndTime = false">取 消</el-button>
        <el-button type="primary" @click="sureEndTime">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="活动详情" :visible.sync="opendetails" width="55%" append-to-body>
      <detailspage :openid='openid' v-if="opendetails"></detailspage>
    </el-dialog>

  </div>
</template>

<script>
  // import templateListJson from './Jsondata/templateList.json';
  // import templateDetailsJson from './Jsondata/templateDetails.json';
  import subassembly from './component/subassembly.vue';
  import detailspage from './component/detailspage.vue';


  import {
    eventslistTemplate,
    eventslist,
    updateevents,
    delevents
  } from "@/api/system/management"


  export default {
    components: {
      subassembly,
      detailspage
    },
    data() {
      return {
        loadinggetList:false,
        rankOptions: [{
            id: '0',
            name: '校级'
          },
          {
            id: '1',
            name: '班级'
          }
        ],
        statusOptions: [{
            id: '0',
            name: '未开始'
          },
          {
            id: '1',
            name: '进行中'
          },
          {
            id: '2',
            name: '已结束'
          }
        ],
        // 分页相关
        currentPage: 1,
        pageSize: 12,
        templateTotal: 0,
        open: false,
        templateType: 'platform',
        activityType: 'all',
        templateTypes: [{
            value: 'platform',
            label: '平台模版'
          },
          {
            value: 'region',
            label: '区域模版'
          },
          {
            value: 'school',
            label: '学校模版'
          }
        ],
        activityTypes: [{
            value: 'all',
            label: '全部'
          },
          {
            value: 'hot',
            label: '热门'
          },
          {
            value: 'patriotic',
            label: '爱党爱国'
          },
          {
            value: 'team',
            label: '团队活动'
          },
          {
            value: 'culture',
            label: '传统文化'
          }
        ],
        templateList: [],
        templateDetails: '',
        detail: '',
        notice: '1、有规则意识，服从组织安排，如：队员严禁超越先导队员或落后于收尾领队。2、 有安全意识， 禁止一切危险行为。3、 有卫生意识， 保持良好卫生习惯， 注意预防疾病。4、 有环保意识， 不乱丢垃圾， 爱护环境， 提倡环保。5、 有尊重意识， 人人在团队中都是平等的。6、 有团队意识， 保持良好的团队协作精神。7、 有互助意识， 有任何困难或疑问尽快和领队沟通。8、 自我服务意识， 整个活动过程必须明确意识， 照顾好自己和保管好随身物品。',
        openCom: false,
        queryParams: {
          pageNum: 1,
          pageSize: 12,
        },
        queryParamsOne: {
          pageNum: 1,
          pageSize: 12,
          rank: null,
          status: null,
          eventName: null
        },
        total: '',
        baseUrl: process.env.VUE_APP_BASE_API,
        loading: false,
        managementList: [],
        openEndTime: false,
        formEndTime: {
          endTime: ''
        },
        rulesEndTime: {
          endTime: [{
            required: true,
            message: '结束时间为必填项',
            trigger: 'change'
          }]
        },
        startTime: '',
        opendetails: false,
        openid: ''
      };
    },
    created() {
      // 将引入的 JSON 数据赋值给组件的 data 属性
      // this.templateList = templateListJson.data.list;
      // this.templateDetails = templateDetailsJson.data;
      // this.templateTotal = templateListJson.data.total;
    },
    mounted() {
      this.getList()
      this.eventslist()
    },
    computed: {
      // 当前页显示的数据
      // showTemplates() {
      //   const start = (this.currentPage - 1) * this.pageSize
      //   const end = start + this.pageSize
      //   return this.templateList.slice(start, end)
      // }
    },
    methods: {
      resetQuery() {
        this.queryParamsOne = {
            rank: null,
            status: null,
            eventName: null
          },
          // this.resetForm("queryForm");
          this.handleQuery();
      },
      handleQuery() {
        this.queryParams.pageNum = 1;
        // console.log('111')
        this.eventslist()
      },
      handledetails(row) {
        this.openid = row.eventId
        // console.log(row ,'this.openid ')
        this.opendetails = true
      },
      getPreviewList(urls) {
        // return urls.map(url => this.baseUrl + url);
      },
      handleDelete(row) {
        const eventMedalIds = row.eventId
        this.$modal.confirm('是否确认删除活动ID为"' + eventMedalIds + '"的数据项？').then(function() {
          return delevents(eventMedalIds);
        }).then(() => {
          this.eventslist();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      sureEndTime() {
        this.$refs.formEndTime.validate((valid) => {
          if (valid) {
            updateevents(this.formEndTime).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.openEndTime = false
            });
          } else {
            console.log('校验失败');
            return false;
          }
        });
      },
      handleUpdate(row) {
        this.startTime = row.startTime
        this.formEndTime.eventId = row.eventId
        this.formEndTime.endTime = row.endTime
        this.openEndTime = true
      },
      handleSelectionChange() {},
      eventslist() {
        this.loading = true
        eventslist(this.queryParamsOne).then(response => {
          this.managementList = response.rows
          for (var i = 0; i < this.managementList.length; i++) {
            this.managementList[i].eventPic = this.managementList[i].eventPic.split(',')
            // console.log(this.managementList[i])
          }
          // var  eventPic = "/profile/template/1.png,/profile/template/1.png"
          // console.log(this.managementList ,'11')
          this.loading = false
        });
      },
      subassemblyclick(data) {
        this.openCom = false
        this.eventslist()
      },
      getList() {
        this.loadinggetList = true
        eventslistTemplate(this.queryParams).then(response => {
             this.loadinggetList = false
          this.templateList = response.rows;
          this.total = response.total;
          for (var i = 0; i < this.templateList.length; i++) {
            this.templateList[i].img = this.baseUrl + this.templateList[i].image
          }
        });
      },
      handleClose() {
        this.openCom = false
      },
      handlePageChange(val) {
        this.currentPage = val
      },
      // 点击模板
      handleTemplateClick(data) {
        this.detail = data
        this.detail.notice = this.notice
        this.open = false
        this.openCom = true

      },
      handleAdd() {
        this.open = true
      }
    }
  };
</script>

<style scoped>
  .template-dialog {
    padding: 10px;
  }

  .filter-section {
    margin-bottom: 20px;
  }

  .filter-group {
    display: flex;
    align-items: center;
    margin-bottom: 15px;
  }

  .filter-label {
    width: 80px;
    font-weight: 600;
    color: #666;
  }

  .filter-options {
    flex: 1;
    display: flex;
    gap: 10px;
  }

  .filter-item {
    padding: 8px 15px;
    border-radius: 4px;
    cursor: pointer;
    border: 1px solid #ebeef5;
    transition: all 0.3s;
  }

  .filter-item.active {
    background-color: #409eff;
    color: white;
    border-color: #409eff;
  }

  /*  .template-card {
    border: 1px solid #ebeef5;
    border-radius: 8px;
    padding: 10px;
    margin-bottom: 20px;
    transition: all 0.3s;
  } */

  .template-card:hover {
    transform: translateY(-3px);
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, .1);
  }

  /* .template-image {
    width: 100%;
    height: 180px;
    border-radius: 4px;
  } */

  /* .template-title {
    text-align: center;
    margin: 10px 0;
    font-size: 14px;
  } */

  /*  .template-tags {
    display: flex;
    flex-wrap: wrap;
    gap: 5px;
    justify-content: center;
  } */

  .tag-item {
    margin: 2px;
  }

  .template-card {
    border: 1px solid #ebeef5;
    border-radius: 8px;
    padding: 10px;
    margin-bottom: 20px;
    transition: all 0.3s;
    display: flex;
    flex-direction: column;
    height: 300px;
    /* 固定卡片高度 */
  }

  .template-image {
    width: 100%;
    height: 180px;
    border-radius: 4px;
    flex-shrink: 0;
    /* 防止图片被压缩 */
  }

  .template-title {
    text-align: center;
    margin: 10px 0;
    font-size: 14px;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 3;
    /* 最多显示3行 */
    overflow: hidden;
    text-overflow: ellipsis;
    height: 60px;
    /* 根据行高计算的最小高度 */
    line-height: 20px;
  }

  .template-tags {
    display: flex;
    flex-wrap: wrap;
    gap: 5px;
    justify-content: center;
    flex-grow: 1;
    /* 允许标签占据剩余空间 */
    align-content: flex-start;
    /* 保持顶部对齐 */
    padding-bottom: 10px;
    /* 增加底部间距 */
  }
</style>
