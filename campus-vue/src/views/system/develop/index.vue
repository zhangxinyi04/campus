<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="机构级别" prop="level">
        <el-select v-model="queryParams.institutionRank" placeholder="请选择机构级别">
          <el-option v-for="item in institutionRankoptions" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="获奖等级" prop="level">
        <el-select v-model="queryParams.awardGrade" placeholder="请选择获奖等级">
          <el-option v-for="item in awardGradeoptions" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="指标" prop="level">
        <el-select v-model="queryParams.developIndex" placeholder="请选择指标">
          <el-option v-for="item in developIndexoptions" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="level">
        <el-select v-model="queryParams.status" placeholder="请选择状态">
          <el-option v-for="item in statusoptions" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="学期" prop="level">
        <el-select v-model="queryParams.semesterId" placeholder="请选择">
          <el-option v-for="item in options" :key="item.semesterId" :label="item.name" :value="item.semesterId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!-- <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['system:develop:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['system:develop:edit']">修改</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['system:develop:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['system:develop:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>


    <div style="width: 100%;margin-top: 10px;">
      <div style="width: 13%;float: left;">
        <el-tree :data="deptOptions" :props="defaultProps" :expand-on-click-node="false"
          :filter-node-method="filterNode" ref="tree" node-key="id" default-expand-all highlight-current
          @node-click="handleNodeClick" />
      </div>

 <div style="float: right;width: 87%">
    <el-table v-loading="loading" :data="developList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="developId" />
      <el-table-column label="学生姓名" align="center" prop="studentName" />
      <!-- 机构级别列 -->
      <el-table-column label="机构级别" align="center" prop="institutionRank">
        <template slot-scope="scope">
          <el-tag type="info" border round size="small">{{ getInstitutionRankText(scope.row.institutionRank) }}</el-tag>
        </template>
      </el-table-column>

      <!-- 获奖等级列 -->
      <el-table-column label="获奖等级" align="center" prop="awardGrade">
        <template slot-scope="scope">
          <el-tag type="success" border round size="small">{{ getAwardGradeText(scope.row.awardGrade) }}</el-tag>
        </template>
      </el-table-column>

      <!-- 指标列 -->
      <el-table-column label="指标" align="center" prop="developIndex">
        <template slot-scope="scope">
          <el-tag type="warning" border round size="small">{{ getIndexText(scope.row.developIndex) }}</el-tag>
        </template>
      </el-table-column>

      <!-- <el-table-column label="内容" align="center" prop="content" /> -->
      <el-table-column label="上传视频" align="center" prop="url">
        <template slot-scope="scope">
          <div class="video-container">
            <video :src="baseUrl + scope.row.url" type="video/mp4" controls @loadedmetadata="onVideoLoaded"
              ref="videos">
              你的浏览器不支持视频播放。
            </video>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag :type='getStatusClass(scope.row.status)'>{{ getStatusText(scope.row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-check" @click="handleExamine(scope.row,1)"
            v-hasPermi="['system:develop:edit']" v-if="scope.row.status == 0">通过</el-button>
          <el-button size="mini" type="text" icon="el-icon-close" @click="handleExamine(scope.row,2)"
            v-hasPermi="['system:develop:edit']" v-if="scope.row.status == 0">拒绝</el-button>
          <el-button size="mini" type="text" icon="el-icon-info" @click="handleUpdate(scope.row)">详情</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['system:develop:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

      </div>
          </div>

    <!-- 详情 -->
    <el-dialog :title="title" :visible.sync="open" width="40%" append-to-body class="detail-dialog">
      <div class="detail-container">
        <!-- 头部状态卡片 -->
        <div class="status-card" :class="'status-' + form.status">
          <div class="status-left">
            <h3 class="student-name">{{ form.studentName }}</h3>
            <div class="meta-info">
              <span class="institution">{{ getInstitutionRankText(form.institutionRank) }}</span>
              <el-divider direction="vertical" />
              <span class="award-level">{{ getAwardGradeText(form.awardGrade) }}</span>
              <el-divider direction="vertical" />
              <span class="award-level">{{ getIndexText(form.developIndex) }}</span>
            </div>
          </div>
          <div class="status-tag">
            <!-- <el-tag :type="statusTypeMap[form.status]">{{ form.statusLabel }}</el-tag> -->
            <el-tag :type='getStatusClass(form.status)'>{{ getStatusText(form.status) }}</el-tag>
          </div>
        </div>

        <!-- 内容主体 -->
        <div class="detail-body">
          <!-- 指标展示 -->
          <!--  <div class="indicator-section">
            <div class="section-title">
              <i class="el-icon-data-analysis"></i>
              <span>评审指标</span>
            </div>
            <div class="indicator-grid">
              <div v-for="(item, index) in form.indicators" :key="index" class="indicator-item">
                <div class="indicator-label">{{ item.label }}</div>
                <div class="indicator-value">{{ item.value }}</div>
              </div>
            </div>
          </div> -->

          <!-- 内容描述 -->
          <div class="content-section">
            <div class="section-title">
              <i class="el-icon-document"></i>
              <span>详细内容</span>
            </div>
            <div class="content-scrollable">
              {{ form.content }}
            </div>
          </div>

          <!-- 视频预览 -->
          <div class="video-section">
            <div class="section-title">
              <i class="el-icon-video-camera"></i>
              <span>作品视频</span>
            </div>
            <div class="video-preview">
              <div class="video-thumbnail">
                <video style="width: 100%;height: 100%;" :src="baseUrl + form.url" type="video/mp4" controls
                  @loadedmetadata="onVideoLoaded" ref="videos">
                  你的浏览器不支持视频播放。
                </video>
                <!-- <div class="play-button">
                  <i class="el-icon-video-play"></i>
                </div> -->
              </div>
              <!-- <div class="video-name">111</div> -->
            </div>
          </div>
        </div>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import {
    listDevelop,
    getDevelop,
    delDevelop,
    addDevelop,
    updateDevelop
  } from "@/api/system/develop";
  import {
    deptTreeSelect
  } from "@/api/system/user";
  import {
    eventssemesterList
  } from "@/api/system/score";

  export default {
    name: "Develop",
    data() {
      return {
        deptOptions: [],
        defaultProps: {
          children: "children",
          label: "label"
        },
        options: [],
        institutionRankoptions: [{
            id: '0',
            name: '国际级'
          },
          {
            id: '1',
            name: '国家级'
          },
          {
            id: '2',
            name: '省级'
          },
          {
            id: '3',
            name: '市级'
          },
          {
            id: '4',
            name: '区县级'
          },
          {
            id: '5',
            name: '校级'
          },
          {
            id: '6',
            name: '班级'
          }
        ],
        awardGradeoptions: [{
            id: '0',
            name: '一等奖'
          },
          {
            id: '1',
            name: '二等奖'
          },
          {
            id: '2',
            name: '三等奖'
          }
        ],
        developIndexoptions: [{
            id: '0',
            name: '品德发展'
          },
          {
            id: '1',
            name: '学业发展'
          },
          {
            id: '2',
            name: '身心发展'
          },
          {
            id: '3',
            name: '艺术素养'
          },
          {
            id: '4',
            name: '劳动与社会实践'
          }
        ],
        statusoptions: [{
            id: '0',
            name: '待审核'
          },
          {
            id: '1',
            name: '通过'
          },
          {
            id: '2',
            name: '拒绝'
          }
        ],
        baseUrl: process.env.VUE_APP_BASE_API,
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 学生发展管理表格数据
        developList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          institutionRank: null,
          awardGrade: null,
          status: null,
          developIndex: null,
          deptId:null,
          semesterId:null
        },
        videoLoaded: {},
        // 表单参数
        form: {},
        // 表单校验

      };
    },
    created() {
      this.eventssemesterList();
      this.getDeptTree()
      // this.getList();
    },
    methods: {
      filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },
      handleNodeClick(row) {
        this.queryParams.deptId = row.id
        this.getList();
        // console.log(row,'22')
      },
      getDeptTree() {
        deptTreeSelect().then(response => {
          // this.enabledDeptOptions = this.filterDisabledDept(JSON.parse(JSON.stringify(response.data)));
          this.deptOptions = response.data;
          let obj = {
            id: '',
            label: '全部',
          }
          this.deptOptions.unshift(obj)
          // console.log(this.deptOptions)

        });
      },
      eventssemesterList() {
        eventssemesterList({
          pageNum: 1,
          pageSize: 10,
        }).then(response => {
          this.options = response.data
          for(var i=0;i<this.options.length;i++){
            if(this.options[i].status == '1'){
              this.queryParams.semesterId = this.options[i].semesterId
            }
          }
           this.getList();
          // console.log(this.queryParams.semesterId, 'response')
        });
      },
      async handleExamine(row, status) {
        // 根据状态生成不同的确认信息
        const message = status === 1 ? '确认通过该审核吗？' : '确认拒绝该审核吗？';

        // 显示二次确认弹窗
        try {
          await this.$msgbox({
            title: '操作确认',
            message: message,
            buttons: [{
                label: '取消',
                type: 'default'
              },
              {
                label: '确认',
                type: status === 1 ? 'success' : 'danger'
              }
            ]
          });

          // 确认后执行操作
          row.status = status;
          await updateDevelop(row);
          this.$modal.msgSuccess("审核操作成功");
          this.getList();
        } catch (error) {
          // 用户取消操作时不做处理
        }
      },
      // handleExamine(row,status){
      //   row.status = status
      //   updateDevelop(row).then(response => {
      //     this.$modal.msgSuccess("审核操作成功");
      //     this.getList();
      //   });
      // },
      getIndexText(index) {
        const indexMap = {
          0: '品德发展',
          1: '学业发展',
          2: '身心发展',
          3: '艺术素养',
          4: '劳动与社会实践'
        };
        return indexMap[index] || '未知指标';
      },
      getStatusText(status) {
        const statusMap = {
          0: '待审核',
          1: '通过',
          2: '拒绝'
        };
        return statusMap[status] || '未知状态';
      },
      getStatusClass(status) {
        switch (status * 1) {
          case 0:
            return 'warning';
          case 1:
            return '';
          case 2:
            return 'danger';
          default:
            return '';
        }
      },
      onVideoLoaded(event) {
        const index = this.$refs.videos.indexOf(event.target);
        this.videoLoaded[index] = true;
      },
      getInstitutionRankText(rank) {
        const rankMap = {
          0: '国际级',
          1: '国家级',
          2: '省级',
          3: '市级',
          4: '区县级',
          5: '校级',
          6: '班级'
        };
        return rankMap[rank] || '未知';
      },
      getAwardGradeText(grade) {
        const gradeMap = {
          0: '一等奖',
          1: '二等奖',
          2: '三等奖'
        };
        return gradeMap[grade] || '未知';
      },
      /** 查询学生发展管理列表 */
      getList() {
        this.loading = true;
        listDevelop(this.queryParams).then(response => {
          this.developList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          developId: null,
          studentId: null,
          index: null,
          institutionRank: null,
          awardGrade: null,
          content: null,
          url: null,
          status: null,
          delFlag: null
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.queryParams = {
            institutionRank: null,
            awardGrade: null,
            status: null,
            developIndex: null
          },
          // this.resetForm("queryForm");
          this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.developId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加学生发展管理";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        // this.reset();
        this.open = true;
        this.title = "审核详情";
        this.form = row
        // const developId = row.developId || this.ids
        // getDevelop(developId).then(response => {
        //   this.form = response.data;
        //   this.open = true;
        //   this.title = "审核详情";
        // });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.developId != null) {
              updateDevelop(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addDevelop(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const developIds = row.developId || this.ids;
        this.$modal.confirm('是否确认删除学生发展管理编号为"' + developIds + '"的数据项？').then(function() {
          return delDevelop(developIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('system/develop/export', {
          ...this.queryParams
        }, `develop_${new Date().getTime()}.xlsx`)
      }
    }
  };
</script>

<style lang="scss" scoped>
  .detail-dialog {
    ::v-deep .el-dialog__body {
      padding: 15px 25px;
    }
  }

  .detail-container {
    .status-card {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 18px;
      border-radius: 12px;
      background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
      margin-bottom: 20px;

      &.status-success {
        background: linear-gradient(135deg, #e6f4ff 0%, #bae0ff 100%);
      }

      &.status-waiting {
        background: linear-gradient(135deg, #fff7e6 0%, #ffe7ba 100%);
      }

      &.status-reject {
        background: linear-gradient(135deg, #fff1f0 0%, #ffccc7 100%);
      }

      .student-name {
        margin: 0;
        font-size: 18px;
        color: #1a1a1a;
      }

      .meta-info {
        margin-top: 8px;
        font-size: 13px;
        color: #666;

        .el-divider {
          background-color: #999;
        }
      }
    }

    .detail-body {
      display: grid;
      gap: 20px;

      .section-title {
        display: flex;
        align-items: center;
        margin-bottom: 12px;
        color: #2c3e50;
        font-weight: 500;

        i {
          margin-right: 8px;
          font-size: 16px;
        }
      }

      .indicator-section {
        .indicator-grid {
          display: grid;
          grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
          gap: 12px;
          padding: 15px;
          background: #f8f9fa;
          border-radius: 8px;

          .indicator-item {
            text-align: center;
            padding: 10px;
            background: white;
            border-radius: 6px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);

            &-label {
              font-size: 12px;
              color: #666;
              margin-bottom: 4px;
            }

            &-value {
              font-weight: 600;
              color: #2c3e50;
            }
          }
        }
      }

      .content-section {
        .content-scrollable {
          max-height: 150px;
          padding: 15px;
          line-height: 1.6;
          background: #f8f9fa;
          border-radius: 8px;
          overflow-y: auto;
          white-space: pre-wrap;
        }
      }

      .video-section {
        .video-preview {
          cursor: pointer;
          transition: transform 0.2s;

          &:hover {
            transform: translateY(-2px);
          }

          .video-thumbnail {
            position: relative;
            height: 160px;
            border-radius: 8px;
            background-size: cover;
            background-position: center;
            background-color: #000;

            .play-button {
              position: absolute;
              top: 50%;
              left: 50%;
              transform: translate(-50%, -50%);
              font-size: 40px;
              color: rgba(255, 255, 255, 0.9);
              text-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
            }
          }

          .video-name {
            margin-top: 8px;
            font-size: 13px;
            color: #666;
            text-align: center;
          }
        }
      }
    }
  }

  .status-pending {
    color: #FFA500;
  }

  .status-approved {
    color: #008000;
  }

  .status-rejected {
    color: #FF0000;
  }

  .video-container {
    position: relative;
    width: 100%;
    height: 0;
    padding-bottom: 56.25%;
    /* 16:9 宽高比 */
    overflow: hidden;

  }


  .video-container video {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    object-fit: cover;
  }

  .loading-spinner {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    color: #fff;
    font-size: 24px;
  }

  .loading {
    /* opacity: 0.5; */
  }
</style>
