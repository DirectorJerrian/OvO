import {
    getManagerListAPI,
    addManagerAPI,
    deleteManagerAPI,
} from '@/api/admin'
import { message } from 'ant-design-vue'

const admin = {
    state: {
        managerList: [

        ],
        addManagerModalVisible: false,
        addManagerParams: {
            email:'',
            password:''
        },
        deleteManagerParams:{
            id:0
        }
    },
    mutations: {
        set_managerList: function(state, data) {
            state.managerList = data
        },
        set_addManagerModalVisible: function(state, data) {
            state.addManagerModalVisible = data
        },
        set_addManagerParams: function(state, data) {
            state.addManagerParams = {
                ...state.addManagerParams,
                ...data,
            }
        }
    },
    actions: {
        deleteManager: async({ state, dispatch }, userId) => {
            state.deleteManagerParams.id=userId
            const res = await deleteManagerAPI(state.deleteManagerParams)
            if(res) {
                dispatch('getManagerList')
                message.success('删除成功')
            }else{
                message.error('删除失败')
            }
        },
        getManagerList: async({ commit }) => {
            const res = await getManagerListAPI()
            if(res){
                commit('set_managerList', res)
            }
        },
        addManager: async({ state, commit, dispatch }) => {
            const res = await addManagerAPI(state.addManagerParams)
            if(res) {
                commit('set_addManagerParams',{
                    email:'',
                    password:''
                })
                commit('set_addManagerModalVisible', false)
                message.success('添加成功')
                dispatch('getManagerList')
            }else{
                message.error('添加失败')
            }
        }
    }
}
export default admin