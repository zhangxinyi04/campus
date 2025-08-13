package com.z.wx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.z.common.core.controller.BaseController;
import com.z.common.core.domain.AjaxResult;
import com.z.system.domain.SysStudentComment;
import com.z.system.service.ISysStudentCommentService;
import com.z.common.core.page.TableDataInfo;

/**
 * 学生家长评语管理Controller
 *
 * @author z
 * @date 2025-04-14
 */
@RestController
@RequestMapping("/wx/comment")
public class WxStudentCommentController extends BaseController {
    @Autowired
    private ISysStudentCommentService sysStudentCommentService;

    /**
     * 查询学生家长评语管理列表
     */
    @GetMapping("/list")
    public AjaxResult list(SysStudentComment sysStudentComment) {
        List<SysStudentComment> list = sysStudentCommentService.selectSysStudentCommentList(sysStudentComment);
        return success(list);
    }

    /**
     * 获取学生家长评语管理详细信息
     */
    @GetMapping(value = "/{studentCommentId}")
    public AjaxResult getInfo(@PathVariable("studentCommentId") Long studentCommentId) {
        return success(sysStudentCommentService.selectSysStudentCommentByStudentCommentId(studentCommentId));
    }

    /**
     * 新增学生家长评语管理
     */
    @PostMapping
    public AjaxResult add(@RequestBody SysStudentComment sysStudentComment) {
        return toAjax(sysStudentCommentService.insertSysStudentComment(sysStudentComment));
    }

    /**
     * 修改学生家长评语管理
     */
    @PutMapping
    public AjaxResult edit(@RequestBody SysStudentComment sysStudentComment) {
        return toAjax(sysStudentCommentService.updateSysStudentComment(sysStudentComment));
    }

}
