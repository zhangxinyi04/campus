package com.z.system.mapper;

import com.z.common.core.domain.entity.SysResources;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单表 数据层
 *
 * @author ruoyi
 */
public interface SysResourcesMapper
{
    /**
     * 查询系统菜单列表
     *
     * @param Resources 菜单信息
     * @return 菜单列表
     */
//    public List<SysResources> selectResourcesList(SysResources Resources);


    /**
     * 根据用户查询系统菜单列表
     *
     * @param Resources 菜单信息
     * @return 菜单列表
     */
    public List<SysResources> selectResourcesList(SysResources resources);


    /**
     * 根据菜单ID查询信息
     *
     * @param ResourcesId 菜单ID
     * @return 菜单信息
     */
    public SysResources selectResourcesById(Long resourcesId);

    /**
     * 是否存在菜单子节点
     *
     * @param ResourcesId 菜单ID
     * @return 结果
     */
    public int hasChildByResourcesId(Long resourcesId);

    /**
     * 新增菜单信息
     *
     * @param Resources 菜单信息
     * @return 结果
     */
    public int insertResources(SysResources resources);

    /**
     * 修改菜单信息
     *
     * @param Resources 菜单信息
     * @return 结果
     */
    public int updateResources(SysResources resources);

    /**
     * 删除菜单管理信息
     *
     * @param ResourcesId 菜单ID
     * @return 结果
     */
    public int deleteResourcesById(Long resourcesId);

    /**
     * 校验菜单名称是否唯一
     *
     * @param ResourcesName 菜单名称
     * @param parentId 父菜单ID
     * @return 结果
     */
    public SysResources checkResourcesNameUnique(@Param("resourcesName") String resourcesName, @Param("parentId") Long parentId);
}
