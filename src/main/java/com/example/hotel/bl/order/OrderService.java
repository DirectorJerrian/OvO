package com.example.hotel.bl.order;

import com.example.hotel.po.Order;
import com.example.hotel.vo.OrderRateVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;


import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
public interface OrderService {

    /**
     * 预订酒店
     * @param orderVO
     * @return
     */
    ResponseVO addOrder(OrderVO orderVO);

    /**
     * 获得所有订单信息
     * @return
     */
    List<Order> getAllOrders();

    List<Order> getStrangeOrders();

    /**
     * 获得指定用户的所有订单信息
     * @param userid
     * @return
     */
    List<Order> getUserOrders(int userid);

    List<Order> getHotelUserOrders(int hotelId,int userId);

    /**
     * 获取指定管理员管理酒店的所有订单信息
     * @param userid
     * @return
     */
    List<OrderVO> getTargetOrders(int userid);

    /**
     * 撤销订单
     * @param orderid
     * @return
     */
    ResponseVO annulOrder(int orderid);

    ResponseVO revokeOrder(int orderid);

    ResponseVO restoreOrder(int orderid);

    ResponseVO executeOrder(int orderid);

    ResponseVO deleteOrderOnly(int orderid);

    /**
     * 查看酒店的所有订单
     * @param hotelId
     * @return
     */
    List<Order> getHotelOrders(Integer hotelId);

    ResponseVO updateRate(OrderRateVO orderRateVO);
}
