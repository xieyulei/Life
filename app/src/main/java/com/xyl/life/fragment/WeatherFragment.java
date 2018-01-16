package com.xyl.life.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xyl.life.R;
import com.xyl.life.entity.weather.Forecast;
import com.xyl.life.entity.weather.Weather;
import com.xyl.life.service.AutoUpdateService;
import com.xyl.life.util.HttpUtil;
import com.xyl.life.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*******************************
 * Created by liuqiang          *
 *******************************
 * data: 2018/1/16               *
 *******************************/

public class WeatherFragment extends BaseFragment {
    private ScrollView weatherLayout;//天气页面布局
    private TextView titleCity;//页面标题-城市
    private TextView titleUpdateTime;//更新时间
    private TextView degreeText;//温度
    private TextView weatherInfoText;//天气情况
    private LinearLayout forecastLayout;//未来几天天气信息
    private TextView aqiText;//aqi指数
    private TextView pm25Text;//pm2.5指数
    private TextView comfortText;//舒适度
    private TextView carWashText;//汽车指数
    private TextView sportText;//运动建议
    private ImageView bingPicImg;//必应每日一图

    public SwipeRefreshLayout swipeRefresh; //外部嵌套布局，具有下拉刷新的功能

    public DrawerLayout drawerLayout;//抽屉栏
    private Button navButton;//用于切换抽屉栏的状态切换(打开和隐藏)

    private static String defaultId = "CN101010400";
    private String weatherId;

    @Override
    protected void initView(LayoutInflater inflater, View view, Bundle savedInstanceState) {


        weatherLayout = (ScrollView) view.findViewById(R.id.weather_layout);
        titleCity = (TextView) view.findViewById(R.id.title_city);
        titleUpdateTime = (TextView) view.findViewById(R.id.title_update_time);

        degreeText = (TextView) view.findViewById(R.id.degree_text);
        weatherInfoText = (TextView) view.findViewById(R.id.weather_info_text);
        forecastLayout = (LinearLayout) view.findViewById(R.id.forecast_layout);

        aqiText = (TextView) view.findViewById(R.id.aqi_text);
        pm25Text = (TextView) view.findViewById(R.id.pm25_text);

        comfortText = (TextView) view.findViewById(R.id.comfort_text);
        carWashText = (TextView) view.findViewById(R.id.car_wash_text);
        sportText = (TextView) view.findViewById(R.id.sport_text);

        bingPicImg = (ImageView) view.findViewById(R.id.bing_pic_img);

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);

        //drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navButton = (Button) view.findViewById(R.id.nav_button);

        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog dialog = new CustomDialog(getContext());
                dialog.setOn(new CustomDialog.OnSelectedListener() {
                    @Override
                    public void onSelected(String id) {

                        requestWeather(id);
                    }
                });

                dialog.show();
            }
        });

        swipeRefresh.setRefreshing(false);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String weatherString = prefs.getString("weather", null);


        if (weatherString != null) {
            //有缓存时，直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            if (weather != null)
                weatherId=weather.basic.weatherId;
                showWeatherInfo(weather);

        } else {
            weatherId=defaultId;
            //无缓存时，去服务器查询天气
            weatherLayout.setVisibility(View.INVISIBLE);//布局设置为不可见
            requestWeather(weatherId);
        }



        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(weatherId);
            }
        });


        String bingPic = prefs.getString("bing_pic", null);
        if (bingPic != null) {
            Glide.with(this).load(bingPic).into(bingPicImg);
        } else {
            loadBingPic();
        }

        loadBingPic();
    }

    private void loadBingPic() {

        final String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(getContext()).load(bingPic).into(bingPicImg);
                    }
                });

            }
        });

    }


    /**
     * 处理并展示Weather实体类中的数据
     *
     * @param weather
     */
    private void showWeatherInfo(Weather weather) {
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.more.info;

        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);

        forecastLayout.removeAllViews();

        for (Forecast forecast : weather.forecastList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);

            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max);
            minText.setText(forecast.temperature.min);

            forecastLayout.addView(view);
        }

        if (weather.aqi != null) {
            aqiText.setText(weather.aqi.city.aqi);
            pm25Text.setText(weather.aqi.city.pm25);
        }


        String comfort = "舒适度" + weather.suggestion.comfort.info;
        String carWash = "汽车指数" + weather.suggestion.carWash.info;
        String sport = "运动建议" + weather.suggestion.sport.info;

        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);

    }


    /**
     * 根据天气id请求城市天气信息
     *
     * @param weatherId
     */
    public void requestWeather(final String weatherId) {
        // String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=bc0418b57b2d4918819d3974ac1285d9";这个key时创建者-郭霖的key，当前已失效
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=92b41385da0648fd977aad16da93a4f9";//和风天气api--key
        //和风天气api接口：https://console.heweather.com/my/service
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);//刷新后隐藏
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            showWeatherInfo(weather);

                            //启动后台更新天气和背景图片的服务
                            Intent intent = new Intent(getActivity(), AutoUpdateService.class);
                            getActivity().startService(intent);
                        } else {
                            Toast.makeText(getContext(), "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);//刷新后隐藏
                    }
                });
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_weather;
    }
}
