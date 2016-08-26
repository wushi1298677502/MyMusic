package com.example.scxh.giveme;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.scxh.giveme.MyFragment.CheapFragment;
import com.example.scxh.giveme.MyFragment.DiscountFragment;
import com.example.scxh.giveme.MyFragment.MoreFragment;
import com.example.scxh.giveme.MyFragment.NearFragment;
import com.example.scxh.giveme.MyFragment.PocketFragment;

import java.util.ArrayList;
import java.util.List;

public class PagerActivity extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener{

    private LinearLayout mOneTabLayout;
    private LinearLayout mTwoTabLayout;
    private LinearLayout mThreeTabLayout;
    private LinearLayout mFinance;
    private LinearLayout mMore;
    private TextView mNear,mCheap,mDiscount,mPocket,more;
    private ImageView mImgNear,mImgCheap,mImgDiscount,mImgPocket,mImgMore;
    List<Fragment> list = new ArrayList<>();
    ViewPager mViewPager;
    Fragment[] str = new Fragment[]{
            NearFragment.newInstance(),
            CheapFragment.newInstance(),
            DiscountFragment.newInstance(),
            PocketFragment.newInstance(),
            MoreFragment.newInstance(),

    };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_layout);

        mViewPager = (ViewPager) findViewById(R.id.tabfragment_framlayout);
        mViewPager.setOffscreenPageLimit(2);
        mOneTabLayout = (LinearLayout)findViewById(R.id.tabfragment_one);
        mTwoTabLayout = (LinearLayout)findViewById(R.id.tabfragment_two);
        mThreeTabLayout = (LinearLayout)findViewById(R.id.tabfragment_three);
        mFinance = (LinearLayout)findViewById(R.id.tabfragment_finance);
        mMore = (LinearLayout)findViewById(R.id.tabfragment_more);
        mNear = (TextView) findViewById(R.id.tabfragment_one_tex);
        mImgNear = (ImageView) findViewById(R.id.tabfragment_one_img);
        mCheap = (TextView) findViewById(R.id.tabfragment_two_tex);
        mImgCheap = (ImageView) findViewById(R.id.tabfragment_two_img);
        mDiscount = (TextView) findViewById(R.id.tabfragment_three_tex);
        mPocket = (TextView) findViewById(R.id.tabfragment_finance_tex);
        mImgPocket = (ImageView) findViewById(R.id.tabfragment_finance_img);
        more = (TextView) findViewById(R.id.tabfragment_more_tex);
        mImgMore = (ImageView) findViewById(R.id.tabfragment_more_img);

        mOneTabLayout.setOnClickListener(this);
        mTwoTabLayout.setOnClickListener(this);
        mThreeTabLayout.setOnClickListener(this);
        mFinance.setOnClickListener(this);
        mMore.setOnClickListener(this);


        MyTextAdapter myAdapter = new MyTextAdapter(getSupportFragmentManager());// TODO: 2016/8/3 实例化的参数是getSupportFragmentManager
        myAdapter.SetDatalist(str);
        mViewPager.setAdapter(myAdapter);
        mViewPager.setCurrentItem(2);
        mViewPager.addOnPageChangeListener(this);// TODO: 2016/8/3  FragmentPagerAdapter的监听项
        mViewPager.setPageTransformer(true,new ZoomOutPageTransformer());
    }

    public void onClick(View view) {
        clearBackground();
        switch (view.getId()){
            case R.id.tabfragment_one:
                mViewPager.setCurrentItem(0);
                mNear.setTextColor(getResources().getColor(R.color.black_deep));;//把当前的选中项颜色加深
                mImgNear.setImageResource(R.drawable.tab_near_s);
                break;
            case R.id.tabfragment_two:
                mViewPager.setCurrentItem(1);
                mCheap.setTextColor(getResources().getColor(R.color.black_deep));
                mImgCheap.setImageResource(R.drawable.tab_cheap_s);
                break;
            case R.id.tabfragment_three:
                mViewPager.setCurrentItem(2);
                mDiscount.setTextColor(getResources().getColor(R.color.black_deep));
                break;
            case R.id.tabfragment_finance:
                mViewPager.setCurrentItem(3);
                mPocket.setTextColor(getResources().getColor(R.color.black_deep));
                mImgPocket.setImageResource(R.drawable.tab_pocket_s);
                break;
            case R.id.tabfragment_more:
                mViewPager.setCurrentItem(4);
                more.setTextColor(getResources().getColor(R.color.black_deep));
                mImgMore.setImageResource(R.drawable.tab_more_s);
                break;
        }
    }

    // TODO: 2016/8/3 每次切换的时候重置背景
    public void clearBackground(){
        Logs.e("clearBackground");
        mNear.setTextColor(getResources().getColor(R.color.black_gray));
        mCheap.setTextColor(getResources().getColor(R.color.black_gray));
        mDiscount.setTextColor(getResources().getColor(R.color.black_gray));
        mPocket.setTextColor(getResources().getColor(R.color.black_gray));
        more.setTextColor(getResources().getColor(R.color.black_gray));
        mImgNear.setImageResource(R.drawable.tab_near);
        mImgCheap.setImageResource(R.drawable.tab_cheap);
        mImgPocket.setImageResource(R.drawable.tab_pocket);
        mImgMore.setImageResource(R.drawable.tab_more);
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    public void onPageSelected(int position) {
        clearBackground();
        switch (position){
            case 0:
                mNear.setTextColor(getResources().getColor(R.color.black_deep));//把当前的选中项颜色加深
                mImgNear.setImageResource(R.drawable.tab_near_s);
                break;
            case 1:
                mCheap.setTextColor(getResources().getColor(R.color.black_deep));
                mImgCheap.setImageResource(R.drawable.tab_cheap_s);
                break;
            case 2:
                mDiscount.setTextColor(getResources().getColor(R.color.black_deep));
                break;
            case 3:
                mPocket.setTextColor(getResources().getColor(R.color.black_deep));
                mImgPocket.setImageResource(R.drawable.tab_pocket_s);
                break;
            case 4:
                more.setTextColor(getResources().getColor(R.color.black_deep));
                mImgMore.setImageResource(R.drawable.tab_more_s);
                break;
        }
    }

    public void onPageScrollStateChanged(int state) {

    }

    class MyTextAdapter extends FragmentStatePagerAdapter {
        public   Fragment[] str = new Fragment[]{} ;
        public MyTextAdapter(FragmentManager fm) {
            super(fm);
        }
        public void SetDatalist(Fragment[] str){
            this.str = str;
            notifyDataSetChanged();
        }
        @Override
        public Fragment getItem(int position) {
            Logs.e("position>>>>"+position);
            return str[position];
        }

        @Override
        public int getCount() {
            return str.length;
        }
    }

}
