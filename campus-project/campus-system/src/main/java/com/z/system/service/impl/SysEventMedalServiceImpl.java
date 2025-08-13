package com.z.system.service.impl;

import java.util.List;

import com.z.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.z.system.mapper.SysEventMedalMapper;
import com.z.system.domain.SysEventMedal;
import com.z.system.service.ISysEventMedalService;

/**
 * 活动奖章Service业务层处理
 *
 * @author z
 * @date 2025-03-13
 */
@Service
public class SysEventMedalServiceImpl implements ISysEventMedalService {
    @Autowired
    private SysEventMedalMapper sysEventMedalMapper;

    /**
     * 查询活动奖章
     *
     * @param eventMedalId 活动奖章主键
     * @return 活动奖章
     */
    @Override
    public SysEventMedal selectSysEventMedalByEventMedalId(Long eventMedalId) {
        return sysEventMedalMapper.selectSysEventMedalByEventMedalId(eventMedalId);
    }

    /**
     * 查询活动奖章列表
     *
     * @param sysEventMedal 活动奖章
     * @return 活动奖章
     */
    @Override
    public List<SysEventMedal> selectSysEventMedalList(SysEventMedal sysEventMedal) {
        return sysEventMedalMapper.selectSysEventMedalList(sysEventMedal);
    }

    /**
     * 新增活动奖章
     *
     * @param sysEventMedal 活动奖章
     * @return 结果
     */
    @Override
    public int insertSysEventMedal(SysEventMedal sysEventMedal) {
        sysEventMedal.setCreateTime(DateUtils.getNowDate());
        return sysEventMedalMapper.insertSysEventMedal(sysEventMedal);
    }

    /**
     * 修改活动奖章
     *
     * @param sysEventMedal 活动奖章
     * @return 结果
     */
    @Override
    public int updateSysEventMedal(SysEventMedal sysEventMedal) {
        return sysEventMedalMapper.updateSysEventMedal(sysEventMedal);
    }

    /**
     * 批量删除活动奖章
     *
     * @param eventMedalIds 需要删除的活动奖章主键
     * @return 结果
     */
    @Override
    public int deleteSysEventMedalByEventMedalIds(Long[] eventMedalIds) {
        return sysEventMedalMapper.deleteSysEventMedalByEventMedalIds(eventMedalIds);
    }

    /**
     * 删除活动奖章信息
     *
     * @param eventMedalId 活动奖章主键
     * @return 结果
     */
    @Override
    public int deleteSysEventMedalByEventMedalId(Long eventMedalId) {
        return sysEventMedalMapper.deleteSysEventMedalByEventMedalId(eventMedalId);
    }
}
