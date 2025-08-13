package com.z.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson2.JSONObject;
import com.z.common.core.domain.entity.SysSemesterManage;
import com.z.system.domain.SysStudent;
import com.z.system.domain.SysStudentMarkRecords;
import com.z.system.domain.vo.StatisticVo;
import com.z.system.mapper.SysSemesterManageMapper;
import com.z.system.service.ISysStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.z.system.mapper.SysBonusTypeMapper;
import com.z.system.domain.SysBonusType;
import com.z.system.service.ISysBonusTypeService;

/**
 * 加分类型模版Service业务层处理
 *
 * @author z
 * @date 2025-04-17
 */
@Service
public class SysBonusTypeServiceImpl implements ISysBonusTypeService {
    @Autowired
    private SysBonusTypeMapper sysBonusTypeMapper;
    @Autowired
    private ISysStudentService sysStudentService;
    @Autowired
    private SysSemesterManageMapper semesterManageMapper;

    /**
     * 查询加分类型模版
     *
     * @param bonusTypeId 加分类型模版主键
     * @return 加分类型模版
     */
    @Override
    public SysBonusType selectSysBonusTypeByBonusTypeId(Long bonusTypeId) {
        return sysBonusTypeMapper.selectSysBonusTypeByBonusTypeId(bonusTypeId);
    }

    /**
     * 查询加分类型模版列表
     *
     * @param sysBonusType 加分类型模版
     * @return 加分类型模版
     */
    @Override
    public List<SysBonusType> selectSysBonusTypeList(SysBonusType sysBonusType) {
        return sysBonusTypeMapper.selectSysBonusTypeList(sysBonusType);
    }

    /**
     * 新增加分类型模版
     *
     * @param sysBonusType 加分类型模版
     * @return 结果
     */
    @Override
    public int insertSysBonusType(SysBonusType sysBonusType) {
        return sysBonusTypeMapper.insertSysBonusType(sysBonusType);
    }

    /**
     * 修改加分类型模版
     *
     * @param sysBonusType 加分类型模版
     * @return 结果
     */
    @Override
    public int updateSysBonusType(SysBonusType sysBonusType) {
        return sysBonusTypeMapper.updateSysBonusType(sysBonusType);
    }

    /**
     * 批量删除加分类型模版
     *
     * @param bonusTypeIds 需要删除的加分类型模版主键
     * @return 结果
     */
    @Override
    public int deleteSysBonusTypeByBonusTypeIds(Long[] bonusTypeIds) {
        return sysBonusTypeMapper.deleteSysBonusTypeByBonusTypeIds(bonusTypeIds);
    }

    /**
     * 删除加分类型模版信息
     *
     * @param bonusTypeId 加分类型模版主键
     * @return 结果
     */
    @Override
    public int deleteSysBonusTypeByBonusTypeId(Long bonusTypeId) {
        return sysBonusTypeMapper.deleteSysBonusTypeByBonusTypeId(bonusTypeId);
    }

    @Override
    public Map<String, Object> statistics(SysStudentMarkRecords markRecords) {
        Map<String, Object> map = new HashMap<>();

        SysSemesterManage sysSemester = new SysSemesterManage();
        sysSemester.setStatus("1");
        SysSemesterManage sysSemesterManage = semesterManageMapper.selectSemesterByConditions(sysSemester);
        markRecords.setSemesterId(sysSemesterManage.getSemesterId());
        //查看总加分件分情况
        List<SysBonusType> scoreDetail = sysBonusTypeMapper.selectScoreDetail(markRecords);
        List<SysBonusType> bonusDetail = sysBonusTypeMapper.selectBonusDetail(markRecords);
        List<SysStudentMarkRecords> students = sysStudentService.selectStudentDetail(markRecords);
        List<StatisticVo> evaluation = sysStudentService.selectEvaluationDetail(markRecords);
        map.put("scoreDetail", scoreDetail);
        map.put("bonusDetail", bonusDetail);
        map.put("students", students);
        map.put("evaluation", evaluation);
        return map;
    }


}
