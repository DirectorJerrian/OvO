import { axios } from '@/utils/request'
const api = {
    voucherPre: '/api/voucher'
}
export function userVoucherAPI(userId) {
    return axios({
        url: `${api.voucherPre}/${userId}/userVoucher`,
        method: 'GET',
    })
}
export function getAllVoucherAPI() {
    return axios({
        url: `${api.voucherPre}/getAllVoucher`,
        method:'GET'
    })
}

export function addVoucherAPI(data) {
    return axios({
        url:`${api.voucherPre}/addVoucher`,
        method:'POST',
        data
    })

}

export function userAvailableVoucherAPI(params) {
    return axios({
        url: `${api.voucherPre}/userAvailableVoucher`,
        method: 'GET',
        params,
    })
}
export function useVoucherAPI(id){
    return axios({
        url: `${api.voucherPre}/${id}/useVoucher`,
        method: 'POST'
    })
}


export function addVoucherToAllAPI(data) {
    return axios({
        url: `${api.voucherPre}/addVoucherToAll`,
        method:'POST',
        data
    })

}

