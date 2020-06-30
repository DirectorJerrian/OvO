package com.example.hotel.bl.hotel;

import com.example.hotel.po.HotelRoom;
import com.example.hotel.vo.RoomVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class RoomServiceTest {
    @Autowired RoomService roomService;

    @Test
    public void insertRoomInfoTest(){
        RoomVO roomVO=new RoomVO();
        roomVO.setCurNum(0);
        roomVO.setPrice(200.0);
        roomVO.setRoomType("Family");
        roomVO.setTotal(0);
        roomVO.setHotelId(3);
        roomService.insertRoomInfo(roomVO);
        List<HotelRoom> allRoom = roomService.retrieveHotelRoomInfo(3);
        List<String> allType=new ArrayList<>();
        for(HotelRoom item: allRoom){
            allType.add(String.valueOf(item.getRoomType()));
        }
        Assert.assertTrue(allType.contains("家庭房"));

    }

}