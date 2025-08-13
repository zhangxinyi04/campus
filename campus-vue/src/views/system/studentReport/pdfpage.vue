<template>
  <div class="export-page">
    <!-- 导出按钮 -->
    <div class="dialog-footer">
      <el-button type="primary" @click="exportPage">下 载</el-button>
    </div>

    <!-- 要导出的内容区域 -->
    <div ref="content" class="pdf-container">
      <!-- 整个报告容器，白底、A4 近似宽度 -->
      <!-- 标题 -->
      <div class="report-header">
        <h1>学生成长纪实报告</h1>
      </div>

      <!-- 学生信息 -->
      <section class="section-block">
        <div class="section-title">
          <span>学生信息</span>
        </div>
        <div class="student-info">
          <div class="info-row"><span class="label">姓名：</span><span>{{ arr.studentInfo.studentName }}</span></div>
          <div class="info-row"><span class="label">性别：</span><span>{{ arr.studentInfo.sex === 1 ? '男' : '女' }}</span>
          </div>
          <div class="info-row"><span class="label">学分：</span><span>{{ arr.studentInfo.group }}</span></div>
          <div class="info-row"><span class="label">班级：</span><span>{{ arr.studentInfo.dept.deptName }}</span></div>
        </div>
      </section>

      <!-- 奖章展示 -->
      <section class="section-block">
        <div class="section-title">
          <span>活动奖章</span>
        </div>
        <div class="medal-list">
          <div v-for="(medal, idx) in arr.studentMedalList" :key="idx" class="medal-item">
            <img :src="baseUrl+medal.url" alt="奖章图片" class="medal-image" />
            <div class="medal-info">
              <div class="medal-name">{{ medal.eventMedalName }}✖️{{ medal.num }}</div>
              <!-- <div class="medal-count">数量：{{ medal.num }}</div> -->
            </div>
          </div>
        </div>
      </section>

      <!-- 体育成绩 -->
      <section class="section-block">
        <div class="section-title">
          <span>体育成绩</span>
        </div>
        <div class="scores-block">
          <table class="score-table">
            <thead>
              <tr>
                <th>总分</th>
                <th>等级</th>
                <th>测试成绩</th>
                <th>测试等级</th>
                <th>附加分数</th>
                <th>身高</th>
                <th>体重</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(peItem, idx) in arr.studentPeScore" :key="idx">
                <td>{{ peItem.score }}</td>
                <td>
                  <el-tag v-if="peItem.level == 0">优秀</el-tag>
                  <el-tag v-if="peItem.level == 1" type="success">良好</el-tag>
                  <el-tag v-if="peItem.level == 2" type="info">及格</el-tag>
                  <el-tag v-if="peItem.level == 3" type="danger">不及格</el-tag>
                </td>
                <td>{{ peItem.testScore }}</td>
                <td>
                  <el-tag v-if="peItem.testLevel == 0">优秀</el-tag>
                  <el-tag v-if="peItem.testLevel == 1" type="success">良好</el-tag>
                  <el-tag v-if="peItem.testLevel == 2" type="info">及格</el-tag>
                  <el-tag v-if="peItem.testLevel == 3" type="danger">不及格</el-tag>
                </td>
                <td>{{ peItem.appendScore }}</td>
                <td>{{ peItem.height }}</td>
                <td>{{ peItem.weight }}</td>
              </tr>
            </tbody>
          </table>
          <table class="score-table" style="margin-top: 15px;">
            <thead>
              <tr>
                <th>肺活量</th>
                <th>身高体重指数</th>
                <th>50米跑</th>
                <th>体前屈</th>
                <th>跳绳</th>
                <th>仰卧起坐</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(peItem, idx) in arr.studentPeScore" :key="idx">
                <td>{{ peItem.vitalCapacity }}</td>
                <td>{{ peItem.bmi }}</td>
                <td>{{ peItem.run }}</td>
                <td>{{ peItem.sitReach }}</td>
                <td>{{ peItem.jump }}</td>
                <td>{{ peItem.sitUp }}</td>
              </tr>
            </tbody>
          </table>

        </div>
      </section>

      <!-- 学科成绩 -->
      <section class="section-block">
        <div class="section-title">
          <span>学科成绩</span>
        </div>
        <div class="scores-block">
          <table class="score-table">
            <thead>
              <tr>
                <th>语文</th>
                <th>数学</th>
                <th>英语</th>
                <th>美术</th>
                <th>音乐</th>
                <th>科学</th>
                <th>书法</th>
                <th>舞蹈</th>
                <th>阅读</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(subjItem, idx) in arr.studentScores" :key="idx">
                <td>{{ subjItem.chinese }}</td>
                <td>{{ subjItem.math }}</td>
                <td>{{ subjItem.english }}</td>
                <td>{{ subjItem.art }}</td>
                <td>{{ subjItem.music }}</td>
                <td>{{ subjItem.science }}</td>
                <td>{{ subjItem.calligraphy }}</td>
                <td>{{ subjItem.dance }}</td>
                <td>{{ subjItem.readbook }}</td>
              </tr>
            </tbody>
          </table>
          <table class="score-table" style="margin-top: 15px;">
            <thead>
              <tr>
                <th>劳动与技术</th>
                <th>道德与法治</th>
                <th>心理健康</th>
                <th>体育与健康</th>
                <th>综合实践</th>
                <th>信息科技</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(subjItem, idx) in arr.studentScores" :key="idx">
                <td>{{ subjItem.labour }}</td>
                <td>{{ subjItem.morality }}</td>
                <td>{{ subjItem.health }}</td>
                <td>{{ subjItem.sports }}</td>
                <td>{{ subjItem.practice }}</td>
                <td>{{ subjItem.computer }}</td>

              </tr>
            </tbody>
          </table>
        </div>
      </section>

      <!-- 品德发展 -->
      <section class="section-block">
        <div class="section-title">
          <span>品德发展</span>
        </div>
        <!-- 累计活动信息 -->
        <div v-if="arr.moralDevelopment   && arr.moralDevelopment  .length" class="activity-section">
          <h3>累计活动信息</h3>
          <div class="activity-grid">
            <div v-for="(activity, idx) in arr.moralDevelopment  " :key="idx" class="activity-item">
              {{ activity.eventName }}
            </div>
          </div>
        </div>
        <div v-else class="no-data">
          暂无累计活动信息
        </div>
        <!-- 写实记录 -->
        <div v-if="arr.moralRealisticRecord  && arr.moralRealisticRecord .length" class="realistic-section">
          <h3>写实记录</h3>
          <table class="score-table">
            <thead>
              <tr>
                <th>活动内容</th>
                <th>获奖等级</th>
                <th>机构级别</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(record, idx) in arr.moralRealisticRecord " :key="idx">
                <td>{{ record.content }}</td>
                <td>
                  <el-tag v-if="record.awardGrade === '0'" type="success">一等奖</el-tag>
                  <el-tag v-else-if="record.awardGrade === '1'" type="info">二等奖</el-tag>
                  <el-tag v-else-if="record.awardGrade === '2'" type="warning">三等奖</el-tag>
                  <span v-else>未评级</span>
                </td>
                <td>
                  <el-tag v-if="record.institutionRank === '0'" effect="dark">国际级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '1'" type="success" effect="dark">国家级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '2'" type="info" effect="dark">省级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '3'" type="warning" effect="dark">市级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '4'">区县级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '5'" type="info">校级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '6'" type="warning">班级</el-tag>
                  <span v-else>未评级</span>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div v-else class="no-data">
          暂无写实记录
        </div>

      </section>

      <!-- 学业发展 -->
      <section class="section-block">
        <div class="section-title">
          <span>学业发展</span>
        </div>

        <!-- 累计活动信息 -->
        <div v-if="arr.academicDevelopment && arr.academicDevelopment.length" class="activity-section">
          <h3>累计活动信息</h3>
          <div class="activity-grid">
            <div v-for="(activity, idx) in arr.academicDevelopment" :key="idx" class="activity-item">
              {{ activity.eventName }}
            </div>
          </div>
        </div>
        <div v-else class="no-data">
          暂无累计活动信息
        </div>

        <!-- 写实记录 -->
        <div v-if="arr.academicRealisticRecord && arr.academicRealisticRecord.length" class="realistic-section">
          <h3>写实记录</h3>
          <table class="score-table">
            <thead>
              <tr>
                <th>活动内容</th>
                <th>获奖等级</th>
                <th>机构级别</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(record, idx) in arr.academicRealisticRecord" :key="idx">
                <td>{{ record.content }}</td>
                <td>
                  <el-tag v-if="record.awardGrade === '0'" type="success">一等奖</el-tag>
                  <el-tag v-else-if="record.awardGrade === '1'" type="info">二等奖</el-tag>
                  <el-tag v-else-if="record.awardGrade === '2'" type="warning">三等奖</el-tag>
                  <span v-else>未评级</span>
                </td>
                <td>
                  <el-tag v-if="record.institutionRank === '0'" effect="dark">国际级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '1'" type="success" effect="dark">国家级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '2'" type="info" effect="dark">省级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '3'" type="warning" effect="dark">市级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '4'">区县级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '5'" type="info">校级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '6'" type="warning">班级</el-tag>
                  <span v-else>未评级</span>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div v-else class="no-data">
          暂无写实记录
        </div>
      </section>

      <!-- 身心发展 -->
      <section class="section-block">
        <div class="section-title">
          <span>身心发展</span>
        </div>
        <!-- 累计活动信息 -->
        <div v-if="arr.physicalAndMentalDevelopment && arr.physicalAndMentalDevelopment.length"
          class="activity-section">
          <h3>累计活动信息</h3>
          <div class="activity-grid">
            <div v-for="(activity, idx) in arr.physicalAndMentalDevelopment" :key="idx" class="activity-item">
              {{ activity.eventName }}
            </div>
          </div>
        </div>
        <div v-else class="no-data">
          暂无累计活动信息
        </div>

        <!-- 写实记录 -->
        <div v-if="arr.physicalAndMentalRealisticRecord && arr.physicalAndMentalRealisticRecord.length"
          class="realistic-section">
          <h3>写实记录</h3>
          <table class="score-table">
            <thead>
              <tr>
                <th>活动内容</th>
                <th>获奖等级</th>
                <th>机构级别</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(record, idx) in arr.physicalAndMentalRealisticRecord" :key="idx">
                <td>{{ record.content }}</td>
                <td>
                  <el-tag v-if="record.awardGrade === '0'" type="success">一等奖</el-tag>
                  <el-tag v-else-if="record.awardGrade === '1'" type="info">二等奖</el-tag>
                  <el-tag v-else-if="record.awardGrade === '2'" type="warning">三等奖</el-tag>
                  <span v-else>未评级</span>
                </td>
                <td>
                  <el-tag v-if="record.institutionRank === '0'" effect="dark">国际级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '1'" type="success" effect="dark">国家级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '2'" type="info" effect="dark">省级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '3'" type="warning" effect="dark">市级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '4'">区县级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '5'" type="info">校级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '6'" type="warning">班级</el-tag>
                  <span v-else>未评级</span>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div v-else class="no-data">
          暂无写实记录
        </div>
        <h3>课堂加分次数</h3>
        <table class="score-table">
          <thead>
            <tr>
              <th>课程名称</th>
              <th>加分次数</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(mark, idx) in arr.sysStudentMarkRecords" :key="idx">
              <td>{{ mark.courseName }}</td>
              <td>{{ mark.counts }}</td>
            </tr>
          </tbody>
        </table>

      </section>

      <!-- 艺术素养 -->
      <section class="section-block">
        <div class="section-title">
          <span>艺术素养</span>
        </div>
        <!-- 累计活动信息 -->
        <div v-if="arr.artisticLiteracy && arr.artisticLiteracy.length" class="activity-section">
          <h3>累计活动信息</h3>
          <div class="activity-grid">
            <div v-for="(activity, idx) in arr.artisticLiteracy " :key="idx" class="activity-item">
              {{ activity.eventName }}
            </div>
          </div>
        </div>
        <div v-else class="no-data">
          暂无累计活动信息
        </div>

        <!-- 写实记录 -->
        <div v-if="arr.artisticLiteracyRealisticRecord && arr.artisticLiteracyRealisticRecord.length"
          class="realistic-section">
          <h3>写实记录</h3>
          <table class="score-table">
            <thead>
              <tr>
                <th>活动内容</th>
                <th>获奖等级</th>
                <th>机构级别</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(record, idx) in arr.artisticLiteracyRealisticRecord" :key="idx">
                <td>{{ record.content }}</td>
                <td>
                  <el-tag v-if="record.awardGrade === '0'" type="success">一等奖</el-tag>
                  <el-tag v-else-if="record.awardGrade === '1'" type="info">二等奖</el-tag>
                  <el-tag v-else-if="record.awardGrade === '2'" type="warning">三等奖</el-tag>
                  <span v-else>未评级</span>
                </td>
                <td>
                  <el-tag v-if="record.institutionRank === '0'" effect="dark">国际级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '1'" type="success" effect="dark">国家级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '2'" type="info" effect="dark">省级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '3'" type="warning" effect="dark">市级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '4'">区县级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '5'" type="info">校级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '6'" type="warning">班级</el-tag>
                  <span v-else>未评级</span>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div v-else class="no-data">
          暂无写实记录
        </div>
      </section>

      <!-- 劳动与社会实践 -->
      <section class="section-block">
        <div class="section-title">
          <span>劳动与社会实践</span>
        </div>
        <!-- 累计活动信息 -->
        <div v-if="arr.laborAndSocialPractice && arr.laborAndSocialPractice.length" class="activity-section">
          <h3>累计活动信息</h3>
          <div class="activity-grid">
            <div v-for="(activity, idx) in arr.laborAndSocialPractice " :key="idx" class="activity-item">
              {{ activity.eventName }}
            </div>
          </div>
        </div>
        <div v-else class="no-data">
          暂无累计活动信息
        </div>
        <!-- 写实记录 -->
        <div v-if="arr.laborAndSocialPracticeRealisticRecord && arr.laborAndSocialPracticeRealisticRecord.length"
          class="realistic-section">
          <h3>写实记录</h3>
          <table class="score-table">
            <thead>
              <tr>
                <th>活动内容</th>
                <th>获奖等级</th>
                <th>机构级别</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(record, idx) in arr.laborAndSocialPracticeRealisticRecord" :key="idx">
                <td>{{ record.content }}</td>
                <td>
                  <el-tag v-if="record.awardGrade === '0'" type="success">一等奖</el-tag>
                  <el-tag v-else-if="record.awardGrade === '1'" type="info">二等奖</el-tag>
                  <el-tag v-else-if="record.awardGrade === '2'" type="warning">三等奖</el-tag>
                  <span v-else>未评级</span>
                </td>
                <td>
                  <el-tag v-if="record.institutionRank === '0'" effect="dark">国际级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '1'" type="success" effect="dark">国家级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '2'" type="info" effect="dark">省级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '3'" type="warning" effect="dark">市级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '4'">区县级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '5'" type="info">校级</el-tag>
                  <el-tag v-else-if="record.institutionRank === '6'" type="warning">班级</el-tag>
                  <span v-else>未评级</span>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div v-else class="no-data">
          暂无写实记录
        </div>
      </section>

      <!-- 评语 -->
      <section class="section-block">
        <div class="section-title">
          <span>评语</span>
        </div>
        <div class="comments-block">
          <div v-for="(cmt, idx) in arr.sysStudentComments" :key="idx" class="comment-item">
            <div class="comment-content"><strong>教师：</strong>{{ cmt.teacherContent }}</div>
            <div class="comment-content"><strong>家长：</strong>{{ cmt.content }}</div>
          </div>
        </div>
      </section>

      <!-- 页脚或生成日期等 -->
      <div class="report-footer">
        <span>生成日期：{{ formatDate(new Date()) }}</span>
      </div>
    </div>
  </div>
</template>

<script>
  import html2canvas from 'html2canvas';
  import jsPDF from 'jspdf';
  import {
    studentstudentReport
  } from "@/api/system/studentReport";
  export default {
    name: 'ExportPage',
    props: {
      user: {
        type: Object,
        required: true,
        default: () => ({})
      }
    },
    data() {
      return {
        baseUrl: process.env.VUE_APP_BASE_API,
        Totaldata: '',
        arr: {

        }
      };
    },
    mounted() {
      // 如果需要从接口获取真实数据，可在此调用并赋值给 arr
      this.fetchStudentReport(this.user);
    },
    methods: {
      fetchStudentReport(row) {
        studentstudentReport({
          studentId: row.studentId,
          semesterId: row.semesterId,
        }).then(response => {
          console.log(response, '11')
          this.arr = response.data;

        });
      },
      formatDate(date) {
        const y = date.getFullYear();
        const m = String(date.getMonth() + 1).padStart(2, '0');
        const d = String(date.getDate()).padStart(2, '0');
        return `${y}-${m}-${d}`;
      },
      exportPage() {
        const content = this.$refs.content;
        if (!content) return;
        html2canvas(content, {
          scale: 2, // 提高分辨率
          useCORS: true
        }).then(canvas => {
          const imgData = canvas.toDataURL('image/png');
          // A4 尺寸 210 x 297 mm，对应 px 可按 96dpi 或更高 dpi 估算，jsPDF 里以 mm 为单位
          const pdf = new jsPDF('p', 'mm', 'a4');
          const pageWidth = 210;
          const pageHeight = 297;
          const imgWidth = pageWidth;
          const imgHeight = (canvas.height * imgWidth) / canvas.width;
          let position = 0;
          if (imgHeight <= pageHeight) {
            pdf.addImage(imgData, 'PNG', 0, 0, imgWidth, imgHeight);
          } else {
            // 若内容高度超过一页，则分段绘制
            let remainingHeight = canvas.height;
            const pageCanvas = document.createElement('canvas');
            const pageCtx = pageCanvas.getContext('2d');
            while (remainingHeight > 0) {
              pageCanvas.width = canvas.width;
              pageCanvas.height = Math.min(canvas.width * (pageHeight / pageWidth), remainingHeight);
              // 从原 canvas 中截取一段
              pageCtx.clearRect(0, 0, pageCanvas.width, pageCanvas.height);
              pageCtx.drawImage(
                canvas,
                0,
                position,
                canvas.width,
                pageCanvas.height,
                0,
                0,
                pageCanvas.width,
                pageCanvas.height
              );
              const pageData = pageCanvas.toDataURL('image/png');
              pdf.addImage(pageData, 'PNG', 0, 0, imgWidth, (pageCanvas.height * imgWidth) / pageCanvas.width);
              remainingHeight -= pageCanvas.height;
              position += pageCanvas.height;
              if (remainingHeight > 0) {
                pdf.addPage();
              }
            }
          }
          pdf.save('学生报告.pdf');
        });
      }
    }
  };
</script>

<style scoped>
  .export-page {
    padding: 16px;
    background-color: #f2f2f2;
  }

  /* 导出按钮区域 */
  .dialog-footer {
    text-align: right;
    margin-bottom: 12px;
  }

  /* PDF 容器：白底、适当宽度、居中 */
  .pdf-container {
    width: 800px;
    /* 屏幕上预览宽度，可调整 */
    margin: 0 auto;
    background-color: #fff;
    padding: 24px;
    box-shadow: 0 0 8px rgba(0, 0, 0, 0.1);
    font-family: "Microsoft YaHei", Arial, sans-serif;
    color: #333;
    /* 可设置 A4 比例：高度可自适应 */
  }

  /* 头部 */
  .report-header {
    text-align: center;
    margin-bottom: 24px;
  }

  .report-header h1 {
    margin: 0;
    font-size: 24px;
    color: #2c3e50;
  }

  /* 各章节公共样式 */
  .section-block {
    margin-bottom: 24px;
  }

  .section-title {
    background-color: #3498db;
    color: #fff;
    padding: 8px 12px;
    font-size: 18px;
    border-radius: 4px;
    margin-bottom: 12px;
  }

  .section-title span {
    font-weight: bold;
  }

  /* 学生信息 */
  .student-info {
    display: flex;
    flex-wrap: wrap;
  }

  .info-row {
    width: 50%;
    margin-bottom: 8px;
  }

  .label {
    font-weight: bold;
  }

  /* 奖章展示 */
  .medal-list {
    display: flex;
    flex-wrap: wrap;
  }

  .medal-item {
    width: 120px;
    margin-right: 16px;
    margin-bottom: 16px;
    text-align: center;
  }

  .medal-image {
    width: 60px;
    height: 60px;
    object-fit: cover;
    margin-bottom: 4px;
  }

  .medal-name {
    font-size: 14px;
    font-weight: bold;
  }

  .medal-count {
    font-size: 12px;
  }

  .no-data {
    color: #999;
    font-style: italic;
  }

  /* 成绩表格 */
  .scores-block {
    /* 可在内部滚动或直接展开 */
  }

  .score-semester {
    margin-bottom: 16px;
  }

  .semester-title {
    font-weight: bold;
    margin-bottom: 8px;
  }

  .score-table {
    width: 100%;
    border-collapse: collapse;
    font-size: 14px;
  }

  .score-table th,
  .score-table td {
    border: 1px solid #ccc;
    padding: 6px 8px;
    text-align: left;
  }

  .score-table th {
    background-color: #ecf5fd;
  }

  .score-table .no-data {
    text-align: center;
    color: #999;
    padding: 12px 0;
  }

  /* 评语 */
  .comments-block {}

  .comment-item {
    margin-bottom: 12px;
  }

  .comment-content {
    margin-left: 12px;
    margin-bottom: 4px;
  }

  /* 页脚 */
  .report-footer {
    text-align: right;
    font-size: 12px;
    color: #666;
    margin-top: 24px;
  }

  /* 添加在 style 部分 */
  .activity-section {
    margin-bottom: 20px;
  }

  .activity-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
    gap: 12px;
    margin-top: 10px;
  }

  .activity-item {
    padding: 10px;
    border: 1px solid #eaeaea;
    border-radius: 4px;
    text-align: center;
    background-color: #f8f8f8;
  }

  .realistic-section {
    margin-top: 20px;
  }

  .no-data {
    text-align: center;
    padding: 20px;
    color: #999;
    font-style: italic;
  }
</style>
