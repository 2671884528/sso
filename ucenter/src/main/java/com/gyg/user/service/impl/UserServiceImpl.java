package com.gyg.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gyg.user.config.security.ResponseStatus;
import com.gyg.user.config.security.Systemcode;
import com.gyg.user.entity.User;
import com.gyg.user.entity.VM.UserToVM;
import com.gyg.user.entity.VM.UserVM;
import com.gyg.user.mapper.UserMapper;
import com.gyg.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author gyg
 * @since 2021-04-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisServiceImpl redisService;

    @Override
    public User getByNumber(String number) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        User user = userMapper.selectOne(wrapper.eq("number", number));
        return user;
    }

    @Override
    public R login(UserVM userVM) {
        logger.info("将要登录的账户：" + userVM.getNumber());
        String hostAddress = "";
        String token = "";
        String encode = MD5.encrypt(userVM.getPassword());
        User user = getByNumber(userVM.getNumber());
        if (ObjectUtils.isEmpty(user)) {
            return R.error().code(Systemcode.AuthError.getCode()).message(Systemcode.AuthError.getMessage());
        }
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
            logger.info("主机地址：{}", hostAddress);
            if (StringUtils.equals(encode, user.getPassword())) {
                token = JwtUtil.createToken(String.valueOf(user.getId()), hostAddress);
                redisService.insertToken("token", token);
                return R.ok().data("token",token);
            } else {
               return R.error().message("账号密码不正确");
            }
        } catch (UnknownHostException e) {
            logger.error("IO异常：获取不到主机");
            return R.error().code(Systemcode.InnerError.getCode()).message(Systemcode.InnerError.getMessage());
        }
    }

    @Override
    public UserToVM user2VM(User user) {
        UserToVM userToVM = new UserToVM();
        BeanUtils.copyProperties(user, userToVM);
        return userToVM;
    }

    @Override
    public User getByToken(HttpServletRequest request) {
        String id = JwtUtil.getUserIdByJwtToken(request);
        User user = getById(id);
        return user;
    }

    @Override
    public User list2VM() {
        List<User> list = list();
        return null;
    }
}
