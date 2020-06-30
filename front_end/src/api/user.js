import { axios } from '@/utils/request'
import qs from "qs";

const api = {
    userPre: '/api/user'
}
export function loginAPI(data){
    return axios({
        url:`${api.userPre}/login`,
        method: 'POST',
        data
    })
}
export function registerAPI(data){
    return axios({
        url: `${api.userPre}/register`,
        method: 'POST',
        data
    })
}
export function getUserInfoAPI(id){
    return axios({
        url: `${api.userPre}/${id}/getUserInfo`,
        method: 'GET'
    })
}
export function updateUserInfoAPI(data) {
    return axios({
        url: `${api.userPre}/${data.id}/userInfo/update`,
        method: 'POST',
        data
    })
}
export function updateUserPasswordAPI(data) {
    return axios({
        url: `${api.userPre}/${data.id}/userInfo/updatePassword`,
        method: 'POST',
        data
    })
}
export function getUserCreditRecordAPI(data) {
    return axios({
        url: `${api.userPre}/${data.id}/userCreditRecords`,
        method: 'GET',
    })

}
export function updateUserAvatarAPI(data,id) {
    let fd = new FormData(); //通过form数据格式来传\
    fd.append("file", data.myAvatar);
    console.log(fd.get('file'));
    return axios({
        withCredentials: true,
        headers: {
            'Content-Type':'application/x-www-form-urlencoded'
        },
/*        processData: false, //processData 默认为false，当设置为true的时候,jquery ajax 提交的时候不会序列化 data，而是直接使用data
        contentType: false,*/
        url: `${api.userPre}/${id}/userInfo/updateAvatar`,
        method: 'POST',
        data: fd
    })
}
export function updateVIPSavingsAPI(data) {
    return axios({
        url: `${api.userPre}/${data.userId}/vip/updateVIPSavings`,
        method: 'POST',
        data
    })
}
export function registerVIPAPI(data){
    return axios({
        url: `${api.userPre}/registerVIP`,
        method: 'POST',
        data
    })
}
export function getVIPInfoAPI(id){
    return axios({
        url: `${api.userPre}/${id}/getVIPInfo`,
        method: 'GET'
    })
}
export function setSavingsAPI(data){
    return axios({
        url: `${api.userPre}/setSavings`,
        method: 'POST',
        data
    })
}