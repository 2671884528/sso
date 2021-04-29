package com.gyg.user.config.security;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 郭永钢
 */

public class ResponseStatus {

    private static final Logger logger = LoggerFactory.getLogger(ResponseStatus.class);
    private static final Gson gson = new Gson();

    public static void response(HttpServletResponse resp, Systemcode systemcode) {
        String json = gson.toJson(systemcode);
        resp.setContentType("application/json;charset=utf-8");
        try {
            resp.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("返回前端异常{}", e.getMessage());
            response(resp,Systemcode.InnerError);
        }
    }
}
