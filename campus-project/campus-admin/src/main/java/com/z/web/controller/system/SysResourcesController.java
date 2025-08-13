package com.z.web.controller.system;

import com.z.common.annotation.Log;
import com.z.common.constant.UserConstants;
import com.z.common.core.controller.BaseController;
import com.z.common.core.domain.AjaxResult;
import com.z.common.core.domain.entity.SysResources;
import com.z.common.core.domain.entity.SysResources;
import com.z.common.enums.BusinessType;
import com.z.common.utils.StringUtils;
import com.z.system.service.ISysResourcesService;
import com.z.system.service.ISysResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 资源信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/resources")
public class SysResourcesController extends BaseController {
    @Autowired
    private ISysResourcesService resourcesService;

    /**
     * 获取资源列表
     */
    @GetMapping("/list")
    public AjaxResult list(SysResources sysResources) {
        List<SysResources> resources = resourcesService.selectResourcesList(sysResources);
        return success(resources);
    }

    /**
     * 根据资源编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:resources:query')")
    @GetMapping(value = "/{resources}")
    public AjaxResult getInfo(@PathVariable Long resources) {
        return success(resourcesService.selectResourcesById(resources));
    }

    /**
     * 获取资源下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(SysResources sysResources) {
        List<SysResources> resourcess = resourcesService.selectResourcesList(sysResources);
        return success(resourcesService.buildResourcesTreeSelect(resourcess));
    }

//    /**
//     * 加载对应角色资源列表树
//     */
//    @GetMapping(value = "/roleResourcesTreeselect/{roleId}")
//    public AjaxResult roleResourcesTreeselect(@PathVariable("roleId") Long roleId) {
//        List<SysResources> Resourcess = ResourcesService.selectResourcesList(getUserId());
//        AjaxResult ajax = AjaxResult.success();
//        ajax.put("checkedKeys", ResourcesService.selectResourcesListByRoleId(roleId));
//        ajax.put("Resourcess", ResourcesService.buildResourcesTreeSelect(Resourcess));
//        return ajax;
//    }

    /**
     * 新增资源
     */
    @PreAuthorize("@ss.hasPermi('system:resources:add')")
    @Log(title = "资源管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysResources resources) {
        if (!resourcesService.checkResourcesNameUnique(resources)) {
            return error("新增资源'" + resources.getResourcesName() + "'失败，资源名称已存在");
        }
        resources.setCreateBy(getUsername());
        return toAjax(resourcesService.insertResources(resources));
    }

    /**
     * 修改资源
     */
    @PreAuthorize("@ss.hasPermi('system:resources:edit')")
    @Log(title = "资源管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysResources resources) {
        if (!resourcesService.checkResourcesNameUnique(resources)) {
            return error("修改资源'" + resources.getResourcesName() + "'失败，资源名称已存在");

        } else if (resources.getResourcesId().equals(resources.getParentId())) {
            return error("修改资源'" + resources.getResourcesName() + "'失败，上级资源不能选择自己");
        }
        resources.setUpdateBy(getUsername());
        return toAjax(resourcesService.updateResources(resources));
    }



    /**
     * 删除资源
     */
    @PreAuthorize("@ss.hasPermi('system:resources:remove')")
    @Log(title = "资源管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{resourcesId}")
    public AjaxResult remove(@PathVariable("resourcesId") Long resourcesId) {
        if (resourcesService.hasChildByResourcesId(resourcesId)) {
            return warn("存在子资源,不允许删除");
        }
        return toAjax(resourcesService.deleteResourcesById(resourcesId));
    }
}