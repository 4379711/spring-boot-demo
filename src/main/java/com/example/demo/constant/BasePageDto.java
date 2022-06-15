package com.example.demo.constant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YaLong
 */
@Data
@ApiModel(value = "PageDto", description = "分页查询数据")
public class BasePageDto<T> {
    @ApiModelProperty(value = "总量")
    Long total = 0L;
    @ApiModelProperty(value = "当前页的数据")
    List<T> dtoList = new ArrayList<>();

}
