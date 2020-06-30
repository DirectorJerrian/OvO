<template>
    <a-modal
        id="addCoupon"
        :visible="addCouponVisible"
        title="添加优惠策略"
        cancelText="取消"
        okText="确定"
        @cancel="cancel"
        @ok="handleSubmit"
    >
        <!-- 这里是添加策略模态框区域，请编写表单 -->
        <a-form :form="form" style="margin-top: 30px" id="addForm" v-bind="formItemLayout">
            <a-form-item label="优惠券类型" v-bind="formItemLayout">
                <a-select id="selectRoomType"
                        v-decorator="[
                    'couponType',
                    { rules: [{ required: true, message: '请选择优惠券类型' }] }]"
                        @change="changeType"
                >
                    <a-select-option name="1" value="1">时间优惠</a-select-option>
                    <a-select-option name="2" value="2">多间优惠</a-select-option>
                    <a-select-option name="3" value="3">满减优惠</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="券名" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写券名"
                        v-decorator="['couponName', { rules: [{ required: true, message: '请填写券名' }] }]"
                />
            </a-form-item>
            <a-form-item label="优惠券简介" v-bind="formItemLayout">
                <a-input
                        type="textarea"
                        :rows="4"
                        placeholder="请填写优惠简介"
                        v-decorator="['description', { rules: [{ required: true, message: '请填写优惠简介' }] }]"
                />
            </a-form-item>
            <a-form-item label="达标金额" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写达标金额"
                        v-decorator="['target_money', { rules: [{ required: true, message: '请填写达标金额' }] }]"
                />
            </a-form-item>
            <a-form-item label="优惠金额" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写优惠金额"
                        v-decorator="['discount_money', { rules: [{ required: true, message: '请填写优惠金额' }] }]"
                />
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
export default {
    name: 'addCouponModal',
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
        }
    },
    computed: {
        ...mapGetters([
            'activeHotelId',
            'addCouponVisible',
        ])
    },
    beforeCreate() {
        // 表单名默认为“form”
        this.form = this.$form.createForm(this, { name: 'addCouponModal' });
    },
    mounted() {

    },
    methods: {
        ...mapMutations([
            'set_addCouponVisible',
        ]),
        ...mapActions([
            // addHotelCoupon：添加酒店策略接口
            'addHotelCoupon'
        ]),
        cancel() {
            this.set_addCouponVisible(false)
        },
        changeType(v){

        },
        handleSubmit(e) {
            e.preventDefault();
            this.form.validateFieldsAndScroll((err, values) => {

                if (!err) {
                    const data = {
                        // 这里添加接口参数
                        "type": Number(this.form.getFieldValue('couponType')),
                        "name": this.form.getFieldValue('couponName'),
                        "description": this.form.getFieldValue('description'),
                        "targetMoney": Number(this.form.getFieldValue('target_money')),
                        "discountMoney": Number(this.form.getFieldValue('discount_money')),
                        "hotelId": this.activeHotelId
                    }
                    console.log(data)
                    this.addHotelCoupon(data)
                }
            });
        },
    }
}
</script>