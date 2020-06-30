package com.example.hotel.data.hotel;

import com.example.hotel.enums.HotelStar;
import com.example.hotel.po.Hotel;
import com.example.hotel.vo.HotelVO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class HotelMapperTest {

    @Autowired
    HotelMapper hotelMapper;

    @Test
    void insertHotel() {
        Hotel hotel = new Hotel();
        hotel.setHotelName("OvO酒店");
        hotel.setManagerId(1);
        hotel.setHotelStar(HotelStar.Five);
        BeanUtils.copyProperties(hotel,hotel);
        Assert.assertEquals(1, hotelMapper.insertHotel(hotel));
        int id = hotelMapper.findHotel("OvO酒店");
        Assert.assertEquals(hotelMapper.deleteHotel(id),1);
    }

    @Test
    void getHotelTest(){
        List<HotelVO> hotelList=hotelMapper.selectQualifiedHotel("1343296078",3);
        HotelVO hotel=hotelList.get(0);
        assertEquals("神仙酒店",hotel.getName());
    }

    @Test
    public void updateHotelInfo(){
        int ret=hotelMapper.updateHotel(1,"北京唐拉雅秀酒店","XiDan","北京唐拉雅秀酒店地处中央政务和金融商务区，紧邻金融街，可便捷前往天安门广场、西单商业中心。酒店位置优越，可将首都长安街壮观景致尽收眼底。");
        assertEquals(1,ret);
    }
}