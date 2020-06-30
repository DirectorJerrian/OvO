package com.example.hotel.bl.hotel;


import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.po.Hotel;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.HotelSearchVO;
import com.example.hotel.vo.HotelVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class HotelServiceTest {
    @Autowired
    HotelService hotelService;
    @Autowired
    HotelMapper hotelMapper;

    @Test
    public void hotelSearchTest(){
        HotelSearchVO hotelSearchVO=new HotelSearchVO();
        hotelSearchVO.setHotelName("神仙酒店");
        hotelSearchVO.setStar(0);
        hotelSearchVO.setRate(0);
        hotelSearchVO.setRoomType("0");
        hotelSearchVO.setBizRegion("1343296078");
        List<HotelVO> hotellist=hotelService.hotelSearch(hotelSearchVO);
        assertEquals(1,hotellist.size());
        assertEquals("神仙酒店",hotellist.get(0).getName());
    }

    @Test
    public void updateHotelInfo(){
        hotelService.updateHotelInfo(1,"店酒庭汉","Nadix","住入您迎欢");
        HotelVO target=hotelMapper.selectById(1);
        assertEquals("店酒庭汉",target.getName());
        assertEquals("Nadix",target.getBizRegion());
        assertEquals("住入您迎欢",target.getDescription());
    }

    @Test
    public void addHotelTest() throws ServiceException {
        HotelVO hotelVO=new HotelVO();
        hotelVO.setAddress("hululu");
        hotelVO.setBizRegion("XiDan");
        hotelVO.setDescription("欢迎您的光临，这里的风景很不错");
        hotelVO.setHotelStar("Five");
        hotelVO.setManagerId(1);
        hotelVO.setName("huLuLuTu");
        hotelService.addHotel(hotelVO);
        List<HotelVO> allHotel=hotelService.retrieveHotels();
        HotelVO target = new HotelVO();
        for(HotelVO item:allHotel){
            if(item.getName().equals("huLuLuTu")){
                target=item;
            }
        }
        Assert.assertEquals(target.getBizRegion(),"XiDan");
        Assert.assertEquals((int)target.getManagerId(),1);
        Assert.assertEquals(target.getDescription(),"欢迎您的光临，这里的风景很不错");
        Assert.assertEquals(target.getHotelStar(),"Five");
    }

    @Test
    public void deleteHotelTest() throws ServiceException {
        List<HotelVO> allHotel = hotelService.retrieveHotels();
        HotelVO target = new HotelVO();
        for (HotelVO item : allHotel) {
            if (item.getName().equals("huLuLuTu")) {
                target = item;
            }
        }
        hotelService.deleteHotel(target.getId());
        List<Integer> allId = new ArrayList<>();
        for (HotelVO item : allHotel) {
                allId.add(item.getId());
        }
            Assert.assertTrue(allId.contains(target.getId()));
    }

}
