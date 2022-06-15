package com.example.demo.annotation;

import java.lang.annotation.*;

/**
 * @author yaLong
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SingleUserDataScope {
    /**
     * 系统名称
     */
    String systemName() default "业务系统";

    /**
     * 菜单名称
     */
    String menuName();

    /**
     * 页面名称
     */
    String pageName();

    /**
     * 产品表的别名
     */
    String productAlias() default "";

    /**
     * 店铺表的别名
     */
    String accountAlias() default "";

    /**
     * 项目表的别名
     */
    String projectAlias() default "";

}
