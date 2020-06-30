<template>
    <a-layout>
        <a-layout-content>
            <div class="hotelDetailCard">
                <h1>
                    {{ currentHotelInfo.title }}
                </h1>
                <div class="hotel-info">
                    <a-card class="hotelCard" style="height: 400px">
                        <img v-if="currentHotelInfo.cover===null"
                             alt="example"
                             src="@/assets/cover.jpeg"
                             slot="cover"
                             referrerPolicy="no-referrer"
                        />
                        <img v-else
                             alt="example"
                             :src=currentHotelInfo.cover
                             slot="cover"
                             referrerPolicy="no-referrer"
                        />
                    </a-card>
                    <div class="info">
                        <div class="items" v-if="currentHotelInfo.name">
                            <span class="label">酒店名称：</span>
                            <span class="value">{{ currentHotelInfo.name }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.address">
                            <span class="label">地址</span>
                            <span class="value">{{ currentHotelInfo.address }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.rate">
                            <span class="label">评分:</span>
                            <span class="value">{{ currentHotelInfo.rate }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.hotelStar">
                            <span class="label">星级:</span>
                            <a-rate style="font-size: 15px" :value="getStar(currentHotelInfo.hotelStar)" disabled allowHalf/>
                        </div>
                        <div class="items" v-if="currentHotelInfo.description">
                            <span class="label">酒店简介:</span>
                            <span class="value">{{ currentHotelInfo.description }}</span>
                        </div>
                    </div>
                </div>
                <a-divider></a-divider>
                <a-tabs>
                    <a-tab-pane tab="房间信息" key="1">
                        <RoomList :rooms="currentHotelInfo.rooms"></RoomList>
                    </a-tab-pane>
                    <a-tab-pane tab="酒店详情" key="2">
                        <div class="a" v-if="currentHotelInfo.bizRegion">
                            <span class="label">商圈:</span>
                            <span class="value">{{ currentHotelInfo.bizRegion }}</span>
                        </div>
                        <div class="a" v-if="currentHotelInfo.phoneNum">
                            <span class="label">联系电话:</span>
                            <span class="value">{{ currentHotelInfo.phoneNum }}</span>
                        </div>
                        <a-table class="orders"
                            :columns="columns"
                            :dataSource="UserHotelOrderList"
                            bordered
                        >
                        </a-table>
                    </a-tab-pane>
                </a-tabs>
            </div>
        </a-layout-content>
    </a-layout>
</template>
<script>
import { mapGetters, mapActions, mapMutations } from 'vuex'
import RoomList from './components/roomList'
const columns=[
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
        title: '状态',
        filters: [{ text: '已预订', value: '已预订' }, { text: '已撤销', value: '已撤销' }, { text: '已入住', value: '已入住' }],
        onFilter: (value, record) => record.orderState.includes(value),
        dataIndex: 'orderState',
        scopedSlots: { customRender: 'orderState' }
    },
]
export default {
    name: 'hotelDetail',
    components: {
        RoomList,
    },
    data() {
        return {
            columns,
        }
    },
    computed: {
        ...mapGetters([
            'currentHotelInfo',
            'UserHotelOrderList',
            'currentHotelId',
        ])
    },
    mounted() {
        this.set_currentHotelId(Number(this.$route.params.hotelId))
        this.getHotelById()
        this.getUserHotelOrders(this.currentHotelId)
    },
    beforeRouteUpdate(to, from, next) {
        this.set_currentHotelId(Number(to.params.hotelId))
        this.getHotelById()
        this.getUserHotelOrders(this.currentHotelId)
        next()
    },
    methods: {
        ...mapMutations([
            'set_currentHotelId',
        ]),
        ...mapActions([
            'getHotelById',
            'getUserOrders',
            'getUserHotelOrders'
        ]),
        getStar(data){
            if(data=="Four") return 4;
            else if(data=="Five") return 5;
            else return 3;

        }
    }
}
</script>
<style scoped lang="less">
    .hotelDetailCard {
        padding: 50px 50px;
        background-color: #fff;
        /*background-color: rgba(233,243,250);*/

    }
    .orders{
        margin-top: 20px;
    }
    .hotel-info {
        display: flex;
        align-items: stretch;
        justify-content: flex-start;
        .hotelCard{
            img{
                height: 400px;
                width: 240px;
                display:flex;
                align-items:center;
                justify-content:center;
            }
        }
        .info{
            padding: 10px 0;
            display: flex;
            flex-direction: column;
            margin-left: 20px;
            .items {
                display: flex;
                align-items: center;
                margin-bottom: 10px;
                padding-bottom: 20px;
                .label{
                    display: inline-block;
                    width: 200px;
                    padding-left: 30px;;
                    margin-right: 10px;
                    font-size: 22px;
                    font-weight: 400;
                }
                .value {
                    display: inline-block;
                    margin-right: 20px;
                    line-height: 50px;
                    font-size: 16px;
                    padding-left: 30px;
                }
            }
        }
    }
    .a{
        padding-bottom: 30px;
        padding-top: 10px;
        .label{
            display: inline-block;
            width: 100px;
            padding-left: 18px;
            font-size: 14px;
        }
        .value{
            font-size: 14px;
        }
    }
</style>