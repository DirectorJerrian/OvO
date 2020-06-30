import {
    addRoomAPI,
    addHotelAPI,
    deleteHotelAPI,
    updateHotelInfoAPI,
    updateHotelCoverAPI,
    executeOrderAPI,
} from '@/api/hotelManager'
import {
    getAllOrdersAPI,
    getTargetOrderAPI,
    deleteOrderOnlyAPI
} from '@/api/order'
import {
    hotelAllCouponsAPI,
    hotelTargetMoneyAPI,
} from '@/api/coupon'
import {
    getHotelByIdAPI,
    getTargetHotelAPI,
    hasTypeAPI,
    findHotelAPI
} from '@/api/hotel'
import {message} from 'ant-design-vue'

const hotelManager = {
    state: {
        targethotelList: [],
        targetorderList: [],
        addHotelParams: {
            name: '',
            address: '',
            bizRegion: 'XiDan',
            hotelStar: '',
            rate: 0,
            description: '',
            phoneNum: '',
            managerId: '',
        },
        addHotelModalVisible: false,
        addRoomParams: {
            roomType: '',
            hotelId: '',
            price: '',
            total: 0,
            curNum: 0,
        },

        addRoomModalVisible: false,
        couponVisible: false,
        modifyVisible: false,
        addCouponVisible: false,
        activeHotelId: 0,
        activeOrderId: 0,
        couponList: [],
        intro: "",
        hasBig:false,
        hasDouble: false,
        hasFam:false,
    },
    mutations: {
        set_TargetOderList: function (state, data) {
            state.targetorderList = data
            console.log(data)
        },
        set_addHotelModalVisible: function (state, data) {
            state.addHotelModalVisible = data
        },
        set_addHotelParams: function (state, data) {
            state.addHotelParams = {
                ...state.addHotelParams,
                ...data,
            }
        },
        set_userId: (state, data) => {
            state.userId = data
        },
        set_activeOrderId: function(state, data){
            state.activeOrderId = data
        },
        set_addRoomModalVisible: function (state, data) {
            state.addRoomModalVisible = data
        },
        set_addRoomParams: function (state, data) {
            state.addRoomParams = {
                ...state.addRoomParams,
                ...data,
            }
        },
        set_couponVisible: function (state, data) {
            state.couponVisible = data
        },
        set_activeHotelId: function (state, data) {
            state.activeHotelId = data
        },
        set_modifyVisible: function (state, data) {
            state.modifyVisible = data
        },
        set_couponList: function (state, data) {
            state.couponList = data
        },
        set_addCouponVisible: function (state, data) {
            state.addCouponVisible = data
        },
        set_targetHotelList: function (state, data) {
            state.targethotelList = data
        },
        set_hasBig: function (state,data) {
            state.hasBig=data
        },
        set_hasDouble: function (state,data) {
            state.hasDouble=data
        },
        set_hasFam: function (state,data) {
            state.hasFam=data
        }
    },
    actions: {
        updateCover: async({state},data) => {
            console.log(data)
            const res = await updateHotelCoverAPI(data,state.activeHotelId)
            if(res){
                message.success('修改成功')
            }
        },
        getTargetOrders: async ({state, commit}) => {
            const data = {
                userId: Number(state.userId)
            }
            console.log(data.userId)
            const res = await getTargetOrderAPI(data)
            if (res) {
                commit('set_TargetOderList', res)
            }
        },
        getTargetHotel: async ({state, commit}) => {
            const data = {
                userId: Number(state.userId)
            }
            const res = await getTargetHotelAPI(data)
            if (res) {
                commit('set_targetHotelList', res)
            }
        },
        updateHotelInfo: async({ state, dispatch}, data) => {
            const params = {
                hotelId: state.activeHotelId,
                ...data,
            }
            const res = await updateHotelInfoAPI(params)
            if(res){
                message.success('修改成功')
                dispatch('getTargetHotel')
            }
        },
        addHotel: async ({state, dispatch, commit}) => {
            const res = await addHotelAPI(state.addHotelParams)
            if (res) {
                dispatch('getTargetHotel')
                commit('set_addHotelParams', {
                    name: '',
                    address: '',
                    bizRegion: 'XiDan',
                    hotelStar: '',
                    rate: 0,
                    description: '',
                    phoneNum: '',
                    managerId: '',
                })
                commit('set_addHotelModalVisible', false)
                message.success('添加成功')
            } else {
                message.error('添加失败')
            }
        },
        addNewRoom: async ({state, dispatch, commit}) => {
            const res = await addRoomAPI(state.addRoomParams)
            if (res) {
                commit('set_addRoomModalVisible', false)
                commit('set_addRoomParams', {
                    roomType: '',
                    hotelId: '',
                    price: '',
                    total: 0,
                    curNum: 0,
                })
                message.success('添加成功')
            } else {
                message.error(res)
            }
        },
        deleteHotel: async ({state, dispatch, commit}) => {
            const res = await deleteHotelAPI({
                hotelId: state.activeHotelId
            })
            if (res) {
                dispatch('getTargetHotel')
                message.success('删除成功')
            } else {
                message.error('删除失败')
            }
        },
        getHotelCoupon: async ({state, commit}) => {
            const res = await hotelAllCouponsAPI(state.activeHotelId)
            if (res) {
                // 获取到酒店策略之后的操作（将获取到的数组赋值给couponList)
                commit('set_couponList', res)
            }
        },
        getHotelInfo: async ({commit, state}) => {
            const res = await getHotelByIdAPI({
                hotelId: state.activeHotelId
            })
            if (res) {
                commit('set_currentHotelInfo', res)
            }
        },
        addHotelCoupon: async ({commit, dispatch}, data) => {
            const res = await hotelTargetMoneyAPI(data)
            if (res) {
                // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                commit('set_addCouponVisible', false)
                message.success('添加成功')
            } else {
                // 添加失败后的操作
                message.error('添加失败')
            }
        },
        executeOrder: async({state,dispatch}) => {
            const res = await executeOrderAPI(state.activeOrderId)
            console.log(res)
            if(res.success){
                dispatch('getTargetOrders')
                message.success('入住成功')
            }else{
                message.error(res.message)
            }
        },
        hasType: async ({state,commit})=>{
            const res=await hasTypeAPI(state.activeHotelId)
            if(res){
                console.log(res)
                commit('set_hasBig',res[0])
                commit('set_hasDouble',res[1])
                commit('set_hasFam',res[2])
            }
         },
        deleteOrderOnly: async ({state,dispatch,commit})=>{
            const res= await deleteOrderOnlyAPI(state.activeOrderId)
            if(res){
                console.log(res)
                dispatch('getTargetOrders')
            }
        },
        findDetail: async ({state,commit},data)=>{
            console.log(data)
            const res=await findHotelAPI(data)
            if(res){
                commit('set_activeHotelId',res)
            }
        }
    },
}
export default hotelManager