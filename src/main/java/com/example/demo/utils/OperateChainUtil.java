package com.example.demo.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * 生成追踪号
 * 注意:
 * 使用完一定要清空上下文!
 * 使用完一定要清空上下文!
 * 使用完一定要清空上下文!
 *
 * @author yaLong
 */
public class OperateChainUtil implements AutoCloseable {
    private static final Snowflake SNOWFLAKE = IdUtil.getSnowflake(1L, 1L);

    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static String getContext() {
        return CONTEXT.get();
    }

    /**
     * 从上下文中获取一个追踪号,如果没有就生产一个
     *
     * @return 追踪号
     */
    public static String getContextWithDefault() {
        String s = getContext();
        if (s == null) {
            s = setContext();
        }
        return s;
    }

    public static String setContext() {
        String idStr = SNOWFLAKE.nextIdStr();
        CONTEXT.set(idStr);
        return idStr;
    }

    public static void clearContext() {
        CONTEXT.remove();
    }

    @Override
    public void close() throws Exception {
        clearContext();
    }
}
