//package com.z.system.mapper;
//
//import java.util.List;
//
//import com.z.common.core.domain.entity.SysUser;
//import com.z.system.domain.SysPatriarch;
//
///**
// * 家长信息Mapper接口
// *
// * @author z
// * @date 2025-03-08
// */
//public interface SysPatriarchMapper
//{
//    /**
//     * 查询家长信息
//     *
//     * @param patriarchId 家长信息主键
//     * @return 家长信息
//     */
//    public SysPatriarch selectSysPatriarchByPatriarchId(Long patriarchId);
//
//    /**
//     * 查询家长信息列表
//     *
//     * @param sysPatriarch 家长信息
//     * @return 家长信息集合
//     */
//    public List<SysUser> selectSysPatriarchList(SysUser user);
//
//    /**
//     * 新增家长信息
//     *
//     * @param sysPatriarch 家长信息
//     * @return 结果
//     */
//    public int insertSysPatriarch(SysPatriarch sysPatriarch);
//
//    /**
//     * 修改家长信息
//     *
//     * @param sysPatriarch 家长信息
//     * @return 结果
//     */
//    public int updateSysPatriarch(SysPatriarch sysPatriarch);
//
//    /**
//     * 删除家长信息
//     *
//     * @param patriarchId 家长信息主键
//     * @return 结果
//     */
//    public int deleteSysPatriarchByPatriarchId(Long patriarchId);
//
//    /**
//     * 批量删除家长信息
//     *
//     * @param patriarchIds 需要删除的数据主键集合
//     * @return 结果
//     */
//    public int deleteSysPatriarchByPatriarchIds(Long[] patriarchIds);
//
//    int insertStudentPatriarch(SysPatriarch sysPatriarch);
//
//    SysPatriarch selectPatriarchByPhone(String phone);
//}
