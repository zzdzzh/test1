import request from '@/utils/request';

/**
 * 获取设备列表
 * @param {Object} params - 查询参数
 * @returns {Promise}
 */
export function listDevices(params) {
  return request({
    url: '/device/device/list',
    method: 'get',
    params: params
  });
}

/**
 * 获取设备详细信息
 * @param {Number} id - 设备ID
 * @returns {Promise}
 */
export function getDevice(id) {
  return request({
    url: `/device/device/${id}`,
    method: 'get'
  });
}

/**
 * 新增设备
 * @param {Object} data - 设备数据
 * @returns {Promise}
 */
export function addDevice(data) {
  return request({
    url: '/device/device',
    method: 'post',
    data: data
  });
}

/**
 * 修改设备
 * @param {Object} data - 设备数据
 * @returns {Promise}
 */
export function updateDevice(data) {
  return request({
    url: '/device/device',
    method: 'put',
    data: data
  });
}

/**
 * 删除设备
 * @param {Number} id - 设备ID
 * @returns {Promise}
 */
export function deleteDevice(id) {
  return request({
    url: `/device/device/${id}`,
    method: 'delete'
  });
}