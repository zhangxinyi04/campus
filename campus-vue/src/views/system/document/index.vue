<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="文件名称" prop="fileName">
        <el-input v-model="queryParams.fileName" placeholder="请输入文件名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="姓名" prop="nickName">
        <el-input v-model="queryParams.nickName" placeholder="请输入姓名" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态">
          <el-option label="未审核" value="0"></el-option>
          <el-option label="通过" value="1"></el-option>
          <el-option label="拒绝" value="2"></el-option>
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
          v-hasPermi="['system:document:add']">新增</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['system:document:edit']">修改</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['system:document:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-edit" size="mini" @click="Uploadrecords">
          <!-- <template #icon>
               <el-icon>
                 <el-icon-download-filled />
               </el-icon>
             </template> -->
          上传记录
        </el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['system:document:export']">导出</el-button>
      </el-col> -->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <div style="width: 100%;margin-top: 10px;">
      <div style="width: 13%;float: left;">
        <el-tree :data="selectList" :props="defaultProps" @node-click="handleNodeClick"></el-tree>
      </div>
      <div style="float: left;width: 87%">
        <el-table v-loading="loading" :data="documentList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="id" align="center" prop="id" width='50' />
          <el-table-column label="文件名" align="center" prop="fileName" show-overflow-tooltip />
          <el-table-column label="姓名" align="center">
            <template slot-scope="scope">
              {{scope.row.sysUser.nickName}}
            </template>
          </el-table-column>
          <el-table-column label="年级" align="center">
            <template slot-scope="scope">
              {{scope.row.sysUser.dept.deptName}}
            </template>
          </el-table-column>
          <el-table-column label="课程" align="center">
            <template slot-scope="scope">
              <el-tag style="float: left;margin: 5px;" v-if="scope.row.course">
                {{ scope.row.course.courseName }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="备注" align="center" prop="remark" show-overflow-tooltip />
          </el-table-column>
          <el-table-column label="创建时间" align="center" prop="createTime" width='200' />
          </el-table-column>

          <el-table-column label="状态" align="center">
            <template slot-scope="scope">
              <el-tag type="warning" v-if="scope.row.status == 0">未审核</el-tag>
              <el-tag v-if="scope.row.status == 1">通过</el-tag>
              <el-tag type="danger" v-if="scope.row.status == 2">拒绝</el-tag>
            </template>
          </el-table-column>

          <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width='180'  fixed="right">
            <template slot-scope="scope">
              <el-button size="mini" type="text" icon="el-icon-download" @click="handledownload(scope.row)">下载</el-button>
              <el-button size="mini" type="text" icon="el-icon-view" @click="handlefile(scope.row)"> 预览</el-button>
              <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                v-hasPermi="['system:document:remove']"> 删除</el-button>
              <el-button v-if="scope.row.status == 0" size="mini" type="text" icon="el-icon-check"
                @click="handlestatus(scope.row,1)"> 通过</el-button>
              <el-button v-if="scope.row.status == 0" size="mini" type="text" icon="el-icon-close"
                @click="handlestatus(scope.row,2)"> 拒绝</el-button>

            </template>
          </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
          @pagination="getList" />
      </div>
    </div>

    <!-- 上传记录 -->
    <el-dialog title="上传记录" :visible.sync="openUpload" width="60%" append-to-body>
      <div class="upload-list-container">
        <el-table :data="MyRecords" style="width: 100%"
          :header-cell-style="{ background: '#f5f7fa', color: '#606266' }" empty-text="暂无上传记录">
          <el-table-column prop="fileName" label="文件名" min-width="100%">
            <template slot-scope="{ row }">
              <div class="file-name">
                <i class="el-icon-document" style="margin-right: 8px; color: #409EFF"></i>
                <span>{{ row.fileName }}</span>
              </div>
            </template>
          </el-table-column>

          <el-table-column prop="status" label="状态">
            <template slot-scope="{ row }">
            <el-tag type="warning" v-if="row.status == 0">未审核</el-tag>
            <el-tag v-if="row.status == 1">通过</el-tag>
            <el-tag type="danger" v-if="row.status == 2">拒绝</el-tag>
            </template>
          </el-table-column>

          <el-table-column prop="uploadTime" label="上传时间" width="180" align="right">
            <template slot-scope="{ row }">
              <span class="upload-time">{{ row.createTime }}</span>
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="totalMyRecords>0" :total="totalMyRecords" :page.sync="queryParamsUpload.pageNum" :limit.sync="queryParamsUpload.pageSize"
          @pagination="Uploadrecords" />
         </div>
    </el-dialog>

    <!-- 添加或修改用户信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="文件" prop="delFlag">
          <div class="upload-file">
            <el-upload multiple :action="uploadFileUrl" :before-upload="handleBeforeUpload" :file-list="fileList"
              :limit="limit" :on-error="handleUploadError" :on-exceed="handleExceed" :on-success="handleUploadSuccess"
              :show-file-list="false" :headers="headers" class="upload-file-uploader" ref="fileUpload">
              <!-- 上传按钮 -->
              <el-button size="mini" type="primary">选取文件</el-button>
              <!-- 上传提示 -->
              <div class="el-upload__tip" slot="tip" v-if="showTip">
                请上传
                <template v-if="fileSize"> 大小不超过 <b style="color: #f56c6c">{{ fileSize }}MB</b> </template>
                <template v-if="fileType"> 格式为 <b style="color: #f56c6c">{{ fileType.join("/") }}</b> </template>
                的文件
              </div>
            </el-upload>

            <!-- 文件列表 -->
            <transition-group class="upload-file-list el-upload-list el-upload-list--text" name="el-fade-in-linear"
              tag="ul">
              <li :key="file.url" class="el-upload-list__item ele-upload-list__item-content"
                v-for="(file, index) in fileList">
                <el-link :href="`${baseUrl}${file.url}`" :underline="false" target="_blank">
                  <span class="el-icon-document"> {{ getFileName(file.name) }} </span>
                </el-link>
                <div class="ele-upload-list__item-content-action">
                  <el-link :underline="false" @click="handleDelete(index)" type="danger">删除</el-link>
                </div>
              </li>
            </transition-group>
          </div>

        </el-form-item>

        <el-form-item label="文件名" prop="fileName">
          <el-input v-model="form.fileName" placeholder="请输入文件名" />
        </el-form-item>


        <el-form-item label="年级" prop="gradeId">
          <el-select v-model="form.gradeId" placeholder="请选择年级">
            <el-option v-for="(item,index) in options" :key="index" :label="item.deptName" :value="item.deptId">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="课程" prop="fileName">
          <el-select v-model="form.courseId" placeholder="请选择课程">
            <el-option v-for="item in courseList" :key="item.courseId" :label="item.courseName" :value="item.courseId"
              :disabled="item.status == 1"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="资源" prop="fileName">
          <treeselect v-model="form.resourcesId" :options="courseLists" :normalizer="normalizer" :show-count="true"
            placeholder="选择资源" />
        </el-form-item>

        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" v-model="form.remark" placeholder="请输入备注" />
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
    listDocument,
    getDocument,
    delDocument,
    addDocument,
    updateDocument,
    resourcestreeselect,
    documentexamine,
    documentrecordsDownload,
    documentselectMyRecords
  } from "@/api/system/document";
  import {
    coursegradeList
  } from "@/api/system/course";
  import {
    getToken
  } from "@/utils/auth";
  import {
    getUser
  } from "@/api/system/user";
  import axios from 'axios';
  import Treeselect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";
  import {
    resourceslist
  } from "@/api/system/resourcemanagement";
  // import pdf from 'vue-pdf';


  export default {
    name: "Document",
    components: {
      Treeselect
    },
    // components: { pdf },
    props: {
      // 值
      value: [String, Object, Array],
      // 数量限制
      limit: {
        type: Number,
        default: 5,
      },
      // 大小限制(MB)
      fileSize: {
        type: Number,
        default: 100,
      },
      // 文件类型, 例如['png', 'jpg', 'jpeg']
      // fileType: {
      //   type: Array,
      //   default: () => ["doc", "xls", "ppt", "txt", "pdf", "docx", "pptx"],
      // },
      fileType: {
        type: Array,
        default: () => ["doc", "xls", "ppt", "txt", "pdf", "docx", "pptx", "docm", "dotx", "dotm"],
      },
      // 是否显示提示
      isShowTip: {
        type: Boolean,
        default: true
      }
    },
    data() {
      return {
        openfile: false,
        url: process.env.VUE_APP_BASE_API,
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
        // 用户信息表格数据
        documentList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          url: null,
          userId: null,
          resourcesId: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {},
        number: 0,
        uploadList: [],
        baseUrl: process.env.VUE_APP_BASE_API,
        uploadFileUrl: process.env.VUE_APP_BASE_API + "/common/uploadByLibreOffice", // 上传文件服务器地址
        headers: {
          Authorization: "Bearer " + getToken(),
        },
        fileList: [],
        fileUrl: '',
        options: [],
        // gradeId: '',
        course: '',
        courseList: [],
        selectList: [],
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        courseLists: [],
        queryParams: {
          pageNum: 1,
          pageSize: 20
        },
        queryParamsUpload: {
          pageNum: 1,
          pageSize: 5
        },
        totalMyRecords:0,
        openUpload: false,
        MyRecords: []
      };
    },
    created() {
      this.getList();
      this.coursegradeList();
      this.getUser()
      this.resourcestreeselect();
      this.resourceslist()
    },
    watch: {
      value: {
        handler(val) {
          if (val) {
            let temp = 1;
            // 首先将值转为数组
            const list = Array.isArray(val) ? val : this.value.split(',');
            // 然后将数组转为对象数组
            this.fileList = list.map(item => {
              if (typeof item === "string") {
                item = {
                  name: item,
                  url: item
                };
              }
              item.uid = item.uid || new Date().getTime() + temp++;
              return item;
            });
          } else {
            this.fileList = [];
            return [];
          }
        },
        deep: true,
        immediate: true
      }
    },
    computed: {
      // 是否显示提示
      showTip() {
        return this.isShowTip && (this.fileType || this.fileSize);
      },
    },
    methods: {
      Uploadrecords() {
        this.openUpload = true
        documentselectMyRecords(this.queryParamsUpload).then(response => {
          this.MyRecords = response.rows
          this.totalMyRecords = response.total
          // console.log(response, 'responseresponse')
        });
      },
      normalizer(node) {
        return {
          id: node.resourcesId,
          label: node.resourcesName,
          children: node.children
        };
      },
      resourceslist() {
        resourceslist(this.queryParams).then(response => {
          this.courseLists = this.handleTree(response.data, "resourcesId")
        });
      },
      handleNodeClick(data) {
        this.queryParams.resourcesId = data.id
        this.getList();
      },
      handlestatus(row, num) {
        let obj = {
          id: row.id,
          status: num
        }
        documentexamine(obj).then(response => {
          if (num == 1) {
            this.$modal.msgSuccess("审核通过成功");
          } else {
            this.$modal.msgSuccess("审核已拒绝");
          }
          this.getList();
        });
      },
      resourcestreeselect() {
        resourcestreeselect().then(response => {
          this.selectList = response.data
          // console.log(this.selectList,'111')
        });
      },
      getUser() {
        getUser().then(response => {
          this.courseList = response.course;
        });
      },
      coursegradeList() {
        coursegradeList({}).then(response => {
          this.options = response.data
          // console.log(response,'responseresponse')
        });
      },
      handledownload(row) {
        console.log(row, '好借好还')
        documentrecordsDownload({
          documentId: row.id,
          receiveUserId: row.userId
        }).then(response => {
          // console.log(response, 'responseresponse')
        });
        // return
        const fileUrl = row.url
        const link = document.createElement('a');
        link.href = fileUrl;
        link.setAttribute('download', fileUrl);
        link.click();
      },
      handlefile(row) {
        // console.log(row,'row')
        // return
        this.fileUrl = this.baseUrl+row.pdfUrl
        console.log(this.fileUrl,'row')
        // window.open(this.fileUrl, '_parent');
        window.location.href =this.fileUrl

      },
      // 上传前校检格式和大小
      handleBeforeUpload(file) {
        // 校检文件类型
        if (this.fileType) {
          const fileName = file.name.split('.');
          const fileExt = fileName[fileName.length - 1];
          const isTypeOk = this.fileType.indexOf(fileExt) >= 0;
          if (!isTypeOk) {
            this.$modal.msgError(`文件格式不正确，请上传${this.fileType.join("/")}格式文件!`);
            return false;
          }
        }
        // 校检文件名是否包含特殊字符
        if (file.name.includes(',')) {
          this.$modal.msgError('文件名不正确，不能包含英文逗号!');
          return false;
        }
        // 校检文件大小
        if (this.fileSize) {
          const isLt = file.size / 1024 / 1024 < this.fileSize;
          if (!isLt) {
            this.$modal.msgError(`上传文件大小不能超过 ${this.fileSize} MB!`);
            return false;
          }
        }
        this.$modal.loading("正在上传文件，请稍候...");
        this.number++;
        return true;
      },
      // 文件个数超出
      handleExceed() {
        this.$modal.msgError(`上传文件数量不能超过 ${this.limit} 个!`);
      },
      // 上传失败
      handleUploadError(err) {
        this.$modal.msgError("上传文件失败，请重试");
        this.$modal.closeLoading();
      },
      // 上传成功回调
      handleUploadSuccess(res, file) {
        if (res.code === 200) {
          this.uploadList.push({
            name: res.fileName,
            url: res.fileName
          });
          // this.form.pdfurl = res.pdfUrl
          this.form.fileName = res.originalFilename
          this.form.url = res.url
          this.form.pdfUrl = res.pdfUrl
          this.uploadedSuccessfully();
        } else {
          this.number--;
          this.$modal.closeLoading();
          this.$modal.msgError(res.msg);
          this.$refs.fileUpload.handleRemove(file);
          this.uploadedSuccessfully();
        }
      },
      // 删除文件
      handleDelete(index) {
        this.fileList.splice(index, 1);
        this.$emit("input", this.listToString(this.fileList));
      },
      // 上传结束处理
      uploadedSuccessfully() {
        if (this.number > 0 && this.uploadList.length === this.number) {
          this.fileList = this.fileList.concat(this.uploadList);
          this.uploadList = [];
          this.number = 0;
          this.$emit("input", this.listToString(this.fileList));
          this.$modal.closeLoading();
        }
      },
      // 获取文件名称
      getFileName(name) {
        // 如果是url那么取最后的名字 如果不是直接返回
        if (name.lastIndexOf("/") > -1) {
          return name.slice(name.lastIndexOf("/") + 1);
        } else {
          return name;
        }
      },
      // 对象转成指定字符串分隔
      listToString(list, separator) {
        let strs = "";
        separator = separator || ",";
        for (let i in list) {
          strs += list[i].url + separator;
        }
        return strs != '' ? strs.substr(0, strs.length - 1) : '';
      },

      /** 查询用户信息列表 */
      getList() {
        this.loading = true;
        listDocument(this.queryParams).then(response => {
          this.documentList = response.rows;
          // console.log(this.documentList,'this.documentListthis.documentList')
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
          id: null,
          delFlag: null,
          createTime: null,
          url: null,
          userId: null
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
        this.ids = selection.map(item => item.id)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        // this.gradeId = []
        this.fileList = []
        this.reset();
        this.open = true;
        this.title = "添加文件";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getDocument(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改用户信息";
        });
      },
      /** 提交按钮 */
      submitForm() {
        // console.log(this.form,'this.formthis.form')
        // return
        // this.form.gradeId = this.gradeId.join(',')
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateDocument(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addDocument(this.form).then(response => {
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
        const ids = row.id || this.ids;
        this.$modal.confirm('是否确认删除用户信息编号为"' + ids + '"的数据项？').then(function() {
          return delDocument(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('system/document/export', {
          ...this.queryParams
        }, `document_${new Date().getTime()}.xlsx`)
      }
    }
  };
</script>

<style scoped>

  .upload-list-container {
    padding: 0 10px;
  }

  /* 表格行高调整 */
  /deep/ .el-table__row {
    height: 50px;
  }

  /* 文件名样式 */
  .file-name {
    display: flex;
    align-items: center;
    font-weight: 500;
    color: #303133;
  }

  /* 上传时间样式 */
  .upload-time {
    color: #909399;
    font-size: 12px;
  }

  /* 表格边框优化 */
  ::v-deep .el-table--border {
    border: 1px solid #ebeef5;
    border-radius: 8px;
  }

  /* 表头分割线 */
  ::v-deep .el-table__header-wrapper {
    border-bottom: 2px solid #ebeef5;
  }

  /* 单元格内边距调整 */
  ::v-deep .el-table td,
  ::v-deep .el-table th {
    padding: 12px 0;
  }

  /* 鼠标悬停效果 */
 ::v-deep .el-table--enable-row-hover .el-table__body tr:hover>td {
    background-color: #f5f7fa !important;
  }
  /* .upload-demo {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .upload-demo .el-upload {
    width: 100%;
    height: 180px;
    text-align: center;
  } */
</style>
