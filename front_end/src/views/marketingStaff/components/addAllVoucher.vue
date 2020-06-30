<template>
<a-modal
        :visible="addAllVoucherVisible"
        title="添加优惠策略"
        cancelText="取消"
        okText="确定"
        @cancel="cancel"
        @ok="handleSubmit"
>
    <!-- 这里是添加策略模态框区域，请编写表单 -->
    <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
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
        <a-form-item label="折扣" v-bind="formItemLayout">
            <a-input
                    placeholder="请填写折扣"
                    v-decorator="['discount', { rules: [{ required: true, message: '请填写优惠金额' }] }]"
            />
        </a-form-item>
        <a-form-item label="数量" v-bind="formItemLayout">
            <a-input
                    placeholder="请填写数量"
                    v-decorator="['number', { rules: [{ required: true, message: '请填写优惠金额' }] }]"
            />
        </a-form-item>
    </a-form>
</a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: 'addVoucherModal',
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
                'addAllVoucherVisible',
            ])
        },
        beforeCreate() {
            // 表单名默认为“form”
            this.form = this.$form.createForm(this, { name: 'addVoucherModal' });
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_addAllVoucherVisible',
            ]),
            ...mapActions([
                // addHotelCoupon：添加酒店策略接口
                'addVoucher',
            ]),
            cancel() {
                this.set_addAllVoucherVisible(false)
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {

                    if (!err) {
                        const data = {
                            // 这里添加接口参数
                            "description": this.form.getFieldValue('description'),
                            "target_money": Number(this.form.getFieldValue('target_money')),
                            "discount_money": Number(this.form.getFieldValue('discount_money')),
                            "number":Number(this.form.getFieldValue('number')),
                            "discount":this.form.getFieldValue('discount'),
                            "status":"未添加"

                        }
                        console.log(data)
                        this.addVoucher(data)
                    }
                });
            },
        }
    }
</script>