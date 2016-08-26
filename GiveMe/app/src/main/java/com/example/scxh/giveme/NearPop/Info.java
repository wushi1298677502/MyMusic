package com.example.scxh.giveme.NearPop;

import com.warmtel.expandtab.*;
import com.warmtel.expandtab.KeyValueBean;

import java.util.ArrayList;

/**
 * Created by scxh on 2016/8/24.
 */
public class Info {
    ArrayList<AreaKey> areaKey;
    ArrayList<KeyValueBean> distanceKey;
    ArrayList<KeyValueBean> industryKey;
    ArrayList<KeyValueBean> sortKey;

    public ArrayList<AreaKey> getAreaKey() {
        return areaKey;
    }

    public void setAreaKey(ArrayList<AreaKey> areaKey) {
        this.areaKey = areaKey;
    }



    public ArrayList<KeyValueBean> getDistanceKey() {
        return distanceKey;
    }

    public void setDistanceKey(ArrayList<KeyValueBean> distanceKey) {
        this.distanceKey = distanceKey;
    }

    public ArrayList<KeyValueBean> getIndustryKey() {
        return industryKey;
    }

    public void setIndustryKey(ArrayList<KeyValueBean> industryKey) {
        this.industryKey = industryKey;
    }

    public ArrayList<KeyValueBean> getSortKey() {
        return sortKey;
    }

    public void setSortKey(ArrayList<KeyValueBean> sortKey) {
        this.sortKey = sortKey;
    }
}
