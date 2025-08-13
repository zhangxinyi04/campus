import request from '@/utils/request'

// 查询学生发展管理列表
export function listDevelop(query) {
  return request({
    url: '/system/develop/list',
    method: 'get',
    params: query
  })
}

// 查询学生发展管理详细
export function getDevelop(developId) {
  return request({
    url: '/system/develop/' + developId,
    method: 'get'
  })
}

// 新增学生发展管理
export function addDevelop(data) {
  return request({
    url: '/system/develop',
    method: 'post',
    data: data
  })
}

// 修改学生发展管理
export function updateDevelop(data) {
  return request({
    url: '/system/develop',
    method: 'put',
    data: data
  })
}

// 删除学生发展管理
export function delDevelop(developId) {
  return request({
    url: '/system/develop/' + developId,
    method: 'delete'
  })
}
