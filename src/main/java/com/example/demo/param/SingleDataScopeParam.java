package com.example.demo.param;

import lombok.Data;

/**
 * 存放权限切面动态生成的sql
 *
 * @author yaLong
 */
@Data
public class SingleDataScopeParam {
    private String productSql;
    private String accountSql;
    private String projectSql;
}
