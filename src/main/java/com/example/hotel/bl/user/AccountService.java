package com.example.hotel.bl.user;

import com.example.hotel.po.User;
import com.example.hotel.po.Vipcard;
import com.example.hotel.vo.*;
import com.example.hotel.po.CreditRecord;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author huwen
 * @date 2019/3/23
 */
public interface AccountService {

    /**
     * 注册账号
     *
     * @return
     */
    ResponseVO registerAccount(UserVO userVO);

    /**
     * 用户登录，登录成功会将用户信息保存再session中
     *
     * @return
     */
    User login(UserForm userForm);

    /**
     * 获取用户个人信息
     * @param id
     * @return
     */
    User getUserInfo(int id);

    User getUserInfo(String email);

    /**
     * 更新用户个人信息
     * @param id

     * @param username
     * @param phonenumber
     * @return
     */
    ResponseVO updateUserInfo(int id,String username,String phonenumber);

    /**
     * 更新用户密码
     * @param id
     * @param password
     * @return
     */
    ResponseVO updateUserPassword(int id,String password);
    /** @param avatar */
    ResponseVO updateUserAvatar(int id, MultipartFile avatar);

    /**
     * 获取用户的信用值变化记录
     * @param userid
     * @return
     */
    List<CreditRecord> getUserCreditRecords(int userid);

    /**
     * 创建一条新的信用变更记录
     * @param
     * @return
     */
    ResponseVO createNewCreditRecord(int userid,int orderid,String operation,double credit_delta);

    /**
     *
     *
     */
    ResponseVO registerVIP(RegisterVIPVO registerVIPVO);

    Vipcard getVIPInfo(int id);

    ResponseVO setSavings(SetSavingsVO setSavingsVO);


    /**
     * 更新会员储值
     * @param userId
     * @param vipcardVO
     * @return
     */
    ResponseVO updateVIPSavings(int userId,VipcardVO vipcardVO);
}
