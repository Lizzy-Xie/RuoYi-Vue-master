import request from '@/utils/request'

// ===================== 原有接口（一字未改） =====================
// 查询图书信息列表
export function listBook(query) {
  return request({
    url: '/library/book/list',
    method: 'get',
    params: query
  })
}

// 查询图书信息详细
export function getBook(bkID) {
  return request({
    url: '/library/book/' + bkID,
    method: 'get'
  })
}

// 新增图书信息
export function addBook(data) {
  return request({
    url: '/library/book',
    method: 'post',
    data: data
  })
}

// 修改图书信息
export function updateBook(data) {
  return request({
    url: '/library/book',
    method: 'put',
    data: data
  })
}

// 删除图书信息
export function delBook(bkID) {
  return request({
    url: '/library/book/' + bkID,
    method: 'delete'
  })
}

// ===================== 新增接口（与后端Controller完全匹配） =====================
// 获取图书分类目录（前端下拉选择用）
export function getBookCatalog() {
  return request({
    url: '/library/book/catalog',
    method: 'get'
  })
}

// 批量入库（传递基础图书信息+入库数量）
export function batchInsertBook(book, batchNum) {
  return request({
    url: `/library/book/batch?batchNum=${batchNum}`,
    method: 'post',
    data: book
  })
}

// 批量更新图书状态（借书/还书联动）
export function updateBatchStatus(bkIDs, bkStatus) {
  return request({
    url: '/library/book/batch/status',
    method: 'put',
    params: { bkIDs, bkStatus } // 数组参数自动拼接为?bkIDs=1&bkIDs=2&bkStatus=借出
  })
}