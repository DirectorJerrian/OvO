package com.example.hotel.bl.order;


import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.vo.OrderRateVO;
import com.example.hotel.vo.OrderVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceTest {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    AccountService accountService;
    @Autowired
    HotelService hotelService;

    @Test
    public void executeOrderTest(){
        orderMapper.setOrderAdded(1);
        orderService.executeOrder(1);
        Order target=orderMapper.getOrderById(1);
        assertEquals("已入住",target.getOrderState());
    }

    @Test
    public void handleStrangeOrderTest(){
        orderService.getAllOrders();
        orderService.restoreOrder(2);
        Order target=orderMapper.getOrderById(2);
        assertEquals("已入住",target.getOrderState());
        orderService.getAllOrders();
        orderMapper.updateOverTimeOrder(2);
        orderService.revokeOrder(2);
        target=orderMapper.getOrderById(2);
        assertEquals("已取消",target.getOrderState());
    }

    @Test
    public void updateRateTest(){
        OrderRateVO orderRateVO=new OrderRateVO();
        orderRateVO.setId(1);
        orderRateVO.setRate(4);
        orderService.updateRate(orderRateVO);
        Order target=orderMapper.getOrderById(1);
        assertEquals(Integer.valueOf(4),target.getRate());
    }

    @Test
    public void annulOrderTest(){
        User user=accountService.getUserInfo(5);
        OrderVO orderVO=new OrderVO();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String curdate = sf.format(date);
        orderVO.setCreateDate(curdate);
        orderVO.setCheckInDate(curdate);
        orderVO.setOrderState("已预订");
        orderVO.setUserId(5);
        orderVO.setPeopleNum(2);
        orderVO.setRoomNum(1);
        orderVO.setHotelId(1);
        orderVO.setPrice(200.0);
        orderVO.setClientName(user.getUserName());
        orderVO.setPhoneNumber(user.getPhoneNumber());
        Order order = new Order();
        BeanUtils.copyProperties(orderVO,order);
        orderMapper.addOrder(order);
        hotelService.updateRoomInfo(orderVO.getHotelId(),orderVO.getRoomType(),orderVO.getRoomNum());
        List<Order> allOrder=orderService.getHotelUserOrders(1,5);
        Order target=new Order();
        for(Order item: allOrder){
            if(item.getCreateDate().equals(curdate)) target=item;
        }
        System.out.println(target.getCheckInDate());
        orderService.annulOrder(target.getId());
        allOrder=orderService.getHotelUserOrders(1,5);
        Assert.assertFalse(allOrder.contains(target));
    }
}
