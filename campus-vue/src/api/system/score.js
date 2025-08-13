import request from '@/utils/request'

// 查询学生成绩管理列表
export function listScore(query) {
  return request({
    url: '/system/score/list',
    method: 'get',
    params: query
  })
}

// 查询学期
export function eventssemesterList(query) {
  return request({
    url: '/system/events/semesterList',
    method: 'get',
    params: query
  })
}

// 查询学生成绩管理详细
export function getScore(studentScoreId) {
  return request({
    url: '/system/score/' + studentScoreId,
    method: 'get'
  })
}

// 新增学生成绩管理
export function addScore(data) {
  return request({
    url: '/system/score',
    method: 'post',
    data: data
  })
}

// 修改学生成绩管理
export function updateScore(data) {
  return request({
    url: '/system/score',
    method: 'put',
    data: data
  })
}

// 删除学生成绩管理
export function delScore(studentScoreId) {
  return request({
    url: '/system/score/' + studentScoreId,
    method: 'delete'
  })
}
