//package com.z.web.controller.system;
//
//import java.util.List;
//import javax.servlet.http.HttpServletResponse;
//
//import com.z.common.core.domain.entity.SysUser;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import com.z.common.annotation.Log;
//import com.z.common.core.controller.BaseController;
//import com.z.common.core.domain.AjaxResult;
//import com.z.common.enums.BusinessType;
//import com.z.system.domain.SysPatriarch;
//import com.z.system.service.ISysPatriarchService;
//import com.z.common.utils.poi.ExcelUtil;
//import com.z.common.core.page.TableDataInfo;
//
///**
// * 家长信息Controller
// *
// * @author z
// * @date 2025-03-08
// */
//@RestController
//@RequestMapping("/system/patriarch")
//public class SysPatriarchController extends BaseController {
//    @Autowired
//    private ISysPatriarchService sysPatriarchService;
//
//    /**
//     * 查询家长信息列表
//     */
//    @PreAuthorize("@ss.hasPermi('system:patriarch:list')")
//    @GetMapping("/list")
//    public AjaxResult list(SysUser user) {
//        List<SysPatriarch> list = sysPatriarchService.selectSysPatriarchList(user);
//        return success(list);
//    }
//
//    /**
//     * 导出家长信息列表
//     */
//    @PreAuthorize("@ss.hasPermi('system:patriarch:export')")
//    @Log(title = "家长信息", businessType = BusinessType.EXPORT)
//    @PostMapping("/export")
//    public void export(HttpServletResponse response, SysPatriarch sysPatriarch) {
//        List<SysPatriarch> list = sysPatriarchService.selectSysPatriarchList(sysPatriarch);
//        ExcelUtil<SysPatriarch> util = new ExcelUtil<SysPatriarch>(SysPatriarch.class);
//        util.exportExcel(response, list, "家长信息数据");
//    }
//
//}
