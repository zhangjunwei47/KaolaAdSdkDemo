package com.kaolaadsdkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kaolafm.ad.engine.api.entity.AdRequest;
import com.kaolafm.ad.engine.api.entity.AdResponse;
import com.kaolafm.ad.sdk.core.adnewrequest.option.AudioOption;
import com.kaolafm.ad.sdk.core.listener.RequestCallback;
import com.kaolafm.ad.sdk.core.mediaplayer.IPlayerStateListener;
import com.kaolafm.ad.sdk.core.personal.AdAudioManager;
import com.kaolafm.ad.sdk.core.personal.Constants;
import com.kaolafm.ad.sdk.core.personal.PlayerManager;
import com.kaolafm.ad.sdk.core.util.LogUtil;
import com.kaolafm.ad.sdk.core.util.StringUtil;

import static com.kaolafm.ad.sdk.core.util.DataUtil.getStrTime;

public class MainActivity extends Activity {
    private TextView tvResult;
    private Button btnPrepare;
    public static final String PREPARE_TAG = "PREPARE_TAG";
    public String isPrepare = Constants.BLANK_STR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectNetwork()
                .detectDiskReads()
                .detectDiskWrites()
                .penaltyLog()
                .build());
        isPrepare = getIntent().getStringExtra(PREPARE_TAG);
        tvResult = (TextView) findViewById(R.id.tvResult);
        btnPrepare = (Button) findViewById(R.id.btnPrepare);
        PlayerManager.getInstance(MainActivity.this).addPlayerStateListener(iPlayerStateListener);
        playRadio();
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playRadio();
//                switchPlayer();
//                AdAudioManager.getInstance().jumpAd(MainActivity.this);
            }
        });
    }

    private boolean isPlaying = true;

    private void switchPlayer() {
        if (isPlaying) {
            AdAudioManager.getInstance().pause(this);
            isPlaying = false;
        } else {
            AdAudioManager.getInstance().play(this);
            isPlaying = true;
        }
    }

    private void playRadio() {
        AudioOption adOption = new AudioOption();
        if (StringUtil.isEmpty(isPrepare)) {
            btnPrepare.setText("非预加载模式");
            adOption.setPreload(false);
        } else {
            btnPrepare.setText("预加载模式");
            adOption.setPreload(true);
        }

        AdAudioManager.getInstance().playRadio(133L, new RequestCallback() {

            @Override
            public void onSuccess(AdRequest mAdRequest, AdResponse adResponse) {
                tvResult.setText("接口请求成功");
            }

            @Override
            public void onError(int code) {
                tvResult.setText("接口请求失败" + code);
            }
        }, adOption);
    }

    IPlayerStateListener iPlayerStateListener = new IPlayerStateListener() {
        @Override
        public void onPlayerPreparing() {
            tvResult.setText("onPlayerPreparing");
        }

        @Override
        public void onPlayerPlaying() {
            LogUtil.i("CAOPENG", "外部：播放开始 onPlayerPlaying");
            tvResult.setText("onPlayerPlaying");
        }

        @Override
        public void onPlayerEnd() {
            tvResult.setText("onPlayerEnd");
        }

        @Override
        public void onPlayerError(int error) {
            LogUtil.i("CAOPENG", "外部：播放错误" + error);
            tvResult.setText("onPlayerError" + error);
        }

        @Override
        public void onProgress(String url, int position, int duration) {
            tvResult.setText("onProgress" + "position" + getStrTime(position) + "duration" + getStrTime(duration));
        }

        @Override
        public void onPlayerPause() {
            LogUtil.i("CAOPENG", "外部：onPlayerPause");
            tvResult.setText("onPlayerPause");
        }

        @Override
        public void onPlayerPlay() {
            LogUtil.i("CAOPENG", "外部：onPlayerPlay");
            tvResult.setText("onPlayerPlay");
        }

    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        PlayerManager.getInstance(MainActivity.this).reset();
        PlayerManager.getInstance(MainActivity.this).removePlayerStateListener(iPlayerStateListener);
        iPlayerStateListener = null;
    }
}
