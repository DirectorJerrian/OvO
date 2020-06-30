package com.example.hotel.data.hotel;

import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.RoomVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoomMapper {

    int updateRoomInfo(@Param("hotelId") Integer hotelId,@Param("roomType") String roomType,@Param("curNum") Integer curNum);

    int insertRoom(RoomVO roomVO);

    List<HotelRoom> selectRoomsByHotelId(@Param("hotelId") Integer hotelId);

    int getRoomCurNum(@Param("hotelId") Integer hotelId,@Param("roomType") String roomType);

    List<HotelRoom> roomSatisfaction(@Param("hotel_id")Integer id, @Param("roomType") String roomType);


    void addTargetNum(@Param("roomId") int roomId);

    void subTargetNum(@Param("roomId") int roomId);


    void deleteRoomsByHotelId(@Param("hotelId") Integer hotelId);

    List<RoomVO> getBig(@Param("hotelId") int hotelId);

    List<RoomVO> getDouble(@Param("hotelId") int hotelId);

    List<RoomVO> getFam(@Param("hotelId") int hotelId);
}
