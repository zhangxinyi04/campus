import request from '@/utils/request'


//评价
export function classEvaluation(data) {
  return request({
    url: '/system/classEvaluation',
    method: 'post',
    data: data
  })
}
//查询当前登陆的课程和班级
export function selectClass(query) {
  return request({
    url: '/system/classEvaluation/selectClass',
    method: 'get',
    params: query
  })
}
//查询所在班级的学生
export function selectStudents(data) {
  return request({
    url: '/system/classEvaluation/selectStudents',
    method: 'post',
    data: data
  })
}
export function classEvaluationremove(data) {
  return request({
    url: '/system/classEvaluation/remove',
    method: 'delete',
    data: data
  })
}


export function classEvaluationeditGroup(data) {
  return request({
    url: '/system/classEvaluation/editGroup',
    method: 'put',
    data: data
  })
}


//wuy
// 查询参数列表
export function listConfig(query) {
  return request({
    url: '/system/config/list',
    method: 'get',
    params: query
  })
}

// 查询参数详细
export function getConfig(configId) {
  return request({
    url: '/system/config/' + configId,
    method: 'get'
  })
}

// 根据参数键名查询参数值
export function getConfigKey(configKey) {
  return request({
    url: '/system/config/configKey/' + configKey,
    method: 'get'
  })
}

// 新增参数配置
export function addConfig(data) {
  return request({
    url: '/system/config',
    method: 'post',
    data: data
  })
}

// 修改参数配置
export function updateConfig(data) {
  return request({
    url: '/system/config',
    method: 'put',
    data: data
  })
}

// 删除参数配置
export function delConfig(configId) {
  return request({
    url: '/system/config/' + configId,
    method: 'delete'
  })
}

// 刷新参数缓存
export function refreshCache() {
  return request({
    url: '/system/config/refreshCache',
    method: 'delete'
  })
}
