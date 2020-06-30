import { axios } from '@/utils/request'
const api = {
    hotelPre: '/api/hotel'
}
export function getHotelsAPI() {
    return axios({
        url: `${api.hotelPre}/all`,
        method: 'get',
    })
}
export function getHotelByIdAPI(param) {
    return axios({
        url: `${api.hotelPre}/${param.hotelId}/detail`,
        method: 'GET',
    })
}
export function findHotelAPI(hotelName) {
    return axios({
        url:`${api.hotelPre}/${hotelName}/findHotel`,
        method:'GET',
    })

}
export function addRoomNumAPI(data) {
    return axios({
        url: `${api.hotelPre}/${data.roomId}/addNum`,
        method: 'POST',
        data,
    })
}
export function subRoomNumAPI(data) {
    return axios({
        url: `${api.hotelPre}/${data.roomId}/subNum`,
        method: 'POST',
        data,
    })
}
export function getTargetHotelAPI(data) {
    return axios({
        url: `${api.hotelPre}/${data.userId}/getTargetHotel`,
        method: `GET`,
    })
}
export function hotelSearchAPI(params){
    return axios({
        url: `${api.hotelPre}/hotelSearch`,
        method:`GET`,
        params
    })
}

export function getBizRegionsAPI() {
    return axios({
        url: `${api.hotelPre}/getBizRegions`,
        method: 'GET',
    })
}

export function hasTypeAPI(data) {
    return axios({
        url:`${api.hotelPre}/${data}/hasType`,
        method:`GET`,
    })

}