package com.gyg.user.service;

import com.gyg.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gyg.user.entity.VM.UserToVM;
import com.gyg.user.entity.VM.UserVM;
import utils.R;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author gyg
 * @since 2021-04-20
 */
public interface UserService extends IService<User> {

    /**
     *
     * @param number
     * @return
     */
    User getByNumber(String number);

    /**
     *
     * @param userVM
     * @return
     */
    R login(UserVM userVM);

    UserToVM user2VM(User user);

    User getByToken(HttpServletRequest request);

    User list2VM();
}
