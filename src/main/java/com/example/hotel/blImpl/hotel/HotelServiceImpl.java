package com.example.hotel.blImpl.hotel;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.*;
import com.example.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {
    private final static String UPDATE_ERROR = "修改失败";

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoomService roomService;



    @Override
    public void addHotel(HotelVO hotelVO) throws ServiceException {
        User manager = accountService.getUserInfo(hotelVO.getManagerId());
        if (manager == null || !manager.getUserType().equals(UserType.HotelManager)) {
            throw new ServiceException("管理员不存在或者无权限！创建酒店失败！");
        }
        Hotel hotel = new Hotel();
        hotel.setDescription(hotelVO.getDescription());
        hotel.setAddress(hotelVO.getAddress());
        hotel.setHotelName(hotelVO.getName());
        hotel.setPhoneNum(hotelVO.getPhoneNum());
        hotel.setManagerId(hotelVO.getManagerId());
        hotel.setRate(hotelVO.getRate());
        hotel.setBizRegion(BizRegion.valueOf(hotelVO.getBizRegion()));
        hotel.setHotelStar(HotelStar.valueOf(hotelVO.getHotelStar()));
        hotelMapper.insertHotel(hotel);
    }

    @Override
    public void deleteHotel(int hotelId) throws ServiceException {
        HotelVO hotelVO = hotelMapper.selectById(hotelId);
        User manager = accountService.getUserInfo(hotelVO.getManagerId());
        if (manager == null || !manager.getUserType().equals(UserType.HotelManager)) {
            throw new ServiceException("管理员不存在或者用户无权限！删除酒店失败！");
        }
        int hotelID = hotelVO.getId();
        hotelMapper.deleteHotel(hotelID);
        roomService.deleteRoomsByHotelId(hotelID);
    }

    @Override
    public void updateRoomInfo(Integer hotelId, String roomType, Integer rooms) {
        roomService.updateRoomInfo(hotelId, roomType, rooms);
    }

    @Override
    public int getRoomCurNum(Integer hotelId, String roomType) {
        return roomService.getRoomCurNum(hotelId, roomType);
    }

    @Override
    public List<HotelVO> retrieveHotels() {

        return hotelMapper.selectAllHotel();
    }

    private boolean starSatisfaction(int star,HotelVO hotel){
        String starS=hotel.getHotelStar();
        int hotelStar=0;
        if(starS.equals("Three")){
            hotelStar=3;
        }else if (starS.equals("Four")){
            hotelStar=4;
        }else if(starS.equals("Five")){
            hotelStar=5;
        }
        if(hotelStar>=star)return true;
        else return false;
    }

    private boolean roomSatisfaction(String room,int id){
        if(room.equals("0"))return true;
        List<HotelRoom> list=roomMapper.roomSatisfaction(id,room);
        if(list.size()>0){

            return true;
        }
        else return false;
    }

    private boolean nameSatisfaction(String search,String hotelName)  {
        if(search.equals(""))return true;
        else{
            try{
                if (new String(hotelName.getBytes(),"utf-8").indexOf(new String(search.getBytes(),"utf-8")) != -1 ){
                    return true;
                }else{
                    return false;
                }
            }catch (Exception e){
                System.out.println("发现错误");
                return false;
            }

        }

    }


    @Override
    public List<HotelVO> hotelSearch(HotelSearchVO hotelSearchVO){
        List<HotelVO> firstChoose=hotelMapper.selectQualifiedHotel(hotelSearchVO.getBizRegion(),hotelSearchVO.getRate());
        List<HotelVO> ans=new ArrayList<>();
        System.out.println("酒店的数量为"+firstChoose.size());
        for(HotelVO i: firstChoose){
            if(starSatisfaction(hotelSearchVO.getStar(),i)&&roomSatisfaction(hotelSearchVO.getRoomType(),i.getId())&&nameSatisfaction(hotelSearchVO.getHotelName(),i.getName())){
                ans.add(i);
                System.out.println(i.getId());
            }
        }


        return ans;
    };

    @Override
    public List<HotelVO> getTargetHotel(int userid) {
        return hotelMapper.selectManager(userid);
    }

    @Override
    public HotelVO retrieveHotelDetails(Integer hotelId) {
        HotelVO hotelVO = hotelMapper.selectById(hotelId);
        List<HotelRoom> rooms = roomService.retrieveHotelRoomInfo(hotelId);
        List<RoomVO> roomVOS = rooms.stream().map(r -> {
            RoomVO roomVO = new RoomVO();
            roomVO.setId(r.getId());
            roomVO.setPrice(r.getPrice());
            roomVO.setRoomType(r.getRoomType().toString());
            roomVO.setCurNum(r.getCurNum());
            roomVO.setTotal(r.getTotal());
            return roomVO;
        }).collect(Collectors.toList());
        hotelVO.setRooms(roomVOS);

        return hotelVO;
    }

    @Override
    public List<String> getBizRegions(){
        List<String>firstchoose=hotelMapper.getBizRegions();
        if(firstchoose.size()<2)return firstchoose;
        else{
            List<String> ans=new ArrayList<>();
            ans.add(firstchoose.get(0));
            for(int i=1;i<firstchoose.size();i++){
                int signal=1;
                for(int j=0;j<ans.size();j++){
                    if(firstchoose.get(i).equals(ans.get(j))){
                        signal=0;
                        break;
                    }
                }
                if(signal==1)ans.add(firstchoose.get(i));
            }
            return ans;
        }

    }

    @Override
    public int findHotel(String hotelName) {
        return hotelMapper.findHotel(hotelName);
    }

    ;

    @Override
    public ResponseVO updateHotelInfo(int id, String name, String bizRegion, String description) {
        try {
            hotelMapper.updateHotel(id, name, bizRegion, description);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override

    public ResponseVO updateHotelCover(int id, MultipartFile cover) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.ENDPOINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtils.KEYID;
        String accessKeySecret = ConstantPropertiesUtils.KEYSECRET;
        String bucketName = ConstantPropertiesUtils.BUCKETNAME;


        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流。
            InputStream inputStream = cover.getInputStream();

            //获取文件名称
            String fileName = cover.getOriginalFilename();
            //1.由于文件名重复会覆盖，生成随机文件名
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid + fileName;
            //2.把文件按照日期分类
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName = "Cover/" + datePath + "/" + fileName; //2020/6/4/dafdf.jpg

            //第二个参数：上传到oss的文件路径和文件名称  /aa/bb1.jpg
            ossClient.putObject(bucketName, fileName, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传之后oss返回的文件url返回（）
            //url格式：https://edu-guli-study.oss-cn-beijing.aliyuncs.com/%25U%7EHW%2502P2OH6FXR%29%5B8%60T2A.png
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
            try {
                hotelMapper.updateCoverURL(id, url);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return ResponseVO.buildFailure(UPDATE_ERROR);
            }
            return ResponseVO.buildSuccess(true);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
