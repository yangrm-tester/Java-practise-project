package com.yrm.edu.common.page;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className TailPage
 * @createTime 2019年06月04日 10:45:00
 */
public class TailPage<T> extends AbstractPage<T> {
    /**
     * 显示10个页码
     */
    protected int showPage = 10;
    protected List<Integer> showNums = new ArrayList<>();
    protected boolean showDot = true;


    public TailPage() {

    }

    public TailPage(Page<T> page, Collection<T> collection, int itemsTotalCount) {
        this(page.getPageNum(), page.getPageSize(), itemsTotalCount, collection);
    }

    /**
     * 构造函数
     */
    public TailPage(int pageNum, int pageSize, int itemsTotalCount, Collection<T> collection) {
        this.setPageNum(pageNum);
        this.setPageSize(pageSize);
        this.setItemsTotalCount(itemsTotalCount);
        this.setItems(collection);
        this.initShowNum();
    }


    private void initShowNum() {
        int startIndex;
        int endIndex;
        if (pageNum - showPage / 2 > 1) {
            startIndex = pageNum - showPage / 2;
            endIndex = pageNum + showPage / 2 - 1;
            if (endIndex > pagesTotalCount) {
                endIndex = pagesTotalCount;
                startIndex = endIndex - showPage + 1;
            }
        } else {
            startIndex = 1;
            endIndex = pagesTotalCount <= showPage ? pagesTotalCount : showPage;
        }
        for (int i = startIndex; i <= endIndex; i++) {
            showNums.add(Integer.valueOf(i));
        }
        if (this.firstPage || this.lastPage) {
            showDot = false;
        } else {
            if (showNums.size() > 0) {
                if (showNums.get(showNums.size() - 1) == this.pagesTotalCount) {
                    showDot = false;
                }
            }
        }
    }


    public int getShowPage() {
        return showPage;
    }

    public void setShowPage(int showPage) {
        this.showPage = showPage;
    }

    public List<Integer> getShowNums() {
        return showNums;
    }

    public boolean isShowDot() {
        return showDot;
    }

    @Override
    public void setItemsTotalCount(int itemsTotalCount) {
        super.setItemsTotalCount(itemsTotalCount);
        initShowNum();
    }

    @Override
    public int getPageTotalCount() {
        return this.pagesTotalCount;
    }

}
