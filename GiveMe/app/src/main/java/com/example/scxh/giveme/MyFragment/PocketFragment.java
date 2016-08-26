package com.example.scxh.giveme.MyFragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.myrequest.UTF8StringRequest;
import com.example.scxh.giveme.AlreadyDefinitUtils.ConnectionUtil;
import com.example.scxh.giveme.Logs;
import com.example.scxh.giveme.MainContent.Content;
import com.example.scxh.giveme.MainContent.Detail;
import com.example.scxh.giveme.MainContent.MainKey;
import com.example.scxh.giveme.R;
import com.example.scxh.giveme.VolleyUtils.RequestManager;
import com.example.scxh.giveme.ZoomOutPageTransformer;
import com.google.gson.Gson;
import com.scxh.slider.library.SliderLayout;
import com.scxh.slider.library.SliderTypes.BaseSliderView;
import com.scxh.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PocketFragment extends Fragment implements View.OnClickListener,ViewPager.OnPageChangeListener{

    TextView unuse,used,outdate;
    List<Fragment> list = new ArrayList<>();
    ViewPager mViewPager;
    String[] str = new String[]{
            "http://www.warmtel.com:8080/maininit",
            "http://www.warmtel.com:8080/maininit",
            "http://www.warmtel.com:8080/maininit",
    };

    public PocketFragment() {
        // Required empty public constructor
    }
    public static Fragment newInstance(){
        PocketFragment cheapFragment = new PocketFragment();
        return cheapFragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pocket_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = (ViewPager)getView(). findViewById(R.id.pocket_viewpager);

        unuse = (TextView) getView(). findViewById(R.id.pocket_notuse);
        used = (TextView) getView(). findViewById(R.id.pocket_used);
        outdate = (TextView) getView(). findViewById(R.id.pocket_outdate);


        unuse.setOnClickListener(this);
        used.setOnClickListener(this);
        outdate.setOnClickListener(this);

        unuse.setBackgroundResource(R.color.gray);
        MyTextAdapter myAdapter = new MyTextAdapter(getChildFragmentManager());// TODO: 2016/8/3 实例化的参数是getSupportFragmentManager
        myAdapter.SetDatalist(str);
        mViewPager.setAdapter(myAdapter);
        mViewPager.addOnPageChangeListener(this);// TODO: 2016/8/3  FragmentPagerAdapter的监听项
        mViewPager.setPageTransformer(true,new ZoomOutPageTransformer());
    }

    @Override
    public void onClick(View view) {
        clearBackground();
        switch (view.getId()){
            case R.id.pocket_notuse:
                mViewPager.setCurrentItem(0);
                unuse.setBackgroundResource(R.color.gray);
                break;
            case R.id.pocket_used:
                mViewPager.setCurrentItem(1);
                used.setBackgroundResource(R.color.gray);
                break;
            case R.id.pocket_outdate:
                mViewPager.setCurrentItem(2);
                outdate.setBackgroundResource(R.color.gray);
                break;

        }
    }

    // TODO: 2016/8/3 每次切换的时候重置背景
    public void clearBackground(){
        Logs.e("clearBackground");
        unuse.setBackgroundResource(R.color.gray1);
        used.setBackgroundResource(R.color.gray1);
        outdate.setBackgroundResource(R.color.gray1);
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    public void onPageSelected(int position) {
        clearBackground();
        switch (position){
            case 0:
                unuse.setBackgroundResource(R.color.gray);//把当前的选中项颜色加深
                break;
            case 1:
                used.setBackgroundResource(R.color.gray);

                break;
            case 2:
                outdate.setBackgroundResource(R.color.gray);
                break;
        }
    }

    public void onPageScrollStateChanged(int state) {

    }
    class MyTextAdapter extends FragmentStatePagerAdapter {
        public String[] urls = new String[]{};
        public MyTextAdapter(FragmentManager fm) {
            super(fm);
        }
        public void SetDatalist(String[] urls){
            this.urls = urls;
            notifyDataSetChanged();
        }
        @Override
        public Fragment getItem(int position) {
            Logs.e("position>>>>"+position);
            return PocketDetailFragment.newInstance(str[position],position);
        }

        @Override
        public int getCount() {
            return str.length;
        }
    }
}
