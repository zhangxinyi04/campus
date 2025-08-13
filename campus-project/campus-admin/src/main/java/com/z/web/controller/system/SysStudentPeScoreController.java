package com.z.web.controller.system;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.z.common.core.domain.entity.SysSemesterManage;
import com.z.system.domain.SysStudent;
import com.z.system.domain.SysStudentScore;
import com.z.system.mapper.SysSemesterManageMapper;
import com.z.system.service.ISysStudentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.z.common.annotation.Log;
import com.z.common.core.controller.BaseController;
import com.z.common.core.domain.AjaxResult;
import com.z.common.enums.BusinessType;
import com.z.system.domain.SysStudentPeScore;
import com.z.system.service.ISysStudentPeScoreService;
import com.z.common.utils.poi.ExcelUtil;
import com.z.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 学生体育成绩管理Controller
 *
 * @author z
 * @date 2025-04-10
 */
@RestController
@RequestMapping("/system/peScore")
public class SysStudentPeScoreController extends BaseController {
    @Autowired
    private ISysStudentPeScoreService sysStudentPeScoreService;
    @Autowired
    private SysSemesterManageMapper semesterManageMapper;
    @Autowired
    private ISysStudentService studentService;

    /**
     * 查询学生体育成绩管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:score:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysStudentPeScore sysStudentPeScore) {
        startPage();
        List<SysStudentPeScore> list = sysStudentPeScoreService.selectSysStudentPeScoreList(sysStudentPeScore);
        return getDataTable(list);
    }

    /**
     * 导出学生体育成绩管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:score:export')")
    @Log(title = "学生体育成绩管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysStudentPeScore sysStudentPeScore) {
        List<SysStudentPeScore> list = sysStudentPeScoreService.selectSysStudentPeScoreList(sysStudentPeScore);
        ExcelUtil<SysStudentPeScore> util = new ExcelUtil<SysStudentPeScore>(SysStudentPeScore.class);
        util.exportExcel(response, list, "学生体育成绩管理数据");
    }

    /**
     * 获取学生体育成绩管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:score:query')")
    @GetMapping(value = "/{studentScoreId}")
    public AjaxResult getInfo(@PathVariable("studentScoreId") Long studentScoreId) {
        return success(sysStudentPeScoreService.selectSysStudentPeScoreByStudentScoreId(studentScoreId));
    }
//
//    /**
//     * 新增学生体育成绩管理
//     */
//    @PreAuthorize("@ss.hasPermi('system:score:add')")
//    @Log(title = "学生体育成绩管理", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody SysStudentPeScore sysStudentPeScore) {
//        return toAjax(sysStudentPeScoreService.insertSysStudentPeScore(sysStudentPeScore));
//    }
//
    /**
     * 修改学生体育成绩管理
     */
    @PreAuthorize("@ss.hasPermi('system:score:edit')")
    @Log(title = "学生体育成绩管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysStudentPeScore sysStudentPeScore) {
        return toAjax(sysStudentPeScoreService.updateSysStudentPeScore(sysStudentPeScore));
    }

    /**
     * 删除学生体育成绩管理
     */
    @PreAuthorize("@ss.hasPermi('system:score:remove')")
    @Log(title = "学生体育成绩管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{studentScoreIds}")
    public AjaxResult remove(@PathVariable Long[] studentScoreIds) {
        return toAjax(sysStudentPeScoreService.deleteSysStudentPeScoreByStudentScoreIds(studentScoreIds));
    }

    @PostMapping("/studentPeScoreTemplate")
    public void importTemplate(HttpServletResponse response) {
        List<SysStudentPeScore> sysStudentPeScores = new ArrayList<>();
        SysSemesterManage sysSemester = new SysSemesterManage();
        sysSemester.setStatus("1");
        SysSemesterManage sysSemesterManage = semesterManageMapper.selectSemesterByConditions(sysSemester);

        List<SysStudent> students = studentService.selectSysStudentListAndDept();
        for (SysStudent student : students) {
            SysStudentPeScore sysStudentPeScore = new SysStudentPeScore();
            sysStudentPeScore.setSemesterId(sysSemesterManage.getSemesterId());
            sysStudentPeScore.setStudentName(student.getStudentName());
            sysStudentPeScore.setDeptName(student.getDept().getDeptName());
            sysStudentPeScores.add(sysStudentPeScore);
        }
        ExcelUtil<SysStudentPeScore> util = new ExcelUtil<>(SysStudentPeScore.class);
        util.importTemplateExcel(sysStudentPeScores, response, "学生体育成绩数据");
    }

    @Log(title = "学生体育成绩管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:score:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<SysStudentPeScore> util = new ExcelUtil<>(SysStudentPeScore.class);
        List<SysStudentPeScore> studentPeScores = util.importExcel(file.getInputStream());
        String semesterId = studentPeScores.get(0).getSemesterId();

        SysStudentPeScore sysStudentPeScore = new SysStudentPeScore();
        sysStudentPeScore.setSemesterId(semesterId);
        List<SysStudentPeScore> list = sysStudentPeScoreService.selectSysStudentPeScoreList(sysStudentPeScore);
        if (!list.isEmpty()) {
            sysStudentPeScoreService.deleteBySemesterId(semesterId);
        }
        for (SysStudentPeScore studentPeScore : studentPeScores) {
            SysStudent student = studentService.selectSysStudentIdByNameAndDept(studentPeScore.getStudentName(), studentPeScore.getDeptName());
            studentPeScore.setStudentId(student.getStudentId());
        }
        sysStudentPeScoreService.insertSysStudentPeScores(studentPeScores);
        return success();
    }


}
