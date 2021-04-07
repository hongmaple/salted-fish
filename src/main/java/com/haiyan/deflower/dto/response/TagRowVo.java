package com.haiyan.deflower.dto.response;

import com.haiyan.deflower.pojo.Flower;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author haiyan
 */
@Data
@ApiModel("首页商品列表")
public class TagRowVo {
    @ApiModelProperty("分类id")
    private Long id;
    @ApiModelProperty("样式")
    private Integer style;
    @ApiModelProperty("分类名")
    private String title;
    @ApiModelProperty("花列表")
    private List<Flower> prods;
}
