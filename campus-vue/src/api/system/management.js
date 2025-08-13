import request from '@/utils/request'

// export function documentrecordsDownload(data) {
//   return request({
//       url: '/system/document/recordsDownload',
//       method: 'post',
//        data
//   })
// }

// export function documentselectMyRecords(data) {
//   return request({
//       url: '/system/document/selectMyRecords ',
//       method: 'post',
//        data
//   })
// }

// export function resourcestreeselect() {
//   return request({
//     url: '/system/resources/treeselect',
//     method: 'get'
//   })
// }

export function systemevents(data) {
  return request({
    url: '/system/events ',
    method: 'post',
    data
  })
}


export function eventslistTemplate(query) {
  return request({
    url: '/system/events/listTemplate',
    method: 'get',
    params: query
  })
}

export function eventslist(query) {
  return request({
    url: '/system/events/list',
    method: 'get',
    params: query
  })
}

 // 修改
export function updateevents(data) {
  return request({
    url: '/system/events',
    method: 'put',
    data: data
  })
}

// 删除
export function delevents(id) {
  return request({
    url: '/system/events/' + id,
    method: 'delete'
  })
}

export function getDocument(id) {
  return request({
    url: '/system/events/' + id,
    method: 'get'
  })
}


// // 查询用户信息详细
// export function getDocument(id) {
//   return request({
//     url: '/system/document/' + id,
//     method: 'get'
//   })
// }

// // 新增用户信息
// export function addDocument(data) {
//   return request({
//     url: '/system/document',
//     method: 'post',
//     data: data
//   })
// }

// // 修改用户信息
// export function updateDocument(data) {
//   return request({
//     url: '/system/document',
//     method: 'put',
//     data: data
//   })
// }

// // 删除用户信息
// export function delDocument(id) {
//   return request({
//     url: '/system/document/' + id,
//     method: 'delete'
//   })
// }
