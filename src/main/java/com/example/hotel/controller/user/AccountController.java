package com.example.hotel.controller.user;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.po.User;
import com.example.hotel.po.Vipcard;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController()
@RequestMapping("/api/user")
public class AccountController {
    private final static String ACCOUNT_INFO_ERROR = "用户名或密码错误";
    private final static String VIP_INFO_ERROR = "非会员用户登录";
    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public ResponseVO login(@RequestBody UserForm userForm) {
        User user = accountService.login(userForm);
        if (user == null) {
            return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        }
        return ResponseVO.buildSuccess(user);
    }

    @PostMapping("/register")
    public ResponseVO registerAccount(@RequestBody UserVO userVO) {
        return accountService.registerAccount(userVO);
    }


    @GetMapping("/{id}/getUserInfo")
    public ResponseVO getUserInfo(@PathVariable int id) {
        User user = accountService.getUserInfo(id);
        if(user==null){
            return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        }
        return ResponseVO.buildSuccess(user);
    }

    @GetMapping("/{id}/getVIPInfo")
    public ResponseVO getVIPInfo(@PathVariable int id) {
        Vipcard vipCard =accountService.getVIPInfo(id);
        if(vipCard==null){
            return ResponseVO.buildSuccess(VIP_INFO_ERROR);
        }
        return ResponseVO.buildSuccess(vipCard);
    }

    @PostMapping("/{id}/userInfo/update")
    public ResponseVO updateInfo(@RequestBody UserInfoVO userInfoVO,@PathVariable int id){
        return accountService.updateUserInfo(id,userInfoVO.getUserName(),userInfoVO.getPhoneNumber());
    }

    @PostMapping("/{id}/userInfo/updatePassword")
    public ResponseVO updatePassword(@RequestBody UserInfoVO userInfoVO,@PathVariable int id){
        return accountService.updateUserPassword(id,userInfoVO.getPassword());
    }

    @GetMapping("/{id}/userCreditRecords")
    public ResponseVO getUserCreditRecords(@PathVariable int id){
        return ResponseVO.buildSuccess(accountService.getUserCreditRecords(id));
    }

    @PostMapping("/{id}/userInfo/updateAvatar")
    public ResponseVO updateAvatar(MultipartFile file, @PathVariable int id){
        return accountService.updateUserAvatar(id,file);
    }

    @PostMapping("/registerVIP")
    public ResponseVO registerVIP(@RequestBody RegisterVIPVO registerVIPVO){
        return accountService.registerVIP(registerVIPVO);
    }

    @PostMapping("/{userId}/vip/updateVIPSavings")
    public ResponseVO updateVIPSavings(@RequestBody VipcardVO vipcardVO, @PathVariable int userId){
        return accountService.updateVIPSavings(userId,vipcardVO);
    }

    @PostMapping("/setSavings")
    public ResponseVO setSavings(@RequestBody SetSavingsVO setSavingsVO){
        return accountService.setSavings(setSavingsVO);
    }

}
