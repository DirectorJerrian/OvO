package com.example.hotel.data.order;

import com.example.hotel.bl.order.OrderService;
import com.example.hotel.po.Order;
import com.example.hotel.vo.OrderRateVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderMapperTest {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderService orderService;

    @Test
    public void executeOrderTest(){
            orderMapper.executeOrder(101);
            Order order=orderMapper.getOrderById(101);
            assertEquals("已入住",order.getOrderState());
            orderMapper.setOrderAdded(101);
    }

    @Test
    public void handleStrangeOrderTest(){
        orderService.getAllOrders();
        orderMapper.annulOrder(102);
        Order order=orderMapper.getOrderById(102);
        assertEquals("已取消",order.getOrderState());
        orderMapper.setOrderAdded(102);
    }

    @Test
    public void updateRateTest(){
        int row=orderMapper.orderRate(1,4);
        assertEquals(1,row);
    }
}
