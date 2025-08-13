<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="课程编码" prop="courseCode">
        <el-input v-model="queryParams.courseCode" placeholder="请输入课程编码" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="课程名称" prop="courseName">
        <el-input v-model="queryParams.courseName" placeholder="请输入课程名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['system:course:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['system:course:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['system:course:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['system:course:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="courseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="课程id" align="center" prop="courseId" /> -->
      <el-table-column label="课程编码" align="center" prop="courseCode" />
      <el-table-column label="课程名称" align="center" prop="courseName" />
      <el-table-column label="显示顺序" align="center" prop="orderNum" />
      <el-table-column label="创建者" align="center" prop="createBy" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="200"/>
      <el-table-column label="更新者" align="center" prop="updateBy" />
      <el-table-column label="更新时间" align="center" prop="updateTime"  width="200"/>
      <!-- <el-table-column label="年级" align="center" prop="gradeId" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['system:course:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['system:course:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改课程对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="课程编码" prop="courseCode">
          <el-input v-model="form.courseCode" placeholder="请输入课程编码" />
        </el-form-item>
        <el-form-item label="课程名称" prop="courseName">
          <el-input v-model="form.courseName" placeholder="请输入课程名称" />
        </el-form-item>
       <!-- <el-form-item label="删除标志" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标志" />
        </el-form-item> -->
        <el-form-item label="年级" prop="gradeId">
           <el-select v-model="gradeId" multiple placeholder="请选择年级">
              <el-option
                v-for="(item,index) in options"
                :key="index"
                :label="item.deptName"
                :value="item.deptId">
              </el-option>
            </el-select>
       <!--   <el-input v-model="form.gradeId" placeholder="请输入年级id" /> -->
        </el-form-item>
        <el-form-item label="显示顺序" prop="orderNum">
           <el-input-number v-model="form.orderNum" :min="1" :max="9999"></el-input-number>
          <!-- <el-input v-model="form.orderNum" placeholder="请输入显示顺序" /> -->
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {
    listCourse,
    getCourse,
    delCourse,
    addCourse,
    updateCourse,
    coursegradeList
  } from "@/api/system/course";

  export default {
    name: "Course",
    data() {
      return {
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
        // 课程表格数据
        courseList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          courseCode: null,
          courseName: null,
          orderNum: null,
          gradeId: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {},
        options:[],
        gradeId:[]
      };
    },
    created() {
      this.getList();
      this.coursegradeList()
    },
    methods: {
      /** 查询课程列表 */
      coursegradeList(){
        coursegradeList({}).then(response => {
          this.options = response.data
            // console.log(response,'responseresponse')
        });
      },
      getList() {
        this.loading = true;
        listCourse(this.queryParams).then(response => {
          this.courseList = response.rows;
          // console.log(this.courseList,'this.courseList')
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
          courseId: null,
          courseCode: null,
          courseName: null,
          orderNum: null,
          delFlag: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null,
          gradeId: null
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
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.courseId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.gradeId = []
        this.reset();
        this.open = true;
        this.title = "添加课程";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        // console.log(row,'row')
        this.reset();
        const courseId = row.courseId || this.ids
        this.form = row
        // row.gradeId.split(',').map(Number);
        this.gradeId = row.gradeId.split(',').map(Number);
        // console.log(this.gradeId)
        this.open = true;
         this.title = "修改课程";
        // getCourse(courseId).then(response => {
        //   this.form = response.data;
        //   this.open = true;
        //   this.title = "修改课程";
        // });
      },
      /** 提交按钮 */
      submitForm() {
        this.form.gradeId = this.gradeId.join(',')
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.courseId != null) {
              updateCourse(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addCourse(this.form).then(response => {
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
        const courseIds = row.courseId || this.ids;
        this.$modal.confirm('是否确认删除课程编号为"' + courseIds + '"的数据项？').then(function() {
          return delCourse(courseIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('system/course/export', {
          ...this.queryParams
        }, `course_${new Date().getTime()}.xlsx`)
      }
    }
  };
</script>
