<template>
    <a-modal
            :visible="modifyVisible"
            title="信息修改"
            width="600px"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="saveModifyHotelInfo"
    >
        <!-- 这里是模态框内容区域，请编写列表代码与添加策略按钮 -->
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="酒店名" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写酒店名"
                        v-decorator="['hotelName', {initialValue:this.currentHotelInfo.name, rules: [{ required: true, message: '请输入酒店名' }] }]"
                />
            </a-form-item>
            <a-form-item label="商圈" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写商圈"
                        v-decorator="['bizRegion', {initialValue:this.currentHotelInfo.bizRegion, rules: [{ required: true, message: '请输入商圈' }] }]"
                />
            </a-form-item>
            <a-form-item label="简介" v-bind="formItemLayout">
                <a-input
                        type="textarea"
                        :rows="4"
                        placeholder="请填写简介"
                        v-decorator="['description', {initialValue:this.currentHotelInfo.description, rules: [{ required: true, message: '请输入简介' }] }]"
                />
            </a-form-item>
            <a-form :form="form" style="margin-left: 200px" class="cover">
                <img :src="coverImg?coverImg:(this.currentHotelInfo.cover?this.currentHotelInfo.cover:require('../../../assets/cover.jpeg'))" alt="" >
                <input type="file" name="" accept="image/gif,image/jpeg,image/jpg,image/png" @change="fileChange">
                <button @click="submitCover">提交酒店封面</button>
            </a-form>
            <a-form :form="form" style="margin-left: 100px;margin-top: 100px"></a-form>
        </a-form>
    </a-modal>
</template>
<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'

    const moment = require('moment')
    export default {
        name: "modifyInfo",
        data() {
            return {
                formItemLayout: {
                    labelCol: {
                        xs: {span: 12},
                        sm: {span: 6},
                    },
                    wrapperCol: {
                        xs: {span: 24},
                        sm: {span: 16},
                    },
                },
                coverImg:'',
                coverFile:'',
            }
        },
        computed: {
            ...mapGetters([
                'modifyVisible',
                'currentHotelInfo',
            ])
        },
        beforeCreate() {
            // 表单名默认为“form”
            this.form = this.$form.createForm(this, { name: 'modifyInfo' });
        },
        async mounted() {
/*            const _this = this
            window.onpageshow = () => {
                _this.getHotelInfo()
                console.log("test1")
                _this.coverImg=this.currentHotelInfo.cover,
                _this.form.setFieldsValue({
                    'hotelName': this.currentHotelInfo.name,
                    'bizRegion': this.currentHotelInfo.bizRegion,
                    'description': this.currentHotelInfo.description,
                })
            }*/
        },
        methods: {
            ...mapMutations([
                'set_modifyVisible',
            ]),
            ...mapActions([
                'getHotelInfo',
                'updateHotelInfo',
                'updateCover',
            ]),
            fileChange(e) {
                const that = this;
                const file = e.target.files[0];
                const reader = new FileReader();
                reader.onload = (data) => {
                    let res = data.target;
                    that.coverFile=file;
                    that.coverImg  = res.result;
                }
                reader.readAsDataURL(file);
            },
            submitCover() {
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            coverFile: this.coverFile,
                        }
                        this.updateCover(data)
                    }
                });
            },
            cancel() {
                this.set_modifyVisible(false)
            },
            saveModifyHotelInfo() {
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            name: this.form.getFieldValue('hotelName'),
                            bizRegion: this.form.getFieldValue('bizRegion'),
                            description: this.form.getFieldValue('description'),
                        }
                        this.updateHotelInfo(data).then(() => {
                            this.cancel()
                        })
                    }
                });
            }
        },
    }
</script>
<style lang="less">
    .cover {
        position: relative;
        width: 117.5px;
        height: 200px;

        img {
            object-fit: cover;
            object-position: center;
            width: 100%;
            height: 100%;
        }

        input {
            position: absolute;
            top: 0;
            right: 0;
            width: 100%;
            height: 100%;
            outline: none;
            opacity: 0;
            cursor: pointer;

            &:focus {
                box-shadow: none;
            }
        }

        button {
            width: 100%;
            height: 30px;
            margin-top: 20px;
            text-align: center;
            border: 1px solid #ccc;
            color: #fff;
            font-size: 14px;
            background: #0aacf8;
        }
    }
</style>