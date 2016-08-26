package com.example.scxh.giveme.Modul;

/**
 * Created by scxh on 2016/7/26.
 */
public class PageInfo{
    int total;
    int pagesize;
    int lastPageNumber;
    int PageNow;
    int CurrentNum;
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getLastPageNumber() {
        return lastPageNumber;
    }

    public void setLastPageNumber(int lastPageNumber) {
        this.lastPageNumber = lastPageNumber;
    }

    public int getPageNow() {
        return PageNow;
    }

    public void setPageNow(int pageNow) {
        PageNow = pageNow;
    }

    public int getCurrentNum() {
        return CurrentNum;
    }

    public void setCurrentNum(int currentNum) {
        CurrentNum = currentNum;
    }



}
