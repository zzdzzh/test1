import request from '@/utils/request'

// 查询故障列表
export function listFault(query) {
  return request({
    url: '/device/device/fault/list',
    method: 'get',
    params: query
  })
}

// 查询故障详细
export function getFault(faultId) {
  return request({
    url: '/device/device/fault/' + faultId,
    method: 'get'
  })
}

// 新增故障
export function addFault(data) {
  return request({
    url: '/device/device/fault',
    method: 'post',
    data: data
  })
}

// 修改故障
export function updateFault(data) {
  return request({
    url: '/device/device/fault',
    method: 'put',
    data: data
  })
}

// 删除故障
export function delFault(faultId) {
  return request({
    url: '/device/device/fault/' + faultId,
    method: 'delete'
  })
} 