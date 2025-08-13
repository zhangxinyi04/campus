package com.z.system.service.impl;

import com.z.common.constant.Constants;
import com.z.common.constant.UserConstants;
import com.z.common.core.domain.TreeSelect;
import com.z.common.core.domain.entity.SysResources;
import com.z.common.utils.StringUtils;
import com.z.system.mapper.SysResourcesMapper;
import com.z.system.service.ISysResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 资源 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysResourcesServiceImpl implements ISysResourcesService {

    @Autowired
    private SysResourcesMapper resourcesMapper;


    /**
     * 根据用户查询系统资源列表
     *
     * @param userId 用户ID
     * @return 资源列表
     */
//    @Override
//    public List<SysResources> selectResourcesList(Long userId)
//    {
//        return selectResourcesList(new SysResources(), userId);
//    }

    /**
     * 查询系统资源列表
     *
     * @param resources 资源信息
     * @return 资源列表
     */
    @Override
    public List<SysResources> selectResourcesList(SysResources resources) {
        return resourcesMapper.selectResourcesList(resources);
    }


    /**
     * 构建前端所需要树结构
     *
     * @param Resourcess 资源列表
     * @return 树结构列表
     */
    public List<SysResources> buildResourcesTree(List<SysResources> Resourcess) {
        List<SysResources> returnList = new ArrayList<SysResources>();
        List<Long> tempList = Resourcess.stream().map(SysResources::getResourcesId).collect(Collectors.toList());
        for (Iterator<SysResources> iterator = Resourcess.iterator(); iterator.hasNext(); ) {
            SysResources Resources = (SysResources) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(Resources.getParentId())) {
                recursionFn(Resourcess, Resources);
                returnList.add(Resources);
            }
        }
        if (returnList.isEmpty()) {
            returnList = Resourcess;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param Resourcess 资源列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildResourcesTreeSelect(List<SysResources> resourcess) {
        List<SysResources> resourcesTrees = buildResourcesTree(resourcess);
        Stream<SysResources> stream = resourcesTrees.stream();
        Stream<TreeSelect> treeSelectStream = stream.map(TreeSelect::new);
        List<TreeSelect> collect = treeSelectStream.collect(Collectors.toList());
        return collect;
    }

    /**
     * 根据资源ID查询信息
     *
     * @param ResourcesId 资源ID
     * @return 资源信息
     */
    @Override
    public SysResources selectResourcesById(Long resourcesId) {
        return resourcesMapper.selectResourcesById(resourcesId);
    }

    /**
     * 是否存在资源子节点
     *
     * @param ResourcesId 资源ID
     * @return 结果
     */
    @Override
    public boolean hasChildByResourcesId(Long resourcesId) {
        int result = resourcesMapper.hasChildByResourcesId(resourcesId);
        return result > 0;
    }


    /**
     * 新增保存资源信息
     *
     * @param Resources 资源信息
     * @return 结果
     */
    @Override
    public int insertResources(SysResources resources) {
        return resourcesMapper.insertResources(resources);
    }

    /**
     * 修改保存资源信息
     *
     * @param Resources 资源信息
     * @return 结果
     */
    @Override
    public int updateResources(SysResources resources) {
        return resourcesMapper.updateResources(resources);
    }

    /**
     * 删除资源管理信息
     *
     * @param ResourcesId 资源ID
     * @return 结果
     */
    @Override
    public int deleteResourcesById(Long resourcesId) {
        return resourcesMapper.deleteResourcesById(resourcesId);
    }

    /**
     * 校验资源名称是否唯一
     *
     * @param Resources 资源信息
     * @return 结果
     */
    @Override
    public boolean checkResourcesNameUnique(SysResources resources) {
        Long resourcesId = StringUtils.isNull(resources.getResourcesId()) ? -1L : resources.getResourcesId();
        SysResources info = resourcesMapper.checkResourcesNameUnique(resources.getResourcesName(), resources.getParentId());
        if (StringUtils.isNotNull(info) && info.getResourcesId().longValue() != resourcesId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }


    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<SysResources> getChildPerms(List<SysResources> list, int parentId) {
        List<SysResources> returnList = new ArrayList<SysResources>();
        for (Iterator<SysResources> iterator = list.iterator(); iterator.hasNext(); ) {
            SysResources t = (SysResources) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list 分类表
     * @param t    子节点
     */
    private void recursionFn(List<SysResources> list, SysResources t) {
        // 得到子节点列表
        List<SysResources> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysResources tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysResources> getChildList(List<SysResources> list, SysResources t) {
        List<SysResources> tlist = new ArrayList<SysResources>();
        Iterator<SysResources> it = list.iterator();
        while (it.hasNext()) {
            SysResources n = (SysResources) it.next();
            if (n.getParentId().longValue() == t.getResourcesId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysResources> list, SysResources t) {
        return getChildList(list, t).size() > 0;
    }

    /**
     * 内链域名特殊字符替换
     *
     * @return 替换后的内链域名
     */
    public String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path, new String[]{Constants.HTTP, Constants.HTTPS, Constants.WWW, ".", ":"},
                new String[]{"", "", "", "/", "/"});
    }
}
