<template>
  <div :class="classObj" class="app-wrapper" :style="{'--current-color': theme}">
    <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside" />
    <sidebar v-if="!sidebar.hide" class="sidebar-container" />
    <div :class="{hasTagsView:needTagsView,sidebarHide:sidebar.hide}" class="main-container">
      <div :class="{'fixed-header':fixedHeader}">
        <navbar />
        <tags-view v-if="needTagsView" />
      </div>
      <app-main />
      <div class="Floating"
           :style="floatingStyle"
           v-if="$store.state.Floating.userIds"
           @mousedown="startDrag">
        <div class="floating-header">
          点评学生
          <div class="header-controls">
            <span class="btn-close" @click="closeFloating">×</span>
          </div>
        </div>
        <div class="floating-content">
          <FloatingWindow></FloatingWindow>
        </div>
        <!-- 调整大小手柄 -->
        <div class="resize-handle top" @mousedown="startResize($event, 'top')"></div>
        <div class="resize-handle right" @mousedown="startResize($event, 'right')"></div>
        <div class="resize-handle bottom" @mousedown="startResize($event, 'bottom')"></div>
        <div class="resize-handle left" @mousedown="startResize($event, 'left')"></div>
        <div class="resize-handle top-left" @mousedown="startResize($event, 'top-left')"></div>
        <div class="resize-handle top-right" @mousedown="startResize($event, 'top-right')"></div>
        <div class="resize-handle bottom-left" @mousedown="startResize($event, 'bottom-left')"></div>
        <div class="resize-handle bottom-right" @mousedown="startResize($event, 'bottom-right')"></div>
      </div>
      <right-panel>
        <settings />
      </right-panel>
    </div>
  </div>
</template>

<script>
import RightPanel from '@/components/RightPanel'
import { AppMain, Navbar, Settings, Sidebar, TagsView } from './components'
import ResizeMixin from './mixin/ResizeHandler'
import { mapState } from 'vuex'
import variables from '@/assets/styles/variables.scss'
import FloatingWindow from '@/views/Studentevaluation/FloatingWindow.vue'

export default {
  name: 'Layout',
  components: {
    AppMain,
    Navbar,
    RightPanel,
    Settings,
    Sidebar,
    TagsView,
    FloatingWindow
  },
  data() {
    return {
      floating: {
        top: '20px',
        left: '20px',
        width: '600px',
        height: '400px',
        isDragging: false,
        isResizing: false,
        direction: null,
        startX: 0,
        startY: 0,
        startTop: 0,
        startLeft: 0,
        startWidth: 0,
        startHeight: 0
      }
    }
  },
  mixins: [ResizeMixin],
  computed: {
    floatingStyle() {
      return {
        top: this.floating.top,
        left: this.floating.left,
        width: this.floating.width,
        height: this.floating.height
      }
    },
    ...mapState({
      theme: state => state.settings.theme,
      sideTheme: state => state.settings.sideTheme,
      sidebar: state => state.app.sidebar,
      device: state => state.app.device,
      needTagsView: state => state.settings.tagsView,
      fixedHeader: state => state.settings.fixedHeader
    }),
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === 'mobile'
      }
    },
    variables() {
      return variables;
    }
  },
  methods: {
    startDrag(e) {
      if (e.target.closest('.resize-handle')) return
      this.floating.isDragging = true
      this.floating.startX = e.clientX
      this.floating.startY = e.clientY
      this.floating.startTop = parseFloat(this.floating.top) || 0
      this.floating.startLeft = parseFloat(this.floating.left) || 0

      document.addEventListener('mousemove', this.onDrag)
      document.addEventListener('mouseup', this.stopDrag)
    },

    onDrag(e) {
      if (!this.floating.isDragging) return
      const deltaX = e.clientX - this.floating.startX
      const deltaY = e.clientY - this.floating.startY

      let newTop = this.floating.startTop + deltaY
      let newLeft = this.floating.startLeft + deltaX

      // 限制在视口范围内
      newTop = Math.max(0, Math.min(newTop, window.innerHeight - 30)) // 保留最小可见区域
      newLeft = Math.max(0, Math.min(newLeft, window.innerWidth - 30))

      this.floating.top = `${newTop}px`
      this.floating.left = `${newLeft}px`
    },

    startResize(e, direction) {
      e.stopPropagation()
      this.floating.isResizing = true
      this.floating.direction = direction
      this.floating.startX = e.clientX
      this.floating.startY = e.clientY
      this.floating.startTop = parseFloat(this.floating.top)
      this.floating.startLeft = parseFloat(this.floating.left)
      this.floating.startWidth = parseFloat(this.floating.width)
      this.floating.startHeight = parseFloat(this.floating.height)

      document.addEventListener('mousemove', this.onResize)
      document.addEventListener('mouseup', this.stopResize)
    },

    onResize(e) {
      if (!this.floating.isResizing) return
      const deltaX = e.clientX - this.floating.startX
      const deltaY = e.clientY - this.floating.startY

      let newTop = this.floating.startTop
      let newLeft = this.floating.startLeft
      let newWidth = this.floating.startWidth
      let newHeight = this.floating.startHeight

      const minSize = 200 // 最小尺寸限制

      switch (this.floating.direction) {
        case 'top':
          newHeight = Math.max(minSize, this.floating.startHeight - deltaY)
          newTop = Math.max(0, this.floating.startTop + deltaY)
          break
        case 'bottom':
          newHeight = Math.max(minSize, this.floating.startHeight + deltaY)
          break
        case 'left':
          newWidth = Math.max(minSize, this.floating.startWidth - deltaX)
          newLeft = Math.max(0, this.floating.startLeft + deltaX)
          break
        case 'right':
          newWidth = Math.max(minSize, this.floating.startWidth + deltaX)
          break
        case 'top-left':
          newWidth = Math.max(minSize, this.floating.startWidth - deltaX)
          newLeft = Math.max(0, this.floating.startLeft + deltaX)
          newHeight = Math.max(minSize, this.floating.startHeight - deltaY)
          newTop = Math.max(0, this.floating.startTop + deltaY)
          break
        case 'top-right':
          newWidth = Math.max(minSize, this.floating.startWidth + deltaX)
          newHeight = Math.max(minSize, this.floating.startHeight - deltaY)
          newTop = Math.max(0, this.floating.startTop + deltaY)
          break
        case 'bottom-left':
          newWidth = Math.max(minSize, this.floating.startWidth - deltaX)
          newLeft = Math.max(0, this.floating.startLeft + deltaX)
          newHeight = Math.max(minSize, this.floating.startHeight + deltaY)
          break
        case 'bottom-right':
          newWidth = Math.max(minSize, this.floating.startWidth + deltaX)
          newHeight = Math.max(minSize, this.floating.startHeight + deltaY)
          break
      }

      // 应用边界限制
      const maxWidth = window.innerWidth - newLeft
      const maxHeight = window.innerHeight - newTop
      newWidth = Math.min(newWidth, maxWidth)
      newHeight = Math.min(newHeight, maxHeight)

      this.floating.top = `${newTop}px`
      this.floating.left = `${newLeft}px`
      this.floating.width = `${newWidth}px`
      this.floating.height = `${newHeight}px`
    },

    stopResize() {
      this.floating.isResizing = false
      this.floating.direction = null
      document.removeEventListener('mousemove', this.onResize)
      document.removeEventListener('mouseup', this.stopResize)
    },

    stopDrag() {
      this.floating.isDragging = false
      document.removeEventListener('mousemove', this.onDrag)
      document.removeEventListener('mouseup', this.stopDrag)
    },

    closeFloating() {
      this.$store.commit('Floating/increment', null)
    },

    handleClickOutside() {
      this.$store.dispatch('app/closeSideBar', { withoutAnimation: false })
    }
  }
}
</script>

<style lang="scss" scoped>
@import "~@/assets/styles/mixin.scss";
@import "~@/assets/styles/variables.scss";

.Floating {
  position: fixed;
  background: white;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
  transition: all 0.2s ease;
  min-width: 200px;
  min-height: 150px;
  overflow: hidden;
  z-index: 100000;

  .floating-header {
    padding: 12px 16px;
    background: #f3f4f6;
    border-bottom: 1px solid #e5e7eb;
    cursor: move;
    position: relative;
    user-select: none;

    .header-controls {
      position: absolute;
      right: 8px;
      top: 50%;
      transform: translateY(-50%);
      display: flex;
      align-items: center;
      gap: 8px;
    }

    .btn-close {
      cursor: pointer;
      padding: 2px 8px;
      font-size: 16px;
      color: #666;
      transition: all 0.2s;

      &:hover {
        color: #333;
        background: #e5e5e5;
        border-radius: 4px;
      }
    }
  }

  .floating-content {
    padding: 16px;
    height: calc(100% - 45px);
    overflow: auto;
  }

  .resize-handle {
    position: absolute;
    background: transparent;
    z-index: 10;

    &.top {
      top: 0;
      left: 0;
      right: 0;
      height: 4px;
      cursor: ns-resize;
    }

    &.right {
      top: 0;
      right: 0;
      bottom: 0;
      width: 4px;
      cursor: ew-resize;
    }

    &.bottom {
      bottom: 0;
      left: 0;
      right: 0;
      height: 4px;
      cursor: ns-resize;
    }

    &.left {
      top: 0;
      left: 0;
      bottom: 0;
      width: 4px;
      cursor: ew-resize;
    }

    &.top-left {
      top: 0;
      left: 0;
      width: 8px;
      height: 8px;
      cursor: nwse-resize;
    }

    &.top-right {
      top: 0;
      right: 0;
      width: 8px;
      height: 8px;
      cursor: nesw-resize;
    }

    &.bottom-left {
      bottom: 0;
      left: 0;
      width: 8px;
      height: 8px;
      cursor: nesw-resize;
    }

    &.bottom-right {
      bottom: 0;
      right: 0;
      width: 8px;
      height: 8px;
      cursor: nwse-resize;
    }

    &:hover {
      background: rgba(59, 130, 246, 0.2);
    }
  }
}

/* 其他原有样式保持不变 */
.app-wrapper {
  @include clearfix;
  position: relative;
  height: 100%;
  width: 100%;

  &.mobile.openSidebar {
    position: fixed;
    top: 0;
  }
}

.drawer-bg {
  background: #000;
  opacity: 0.3;
  width: 100%;
  top: 0;
  height: 100%;
  position: absolute;
  z-index: 999;
}

.fixed-header {
  position: fixed;
  top: 0;
  right: 0;
  z-index: 9;
  width: calc(100% - #{$base-sidebar-width});
  transition: width 0.28s;
}

.hideSidebar .fixed-header {
  width: calc(100% - 54px);
}

.sidebarHide .fixed-header {
  width: 100%;
}

.mobile .fixed-header {
  width: 100%;
}
</style>
