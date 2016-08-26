package com.example.scxh.giveme.MyFragment;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.myrequest.UTF8StringRequest;
import com.bumptech.glide.Glide;
import com.example.scxh.giveme.Logs;
import com.example.scxh.giveme.Modul.Gsons;
import com.example.scxh.giveme.Modul.Info;
import com.example.scxh.giveme.NearPop.AreaKey;
import com.example.scxh.giveme.NearPop.Content;

import com.example.scxh.giveme.R;
import com.example.scxh.giveme.Modul.Student;
import com.example.scxh.giveme.VolleyUtils.RequestManager;
import com.google.gson.Gson;
import com.warmtel.expandtab.ExpandPopTabView;
import com.warmtel.expandtab.KeyValueBean;
import com.warmtel.expandtab.PopOneListView;
import com.warmtel.expandtab.PopTwoListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class NearFragment extends Fragment {

    ListView listView;
    List<Hotel> hotellist = new ArrayList<>();
    JsonAdapter jsonAdapter;

    ExpandPopTabView expandTabView;
    private List<KeyValueBean> mPriceLists; //价格
    private List<KeyValueBean> mSortLists;  //排序
    private List<KeyValueBean> mFavorLists; //优惠
    private List<KeyValueBean> mParentLists;
    private List<ArrayList<KeyValueBean>> mChildrenListLists;
    public NearFragment() {
        // Required empty public constructor
    }
    public static Fragment newInstance(){
        NearFragment cheapFragment = new NearFragment();
        return cheapFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_near_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView)getView().findViewById(R.id.json_listview);
        expandTabView = (ExpandPopTabView) getView().findViewById(R.id.near_location);
        doDownLoadTxt();
        jsonAdapter = new JsonAdapter(getContext());
        Logs.e("mDataLists>>>>>>>>>>>>>>"+hotellist);
        listView.setAdapter(jsonAdapter);

        mPriceLists = new ArrayList<>();
        mSortLists = new ArrayList<>();
        mFavorLists = new ArrayList<>();
        mParentLists = new ArrayList<>();//父区域
        mChildrenListLists = new ArrayList<>();//子区域


        doDownLoadPop();
    }

   public void doDownLoadPop(){
       String txtUrl = "http://www.warmtel.com:8080/configs";
       UTF8StringRequest request = new UTF8StringRequest(Request.Method.GET, txtUrl,
               new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       setGson(response);
                   }
               },
               new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {

                   }
               });
       request.setShouldCache(false);
       RequestManager.addRequest(request,this);
   }

    public void setGson(String string){
            Gson gson = new Gson();
            Content content = gson.fromJson(string,Content.class);
            com.example.scxh.giveme.NearPop.Info info = content.getInfo();
            mPriceLists = info.getDistanceKey();
            mSortLists = info.getSortKey();
            mFavorLists = info.getIndustryKey();

            for(AreaKey areaKey:info.getAreaKey()){
                KeyValueBean keyValueBean = new KeyValueBean();
                keyValueBean.setKey(areaKey.getKey());
                keyValueBean.setValue(areaKey.getValue());
                mParentLists.add(keyValueBean);
                mChildrenListLists.add(areaKey.getCircles());
            }
                addItem(expandTabView, mPriceLists, "", "价格");
                addItem(expandTabView, mFavorLists, "默认", "排序");
                addItem(expandTabView, mSortLists, "优惠最多", "优惠");
                addItem(expandTabView, mParentLists, mChildrenListLists, "锦江区", "合江亭", "区域");
    }


    public void addItem(ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        PopOneListView popOneListView = new PopOneListView(getContext());
        popOneListView.setDefaultSelectByValue(defaultSelect);
        //popViewOne.setDefaultSelectByKey(defaultSelect);
        popOneListView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                //
            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, popOneListView);
    }

    public void addItem(ExpandPopTabView expandTabView, List<KeyValueBean> parentLists,
                        List<ArrayList<KeyValueBean>> childrenListLists, String defaultParentSelect, String defaultChildSelect, String defaultShowText) {
        PopTwoListView popTwoListView = new PopTwoListView(getContext());
        popTwoListView.setDefaultSelectByValue(defaultParentSelect, defaultChildSelect);
        //distanceView.setDefaultSelectByKey(defaultParent, defaultChild);
        popTwoListView.setCallBackAndData(expandTabView, parentLists, childrenListLists, new PopTwoListView.OnSelectListener() {

            public void getValue(String showText, String parentKey, String childrenKey) {
                //弹出框选项点击选中回调方法
            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, popTwoListView);
    }
    /**
     * 异步获取文本信息
     */
    public void doDownLoadTxt() {
//        String txtUrl = "http://192.168.5.11:8080/json/students";
        String txtUrl = "http://www.warmtel.com:8080/around";
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String txturl = params[0];
                return doTextByNet(txturl);
            }

            @Override
            protected void onPostExecute(String s) {
                Logs.e(s);
                doMerchantJsonParase(s);//此方法过后hotellist数据源就加载完成了
//                jsonAdapter.notifyDataSetChanged();//数据更新后通知改变，保证进入页面有内容
                jsonAdapter.SetDatalist(hotellist);
                Logs.e("onPostExecute jsonAdapter.SetDatalist(hotellist)>>>"+hotellist);
            }
        }.execute(txtUrl);

    }

    /**
     * 解析商家json字符串
     * @param jsonStr
     */
    public void doMerchantJsonParase(String jsonStr){


        /**
         * 使用Gson工具类，首先引入工具包 file--Project Structure--app--Dependencies--"+"--library dependency(加载在线)--搜索谷歌com.google.decode.Gson就可以了
         *                                                                                  File dependency(加载离线工具包)
         *   按照数据格式{"name":12,"object":{"total":28}}
         *   由里到外依次建立相应的类，如果是数组类型的建立的类 ArrayList<Student> merchantKey 作为成员，而Student类就负责具体的数组所包含的项
         */
        Gson gson = new Gson();

        Gsons gsons = gson.fromJson(jsonStr,Gsons.class);
        Logs.e("111"+jsonStr);
        Info info = gsons.getInfo();
        ArrayList<Student> list = info.getMerchantKey();
        int length = list.size();
        Logs.e("length>>>>"+length);
        for(Student student:list){
            Hotel hotel = new Hotel();//必须写在里面，这样每遍历一次，实例化一次，hotellist加载才会得到不同的view，定义在外面，加载的都相同，并且是最后一项
            Logs.e(student.getId()+" "+student.getName()+" "+student.getCoupon()+" "+student.getCardType()+" "+student.getLocation()+" "+student.getDistance()
                    +" "+student.getPicUrl()+" "+student.getCouponType()+" "+student.getCardType()+" "+student.getGroupType()+" "
                    +student.getGpsX()+" "+student.getGpsY()+" "+student.getGoodSayNum()+" "+student.getMidSayNum()+" "+student.getBasSayNum());
            hotel.setName(student.getName());
            hotel.setCoupon(student.getCoupon());
            hotel.setLocation(student.getLocation());
            hotel.setDistance(student.getDistance());
            hotel.setPicUrl(student.getPicUrl());
            hotel.setCouponType(student.getCouponType());
            hotel.setCardType(student.getCardType());
            hotel.setGroupType(student.getGroupType());
            hotel.setGpsX(student.getGpsX());
            hotel.setGpsY(student.getGpsY());
            hotel.setGoodSayNum(student.getGoodSayNum());
            hotel.setMidSayNum(student.getMidSayNum());
            hotel.setBasSayNum(student.getBasSayNum());
            hotellist.add(hotel);
        }
    }

    /**
     * 从网络获取文本
     *
     * @param
     * @return
     */
    public String doTextByNet(String httpUrl) {
        String message = "";
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(httpUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");  //请求方法 GET POST
//            urlConnection.setConnectTimeout(10000); //连接超时时间
//            urlConnection.setReadTimeout(15000);    //读数据超时
            urlConnection.connect();

            int code = urlConnection.getResponseCode();  //状态行:状态码 200 OK
            Logs.e("code :" + code);
            if (code == HttpURLConnection.HTTP_OK) {
                InputStream is = urlConnection.getInputStream();
                message = readInput(is);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            urlConnection.disconnect();
        }
        return message;
    }

    public String readInput(InputStream is) {
        Reader reader = new InputStreamReader(is);  //字节转字符流
        BufferedReader br = new BufferedReader(reader); //字符缓存流

        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                reader.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }




    class JsonAdapter extends BaseAdapter {
        List<Hotel> list = new ArrayList<>();
        LayoutInflater layoutInflater;
        public JsonAdapter(Context context){
            this.layoutInflater = LayoutInflater.from(context);
        }
        public void SetDatalist(List<Hotel> list){
            this.list = list;
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
            HoldView holdView;
            if(view == null){
                view = layoutInflater.inflate(R.layout.activity_jsonlistview_layout,null);
                ImageView imageView = (ImageView) view.findViewById(R.id.json_listview_img);
                ImageView card = (ImageView) view.findViewById(R.id.json_card);
                ImageView group = (ImageView) view.findViewById(R.id.json_ticket);
                ImageView tuan = (ImageView) view.findViewById(R.id.json_tuan);
                TextView hotel = (TextView) view.findViewById(R.id.json_hotel);
                TextView youhui = (TextView) view.findViewById(R.id.json_youhui);
                TextView location = (TextView) view.findViewById(R.id.json_location);
                TextView distance = (TextView) view.findViewById(R.id.json_distance);

                holdView = new HoldView();
                holdView.pic = imageView;
                holdView.card = card;
                holdView.group = group;
                holdView.tuan = tuan;
                holdView.hotel = hotel;
                holdView.youhui = youhui;
                holdView.location = location;
                holdView.distance = distance;
                view.setTag(holdView);
            }
            holdView = (HoldView) view.getTag();
            Hotel hotel = (Hotel) getItem(i);
            String name= hotel.getName();
            String coupon= hotel.getCoupon();
            String location= hotel.getLocation();
            String distance= hotel.getDistance();
            String picUrl= hotel.getPicUrl();
            holdView.hotel.setText(name);
            holdView.youhui.setText(coupon);
            holdView.location.setText(location);
            holdView.distance.setText(distance);
            String couponType= hotel.getCouponType();
            String cardType= hotel.getCardType();
            String groupType= hotel.getGroupType();
            holdView.card.setImageBitmap(cardType.equals("YES")? BitmapFactory.decodeResource(getResources(),R.drawable.near_card):null);//根据返回的数据“YES”或者“NO”，使用三目运算；
            holdView.group.setImageBitmap(groupType.equals("YES")? BitmapFactory.decodeResource(getResources(),R.drawable.near_group):null);
            holdView.tuan.setImageBitmap(couponType.equals("YES")? BitmapFactory.decodeResource(getResources(),R.drawable.near_ticket):null);
            Glide.with(getContext()).load(picUrl).into(holdView.pic);//对于直接从网络取数据的图片使用第三方包，还可以缓存

            return view;
        }
    }
    public class HoldView{
        ImageView pic;
        ImageView card;
        ImageView group;
        ImageView tuan;
        TextView hotel;
        TextView youhui;
        TextView location;
        TextView distance;
    }
    class Hotel {
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


}

