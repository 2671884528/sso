package utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author 郭永钢
 */

public class JwtUtil {

    // 过期时间
    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    // 加密密钥
    public static final String SECRET = "cdhushhfmaiwdjiajdwdadkwda";

    /**
     * @param id   用户id
     * @param name 用户名字
     * @return token 生成的token
     * setHeaderParam
     * setSubject
     * setIssuedAt
     * setExpiration
     * claim
     * compact
     * signWith 建议您通过调用JwtBuilder的signWith方法来指定签名密钥，
     * 并让JJWT确定指定密钥允许的最安全算法。
     */
    public static String createToken(String id, String host) {

        return Jwts.builder()
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                .setSubject("gyg")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim("id", id)
                .claim("host", host)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();

    }

    /**
     * 判断是否存在
     *
     * @param token
     * @return
     */
    public static boolean checkToken(String token) {

        if (StringUtils.isEmpty(token)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     *
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String token = request.getHeader("token");
            if (StringUtils.isEmpty(token)) {
                return false;
            }
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 根据token获取会员id
     * @param request
     * @return
     */
    public static String getUserIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if (StringUtils.isEmpty(jwtToken)) {
            return "";
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String) claims.get("id");
    }

    /**
     * 根据token获取主机，防止token被盗
     * @param request
     * @return
     */
    public static String getHostByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if (StringUtils.isEmpty(jwtToken)) {
            return "";
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String) claims.get("host");
    }
}
