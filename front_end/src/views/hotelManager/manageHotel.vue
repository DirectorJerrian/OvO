<template>
    <div class="manageHotel-wrapper">
        <a-tabs>
            <a-tab-pane tab="酒店管理" key="1">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addHotel"><a-icon type="plus" />添加酒店</a-button>
                </div>
                <a-table
                    :columns="columns1"
                    :dataSource="targethotelList"
                    bordered
                >
                    <span slot="action" slot-scope="record" style="display: inline-block;width: 200px;">
                        <a-button type="primary" size="small" @click="addRoom(record)">录入房间</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-button type="info" size="small" @click="showCoupon(record)">优惠策略</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-button type="info" size="small" @click="showModify(record)">修改信息</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-popconfirm
                            title="确定想删除该酒店吗？"
                            @confirm="deletehotel(record)"
                            okText="确定"
                            cancelText="取消"
                        >
                            <a-button type="danger" size="small">删除酒店</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="订单管理" key="2">
                <a-table
                    :columns="columns2"
                    :dataSource="targetorderList"
                    bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="roomType" slot-scope="text">
                        <span v-if="text == 'BigBed'">大床房</span>
                        <span v-if="text == 'DoubleBed'">双床房</span>
                        <span v-if="text == 'Family'">家庭房</span>
                    </span>
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="showModal(record)">订单详情</a-button>
                        <a-modal
                                :visible="visible"
                                title="订单详情"
                                okText="确认"
                                @ok="closeView"
                                @cancel="cancel"
                                footer=""
                        >
                            <div class="a">
                                <span class="l">订单号:</span>
                                <span class="r">{{currentOrderInfo.id}}</span>
                            </div>
                            <div class="a">
                                <span class="l">酒店名称:</span>
                                <span class="r">{{currentOrderInfo.hotelName}}</span>
                            </div>
                            <div class="a">
                                <span class="l">房型:</span>
                                <span class="r">{{currentOrderInfo.roomType}}</span>
                            </div>
                            <div class="a">
                                <span class="l">房间数:</span>
                                <span class="r">{{currentOrderInfo.roomNum}}</span>
                            </div>
                            <div class="a">
                                <span class="l">入住时间:</span>
                                <span class="r">{{currentOrderInfo.checkInDate}}</span>
                            </div>
                            <div class="a">
                                <span class="l">离店时间:</span>
                                <span class="r">{{currentOrderInfo.checkOutDate}}</span>
                            </div>
                            <div class="a">
                                <span class="l">订单状态:</span>
                                <span class="r">{{currentOrderInfo.orderState}}</span>
                            </div>
                            <div class="a">
                                <span class="l">价格:</span>
                                <span class="r">{{currentOrderInfo.price}}</span>
                            </div>
                        </a-modal>
                        <a-divider type="vertical"></a-divider>
                        <a-popconfirm
                            title="确定想删除该订单吗？"
                            @confirm="deleteOrder(record)"
                            okText="确定"
                            cancelText="取消"
                        >
                            <a-button v-if="record.orderState==='已预订'" type="danger" size="small" >删除订单</a-button>
                        </a-popconfirm>
                        <a-divider type="vertical"></a-divider>
                        <a-button size="small" @click="executeorder(record)"
                                  v-if="record.orderState==='已预订'">
                            办理入住
                        </a-button>
                    </span>
                </a-table>
            </a-tab-pane>
        </a-tabs>
        <AddHotelModal></AddHotelModal>
        <AddRoomModal></AddRoomModal>
        <Coupon></Coupon>
        <ModifyInfo></ModifyInfo>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddHotelModal from './components/addHotelModal'
import AddRoomModal from './components/addRoomModal'
import Coupon from './components/coupon'
import ModifyInfo from "./components/modifyInfo";
const moment = require('moment')
const columns1 = [
    {  
        title: '酒店名',
        dataIndex: 'name',
    },
    {
        title: '商圈',
        dataIndex: 'bizRegion',
    },
    {
        title: '地址',
        dataIndex: 'address',
    },
    {
        title: '酒店星级',
        dataIndex: 'hotelStar'
    },
    {
        title: '评分',
        dataIndex: 'rate',
    },
    {
        title: '简介',
        dataIndex: 'description',
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
  ];
const columns2 = [
    {  
        title: '订单号',
        dataIndex: 'id',
    },
    {  
        title: '酒店名',
        dataIndex: 'hotelName',
    },
    {
        title: '房型',
        dataIndex: 'roomType',
        scopedSlots: { customRender: 'roomType' }
    },
    {
        title: '入住时间',
        dataIndex: 'checkInDate',
        scopedSlots: { customRender: 'checkInDate' }
    },
    {
        title: '离店时间',
        dataIndex: 'checkOutDate',
        scopedSlots: { customRender: 'checkOutDate' }
    },
    {
        title: '入住人数',
        dataIndex: 'peopleNum',
    },
    {
        title: '房价',
        dataIndex: 'price',
    },
    {
        title: '订单状态',
        dataIndex: 'orderState',
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
  ];
export default {
    name: 'manageHotel',
    data(){
        return {
            modify:false,
            formLayout: 'horizontal',
            pagination: {},
            columns1,
            columns2,
            form: this.$form.createForm(this, { name: 'manageHotel' }),
            visible:false,
            currentOrderInfo:{},
        }
    },
    components: {
        ModifyInfo,
        AddHotelModal,
        AddRoomModal,
        Coupon,
    },
    computed: {
        ...mapGetters([
            'targetorderList',
            'targethotelList',
            'addHotelModalVisible',
            'addRoomModalVisible',
            'deleteHotelModalVisible',
            'activeHotelId',
            'couponVisible',
            'userId',
            'activeOrderId',
        ]),
    },
    async mounted() {
        await this.getTargetHotel()
        await this.getTargetOrders()
    },
    methods: {
        ...mapMutations([
            'set_addHotelModalVisible',
            'set_addRoomModalVisible',
            'set_couponVisible',
            'set_activeHotelId',
            'set_modifyVisible',
            'set_deleteHotelModalVisible',
            'set_activeOrderId',
        ]),
        ...mapActions([
            'getTargetHotel',
            'getTargetOrders',
            'getHotelCoupon',
            'getHotelInfo',
            'deleteHotel',
            'executeOrder',
            'hasType',
            'deleteOrderOnly',
            'findHotel'
        ]),
        addHotel() {
            this.set_addHotelModalVisible(true)
        },
        addRoom(record) {
            this.set_activeHotelId(record.id)
            this.hasType()
            this.set_addRoomModalVisible(true)
        },
        showCoupon(record) {
            this.set_activeHotelId(record.id)
            this.set_couponVisible(true)
            this.getHotelCoupon()
        },
        showModify(record) {
            this.set_activeHotelId(record.id)
            this.set_modifyVisible(true)
            this.getHotelInfo()
        },
        deletehotel(record){
            this.set_activeHotelId(record.id)
            this.deleteHotel()
        },
        deleteOrder(record){
            this.set_activeOrderId(record.id)
            this.deleteOrderOnly()
        },
        executeorder(record){
            this.set_activeOrderId(record.id)
            this.executeOrder()
        },
        showModal(record){
            this.currentOrderInfo=record
            this.visible=true
        },
        cancel(){
            this.visible=false
        },
        closeView(){
            this.visible=false
        }
    }
}
</script>
<style scoped lang="less">
    .manageHotel-wrapper {
        border-radius: 36px;
        background: transparent;
        z-index: 1;
        margin-top: 50px;
        padding: 50px;
        .chart {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px
        }
    }
</style>
<style lang="less">
    .manageHotel-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
    .a{
        padding-top: 10px;
        padding-bottom: 10px;
    }
    .l{
        display: inline-block;
        width: 100px;
        margin-left: 40px;
        font-size: 15px;
    }
    .r{
        margin-left: 10px;
        font-size: 15px;
    }
</style>
<style lang="less">
    
</style>