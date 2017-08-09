package com.yunhuishou.administrator.pack;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;


/**
 * Created by Administrator on 2017/8/7/007.
 */

public class AfterLogin extends Activity implements QRCodeView.Delegate
{
    //private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
    private static boolean IS_FLASHLIGHT_ON = false;
    private static final String TAG = AfterLogin.class.getSimpleName();

    private QRCodeView mQRCodeView;
    private ImageButton mSetupButton, mFlashLightButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainwin);

        mQRCodeView = (ZBarView)findViewById(R.id.zbarview);
        mQRCodeView.setDelegate(this);

        mFlashLightButton = (ImageButton) findViewById(R.id.FlashLightButton);
        mSetupButton = (ImageButton) findViewById(R.id.SettingsButton);

        mFlashLightButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(IS_FLASHLIGHT_ON)
                {
                    mQRCodeView.closeFlashlight();
                    mFlashLightButton.setImageResource(R.mipmap.flashlight);
                    IS_FLASHLIGHT_ON = false;
                }else
                {
                    mQRCodeView.openFlashlight();
                    mFlashLightButton.setImageResource(R.mipmap.flashlight_fill);
                    IS_FLASHLIGHT_ON = true;
                }
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        mQRCodeView.startCamera();

        mQRCodeView.showScanRect();
    }

    @Override
    protected void onStop()
    {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result)
    {
        Log.i(TAG, "结果：" + result);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        vibrate();
        mQRCodeView.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError()
    {
        Log.e(TAG, "打开相机错误");
        Toast.makeText(this, "未能打开相机，请检查权限", Toast.LENGTH_SHORT).show();
    }


}
