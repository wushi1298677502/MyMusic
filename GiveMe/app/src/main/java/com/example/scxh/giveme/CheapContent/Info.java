package com.example.scxh.giveme.CheapContent;

import java.util.ArrayList;

/**
 * Created by scxh on 2016/8/22.
 */
public class Info {
    ArrayList<AdvertisingKey> advertisingKey;
    ArrayList<Hotkey> hotkey;

    public ArrayList<AdvertisingKey> getAdvertisingKey() {
        return advertisingKey;
    }

    public void setAdvertisingKey(ArrayList<AdvertisingKey> advertisingKey) {
        this.advertisingKey = advertisingKey;
    }

    public ArrayList<Hotkey> getHotkey() {
        return hotkey;
    }

    public void setHotkey(ArrayList<Hotkey> hotkey) {
        this.hotkey = hotkey;
    }
}
