package com.z.system.service;

import java.util.List;
import com.z.system.domain.SysUserDocument;

/**
 * 文件信息Service接口
 * 
 * @author z
 * @date 2025-03-04
 */
public interface ISysUserDocumentService 
{
    /**
     * 查询文件信息
     * 
     * @param id 文件信息主键
     * @return 文件信息
     */
    public SysUserDocument selectSysUserDocumentById(Long id);

    /**
     * 查询文件信息列表
     * 
     * @param sysUserDocument 文件信息
     * @return 文件信息集合
     */
    public List<SysUserDocument> selectSysUserDocumentList(SysUserDocument sysUserDocument);

    /**
     * 新增文件信息
     * 
     * @param sysUserDocument 文件信息
     * @return 结果
     */
    public int insertSysUserDocument(SysUserDocument sysUserDocument);

    /**
     * 修改文件信息
     * 
     * @param sysUserDocument 文件信息
     * @return 结果
     */
    public int updateSysUserDocument(SysUserDocument sysUserDocument);

    /**
     * 批量删除文件信息
     * 
     * @param ids 需要删除的文件信息主键集合
     * @return 结果
     */
    public int deleteSysUserDocumentByIds(Long[] ids);

    /**
     * 删除文件信息信息
     * 
     * @param id 文件信息主键
     * @return 结果
     */
    public int deleteSysUserDocumentById(Long id);

    void recordsDownload(SysUserDocument sysUserDocument);

    List<SysUserDocument> selectMyDocumentList(SysUserDocument sysUserDocument);

    List<SysUserDocument> downloadNotification(SysUserDocument sysUserDocument);

    void read(Long id);
}
