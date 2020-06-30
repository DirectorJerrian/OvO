<template>
  <a-card hoverable class="hotelCard ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3">
    <img v-if="hotel.cover===null"
      alt="example"
      src="@/assets/cover.jpeg"
      slot="cover"
      referrerPolicy="no-referrer"
    />
    <img v-else
         alt="example"
         :src=hotel.cover
         slot="cover"
         referrerPolicy="no-referrer"
    />
    <a-tooltip :title="hotel.title" placement="top">
      <a-card-meta :title="hotel.name">
      <template slot="description">
        星级&nbsp;:&nbsp;&nbsp;<a-rate style="font-size: 15px" :value="getStar(hotel.hotelStar)" disabled allowHalf/>
        <br>评分:{{hotel.rate}}
      </template>
    </a-card-meta>
    </a-tooltip>
    <a-popover style="margin-top: 6px" v-if="judgeOne">
      <template slot="content">
        <p>在该酒店拥有正常订单</p>
      </template>
      <a-button type="link">
        <a-icon type="check-circle" theme="twoTone" two-tone-color="#52c41a" />
      </a-button>
    </a-popover>
    <a-popover style="margin-top: 6px" v-if="judgeTwo">
      <template slot="content">
        <p>在该酒店拥有异常订单</p>
      </template>
      <a-button type="link">
        <a-icon type="exclamation-circle" theme="twoTone" two-tone-color="#ffbf00" />
      </a-button>
    </a-popover>
    <a-popover style="margin-top: 6px" v-if="judgeThree">
      <template slot="content">
        <p>在该酒店拥有撤销订单</p>
      </template>
      <a-button type="link">
        <a-icon type="close-circle" theme="twoTone" two-tone-color="#e21010" />
      </a-button>
    </a-popover>
  </a-card>
</template>
<script>
  import {mapActions, mapGetters, mapMutations} from "vuex";

export default {
  name:'',
  props: {
    hotel: {}
  },
  data() {
    return{
      judgeOne:false,
      judgeTwo:false,
      judgeThree:false,
    }
  },
  computed: {
    ...mapGetters([
      'UserHotelOrderList',
      'currentHotelId',
    ])
  },
  async mounted() {
    var that=this
    that.set_currentHotelId(Number(that.hotel.id))
    await that.getUserHotelOrders(that.currentHotelId)
    console.log(that.currentHotelId)
    console.log(that.UserHotelOrderList)
    for (var i = 0; i < that.UserHotelOrderList.length; i++) {
      if(that.UserHotelOrderList[i].orderState==='已入住' ||that.UserHotelOrderList[i].orderState==='已评价'){
        that.judgeOne=true;
      }
      if(that.UserHotelOrderList[i].orderState==='异常'){
        that.judgeTwo=true;
      }
      if(that.UserHotelOrderList[i].orderState==='已取消'){
        that.judgeThree=true;
      }
    }
  },
  methods:{
    ...mapMutations([
      'set_currentHotelId',
    ]),
    ...mapActions([
      'getHotelById',
      'getUserOrders',
      'getUserHotelOrders'
    ]),
    getStar(data){
      if(data=="Four") return 4;
      else if(data=="Five") return 5;
      else return 3;
    }
  }
}
</script>
<style scoped lang="less">
    .hotelCard {
        margin: 10px 10px;
        min-width: 250px;
        max-height: 420px;
        img {
          height: 300px;
          width: 248px;
          display:flex;
          align-items:center;
          justify-content:center;

        }
    }
</style>

<style lang="less">
.hotelCard{
  .ant-card-body{
    padding: 12px
  }
}

.ant-card-hoverable:hover {
  box-shadow: 0 2px 8px rgba(0,0,0,0.4)
}

</style>