package com.gyg.user.controller;


import com.gyg.user.entity.User;
import com.gyg.user.entity.VM.UserToVM;
import com.gyg.user.entity.VM.UserVM;
import com.gyg.user.service.impl.UserServiceImpl;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.R;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author gyg
 * @since 2021-04-20
 */
@RestController
@RequestMapping("/ucenter/user")
@ApiModel("用户控制中心")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping(value = "/login")
    public R login(@RequestBody UserVM userVM) {
        return userService.login(userVM);
    }

    @GetMapping(value = "/getUserMessage")
    public R login(HttpServletRequest request) {
        User user = userService.getByToken(request);
        UserToVM userVM = userService.user2VM(user);
        return R.ok().data("user",userVM);
    }

    @GetMapping(value = "/list")
    public R list() {

        List<User> userlist = userService.list();

        return R.ok().data("userlist", userlist);
    }

}

