package com.example.scxh.giveme.MyFragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.scxh.giveme.AlreadyDefinitUtils.ConnectionUtil;
import com.example.scxh.giveme.CheapContent.AdvertisingKey;
import com.example.scxh.giveme.CheapContent.Content;
import com.example.scxh.giveme.CheapContent.Hotkey;
import com.example.scxh.giveme.CheapContent.Industry;
import com.example.scxh.giveme.CheapContent.Info;
import com.example.scxh.giveme.CheapContent.Infos;
import com.example.scxh.giveme.InnerListView;
import com.example.scxh.giveme.ListViewForScorllView;
import com.example.scxh.giveme.Logs;
import com.example.scxh.giveme.R;
import com.google.gson.Gson;
import com.scxh.slider.library.SliderLayout;
import com.scxh.slider.library.SliderTypes.BaseSliderView;
import com.scxh.slider.library.SliderTypes.TextSliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheapFragment extends Fragment {

    SliderLayout mViewpager;
    GridView mGridView;
    ScrollView mScrollView;
    public  ArrayList<Pict> list = new ArrayList<>();
    public  ArrayList<Infos> listhold = new ArrayList<>();
    private ConnectionUtil connectionUtil;
    GridViewAdapter adapter;
    MyPagerAdapter myPagerAdapter;
    ListViewForScorllView mLsitview;
    String[] str = new String[]{
            "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383291_6518.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383291_8239.jpg"
    };
    public CheapFragment() {
        // Required empty public constructor
    }
    public static Fragment newInstance(){
        CheapFragment cheapFragment = new CheapFragment();
        return cheapFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cheap_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        connectionUtil = new ConnectionUtil(getContext());
        mViewpager = (SliderLayout) getView().findViewById(R.id.cheap_viewpager);
        mGridView = (GridView) getView().findViewById(R.id.cheap_gridview);
        mScrollView = (ScrollView) getView().findViewById(R.id.cheap_scrollview);
        mLsitview = (ListViewForScorllView) getView().findViewById(R.id.cheap_listview);
        getDataLists();
        mViewpager.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

        adapter = new GridViewAdapter(getContext());
        mGridView.setAdapter(adapter);

        myPagerAdapter = new MyPagerAdapter(getContext());
        getListViewData();
        mLsitview.setAdapter(myPagerAdapter);
    }
    public void getListViewData(){
        String baseUrl = "http://www.warmtel.com:8080/industry";
        connectionUtil.asyncConnect(baseUrl, ConnectionUtil.Mothod.GET, new ConnectionUtil.HttpConnectionInterface() {

            public void excute(String cont) {
                if (cont == null) {
                    Toast.makeText(getContext(), "请求出错!", Toast.LENGTH_SHORT).show();
                    return;
                }
                setListData(cont);
            }

        });
    }

    public void setListData(String content){
        Gson gson = new Gson();
        Industry conten = gson.fromJson(content,Industry.class);
        ArrayList<Infos> info = conten.getInfo();
        int len = info.size();
        for (int g = 0; g < len; g++) {
            Infos Item = info.get(g);
            listhold.add(Item);
        }
        myPagerAdapter.addDataList(listhold);
    }
    public void getDataLists() {
        String baseUrl = "http://www.warmtel.com:8080/keyConfig";
        connectionUtil.asyncConnect(baseUrl, ConnectionUtil.Mothod.GET, new ConnectionUtil.HttpConnectionInterface() {

            public void excute(String cont) {
                if (cont == null) {
                    Toast.makeText(getContext(), "请求出错!", Toast.LENGTH_SHORT).show();
                    return;
                }
                setViewpage(cont);
            }

        });
    }
    public void setViewpage(String content) {
        Gson gson = new Gson();
        Content conten = gson.fromJson(content, Content.class);
        Info info = conten.getInfo();
        ArrayList<AdvertisingKey> advertisingKey = info.getAdvertisingKey();
        ArrayList<Hotkey> hotkey = info.getHotkey();
        int len = advertisingKey.size();
            for (int g = 0; g < len; g++) {
                AdvertisingKey Item = advertisingKey.get(g);
//                String imgsrc = Item.getPicUrl();
                String imgsrc = str[g];
                String tex = Item.getDescription();
                Logs.e("imgsrc>>>>" + imgsrc);
                Logs.e("tex>>>>" + tex);
                TextSliderView textSliderView = new TextSliderView(getContext());
                textSliderView
                        .description(tex)
                        .image(imgsrc)
                        .setScaleType(BaseSliderView.ScaleType.Fit);
                mViewpager.addSlider(textSliderView);
            }
        int length = hotkey.size();
        for (int g = 0; g < length; g++) {
            Hotkey Item = hotkey.get(g);
            String tex = Item.getValue();
            Logs.e("CheapFragment..tex>>>>" + tex);
            Pict pict = new Pict();
            pict.setTxt(tex);
            list.add(pict);
        }
        Logs.e("list"+list.size());
        adapter.setList(list);
    }
    public class GridViewAdapter extends BaseAdapter {
        List<Pict> list = new ArrayList<>();
        LayoutInflater layoutInflater;

        public GridViewAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
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
        public void setList(ArrayList list) {
            this.list = list;
            notifyDataSetChanged();
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = layoutInflater.inflate(android.R.layout.simple_list_item_1, null);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                view.setTag(textView);
            }
                TextView textView = (TextView) view.getTag();
                Pict pict = (Pict) getItem(i);
                textView.setText(pict.getTxt());


            return view;
        }
    }

    public class Pict {
        String txt;
        public Pict(String title){
            this.txt = title;
        }

        public Pict() {

        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }
    }

    class MyPagerAdapter extends BaseAdapter {
        List<Infos> list = new ArrayList<>();
        LayoutInflater layoutInflater;

        public MyPagerAdapter(Context context) {
            this.layoutInflater = LayoutInflater.from(context);
        }

        public void addDataList(List<Infos> list) {
            this.list.addAll(list);
            notifyDataSetChanged();
        }

        public int getCount() {
            Logs.e("list>>>" + list);
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

        public View getView(int i, View view, ViewGroup viewGroup) {

            HoldView holdView;
            if (view == null) {
                view = layoutInflater.inflate(R.layout.activity_cheap_listview_layout, null);
                ImageView imageView = (ImageView) view.findViewById(R.id.find_business);
                TextView title = (TextView) view.findViewById(R.id.cheap_tex);

                holdView = new HoldView();
                holdView.pic = imageView;
                holdView.title = title;
                view.setTag(holdView);
            }
            holdView = (HoldView) view.getTag();
            Infos n = (Infos) getItem(i);
            Logs.e("normal>>>i" + i);
            String title = n.getIndustry();
            String img = n.getIconUrl();
            holdView.title.setText(title);
            Glide.with(getContext()).load(img).into(holdView.pic);//对于直接从网络取数据的图片使用第三方包，还可以缓存

            return view;
        }
    }

    public class HoldView{
        ImageView pic;
        TextView title;
    }

}
