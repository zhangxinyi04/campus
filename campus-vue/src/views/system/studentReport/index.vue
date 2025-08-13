<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <!-- <el-form-item label="部门ID" prop="deptId">
        <el-input v-model="queryParams.deptId" placeholder="请输入部门ID" clearable @keyup.enter.native="handleQuery" />
      </el-form-item> -->
      <!--  <el-form-item label="学生姓名" prop="studentName">
        <el-input v-model="queryParams.studentName" placeholder="请输入学生姓名" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="学籍号" prop="studentNumber">
        <el-input v-model="queryParams.studentNumber" placeholder="请输入学籍号" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item> -->
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
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['system:student:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['system:student:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['system:student:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['system:student:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <div style="width: 100%;margin-top: 10px;">
      <div style="width: 13%;float: left;">
        <!-- <el-tree
             :data="deptOptions"
             :props="defaultProps"
             :expand-on-click-node="false"
             :filter-node-method="filterNode"
             ref="tree"
             node-key="id"
             default-expand-all
             highlight-current
             @node-click="handleNodeClick"
             :render-content="renderTreeContent"
           /> -->

        <el-tree :data="deptOptions" :props="defaultProps" :expand-on-click-node="false"
          :filter-node-method="filterNode" ref="tree" node-key="id" default-expand-all highlight-current
          @node-click="handleNodeClick" />
      </div>

      <div style="float: right;width: 87%">
        <el-table v-loading="loading" :data="studentList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="学生ID" align="center" prop="studentId" />
          <el-table-column label="学生姓名" align="center" prop="studentName" />
          <el-table-column label="性别" align="center" prop="sex">
            <template slot-scope="scope">
              <span v-if="scope.row.sex == 0">男</span>
              <span v-if="scope.row.sex == 1">女</span>
            </template>
          </el-table-column>
          <el-table-column label="学籍号" align="center" prop="studentNumber" />
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" @click="Investigationreport(scope.row)">查案报告</el-button>
              <!-- <el-button size="mini" type="text" @click="Addparents(scope.row)">家长管理</el-button> -->
              <!--  <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                v-hasPermi="['system:student:edit']">修改</el-button>
              <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                v-hasPermi="['system:student:remove']">删除</el-button> -->
            </template>
          </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
          @pagination="getList" />

      </div>

    </div>

    <el-dialog title="查案报告" :visible.sync="opengridData" width="70%" append-to-body>
         <pdfpageVue :user="user" v-if="opengridData"></pdfpageVue>

     <!-- <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitgridData">下 载</el-button>
        <el-button @click="cancelgridData">取 消</el-button>
      </div> -->

    </el-dialog>


  </div>
</template>

<script>
  import pdfpageVue from "./pdfpage.vue";
  import {
    listStudent,
    getStudent,
    delStudent,
    addStudent,
    updateStudent,
    patriarchlist,
    addpatriarch,
    updatepatriarch,
    delpatriarch
  } from "@/api/system/student";
  import {
    coursegradeList
  } from "@/api/system/course";
  import {
    eventssemesterList
  } from "@/api/system/score";
  import {
    deptTreeSelect,
    addUser,
    updateUser,
    listUser
  } from "@/api/system/user";
  import {
    studentstudentReport
  } from "@/api/system/studentReport";

  import Treeselect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";

  export default {
    name: "Student",
    components: {
      Treeselect
    },
    data() {
      return {
        options: [],
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        idsparents: [],
        // 非单个禁用
        single: true,
        // single: true,
        // 非多个禁用
        multiple: true,
        multipleparents: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 学生信息表格数据
        studentList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          deptId: null,
          studentName: null,
          sex: 0,
          studentNumber: null,
          semesterId: ''
        },
        // 表单参数
        form: {
          sex: 0,
        },
        // 表单校验
        rules: {

        },
        rulesparents: {

        },
        // options: [],
        deptOptions: [],
        enabledDeptOptions: [],
        defaultProps: {
          children: "children",
          label: "label"
        },
        table: false,
        gridData: [],
        titlegridData: '添加家长',
        opengridData: false,
        studentId: '',
        formgridData: {
          userType: "01"
        },
        user:{}
      };
    },
    created() {
      this.getList();
      this.eventssemesterList()
      this.getDeptTree()

    },
    components:{
      pdfpageVue
    },
    methods: {
      Investigationreport(row) {
        this.user.studentId = row.studentId
        this.user.semesterId = this.queryParams.semesterId
        this.opengridData = true

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
      handleSelectionChangeparents(selection) {

      },
      handleDeleteparents(row) {

      },
      cancelgridData() {
        this.opengridData = false
      },
      submitgridData() {
        this.opengridData = false
      },
      handleUpdateparents(row) {

      },
      handleAddparents() {

      },
      Addparents(row) {

      },

      patriarchlist() {

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
      renderTreeContent(h, {
        node,
        data,
        store
      }) {
        return h('span', {
          class: 'tree-node-wrapper'
        }, [
          h('span', {
            class: 'tree-node-label'
          }, node.label),
          h('i', {
            class: 'el-icon-arrow-right el-icon--right tree-node-arrow'
          })
        ]);
      },
      getDeptTree() {
        deptTreeSelect().then(response => {
          this.enabledDeptOptions = this.filterDisabledDept(JSON.parse(JSON.stringify(response.data)));
          this.deptOptions = response.data;
          let obj = {
            id: '',
            label: '全部',
          }
          this.deptOptions.unshift(obj)
          // console.log(this.deptOptions)

        });
      },
      // 过滤禁用的班级
      filterDisabledDept(deptList) {
        return deptList.filter(dept => {
          if (dept.disabled) {
            return false;
          }
          if (dept.children && dept.children.length) {
            dept.children = this.filterDisabledDept(dept.children);
          }
          return true;
        });
      },
      coursegradeList() {
        coursegradeList({}).then(response => {
          this.options = response.data
          // console.log(response,'responseresponse')
        });
      },
      /** 查询学生信息列表 */
      getList() {
        this.loading = true;
        listStudent(this.queryParams).then(response => {
          this.studentList = response.rows;
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
          studentId: null,
          deptId: null,
          studentName: null,
          sex: null,
          delFlag: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null,
          remark: null,
          studentNumber: null
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams = {}
        // this.queryParams.pageNum = 1;4
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.studentId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {

      },
      /** 修改按钮操作 */
      handleUpdate(row) {

      },
      /** 提交按钮 */
      submitForm() {

      },
      /** 删除按钮操作 */
      handleDelete(row) {

      },
      /** 导出按钮操作 */
      handleExport() {

      }
    }
  };
</script>

<style scoped>
  .tree-node-wrapper {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
  }

  .tree-node-label {
    /* 可以根据需要添加更多样式 */
  }

  .tree-node-arrow {
    /* 可以根据需要添加更多样式 */
  }
</style>
