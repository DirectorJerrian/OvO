import {message} from 'ant-design-vue'
import store from '@/store'
import {
    getHotelsAPI,
    addRoomNumAPI,
    subRoomNumAPI,
    getHotelByIdAPI,
    hotelSearchAPI,
    getBizRegionsAPI,
} from '@/api/hotel'
import {
    reserveHotelAPI,
    getHotelOrdersAPI
} from '@/api/order'
import {
    orderMatchCouponsAPI,
} from '@/api/coupon'
import hotelList from "../../views/hotel/hotelList";

const hotel = {
    state: {
        hotelList: [],
        hotelListParams: {
            pageNo: 0,
            pageSize: 12
        },
        hotelListLoading: true,
        currentHotelId: '',
        currentHotelInfo: {},
        orderModalVisible: false,
        searchModalVisible: false,
        orderMatchCouponList: [],
        currentOrderRoom: {},
        hotelOrderList:[],
        orderPass:false,
        searchParams:{
            hotelName: '',
            bizRegion: '',
            roomType: '',
            hotelStar: '',
            rate: '',
        },
        bizRegions: [],
    },
    mutations: {
        set_hotelList: function (state, data) {
            state.hotelList = data
        },
        set_bizRegions: function(state,data){
            state.bizRegions = data
        },
        set_hotelListParams: function (state, data) {
            state.hotelListParams = {
                ...state.hotelListParams,
                ...data,
            }
        },
        set_hotelListLoading: function (state, data) {
            state.hotelListLoading = data
        },
        set_currentHotelId: function (state, data) {
            state.currentHotelId = data
        },
        set_currentHotelInfo: function (state, data) {
            state.currentHotelInfo = {
                ...state.currentHotelInfo,
                ...data,
            }
        },
        set_orderModalVisible: function (state, data) {
            state.orderModalVisible = data
        },
        set_searchModalVisible: function (state, data) {
            state.searchModalVisible = data
        },
        set_currentOrderRoom: function (state, data) {
            state.currentOrderRoom = {
                ...state.currentOrderRoom,
                ...data,
            }
        },
        set_orderMatchCouponList: function (state, data) {
            state.orderMatchCouponList = data
        },
        set_hotelOrderList: function (state,data) {
            state.hotelOrderList=data
        },
        set_searchParams: function (state,data) {
            state.searchParams ={
                ...state.searchParams,
                ...data,
            }
        }
    },

    actions: {
        getHotelList: async ({commit, state}) => {
            const res = await getHotelsAPI()
            if (res) {
                commit('set_hotelList', res)
                commit('set_hotelListLoading', false)
            }
        },
        getHotelById: async ({commit, state}) => {
            const res = await getHotelByIdAPI({
                hotelId: state.currentHotelId
            })
            if (res) {
                commit('set_currentHotelInfo', res)
            }
        },
        addOrder:  async ({state, commit}, data) => {
            const res =  await reserveHotelAPI(data)

            console.log(res)
            if (res) {
                message.success('预定成功')
                return true
            }else{
                return false
            }

        },
        addNum: async ({state,commit},data)=>{
            const res=await addRoomNumAPI(data)
            console.log(res)
        },
        subNum: async ({state,commit},data)=>{
            const res=await subRoomNumAPI(data)
            console.log(res)
        },

        getOrderMatchCoupons: async({ state, commit }, data) => {
            const res = await orderMatchCouponsAPI(data)
            if (res) {
                commit('set_orderMatchCouponList', res)
                console.log("得到coupon列表")
                console.log(state.orderMatchCouponList)
            }else{
                console.log("没得到coupon列表")
            }
        },
        getHotelOrders: async ({state,commit},data)=>{
            const res = await getHotelOrdersAPI({
                hotelId: state.currentHotelId
            })
            if(res){
                commit('set_hotelOrderList', res)
            }

        },
        searchHotel: async ({state, dispatch, commit},data) => {
            const res = await hotelSearchAPI(data)
            if (res) {
                commit('set_searchParams', {
                        hotelName: '',
                        bizRegion: '',
                        roomType: '',
                        hotelStar: '',
                        rate: '',
                })
                commit('set_searchModalVisible', false)
                commit('set_hotelList',res)
            }
        },
        getBizRegions: async({commit,state}) =>{
            const res = await getBizRegionsAPI()
            if(res){
                commit('set_bizRegions',res)
            }
        },
        StarSort: ({commit,state}) =>{
            var res = state.hotelList
            for(let i=0; i<res.length-1; i++){
                for(let j=0; j<res.length-1-i; j++){
                    if(res[j].hotelStar==="Five" || res[j].hotelStar==="Four"&&res[j+1].hotelStar==="Three"){
                        const temp = res[j];
                        res[j] = res[j+1]
                        res[j+1] = temp
                    }
                }
            }
            commit('set_hotelListLoading',true)
            commit('set_hotelList',res)
            commit('set_hotelListLoading',false)
        },
        RateSort: ({commit,state}) =>{
            var res = state.hotelList
            for(let i=0; i<res.length-1; i++){
                for(let j=0; j<res.length-1-i; j++){
                    if(res[j].rate<res[j+1].rate){
                        const temp = res[j];
                        res[j] = res[j+1]
                        res[j+1] = temp
                    }
                }
            }
            commit('set_hotelListLoading',true)
            commit('set_hotelList',res)
            commit('set_hotelListLoading',false)
        },
    }
}

export default hotel