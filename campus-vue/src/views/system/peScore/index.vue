<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
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
          v-hasPermi="['system:score:add']">新增</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['system:score:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['system:score:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="info" plain icon="el-icon-upload2" size="mini" @click="handleImport">导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['system:score:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <div style="width: 100%;margin-top: 10px;">
      <div style="width: 10%;float: left;">
        <el-tree :data="deptOptions" :props="defaultProps" :expand-on-click-node="false"
          :filter-node-method="filterNode" ref="tree" node-key="id" default-expand-all highlight-current
          @node-click="handleNodeClick" />
      </div>

      <div style="float: right;width: 87%">
        <el-table v-loading="loading" :data="scoreList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="ID" align="center" prop="studentScoreId" />
          <el-table-column label="学生姓名" align="center" prop="studentName" />
          <!--  <el-table-column label="学期表" align="center" prop="semesterId" /> -->
          <el-table-column label="总分" align="center" prop="score" />
          <el-table-column label="等级" align="center" prop="level">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.level == 0">优秀</el-tag>
              <el-tag v-if="scope.row.level == 1" type="success">良好</el-tag>
              <el-tag v-if="scope.row.level == 2" type="info">及格</el-tag>
              <el-tag v-if="scope.row.level == 3" type="danger">不及格</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="测试成绩" align="center" prop="testScore" />
          <el-table-column label="测试等级" align="center" prop="testLevel">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.testLevel == 0">优秀</el-tag>
              <el-tag v-if="scope.row.testLevel == 1" type="success">良好</el-tag>
              <el-tag v-if="scope.row.testLevel == 2" type="info">及格</el-tag>
              <el-tag v-if="scope.row.testLevel == 3" type="danger">不及格</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="附加分数" align="center" prop="appendScore" />
          <el-table-column label="身高/cm" align="center" prop="height" />
          <el-table-column label="体重" align="center" prop="weight" />
          <el-table-column label="肺活量/毫升" align="center" prop="vitalCapacity"  width="120"/>
          <el-table-column label="身高体重指数" align="center" prop="bmi" width="100" />
          <el-table-column label="50米跑/秒" align="center" prop="run" />
          <el-table-column label="体前屈/cm" align="center" prop="sitReach" width="120"/>
          <el-table-column label="跳绳/次" align="center" prop="jump" />
          <el-table-column label="仰卧起坐" align="center" prop="sitUp" />
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="100">
            <template slot-scope="scope">
              <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                v-hasPermi="['system:score:edit']">修改</el-button>
              <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                v-hasPermi="['system:score:remove']">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
          @pagination="getList" />
      </div>
    </div>

    <!-- 添加或修改学生体育成绩管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="总分" prop="score">
          <el-input v-model="form.score" placeholder="请输入总分" />
        </el-form-item>
        <el-form-item label="等级" prop="level">
          <el-select v-model="form.level" placeholder="请选择等级">
            <el-option label="优秀" value="0"></el-option>
            <el-option label="良好" value="1"></el-option>
            <el-option label="及格" value="2"></el-option>
            <el-option label="不及格" value="3"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="测试成绩" prop="testScore">
          <el-input v-model="form.testScore" placeholder="请输入测试成绩" />
        </el-form-item>
        <el-form-item label="测试等级" prop="testLevel">
          <!-- <el-input v-model="form.testLevel" placeholder="请输入测试等级 0优秀 1良好 2及格 3不及格" /> -->
          <el-select v-model="form.testLevel" placeholder="请选择等级">
            <el-option label="优秀" value="0"></el-option>
            <el-option label="良好" value="1"></el-option>
            <el-option label="及格" value="2"></el-option>
            <el-option label="不及格" value="3"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="附加分数" prop="appendScore">
          <el-input v-model="form.appendScore" placeholder="请输入附加分数" />
        </el-form-item>
        <el-form-item label="身高" prop="height">
          <el-input v-model="form.height" placeholder="请输入身高" />
        </el-form-item>
        <el-form-item label="体重" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入体重" />
        </el-form-item>
        <el-form-item label="肺活量" prop="vitalCapacity">
          <el-input v-model="form.vitalCapacity" placeholder="请输入肺活量" />
        </el-form-item>
        <el-form-item label="身高体重指数" prop="bmi">
          <el-input v-model="form.bmi" placeholder="请输入身高体重指数" />
        </el-form-item>
        <el-form-item label="50米跑" prop="run">
          <el-input v-model="form.run" placeholder="请输入50米跑" />
        </el-form-item>
        <el-form-item label="体前屈" prop="sitReach">
          <el-input v-model="form.sitReach" placeholder="请输入体前屈" />
        </el-form-item>
        <el-form-item label="跳绳" prop="jump">
          <el-input v-model="form.jump" placeholder="请输入跳绳" />
        </el-form-item>
        <el-form-item label="仰卧起坐" prop="sitUp">
          <el-input v-model="form.sitUp" placeholder="请输入仰卧起坐" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport" :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的用户数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size: 12px; vertical-align: baseline"
            @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
  import {
    listScore,
    getScore,
    delScore,
    addScore,
    updateScore
  } from "@/api/system/peScore";
  import {
    eventssemesterList
  } from "@/api/system/score";
  import {
    getToken
  } from "@/utils/auth";
  import {
    deptTreeSelect
  } from "@/api/system/user";

  export default {
    name: "Score",
    data() {
      return {
        deptOptions: [],
        defaultProps: {
          children: "children",
          label: "label"
        },
        options: [],
        // 用户导入参数
        upload: {
          // 是否显示弹出层（用户导入）
          open: false,
          // 弹出层标题（用户导入）
          title: "",
          // 是否禁用上传
          isUploading: false,
          // 是否更新已经存在的用户数据
          updateSupport: 0,
          // 设置上传的请求头部
          headers: {
            Authorization: "Bearer " + getToken()
          },
          // 上传的地址
          url: process.env.VUE_APP_BASE_API + "/system/peScore/importData"
        },
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
        // 学生体育成绩管理表格数据
        scoreList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          score: null,
          level: 0,
          testScore: null,
          testLevel: null,
          appendScore: null,
          height: null,
          weight: null,
          vitalCapacity: null,
          bmi: null,
          run: null,
          sitReach: null,
          jump: null,
          sitUp: null,
          deptId: null,
          semesterId: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {}
      };
    },
    created() {
      // this.getList();
      this.eventssemesterList();
      this.getDeptTree()
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
          for (var i = 0; i < this.options.length; i++) {
            if (this.options[i].status == '1') {
              this.queryParams.semesterId = this.options[i].semesterId
            }
          }
          this.getList();
          // console.log(this.options, 'response')
        });
      },
      /** 导入按钮操作 */
      handleImport() {
        this.upload.title = "用户导入";
        this.upload.open = true;
      },
      /** 下载模板操作 */
      importTemplate() {
        this.download('system/peScore/studentPeScoreTemplate', {}, `user_template_${new Date().getTime()}.xlsx`)
      },
      // 文件上传中处理
      handleFileUploadProgress(event, file, fileList) {
        this.upload.isUploading = true;
      },
      // 文件上传成功处理
      handleFileSuccess(response, file, fileList) {
        this.upload.open = false;
        this.upload.isUploading = false;
        this.$refs.upload.clearFiles();
        this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response
          .msg + "</div>", "导入结果", {
            dangerouslyUseHTMLString: true
          });
        this.getList();
      },
      // 提交上传文件
      submitFileForm() {
        this.$refs.upload.submit();
      },
      /** 查询学生体育成绩管理列表 */
      getList() {
        this.loading = true;
        listScore(this.queryParams).then(response => {
          this.scoreList = response.rows;
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
          studentScoreId: null,
          studentId: null,
          semesterId: null,
          score: null,
          level: null,
          testScore: null,
          testLevel: null,
          appendScore: null,
          height: null,
          weight: null,
          vitalCapacity: null,
          bmi: null,
          run: null,
          sitReach: null,
          jump: null,
          sitUp: null,
          deptId: null,
          semesterId: null
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
        this.queryParams.semesterId = null
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.studentScoreId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加学生体育成绩管理";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const studentScoreId = row.studentScoreId || this.ids
        this.form = row;
        this.open = true;
        this.title = "修改学生体育成绩管理";
        // getScore(studentScoreId).then(response => {
        //   this.form = response.data;
        //     console.log(this.form,'this.form')
        //   this.open = true;
        //   this.title = "修改学生体育成绩管理";
        // });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.studentId != null) {
              updateScore(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addScore(this.form).then(response => {
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
        const studentScoreIds = row.studentScoreId || this.ids;
        this.$modal.confirm('是否确认删除学生体育成绩管理编号为"' + studentScoreIds + '"的数据项？').then(function() {
          return delScore(studentScoreIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('system/peScore/export', {
          ...this.queryParams
        }, `peScore_${new Date().getTime()}.xlsx`)
      }
    }
  };
</script>
