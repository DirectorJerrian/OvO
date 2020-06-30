<template>
    <div class="manageCoupon-wrapper">
        <a-tabs>
            <a-tab-pane tab="优惠策略" key="1">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <!--                <a-button type="primary" @click="addAllCoupon"><a-icon type="plus"/> </a-button>-->

                </div>
                <a-table
                    :columns="columns"
                    :dataSource="allCouponList"
                    bordered
                >
                </a-table>
            </a-tab-pane>
        </a-tabs>
<!--        <addAllCoupon></addAllCoupon>-->
    </div>
</template>

<script>
import {mapGetters,mapActions,mapMutations} from 'vuex'
import AddAllCoupon from './components/addAllCoupon'
const columns=[
    {
        title: '优惠类型',
        dataIndex: 'couponType',
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
        title: '优惠金额',
        dataIndex: 'discount_money',
    },
];
export default {
    name: 'manageOrder',
    data() {
        return {
            columns
        }
    },
    components:{
        // AddAllCoupon,
    },
    computed:{
        ...mapGetters([
            'allCouponList',
        ])
    },
    async mounted() {
        await this.getAllCoupon()
    },
    methods:{
        ...mapMutations([
            'set_addAllCouponVisible'
        ]),
        ...mapActions([
            'getAllCoupon',
        ]),
        // addAllCoupon(){
        //     this.set_addAllCouponVisible(true)
        // }
    }
}
</script>

<style scoped lang="less">
    .manageCoupon-wrapper {
        padding: 50px;
        border-radius: 36px;
        background: transparent;
        z-index: 1;
        margin-top: 50px;
    }

</style>
<style lang="less">
    .manageCoupon-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>