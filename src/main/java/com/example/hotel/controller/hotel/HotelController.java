package com.example.hotel.controller.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomService roomService;


    @PostMapping("/addHotel")
    public ResponseVO createHotel(@RequestBody HotelVO hotelVO) throws ServiceException {

        hotelService.addHotel(hotelVO);
        return ResponseVO.buildSuccess(true);
    }

    @GetMapping("/{hotelId}/hasType")
    public ResponseVO hasType(@PathVariable int hotelId){
        System.out.println(hotelId);
        return ResponseVO.buildSuccess(roomService.hasType(hotelId));
    }

    @PostMapping("/{hotelId}/deleteHotel")
    public ResponseVO deleteHotel(@PathVariable int hotelId) throws ServiceException{
        hotelService.deleteHotel(hotelId);
        return ResponseVO.buildSuccess(true);
    }

    @GetMapping("/all")
    public ResponseVO retrieveAllHotels(){
        return ResponseVO.buildSuccess(hotelService.retrieveHotels());
    }

    @PostMapping("/roomInfo")
    public ResponseVO addRoomInfo(@RequestBody RoomVO roomVO) {
        List<Boolean> allType = roomService.hasType(roomVO.getHotelId());
        if(!allType.get(0)&&roomVO.getRoomType().equals("BigBed")) return ResponseVO.buildFailure("已有大床房");
        if(!allType.get(1)&&roomVO.getRoomType().equals("DoubleBed")) return ResponseVO.buildFailure("已有双床房");
        if(!allType.get(2)&&roomVO.getRoomType().equals("Family")) return ResponseVO.buildFailure("已有家庭房");
        roomService.insertRoomInfo(roomVO);
        return ResponseVO.buildSuccess(true);
    }
    @PostMapping("/{roomId}/addNum")
    public ResponseVO addRoomNum(@PathVariable int roomId){
        roomService.addRoomNum(roomId);
        return ResponseVO.buildSuccess();
    }
    @PostMapping("/{roomId}/subNum")
    public ResponseVO subRoomNum(@PathVariable int roomId){
        roomService.subRoomNum(roomId);
        return ResponseVO.buildSuccess();
    }
    @GetMapping("/{userid}/getTargetHotel")
    public  ResponseVO retrieveTargetHotel(@PathVariable int userid){
        return ResponseVO.buildSuccess(hotelService.getTargetHotel(userid));
    }

    @GetMapping("/{hotelId}/detail")
    public ResponseVO retrieveHotelDetail(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(hotelService.retrieveHotelDetails(hotelId));
    }

    @GetMapping("/{hotelName}/findHotel")
    public ResponseVO findHotel(@PathVariable String hotelName){
        return ResponseVO.buildSuccess(hotelService.findHotel(hotelName));
    }

    @GetMapping("/getBizRegions")
    public ResponseVO getBizRegions() {
        return ResponseVO.buildSuccess(hotelService.getBizRegions());
    }


    @GetMapping("/hotelSearch")
    public ResponseVO hotelSearch(@RequestParam String hotelName,
                                  @RequestParam String bizRegion,
                                  @RequestParam String roomType,
                                  @RequestParam Integer hotelStar,
                                  @RequestParam Integer rate
                                  ) {
        HotelSearchVO hotelSearchVO=new HotelSearchVO();
        hotelSearchVO.setHotelName(hotelName);
        hotelSearchVO.setBizRegion(bizRegion);
        hotelSearchVO.setRoomType(roomType);
        hotelSearchVO.setStar(hotelStar);
        hotelSearchVO.setRate(rate);
        List<HotelVO> ans=hotelService.hotelSearch(hotelSearchVO);
        if(ans==null)return ResponseVO.buildFailure("不存在该类型酒店");
        else return ResponseVO.buildSuccess(ans);
    }

    @PostMapping("/{hotelId}/hotelInfo/update")
    public ResponseVO updateInfo(@RequestBody HotelInfoVO hotelInfoVO, @PathVariable int hotelId){
        return hotelService.updateHotelInfo(hotelId,hotelInfoVO.getName(),hotelInfoVO.getBizRegion(),hotelInfoVO.getDescription());
    }
    @PostMapping("/{hotelId}/hotelInfo/updateCover")
    public ResponseVO updateCover(MultipartFile file, @PathVariable int hotelId){
        return hotelService.updateHotelCover(hotelId,file);
    }
}
