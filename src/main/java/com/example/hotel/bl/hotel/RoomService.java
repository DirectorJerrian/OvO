package com.example.hotel.bl.hotel;

import com.example.hotel.po.HotelRoom;
import com.example.hotel.vo.RoomVO;

import java.util.List;

public interface RoomService {

    /**
     * 获取某个酒店的全部房间信息
     * @param hotelId
     * @return
     */
    List<HotelRoom> retrieveHotelRoomInfo(Integer hotelId);

    /**
     * 添加酒店客房信息
     * @param roomVO
     */
    void insertRoomInfo(RoomVO roomVO);

    /**
     * 预订酒店后更新客房房间数量
     * @param hotelId
     * @param roomType
     * @param rooms
     */
    void updateRoomInfo(Integer hotelId, String roomType, Integer rooms);

    /**
     * 获取酒店指定房间剩余数量
     * @param hotelId
     * @param roomType
     * @return
     */
    int getRoomCurNum(Integer hotelId, String roomType);

    /**
     * 增加totalNum
     * @param roomId
     * @return
     */
    void addRoomNum(int roomId);

    /**
     * 减少totalNum 同时减少curNum
     * @param roomId
     * @return
     */
    void subRoomNum(int roomId);

    /**
     * 删除指定酒店的房间
     * @param hotelId
     * @return
     */
    void deleteRoomsByHotelId(Integer hotelId);


    List<Boolean> hasType(Integer hotelId);
}
