package com.kaolaadsdkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaolafm.ad.engine.api.entity.AdRequest;
import com.kaolafm.ad.engine.api.entity.AdResponse;
import com.kaolafm.ad.sdk.core.adnewrequest.option.ImageOption;
import com.kaolafm.ad.sdk.core.listener.AdListener;
import com.kaolafm.ad.sdk.core.personal.AdImageManager;
import com.kaolafm.ad.sdk.core.personal.Constants;
import com.kaolafm.ad.sdk.core.util.StringUtil;

/**
 * 类说明: 图片广告
 * Created by zhangchao on 2017/3/7.
 */

public class Ad2ImageActivity extends Activity {
    ImageView iv;
    TextView tv_result;
    public static final String PREPARE_TAG = "PREPARE_TAG";
    public String isPrepare = Constants.BLANK_STR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isPrepare = getIntent().getStringExtra(PREPARE_TAG);
        setContentView(R.layout.activity_ad_main);
        iv = (ImageView) findViewById(R.id.iv);
        tv_result = (TextView) findViewById(R.id.tv_result);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectNetwork()
                .detectDiskReads()
                .detectDiskWrites()
                .penaltyLog()
                .build());

        startRequest();
    }

    public void startRequest() {
        ImageOption adOption = new ImageOption();//图片可配置参数
        adOption.setDefaultPic(R.drawable.ic_launcher);//默认显示的图片(只在广告为预加载模式下起作用)
        adOption.setTimeOut(10000); //超时时间(只在广告为非预加载模式下起作用, 默认 为3s)
        adOption.setRetryTime(5000); //请求失败后,重试时间(只在广告为预加载模式下起作用, 默认 为3s)
        if (!StringUtil.isEmpty(isPrepare)) {
            adOption.setPreload(true);  //是否是预加载
        } else {
            adOption.setPreload(false);  //是否是预加载
        }
        adOption.setAdPicWidth(400); //图片宽度(默认情况下为 原始图片大小)
        adOption.setAdPicHeight(400);//图片高度(默认情况下为 原始图片大小)
        adOption.setDisplayType(ImageOption.DISPLAY_ROUND); //图片显示的类型(DISPLAY_NORMAL 普通, DISPLAY_ROUND 圆角, DISPLAY_CIRCLE 圆)
        adOption.setCornerRadius(20); //图片显示为圆角的情况下, 圆角弧度
        AdImageManager.getInstance().loadAd(iv, 132, new AdListener() {
            @Override
            public void onDataLoadingStarted() {
                tv_result.setText("onDataLoadingStarted");
            }

            @Override
            public void onDataLoadAdFailed(final int code) {
                tv_result.setText("onDataLoadAdFailed" + code);
            }

            @Override
            public void onAdViewShow(int showType) {
                tv_result.setText("onAdViewShow");
            }

            @Override
            public void onAdViewClick(String url) {
                tv_result.setText("onAdViewClick" + url);
            }

            @Override
            public void onGetAdData(AdRequest mAdRequest, AdResponse adResponse) {
                tv_result.setText("onGetAdData");
            }
        }, adOption);
    }
}
