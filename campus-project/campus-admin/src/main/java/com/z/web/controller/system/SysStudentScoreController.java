package com.z.web.controller.system;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.z.common.annotation.Excel;
import com.z.common.core.domain.entity.SysSemesterManage;
import com.z.common.core.domain.entity.SysUser;
import com.z.system.domain.SysStudent;
import com.z.system.mapper.SysSemesterManageMapper;
import com.z.system.service.ISysSemesterService;
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
import com.z.system.domain.SysStudentScore;
import com.z.system.service.ISysStudentScoreService;
import com.z.common.utils.poi.ExcelUtil;
import com.z.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 学生成绩管理Controller
 *
 * @author z
 * @date 2025-04-10
 */
@RestController
@RequestMapping("/system/score")
public class SysStudentScoreController extends BaseController {
    @Autowired
    private ISysStudentScoreService sysStudentScoreService;
    @Autowired
    private ISysStudentService studentService;
    @Autowired
    private SysSemesterManageMapper semesterManageMapper;

    /**
     * 查询学生成绩管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:score:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysStudentScore sysStudentScore) {
        startPage();
        List<SysStudentScore> list = sysStudentScoreService.selectSysStudentScoreList(sysStudentScore);
        return getDataTable(list);
    }

    /**
     * 导出学生成绩管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:score:export')")
    @Log(title = "学生成绩管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysStudentScore sysStudentScore) {
        List<SysStudentScore> list = sysStudentScoreService.selectSysStudentScoreList(sysStudentScore);
        ExcelUtil<SysStudentScore> util = new ExcelUtil<SysStudentScore>(SysStudentScore.class);
        util.exportExcel(response, list, "学生成绩管理数据");
    }

    /**
     * 获取学生成绩管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:score:query')")
    @GetMapping(value = "/{studentScoreId}")
    public AjaxResult getInfo(@PathVariable("studentScoreId") Long studentScoreId) {
        return success(sysStudentScoreService.selectSysStudentScoreByStudentScoreId(studentScoreId));
    }

//    /**
//     * 新增学生成绩管理
//     */
//    @PreAuthorize("@ss.hasPermi('system:score:add')")
//    @Log(title = "学生成绩管理", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody SysStudentScore sysStudentScore) {
//        return toAjax(sysStudentScoreService.insertSysStudentScore(sysStudentScore));
//    }
//
    /**
     * 修改学生成绩管理
     */
    @PreAuthorize("@ss.hasPermi('system:score:edit')")
    @Log(title = "学生成绩管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysStudentScore sysStudentScore) {
        return toAjax(sysStudentScoreService.updateSysStudentScore(sysStudentScore));
    }

    /**
     * 删除学生成绩管理
     */
    @PreAuthorize("@ss.hasPermi('system:score:remove')")
    @Log(title = "学生成绩管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{studentScoreIds}")
    public AjaxResult remove(@PathVariable Long[] studentScoreIds) {
        return toAjax(sysStudentScoreService.deleteSysStudentScoreByStudentScoreIds(studentScoreIds));
    }

    /**
     * 导出模版
     *
     * @param response
     */
    @PostMapping("/studentScoreTemplate")
    public void importTemplate(HttpServletResponse response) {
        List<SysStudentScore> sysStudentScores = new ArrayList<>();
        SysSemesterManage sysSemester = new SysSemesterManage();
        sysSemester.setStatus("1");
        SysSemesterManage sysSemesterManage = semesterManageMapper.selectSemesterByConditions(sysSemester);
        List<SysStudent> students = studentService.selectSysStudentListAndDept();
        for (SysStudent student : students) {
            SysStudentScore sysStudentScore = new SysStudentScore();
            sysStudentScore.setSemesterId(sysSemesterManage.getSemesterId());
            sysStudentScore.setStudentName(student.getStudentName());
            sysStudentScore.setDeptName(student.getDept().getDeptName());
            sysStudentScores.add(sysStudentScore);
        }
        ExcelUtil<SysStudentScore> util = new ExcelUtil<>(SysStudentScore.class);
        util.importTemplateExcel(sysStudentScores, response, "学生成绩数据");
    }

    @Log(title = "学生成绩管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:score:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<SysStudentScore> util = new ExcelUtil<>(SysStudentScore.class);
        List<SysStudentScore> studentScores = util.importExcel(file.getInputStream());

        SysStudentScore sysStudentScore = new SysStudentScore();
        List<SysStudentScore> list = sysStudentScoreService.selectSysStudentScoreList(sysStudentScore);
        if (!list.isEmpty()) {
            String semesterId = studentScores.get(0).getSemesterId();
            sysStudentScoreService.deleteBySemesterId(semesterId);
        }
        for (SysStudentScore studentScore : studentScores) {
            SysStudent student = studentService.selectSysStudentIdByNameAndDept(studentScore.getStudentName(), studentScore.getDeptName());
            studentScore.setStudentId(student.getStudentId());
            studentScore.setDeptId(student.getDeptId());
        }
        sysStudentScoreService.insertSysStudentScores(studentScores);
        return success();
    }
}
