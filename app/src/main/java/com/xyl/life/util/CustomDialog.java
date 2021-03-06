package com.xyl.life.util;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.xyl.life.R;
import com.xyl.life.entity.weather.City;
import com.xyl.life.entity.weather.County;
import com.xyl.life.entity.weather.Province;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 工具类：自定义对话框--用于选择城市，展示对应城市的天气信息
 */

public class CustomDialog extends Dialog {

    private static final int LEVEL_PROVINCE = 0;
    private static final int LEVEL_CITY = 1;
    private static final int LEVEL_COUNTY = 2;
    private Button backButton;

    private TextView titleText;

    private ListView listView;

    private ArrayAdapter<String> adapter;

    private List<String> dataList = new ArrayList<>();

    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;

    private Province selectedProvince;//选中的省份
    private City selectedCity;//选中的城市
    private int currentLevel;//当前选中的级别

    private ProgressDialog dialog;
    private Context mContext;

    private OnSelectedListener mListener;

    public CustomDialog(@NonNull Context context) {
        this(context, 0);
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        init();
    }

    protected CustomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_choose_area);

        titleText = (TextView) findViewById(R.id.title_text);
        backButton = (Button) findViewById(R.id.back_button);
        listView = (ListView) findViewById(R.id.list_view);

        //设置对话框大小
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics();
        //获取屏幕宽度、高度
        lp.width = (int) (d.widthPixels * 0.7);
        lp.height = (int) (d.heightPixels * 0.7);
        dialogWindow.setAttributes(lp);


        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(position);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = cityList.get(position);
                    queryCounties();
                } else if (currentLevel == LEVEL_COUNTY) {
                    String weatherId = countyList.get(position).getWeatherId();
                    mListener.onSelected(weatherId);
                    CustomDialog.this.dismiss();
                }
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLevel == LEVEL_COUNTY) {
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    queryProvinces();
                }
            }
        });
        queryProvinces();
    }

    public void setOn(OnSelectedListener listener) {
        this.mListener = listener;
    }

    /**
     * 查询全国的省，优先从数据库查询，如果没有查询到再从服务器上查询
     */
    private void queryProvinces() {
        titleText.setText("中国");
        backButton.setVisibility(View.GONE);//查询省份时，返回按钮不显示
        provinceList = DataSupport.findAll(Province.class);//先从数据库中查询
        if (provinceList.size() > 0) {
            dataList.clear();//刷新列表，清除列表中的内容
            for (Province province : provinceList) {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();//通知用户界面发生了改变
            listView.setSelection(0);//从下标为1的地方显示列表内容
            currentLevel = LEVEL_PROVINCE;//设置时当前选择的等级为省份
        } else {
            String address = "http://guolin.tech/api/china";//本地数据库没有查询到，现在去服务器上查询"province"，获取全国省份信息
            queryFromServer(address, "province");
        }

    }


    /**
     * 查询全国的城市，优先从数据库查询，如果没有查询到再从服务器上查询
     */
    private void queryCities() {
        titleText.setText(selectedProvince.getProvinceName());//将标题设置为选择的省份名称
        backButton.setVisibility(View.VISIBLE);//将返回按钮设置为显示状态
        cityList = DataSupport.where("provinceId=?", String.valueOf(selectedProvince.getId())).find(City.class);//优先从数据库中查询城市数据
        if (cityList.size() > 0) {//如果数据库中存在城市数据
            dataList.clear();//刷新列表，清除列表中的数据
            for (City city : cityList) {
                dataList.add(city.getCityName());//将查询到的城市名称显示到listView中
            }
            adapter.notifyDataSetChanged();//通知用户界面发生了变化
            listView.setSelection(0);//从listView中列表下标为0（第一个）开始显示
            currentLevel = LEVEL_CITY;//设置当前 选择的等级为 城市
        } else {
            int provinceCode = selectedProvince.getProvinceCode();//获取选择省份的代号，赋值给provinceCode
            String address = "http://guolin.tech/api/china/" + provinceCode;//通过服务器的api查询provinceCode，查询到城市数据
            queryFromServer(address, "city");
        }

    }


    /**
     * 查询全国的县，优先从数据库查询，如果没有查询到再从服务器上查询
     */
    private void queryCounties() {
        titleText.setText(selectedCity.getCityName());//设置标题显示为查询到城市名称
        backButton.setVisibility(View.VISIBLE);//设置返回按钮为显示状态
        countyList = DataSupport.where("cityId=?", String.valueOf(selectedCity.getId())).find(County.class);
        if (countyList.size() > 0) {
            dataList.clear();
            for (County county : countyList) {
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCityCode();
            String address = "http://guolin.tech/api/china/" + provinceCode + "/" + cityCode;
            queryFromServer(address, "county");
        }

    }

    /**
     * 根据传入的服务器地址和类型从服务器上查询省市县数据
     *
     * @param address
     * @param type
     */
    private void queryFromServer(String address, final String type) {
        //  showProgressDialog();//显示进度对话框
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if ("province".equals(type)) {
                    result = Utility.handleProvinceResponse(responseText);
                } else if ("city".equals(type)) {
                    result = Utility.handleCityResponse(responseText, selectedProvince.getId());
                } else if ("county".equals(type)) {
                    result = Utility.handleCountyResponse(responseText, selectedCity.getId());
                }

                if (result) {

                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvinces();
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("county".equals(type)) {
                                queryCounties();
                            }
                        }
                    });
                }
            }
        });

    }


    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(mContext);
            dialog.setMessage("正在加载中...");
            dialog.setCanceledOnTouchOutside(false);//点击对话框外部区域无法让对话框消失
        }
        dialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();//隐藏对话款，dismiss方法会释放对话框所占的资源，而hide方法不会

        }

    }

    /**
     * 添加回调监听，监听最后选择的 县 结果
     */
    public interface OnSelectedListener {
        void onSelected(String id);
    }
}
