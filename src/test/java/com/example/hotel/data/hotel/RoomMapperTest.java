package com.example.hotel.data.hotel;

import com.example.hotel.po.HotelRoom;
import com.example.hotel.vo.RoomVO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class RoomMapperTest {

    @Autowired
    RoomMapper roomMapper;

    @Test
    void insertRoom() {
        RoomVO roomVO = new  RoomVO();
        roomVO.setHotelId(3);
        roomVO.setTotal(3);
        roomVO.setRoomType("DoubleBed");
        roomVO.setPrice(200.0);
        roomVO.setCurNum(2);
        Assert.assertEquals(1,roomMapper.insertRoom(roomVO));
    }
}