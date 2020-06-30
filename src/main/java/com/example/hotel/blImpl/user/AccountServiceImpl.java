package com.example.hotel.blImpl.user;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.data.user.CreditMapper;
import com.example.hotel.data.vip.VipMapper;
import com.example.hotel.data.voucher.VoucherMapper;
import com.example.hotel.po.CreditRecord;
import com.example.hotel.po.User;
import com.example.hotel.po.Vipcard;
import com.example.hotel.po.Voucher;
import com.example.hotel.vo.*;
import com.example.utils.ConstantPropertiesUtils;
import org.apache.ibatis.jdbc.Null;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class AccountServiceImpl implements AccountService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    private final static String UPDATE_ERROR = "修改失败";
    private final static String PHONENUMBER_ERROR = "手机账号已被注册";
    private final static String CREDIT_ERROR="无法获取用户信用值记录";
    private final static String REGISTERVIP_SUCCESS="VIP注册成功";
    private final static String REGISTERVIP_FAILURE="VIP注册失败";
    private final static String SETSAVINGS_FAILURE="VIP储蓄值使用失败";
    private final static String SETSAVINGS_SUCCESS="VIP储蓄值使用成功";
    private final static String RECHARGE_ERROR="充值失败";

    @Autowired
    private VoucherMapper voucherMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private CreditMapper creditMapper;

    @Autowired
    private VipMapper vipMapper;

    @Override
    public ResponseVO registerAccount(UserVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        User user1 = accountMapper.getAccountByphoneNumber(userVO.getPhoneNumber());
        if (user1 != null) {
            return ResponseVO.buildFailure(PHONENUMBER_ERROR);
        }
        user1 = accountMapper.getAccountByName(userVO.getEmail());
        if (user1 != null) {
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        try {
            accountMapper.createNewAccount(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public User login(UserForm userForm) {
        User user = accountMapper.getAccountByName(userForm.getEmail());
        if (null == user || !user.getPassword().equals(userForm.getPassword())) {
            return null;
        }
        return user;
    }

    @Override
    public User getUserInfo(int id) {
        //refresh credit
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String curdate = sf.format(date);
        /**
        if(curdate.substring(8).equals("01")){
            //是否为VIP
            Vipcard vipcard = vipMapper.getVIPInfoByUserId(id);
            if(vipcard==null){
                createNewCreditRecord(id, -1, "信用值成长", 10);
            }
            else {
                if (vipcard.getLevel() == 1) {
                    createNewCreditRecord(id, -1, "信用值成长", 15);
                } else if (vipcard.getLevel() == 2) {
                    createNewCreditRecord(id, -1, "信用值成长", 20);
                } else if (vipcard.getLevel() == 3) {
                    createNewCreditRecord(id, -1, "信用值成长", 25);
                } else {
                    createNewCreditRecord(id, -1, "信用值成长", 10);
                }
            }
        }*/
        User user = accountMapper.getAccountById(id);
        if (user == null) {
            return null;
        }
        return user;
    }

    @Override
    public User getUserInfo(String email){
        User user = accountMapper.getAccountByName(email);
        if(user==null){
            return null;
        }
        return user;
    }

    @Override
    public ResponseVO updateUserInfo(int id, String username, String phonenumber) {
        try {
            accountMapper.updateAccount(id, username, phonenumber);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO updateUserPassword(int id, String password) {
        try {
            accountMapper.updatePassword(id, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<CreditRecord> getUserCreditRecords(int userid){
        return creditMapper.getUserRecords(userid);
    }

    @Override
    public ResponseVO createNewCreditRecord(int userid,int orderid,String operation,double credit_delta){
        CreditRecord creditRecord = new CreditRecord();
        creditRecord.setCredit_delta(credit_delta);
        creditRecord.setOperation(operation);
        creditRecord.setOrderId(orderid);
        creditRecord.setUserId(userid);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String curdate = sf.format(date);
        creditRecord.setOperationDate(curdate);
        User user = getUserInfo(userid);
        creditRecord.setCredit_result(user.getCredit()+credit_delta);
        try{
            creditMapper.createNewRecord(creditRecord);
            accountMapper.updateUserCredit(userid, creditRecord.getCredit_result());
        }catch (Exception e){
            System.out.println(e.getMessage());
            ResponseVO.buildFailure("用户信用值记录创建失败");
        }
        return ResponseVO.buildSuccess(true);
    }


    /*public ResponseVO updateUserAvatar(int id, String avatar){
        System.out.println(avatar);
        return ResponseVO.buildSuccess();
    }*/
    @Override

    public ResponseVO updateUserAvatar(int id, MultipartFile avatar) {
        String endpoint = ConstantPropertiesUtils.ENDPOINT;
        String accessKeyId = ConstantPropertiesUtils.KEYID;
        String accessKeySecret = ConstantPropertiesUtils.KEYSECRET;
        String bucketName = ConstantPropertiesUtils.BUCKETNAME;


        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流。
            InputStream inputStream = avatar.getInputStream();

            //获取文件名称
            String fileName = avatar.getOriginalFilename();
            //1.由于文件名重复会覆盖，生成随机文件名
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid + fileName;
            //2.把文件按照类型+日期分类
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName = "Avatar/" + datePath + "/" + fileName;

            //第二个参数：上传到oss的文件路径和文件名称
            ossClient.putObject(bucketName, fileName, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传之后oss返回的文件url返回（）
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
            try {
                accountMapper.updateAvatarURL(id, url);
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

    @Override
    public ResponseVO updateVIPSavings(int userId, VipcardVO vipcardVO) {
        try {
            Voucher voucher=new Voucher();
            voucher.setUserId(userId);
            voucher.setDescription("九折优惠券");
            voucher.setDiscount(0.9);
            voucher.setTarget_money(0);
            voucher.setDiscount_money(0);
            voucher.setNumber(2);
            voucher.setStatus("可使用");
            if (vipcardVO.getVip_credit()<100){
                vipcardVO.setLevel(0);
            }else if(vipcardVO.getVip_credit()<800){
                vipcardVO.setLevel(1);
            }else if(vipcardVO.getVip_credit()<2000){
                if(vipcardVO.getLevel()<2){
                    voucherMapper.addVoucher(voucher);
                }
                vipcardVO.setLevel(2);
            }else{
                if(vipcardVO.getLevel()<2){
                    voucherMapper.addVoucher(voucher);
                }
                vipcardVO.setLevel(3);
            }
            accountMapper.updateVIPCard(userId,vipcardVO.getVip_credit(), vipcardVO.getLevel(),vipcardVO.getSavings());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(RECHARGE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO registerVIP(RegisterVIPVO registerVIPVO){
        Vipcard vip=new Vipcard();
        vip.setCard_type(registerVIPVO.getType());
        vip.setLevel(0);
        vip.setSavings(0);
        vip.setInfo(registerVIPVO.getInfo());
        vip.setUserId(registerVIPVO.getUserId());
        vip.setInfo(registerVIPVO.getInfo());
        try{
            vipMapper.addVipCard(vip);
            accountMapper.registerVIP(vip.getUserId());
            return  ResponseVO.buildSuccess(REGISTERVIP_SUCCESS);
        }catch (Exception e){
            return ResponseVO.buildSuccess(REGISTERVIP_FAILURE);
        }
    };

    @Override
    public Vipcard getVIPInfo(int id){
        Vipcard vipInfo=vipMapper.getVIPInfoByUserId(id);
        return  vipInfo;
    }

    @Override
    public ResponseVO setSavings(SetSavingsVO setSavingsVO){
        try{
            vipMapper.setSavings(setSavingsVO.getCardId(),setSavingsVO.getSavings());
            return ResponseVO.buildSuccess(SETSAVINGS_SUCCESS);
        }catch (Exception e){
            return ResponseVO.buildFailure(SETSAVINGS_FAILURE);
        }

    }

}
