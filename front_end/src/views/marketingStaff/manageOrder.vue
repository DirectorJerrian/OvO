<template>
    <div class="manageOrder-wrapper">
        <a-tabs>
        <a-tab-pane tab="订单管理" key="'1">
            <a-table
                :columns="columns"
                :dataSource="strangeOrderList"
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

                        <a-popconfirm
                                title="确定想取消该订单吗？"
                                @confirm="mRevokeOrder(record.id)"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger" size="small">取消订单</a-button>
                        </a-popconfirm>
                    <a-divider type="vertical"></a-divider>
                    <a-popconfirm
                            title="确定想恢复该订单吗？"
                            @confirm="mRestoreOrder(record.id)"
                            okText="确定"
                            cancelText="取消"
                    >
                            <a-button type="danger" size="small">恢复订单</a-button>
                        </a-popconfirm>
                    </span>
            </a-table>

        </a-tab-pane>
        </a-tabs>
    </div>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    const moment = require('moment')
    const columns= [
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
        {
            title: '操作',
            key: 'action',
            scopedSlots: { customRender: 'action' },
        },
    ];
    export default {
        name: "manageOrder",
        data(){
            return {
                formLayout: 'horizontal',
                pagination: {},
                columns,
                form: this.$form.createForm(this, { name: 'manageOrder' }),
            }
        },
        computed: {
            ...mapGetters([
                'strangeOrderList',
                'userId'
            ]),
        },
        async mounted() {
            await this.getStrangeOrders()
        },
        methods:{
            ...mapMutations([

            ]),
            ...mapActions([
                'getStrangeOrders',
                'revokeOrder',
                'restoreOrder',
            ]),
            mRevokeOrder(id){
                console.log(id)
                this.revokeOrder(Number(id))
            },
            mRestoreOrder(id){
                console.log(id)
                this.restoreOrder(Number(id))
            }

        }
    }
</script>

<style scoped lang="less">
    .manageOrder-wrapper {
        padding: 50px;
        border-radius: 36px;
        background: transparent;
        z-index: 1;
        margin-top: 50px;
    }

</style>
<style lang="less">
    .manageHotel-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">
    .manageOrder-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">

</style>