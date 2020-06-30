<template>
    <div class="header">
        <div class="word">
            <div class="inner" >
                <div class="label">
                    <img src="@/assets/logo.svg" class="logo" alt="logo" @click="jumpToHome">
                    <span class="title">    OvO</span>
                </div>
                <a-menu v-model="current" mode="horizontal" theme="light">
                    <a-menu-item key="1" @click="selectMenu"   class="icon" v-if="userInfo.userType!='Marketingstaff'">
                        <router-link to="/hotel/hotelList" >
                            <a-icon  type="home" />首页
                        </router-link>
                    </a-menu-item>
                    <a-menu-item key="2"  class="icon" @click="jumpToUserInfo">
                        <a-icon  type="user" />个人中心
                    </a-menu-item>
                    <a-menu-item key="3" @click="selectMenu"  class="icon" v-if="userInfo.userType=='HotelManager'">
                        <router-link :to="{ name: 'manageHotel'}">
                            <a-icon  type="switcher" />酒店管理
                        </router-link>
                    </a-menu-item>
                    <a-menu-item key="4" @click="selectMenu"   class="icon" v-if="userInfo.userType=='Manager'">
                        <router-link :to="{ name: 'manageUser'}">
                            <a-icon  type="user" />账户管理
                        </router-link>
                    </a-menu-item>
                    <a-menu-item key="5" @click="selectMenu" class="icon"  v-if="userInfo.userType=='Marketingstaff'">
                        <router-link :to="{ name: 'manageOrder'}">
                            <a-icon   type="switcher" />订单管理
                        </router-link>
                    </a-menu-item>
                    <a-menu-item key="6" @click="selectMenu"  class="icon" v-if="userInfo.userType=='Marketingstaff'">
                        <router-link :to="{ name: 'manageCoupon'}">
                            <a-icon  type="switcher" />优惠策略
                        </router-link>
                    </a-menu-item>
                    <a-menu-item key="7" @click="selectMenu"  class="icon" v-if="userInfo.userType=='Marketingstaff'">
                        <router-link :to="{name: 'creditCharge'}">
                            <a-icon  type="switcher">
                            </a-icon>
                            信用充值
                        </router-link>
                    </a-menu-item>
                </a-menu>
                <div class="logout">
                    <a-dropdown placement="bottomCenter">
                        <div class="user">
                            <a-avatar :src="userInfo.avatar"></a-avatar>
                            <span style="font-size: 14px">{{ userInfo.userName }}</span>
                            <a-icon style="margin-left: 3px; font-size: 16px" type="down"></a-icon>
                        </div>
                        <a-menu slot="overlay" v-model="currentslide">
                            <a-menu-item  key="1" @click="jumpToHome(),switchTab(1)" v-if="userInfo.userType==='Client' ||userInfo.userType==='HotelManager'">
                                <a-icon type="home"></a-icon>
                                首页
                            </a-menu-item>
                            <a-menu-item key="2" @click="jumpToUserInfo(),switchTab(2)" >
                                <a-icon type="profile"></a-icon>
                                我的信息
                            </a-menu-item>
                            <a-menu-item  @click="quit()">
                                <a-icon type="poweroff"></a-icon>
                                退出登录
                            </a-menu-item>
                        </a-menu>
                    </a-dropdown>
                </div>
            </div>
        </div>
        <div class="background" v-if="this.$route.name == 'hotelList'">
            <img  src="@/assets/headerback.jpg" width="100%">
        </div>

    </div>
    
</template>
<script>
import { mapGetters, mapActions, mapMutations } from 'vuex'
export default {
    name: '',
    data() {
        return {
            current: ['1'],
            currentslide:['1']
        }
    },
    computed: {
        ...mapGetters([
            'userId',
            'userInfo'
        ])
    },
    mounted() {
        if (this.$route.name == 'hotelList' || this.$route.name == 'hotelDetail') {
            this.current = ['1']
        }else if(this.$route.name == 'userInfo') {
            this.current = ['2']
        }else if(this.$route.name == 'manageHotel') {
            this.current = ['3']
        }else if(this.$route.name == 'manageUser'){
            this.current = ['4']
        }
        else{
            this.current=['5']
        }
    },
    methods: {
        ...mapMutations([

        ]),
        ...mapActions([
            'logout'
        ]),
        selectMenu(v){
        },
        async quit() {
            await this.$store.dispatch('logout')
            this.$router.push(`/login?redirect=${this.$route.fullPath}`)
        },
        jumpToUserInfo() {
            this.$router.push({ name: 'userInfo', params: { userId: this.userId } })
        },
        jumpToHome() {
            this.$router.push(`/hotel/hotelList`)
        },
        switchTab(key){
            console.log(key)
            if(key=='1') this.current=['1']
            else if(key=='2') this.current=['2']
        }
    }
}
</script>
<style scoped lang="less">
    .header {
        position: relative;
        z-index: 1;
        background: transparent;
        line-height: 44px;
        height: 66px;
        border-radius: 10px;
        min-width: 800px;
        .background{
            width: 100%;
            height: 66px;
            min-width: 800px;
            image{
                margin-top: 10px;
                width: 100%;
                height: 66px;
            }
        }
        .word{
            position:absolute;
            width:100%;
            height:100%;
            z-indent:2;
            left:0;
            top:0;
            .inner{
                width: 100%;
                height: 100%;
                display: flex;
                align-items: flex-end;
                justify-content: space-between;
                .label{
                    margin-bottom: 6px;
                    height: 44px;
                    line-height: 44px;
                    vertical-align: middle;
                    min-width: 400px;

                    .logo {
                        height: 44px;
                        vertical-align: top;
                        margin-left: 20px;
                        margin-right: 16px;
                        border-style: none;
                        cursor: pointer;
                    }

                    .title {
                        font-size: 33px;
                        color: rgba(0, 0, 0, .85);
                        font-family: Avenir, 'Helvetica Neue', Arial, Helvetica, sans-serif;
                        font-weight: 600;
                        position: relative;
                        top: 2px;
                    }
                }
            }

        }
        .logout {
            margin-right: 40px;
            margin-bottom: 6px;
            .user {
                cursor: pointer;
                display:flex;
                align-items: center;
                span {
                    margin-left: 5px
                }
            }
        }

    }
</style>
<style lang="less">
    .header {
        .ant-menu {
            background: none
        }
    }
</style>