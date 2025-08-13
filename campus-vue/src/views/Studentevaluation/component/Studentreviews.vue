<template>
  <div class="review-container">
    <div v-if="!showDialog">
      <div v-if='!showTextreview'>
        <!-- 操作按钮组 -->
        <div class="action-buttons">
          <el-button type="primary" @click="openDialog('add')">添加评价项</el-button>
        </div>

        <!-- 选项卡切换 -->
        <el-tabs v-model="currentTab" class="custom-tabs" style="margin-top: 30px;" @tab-click="handleClick">
          <el-tab-pane name="praise" :label="'👍 表扬榜'" class="praise-tab"></el-tab-pane>
          <el-tab-pane name="improve" :label="'💡 待改进'" class="improve-tab"></el-tab-pane>
        </el-tabs>

        <!-- 内容展示区域 -->
        <div class="content-container">
          <transition name="slide-in-up">
            <div v-show="currentTab === 'praise'" class="grid-section praise">
              <!-- <h2 class="section-title">👍 表扬榜</h2> -->
              <div class="grid-items">
                <div v-for="item in items.praise" :key="item.id" class="grid-item" @click="increment(item)">
                  <div class="item-badge" v-if="obj.length == 1">{{item.counts}}</div>
                  <div class="item-icon">
                    <img :src="baseUrl+item.image" alt="" style="width: 70px;height: 70px;border-radius: 50%;">
                  </div>
                  <div class="item-title">{{ item.title }}</div>
                  <div class="item-actions">
                    <el-button type="text" @click.stop="Textreview(item)">文字点评</el-button>
                    <el-button type="text" @click.stop="openDialog('edit', item)">编辑</el-button>
                    <el-button type="text" @click.stop="deleteItem(item)">删除</el-button>
                  </div>
                </div>
              </div>
            </div>
          </transition>

          <transition name="slide-in-up">
            <div v-show="currentTab === 'improve'" class="grid-section improve">
              <!-- <h2 class="section-title">💡 待改进</h2> -->
              <div class="grid-items">
                <div v-for="item in items.improve" :key="item.id" class="grid-item" @click="increment(item)">
                  <div class="item-badge" v-if="obj.length == 1">{{item.counts}}</div>
                  <div class="item-icon">
                    <img :src="baseUrl+item.image" alt="" style="width: 70px;height: 70px;border-radius: 50%;">
                  </div>
                  <div class="item-title">{{ item.title }}</div>
                  <div class="item-actions">
                    <el-button type="text" @click.stop="Textreview(item)">文字点评</el-button>
                    <el-button type="text" @click.stop="openDialog('edit', item)">编辑</el-button>
                    <el-button type="text" @click.stop="deleteItem(item)">删除</el-button>
                  </div>
                </div>
              </div>
            </div>
          </transition>
        </div>
      </div>

      <div class="action-buttons" style="margin-top: 20px;">
        <el-button type="primary" @click="openDialog('customize')">添加自定义评价项</el-button>
      </div>
      <transition name="slide-in-up">
        <div v-show="currentTab === 'praise'" class="grid-section praise">
          <!-- <h2 class="section-title">👍 表扬榜</h2> -->
          <div class="grid-items">
            <div v-for="item in items.praisecustomize" :key="item.id" class="grid-item" @click="increment(item)">
              <div class="item-badge" v-if="obj.length == 1">{{item.counts}}</div>
              <div class="item-icon">
                <img :src="baseUrl+item.image" alt="" style="width: 70px;height: 70px;border-radius: 50%;">
              </div>
              <div class="item-title">{{ item.title }}</div>
              <div class="item-actions">
                <el-button type="text" @click.stop="Textreview(item)">文字点评</el-button>
                <el-button type="text" @click.stop="openDialog('edit', item)">编辑</el-button>
                <el-button type="text" @click.stop="deleteItem(item)">删除</el-button>
              </div>
            </div>
          </div>
        </div>
      </transition>
      <transition name="slide-in-up">
        <div v-show="currentTab === 'improve'" class="grid-section improve">
          <!-- <h2 class="section-title">💡 待改进</h2> -->
          <div class="grid-items">
            <div v-for="item in items.improvecustomize" :key="item.id" class="grid-item" @click="increment(item)">
              <div class="item-badge" v-if="obj.length == 1">{{item.counts}}</div>
              <div class="item-icon">
                <img :src="baseUrl+item.image" alt="" style="width: 70px;height: 70px;border-radius: 50%;">
              </div>
              <div class="item-title">{{ item.title }}</div>
              <div class="item-actions">
                <el-button type="text" @click.stop="Textreview(item)">文字点评</el-button>
                <el-button type="text" @click.stop="openDialog('edit', item)">编辑</el-button>
                <el-button type="text" @click.stop="deleteItem(item)">删除</el-button>
              </div>
            </div>
          </div>
        </div>
      </transition>



    </div>




    <div v-if="showTextreview">
      <el-form ref="TextreviewItem" :model="TextreviewItem" :rules="rulesTextreview" label-width="80px">
        <el-form-item label="评价" required prop="content">
          <!-- <el-input v-model="currentItem.content" placeholder="请输入标题评价内容"></el-input> -->
          <el-input type="textarea" :rows="5" placeholder="请输入评价内容" v-model="TextreviewItem.content">
          </el-input>
        </el-form-item>
        <el-form-item label="图片" required>
          <ImageUpload @input="TextreviewChild" :value='fileListTextreview' />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="showTextreview = false">取消</el-button>
        <el-button type="primary" @click="saveTextreview">确认</el-button>
      </span>
    </div>

    <!-- 新增/编辑对话框 -->
    <!-- <el-dialog :title="dialogTitle" :visible.sync="showDialog" width="500px" style="z-index: 10000;"> -->
    <div v-if="showDialog">
      <el-form ref="currentItem" :model="currentItem" :rules="rules" label-width="80px">
        <el-form-item label="类型" required prop="type">
          <el-radio-group v-model="currentItem.type">
            <el-radio label="0">表扬</el-radio>
            <el-radio label="1">待改进</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="标题" required prop="title">
          <el-input v-model="currentItem.title" placeholder="请输入标题"></el-input>
        </el-form-item>
        <el-form-item label="图标" required>
          <ImageUpload @input="receiveDataFromChild" :value='fileList' />
        </el-form-item>
        <el-form-item label="分数" prop="mark">
          <el-input-number v-model="currentItem.mark" :min="0" v-if="currentItem.type == '0'"></el-input-number>
          <el-input-number v-model="currentItem.mark" :max="0" v-if="currentItem.type == '1'"></el-input-number>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="saveItem" :disabled="currentItem.mark < 0"
          v-if="currentItem.type == '0'">确认</el-button>
        <el-button type="primary" @click="saveItem" :disabled="currentItem.mark > 0"
          v-if="currentItem.type == '1'">确认</el-button>
      </span>
    </div>
    <!-- </el-dialog> -->
  </div>
</template>

<script>
  import ImageUpload from "@/components/ImageUpload/index"
  import {
    classEvaluationpost,
    classEvaluationput,
    classEvaluationlist,
    classEvaluationdelete,
    classEvaluation,
    selectStudentBonusCountByStudentId
  } from "@/api/system/type";
  import {
    getUserProfile
  } from "@/api/system/user";
  export default {
    props: ['obj'],
    data() {
      return {

        showTextreview: false,
        fileListTextreview: [],
        TextreviewItem: {
          image: null,
          content: null
        },
        rulesTextreview: {
          content: [{
            required: true,
            message: "评价内容不能为空",
            trigger: "blur"
          }],
        },
        baseUrl: process.env.VUE_APP_BASE_API,
        currentTab: 'praise',
        items: {
          praise: [],
          improve: [],
          praisecustomize: [],
          improvecustomize: [],
        },
        showDialog: false,
        dialogMode: 'add',
        currentItem: {

        },
        tempIconUrl: null,
        fileList: [],
        queryParams: {
          pageNum: 1,
          pageSize: 100,
          userId:null,
          title: null,
          image: null,
          type: null,
          mark: null
        },
        rules: {
          title: [{
            required: true,
            message: "标题不能为空",
            trigger: "blur"
          }],
          type: [{
            required: true,
            message: "类型不能为空",
            trigger: "change"
          }],
          mark: [{
            required: true,
            message: "分数不能为空",
            trigger: "blur"
          }]
        },
        total: 0,
        showTextreview: false,
        showTextreviewObj: [],
        selectStudentArr: [],
        userId: ''
      };
    },
    created() {
      this.currentItem = this.emptyItem();
      this.getUser()
      if (this.obj.length == 1) {
        this.selectStudentBonusCountByStudentId()
      } else {
        this.classEvaluationlist0()
        this.classEvaluationlist1()
        this.classEvaluationlist2()
        this.classEvaluationlist3()
      }

    },
    mounted() {

    },
    computed: {
      dialogTitle() {
        return this.dialogMode === 'add' ? '新增评价项' : '编辑评价项';
      }
    },
    components: {
      ImageUpload
    },

    methods: {
      getUser() {
        getUserProfile().then(response => {
          this.userId = response.data.userId
        });
      },
      handleClick(tab, event) {
        if (this.obj.length == 1) {
          this.selectStudentBonusCountByStudentId()
        }
      },
      selectStudentBonusCountByStudentId() {
        // console.log(this.obj,'0')
        var type = this.currentTab == 'praise' ? '0' : '1'
        selectStudentBonusCountByStudentId({
          studentId: this.obj[0].studentId,
          type: type
        }).then(response => {
          this.selectStudentArr = response.data
          this.classEvaluationlist0()
          this.classEvaluationlist1()
          this.classEvaluationlist2()
          this.classEvaluationlist3()
        });
      },
      TextreviewChild(data) {
        this.TextreviewItem.image = data
      },
      saveTextreview() {
        var newShowTextreviewObj = this.showTextreviewObj.map(item => {
          return {
            ...item,
            content: this.TextreviewItem.content,
            image: this.TextreviewItem.image
          };
        });
        // console.log(newShowTextreviewObj, 'newShowTextreviewObjnewShowTextreviewObj')
        this.$refs["TextreviewItem"].validate(valid => {
          if (valid) {
            classEvaluation(newShowTextreviewObj).then(response => {
              this.$message({
                message: newShowTextreviewObj[0].type === '0' ?
                  `${newShowTextreviewObj[0].title} +${newShowTextreviewObj[0].mark}` :
                  `${newShowTextreviewObj[0].title} -${newShowTextreviewObj[0].mark}`,
                type: newShowTextreviewObj[0].type === '0' ? 'success' : 'warning'
              });
              this.$emit('message', 'ok')
              this.showTextreview = false;
            });
          }
        });
      },
      Textreview(item) {
        var dataArr = this.obj
        var dataObj = {}
        for (var i = 0; i < dataArr.length; i++) {
          dataArr[i].mark = item.mark
          dataArr[i].type = item.type
          dataArr[i].title = item.title
          dataArr[i].bonusTypeId = item.bonusTypeId
        }
        this.showTextreviewObj = dataArr
        this.showTextreview = true;
      },
      classEvaluationlist0() {
        this.queryParams.type = 0
        this.queryParams.userId = 0
        classEvaluationlist(this.queryParams).then(response => {
          this.items.praise = response.data
          const countMap = new Map();
          this.selectStudentArr.forEach(item => {
            countMap.set(item.bonusTypeId, item.counts);
          });
          this.items.praise = this.items.praise.map(item => ({
            ...item,
            counts: countMap.has(item.bonusTypeId) ? countMap.get(item.bonusTypeId) : 0
          }));
          // this.classEvaluationlist2()
        });
      },
      classEvaluationlist1() {
        this.queryParams.type = 1
        this.queryParams.userId = 0
        classEvaluationlist(this.queryParams).then(response => {
          this.items.improve = response.data
          const countMap = new Map();
          this.selectStudentArr.forEach(item => {
            countMap.set(item.bonusTypeId, item.counts);
          });
          this.items.improve = this.items.improve.map(item => ({
            ...item,
            counts: countMap.has(item.bonusTypeId) ? countMap.get(item.bonusTypeId) : 0
          }));
          // this.classEvaluationlist3()
        });
      },
      classEvaluationlist2() { //自定义表扬
        this.queryParams.type = 0
        this.queryParams.userId = this.userId
        classEvaluationlist(this.queryParams).then(response => {
          this.items.praisecustomize = response.data
          const countMap = new Map();
          this.selectStudentArr.forEach(item => {
            countMap.set(item.bonusTypeId, item.counts);
          });
          if (this.items.praisecustomize.length != 0) {
            this.items.praisecustomize = this.items.praisecustomize.map(item => ({
              ...item,
              customize: true,
              counts: countMap.has(item.bonusTypeId) ? countMap.get(item.bonusTypeId) : 0
            }));
            // this.items.praise = [...this.items.praise,...praise]
          }
        });
      },
      classEvaluationlist3() { //自定义批评
        this.queryParams.type = 1
        this.queryParams.userId = this.userId
        classEvaluationlist(this.queryParams).then(response => {
          this.items.improvecustomize= response.data
          // var improve = response.data
          const countMap = new Map();
          this.selectStudentArr.forEach(item => {
            countMap.set(item.bonusTypeId, item.counts);
          });
          if (this.items.improvecustomize.length != 0) {
            this.items.improvecustomize = this.items.improvecustomize.map(item => ({
              ...item,
              customize: true,
              counts: countMap.has(item.bonusTypeId) ? countMap.get(item.bonusTypeId) : 0
            }));
             // this.items.improve = [...this.items.improve,...improve]
          }

        });
      },
      receiveDataFromChild(data) {
        this.currentItem.image = data
      },
      emptyItem() {
        return {
          id: null,
          type: '0',
          title: null,
          mark: null
        };
      },
      openDialog(mode, item = null) {
        this.dialogMode = mode;
        this.currentItem = item ? {
          ...item
        } : this.emptyItem();
        this.fileList = item !== null ? item.image : []
        this.showDialog = true;
      },
      // handleIconUpload(file) {
      //   const url = URL.createObjectURL(file);
      //   this.currentItem.icon = url;
      //   this.currentItem.iconType = 'image';
      //   this.tempIconUrl = url; // 存储临时 URL 用于释放内存
      //   return false;
      // },
      saveItem() {
        this.currentItem.userId = this.dialogMode == 'customize' ? this.userId : null;
        this.$refs["currentItem"].validate(valid => {
          if (valid) {
            if (this.currentItem.bonusTypeId != null) {
              classEvaluationput(this.currentItem).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.showDialog = false;
                this.classEvaluationlist0()
                this.classEvaluationlist1()
                this.classEvaluationlist2()
                this.classEvaluationlist3()
              });
            } else {
              classEvaluationpost(this.currentItem).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.showDialog = false;
                this.classEvaluationlist0()
                this.classEvaluationlist1()
                this.classEvaluationlist2()
                this.classEvaluationlist3()
              });
            }
          }
        });
      },
      deleteItem(item) {
        const bonusTypeIds = item.bonusTypeId
        this.$modal.confirm('是否确认删除该评价项？').then(function() {
          return classEvaluationdelete(bonusTypeIds);
        }).then(() => {
          this.classEvaluationlist0()
          this.classEvaluationlist1()
          this.classEvaluationlist2()
          this.classEvaluationlist3()
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
        // this.$confirm('确定删除该评价项吗？', '提示', {
        //     type: 'warning'
        //   })
        //   .then(() => {
        //     // const targetArray = item.type === 'praise' ? this.items.praise : this.items.improve;
        //     // const index = targetArray.findIndex(i => i.id === item.id);
        //     // targetArray.splice(index, 1);

        //   });
      },
      increment(item) {
        // console.log(this.obj,'11')
        var dataArr = this.obj
        var dataObj = {}
        dataObj.mark = item.mark
        dataObj.type = item.type
        dataObj.bonusTypeId = item.bonusTypeId
        for (var i = 0; i < dataArr.length; i++) {
          dataArr[i].mark = item.mark
          dataArr[i].type = item.type
          dataArr[i].bonusTypeId = item.bonusTypeId
        }
        classEvaluation(dataArr).then(response => {
          this.$message({
            message: item.type === '0' ? `${item.title} +${item.mark}` : `${item.title} ${item.mark}`,
            type: item.type === '0' ? 'success' : 'warning'
          });
          if (this.obj.length == 1) {
            this.selectStudentBonusCountByStudentId()
          }
          this.$emit('message', 'ok')
        });
      },
      showMessage(options) {
        this.$message({
          message: options.content,
          type: options.type === 'praise' ? 'success' : 'info',
          duration: 1500
        });
      }
    },
    beforeUnmount() {
      // 释放图片资源内存
      if (this.tempIconUrl) {
        URL.revokeObjectURL(this.tempIconUrl);
      }
    }
  };
</script>

<style scoped>
  /* 整体容器 */
  .review-container {
    padding: 20px;
    max-width: 1200px;
    margin: 0 auto;
  }

  /* 操作按钮组 */
  .action-buttons {
    text-align: right;
    margin-bottom: 30px;
  }

  /* 选项卡样式 */
  .custom-tabs {
    --tab-active-color: #27ae60;
    --tab-improve-color: #e67e22;
    --tab-hover-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  }

  .el-tabs {
    border-bottom: none;
  }

  .el-tabs__header {
    justify-content: center;
    padding-bottom: 15px;
  }

  .el-tabs__item {
    font-size: 16px;
    font-weight: 500;
    padding: 12px 24px;
    border-radius: 24px;
    margin: 0 8px;
    transition: all 0.3s cubic-bezier(0.25, 0.46, 0.45, 0.94);
    position: relative;
    color: #6c757d;
    box-shadow: inset 0 0 0 2px transparent;
  }

  .el-tabs__item:before {
    margin-right: 8px;
    font-size: 18px;
  }

  .el-tabs__item.praise-tab:before {
    content: "👍";
    color: var(--tab-active-color);
  }

  .el-tabs__item.improve-tab:before {
    content: "💡";
    color: var(--tab-improve-color);
  }

  .el-tabs__item.is-active {
    color: white;
    box-shadow: 0 4px 16px rgba(0, 0, 0, 0.12);
  }

  .el-tabs__item.is-active.praise-tab {
    background: linear-gradient(135deg, var(--tab-active-color) 0%, #2ecc71 100%);
  }

  .el-tabs__item.is-active.improve-tab {
    background: linear-gradient(135deg, var(--tab-improve-color) 0%, #f39c12 100%);
  }

  .el-tabs__item:hover:not(.is-active) {
    transform: scale(1.02);
    box-shadow: var(--tab-hover-shadow);
  }

  /* 内容区域 */
  .content-container {
    min-height: 400px;
  }

  .grid-section {
    padding: 24px;
    background: #fff;
    border-radius: 16px;
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
  }

  /* 过渡动画 */
  .slide-in-up-enter-active,
  .slide-in-up-leave-active {
    transition: all 0.3s ease-out;
  }

  .slide-in-up-enter-from,
  .slide-in-up-leave-to {
    opacity: 0;
    transform: translateY(20px);
  }

  .section-title {
    font-size: 20px;
    font-weight: 600;
    color: white;
    padding: 8px 20px;
    border-radius: 20px;
    display: inline-block;
    margin: 0 0 24px 0;
  }

  .praise .section-title {
    background: var(--tab-active-color);
  }

  .improve .section-title {
    background: var(--tab-improve-color);
  }

  /* 评价项卡片 */
  .grid-items {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
    gap: 24px;
  }

  .grid-item {
    position: relative;
    /* 解决定位错位 */
    text-align: center;
    padding: 24px 16px;
    border-radius: 12px;
    transition: all 0.3s ease;
    background: #f8f9fa;
  }

  .grid-item:hover {
    transform: translateY(-8px);
    box-shadow: 0 12px 32px rgba(0, 0, 0, 0.12);
  }

  .item-badge {
    position: absolute;
    right: 16px;
    top: 16px;
    width: 32px;
    height: 32px;
    border-radius: 50%;
    background: #ff4757;
    color: white;
    font-size: 14px;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .item-icon {
    height: 60px;
    margin: 16px 0;
  }

  .item-title {
    font-size: 16px;
    color: #333;
    margin: 8px 0;
    min-height: 48px;
  }

  .item-actions {
    margin-top: 16px;
    opacity: 0;
    transition: opacity 0.3s ease;
  }

  .grid-item:hover .item-actions {
    opacity: 1;
  }

  /* 对话框样式 */
  .avatar-uploader {
    width: 100px;
    height: 100px;
    border: 2px dashed #e0e0e0;
    border-radius: 8px;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 0 auto;
  }

  .avatar-uploader:hover {
    border-color: var(--tab-active-color);
  }

  .avatar-uploader-icon {
    font-size: 32px;
    color: #999;
  }

  .avatar {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 6px;
  }

  /* 图标颜色 */
  .el-icon {
    color: var(--tab-active-color);
  }

  .improve .el-icon {
    color: var(--tab-improve-color);
  }
</style>
