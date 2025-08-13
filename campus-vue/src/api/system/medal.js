import request from '@/utils/request'

// 查询活动奖章列表
export function listMedal(query) {
  return request({
    url: '/system/medal/list',
    method: 'get',
    params: query
  })
}

// 查询活动奖章详细
export function getMedal(eventMedalId) {
  return request({
    url: '/system/medal/' + eventMedalId,
    method: 'get'
  })
}

// 新增活动奖章
export function addMedal(data) {
  return request({
    url: '/system/medal',
    method: 'post',
    data: data
  })
}

// 修改活动奖章
export function updateMedal(data) {
  return request({
    url: '/system/medal',
    method: 'put',
    data: data
  })
}

// 删除活动奖章
export function delMedal(eventMedalId) {
  return request({
    url: '/system/medal/' + eventMedalId,
    method: 'delete'
  })
}
