package com.example.scxh.giveme.MyFragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.myrequest.UTF8StringRequest;
import com.bumptech.glide.Glide;
import com.example.scxh.giveme.CheapContent.Infos;
import com.example.scxh.giveme.Logs;
import com.example.scxh.giveme.MainContent.Content;
import com.example.scxh.giveme.MainContent.Detail;
import com.example.scxh.giveme.MainContent.MainKey;
import com.example.scxh.giveme.R;
import com.example.scxh.giveme.VolleyUtils.RequestManager;
import com.google.gson.Gson;
import com.scxh.slider.library.SliderTypes.BaseSliderView;
import com.scxh.slider.library.SliderTypes.TextSliderView;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PocketDetailFragment extends Fragment {

    ArrayList<Detail> list = new ArrayList<>();

    String url = "http://www.warmtel.com:8080/maininit";
    ListView mListview;
    PocketDetailAdapter adapter;
    int nums;//决定加那个Fragment
    int pageSize = 20; //页大小，显示每页多少条数据
    String news_type_id = "T1348647909107";  //新闻类型标识, 此处表示头条新闻
    public PocketDetailFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(String str,int num){
        PocketDetailFragment mainFragment = new PocketDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("MESSAGE",str);
        bundle.putInt("NUM",num);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        news_type_id = getArguments()==null?null:getArguments().getString("MESSAGE");
        nums = getArguments()==null?-1:getArguments().getInt("NUM");

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pocket_detail_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListview = (ListView) getView().findViewById(R.id.pocket_detail_listview);
        adapter = new PocketDetailAdapter(getContext());
        getSliderData();
        mListview.setAdapter(adapter);
    }
    public void getSliderData(){
        Logs.e("getSliderData");
        UTF8StringRequest gsonRequest = new UTF8StringRequest(Request.Method.GET,news_type_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Content content = gson.fromJson(response,Content.class);
                        MainKey mainKey = content.getInfo();
                        list = mainKey.getMainKey();
                        adapter.addDataList(list);
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

    class PocketDetailAdapter extends BaseAdapter{
        ArrayList<Detail> list = new ArrayList<>();
        LayoutInflater layoutInflater;
        public PocketDetailAdapter(Context context){
            layoutInflater = LayoutInflater.from(context);
        }
        public void addDataList(List<Detail> list) {
            this.list.addAll(list);
            notifyDataSetChanged();
        }
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ImageView imageView;
            if(view == null){
                view = layoutInflater.inflate(R.layout.activity_pocketdetails_layout, null);
                imageView = (ImageView) view.findViewById(R.id.pocket_detail_img);
                view.setTag(imageView);
            }
            imageView = (ImageView) view.getTag();
            Detail n = (Detail) getItem(i);
            Logs.e("normal>>>i" + i);
            String img = n.getPicUrl();
            Glide.with(getContext()).load(img).into(imageView);//对于直接从网络取数据的图片使用第三方包，还可以缓存

            return view;
        }
    }


}
