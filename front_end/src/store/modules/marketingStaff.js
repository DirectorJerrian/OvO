
import {
    revokeOrderAPI,
    restoreOrderAPI,
    creditChargeAPI,
} from "../../api/Marketingstaff";

import{
    getAllVoucherAPI,
    addVoucherAPI,
    addVoucherToAllAPI
} from "../../api/voucher";

import { message } from 'ant-design-vue'
import {getStrangeOrdersAPI} from "../../api/order";

const marketingStaff = {
    state: {
        targetorderList:[],
        couponVisible: false,
        addAllVoucherVisible: false,
        couponList: [],
        allVoucherList:[],
        chargeParams:{
            email:'',
            phoneNumber:'',
            money:'',
        }

    },
    mutations: {
        set_TargetOderList: function(state, data) {
            state.targetorderList = data
            console.log(data)
        },

        set_userId: (state, data) => {
            state.userId = data
        },

        set_allVoucherList: function (state,data) {
            state.allVoucherList=data
            console.log(data)
        },
        set_addAllVoucherVisible: function (state,data) {
            state.addAllVoucherVisible=data
        }

    },
    actions: {
        getStrangeOrders: async({ state, commit }) => {
            const data={
                userId:Number(state.userId)
            }
            console.log(data.userId)
            const res = await getStrangeOrdersAPI()
            if(res){
                commit('set_TargetOderList', res)
            }
        },
        addVouchertoAll: async ({state,dispatch,commit},data)=>{
            console.log(data)
            const res=await addVoucherToAllAPI(data)
            if(res){
                dispatch('getAllVoucher')
                console.log(res)
            }
        },
        getAllVoucher: async ({state,commit})=>{
          const res=await getAllVoucherAPI()
            if(res){
                commit('set_allVoucherList',res)
            }
        },
        revokeOrder: async({ state, dispatch }, orderId) => {
            const res = await revokeOrderAPI(orderId)
            if(res) {
                dispatch('getStrangeOrders')
                await message.success('撤销成功')
            }else{
                await message.error('撤销失败')
            }
        },
        restoreOrder: async({ state, dispatch }, orderId) => {
            const res = await restoreOrderAPI(orderId)
            if(res) {
                dispatch('getStrangeOrders')
                await message.success('恢复成功')
            }else{
                await message.error('恢复失败')
            }
        },
        creditCharge: async({state,commit},data)=>{
            state.chargeParams.email=data.email
            state.chargeParams.phoneNumber=data.phoneNumber
            state.chargeParams.money=data.money
            const res = await creditChargeAPI(state.chargeParams)
            if(res){
                commit('set_addManagerParams',{
                    email:'',
                    phoneNumber:'',
                    money:'',
                })
                await message.success("充值成功")
            }else{
                await message.error("充值失败")
            }
        },
        addVoucher: async ({state,dispatch,commit},data)=>{
            const res=await addVoucherAPI(data)
            if(res){
                dispatch('getAllVoucher')
                commit('set_addAllVoucherVisible',false)
                message.success('添加成功')
            }else{
                message.error('添加失败')
            }
        }


    }
}
export default marketingStaff