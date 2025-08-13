import request from '@/utils/request'

// 查询课程列表
export function listCourse(query) {
  return request({
    url: '/system/course/list',
    method: 'get',
    params: query
  })
}

export function coursegradeList(query) {
  return request({
    url: '/system/course/gradeList',
    method: 'get',
    params: query
  })
}


// 已添加课程年级
export function usercheckCourse(query) {
  return request({
    url: '/system/user/checkCourse',
    method: 'get',
    params: query
  })
}


// 查询课程详细
export function getCourse(courseId) {
  return request({
    url: '/system/course/' + courseId,
    method: 'get'
  })
}

// 新增课程
export function addCourse(data) {
  return request({
    url: '/system/course',
    method: 'post',
    data: data
  })
}

// 修改课程
export function updateCourse(data) {
  return request({
    url: '/system/course',
    method: 'put',
    data: data
  })
}

// 删除课程
export function delCourse(courseId) {
  return request({
    url: '/system/course/' + courseId,
    method: 'delete'
  })
}
