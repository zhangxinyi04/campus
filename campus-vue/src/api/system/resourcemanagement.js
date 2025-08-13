import request from '@/utils/request'

// 列表
export function resourceslist(query) {
  return request({
    url: '/system/resources/list',
    method: 'get',
    params: query
  })
}

// 详细
export function getresources(courseId) {
  return request({
    url: '/system/resources/' + courseId,
    method: 'get'
  })
}

// 新增
export function addresources(data) {
  return request({
    url: '/system/resources',
    method: 'post',
    data: data
  })
}

// 修改
export function updateresources(data) {
  return request({
    url: '/system/resources',
    method: 'put',
    data: data
  })
}

// 删除
export function delresources(courseId) {
  return request({
    url: '/system/resources/' + courseId,
    method: 'delete'
  })
}
