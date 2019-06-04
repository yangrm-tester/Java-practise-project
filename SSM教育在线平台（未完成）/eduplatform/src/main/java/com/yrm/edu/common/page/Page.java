package com.yrm.edu.common.page;

import java.util.List;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className Page
 * @createTime 2019年06月04日 10:45:00
 */
public interface Page<T> extends Iterable<T> {

    /**
     * 获得第一页
     * */
    int getFirstPageNum();

    /**
     * 获得最后一页
     * */
    int getLastPageNum();

    /**
     * 获得当前页码
     * */

    int getPageNum();

    /**
     * 获得分页数量大小
     **/

    int getPageSize();

    /**
     * 获得分页数据
     * */

    List<T> getItems();

    /**
     * 获得数据总量数目
     * */
    int getItemsTotalCount();

    /**
     * 获得页数总量数目
     * */
    int getPageTotalCount();

    /**
     * 是否是第一页
     * */
    boolean isFirstPage();

    /**
     * 是否是最后一页
     * */
    boolean isLastPage();

    /**
     * 是否为空
     * */
    boolean isEmpty();

}
