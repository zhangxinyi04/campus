import request from '@/utils/request'

export function documentdownloadNotification(data) {
  return request({
      url: '/system/document/downloadNotification',
      method: 'post',
       data
  })
}

export function documentread(query) {
  return request({
      url: '/system/document/read',
      method: 'get',
      params: query
  })
}

// export function documentread(id) {
//   return request({
//     url: '/system/document/read/' + id,
//     method: 'get'
//   })
// }
