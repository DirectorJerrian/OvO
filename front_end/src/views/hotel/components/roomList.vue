<template>
    <div class="room-list">
        <div class="filter">

        </div>
        <div class="list">
            <a-table
                :columns="columns"
                :dataSource="rooms"
            >
                <span slot="price" slot-scope="text">
                    <span>￥{{ text }}</span>
                </span>
                <span slot="action" slot-scope="text, record" >
                    <a-button type="primary" @click="order(record)" v-if="userInfo.userType=='Client'">预定</a-button>
                    <a-button-group v-if="userInfo.userType=='HotelManager'">
                      <a-button icon="plus" @click="handleAdd(record)"/>
                      <a-button icon="minus" @click="handleSub(record)"/>
                    </a-button-group>
                </span>
            </a-table>
        </div>
        <OrderModal style="width: 70%"></OrderModal>
    </div>
</template>
<script>
import { mapGetters, mapActions, mapMutations } from 'vuex'
import OrderModal from './orderModal'
const columns = [
    {  
      title: '房型',
      dataIndex: 'roomType',
      key: 'roomType',
    },
    {
      title: '当前房间数量',
      dataIndex: 'curNum',
      key: 'curNum',
    },
    {
      title: '房间总数',
      dataIndex: 'total',
      key: 'total',
    },
    {
      title: '房价',
      key: 'price',
      dataIndex: 'price',
      scopedSlots: { customRender: 'price'}
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
  ];
const columns1 = [
    {
        title: '勾选',
        dataIndex: 'id',
        scopedSlots: {customRender: 'id'}

    },
    {
        title: '优惠描述',
        dataIndex: 'description',

    },
    {
        title: '会员专享折扣',
        dataIndex: 'discount',
    },

    {
        title: '剩余数量',
        dataIndex: 'number',

    },
    {
        title: '优惠金额',
        dataIndex: 'discount_money',
    },
];
export default {
    name:'roomList',
    props: {
        rooms: {
            type: Array
        }
    },
    data() {
        return {
            columns,
            columns1
        }
    },
    components: {
        OrderModal
    },
    computed: {
        ...mapGetters([
            'orderModalVisible',
            'userInfo',
            'userId'
        ])
    },
    monuted() {
    },
    methods: {
        ...mapMutations([
            'set_orderModalVisible',
            'set_currentOrderRoom'
        ]),
        ...mapActions([
            'addNum',
            'subNum'
        ]),
        order(record) {
            this.set_currentOrderRoom(record)
            this.set_orderModalVisible(true)
        },
        handleAdd(record){
            console.log(record)
            const data={
                roomId:record.id
            }
            record.total++
            record.curNum++
            this.addNum(data)
        },
        handleSub(record){
            const data={
                roomId:record.id
            }
            if(record.total>0) {
                record.curNum--
                record.total--
                this.subNum(data)
            }
        }
    }
}
</script>