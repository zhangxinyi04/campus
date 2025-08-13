<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <!-- <el-form-item label="部门ID" prop="deptId">
        <el-input v-model="queryParams.deptId" placeholder="请输入部门ID" clearable @keyup.enter.native="handleQuery" />
      </el-form-item> -->
      <el-form-item label="学生姓名" prop="studentName">
        <el-input v-model="queryParams.studentName" placeholder="请输入学生姓名" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="学籍号" prop="studentNumber">
        <el-input v-model="queryParams.studentNumber" placeholder="请输入学籍号" clearable
          @keyup.enter.native="handleQuery" />
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
              <el-button size="mini" type="text" @click="Addparents(scope.row)">家长管理</el-button>
              <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                v-hasPermi="['system:student:edit']">修改</el-button>
              <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                v-hasPermi="['system:student:remove']">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
          @pagination="getList" />

      </div>

    </div>

    <el-dialog :title="titlegridData" :visible.sync="opengridData" width="500px" append-to-body>
      <el-form ref="formgridData" :model="formgridData" :rules="rulesparents" label-width="80px">
        <el-form-item label="家长姓名" prop="nickName">
          <el-input v-model="formgridData.nickName" placeholder="请输入家长姓名" />
        </el-form-item>
        <el-form-item label="类型" prop="sex">
          <el-radio-group v-model="formgridData.sex">
            <el-radio :label="0">父亲</el-radio>
            <el-radio :label="1">母亲</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号码" prop="phonenumber">
          <el-input v-model="formgridData.phonenumber" placeholder="请输入手机号码" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitgridData">确 定</el-button>
        <el-button @click="cancelgridData">取 消</el-button>
      </div>

    </el-dialog>

    <el-drawer title="家长管理" :visible.sync="table" direction="rtl" size="50%">
      <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAddparents"
        style="margin: 20px;">添加家长</el-button>
      <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multipleparents"
        @click="handleDeleteparents">删除</el-button>
      <el-table :data="gridData" style="width: 95%;margin-left:20px;" @selection-change="handleSelectionChangeparents">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column property="nickName" label="家长姓名"></el-table-column>
        <el-table-column property="sex" label="类型">
          <template slot-scope="scope">
            <p v-if="scope.row.sex == 0">父亲</p>
            <p v-if="scope.row.sex == 1">母亲</p>
          </template>
        </el-table-column>
        <el-table-column property="phonenumber" label="手机号码"></el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-edit"
              @click="handleUpdateparents(scope.row)">修改</el-button>
            <el-button size="mini" type="text" icon="el-icon-delete"
              @click="handleDeleteparents(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-drawer>


    <!-- 添加或修改学生信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="班级" prop="deptId">
          <treeselect v-model="form.deptId" :options="enabledDeptOptions" :show-count="true" placeholder="请选择班级" />
        </el-form-item>
        <el-form-item label="学生姓名" prop="studentName">
          <el-input v-model="form.studentName" placeholder="请输入学生姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="form.sex">
            <el-radio :label="0">男</el-radio>
            <el-radio :label="1">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="学籍号" prop="studentNumber">
          <el-input v-model="form.studentNumber" placeholder="请输入学籍号" />
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
    deptTreeSelect,
    addUser,
    updateUser,
    listUser
  } from "@/api/system/user";

  import Treeselect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";

  export default {
    name: "Student",
    components: {
      Treeselect
    },
    data() {
      return {
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
          studentNumber: null
        },
        // 表单参数
        form: {
          sex: 0,
        },
        // 表单校验
        rules: {
          deptId: [{
            required: true,
            message: '请选择年级',
            trigger: 'change'
          }],
          studentName: [{
            required: true,
            message: '请输入学生姓名',
            trigger: 'blur'
          }],
          sex: [{
            required: true,
            message: '请选择性别',
            trigger: 'change'
          }],
          studentNumber: [{
            required: true,
            message: '请输入学籍号',
            trigger: 'blur'
          }]
        },
        rulesparents: {
          nickName: [{
            required: true,
            message: '请输入家长姓名',
            trigger: 'blur'
          }],
          sex: [{
            required: true,
            message: '请选择家长类型',
            trigger: 'change'
          }],
          phonenumber: [{
              required: true,
              message: '请输入手机号码',
              trigger: 'blur'
            },
            {
              pattern: /^1[3-9]\d{9}$/,
              message: '请输入正确的手机号码',
              trigger: 'blur'
            }
          ]
        },
        options: [],
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
        }
      };
    },
    created() {
      this.getList();
      this.coursegradeList()
      this.getDeptTree()
    },
    methods: {
      handleSelectionChangeparents(selection) {
        this.idsparents = selection.map(item => item.patriarchId)
        this.multipleparents = !selection.length
      },
      handleDeleteparents(row) {
        const patriarchId = row.patriarchId || this.idsparents;
        this.$modal.confirm('是否确认删除家长信息编号为"' + patriarchId + '"的数据项？').then(function() {
          return delpatriarch(patriarchId);
        }).then(() => {
          this.patriarchlist();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      cancelgridData() {
        this.formgridData = {}
        this.opengridData = false
      },
      submitgridData() {

        this.$refs.formgridData.validate((valid) => {
          if (valid) {
            this.formgridData.studentId = this.studentId
            this.formgridData.userType = '01',
              this.formgridData.userName = this.formgridData.phonenumber
            // var phonenumber = '18600593392'
            this.formgridData.password = this.formgridData.phonenumber.slice(-6);
            if (this.formgridData.patriarchId != null) {
              // updatepatriarch(this.formgridData).then(response => {
              //   this.$modal.msgSuccess("修改成功");
              //   this.opengridData = false;
              //   this.patriarchlist();
              // });
              updateUser(this.formgridData).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.opengridData = false;
                this.patriarchlist();
              });
            } else {
              // addpatriarch(this.formgridData).then(response => {
              //   this.$modal.msgSuccess("新增成功");
              //   this.opengridData = false;
              //   this.patriarchlist();
              // });
              addUser(this.formgridData).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.opengridData = false;
                this.patriarchlist();
              })
            }
          } else {
            // 校验不通过
            // console.log('表单校验不通过');
            return false;
          }
        });
      },
      handleUpdateparents(row) {
        this.titlegridData = '修改家长信息'
        this.formgridData = row
        this.formgridData.userType = row.userType * 1
        this.opengridData = true
      },
      handleAddparents() {
        this.titlegridData = '添加家长信息'
        this.formgridData = {}
        this.opengridData = true
      },
      Addparents(row) {
        this.studentId = row.studentId
        this.patriarchlist()
        this.table = true
      },
      // getList() {
      //   this.loading = true;
      //   listUser(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
      //     this.userList = response.rows;
      //     this.total = response.total;
      //     this.loading = false;
      //   });
      // },
      patriarchlist() {
        let obj = {
          studentId: this.studentId,
          userType: '01',
          pageNum: 1,
          pageSize: 10,
        }
        // listUser(obj).then(response => {
        //   this.gridData = response.rows
        //   // console.log(response)
        // });

        patriarchlist(obj).then(response => {
          this.gridData = response.data
          // console.log(response)
        });
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
        // this.reset();
        this.form = {
          deptId: '',
          studentName: '',
          studentNumber: '',
          sex: 0
        }
        this.open = true;
        this.title = "添加学生信息";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        // this.reset();
        // this.form = {
        //   deptId: '',
        //   studentName: '',
        //   studentNumber: '',
        //   sex: 0
        // }
        const studentId = row.studentId || this.ids
        getStudent(studentId).then(response => {
          this.form = response.data;
          this.form.sex =  this.form.sex * 1
          // console.log( this.form,' this.form this.form')
          this.open = true;
          this.title = "修改学生信息";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.studentId != null) {
              updateStudent(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addStudent(this.form).then(response => {
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
        const studentIds = row.studentId || this.ids;
        this.$modal.confirm('是否确认删除学生信息编号为"' + studentIds + '"的数据项？').then(function() {
          return delStudent(studentIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('system/student/export', {
          ...this.queryParams
        }, `student_${new Date().getTime()}.xlsx`)
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
