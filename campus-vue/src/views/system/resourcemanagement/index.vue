<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="资源名称" prop="resourcesName">
        <el-input v-model="queryParams.resourcesName" placeholder="请输入资源名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
     <!-- <el-form-item label="课程名称" prop="courseName">
        <el-input v-model="queryParams.courseName" placeholder="请输入课程名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item> -->
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
     <!-- <el-col :span="1.5">
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
      </el-col> -->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

<el-table v-loading="loading" :data="courseList" row-key="resourcesId"
     :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
       <el-table-column prop="resourcesId" label="资源id"></el-table-column>
      <el-table-column prop="resourcesName" label="资源名称"></el-table-column>
     <!-- <el-table-column label="状态" prop="status">
        <template slot-scope="scope">
          <span>{{ scope.row.status }}</span>
        </template>
      </el-table-column> -->
       <el-table-column prop="createTime" label="日期"></el-table-column>
     <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
           >修改</el-button>
           <el-button size="mini" type="text" icon="el-icon-plus" @click="handleAdd(scope.row)"
            >新增</el-button>
         <!-- <el-button size="mini" type="text" icon="el-icon-plus" @click="handleAdd(scope.row)"
            v-hasPermi="['system:menu:add']">新增</el-button>-->
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- <el-table v-loading="loading" :data="courseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="课程编码" align="center" prop="courseCode" />
      <el-table-column label="课程名称" align="center" prop="courseName" />
      <el-table-column label="显示顺序" align="center" prop="orderNum" />
      <el-table-column label="创建者" align="center" prop="createBy" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="200"/>
      <el-table-column label="更新者" align="center" prop="updateBy" />
      <el-table-column label="更新时间" align="center" prop="updateTime"  width="200"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['system:course:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['system:course:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table> -->

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改课程对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
       <el-form-item label="上级资源" prop="parentId">
         <treeselect v-model="form.parentId" :options="courseList" :normalizer="normalizer" :show-count="true"
           placeholder="选择上级菜单" />
       </el-form-item>
        <el-form-item label="资源名称" prop="resourcesName">
          <el-input v-model="form.resourcesName" placeholder="请输入课程名称" />
        </el-form-item>
        <el-form-item label="显示顺序" prop="orderNum">
           <el-input-number v-model="form.orderNum" :min="1" :max="9999"></el-input-number>
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
    resourceslist,
    addresources,
    delresources,
    updateresources
  } from "@/api/system/resourcemanagement";
    import Treeselect from "@riophae/vue-treeselect";
    import "@riophae/vue-treeselect/dist/vue-treeselect.css";

  export default {
    name: "Course",
    components: {
      Treeselect
    },
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
      // this.coursegradeList()
    },
    methods: {
      normalizer(node) {
        // console.log(node,'nodenode')
        // if (node.children && !node.children.length) {
        //   delete node.children;
        // }
        return {
          id: node.resourcesId,
          label: node.resourcesName,
          children: node.children
        };
      },
      /** 查询课程列表 */
      coursegradeList(){
        coursegradeList({}).then(response => {
          this.options = response.data
            // console.log(response,'responseresponse')
        });
      },
      getList() {
        this.loading = true;
        resourceslist(this.queryParams).then(response => {
          this.courseList = this.handleTree(response.data, "resourcesId");
         // console.log(this.courseList,'this.courseList')
          // this.total = response.total;
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
             resourcesId:null,
             resourcesName:'',
             orderNum:''
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
      handleAdd(row) {
        // console.log(row,'111')
        // this.gradeId = []
        this.reset();
        if (row != null && row.parentId) {
          this.form.parentId = row.resourcesId;
        } else {
          // this.form.parentId = 0;
        }
        this.open = true;
        this.title = "添加资源";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        // console.log(row,'row')
        // return
        this.reset();
        const resourcesId = row.resourcesId || this.ids
        this.form = row
        this.open = true;
         this.title = "修改资源";
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            // addresources(this.form).then(response => {
            //   this.$modal.msgSuccess("新增成功");
            //   this.open = false;
            //   this.getList();
            // });
            // return
            if (this.form.resourcesId != null) {
              updateresources(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addresources(this.form).then(response => {
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
        // console.log(row)
        // return
        const resourcesId = row.resourcesId || this.ids;
        this.$modal.confirm('是否确认删除资源id为"' + resourcesId + '"的数据项？').then(function() {
          return delresources(resourcesId);
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
