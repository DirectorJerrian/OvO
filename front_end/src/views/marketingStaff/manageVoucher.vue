<template>
    <div class="manageVoucher-wrapper">
        <a-tabs>
            <a-tab-pane tab="优惠策略" key="1">
                <div style="width: 100%; text-align: right; margin:20px 0">
                                    <a-button type="primary" @click="addAllVoucher"><a-icon type="plus"/> </a-button>

                </div>
                <a-table
                    :columns="columns"
                    :dataSource="allVoucherList"
                    bordered

                >
                    <span class='add' slot="action" slot-scope="record">

                        <a-popconfirm v-if="record.status==='未添加'"
                                title="确定给所有人添加优惠券吗？"
                                @confirm="addVoucherToAll(record)"
                                okText="确定"
                                cancelText="取消"
                        >
                             <a-button type="primary" size="small">给所有用户添加</a-button>
                        </a-popconfirm>
                        <div v-else>已添加</div>
                    </span>
                </a-table>
            </a-tab-pane>
        </a-tabs>
        <addAllVoucher></addAllVoucher>
    </div>
</template>

<script>
import {mapGetters,mapActions,mapMutations} from 'vuex'
import addAllVoucher from "./components/addAllVoucher";
const columns=[
    {
        title: 'id',
        dataIndex: 'id',
    },
    {
        title: '折扣',
        dataIndex: 'discount',
    },
    {
        title: '优惠简介',
        dataIndex: 'description',
    },
    {
        title: '目标金额',
        dataIndex: 'target_money',
    },
    {
        title: '优惠金额',
        dataIndex: 'discount_money',
    },
    {
        title: '操作',
        key: 'action',
        scopedSlots: { customRender: 'action' },
        className: 'action',
    },
];
export default {
    name: 'manageVoucher',
    data() {
        return {
            columns
        }
    },
    components:{
         addAllVoucher,
    },
    computed:{
        ...mapGetters([
            'allVoucherList',
        ])
    },
    async mounted() {
        await this.getAllVoucher()
    },
    methods:{
        ...mapMutations([
            'set_addAllVoucherVisible'
        ]),
        ...mapActions([
            'getAllVoucher',
            'addVouchertoAll'
        ]),
        addAllVoucher(){
             this.set_addAllVoucherVisible(true)
         },
        addVoucherToAll(record){
            this.addVouchertoAll(record)
        }
    }
}
</script>

<style scoped lang="less">
    .manageVoucher-wrapper {
        padding: 50px;
    }

</style>
<style lang="less">
    .manageVoucher-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>