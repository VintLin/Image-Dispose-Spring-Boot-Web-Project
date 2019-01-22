package com.felink.project.model;

import com.github.pagehelper.PageInfo;

import java.util.List;

public class MyPageInfo<T> {

    private List<T> list;
    private boolean hasPrevious;
    private boolean hasNext;
    private int pageIndex;
    private int pageSize;
    private long total;
    private int pages;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public MyPageInfo(PageInfo<T> pageInfo) {
        list = pageInfo.getList();
        hasNext = pageInfo.isHasNextPage();
        hasPrevious = pageInfo.isHasPreviousPage();
        pageIndex = pageInfo.getPageNum();
        pageSize = pageInfo.getSize();
        total = pageInfo.getTotal();
        pages = pageInfo.getPages();
    }
}
