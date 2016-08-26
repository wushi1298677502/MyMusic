package com.example.scxh.giveme.Modul;

import java.util.ArrayList;

/**
 * Created by scxh on 2016/7/26.
 */
public class Info {
    public PageInfo pageInfo;
    public ArrayList<Student> merchantKey;

    public ArrayList<Student> getMerchantKey() {
        return merchantKey;
    }

    public void setMerchantKey(ArrayList<Student> merchantKey) {
        this.merchantKey = merchantKey;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
