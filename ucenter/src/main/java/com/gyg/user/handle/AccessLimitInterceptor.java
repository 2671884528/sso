package com.gyg.user.handle;

import com.gyg.user.config.security.ResponseStatus;
import com.gyg.user.config.security.Systemcode;
import com.gyg.user.service.impl.RedisServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 郭永钢
 */
@Component
public class AccessLimitInterceptor extends HandlerInterceptorAdapter {

    Logger logger = LoggerFactory.getLogger(AccessLimitInterceptor.class);

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 配置全局拦截器
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        logger.info("请求头{}", request.getRequestURI());
        logger.info("请求地址{}", request.getRequestURI());
        String token = request.getHeader("token");
        if (request.getRequestURI().equals("/ucenter/user/login")) {
            return true;
        } else {
            if (StringUtils.isEmpty(token)) {
                ResponseStatus.response(response, Systemcode.UNAUTHORIZED);
                return false;
            }

            if (StringUtils.isBlank(token)) {
                ResponseStatus.response(response, Systemcode.UNAUTHORIZED);
                return false;
            }

            if (token.equals(redisTemplate.opsForValue().get("token"))) {
                return true;
            }
        }
        ResponseStatus.response(response,Systemcode.UNAUTHORIZED);
        return false;
    }
}
