package com.haiyan.deflower.dto.response;

import com.haiyan.deflower.pojo.Flower;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author haiyan
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("花管理-花的一行数据")
public class FlowerRowVo extends Flower {
    @ApiModelProperty("分类名")
    private String categoryName;
}
