package com.haiyan.deflower.dto.request;

import com.haiyan.deflower.pojo.PageDomain;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author haiyan
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("查询留言")
public class EvaluationQuery extends PageDomain {
    /**
     * 父评论id
     */
    private Long parentId;

    /**
     * 商品id
     */
    private Long flowerId;
}
