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
          <el-table-column label="语文" align="center">
            <template slot-scope="scope">
              <el-tag :type="getScoreTagType(scope.row.chinese)">{{ getScoreLevel(scope.row.chinese) }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="数学" align="center">
            <template slot-scope="scope">
              <el-tag :type="getScoreTagType(scope.row.math)">{{ getScoreLevel(scope.row.math) }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="英语" align="center">
            <template slot-scope="scope">
              <el-tag :type="getScoreTagType(scope.row.english)">{{ getScoreLevel(scope.row.english) }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="美术" align="center">
            <template slot-scope="scope">
              <el-tag :type="getScoreTagType(scope.row.art)">{{ getScoreLevel(scope.row.art) }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="音乐" align="center">
            <template slot-scope="scope">
              <el-tag :type="getScoreTagType(scope.row.music)">{{ getScoreLevel(scope.row.music) }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="体育与健康" align="center">
            <template slot-scope="scope">
              <el-tag :type="getScoreTagType(scope.row.sports)">{{ getScoreLevel(scope.row.sports) }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="综合实践" align="center">
            <template slot-scope="scope">
              <el-tag :type="getScoreTagType(scope.row.practice)">{{ getScoreLevel(scope.row.practice) }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="信息科技" align="center">
            <template slot-scope="scope">
              <el-tag :type="getScoreTagType(scope.row.computer)">{{ getScoreLevel(scope.row.computer) }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="劳动与技术" align="center">
            <template slot-scope="scope">
              <el-tag :type="getScoreTagType(scope.row.labour)">{{ getScoreLevel(scope.row.labour) }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="道德与法治" align="center">
            <template slot-scope="scope">
              <el-tag :type="getScoreTagType(scope.row.morality)">{{ getScoreLevel(scope.row.morality) }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="心理健康" align="center">
            <template slot-scope="scope">
              <el-tag :type="getScoreTagType(scope.row.health)">{{ getScoreLevel(scope.row.health) }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="科学" align="center">
            <template slot-scope="scope">
              <el-tag :type="getScoreTagType(scope.row.science)">{{ getScoreLevel(scope.row.science) }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="书法" align="center">
            <template slot-scope="scope">
              <el-tag :type="getScoreTagType(scope.row.calligraphy)">{{ getScoreLevel(scope.row.calligraphy) }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="舞蹈" align="center">
            <template slot-scope="scope">
              <el-tag :type="getScoreTagType(scope.row.dance)">{{ getScoreLevel(scope.row.dance) }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="阅读" align="center">
            <template slot-scope="scope">
              <el-tag :type="getScoreTagType(scope.row.readbook)">{{ getScoreLevel(scope.row.readbook) }}</el-tag>
            </template>
          </el-table-column>

          <!--
          <el-table-column label="劳动与技术" align="center" prop="labour" width="100" />
          <el-table-column label="道德与法治" align="center" prop="morality" width="100" />
          <el-table-column label="心理健康" align="center" prop="health" />
          <el-table-column label="科学" align="center" prop="science" />
          <el-table-column label="书法" align="center" prop="calligraphy" />
          <el-table-column label="舞蹈" align="center" prop="dance" />
          <el-table-column label="阅读" align="center" prop="readbook" /> -->
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

    <!-- 添加或修改学生成绩管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="语文" prop="chinese">
          <el-input v-model="form.chinese" placeholder="请输入语文" />
        </el-form-item>
        <el-form-item label="数学" prop="math">
          <el-input v-model="form.math" placeholder="请输入数学" />
        </el-form-item>
        <el-form-item label="英语" prop="english">
          <el-input v-model="form.english" placeholder="请输入英语" />
        </el-form-item>
        <el-form-item label="美术" prop="art">
          <el-input v-model="form.art" placeholder="请输入美术" />
        </el-form-item>
        <el-form-item label="音乐" prop="music">
          <el-input v-model="form.music" placeholder="请输入音乐" />
        </el-form-item>
        <el-form-item label="体育与健康" prop="sports">
          <el-input v-model="form.sports" placeholder="请输入体育与健康" />
        </el-form-item>
        <el-form-item label="综合实践" prop="practice">
          <el-input v-model="form.practice" placeholder="请输入综合实践" />
        </el-form-item>
        <el-form-item label="信息科技" prop="computer">
          <el-input v-model="form.computer" placeholder="请输入信息科技" />
        </el-form-item>
        <el-form-item label="劳动与技术" prop="labour">
          <el-input v-model="form.labour" placeholder="请输入劳动与技术" />
        </el-form-item>
        <el-form-item label="道德与法治" prop="morality">
          <el-input v-model="form.morality" placeholder="请输入道德与法治" />
        </el-form-item>
        <el-form-item label="心理健康" prop="health">
          <el-input v-model="form.health" placeholder="请输入心理健康" />
        </el-form-item>
        <el-form-item label="科学" prop="science">
          <el-input v-model="form.science" placeholder="请输入科学" />
        </el-form-item>
        <el-form-item label="书法" prop="calligraphy">
          <el-input v-model="form.calligraphy" placeholder="请输入书法" />
        </el-form-item>
        <el-form-item label="舞蹈" prop="dance">
          <el-input v-model="form.dance" placeholder="请输入舞蹈" />
        </el-form-item>
        <el-form-item label="阅读" prop="readbook">
          <el-input v-model="form.readbook" placeholder="请输入阅读" />
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
    updateScore,
    eventssemesterList
  } from "@/api/system/score";
  import {
    deptTreeSelect
  } from "@/api/system/user";
  import {
    getToken
  } from "@/utils/auth";
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
        value: '',
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
          url: process.env.VUE_APP_BASE_API + "/system/score/importData"
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
        // 学生成绩管理表格数据
        scoreList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
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
      this.eventssemesterList();
      this.getDeptTree()

    },
    methods: {
      getScoreLevel(score) {
        if (score >= 85) return '优秀';
        if (score >= 70) return '良好';
        if (score >= 60) return '达标';
        return '待达标';
      },

      // 根据分数获取标签类型，用于显示不同颜色
      getScoreTagType(score) {
        if (score >= 85) return 'success';
        if (score >= 70) return 'info';
        if (score >= 60) return 'warning';
        return 'danger';
      },
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
          // console.log(this.queryParams.semesterId, 'response')
        });
      },
      /** 导入按钮操作 */
      handleImport() {
        this.upload.title = "用户导入";
        this.upload.open = true;
      },
      /** 下载模板操作 */
      importTemplate() {
        this.download('system/score/studentScoreTemplate', {}, `user_template_${new Date().getTime()}.xlsx`)
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
      /** 查询学生成绩管理列表 */
      getList() {
        this.loading = true;
        // this.queryParams.semesterId = 1
        // console.log(this.queryParams,'this.queryParams')
        this.queryParams.semesterId = this.queryParams.semesterId
        // console.log(this.queryParams,'this.queryParams')
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
          chinese: null,
          math: null,
          english: null,
          art: null,
          music: null,
          sports: null,
          practice: null,
          computer: null,
          labour: null,
          morality: null,
          health: null,
          science: null,
          calligraphy: null,
          dance: null,
          readbook: null,
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
        this.title = "添加学生成绩管理";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        // console.log(row,'row')
        this.reset();
        const studentScoreId = row.studentScoreId || this.ids
        getScore(studentScoreId).then(response => {
          this.form = response.data;
          // console.log( this.form ,' this.form ')
          this.open = true;
          this.title = "修改学生成绩管理";
        });
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
        this.$modal.confirm('是否确认删除学生成绩管理编号为"' + studentScoreIds + '"的数据项？').then(function() {
          return delScore(studentScoreIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('system/score/export', {
          ...this.queryParams
        }, `score_${new Date().getTime()}.xlsx`)
      }
    }
  };
</script>
