package com.z.system.mapper;

import java.util.List;
import com.z.system.domain.SysBonusType;
import com.z.system.domain.SysStudentMarkRecords;

/**
 * 加分类型模版Mapper接口
 * 
 * @author z
 * @date 2025-04-17
 */
public interface SysBonusTypeMapper 
{
    /**
     * 查询加分类型模版
     * 
     * @param bonusTypeId 加分类型模版主键
     * @return 加分类型模版
     */
    public SysBonusType selectSysBonusTypeByBonusTypeId(Long bonusTypeId);

    /**
     * 查询加分类型模版列表
     * 
     * @param sysBonusType 加分类型模版
     * @return 加分类型模版集合
     */
    public List<SysBonusType> selectSysBonusTypeList(SysBonusType sysBonusType);

    /**
     * 新增加分类型模版
     * 
     * @param sysBonusType 加分类型模版
     * @return 结果
     */
    public int insertSysBonusType(SysBonusType sysBonusType);

    /**
     * 修改加分类型模版
     * 
     * @param sysBonusType 加分类型模版
     * @return 结果
     */
    public int updateSysBonusType(SysBonusType sysBonusType);

    /**
     * 删除加分类型模版
     * 
     * @param bonusTypeId 加分类型模版主键
     * @return 结果
     */
    public int deleteSysBonusTypeByBonusTypeId(Long bonusTypeId);

    /**
     * 批量删除加分类型模版
     * 
     * @param bonusTypeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysBonusTypeByBonusTypeIds(Long[] bonusTypeIds);

    List<SysBonusType> selectScoreDetail(SysStudentMarkRecords markRecords);

    List<SysBonusType> selectBonusDetail(SysStudentMarkRecords markRecords);

}
