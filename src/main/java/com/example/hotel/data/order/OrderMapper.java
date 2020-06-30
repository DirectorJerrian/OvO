package com.example.hotel.data.order;

import com.example.hotel.po.Order;
import com.example.hotel.vo.OrderVO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Mapper
@Repository
public interface OrderMapper {

    int addOrder(Order order);

    int updateOverTimeOrder(@Param("orderid") int orderid);

    List<Order> getAllOrders();

    List<Order> getStrangeOrders();

    List<Order> getUserOrders(@Param("userid") int userid);

    List<OrderVO> getTargetOrders(@Param("userid") int userid);

    int annulOrder(@Param("orderid") int orderid);

    int delectOrderOnly(@Param("orderid") int orderid);

    int executeOrder(@Param("orderid") int orderid);

    int setOrderAdded(@Param("orderid") int orderid);

    int rateOrder(@Param("orderid") int orderid);

    Order getOrderById(@Param("orderid") int orderid);

    int orderRate(@Param("orderid") int orderid,@Param("rate")int rate);

    double avgRate(@Param("hotelId") int hotelId);

    List<Order> getHotelUserOrders(@Param("hotelId") int hotelId,@Param("userId") int userId);

}
