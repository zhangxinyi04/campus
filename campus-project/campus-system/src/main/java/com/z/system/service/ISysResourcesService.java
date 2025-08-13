package com.z.system.service;

import com.z.common.core.domain.TreeSelect;
import com.z.common.core.domain.entity.SysResources;
import com.z.system.domain.vo.RouterVo;

import java.util.List;
import java.util.Set;

/**
 * 资源 业务层
 * 
 * @author ruoyi
 */
public interface ISysResourcesService
{
//    /**
//     * 根据用户查询系统资源列表
//     *
//     * @param userId 用户ID
//     * @return 资源列表
//     */
//    public List<SysResources> selectResourcesList();

    /**
     * 根据用户查询系统资源列表
     * 
     * @param resources 资源信息
     * @param userId 用户ID
     * @return 资源列表
     */
    public List<SysResources> selectResourcesList(SysResources resources);



    /**
     * 构建前端所需要下拉树结构
     * 
     * @param resourcess 资源列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildResourcesTreeSelect(List<SysResources> resourcess);

    /**
     * 根据资源ID查询信息
     * 
     * @param resourcesId 资源ID
     * @return 资源信息
     */
    public SysResources selectResourcesById(Long resourcesId);

    /**
     * 是否存在资源子节点
     * 
     * @param resourcesId 资源ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean hasChildByResourcesId(Long resourcesId);


    /**
     * 新增保存资源信息
     * 
     * @param resources 资源信息
     * @return 结果
     */
    public int insertResources(SysResources resources);

    /**
     * 修改保存资源信息
     * 
     * @param resources 资源信息
     * @return 结果
     */
    public int updateResources(SysResources resources);

    /**
     * 删除资源管理信息
     * 
     * @param resourcesId 资源ID
     * @return 结果
     */
    public int deleteResourcesById(Long resourcesId);

    /**
     * 校验资源名称是否唯一
     * 
     * @param resources 资源信息
     * @return 结果
     */
    public boolean checkResourcesNameUnique(SysResources resources);
}
