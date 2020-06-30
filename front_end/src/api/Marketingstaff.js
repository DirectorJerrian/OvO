import { axios } from '@/utils/request'
const api = {
    orderPre: '/api/order',
    marketingStaffPre: '/api/marketingStaff',
}
export function revokeOrderAPI(orderId){
    return axios({
        url:`${api.orderPre}/${orderId}/revokeOrder`,
        method:'GET',
    })
}


export function restoreOrderAPI(orderId){
    return axios({
        url:`${api.orderPre}/${orderId}/restoreOrder`,
        method:'GET',
    })
}

export function creditChargeAPI(data){
    return axios({
        url: `${api.marketingStaffPre}/creditCharge`,
        method:`POST`,
        data,
    })
}