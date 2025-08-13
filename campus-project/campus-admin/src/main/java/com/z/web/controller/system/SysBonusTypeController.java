package com.z.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.z.common.core.domain.entity.SysCourse;
import com.z.common.utils.SecurityUtils;
import com.z.system.domain.SysStudent;
import com.z.system.domain.SysStudentMarkRecords;
import com.z.system.service.ISysCourseService;
import com.z.system.service.ISysStudentService;
import com.z.system.service.IWxStudentService;
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
import com.z.system.domain.SysBonusType;
import com.z.system.service.ISysBonusTypeService;
import com.z.common.utils.poi.ExcelUtil;
import com.z.common.core.page.TableDataInfo;

/**
 * 加分类型模版Controller
 *
 * @author z
 * @date 2025-04-17
 */
@RestController
@RequestMapping("/system/classEvaluation")
public class SysBonusTypeController extends BaseController {
    @Autowired
    private ISysBonusTypeService sysBonusTypeService;
    @Autowired
    private ISysCourseService sysCourseService;
    @Autowired
    private ISysStudentService sysStudentService;

    /**
     * 查询所在班级
     *
     * @return
     */
    @GetMapping("/selectClass")
    @PreAuthorize("@ss.hasPermi('system:classEvaluation:selectClass')")
    public AjaxResult classEvaluation() {
        Long userId = SecurityUtils.getLoginUser().getUserId();
        List<SysCourse> sysCourses = sysCourseService.selectCourseByUserId(userId);
        return success(sysCourses);
    }

    /**
     * 查询所在班级的学生
     *
     * @return
     */
    @PostMapping("/selectStudents")
    @PreAuthorize("@ss.hasPermi('system:classEvaluation:selectStudents')")
    public AjaxResult selectStudents(@RequestBody SysStudent student) {
        List<SysStudent> list = sysStudentService.selectSysStudentList(student);
        return success(list);
    }

    @PostMapping("/selectStudentBonusCount")
    public AjaxResult selectStudentBonusCount(@RequestBody SysStudentMarkRecords markRecords) {
        List<SysStudentMarkRecords> list = sysStudentService.selectStudentBonusCount(markRecords);
        return success(list);
    }

    @PostMapping("/selectStudentBonusCountByStudentId")
    public AjaxResult selectStudentBonusCountByStudentId(@RequestBody SysStudentMarkRecords markRecords) {
        List<SysStudentMarkRecords> list = sysStudentService.selectStudentBonusCountByStudentId(markRecords);
        return success(list);
    }

    /**
     * 查询加分类型模版列表
     */
    @PreAuthorize("@ss.hasPermi('system:classEvaluation:list')")
    @PostMapping("/list")
    public AjaxResult list(@RequestBody SysBonusType sysBonusType) {
        return success(sysBonusTypeService.selectSysBonusTypeList(sysBonusType));
    }

    /**
     * 课堂评价
     *
     */
    @PreAuthorize("@ss.hasPermi('system:classEvaluation:evaluation')")
    @PostMapping("/classEvaluation")
    public AjaxResult evaluation(@RequestBody List<SysStudentMarkRecords> studentMarkRecordsList) {
        return sysStudentService.addClassEvaluation(studentMarkRecordsList);
    }

    /**
     * 新增加分类型模版
     */
    @PreAuthorize("@ss.hasPermi('system:classEvaluation:add')")
    @Log(title = "加分类型模版", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysBonusType sysBonusType) {
        return toAjax(sysBonusTypeService.insertSysBonusType(sysBonusType));
    }

    /**
     * 修改加分类型模版
     */
    @PreAuthorize("@ss.hasPermi('system:classEvaluation:edit')")
    @Log(title = "加分类型模版", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysBonusType sysBonusType) {
        return toAjax(sysBonusTypeService.updateSysBonusType(sysBonusType));
    }

    /**
     * 删除加分类型模版
     */
    @PreAuthorize("@ss.hasPermi('system:classEvaluation:remove')")
    @Log(title = "加分类型模版", businessType = BusinessType.DELETE)
    @DeleteMapping("/{bonusTypeIds}")
    public AjaxResult remove(@PathVariable Long[] bonusTypeIds) {
        return toAjax(sysBonusTypeService.deleteSysBonusTypeByBonusTypeIds(bonusTypeIds));
    }

    @PostMapping("/statistics")
    public AjaxResult statistics(@RequestBody SysStudentMarkRecords markRecords) {
        return success(sysBonusTypeService.statistics(markRecords));
    }

    /**
     * 删除加分类型模版
     */
    @DeleteMapping("/remove")
    public AjaxResult remove(@RequestBody SysStudentMarkRecords markRecords) {
        return toAjax(sysStudentService.removeMarkRecords(markRecords));
    }

    /**
     * 批量修改学生信息
     */
    @PreAuthorize("@ss.hasPermi('system:classEvaluation:group')")
    @Log(title = "学生小组", businessType = BusinessType.UPDATE)
    @PutMapping("/editGroup")
    public AjaxResult editGroup(@RequestBody List<SysStudent> sysStudent) {
        return toAjax(sysStudentService.updateSysStudentBatch(sysStudent));
    }
}
