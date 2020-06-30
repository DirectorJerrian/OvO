package com.example.hotel.data.user;

import com.example.hotel.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface AccountMapper {

    /**
     * 创建一个新的账号
     *
     * @return
     */
    int createNewAccount(User user);

    int registerVIP(int id);

    List<User> getAllUser();
    /**
     * 根据用户名查找账号
     * @param email
     * @return
     */
    User getAccountByName(@Param("email") String email);

    User getAccountById(@Param("id") int id);
    User getAccountByphoneNumber(@Param("phoneNumber") String phoneNumbr);

    /**
     * 更新用户信息
     * @param id
     * @param username
     * @param phonenumber
     * @return
     */
    int updateAccount(@Param("id") int id,@Param("userName") String username, @Param("phoneNumber") String phonenumber);

    /** @param password*/
    int updatePassword(@Param("id") int id,@Param("password") String password);

    /** @param avatar*/
    int updateAvatarURL(@Param("id") int id,@Param("avatar") String avatar);
    int updateUserCredit(@Param("id") int id,@Param("credit") double credit);
    /**
     * 更新VIP信息
     * @param userId
     * @param level
     * @param savings
     * @return
     */
    int updateVIPCard(@Param("userId") int userId,@Param("vip_credit") double vip_credit, @Param("level") int level,@Param("savings") double savings);
}