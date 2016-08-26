package com.example.scxh.giveme.MyFragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.myrequest.GsonRequest;

import com.android.volley.myrequest.UTF8StringRequest;
import com.example.scxh.giveme.AlreadyDefinitUtils.ConnectionUtil;
import com.example.scxh.giveme.Logs;
import com.example.scxh.giveme.MainContent.Content;
import com.example.scxh.giveme.MainContent.Detail;
import com.example.scxh.giveme.MainContent.MainKey;
import com.example.scxh.giveme.R;
import com.example.scxh.giveme.VolleyUtils.RequestManager;
import com.google.gson.Gson;
import com.scxh.slider.library.SliderLayout;
import com.scxh.slider.library.SliderTypes.BaseSliderView;
import com.scxh.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscountFragment extends Fragment {
    ConnectionUtil connectionUtil;
    SliderLayout mSliderLayout;
    ArrayList<Detail> list = new ArrayList<>();
    String url = "http://www.warmtel.com:8080/maininit";
    public DiscountFragment() {
        // Required empty public constructor
    }
    public static Fragment newInstance(){
        DiscountFragment cheapFragment = new DiscountFragment();
        return cheapFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discount_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        connectionUtil = new ConnectionUtil(getContext());
        mSliderLayout = (SliderLayout) getView().findViewById(R.id.discount_sliderlayout);
        getSliderData();
    }
    public void getSliderData(){
        Logs.e("getSliderData");
        UTF8StringRequest gsonRequest = new UTF8StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Content content = gson.fromJson(response,Content.class);
                        MainKey mainKey = content.getInfo();
                        list = mainKey.getMainKey();
                        SetViewPage(list);
                    }
                    },
                new Response.ErrorListener(){
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "请求出错!", Toast.LENGTH_SHORT).show();
                    }
                });
        gsonRequest.setShouldCache(true);
        Logs.e("gsonRequest>>"+gsonRequest);
        RequestManager.addRequest(gsonRequest,this);//需要实现一个类继承Application，在AndroiMainefest里面实例化它
    }
    public void SetViewPage( ArrayList<Detail> list ){
        ArrayList<Detail> lists = list;
        int length = lists.size();
        for(int g=0;g<length;g++){
            Detail detail = lists.get(g);
            String imgUrl = detail.getBigpicUrl();
            String tex = detail.getDescription();
            Logs.e("tex"+tex);
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView
                    .description(tex)
                    .image(imgUrl)
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            mSliderLayout.addSlider(textSliderView);
        }
    }
}
