package com.z.system.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.z.common.core.domain.AjaxResult;
import com.z.common.core.domain.entity.SysDept;
import com.z.common.core.domain.entity.SysUser;
import com.z.common.core.domain.model.LoginUser;
import com.z.common.exception.ServiceException;
import com.z.common.exception.user.UserPasswordNotMatchException;
import com.z.common.utils.SecurityUtils;
import com.z.common.utils.UserToken;
import com.z.common.utils.UserTokenManager;
import com.z.system.domain.SysStudent;
import com.z.system.mapper.*;
import com.z.system.service.ISysDeptService;
import com.z.system.service.ISysStudentService;
import com.z.system.service.IWxUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户 业务层处理
 *
 * @author ruoyi
 */
@Service
public class WxUserServiceImpl implements IWxUserService {
    private static final Logger log = LoggerFactory.getLogger(WxUserServiceImpl.class);

    @Autowired
    private WxUserMapper userMapper;
    @Autowired
    private UserTokenManager manager;
    @Autowired
    protected Validator validator;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private ISysStudentService studentService;

    /**
     * 通过用户名查询用户
     *
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByPhone(String phone) {
        return userMapper.selectUserByPhone(phone);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }


    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUser(SysUser user) {
        return userMapper.updateUser(user);
    }


    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserStatus(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserProfile(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar   头像地址
     * @return 结果
     */
    @Override
    public boolean updateUserAvatar(String userName, String avatar) {
        return userMapper.updateUserAvatar(userName, avatar) > 0;
    }

    /**
     * 重置用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetPwd(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public int resetUserPwd(String userName, String password) {
        return userMapper.resetUserPwd(userName, password);
    }


    @Override
    public AjaxResult getInfo() {
        Long userId = SecurityUtils.getLoginUser().getUserId();
        SysUser user = selectUserById(userId);
        return AjaxResult.success(user);
    }

    @Override
    public AjaxResult updatePw(SysUser user) {
        return AjaxResult.success(resetUserPwd(user.getUserName(), user.getPassword()));
    }

    @Override
    public AjaxResult getStudentInfo(SysStudent student) {
        return AjaxResult.success(studentService.selectSysStudentList(student));
    }


}
