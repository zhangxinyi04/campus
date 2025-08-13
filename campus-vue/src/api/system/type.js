import request from '@/utils/request'

// 新增
export function classEvaluationpost(data) {
  return request({
    url: '/system/classEvaluation',
    method: 'post',
    data: data
  })
}
//修改
export function classEvaluationput(data) {
  return request({
    url: '/system/classEvaluation',
    method: 'put',
    data: data
  })
}
//列表查询
export function classEvaluationlist(data) {
  return request({
    url: '/system/classEvaluation/list',
    method: 'post',
    data: data
  })
}
// 删除加分类型模版
export function classEvaluationdelete(bonusTypeId) {
  return request({
    url: '/system/classEvaluation/' + bonusTypeId,
    method: 'delete'
  })
}
// 评价
export function classEvaluation(data) {
  return request({
    url: '/system/classEvaluation/classEvaluation',
    method: 'post',
    data: data
  })
}
//学生评价次数
export function selectStudentBonusCount(data) {
  return request({
    url: '/system/classEvaluation/selectStudentBonusCount',
    method: 'post',
    data: data
  })
}
//
export function selectStudentBonusCountByStudentId(data) {
  return request({
    url: '/system/classEvaluation/selectStudentBonusCountByStudentId',
    method: 'post',
    data: data
  })
}
export function classEvaluationstatistics(data) {
  return request({
    url: '/system/classEvaluation/statistics',
    method: 'post',
    data: data
  })
}





//
// 查询加分类型模版列表
export function listType(query) {
  return request({
    url: '/system/type/list',
    method: 'get',
    params: query
  })
}

// 查询加分类型模版详细
export function getType(bonusTypeId) {
  return request({
    url: '/system/type/' + bonusTypeId,
    method: 'get'
  })
}

// 新增加分类型模版
export function addType(data) {
  return request({
    url: '/system/classEvaluation/type',
    method: 'post',
    data: data
  })
}

// 修改加分类型模版
export function updateType(data) {
  return request({
    url: '/system/type',
    method: 'put',
    data: data
  })
}

// 删除加分类型模版
export function delType(bonusTypeId) {
  return request({
    url: '/system/type/' + bonusTypeId,
    method: 'delete'
  })
}
