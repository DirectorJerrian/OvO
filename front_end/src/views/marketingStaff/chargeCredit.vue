<template>
    <div class="chargeCredit-wrapper">
        <a-tabs>
            <a-tab-pane tab="信用充值" key="1">
                <a-form :form="form" style="margin-top: 30px">
                    <a-form-item label="邮箱" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <a-input
                                placeholder="请填写邮箱"
                                v-decorator="['email', { rules: [{ required: true, message: '请输入用户邮箱' }] }]"
                        />
                    </a-form-item>
                    <a-form-item label="手机号" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <a-input
                                placeholder="请填写手机号"
                                v-decorator="['phoneNumber', { rules: [{ required: true, message: '请输入用户手机号' }] }]"
                        />
                    </a-form-item>
                    <a-form-item label="充值金额" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <a-input
                                placeholder="请填写充值金额"
                                v-decorator="['money', { rules: [{ required: true, message: '请输入充值金额' }] }]"
                        />
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 12, offset: 5 }">
                        <a-button type="primary" @click="saveModify">
                            充值
                        </a-button>
                        <a-button type="default" style="margin-left: 30px" @click="cancelModify">
                            取消
                        </a-button>
                    </a-form-item>
                </a-form>

            </a-tab-pane>
        </a-tabs>
    </div>
</template>

<script>
    import {mapActions, mapMutations} from "vuex";

    export default {
        name: "chargeCredit",
        data(){
            return{
                modify: false,
                formLayout: 'horizontal',
                form: this.$form.createForm(this, { name: 'form' }),
            }
        },
        components: {

        },
        computed: {

        },
        methods: {
            ...mapMutations([

            ]),
            ...mapActions([
                "creditCharge",
            ]),
            //new methods
            modifyCharge(){
                setTimeout(() => {
                    this.form.setFieldsValue({
                        'email': '',
                        'phoneNumber': '',
                        'money': '',
                    })
                }, 0)
                this.modify = true
            },
            saveModify() {
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            email: this.form.getFieldValue('email'),
                            phoneNumber: this.form.getFieldValue('phoneNumber'),
                            money: this.form.getFieldValue('money')
                        }
                        this.creditCharge(data).then(()=>{
                            this.modify = false
                            this.modifyCharge()
                        })
                    }
                });
            },
            cancelModify() {
                this.modify = false
                this.modifyCharge()
            },
        },

    }

</script>

<style scoped lang="less">
    .chargeCredit-wrapper {
        border-radius: 36px;
        background: transparent;
        z-index: 1;
        margin-top: 50px;
        padding: 50px;
    }

</style>

<style lang="less">
    .chargeCredit-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">

</style>