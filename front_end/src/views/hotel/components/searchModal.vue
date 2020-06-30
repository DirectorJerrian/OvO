<template>
    <div>
    <a-modal
            :visible="searchModalVisible"
            title="精确搜索"
            cancelText="取消"
            okText="搜索"
            @cancel="cancelSearch"
            @ok="handleSubmit"
    >
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="酒店名称" v-bind="formItemLayout">
                <a-input v-decorator="['hotelName']"
                        placeholder="请输入酒店名称"></a-input>
            </a-form-item>
            <a-form-item label="商圈" v-bind="formItemLayout">
                <a-select v-decorator="['bizRegion',
                          { rules: [{required: true, message:'请选择商圈'}]},]"
                          placeholder="请选择商圈">
                    <a-select-option v-for="item in bizRegions" :key="item" :value="item">
                        {{item}}
                    </a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="房型" v-bind="formItemLayout">
                <a-select v-decorator="['roomType']"
                          placeholder="请选择房型">
                    <a-select-option value="BigBed">
                        大床房
                    </a-select-option>
                    <a-select-option value="DoubleBed">
                        双床房
                    </a-select-option>
                    <a-select-option value="Family">
                        家庭房
                    </a-select-option>
                </a-select>
            </a-form-item>
        <a-form-item label="星级不低于" v-bind="formItemLayout">
            <a-select v-decorator="['hotelStar',
            { rules: [{required: true, message:'请选择星级'}]},]"
                      placeholder="星级不低于">
                <a-select-option value=3>
                    三星级
                </a-select-option>
                <a-select-option value=4>
                    四星级
                </a-select-option>
                <a-select-option value=5>
                    五星级
                </a-select-option>
            </a-select>
        </a-form-item>
        <a-form-item v-bind="formItemLayout" label="评分不低于">
            <a-input-number :max="5" :min="0" v-decorator="['rate']">
            </a-input-number>
        </a-form-item>
        </a-form>
    </a-modal>
    </div>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from "vuex";

    export default {
        name: "searchModal",
        data(){
            return{
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
            }
        },
        async mounted() {
            this.getBizRegions()
        },
        computed: {
            ...mapGetters([
                'searchModalVisible',
                'bizRegions',
            ]),
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'searchModal' });
        },
        methods: {
            ...mapMutations([
                'set_searchModalVisible',
                'set_searchParams',
            ]),
            ...mapActions([
                'searchHotel',
                `getBizRegions`,
            ]),
            cancelSearch(){
                this.set_searchModalVisible(false)
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            hotelName: this.form.getFieldValue('hotelName'),
                            bizRegion: this.form.getFieldValue('bizRegion'),
                            roomType: this.form.getFieldValue('roomType'),
                            hotelStar: parseInt(this.form.getFieldValue('hotelStar')),
                            rate: Number(this.form.getFieldValue('rate')),
                        }
                        console.log(data)
                        if(data.hotelName===undefined){
                            data.hotelName=''
                        }
                        if(data.roomType===undefined){
                            data.roomType='0'
                        }
                        if(isNaN(data.rate)){
                            data.rate=0
                        }
                        console.log(data)
                        this.searchHotel(data)
                    }
                });
            },
        }
    }
</script>

