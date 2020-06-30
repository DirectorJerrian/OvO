<template>
    <a-modal
        :visible="orderModalVisible"
        title="预定详情"
        cancelText="取消"
        okText="下单"
        @cancel="cancelOrder"
        @ok="handleSubmit"
    >
        <a-form :form="form">
            <a-form-item v-bind="formItemLayout" label="房型信息">
                <span>{{ currentOrderRoom.roomType }}</span>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="入住人姓名">
                <a-input
                    v-decorator="[
                        'clientName',
                        { rules: [{required: true, message: '请填写入住人姓名', }] }
                    ]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="手机号">
                <a-input
                    v-decorator="[
                        'phoneNumber',
                        { rules: [{required: true, message: '请填写入住人联系手机', }] }
                    ]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="入住日期">
                <a-range-picker
                    format="YYYY-MM-DD"
                    @change="changeDate"
                    v-decorator="[
                        'date', 
                        {
                            rules: [{ required: true, message: '请选择入住时间' }]   
                        }
                    ]"
                    :placeholder="['入住日期','退房日期']"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="入住人数">
                <a-input
                        placeholder="入住人数"
                        v-decorator="[
                'peopleNum',
                {rules: [{ required: true, message: '请输入入住人数' }, { validator: this.handlepeopleNum }], validateTrigger: 'blur'}]">
                </a-input>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="有无儿童">
                <a-radio-group
                    v-decorator="[
                        'haveChild',
                        { rules: [{required: true, message: '请选择有无儿童入住', }] }
                    ]"
                >
                    <a-radio :value="1">有</a-radio>
                    <a-radio :value="0">无</a-radio>
                </a-radio-group>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="房间数">
                <a-select
                    v-decorator="[
                        'roomNum',
                        { rules: [{ required: true, message: '请选择房间数' }] },
                    ]"
                    placeholder="请选择房间数"
                    @change="changeRoomNum"
                >
                    <a-select-option :value="1">
                    1
                    </a-select-option>
                    <a-select-option :value="2">
                    2
                    </a-select-option>
                     <a-select-option :value="3">
                    3
                    </a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="房间单价">
                <span>{{ currentOrderRoom.price }}</span>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="总价">
                <span>￥{{ totalPrice }}</span>
            </a-form-item>
            <a-divider></a-divider>
            <h2 v-if="orderMatchCouponList.length>0">优惠活动</h2>
            <a-checkbox-group v-model="checkedList" @change="onchange">
                <a-table
                    :columns="columns"
                    :dataSource="orderMatchCouponList"
                    :showHeader="false"
                    bordered
                    v-if="orderMatchCouponList.length>0"
                >
                    <a-checkbox
                        slot="id"
                        slot-scope="record"
                        :value="record"
                    >
                    </a-checkbox>
                </a-table>
            </a-checkbox-group>
            <h2 v-if="userAvailableVoucherList.length>0">可用优惠券(单次只能使用一张)</h2>
            <a-radio-group v-model="useVoucherId" @change="changeVoucher">
                <a-table
                        :columns="columns1"
                        :dataSource="userAvailableVoucherList"
                        :showHeader="false"
                        bordered
                        v-if="userAvailableVoucherList.length>0"
                >
                    <a-radio :style="radioStyle" slot="id"
                             slot-scope="id"
                             :value="id">

                    </a-radio>
                    <span slot="discount" slot-scope="text">
                    <span>会员结算比例：{{ text }}</span>
                </span>
                    <span slot="number" slot-scope="text">
                    <span>数量：{{ text }}</span>
                </span>

                    <span slot="discount_money" slot-scope="text">
                    <span>优惠金额：￥{{ text }}</span>
                </span>
                </a-table>
            </a-radio-group>
             <a-form-item v-if="userInfo.vip===0" v-bind="formItemLayout" label="结算后总价">
                <span>￥{{ totalPrice-voucherDiscount-couponDiscount }}</span>
            </a-form-item>
            <a-form-item v-if="userInfo.vip===1"  v-bind="formItemLayout" label="">
                <span style="color: #ff0000">会员尊享价：￥{{realPrice.toFixed(2)}}</span>
                <span>（使用会员储蓄值{{this.useSavings.toFixed(2)}}点）</span>
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import {userAvailableVoucherAPI} from "../../../api/voucher";
const moment = require('moment')
const columns = [
    {  
        title: '勾选',
        dataIndex: 'id',
        scopedSlots: {customRender: 'id'}
    },
    {
        title: '优惠类型',
        dataIndex: 'couponName',
        scopedSlots: {customRender: 'couponName'}
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
        dataIndex: 'discountMoney',
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
        scopedSlots: {customRender: 'description'}

    },
    {
        title: '会员专享折扣',
        dataIndex: 'discount',
        scopedSlots: {customRender: 'discount'}
    },

    {
        title: '剩余数量',
        dataIndex: 'number',
        scopedSlots: {customRender: 'number'}

    },
    {
        title: '优惠金额',
        dataIndex: 'discount_money',
        scopedSlots: {customRender: 'discount_money'}
    },
];
export default {
    name: 'orderModal',
    data() {
        return {
            formItemLayout: {
                labelCol: {
                    xs: { span: 12 },
                    sm: { span: 6 },
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 16 },
                },
            },
            radioStyle: {
                display: 'block',
                height: '30px',
                lineHeight: '30px',
            },
            totalPrice: '',
            columns,
            columns1,
            checkedList: [],
            finalPrice: '',
            ultPrice:'',
            useVoucherId:'',
            dateReady:false,
            roomNumReady:false,
            voucherDiscount:0,
            couponDiscount:0,
            VIPDiscountPercent:1,
            useSavings:0,
            setSavings:0,
            realPrice:0,
        }
    },
    computed: {
        ...mapGetters([
            'orderModalVisible',
            'currentOrderRoom',
            'currentHotelId',
            'currentHotelInfo',
            'userId',
            'orderMatchCouponList',
            'userAvailableVoucherList',
            'VIPInfo',
            'userInfo',
            'orderPass'
        ]),
        
    },
    mounted() {
        this.getUserInfo()
        this.getVIPInfo()


    },
    beforeCreate() {
        this.form = this.$form.createForm(this, { name: 'orderModal' });
    },
    methods: {
        ...mapMutations([
            'set_orderModalVisible'
        ]),
        ...mapActions([
            'addOrder',
            'getOrderMatchCoupons',
            'getUserAvailableVoucherList',
            'getVIPInfo',
            'getUserInfo',
            'useVoucher',
            'setUserSavings',

        ]),
        cancelOrder() {
            this.set_orderModalVisible(false)
        },
        confirmOrder() {

        },
        changeDate(v) {
            if(this.totalPrice != ''||this.roomNumReady){
                this.totalPrice = this.form.getFieldValue('roomNum') * moment(v[1]).diff(moment(v[0]), 'day') * Number(this.currentOrderRoom.price)
            }

            this.dateReady=true
            this.couponDiscount=0
            this.voucherDiscount=0
            this.savingsCompute()
        },
        changePeopleNum(v){

        },
        changeRoomNum(v) {
            if(this.totalPrice!=''||this.dateReady){
                this.totalPrice = Number(v) * Number(this.currentOrderRoom.price) * moment(this.form.getFieldValue('date')[1]).diff(moment(this.form.getFieldValue('date')[0]),'day')
            }

            this.roomNumReady=true
            this.couponDiscount=0
            this.voucherDiscount=0
            this.savingsCompute()
        },
        onchange() {
            this.couponDiscount=0
            if(this.checkedList.length>0){
                this.orderMatchCouponList.filter(item => this.checkedList.indexOf(item.id)!=-1).forEach(item => this.couponDiscount= this.couponDiscount+item.discountMoney)
            }else{
                
            }
            this.savingsCompute()
        },
        changeVoucher(e){
            this.voucherDiscount=0
            var newDiscount
            for(var i=0;i<this.userAvailableVoucherList.length;i++){
                if(this.userAvailableVoucherList[i].id===this.useVoucherId){
                    newDiscount=this.userAvailableVoucherList[i].discount_money
                    this.VIPDiscountPercent=this.userAvailableVoucherList[i].discount
                    this.savingsCompute()
                    break
                }
            }

            this.voucherDiscount=newDiscount

        },
        handlepeopleNum(rule,value,callback){
            var numReg = /^[0-9]+$/
            var numRe = new RegExp(numReg)
            if (!numRe.test(value)) {
                callback(new Error('请输入正数'))
            }
            callback()

        },
        savingsCompute(){
            if(this.userInfo.vip===0) {
                console.log("haha")
                this.realPrice=this.totalPrice-this.voucherDiscount-this.couponDiscount
            }
            else{

                var price=(this.totalPrice-this.voucherDiscount-this.couponDiscount)*this.VIPDiscountPercent
                if(this.VIPInfo.savings>=price){
                    this.useSavings=price
                    this.setSavings=this.VIPInfo.savings-price
                }else{
                    this.useSavings=this.VIPInfo.savings
                    this.setSavings=0
                }
                this.realPrice=price-this.useSavings
                console.log("123123")
                console.log("使用会员储值"+this.useSavings+"剩余会员储值"+this.setSavings+"真正价格"+this.realPrice)
            }
        },
        refresh(){
            this.$router.go(0)
        },
        handleSubmit(e) {
            e.preventDefault();
             this.form.validateFieldsAndScroll((err, values) => {
                if (!err) {
                    const data = {
                        hotelId: this.currentHotelId,
                        hotelName: this.currentHotelInfo.name,
                        userId: Number(this.userId),
                        checkInDate: moment(this.form.getFieldValue('date')[0]).format('YYYY-MM-DD'),
                        checkOutDate: moment(this.form.getFieldValue('date')[1]).format('YYYY-MM-DD'),
                        roomType: this.currentOrderRoom.roomType == '大床房' ? 'BigBed' : this.currentOrderRoom.roomType == '双床房' ? 'DoubleBed' : 'Family',
                        roomNum: this.form.getFieldValue('roomNum'),
                        peopleNum: this.form.getFieldValue('peopleNum'),
                        haveChild: this.form.getFieldValue('haveChild'),
                        createDate: '',
                        price: this.realPrice
                    }
                     this.addOrder(data).then(orderPass=>{

                         if(this.useSavings!=0 && orderPass){
                             const savings={
                                 cardId:Number(this.VIPInfo.cardId),
                                 savings:Number(this.setSavings),
                             }
                             this.setUserSavings(savings)
                         }
                         if(this.useVoucherId!=0 && orderPass){
                             this.useVoucher(Number(this.useVoucherId))
                         }
                         if(orderPass){
                             this.timer = setTimeout(()=>{   //设置延迟执行
                                 this.refresh()
                             },1000);
                         }
                     })
                }
            });
        },
    },
    watch:{
        totalPrice(val) {
            let data = {
                userId: this.userId,
                hotelId: this.currentHotelId,
                orderPrice: this.totalPrice,
                roomNum: this.form.getFieldValue('roomNum'),
                checkIn: moment(this.form.getFieldValue('date')[0]).format('YYYY-MM-DD'),
                checkOut: moment(this.form.getFieldValue('date')[1]).format('YYYY-MM-DD'),
            }
            this.getOrderMatchCoupons(data)
            let data1={
                userId: Number(this.userId),
                targetMoney: this.totalPrice,
            }
            this.getUserAvailableVoucherList(data1)
            this.savingsCompute()
        },
        couponDiscount(val){
            this.savingsCompute()
        },
        voucherDiscount(val){
            this.savingsCompute()
        }
    }
}
</script>
