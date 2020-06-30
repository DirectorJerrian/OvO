<template>
    <a-modal
        :visible="viewModalVisible"
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
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: "viewModal",
        components: {},

        data(){
            return{

            }
        },
        computed:{
            ...mapGetters([
                'viewModalVisible',
                'currentOrderInfo'
            ]),
        },
        mounted(){
        },
        methods:{
            ...mapMutations([
                'set_viewModalVisible',
            ]),
            cancel(){
                console.log(this.currentOrderInfo)
                this.set_viewModalVisible(false)
            },
            closeView(){
                this.set_viewModalVisible(false)
            }
        }
    }
</script>

<style scoped>
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