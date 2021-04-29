package utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 郭永钢
 * 自定义全局异常
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerException extends RuntimeException{
    private Integer code;
    private String msg;
}

