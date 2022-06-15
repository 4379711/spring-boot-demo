package com.example.demo.user.domain;

import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @author YaLong
 */
@TableName(value = "user")
@Data
public class User {
    /**
     * 主键ID
     */
    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 账号
     */
    @ApiModelProperty("账号")
    @TableField(value = "account")
    private String account;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    @TableField(value = "password")
    private String password;

    /**
     * 盐
     */
    @ApiModelProperty("盐")
    @TableField(value = "salt")
    private String salt;

}
