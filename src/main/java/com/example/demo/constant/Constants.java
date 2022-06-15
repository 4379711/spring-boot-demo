package com.example.demo.constant;

/**
 * 通用常量信息
 *
 * @author YaLong
 */
public class Constants {

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * 令牌有效期（分钟）
     */
    public final static long TOKEN_EXPIRE = 60 * 12;

    public final static String TOKEN_PREFIX = "login_token:";

    public final static String TOKEN_HEADER = "Authorization";

    /**
     * 成功标记
     */
    public static final Integer SUCCESS = 200;

    /**
     * 失败标记
     */
    public static final Integer FAIL = 500;


    /**
     * 未知
     */
    public static final String UNKNOWN = "unknown";

    /**
     * 用户登录出错过多锁定时间，秒
     */
    public static final long LOCK_EXPIRE_SECONDS = 60 * 60;


    public static final String FAIL_RESULT = "失败";

    public static final String SUCCESS_RESULT = "成功";

    /**
     * 超管账号
     */
    public static final String ADMIN = "admin";

    /**
     * 超管密码
     */
    public static final String ADMIN_PASSWORD = "admin123!@#";
}
