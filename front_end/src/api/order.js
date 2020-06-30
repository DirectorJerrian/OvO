import { axios } from '@/utils/request'
const api = {
    orderPre: '/api/order'
}
export function reserveHotelAPI(data) {
    return axios({
        url: `${api.orderPre}/addOrder`,
        method: 'POST',
        data,
    })
}
export function getAllOrdersAPI() {
    return axios({
        url: `${api.orderPre}/getAllOrders`,
        method: 'GET',
    })
}
export function getStrangeOrdersAPI() {
    return axios({
        url: `${api.orderPre}/getStrangeOrders`,
        method: 'GET',
    })
}
export function getUserOrdersAPI(data) {
    return axios({
        url: `${api.orderPre}/${data.userId}/getUserOrders`,
        method: 'GET',
    })
}
export function getTargetOrderAPI(data) {
    return axios({
        url:`${api.orderPre}/${data.userId}/getTargetOrders`,
    })

}
export function cancelOrderAPI(orderId) {
    return axios({
        url: `${api.orderPre}/${orderId}/annulOrder`,
        method: 'GET',
    })
}
export function deleteOrderOnlyAPI(orderId) {
    return axios({
        url:`${api.orderPre}/${orderId}/deleteOrderOnly`,
        method:'POST'
    })

}
export function orderRateAPI(data){
    return axios({
        url:`${api.orderPre}/orderRate`,
        method:'POST',
        data,
    })
}

export function getHotelOrdersAPI(data) {
    return axios({
        url:`${api.orderPre}/${data.hotelId}/allOrders`,
        method:'GET',
        data
    })

}
export function getUserHotelOrdersAPI(data) {
    return axios({
        url:`${api.orderPre}/${data.hotelId}/${data.userId}/getUserHotelOrders`,
        method:'GET',
    })

}