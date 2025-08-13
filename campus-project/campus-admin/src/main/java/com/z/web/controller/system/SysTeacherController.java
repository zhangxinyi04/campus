package com.z.web.controller.system;

import com.z.common.annotation.Log;
import com.z.common.core.controller.BaseController;
import com.z.common.core.domain.AjaxResult;
import com.z.common.core.domain.entity.SysCourse;
import com.z.common.core.domain.entity.SysDept;
import com.z.common.core.domain.entity.SysRole;
import com.z.common.core.domain.entity.SysUser;
import com.z.common.core.page.TableDataInfo;
import com.z.common.enums.BusinessType;
import com.z.common.utils.SecurityUtils;
import com.z.common.utils.StringUtils;
import com.z.common.utils.poi.ExcelUtil;
import com.z.system.domain.SysStudent;
import com.z.system.service.*;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 课堂评价
 *
 */
@RestController
@RequestMapping("/system/classEvaluation")
public class SysTeacherController extends BaseController {
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysCourseService sysCourseService;

    /**
     * 课堂评价
     *
     * @return
     */
    @GetMapping("/classEvaluation")
    @PreAuthorize("@ss.hasPermi('system:type:classEvaluation')")
    public AjaxResult classEvaluation() {
        Long userId = SecurityUtils.getLoginUser().getUserId();

        List<SysCourse> sysCourses = sysCourseService.selectCourseByUserId(userId);
        return success(sysCourses);
    }

}
