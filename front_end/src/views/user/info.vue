<template>
    <div class="info-wrapper">
        <a-tabs>
            <a-tab-pane tab="我的信息" key="1" >
                <a-form :form="form" style="margin-top: 30px;">

                    <a-form-item label="用户名" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <a-input
                                placeholder="请填写用户名"
                                v-decorator="['userName', { rules: [{ required: true, message: '请输入用户名' }] }]"
                                v-if="modify"
                        />
                        <span v-else>{{ userInfo.userName }}</span>
                    </a-form-item>
                    <a-form-item label="邮箱" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ userInfo.email }}</span>
                    </a-form-item>
                    <a-form-item label="手机号" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <a-input
                                placeholder="请填写手机号"
                                v-decorator="['phoneNumber', { rules: [{ required: true, message: '请输入手机号' }] }]"
                                v-if="modify"
                        />
                        <span v-else>{{ userInfo.phoneNumber}}</span>
                    </a-form-item>
                    <a-form-item label="信用值" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" v-if="userInfo.userType==='Client'">
                        <span>{{ userInfo.credit }}</span>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify">
                        <a-button type="primary" @click="saveModify">
                            保存
                        </a-button>
                        <a-button type="default" style="margin-left: 30px" @click="cancelModify">
                            取消
                        </a-button>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-else>
                        <a-button type="default" @click="modifyInfo">
                            修改信息
                        </a-button>
                    </a-form-item>
                </a-form>
                <a-form :form="form" style="margin-top: 30px;" >
                    <a-form-item label="原密码" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }"
                                 v-if="modifyPassword">
                        <a-input
                                type="password"
                                placeholder="请输入原密码"
                                v-decorator="['originPassword', { rules: [{ required: true, message: '请输入原密码' }, { validator: this.handleOriginPasswordCheck }], validateTrigger: 'blur' }]"
                        />
                    </a-form-item>
                    <a-form-item label="新密码" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }"
                                 v-if="modifyPassword">
                        <a-input
                                type="password"
                                placeholder="请输入新密码"
                                v-decorator="['password', { rules: [{ required: true, message: '请输入新密码' }, { validator: this.handlePassword }] }]"
                        />
                    </a-form-item>
                    <a-form-item label="再次输入密码" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }"
                                 v-if="modifyPassword">
                        <a-input
                                type="password"
                                placeholder="请再次输入新密码"
                                v-decorator="['confirmPassword', { rules: [{ required: true, message: '请再次输入新密码' }, { validator: this.handlePasswordCheck }], validateTrigger: 'blur' }]"
                        />
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modifyPassword">
                        <a-button type="primary" @click="saveModifyPassword">
                            保存
                        </a-button>
                        <a-button type="default" style="margin-left: 30px" @click="cancelModifyThePassword">
                            取消
                        </a-button>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-else>
                        <a-button type="default" @click="modifyThePassword">
                            修改密码
                        </a-button>
                    </a-form-item>
                </a-form>
            </a-tab-pane>
            <a-tab-pane tab="我的订单" key="2" v-if="userInfo.userType==='Client'">
                <a-table
                        :columns="columns"
                        :dataSource="userOrderList"
                        rowKey="id"
                        bordered
                >
                    <span slot="hotelDetial" slot-scope="record">
                         <a-button type="link" :size="size" @click="hotelDetail(record)">{{record}}</a-button>
                    </span>
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="roomType" slot-scope="text">
                        <span v-if="text == 'BigBed'">大床房</span>
                        <span v-if="text == 'DoubleBed'">双床房</span>
                        <span v-if="text == 'Family'">家庭房</span>
                    </span>
                    <a-tag slot="orderState" color="blue" slot-scope="text">
                        {{ text }}
                    </a-tag>
                    <span slot="action" slot-scope="record">
                        <a-button type="default" size="small" @click="detail(record)">查看</a-button>
                        <a-divider type="vertical" v-if="record.orderState == '已预订'"></a-divider>
                        <a-popconfirm
                                title="你确定撤销该笔订单吗？"
                                @confirm="confirmCancelOrder(record.id)"
                                @cancel="cancelCancelOrder"
                                okText="确定"
                                cancelText="取消"
                                v-if="record.orderState == '已预订'"
                        >
                            <a-button type="danger" size="small">撤销</a-button>
                        </a-popconfirm>
                        <a-divider type="vertical" v-if="record.orderState == '已入住'"></a-divider>
                        <a-button type="primary" size="small" v-if="record.orderState == '已入住'"
                                  @click="showRateModal(record.id)">评价</a-button>



                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="修改头像" key="3">
                <a-form :form="form" style="margin-left: 100px;margin-top: 100px" class="avatar">
                    <img :src="myAvatar?myAvatar:require('../../assets/cover.jpeg')" alt="">
                    <input type="file" name="" accept="image/gif,image/jpeg,image/jpg,image/png" @change="fileChange">
                    <button @click="submitAvatar">提交</button>
                </a-form>
                <a-form :form="form" style="margin-left: 100px;margin-top: 100px"></a-form>
            </a-tab-pane>
            <a-tab-pane tab="我的优惠券" key="4" v-if="userInfo.userType==='Client'">
                <a-table
                        :columns="coloums3"
                        :dataSource="userVoucherList"
                        bordered
                >

                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="信用值变化记录" key="5" v-if="userInfo.userType==='Client'">
                <a-table
                        :columns="coloums2"
                        :dataSource="userCreditRecord"
                        bordered
                >
                   <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                   </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane v-if="userInfo.vip===0 && userInfo.userType==='Client'" tab="会员注册" key="6" >
                <a-button type="primary" style="left: 20% ;margin-top: 6%;"  @click="choosePersonal">
                    个人注册
                </a-button>
                <a-button type="primary"  style="left: 30% ;margin-top: 6%"  @click="chooseBusiness">
                    企业注册
                </a-button>
                <br/>
                <br/>
                <a-form :form="form" style="margin-left: 20% ;margin-top: 30px">
                    <a-form-item  v-if="personal" label="生日信息" :label-col="{ span: 2}" :wrapper-col="{ span: 8, offset: 1 }">
                    <a-date-picker
                            format="YYYY-MM-DD"
                            @change="changeBirthday"
                            v-decorator="[
                        'birthdayInfo',
                        {
                            rules: [{ required: true, message: '请选择您的生日' }]
                        }
                    ]"

                    />

                </a-form-item>
                    <a-form-item  v-if="business" label="企业信息" :label-col="{ span: 2 }" :wrapper-col="{ span: 8, offset: 1 }">
                    <a-input
                            placeholder="请输入企业名称"
                            v-decorator="[
                'businessInfo',
                {rules: [{ required: true, message: '请输入企业名称' }], validateTrigger: 'blur'}
              ]"
                    >

                    </a-input>
                </a-form-item>
                </a-form>
                <br/>
                <a-button type="primary" style="margin-left: 20% ;margin-top: 6%" v-if="personal|business"   @click="registerVip">
                    确认注册
                </a-button>
                <a-button type="default" v-if="personal|business" style="margin-left: 10% ;margin-top: 6%"  @click="cancelVIP">
                    取消注册
                </a-button>
            </a-tab-pane>
            <a-tab-pane v-if="userInfo.vip!==0 && userInfo.userType==='Client'" tab="会员充值" key="7" >
                <a-form :form="form" style="marginTop: 50px;marginLeft:10px">
                    <a-form-item label="会员等级" :label-col="{ span:5 }" :wrapper-col="{ span:8, offset: 1 }">
                        <span>{{ VIPInfo.level }}</span>
                    </a-form-item>
                    <a-form-item label="会员积分" :label-col="{ span: 5 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ VIPInfo.vip_credit }}</span>
                    </a-form-item>
                    <a-form-item label="会员储蓄值" :label-col="{ span: 5}" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ VIPInfo.savings }}</span>
                    </a-form-item>
                    <a-form-item label="会员生日" v-if="VIPInfo.card_type==='personal'" :label-col="{ span: 5}" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ VIPInfo.info }}</span>
                    </a-form-item>
                    <a-form-item label="会员企业名称" v-if="VIPInfo.card_type==='business'" :label-col="{ span: 5}" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ VIPInfo.info }}</span>
                    </a-form-item>

                </a-form>
                <a-form :form="form" style="marginTop: 60px">
                    <h4 style="margin-left: 430px; font-size:20px">储 值 充 值</h4>
                    <a-form-item style="marginTop: 30px" label="充值金额" :label-col="{ span: 5 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <a-radio-group default-value="100" button-style="solid" @change="changeMoney">
                            <a-radio-button value="100">
                                100元
                            </a-radio-button>
                            <a-radio-button value="200">
                                200元
                            </a-radio-button>
                            <a-radio-button value="500">
                                500元
                            </a-radio-button>
                            <a-radio-button value="1000">
                                1000元
                            </a-radio-button>
                            <a-radio-button value="other">
                                其他金额
                            </a-radio-button>
                        </a-radio-group>
                    </a-form-item>
                    <a-form-item label="支付方式" :label-col="{ span: 5 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <a-radio-group default-value="a" button-style="solid">
                            <a-radio-button value="a">
                                支付宝
                            </a-radio-button>
                        </a-radio-group>
                    </a-form-item>
                    <a-form-item label="支付金额(元)" :label-col="{ span: 5 }" :wrapper-col="{ span: 6, offset: 1 }">
                        <a-input
                                placeholder="请填写金额"
                                v-decorator="['moneyDIY', { rules: [{ required: true, message: '请输入金额' }] }]"
                                v-if="diyMoney"
                        />
                        <span v-else>{{moneyToBePaid}}</span>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 8, offset: 6 }">
                        <a-button style="font-size:24px;background:#8cc8ff; margin-left:100px; width: 100px; height: 40px" @click="recharge">
                            充值
                        </a-button>
                    </a-form-item>
                </a-form>
            </a-tab-pane>
        </a-tabs>
        <rateModal :show="show" :title="title" :rateId="rateId" :rate="rate" @hideModal="hideModal" @submit="submit"
                   @changeRate="changeRate">

        </rateModal>
        <viewModal></viewModal>
    </div>
</template>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import rateModal from "./rateModal";
    import viewModal from "./components/viewModal";
    const moment = require('moment')
    const columns = [
        {
            title: '订单号',
            dataIndex: 'id',
        },
        {
            title: '酒店名',
            key: 'hotelDetail',
            dataIndex:'hotelName',
            scopedSlots: {customRender: 'hotelDetial'},
        },
        {
            title: '房型',
            dataIndex: 'roomType',
            scopedSlots: {customRender: 'roomType'}
        },
        {
            title: '入住时间',
            dataIndex: 'checkInDate',
            scopedSlots: {customRender: 'checkInDate'}
        },
        {
            title: '离店时间',
            dataIndex: 'checkOutDate',
            scopedSlots: {customRender: 'checkOutDate'}
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
            filters: [{text: '已预订', value: '已预订'}, {text: '已撤销', value: '已撤销'}, {text: '已入住', value: '已入住'}],
            onFilter: (value, record) => record.orderState.includes(value),
            dataIndex: 'orderState',
            scopedSlots: {customRender: 'orderState'}
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },

    ];
    const coloums2 = [
        {
            title: '变更时间',
            dataIndex: 'operationDate',
            scopedSlots: {customRender: 'operationDate'}
        },
        {
            title: '订单号',
            dataIndex: 'orderId',
        },
        {
            title: '操作',
            dataIndex: 'operation',
        },
        {
            title: '信用值变化',
            dataIndex: 'credit_delta',
        },
        {
            title: '信用值结果',
            dataIndex: 'credit_result',
        },
    ];
    const coloums3=[
        {
            title:'描述',
            dataIndex:'description',

        },
        {
            title: '额外折扣',
            dataIndex: 'discount',
        },
        {
            title: '使用金额',
            dataIndex: 'target_money',
        },
        {
            title: '优惠金额',
            dataIndex: 'discount_money',
        },
        {
            title: '数量',
            dataIndex: 'number',
        },
        {
            title:'状态',
            dataIndex: 'status'
        }


    ];
    export default {
        name: 'info',
        data() {
            return {
                moneyToBePaid:100,
                diyMoney:false,
                business:false,
                personal:false,
                modify: false,
                modifyPassword:false,
                formLayout: 'horizontal',
                pagination: {},
                columns,
                coloums2,
                coloums3,
                data: [],
                form: this.$form.createForm(this, {name: 'coordinated'}),
                title: "请对本次酒店服务做出评价",
                show: false,
                rateId: 0,
                rate: 0,
                myAvatar: '',
                avatarFile: '',
                birthdayInfo:'',
                businessInfo:''
            }
        },
        components: {
            rateModal,
            viewModal
        },
        computed: {
            ...mapGetters([
                'userId',
                'userInfo',
                'userOrderList',
                'userCreditRecord',
                'activeHotelId',
                'currentOrderId',
                'userVoucherList',
                'VIPInfo'
            ])
        },
        async mounted() {
            await this.getUserInfo()
            await this.getUserOrders()
            await this.getUserCreditRecord()
            await this.getUserVoucherList()
            await this.getVIPInfo()


        },
        methods: {
            ...mapMutations([
                'set_viewModalVisible',
                'set_currentOrderInfo',
            ]),
            ...mapActions([
                'getVIPInfo',
                'getUserInfo',
                'getUserOrders',
                'updateUserInfo',
                'updateUserPassword',
                'cancelOrder',
                'orderRate',
                'getUserCreditRecord',
                'updateAvatar',
                'orderRate',
                'userRecharge',
                'getUserVoucherList',
                'registerVIP',
                'hotelDetail',
                'findDetail',
            ]),
            //选择金额
            changeMoney(e){
                if(e.target.value!="other"){
                    this.moneyToBePaid=e.target.value;
                    this.diyMoney=false
                }else{
                    this.diyMoney=true
                }
            },
            click(record, index){
                return {
                    on: {
                        click: () => {
                            this.$router.push({ name: 'hotelDetail', params: { hotelId: record.hotelId }})
                        }

                    }
                }
            },
            hotelDetail(record){
                console.log(record)
                this.findDetail(record);
                this.$router.push({ name: 'hotelDetail', params: { hotelId: this.activeHotelId }})
            },
            //提交充值
            recharge() {
                const _this=this;
                this.form.validateFields((err, values) => {
                    let data;
                    if (!err) {
                        if (this.diyMoney == true) {
                            data = {
                                savings: parseInt(this.form.getFieldValue('moneyDIY'))+this.VIPInfo.savings,
                                vip_credit:parseInt(this.form.getFieldValue('moneyDIY'))+this.VIPInfo.vip_credit,
                                level:this.VIPInfo.level,
                            }
                        } else {
                            data = {
                                savings: parseInt(_this.moneyToBePaid)+this.VIPInfo.savings,
                                vip_credit: parseInt(_this.moneyToBePaid)+this.VIPInfo.vip_credit,
                                level:this.VIPInfo.level,
                            }
                        }
                        this.userRecharge(data).then(() => {
                        })
                    }
                });
            },
            //头像选择
            fileChange(e) {
                const that = this;
                const file = e.target.files[0];
                const reader = new FileReader();
                reader.onload = (data) => {
                    let res = data.target;
                    that.avatarFile = file;
                    that.myAvatar = res.result;
                }
                reader.readAsDataURL(file);
            },
            submitAvatar() {
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            myAvatar: this.avatarFile,
                        }
                        this.updateAvatar(data)
                    }
                });
            },
            handlePassword(rule, value, callback) {
                const originPassword = this.userInfo.password
                console.log(originPassword)
                if (value === undefined) {
                    callback(new Error('请输入新密码'))
                }
                if (value && originPassword && value.trim() === originPassword.trim()) {
                    callback(new Error('不可与原密码相同'))
                }
                callback()
            },
            handlePasswordCheck(rule, value, callback) {
                const password = this.form.getFieldValue('password')
                console.log(password)
                if (value === undefined) {
                    callback(new Error('请再次输入新密码'))
                }
                if (value && password && value.trim() !== password.trim()) {
                    callback(new Error('两次密码不一致'))
                }
                callback()
            },
            handleOriginPasswordCheck(rule, value, callback) {
                const originPassword = this.userInfo.password
                console.log(originPassword)
                if (value === undefined) {
                    callback(new Error('请输入原密码'))
                }
                if (value && originPassword && value.trim() !== originPassword.trim()) {
                    callback(new Error('原密码错误'))
                }
                callback()
            },
            saveModify() {
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            userName: this.form.getFieldValue('userName'),
                            phoneNumber: this.form.getFieldValue('phoneNumber'),
                        }
                        this.updateUserInfo(data).then(() => {
                            this.modify = false
                        })
                    }
                });
            },
            saveModifyPassword() {
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            password: this.form.getFieldValue('password')
                        }
                        this.updateUserPassword(data).then(() => {
                            this.modifyPassword = false
                        })
                    }
                });
            },
            modifyInfo() {
                setTimeout(() => {
                    this.form.setFieldsValue({
                        'userName': this.userInfo.userName,
                        'phoneNumber': this.userInfo.phoneNumber,

                    })
                }, 0)
                this.modify = true
            },
            modifyThePassword() {
                this.modifyPassword = true
            },
            cancelModify() {
                this.modify = false
            },
            cancelModifyThePassword() {
                this.modifyPassword = false
            },
            confirmCancelOrder(orderId) {
                this.cancelOrder(orderId)
            },
            cancelCancelOrder() {

            },
            hideModal() {
                // 取消弹窗回调
                this.show = false
                this.rate
            },

            submit() {
                // 确认弹窗回调
                this.show = false
                console.log("评价的orderid是", this.rateId, "评分是", this.rate)
                let data1 = {
                    id: 0,
                    rate: 0
                };
                data1.id = this.rateId
                data1.rate = this.rate
                this.orderRate(data1)

            },
            showRateModal(id) {
                this.rateId = id
                console.log(id)
                this.show = true
            },
            changeRate(value){
                this.rate=value
                console.log("评分被改为",this.rate)
            },
            detail(value){
                this.set_currentOrderInfo(value)
                this.set_viewModalVisible(true)
            },
            chooseBusiness(){
                this.business=true
                this.personal=false
            },
            choosePersonal(){
                this.personal=true
                this.business=false
            },
            cancelVIP(){
                this.business=false
                this.personal=false
            },
            changeBirthday(date, dateString){
                console.log(date, dateString);
                this.birthdayInfo=moment(dateString).format('YYYY-MM-DD')
                console.log(this.birthdayInfo)
            },
            registerVip(){
                if(this.personal==true){
                    this.form.validateFields((err, values) => {
                        if (!err) {
                            const data = {
                                info: this.birthdayInfo,
                                userId: this.userId,
                                type:"personal"
                            }
                            console.log(data)
                            this.registerVIP(data)
                        }
                    });
                }else{
                    this.form.validateFields((err, values) => {
                        if (!err) {
                            const data = {
                                info: this.form.getFieldValue('businessInfo'),
                                userId: this.userId,
                                type:"business"
                            }
                            console.log(data)
                            this.registerVIP(data)
                        }
                    });

                }
            },
            handleBusinessName(rule,value,callback){
                if(value===undefined) callback(new Error('请输入企业名称'))
                callback()
            }


        }
    }
</script>
<style scoped lang="less">
    .info-wrapper {
        border-radius: 36px;
        background: transparent;
        z-index: 1;
        margin-top: 50px;
        padding: 50px;

        .chart {
            display: flex;
            flex-wrap: wrap;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px
        }
    }
</style>
<style lang="less">
    .info-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
    .avatar {
        position: relative;
        width: 168px;
        height: 168px;

        img {
            object-fit: cover;
            object-position: center;
            width: 100%;
            height: 100%;
            border-radius: 50%;
        }

        input {
            position: absolute;
            top: 0;
            right: 0;
            width: 100%;
            height: 100%;
            border-radius: 50%;
            outline: none;
            opacity: 0;
            cursor: pointer;

            &:focus {
                box-shadow: none;
            }
        }
        .Info{
            display: flex;
            justify-content: space-between;
        }

        button {
            width: 100%;
            height: 30px;
            margin-top: 20px;
            text-align: center;
            border: 1px solid #ccc;
            color: #fff;
            font-size: 14px;
            border-radius: 10%;
            background: #1890ff;
        }
    }
</style>