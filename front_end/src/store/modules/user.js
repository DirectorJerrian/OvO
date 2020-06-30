import Vue from 'vue'
import qs from 'qs'
import router from '@/router'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'
import { message } from 'ant-design-vue'
import { axios } from '@/utils/request'
import {
    loginAPI,
    registerAPI,
    getUserInfoAPI,
    updateUserInfoAPI,
    updateUserPasswordAPI,
    getUserCreditRecordAPI,
    updateUserAvatarAPI,
    registerVIPAPI,
    updateVIPSavingsAPI,
    getVIPInfoAPI,
    setSavingsAPI,
} from '@/api/user'

import {
    getUserOrdersAPI,
    cancelOrderAPI,
    orderRateAPI,
    getUserHotelOrdersAPI,
} from '@/api/order'


import{
    userVoucherAPI,
    userAvailableVoucherAPI,
    useVoucherAPI,
} from "../../api/voucher";
import {restoreOrderAPI} from "../../api/Marketingstaff";

const getDefaultState = () => {
    return {
        userId: '',

        userInfo: {

        },
        VIPInfo:{

        },
        userOrderList: [

        ],
        userCreditRecord: [

        ],
        currentOrderInfo:[

        ],
        viewModalVisible:false,
        userVoucherList:[

        ],
        userAvailableVoucherList:[

        ],
        UserHotelOrderList:[],

    }
}

const user = {
    state : getDefaultState(),

    mutations: {
        reset_state: function(state) {
            state.token = '',
                state.userId = '',
                state.userInfo = {

                },
                state.userOrderList = []
        },
        reset_userAvailableVoucherList:function(state) {
             state.userAvailableVoucherList = []
        },
        set_token: function(state, token){
            state.token = token
        },
        set_email: (state, data) => {
            state.email = data
        },
        set_userId: (state, data) => {
            state.userId = data
        },
        set_userInfo: (state, data) => {
            state.userInfo = {
                ...state.userInfo,
                ...data
            }
        },
        set_VIPInfo: (state, data) => {
            state.VIPInfo = {
                ...state.VIPInfo,
                ...data
            }
        },
        set_userOrderList: (state, data) => {
            state.userOrderList = data
        },
        set_userCreditRecord: (state, data) =>{
            state.userCreditRecord = data
        },
        set_viewModalVisible: (state,data)=>{
            state.viewModalVisible=data
        },
        set_currentOrderInfo:(state,data)=> {
            state.currentOrderInfo = {
                ...state.currentOrderInfo,
                ...data,
            }
        },

        set_userVoucherList:(state,data)=>{
            state.userVoucherList=data
        },
        set_userAvailableVoucherList:(state,data)=>{
            state.userAvailableVoucherList=data
            console.log(state.userAvailableVoucherList.length)
        },
        set_UserHotelOrderList:(state,data)=>{
            state.UserHotelOrderList=data
        }

    },

    actions: {
        login: async ({ dispatch, commit }, userData) => {
            const res = await loginAPI(userData)
            if(res){
                setToken(res.id)
                commit('set_userId', res.id)
                dispatch('getUserInfo')
                if(res.userType === 'Marketingstaff'){
                    router.push('/marketingStaff/manageOrder')
                }
                else{
                    router.push('/hotel/hotelList')
                }

            }
        },
        register: async({commit }, data) => {
            const res = await registerAPI(data)
            if(res){
                message.success('注册成功')
            }
        },
        registerVIP: async({ dispatch,commit }, data) => {
            const res = await registerVIPAPI(data)
            if(res){
                message.success('VIP注册成功')
                dispatch('getUserInfo')
                dispatch('getVIPInfo')
            }
        },
        getUserInfo({ state, commit }) {
            return new Promise((resolve, reject) => {
                getUserInfoAPI(state.userId).then(response => {
                    const data = response
                    if (!data) {
                        reject('登录已过期，请重新登录')
                    }
                    commit('set_userInfo', data)
                    commit('set_userId', data.id)

                    resolve(data)
                }).catch(error => {
                    reject(error)
                })
            })
        },
        getVIPInfo({ state, commit }) {
            return new Promise((resolve, reject) => {
                getVIPInfoAPI(state.userId).then(response => {
                    const data = response
                    if (!data) {
                        reject('非会员用户')
                    }
                    commit('set_VIPInfo', data)
                    resolve(data)
                }).catch(error => {
                    reject(error)
                })
            })
        },
        userRecharge: async({ state, dispatch }, data) => {
            const params = {
                cardId:state.VIPInfo.cardId,
                userId: state.userId,
                ...data,
            }
            const res = await updateVIPSavingsAPI(params)
            if(res){
                message.success('充值成功')
                dispatch('getVIPInfo')
            }
        },
        updateUserInfo: async({ state, dispatch }, data) => {
            const params = {
                id: state.userId,
                ...data,
            }
            const res = await updateUserInfoAPI(params)
            if(res){
                message.success('修改成功')
                dispatch('getUserInfo')
            }
        },
        updateAvatar: async({state},data) => {
/*            let fd = new FormData(); //通过form数据格式来传\
            fd.append("file", data.myAvatar);*/
            const res = await updateUserAvatarAPI(data,state.userId)
            if(res){
                message.success('修改成功')
            }
        },
        updateUserPassword: async({ state, dispatch }, data) => {
            const params = {
                id: state.userId,
                ...data,
            }
            const res = await updateUserPasswordAPI(params)
            if(res){
                message.success('修改成功')
                dispatch('getUserInfo')
            }
        },
        orderRate:async({ state, dispatch }, data) => {
            const res = await orderRateAPI(data)
            if(res){
                dispatch('getUserOrders')
                message.success('评价成功')

            }
        },
        getUserOrders: async({ state, commit }) => {
            const data = {
                userId: Number(state.userId)
            }
            console.log(state.userInfo)
            const res = await getUserOrdersAPI(data)
            if(res){
                commit('set_userOrderList', res)
            }
        },
        getUserCreditRecord: async({state,commit})=>{
            const data = {
                id: state.userId,
            }
            console.log(state.userCreditRecord)
            const res = await getUserCreditRecordAPI(data)
            if(res){
                commit('set_userCreditRecord',res)
            }
        },
        cancelOrder: async({ state, dispatch }, orderId) => {
            const res = await cancelOrderAPI(orderId)
            if(res) {
                dispatch('getUserOrders')
                message.success('撤销成功')
            }else{
                message.error('撤销失败')
            }
        },
        getUserHotelOrders: async ({state,commit},data)=>{
            const res = await getUserHotelOrdersAPI({
                hotelId: data,
                userId:Number(state.userId)
            })
            if(res){
            commit('set_UserHotelOrderList', res)
            }
        },
        logout: async({ commit }) => {
            removeToken()
            resetRouter()
            commit('reset_state')
        },
        // remove token
        resetToken({ commit }) {
            return new Promise(resolve => {
                removeToken() // must remove  token  first
                commit('reset_state')
                resolve()
            })
        },
        getUserVoucherList: async({ state, commit }) => {
            const data = {
                userId: Number(state.userId)
            }
            console.log(data.userId)
            const res = await userVoucherAPI(data.userId)
            if(res){
                commit('set_userVoucherList', res)
            }
        },
        getUserAvailableVoucherList: async({ state, commit },data) => {
            console.log("1231")
            console.log(data)
            const res = await userAvailableVoucherAPI(data)

            if(res){
                commit('set_userAvailableVoucherList', res)
            }
        },
        useVoucher: async({ state, dispatch }, useVoucherId) => {
            const res = await useVoucherAPI(useVoucherId)

            if(res) {
                await message.success('优惠券使用成功')
            }else{
                await message.error('优惠卷使用失败')
            }
        },
        setUserSavings: async({ state, dispatch }, savings) => {
            console.log(savings)
            const res = await setSavingsAPI(savings)
            if(res) {
                await message.success('储蓄值使用成功')
            }else{
                await message.error('储蓄值使用失败')
            }
        },
        clearAvailableVoucher: async({ state, commit }) => {

                commit('reset_userAvailableVoucherList', )

        },
    }
}

export default user