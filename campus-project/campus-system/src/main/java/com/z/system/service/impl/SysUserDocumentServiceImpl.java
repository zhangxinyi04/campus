package com.z.system.service.impl;

import java.util.List;

import com.z.common.core.domain.entity.SysUser;
import com.z.common.core.domain.model.LoginUser;
import com.z.common.utils.DateUtils;
import com.z.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.z.system.mapper.SysUserDocumentMapper;
import com.z.system.domain.SysUserDocument;
import com.z.system.service.ISysUserDocumentService;

/**
 * 文件信息Service业务层处理
 *
 * @author z
 * @date 2025-03-04
 */
@Service
public class SysUserDocumentServiceImpl implements ISysUserDocumentService {
    @Autowired
    private SysUserDocumentMapper sysUserDocumentMapper;

    /**
     * 查询文件信息
     *
     * @param id 文件信息主键
     * @return 文件信息
     */
    @Override

    public SysUserDocument selectSysUserDocumentById(Long id) {
        return sysUserDocumentMapper.selectSysUserDocumentById(id);
    }

    /**
     * 查询文件信息列表
     *
     * @param sysUserDocument 文件信息
     * @return 文件信息
     */
    @Override
    public List<SysUserDocument> selectSysUserDocumentList(SysUserDocument sysUserDocument) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getUser();
        if (!SysUser.isAdmin(user.getUserId())) {
            sysUserDocument.setStatus("1");
        }
        return sysUserDocumentMapper.selectSysUserDocumentList(sysUserDocument);
    }

    /**
     * 新增文件信息
     *
     * @param sysUserDocument 文件信息
     * @return 结果
     */
    @Override
    public int insertSysUserDocument(SysUserDocument sysUserDocument) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getUser();
        sysUserDocument.setCreateTime(DateUtils.getNowDate());
        sysUserDocument.setUserId(user.getUserId());
        return sysUserDocumentMapper.insertSysUserDocument(sysUserDocument);
    }

    /**
     * 修改文件信息
     *
     * @param sysUserDocument 文件信息
     * @return 结果
     */
    @Override
    public int updateSysUserDocument(SysUserDocument sysUserDocument) {
        return sysUserDocumentMapper.updateSysUserDocument(sysUserDocument);
    }

    /**
     * 批量删除文件信息
     *
     * @param ids 需要删除的文件信息主键
     * @return 结果
     */
    @Override
    public int deleteSysUserDocumentByIds(Long[] ids) {
        return sysUserDocumentMapper.deleteSysUserDocumentByIds(ids);
    }

    /**
     * 删除文件信息信息
     *
     * @param id 文件信息主键
     * @return 结果
     */
    @Override
    public int deleteSysUserDocumentById(Long id) {
        return sysUserDocumentMapper.deleteSysUserDocumentById(id);
    }

    @Override
    public void recordsDownload(SysUserDocument sysUserDocument) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        sysUserDocument.setUserId(user.getUserId());
        sysUserDocumentMapper.recordsDownload(sysUserDocument);
    }

    @Override
    public List<SysUserDocument> selectMyDocumentList(SysUserDocument sysUserDocument) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        sysUserDocument.setUserId(user.getUserId());
        return sysUserDocumentMapper.selectMyDocumentList(sysUserDocument);
    }

    @Override
    public List<SysUserDocument> downloadNotification(SysUserDocument sysUserDocument) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        sysUserDocument.setUserId(user.getUserId());
        return sysUserDocumentMapper.downloadNotification(sysUserDocument);
    }

    @Override
    public void read(Long id) {
         sysUserDocumentMapper.read(id);

    }
}
