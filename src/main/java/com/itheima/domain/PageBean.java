package com.itheima.domain;

import java.util.List;

public class PageBean<T> {
    private Integer currentPage;
    private Integer totalPage;
    private Integer totalSize;
    private Integer pageSize;
    private List<T> list;

    @Override
    public String toString() {
        return "PageBean{" +
                "currentPage=" + currentPage +
                ", totalPage=" + totalPage +
                ", totalSize=" + totalSize +
                ", pageSize=" + pageSize +
                ", list=" + list +
                '}';
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;

    }

    public Integer getTotalSize() {
        return totalSize;

    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
        //当设置好totalsize 就可以确定totalpage
        this.totalPage= totalSize %pageSize==0? totalSize /pageSize: totalSize /pageSize +1;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
