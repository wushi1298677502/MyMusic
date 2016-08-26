package com.example.scxh.giveme;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.ScrollView;


/**
 * 其实实现原理很简单ScrollView有一个方法requestDisallowInterceptTouchEvent(boolean);
 * 这个方法是设置是否交出ontouch权限的
 * ，如果让外层的scrollview.requestDisallowInterceptTouchEvent(false)
 那么外层的onTouch权限会失去，这样里面的listview就能 拿到ontouch权限了，listView也就能滚了。
 *当手指触到listview的时候，让外面的scrollview交出权限，当手指松开后，外面的scrollview重新获得权限
 *
 */
public class InnerListView extends ListView {
    ScrollView parentScrollView;
    public InnerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setParentScrollView(ScrollView parentScrollView) {
        this.parentScrollView = parentScrollView;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Logs.e("InnerListView>>>dispatchTouchEvent");
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                setParentScrollale(false);//当手指触到listview的时候，让父ScrollView交出ontouch权限，也就是让父scrollview
                  // 停住不能滚动
                Logs.e("InnerListView>>>ACTION_DOWN");

            case MotionEvent.ACTION_MOVE:
                Logs.d("onInterceptTouchEvent move");
                break;
            case MotionEvent.ACTION_UP:
                Logs.d("onInterceptTouchEvent up");
            case MotionEvent.ACTION_CANCEL:
                Logs.d("onInterceptTouchEvent cancel");
                setParentScrollale(true);// 当手指松开时，让父ScrollView重新拿到onTouch权限
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Logs.e("InnerListView>>>onInterceptTouchEvent");

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Logs.e("InnerListView>>>onTouchEvent");
        return super.onTouchEvent(ev);
    }
    public void setParentScrollale(boolean flag){
        parentScrollView.requestDisallowInterceptTouchEvent(!flag);// 这里的parentScrollView就是listview外面的那个scrollview
    }
}
