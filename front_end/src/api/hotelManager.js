import { axios } from '@/utils/request'
const api = {
    hotelPre: '/api/hotel',
    orderPre: `/api/order`,
}
export function addRoomAPI(data) {
    return axios({
        url: `${api.hotelPre}/roomInfo`,
        method: 'POST',
        data,
    })
}
export function addHotelAPI(data) {
    return axios({
        url: `${api.hotelPre}/addHotel`,
        method: 'POST',
        data,
    })
}
export function deleteHotelAPI(data) {
    return axios({
        url: `${api.hotelPre}/${data.hotelId}/deleteHotel`,
        method: 'POST',
    })
}
export function updateHotelInfoAPI(data) {
    return axios({
        url: `${api.hotelPre}/${data.hotelId}/hotelInfo/update`,
        method: 'POST',
        data
    })
}
export function updateHotelCoverAPI(data,id) {
    let fd = new FormData(); //通过form数据格式来传\
    fd.append("file", data.coverFile);
    console.log(fd.get('file'));
    return axios({
        withCredentials: true,
        headers: {
            'Content-Type':'application/x-www-form-urlencoded'
        },
        url: `${api.hotelPre}/${id}/hotelInfo/updateCover`,
        method: 'POST',
        data: fd
    })
}
export function executeOrderAPI(orderId){
    return axios({
        url:`${api.orderPre}/${orderId}/executeOrder`,
        method: `POST`,
    })
}