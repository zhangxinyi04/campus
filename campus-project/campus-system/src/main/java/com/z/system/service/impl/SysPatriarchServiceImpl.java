//package com.z.system.service.impl;
//
//import java.util.List;
//
//import com.z.common.core.domain.entity.SysUser;
//import com.z.common.utils.DateUtils;
//import com.z.common.utils.SecurityUtils;
//import com.z.common.utils.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import com.z.system.mapper.SysPatriarchMapper;
//import com.z.system.domain.SysPatriarch;
//import com.z.system.service.ISysPatriarchService;
//
///**
// * 家长信息Service业务层处理
// *
// * @author z
// * @date 2025-03-08
// */
//@Service
//public class SysPatriarchServiceImpl implements ISysPatriarchService {
//    @Autowired
//    private SysPatriarchMapper sysPatriarchMapper;
//
//    /**
//     * 查询家长信息
//     *
//     * @param patriarchId 家长信息主键
//     * @return 家长信息
//     */
//    @Override
//    public SysPatriarch selectSysPatriarchByPatriarchId(Long patriarchId) {
//        return sysPatriarchMapper.selectSysPatriarchByPatriarchId(patriarchId);
//    }
//
//    /**
//     * 查询家长信息列表
//     *
//     * @param sysPatriarch 家长信息
//     * @return 家长信息
//     */
//    @Override
//    public List<SysUser> selectSysPatriarchList(SysUser user) {
//        return sysPatriarchMapper.selectSysPatriarchList(user);
//    }
//
//    /**
//     * 新增家长信息
//     *
//     * @param sysPatriarch 家长信息
//     * @return 结果
//     */
//    @Override
//    public int insertSysPatriarch(SysPatriarch sysPatriarch) {
//        sysPatriarch.setCreateTime(DateUtils.getNowDate());
//        sysPatriarch.setPassword(SecurityUtils.encryptPassword(StringUtils.substring(sysPatriarch.getPhone(), 5, 11)));
//        sysPatriarchMapper.insertSysPatriarch(sysPatriarch);
//        sysPatriarch.setPatriarchId(sysPatriarch.getPatriarchId());
//
//        return sysPatriarchMapper.insertStudentPatriarch(sysPatriarch);
//    }
//
//    /**
//     * 修改家长信息
//     *
//     * @param sysPatriarch 家长信息
//     * @return 结果
//     */
//    @Override
//    public int updateSysPatriarch(SysPatriarch sysPatriarch) {
//        sysPatriarch.setUpdateTime(DateUtils.getNowDate());
//        return sysPatriarchMapper.updateSysPatriarch(sysPatriarch);
//    }
//
//    /**
//     * 批量删除家长信息
//     *
//     * @param patriarchIds 需要删除的家长信息主键
//     * @return 结果
//     */
//    @Override
//    public int deleteSysPatriarchByPatriarchIds(Long[] patriarchIds) {
//        return sysPatriarchMapper.deleteSysPatriarchByPatriarchIds(patriarchIds);
//    }
//
//    /**
//     * 删除家长信息信息
//     *
//     * @param patriarchId 家长信息主键
//     * @return 结果
//     */
//    @Override
//    public int deleteSysPatriarchByPatriarchId(Long patriarchId) {
//        return sysPatriarchMapper.deleteSysPatriarchByPatriarchId(patriarchId);
//    }
//}
