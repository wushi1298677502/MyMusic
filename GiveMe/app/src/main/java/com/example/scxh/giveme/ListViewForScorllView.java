package com.example.scxh.giveme;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 虽然listview的高度出来了，但是存在两个问题：
 * a.还是只支持ScrollView滚动，listView不会滚动，因为listView的高度已经达到最大，它不需要滚动。
 * b.listview的优点就是能够复用listItem
 * ，如果把listView撑到最大，等于是没有复用listItem，这跟没用listView一样，省不了ui资源，那我还不如直接用linearlayout呢
 *
 */
public class ListViewForScorllView extends ListView {
    public ListViewForScorllView(Context context) {
        super(context);
    }

    public ListViewForScorllView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewForScorllView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }

}
