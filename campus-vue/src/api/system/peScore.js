import request from '@/utils/request'

// 查询学生体育成绩管理列表
export function listScore(query) {
  return request({
    url: '/system/peScore/list',
    method: 'get',
    params: query
  })
}

// 查询学生体育成绩管理详细
export function getScore(studentScoreId) {
  return request({
    url: '/system/peScore/' + studentScoreId,
    method: 'get'
  })
}

// 新增学生体育成绩管理
export function addScore(data) {
  return request({
    url: '/system/peScore',
    method: 'post',
    data: data
  })
}

// 修改学生体育成绩管理
export function updateScore(data) {
  return request({
    url: '/system/peScore',
    method: 'put',
    data: data
  })
}

// 删除学生体育成绩管理
export function delScore(studentScoreId) {
  return request({
    url: '/system/peScore/' + studentScoreId,
    method: 'delete'
  })
}
