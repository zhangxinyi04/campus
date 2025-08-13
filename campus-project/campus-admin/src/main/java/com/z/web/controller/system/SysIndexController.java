package com.z.web.controller.system;

import com.z.common.annotation.Log;
import com.z.common.core.domain.AjaxResult;
import com.z.common.core.domain.entity.SysUser;
import com.z.common.enums.BusinessType;
import com.z.common.utils.poi.ExcelUtil;
import com.z.system.service.ISysIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.z.common.config.RuoYiConfig;
import com.z.common.utils.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 首页
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/index")
public class SysIndexController {
    /**
     * 系统基础配置
     */
    @Autowired
    private ISysIndexService indexService;

    /**
     * 访问首页，提示语
     */
    @RequestMapping("/index")
    @PreAuthorize("@ss.hasPermi('system:index:list')")
    public AjaxResult index() {
        return indexService.index();
    }

    /**
     * 访问首页，提示语
     */
    @RequestMapping("/visualization")
    @PreAuthorize("@ss.hasPermi('system:index:visualization')")
    public AjaxResult visualization(String semesterId) {
        return indexService.visualization(semesterId);
    }
}
