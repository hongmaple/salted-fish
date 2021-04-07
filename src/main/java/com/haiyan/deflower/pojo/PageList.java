package com.haiyan.deflower.pojo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author haiyan
 */
@ApiModel("分页列表数据")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class PageList<E> {

    @ApiModelProperty("记录总数")
    private Long total;

    @ApiModelProperty("页数")
    private Integer pages;

    @ApiModelProperty("当前页码")
    private Integer pageNum;

    @ApiModelProperty("每页记录数")
    private Integer pageSize;

    @ApiModelProperty("当前页记录列表")
    private List<E> list;

    /**
     * 当一对多进行join操作查询的数据通过ResultMap映射到model列表上后，
     * 列表的元素个数可能和查询的记录数不一致
     * @param list 通过join获取的数据
     * @param ids 通过筛选获取的主键(或其他类型)列表
     */
    public PageList(List<E> list, PageInfo<?> ids) {
        this.list = list;
        total = ids.getTotal();
        pageNum = ids.getPageNum();
        pages = ids.getPages();
        pageSize = ids.getPageSize();
    }

    public PageList(PageInfo<E> pageInfo) {
        this.list = pageInfo.getList();
        total = pageInfo.getTotal();
        pageNum = pageInfo.getPageNum();
        pages = pageInfo.getPages();
        pageSize = pageInfo.getPageSize();
    }

    public static<E> PageList<E> of(List<E> list, PageInfo<?> ids) {
        return new PageList<>(list, ids);
    }

    public static<E> PageList<E> of(PageInfo<E> pageInfo) {
        return new PageList<>(pageInfo);
    }

    public static<E> PageList<E> of(Page<E> page) {
        PageList<E> pageList = new PageList<>();
        pageList.setPageNum((int)page.getCurrent());
        pageList.setPages((int)page.getPages());
        pageList.setPageSize((int)page.getSize());
        pageList.setTotal(page.getTotal());
        pageList.setList(page.getRecords());
        return pageList;
    }

    public static<E> PageList<E> of(List<E> list, Page<?> page) {
        PageList<E> pageList = new PageList<>();
        pageList.setPageNum((int)page.getCurrent());
        pageList.setPages((int)page.getPages());
        pageList.setPageSize((int)page.getSize());
        pageList.setTotal(page.getTotal());
        pageList.setList(list);
        return pageList;
    }
}