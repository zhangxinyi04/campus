import request from '@/utils/request'

// 查询学生信息列表
export function listStudent(query) {
  return request({
    url: '/system/student/list',
    method: 'get',
    params: query
  })
}

// 查询学生信息详细
export function getStudent(studentId) {
  return request({
    url: '/system/student/' + studentId,
    method: 'get'
  })
}

// 新增学生信息
export function addStudent(data) {
  return request({
    url: '/system/student',
    method: 'post',
    data: data
  })
}

// 修改学生信息
export function updateStudent(data) {
  return request({
    url: '/system/student',
    method: 'put',
    data: data
  })
}

// 删除学生信息
export function delStudent(studentId) {
  return request({
    url: '/system/student/' + studentId,
    method: 'delete'
  })
}


// 查询家长信息列表
export function patriarchlist(query) {
  return request({
    url: '/system/user/listPatriarch',
    method: 'get',
    params: query
  })
}
// 新增家长信息
export function addpatriarch(data) {
  return request({
    url: '/system/patriarch',
    method: 'post',
    data: data
  })
}

// 修改家长信息
export function updatepatriarch(data) {
  return request({
    url: '/system/patriarch',
    method: 'put',
    data: data
  })
}

// 删除学生信息
export function delpatriarch(studentId) {
  return request({
    url: '/system/patriarch/' + studentId,
    method: 'delete'
  })
}
