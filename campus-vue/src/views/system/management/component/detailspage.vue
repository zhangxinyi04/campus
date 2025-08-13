<template>
  <div class="activity-detail-container">
    <div class="header-section">
      <h1 class="activity-title">{{ opendata.eventName }}</h1>
    </div>

    <div class="grid-layout">
      <!-- 左侧信息区 -->
      <div class="info-card">
        <div class="info-item-group">
          <div class="info-item">
            <span class="icon">📅</span>
            <div>
              <p class="label">活动时间</p>
              <p>{{opendata.startTime}}<br>{{ opendata.endTime }}</p>
            </div>
          </div>

          <div class="info-item">
            <span class="icon">🏷️</span>
            <div>
              <p class="label">活动类型</p>
              <div class="tag-group">
                <span class="type-tag">{{ levelTypeMap[opendata.rank] }}</span>
                <span class="type-tag">{{ leveleventType[opendata.eventType] }}</span>
                <span class="type-tag">{{ activityTypeMap[opendata.eventTag] }}</span>
              </div>
              <div class="tag-group" style="margin-top: 10px;">
                <span class="type-tag">{{ eventTagTypeMap[opendata.eventTagType] }}</span>
              </div>
            </div>
          </div>

          <div class="info-item">
            <span class="icon">👥</span>
            <div>
              <p class="label">参与班级</p>
              <p v-for="(item,index) in opendata.depts" :key="index" style="float: left;">
                {{item.deptName}}、
              </p>
            </div>
          </div>
        </div>

        <div class="divider"></div>

        <div class="info-item-group">
          <div class="info-item">
            <span class="icon">📝</span>
            <div>
              <p class="label">活动类别</p>
              <p>{{ categoryMap[opendata.eventCategory] }}</p>
              <p v-if="opendata.eventCategory === 0" class="sub-info">
                打卡周期：{{ cycleMap[opendata.cycle] }}
              </p>
            </div>
          </div>

          <div class="info-item">
            <span class="icon">⚙️</span>
            <div>
              <p class="label">活动设置</p>
              <div class="status-tags">
                <p>活动加分： <span class="status-tag active">{{ opendata.mark }}分</span></p>
                <p>是否需要上传记录： <span class="status-tag active">{{ opendata.records == 0 ? '否' : '是' }}</span></p>
                <p>是否可见：<span class="status-tag active">{{ opendata.visible == 0 ? '否' : '是' }}</span> </p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 右侧媒体区 -->
      <div class="media-card">
        <div class="gallery-section">
          <h3>活动图片</h3>
          <div class="image-grid">
            <img v-for="(img, index) in opendata.eventPic" :key="index" :src="baseUrl+img" class="activity-image"
              alt="活动图片" />
          </div>
        </div>

        <div class="attachment-section">
          <h3>活动附件</h3>
          <button class="download-btn" @click="viewAttachment">
            📎 查看附件
          </button>
        </div>

        <div class="medal-section" v-if="opendata.eventMedal">
          <h3>活动奖章</h3>
          <img :src="baseUrl+opendata.eventMedal.url" class="medal-image" alt="活动奖章" />
        </div>
      </div>
    </div>

    <!-- 详情描述区 -->
    <div class="description-card">
      <div class="desc-section">
        <h3>活动简介</h3>
        <p class="desc-content">{{ opendata.eventIntro }}</p>
      </div>

      <div class="desc-section">
        <h3>注意事项</h3>
        <p class="desc-content">{{ opendata.precautions }}</p>
      </div>
    </div>
  </div>
</template>

<script>
  import {
    getDocument
  } from "@/api/system/management"
  export default {
    props: [
      'openid'
    ],
    data() {
      return {
        baseUrl: process.env.VUE_APP_BASE_API,
        opendata: [],
        eventTagTypeMap:{
          0:'品德发展',
          1:'学业发展',
          2:'身心发展',
          3:'艺术素养',
          4:'劳动与社会实践'
        },
        levelTypeMap: {
          0: '校级',
          1: '班级'
        },
        activityTypeMap: {
          0: '学科活动',
          1: '跨学科活动'
        },
        categoryMap: {
          0: '打卡类',
          1: '记录类'
        },
        cycleMap: {
          0: '每日',
          1: '每周',
          2: '每月'
        },
        leveleventType: {
          "0": "爱党爱国",
          "1": "队团活动",
          "2": "爱护公物",
          "3": "责任担当",
          "4": "文明朴素",
          "5": "尊老爱幼",
          "6": "自立勤快",
          "7": "学习方法",
          "8": "发明创造",
          "9": "问题解决",
          "10": "阅读习惯",
          "11": "动手动脑",
          "12": "卫生健康与安全",
          "13": "意志坚强",
          "14": "积极阳光",
          "15": "人际关系",
          "16": "艺术实践",
          "17": "劳动观念",
          "18": "家庭劳动",
          "19": "环保意识"
        }
      }
    },
    mounted() {
      // console.log(this.openid)
      this.getDocument(this.openid)

    },
    methods: {
      viewAttachment() {
        var fileUrl = this.baseUrl + this.opendata.attachments
        window.open(fileUrl, '_blank');
        // 查看附件逻辑
      },
      getDocument(openid) {
        getDocument(openid).then(response => {
          this.opendata = response.data //详情
          this.opendata.eventPic = this.opendata.eventPic.split(',')
          console.log(this.opendata, ' this.opendata')
        })
      },

    }
  }
</script>

<style scoped>
  .activity-detail-container {
    max-width: 1200px;
    margin: 2rem auto;
    padding: 0 1rem;
  }

  .header-section {
    display: flex;
    align-items: center;
    gap: 1.5rem;
    margin-bottom: 2rem;
  }

  .activity-title {
    font-size: 2.2rem;
    color: #2c3e50;
    margin: 0;
  }

  .score-badge {
    background: #3498db;
    color: white;
    padding: 0.5rem 1.2rem;
    border-radius: 20px;
    font-weight: bold;
  }

  .grid-layout {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 2rem;
    margin-bottom: 2rem;
  }

  .info-card,
  .media-card {
    background: white;
    border-radius: 12px;
    padding: 1.5rem;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
  }

  .info-item-group {
    display: grid;
    gap: 1.5rem;
  }

  .info-item {
    display: flex;
    gap: 1rem;
    align-items: flex-start;
  }

  .icon {
    font-size: 1.4rem;
    margin-top: 0.3rem;
  }

  .label {
    color: #7f8c8d;
    margin: 0 0 0.3rem 0;
    font-size: 0.9rem;
  }

  .tag-group {
    display: flex;
    gap: 0.5rem;
  }

  .type-tag {
    background: #ecf0f1;
    padding: 0.3rem 0.8rem;
    border-radius: 8px;
    font-size: 0.9rem;
  }

  .status-tag {
    background: #f1f2f6;
    padding: 0.3rem 0.8rem;
    border-radius: 6px;
    font-size: 0.85rem;
    margin-right: 20px;
    margin-bottom: 5px;
    /* float: left; */
  }

  .status-tag.active {
    background: #2ecc71;
    color: white;
  }

  .divider {
    height: 1px;
    background: #eee;
    margin: 1.5rem 0;
  }

  .image-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
    gap: 1rem;
    margin-top: 1rem;
  }

  .activity-image {
    width: 100%;
    height: 150px;
    object-fit: cover;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }

  .download-btn {
    background: #3498db;
    color: white;
    border: none;
    padding: 0.8rem 1.5rem;
    border-radius: 8px;
    cursor: pointer;
    transition: all 0.2s;
  }

  .download-btn:hover {
    background: #2980b9;
  }

  .medal-section {
    margin-top: 2rem;
  }

  .medal-image {
    max-width: 50px;
    height: auto;
    margin-top: 1rem;
  }

  .description-card {
    background: white;
    border-radius: 12px;
    padding: 2rem;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
  }

  .desc-section {
    margin-bottom: 2rem;
  }

  .desc-section h3 {
    color: #2c3e50;
    margin-top: 0;
  }

  .desc-content {
    line-height: 1.6;
    color: #555;
    white-space: pre-wrap;
  }

  @media (max-width: 768px) {
    .grid-layout {
      grid-template-columns: 1fr;
    }

    .header-section {
      flex-direction: column;
      align-items: flex-start;
    }
  }
</style>
