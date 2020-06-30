package com.example.hotel.blImpl.order;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.vo.OrderRateVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final static String RESERVE_ERROR = "预订失败";
    private final static String ROOMNUM_LACK = "预订房间数量剩余不足";
    private final static String ANNUL_ERROR = "取消订单失败";
    private final static String PEOPLENUM_ERROR = "人数不符合规定";
    private final static String HAVEALREADYRATED="已评价";
    private final static String RATEERROR="评价错误";
    private final static String RATESUCCESS="评价成功";
    private final static String CREDIT_ERROR="信用值不足，无法下单";
    private final static String RESTOREORDER_SUCCESS="订单恢复成功";
    private final static String RESTOREORDER_ERROR="订单恢复失败";

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    HotelMapper hotelMapper;
    @Autowired
    HotelService hotelService;
    @Autowired
    AccountService accountService;

    private void refreshOrders(){
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        Date now=new Date();
        List<Order> pre=orderMapper.getAllOrders();
        for(int i=0;i<pre.size();i++){
            String checkDate=pre.get(i).getCheckOutDate();
            try{
                Date check=form.parse(checkDate);
                if(check.getTime()<now.getTime() && pre.get(i).getOrderState().equals("已预订")){
                    orderMapper.updateOverTimeOrder(pre.get(i).getId());
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public ResponseVO addOrder(OrderVO orderVO) {
        int reserveRoomNum = orderVO.getRoomNum();
        int curNum = hotelService.getRoomCurNum(orderVO.getHotelId(),orderVO.getRoomType());
        if(reserveRoomNum>curNum){
            return ResponseVO.buildFailure(ROOMNUM_LACK);
        }
        int numLim=orderVO.isHaveChild()? 4*orderVO.getRoomNum():2*orderVO.getRoomNum();
        if(orderVO.getPeopleNum()<0 || orderVO.getPeopleNum()>numLim){
            return ResponseVO.buildFailure(PEOPLENUM_ERROR);
        }
        User user = accountService.getUserInfo(orderVO.getUserId());
        if(user.getCredit()<=0){
            return ResponseVO.buildFailure(CREDIT_ERROR);
        }
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String curdate = sf.format(date);
            orderVO.setCreateDate(curdate);
            orderVO.setOrderState("已预订");
            orderVO.setClientName(user.getUserName());
            orderVO.setPhoneNumber(user.getPhoneNumber());
            Order order = new Order();
            BeanUtils.copyProperties(orderVO,order);
            orderMapper.addOrder(order);
            hotelService.updateRoomInfo(orderVO.getHotelId(),orderVO.getRoomType(),orderVO.getRoomNum());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(RESERVE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }



    @Override
    public List<Order> getAllOrders() {
        refreshOrders();
        return orderMapper.getAllOrders();
    }

    public List<Order> getStrangeOrders() {
        refreshOrders();
        return orderMapper.getStrangeOrders();
    }

    @Override
    public List<Order> getUserOrders(int userid) {
        refreshOrders();
        return orderMapper.getUserOrders(userid);
    }

    @Override
    public List<Order> getHotelUserOrders(int hotelId, int userId) {
        List<Order> allOrder=orderMapper.getHotelUserOrders(hotelId,userId);
        System.out.println(allOrder.size());
        return allOrder;
    }

    @Override
    public List<OrderVO> getTargetOrders(int userid){
        refreshOrders();
        System.out.println("Here+"+userid);
        return orderMapper.getTargetOrders(userid);
    }

    @Override
    public ResponseVO annulOrder(int orderid) {
        //取消订单逻辑的具体实现（注意可能有和别的业务类之间的交互）
        Order order=orderMapper.getOrderById(orderid);
        if(order.getOrderState().equals("已取消")){
            return ResponseVO.buildFailure(ANNUL_ERROR);
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String curdate = sf.format(date);
        String check_in_date = order.getCheckInDate();
        if(check_in_date.substring(0,7).equals(curdate.substring(0, 7))){
            if((Integer.valueOf(check_in_date.substring(8))-Integer.valueOf(curdate.substring(8))<=1)){
                //距离入住时间不足24小时，撤销的同时扣除信用值，信用值为订单总价值的二分之一
                accountService.createNewCreditRecord(order.getUserId(), orderid, "订单撤销", -order.getPrice()*0.5);
            }
        }
        int reserveRoomNum = order.getRoomNum();
        try{
            //OrderVO orderVO = new OrderVO();
            //BeanUtils.copyProperties(order, orderVO);
            //orderVO.setOrderState("已取消");
            //在数据库中更改状态
            orderMapper.annulOrder(orderid);
            hotelService.updateRoomInfo(order.getHotelId(),order.getRoomType(),-reserveRoomNum);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ANNUL_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO revokeOrder(int orderid){
        try{
            this.annulOrder(orderid);
        }catch (Exception e){
            return ResponseVO.buildFailure(ANNUL_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO restoreOrder(int orderid){
        try{
            orderMapper.executeOrder(orderid);
        }catch(Exception e){
            return ResponseVO.buildFailure(RESTOREORDER_ERROR);
        }
        return ResponseVO.buildSuccess(RESTOREORDER_SUCCESS);
    }

    @Override
    public ResponseVO executeOrder(int orderid){
        Order order = orderMapper.getOrderById(orderid);
        if(order.getOrderState().equals("已入住") || order.getOrderState().equals("已取消") || order.getOrderState().equals("异常")){
            return ResponseVO.buildFailure("入住失败");
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String curdate = sf.format(date);
        if(curdate.compareTo(order.getCheckInDate())<0){
            return ResponseVO.buildFailure("不能提前办理入住");
        }
        try{
            orderMapper.executeOrder(orderid);
            accountService.createNewCreditRecord(order.getUserId(),orderid,"订单执行",100);
        }catch (Exception e){
            return ResponseVO.buildFailure("入住失败");
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO deleteOrderOnly(int orderid) {
        return(ResponseVO.buildSuccess(orderMapper.delectOrderOnly(orderid)));

    }

    /**
     * @param hotelId
     * @return
     */
    @Override
    public List<Order> getHotelOrders(Integer hotelId) {
        refreshOrders();
        List<Order> orders = getAllOrders();
        return orders.stream().filter(order -> order.getHotelId().equals(hotelId)).collect(Collectors.toList());
    }

    public ResponseVO updateRate(OrderRateVO orderRateVO){
        Order order=orderMapper.getOrderById(orderRateVO.getId());
        if(order.getOrderState().equals("已评价")){
            return ResponseVO.buildFailure(HAVEALREADYRATED);
        }
        try{
            orderMapper.orderRate(orderRateVO.getId(),orderRateVO.getRate());
            orderMapper.rateOrder(orderRateVO.getId());
            int hotelId=order.getHotelId();
            double newRate=orderMapper.avgRate(hotelId);
            BigDecimal b =new BigDecimal(newRate);
            newRate=b.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
            hotelMapper.setRate(hotelId,newRate);

        }catch(Exception e){
            return  ResponseVO.buildFailure(RATEERROR);
        }
        return ResponseVO.buildSuccess(RATESUCCESS);
    }
}