import request from '@/utils/request'

// 查询借阅信息列表
export function listBorrow(query) {
  return request({
    url: '/library/borrow/list',
    method: 'get',
    params: query
  })
}

// 查询借阅信息详细
export function getBorrow(BorrowID) {
  return request({
    url: '/library/borrow/' + BorrowID,
    method: 'get'
  })
}

// 新增借阅信息
export function addBorrow(data) {
  return request({
    url: '/library/borrow',
    method: 'post',
    data: data
  })
}

// 修改借阅信息
export function updateBorrow(data) {
  return request({
    url: '/library/borrow',
    method: 'put',
    data: data
  })
}

// 删除借阅信息
export function delBorrow(BorrowID) {
  return request({
    url: '/library/borrow/' + BorrowID,
    method: 'delete'
  })
}
