package com.z.system.service.impl;

import com.z.common.config.RuoYiConfig;
import com.z.common.core.domain.AjaxResult;
import com.z.common.core.domain.entity.SysSemesterManage;
import com.z.common.core.domain.entity.SysUser;
import com.z.common.utils.QrCodeUtil;
import com.z.common.utils.SecurityUtils;
import com.z.common.utils.UserTokenManager;
import com.z.common.utils.uuid.IdUtils;
import com.z.common.utils.uuid.UUID;
import com.z.system.domain.SysEventCycleStudent;
import com.z.system.domain.SysStudent;
import com.z.system.domain.SysStudentMarkRecords;
import com.z.system.domain.SysUserDevelop;
import com.z.system.domain.vo.ClassCircleVo;
import com.z.system.mapper.SysSemesterManageMapper;
import com.z.system.mapper.SysStudentMapper;
import com.z.system.mapper.WxStudentMapper;
import com.z.system.mapper.WxUserMapper;
import com.z.system.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * wx学生服务
 *
 * @author ruoyi
 */
@Service
public class WxStudentServiceImpl implements IWxStudentService {

    @Autowired
    private WxStudentMapper studentMapper;
    @Autowired
    private ISysUserDevelopService sysUserDevelopService;
    @Autowired
    private SysSemesterManageMapper semesterManageMapper;
    @Autowired
    private SysStudentMapper sysStudentMapper;
    @Value("${campus.domain}")
    private String domain;

    @Override
    public AjaxResult getInfo() {
        Map<String, Object> map = new HashMap<>();
        Long userId = SecurityUtils.getLoginUser().getUserId();
        SysStudent student = studentMapper.selectStudentByUserId(userId);
        if (student == null) {
            return AjaxResult.error("暂无学生信息");
        }
        SysSemesterManage sysSemester = new SysSemesterManage();
        sysSemester.setStatus("1");
        SysSemesterManage sysSemesterManage = semesterManageMapper.selectSemesterByConditions(sysSemester);
        Long mark = studentMapper.selectMarkByStudentId(sysSemesterManage.getSemesterId(), student.getStudentId());
        map.put("student", student);
        map.put("semester", sysSemesterManage);
        map.put("mark", mark);
        return AjaxResult.success(map);
    }

    @Override
    public AjaxResult realisticRecords(SysUserDevelop sysUserDevelop) {
        SysSemesterManage sysSemester = new SysSemesterManage();
        sysSemester.setStatus("1");
        SysSemesterManage sysSemesterManage = semesterManageMapper.selectSemesterByConditions(sysSemester);
        sysUserDevelop.setSemesterId(sysSemesterManage.getSemesterId());
        String url = sysUserDevelop.getUrl();
        String[] split = url.split("\\.");
        String content = domain + url;
        String qrcodeName = "/" + IdUtils.fastSimpleUUID() + ".png";
        if ("mp4".equals(split[1])) {
            try {
                QrCodeUtil.generateQRCode(content, RuoYiConfig.getUploadQrCodePath() + qrcodeName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        sysUserDevelop.setQrCode("/profile/qrcode" + qrcodeName);
        sysUserDevelopService.insertSysUserDevelop(sysUserDevelop);
        return AjaxResult.success();
    }

    @Override
    public AjaxResult selectRealisticRecords(SysUserDevelop sysUserDevelop) {
        return AjaxResult.success(sysUserDevelopService.selectSysUserDevelopList(sysUserDevelop));
    }

    @Override
    @Transactional
    public AjaxResult clockIn(SysEventCycleStudent eventCycleStudent) {
        List<SysStudentMarkRecords> studentMarkRecordsList = new ArrayList<>();
        SysStudentMarkRecords sysStudentMarkRecords = new SysStudentMarkRecords();
        SysSemesterManage sysSemester = new SysSemesterManage();
        sysSemester.setStatus("1");
        SysSemesterManage sysSemesterManage = semesterManageMapper.selectSemesterByConditions(sysSemester);
        sysStudentMarkRecords.setStudentId(eventCycleStudent.getStudentId());
        sysStudentMarkRecords.setSemesterId(sysSemesterManage.getSemesterId());
        sysStudentMarkRecords.setMark(eventCycleStudent.getMark());
        studentMarkRecordsList.add(sysStudentMarkRecords);

        studentMapper.clockIn(eventCycleStudent);
        sysStudentMapper.addStudentMark(studentMarkRecordsList);
        return AjaxResult.success();
    }

    @Override
    public AjaxResult classCircle(SysStudentMarkRecords studentMarkRecords) {
        SysSemesterManage sysSemester = new SysSemesterManage();
        sysSemester.setStatus("1");
        SysSemesterManage sysSemesterManage = semesterManageMapper.selectSemesterByConditions(sysSemester);
        studentMarkRecords.setSemesterId(sysSemesterManage.getSemesterId());
        List<ClassCircleVo> studentMarkRecord = studentMapper.classCircle(studentMarkRecords);
        return AjaxResult.success(studentMarkRecord);
    }
}
