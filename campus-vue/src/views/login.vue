<template>
  <div class="login-container">
    <div class="login-card">
      <div class="school-brand">
        <div class="campus-icon">
          <!-- <svg-icon icon-class="campus" class="icon" /> -->
          <img  src="../assets/logo/logo2.jpg" class="icon" style="width: 100%;height: 100%;border-radius: 50%;"/>
        </div>
        <h2 class="system-name">昌平区回龙观第二小学</h2>
      </div>

      <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
        <el-form-item prop="name">
          <el-input
            v-model="loginForm.name"
            type="text"
            placeholder="请输入账号"
            class="custom-input"
            style="padding-left: 5px;"
          >
            <template #prefix>
              <svg-icon icon-class="user" class="input-icon" />
            </template>
          </el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="请输入登录密码"
            class="custom-input"
            show-password
              style="padding-left: 5px;"
            @keyup.enter.native="handleLogin"
          >
            <template #prefix>
              <svg-icon icon-class="password" class="input-icon" />
            </template>
          </el-input>
        </el-form-item>

        <div class="flex-between">
          <el-checkbox v-model="loginForm.rememberMe">记住账号</el-checkbox>
          <router-link v-if="register" to="/register" class="link-btn">
            新用户注册
          </router-link>
        </div>

        <el-button
          type="primary"
          class="login-btn"
          :loading="loading"
          @click.native.prevent="handleLogin"
        >
          {{ loading ? '登录中...' : '进入平台' }}
        </el-button>
      </el-form>

      <div class="decorative-line"></div>
    </div>

    <div class="bg-blobs">
      <div class="blob blue"></div>
      <div class="blob green"></div>
    </div>
  </div>
</template>


<script>
import { getCodeImg } from "@/api/login";
import Cookies from "js-cookie";
import { encrypt, decrypt } from '@/utils/jsencrypt'

export default {
  name: "Login",
  data() {
    return {
      codeUrl: "",
      loginForm: {
        name: "",
        password: "",
        rememberMe: false,
        // code: "",
        uuid: ""
      },
      loginRules: {
        name: [
          { required: true, trigger: "blur", message: "请输入您的账号" }
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" }
        ],
        // code: [{ required: true, trigger: "change", message: "请输入验证码" }]
      },
      loading: false,
      // 验证码开关
      captchaEnabled: true,
      // 注册开关
      register: false,
      redirect: undefined
    };
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  created() {
    // this.getCode();
    this.getCookie();
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled;
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.loginForm.uuid = res.uuid;
        }
      });
    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.name : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;

          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.name, { expires: 30 });
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 });
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove('rememberMe');
          }
          this.loginForm.username = this.loginForm.name+'-00'
          this.$store.dispatch("Login", this.loginForm).then(() => {
            // this.$router.push({ path: this.redirect || "/" }).catch(()=>{});
            this.$router.push({ path: "/index" }).catch(()=>{});
          }).catch(() => {
            this.loading = false;
            if (this.captchaEnabled) {
              // this.getCode();
            }
          });
        }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: url('../assets/bj.jpg') no-repeat;
  background-size: 100% 100%;
  position: relative;
  overflow: hidden;
}

.login-card {
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(8px);
  padding: 2.5rem 3rem;
  border-radius: 20px;
  box-shadow: 0 8px 24px rgba(255, 122, 69, 0.15);
  width: 420px;
  z-index: 1;
  position: relative;
  border: 1px solid rgba(255, 255, 255, 0.4);
  opacity: 1;
}

.school-brand {
  text-align: center;
  margin-bottom: 2rem;

  .campus-icon {
    // background: linear-gradient(45deg, #409EFF, #67C23A);
    width: 60px;
    height: 60px;
    border-radius: 16px;
    margin: 0 auto 1rem;
    display: flex;
    align-items: center;
    justify-content: center;

    .icon {
      width: 36px;
      height: 36px;
      color: white;
    }
  }

  .system-name {
    color: #333;
    font-size: 1.5rem;
    margin: 0;
    font-weight: 600;
    letter-spacing: 1px;
  }
}

.custom-input {
  :deep(.el-input__inner) {
    height: 44px;
    border-radius: 12px;
    padding-left: 40px;
    border: 1px solid #eee;
    transition: all 0.3s ease;

    &:focus {
      border-color: #ff7a45;
      box-shadow: 0 2px 8px rgba(255, 122, 69, 0.2);
    }
  }
}

.input-icon {
  width: 18px;
  height: 18px;
  margin-left: 8px;
  color: #ff7a45;
}

.flex-between {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 1rem 0;
}

.link-btn {
  color: #409EFF;
  font-size: 0.9em;
  text-decoration: none;
  transition: all 0.2s;

  &:hover {
    color: #67C23A;
    transform: translateY(-1px);
  }
}

.login-btn {
  width: 100%;
  height: 44px;
  border-radius: 12px;
  font-size: 1rem;
  letter-spacing: 1px;
  background: linear-gradient(45deg, #ff7a45, #ffb980);
  border: none;
  transition: all 0.3s ease;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 5px 15px rgba(255, 122, 69, 0.3);
  }
}

.decorative-line {
  height: 4px;
  background: linear-gradient(90deg, transparent, #ff7a45, #ffb980, transparent);
  margin: 2rem -3rem 0;
  opacity: 0.3;
}

.bg-blobs {
  .blob {
    position: absolute;
    border-radius: 50%;
    filter: blur(60px);
    opacity: 0.1;

    &.blue {
      background: #ff7a45;
      width: 300px;
      height: 300px;
      top: 20%;
      left: 20%;
    }

    &.green {
      background: #ffb980;
      width: 250px;
      height: 250px;
      bottom: 30%;
      right: 20%;
    }
  }
}

// 复选框样式（穿透Element UI）
:deep(.el-checkbox__input.is-checked .el-checkbox__inner) {
  background-color: #ff7a45;
  border-color: #ff7a45;
}

@media (max-width: 768px) {
  .login-card {
    width: 90%;
    padding: 2rem;
  }
}
</style>
