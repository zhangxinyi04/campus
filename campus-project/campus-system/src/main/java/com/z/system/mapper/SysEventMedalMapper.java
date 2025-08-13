package com.z.system.mapper;

import java.util.List;
import com.z.system.domain.SysEventMedal;

/**
 * 活动奖章Mapper接口
 * 
 * @author z
 * @date 2025-03-13
 */
public interface SysEventMedalMapper 
{
    /**
     * 查询活动奖章
     * 
     * @param eventMedalId 活动奖章主键
     * @return 活动奖章
     */
    public SysEventMedal selectSysEventMedalByEventMedalId(Long eventMedalId);

    /**
     * 查询活动奖章列表
     * 
     * @param sysEventMedal 活动奖章
     * @return 活动奖章集合
     */
    public List<SysEventMedal> selectSysEventMedalList(SysEventMedal sysEventMedal);

    /**
     * 新增活动奖章
     * 
     * @param sysEventMedal 活动奖章
     * @return 结果
     */
    public int insertSysEventMedal(SysEventMedal sysEventMedal);

    /**
     * 修改活动奖章
     * 
     * @param sysEventMedal 活动奖章
     * @return 结果
     */
    public int updateSysEventMedal(SysEventMedal sysEventMedal);

    /**
     * 删除活动奖章
     * 
     * @param eventMedalId 活动奖章主键
     * @return 结果
     */
    public int deleteSysEventMedalByEventMedalId(Long eventMedalId);

    /**
     * 批量删除活动奖章
     * 
     * @param eventMedalIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysEventMedalByEventMedalIds(Long[] eventMedalIds);
}
