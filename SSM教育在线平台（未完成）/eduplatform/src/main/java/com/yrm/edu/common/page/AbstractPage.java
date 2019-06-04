package com.yrm.edu.common.page;

import com.yrm.edu.common.util.BeanUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className AbstractPage
 * @createTime 2019年06月04日 10:45:00
 */
public class AbstractPage<T> implements Page<T> {

    /**
     * 默认第一页码数
     */
    public static final int DEFAULT_FIRST_PAGE_NUM = 1;

    /**
     * 默认分页大小
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    protected int pageNum = DEFAULT_FIRST_PAGE_NUM;

    protected int pageSize = DEFAULT_PAGE_SIZE;

    /**
     * 总数量
     */
    protected int itemsTotalCount = 0;
    /**
     * 总页码数
     */
    protected int pagesTotalCount = 0;
    /**
     * 每页数据
     */
    protected List<T> items;

    protected boolean firstPage;

    protected boolean lastPage;

    protected boolean isEmpty;
    /**
     * 起始索引
     */
    protected int startIndex;

    //排序
    private String sortField="update_time";
    //排序方向
    private String sortDirection = "DESC";

    @Override
    public int getFirstPageNum() {
        return DEFAULT_FIRST_PAGE_NUM;
    }

    @Override
    public int getLastPageNum() {
        return itemsTotalCount;
    }

    @Override
    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        if (pageNum < DEFAULT_FIRST_PAGE_NUM) {
            pageNum = DEFAULT_FIRST_PAGE_NUM;
        }
        this.pageNum = pageNum;
    }


    @Override
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public List<T> getItems() {
        return items;
    }

    public void setItems(Collection<T> items) {
        if (CollectionUtils.isEmpty(items)) {
            this.items = Collections.EMPTY_LIST;
        }
        this.items = new ArrayList<>(items);
        /**是否是第一页*/
        this.firstPage = this.pageNum == DEFAULT_FIRST_PAGE_NUM;
        /**是否是最后一页*/
        this.lastPage = this.pageNum == this.pagesTotalCount;
    }

    @Override
    public int getItemsTotalCount() {
        return itemsTotalCount;
    }

    public void setItemsTotalCount(int itemsTotalCount) {
        this.itemsTotalCount = itemsTotalCount;
        if (itemsTotalCount % pageSize == 0) {
            pagesTotalCount = itemsTotalCount / pageSize;
        } else {
            pagesTotalCount = (itemsTotalCount / pageSize) + 1;
        }
        if (this.pageSize > this.itemsTotalCount) {
            this.lastPage = true;
            this.firstPage = true;
        }
        if (this.pageNum > pagesTotalCount) {
            pageNum = DEFAULT_FIRST_PAGE_NUM;
        }
    }


    @Override
    public int getPageTotalCount() {
        return pagesTotalCount;
    }

    @Override
    public boolean isFirstPage() {
        firstPage = (getPageNum() <= getFirstPageNum());
        return firstPage;
    }

    @Override
    public boolean isLastPage() {
        return lastPage;
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return this.items.iterator();
    }

    /**
     * 获得上一页页码
     */
    public int getPrePageNum() {
        return isFirstPage() ? getFirstPageNum() : pageNum - 1;
    }

    /**
     * 获得下一页页码
     */
    public int getNextPageNum() {
        return isLastPage() ? getLastPageNum() : pageNum + 1;
    }

    /**
     * 获得起始位置
     */
    public int getStartIndex() {
        this.startIndex = (pageNum - 1) * pageSize;
        return startIndex < 0 ? 0 : startIndex;
    }

    /**
     * 按照sortField升序
     * @param sortField：指java bean中的属性
     */
    public void ascSortField(String sortField) {
        if(StringUtils.isNotEmpty(sortField)){
            this.sortField = BeanUtil.fieldToColumn(sortField);
            this.sortDirection = " ASC ";
        }
    }

    /**
     * 按照sortField降序
     * @param sortField ：指java bean中的属性
     */
    public void descSortField(String sortField) {
        if(StringUtils.isNotEmpty(sortField)){
            this.sortField = BeanUtil.fieldToColumn(sortField);
            this.sortDirection = " DESC ";
        }
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }


    @Override
    public String toString() {
        return "AbstractPage{" +
                "pageNum=" + pageNum +
                ", items=" + items.toString() +
                '}';
    }
}
