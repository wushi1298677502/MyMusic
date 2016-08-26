package com.example.scxh.giveme.Modul;

/**

 * {
 "weatherinfo": {
 "city": "北京",
 "cityid": "101010100",
 "temp": "21",
 "WD": "东南风",
 "WS": "2  级",
 "SD": "78%",
 "WSE": "2",
 "time": "21:10",
 "isRadar": "1",
 "Radar": "JC_RADAR_AZ9010_JB"
 }
 }
 */

public class Student {
    int id;
    String name;
    String coupon;
    String location;
    String distance;
    String picUrl;
    String couponType;
    String cardType;
    String groupType;
    String gpsX;
    String gpsY;
    int goodSayNum;
    int midSayNum;
    int badSayNum;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getGpsX() {
        return gpsX;
    }

    public void setGpsX(String gpsX) {
        this.gpsX = gpsX;
    }

    public String getGpsY() {
        return gpsY;
    }

    public void setGpsY(String gpsY) {
        this.gpsY = gpsY;
    }

    public int getGoodSayNum() {
        return goodSayNum;
    }

    public void setGoodSayNum(int goodSayNum) {
        this.goodSayNum = goodSayNum;
    }

    public int getMidSayNum() {
        return midSayNum;
    }

    public void setMidSayNum(int midSayNum) {
        this.midSayNum = midSayNum;
    }

    public int getBasSayNum() {
        return badSayNum;
    }

    public void setBasSayNum(int badSayNum) {
        this.badSayNum = badSayNum;
    }

}
