package com.kaolaadsdkdemo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kaolafm.ad.engine.api.entity.Device;
import com.kaolafm.ad.sdk.core.personal.KlAdSdkLoader;


/******************************************
 * 类名称：AdSdkDemo
 * 类描述：
 *
 * @version: 2.3.1
 * @author: caopeng
 * @time: 2016/10/18 16:54
 ******************************************/
public class AdSdkDemo extends Activity {
    private static final String VERSION = "1.1.2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_demo);

        KlAdSdkLoader klAdSdkLoader = KlAdSdkLoader.getInstance();
        klAdSdkLoader.setAppId("91032253a3fc4d7883cc11ec49c832f9");
        klAdSdkLoader.init(this, Device.DeviceType.APP);

        Button btn_version = (Button) this.findViewById(R.id.btn_version);
        btn_version.setText("VERSION  " + VERSION);
        Button btnOrigin = (Button) this.findViewById(R.id.btnOrigin);
        btnOrigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdSdkDemo.this, Ad2ImageActivity.class);
                intent.putExtra(MainActivity.PREPARE_TAG, "");
                startActivity(intent);
            }
        });

        Button btnOriginPrepare = (Button) this.findViewById(R.id.btnOriginPrepare);
        btnOriginPrepare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdSdkDemo.this, Ad2ImageActivity.class);
                intent.putExtra(MainActivity.PREPARE_TAG, "1");
                startActivity(intent);
            }
        });

        Button btnVideoFeed = (Button) this.findViewById(R.id.btnVideoFeed);
        btnVideoFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdSdkDemo.this, MainActivity.class);
                intent.putExtra(MainActivity.PREPARE_TAG, "1");
                startActivity(intent);
            }
        });

        Button btnVideoFeedUnPrepare = (Button) this.findViewById(R.id.btnVideoFeedUnPrepare);
        btnVideoFeedUnPrepare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdSdkDemo.this, MainActivity.class);
                intent.putExtra(MainActivity.PREPARE_TAG, "");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KlAdSdkLoader.getInstance().destroy();
    }
}