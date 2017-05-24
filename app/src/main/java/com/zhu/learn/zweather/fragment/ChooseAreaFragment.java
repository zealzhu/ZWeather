package com.zhu.learn.zweather.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhu.learn.zweather.activity.MainActivity;
import com.zhu.learn.zweather.R;
import com.zhu.learn.zweather.activity.WeatherActivity;
import com.zhu.learn.zweather.db.City;
import com.zhu.learn.zweather.db.Country;
import com.zhu.learn.zweather.db.Province;
import com.zhu.learn.zweather.util.HttpUtil;
import com.zhu.learn.zweather.util.Utility;
import com.zhu.learn.zweather.util.ZWeatherApplication;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by zhu on 2017/3/13.
 */

public class ChooseAreaFragment extends Fragment {
    /**
     * 等级
     */
    public  static final int LEVEL_PROVINCE = 0;
    public  static final int LEVEL_CITY = 1;
    public  static final int LEVEL_COUNTRY = 2;
    /**
     * 控件
     */
    private ProgressDialog dialog;
    private TextView titleText;
    private Button backButton;
    private ListView listView;
    /**
     * ListView数据适配器
     */
    private ArrayAdapter<String> adapter;

    /**
     * 显示的数据
     */
    private List<String> dataList = new ArrayList<>();

    /**
     * 省、市和县数据列表
     */
    private List<Province> provinceList;
    private List<City> cityList;
    private List<Country> countryList;

    /**
     * 当前选中的省和市,通过这个查询下级数据
     */
    private Province selectedProvince;
    private City selectedCity;

    /**
     * 当前选中的级别
     */
    private int currentLevel;

    /**
     * 显示对话框
     */
    public void showDialog() {
        if(dialog == null) {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("加载中");
            dialog.setCanceledOnTouchOutside(false);
        }
        dialog.show();
    }

    /**
     * 关闭对话框
     */
    public void closeDialog() {
        if(dialog != null)
            dialog.dismiss();
    }

    /**
     * 加载布局时调用
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);
        //获取各个控件
        titleText = (TextView)view.findViewById(R.id.title_text);
        backButton = (Button)view.findViewById(R.id.back_button);
        listView = (ListView)view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(ZWeatherApplication.getContext(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //绑定列表的选项单击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentLevel == LEVEL_PROVINCE) {
                    //获取选中省份
                    selectedProvince = provinceList.get(position);
                    queryCities();
                } else if(currentLevel == LEVEL_CITY) {
                    selectedCity = cityList.get(position);
                    queryCountries();
                } else if(currentLevel == LEVEL_COUNTRY) {
                    //选中地区
                    String weatherCode = countryList.get(position).getWeatherCode().substring(2);
                    if(getActivity() instanceof MainActivity) {
                        //如果该碎片是MainActivity的
                        Intent intent = new Intent(ZWeatherApplication.getContext(), WeatherActivity.class);
                        intent.putExtra("WEATHER_CODE", weatherCode);
                        startActivity(intent);
                        //结束当前活动
                        getActivity().finish();
                    } else if(getActivity() instanceof WeatherActivity) {
                        WeatherActivity activity = (WeatherActivity)getActivity();
                        //关闭抽屉
                        activity.drawerLayout.closeDrawers();
                        //显示刷新图标
                        activity.swipeRefreshLayout.setRefreshing(true);
                        //请求
                        activity.requestWeather(weatherCode);
                    }
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLevel == LEVEL_CITY) {
                    queryProvinces();
                } else if(currentLevel == LEVEL_COUNTRY) {
                    queryCities();
                }
            }
        });
        //加载省份数据
        queryProvinces();
    }

    /**
     * 从网上获取数据
     * @param address 请求的地址
     * @param type 请求的类型
     */
    private void queryFromServer(String address, final String type) {
        //显示加载对话框
        showDialog();
        HttpUtil.sendOKHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeDialog();
                        Toast.makeText(ZWeatherApplication.getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //响应正文
                String responseText = response.body().string();
                //是否查到数据
                boolean result = false;
                //从网上查询数据保存到数据库中
                if("Province".equals(type)) {
                    result = Utility.handleProvinceResponse(responseText);
                }else if("City".equals(type)) {
                    result = Utility.handleCityResponse(responseText, selectedProvince.getId());
                }else if("Country".equals(type)) {
                    result = Utility.handleCountryResponse(responseText, selectedCity.getId());
                }
                if(result) {
                    //如果有从网上查到数据，则在从数据库中加载数据，返回到主线程去更新
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeDialog();
                            if("Province".equals(type)) {
                                queryProvinces();
                            }else if("City".equals(type)) {
                                queryCities();
                            }else if("Country".equals(type)) {
                                queryCountries();
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 查询省份数据
     */
    private void queryProvinces(){
        //设置标题
        titleText.setText("中国");
        //隐藏后退按钮
        backButton.setVisibility(View.GONE);
        //先从数据库中读取数据
        provinceList = DataSupport.findAll(Province.class);
        if(provinceList.size() > 0) {
            //数据库中有数据，则将省名添加到要显示的数据列表中
            dataList.clear();
            for(Province province : provinceList) {
                dataList.add(province.getProvinceName());
            }
            //刷新列表
            adapter.notifyDataSetChanged();
            //从第一条显示
            listView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        } else {
            //数据库中没有数据
            String address = "http://guolin.tech/api/china";
            queryFromServer(address, "Province");
        }
    }
    /**
     * 查询市数据
     */
    private void queryCities(){
        //设置标题
        titleText.setText(selectedProvince.getProvinceName());
        //显示后退按钮
        backButton.setVisibility(View.VISIBLE);
        //先从数据库中读取数据
        cityList = DataSupport.where("provinceId = ?", String.valueOf(selectedProvince.getId())).find(City.class);
        if(cityList.size() > 0) {
            //数据库中有数据，则将市名添加到要显示的数据列表中
            dataList.clear();
            for(City city : cityList) {
                dataList.add(city.getCityName());
            }
            //刷新列表
            adapter.notifyDataSetChanged();
            //从第一条显示
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        } else {
            //数据库中没有数据
            String address = "http://guolin.tech/api/china/" + selectedProvince.getProvinceCode();
            queryFromServer(address, "City");
        }
    }
    /**
     * 查询县级数据
     */
    private void queryCountries(){
        //设置标题
        titleText.setText(selectedCity.getCityName());
        //显示后退按钮
        backButton.setVisibility(View.VISIBLE);
        //先从数据库中读取数据
        countryList = DataSupport.where("cityId = ?", String.valueOf(selectedCity.getId())).find(Country.class);
        if(countryList.size() > 0) {
            //数据库中有数据，则将省名添加到要显示的数据列表中
            dataList.clear();
            for(Country country : countryList) {
                dataList.add(country.getCountryName());
            }
            //刷新列表
            adapter.notifyDataSetChanged();
            //从第一条显示
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTRY;
        } else {
            //数据库中没有数据
            String address = "http://guolin.tech/api/china/" + selectedProvince.getProvinceCode() + "/" + selectedCity.getCityCode();
            queryFromServer(address, "Country");
        }
    }
}
