package com.z.system.mapper;

import java.util.List;
import com.z.system.domain.SysUserDocument;

/**
 * 用户信息Mapper接口
 * 
 * @author z
 * @date 2025-03-04
 */
public interface SysUserDocumentMapper 
{
    /**
     * 查询用户信息
     * 
     * @param id 用户信息主键
     * @return 用户信息
     */
    public SysUserDocument selectSysUserDocumentById(Long id);

    /**
     * 查询用户信息列表
     * 
     * @param sysUserDocument 用户信息
     * @return 用户信息集合
     */
    public List<SysUserDocument> selectSysUserDocumentList(SysUserDocument sysUserDocument);

    /**
     * 新增用户信息
     * 
     * @param sysUserDocument 用户信息
     * @return 结果
     */
    public int insertSysUserDocument(SysUserDocument sysUserDocument);

    /**
     * 修改用户信息
     * 
     * @param sysUserDocument 用户信息
     * @return 结果
     */
    public int updateSysUserDocument(SysUserDocument sysUserDocument);

    /**
     * 删除用户信息
     * 
     * @param id 用户信息主键
     * @return 结果
     */
    public int deleteSysUserDocumentById(Long id);

    /**
     * 批量删除用户信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysUserDocumentByIds(Long[] ids);

    void recordsDownload(SysUserDocument sysUserDocument);

    List<SysUserDocument> selectMyDocumentList(SysUserDocument sysUserDocument);

    List<SysUserDocument> downloadNotification(SysUserDocument sysUserDocument);

    void read(Long id);
}
