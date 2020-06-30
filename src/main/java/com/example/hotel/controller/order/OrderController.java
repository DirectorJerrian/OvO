package com.example.hotel.controller.order;

import com.example.hotel.bl.order.OrderService;
import com.example.hotel.vo.OrderRateVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: chenyizong
 * @Date: 2020-02-29
 */


@RestController()
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/addOrder")
    public ResponseVO reserveHotel(@RequestBody OrderVO orderVO){
        return orderService.addOrder(orderVO);
    }

    @PostMapping("/orderRate")
    public ResponseVO reserveRate(@RequestBody OrderRateVO orderRateVO){return orderService.updateRate(orderRateVO);}

    @GetMapping("/getAllOrders")
    public ResponseVO retrieveAllOrders(){
        return ResponseVO.buildSuccess(orderService.getAllOrders());
    }

    @GetMapping("/{orderId}/revokeOrder")
    public ResponseVO revokeOrder(@PathVariable int orderId){
        return ResponseVO.buildSuccess(orderService.revokeOrder(orderId));
    }

    @GetMapping("/{orderId}/restoreOrder")
    public ResponseVO restoreOrder(@PathVariable int orderId){
        return ResponseVO.buildSuccess(orderService.restoreOrder(orderId));
    }

    @PostMapping("/{orderId}/executeOrder")
    public ResponseVO executeOrder(@PathVariable int orderId){
        return ResponseVO.buildSuccess(orderService.executeOrder(orderId));
    }

    @PostMapping("/{orderId}/deleteOrderOnly")
    public ResponseVO deleteOrderOnly(@PathVariable int orderId){
        return ResponseVO.buildSuccess(orderService.deleteOrderOnly(orderId));
    }
    @GetMapping("/{hotelId}/{userId}/getUserHotelOrders")
    public ResponseVO getUserHotelOrders(@PathVariable int hotelId,@PathVariable int userId){
        return ResponseVO.buildSuccess(orderService.getHotelUserOrders(hotelId,userId));
    }

    @GetMapping("/getStrangeOrders")
    public ResponseVO retrieveStrangeOrders(){
        return ResponseVO.buildSuccess(orderService.getStrangeOrders());
    }

    @GetMapping("/{userid}/getUserOrders")
    public  ResponseVO retrieveUserOrders(@PathVariable int userid){
        return ResponseVO.buildSuccess(orderService.getUserOrders(userid));
    }
    @GetMapping("/{userid}/getTargetOrders")
    public  ResponseVO retrieveTargetOrders(@PathVariable int userid){
        return ResponseVO.buildSuccess(orderService.getTargetOrders(userid));
    }

    @GetMapping("/{orderid}/annulOrder")
    public ResponseVO annulOrder(@PathVariable int orderid){
        return orderService.annulOrder(orderid);
    }

    @GetMapping("/{hotelId}/allOrders")
    public ResponseVO retrieveHotelOrders(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(orderService.getHotelOrders(hotelId));
    }
}
