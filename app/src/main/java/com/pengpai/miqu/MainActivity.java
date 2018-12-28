package com.pengpai.miqu;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.pengpai.miqu.base.DeviceInfo;

import java.io.IOException;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = getApplicationContext();

        printLog(context);
    }

    private void printLog(Context context) {
        Log.i("Mr.XieXie","品牌："+DeviceInfo.getDeviceBrand());
        Log.i("Mr.XieXie","型号："+DeviceInfo.getDeviceModel());
        Log.i("Mr.XieXie","Advertising ID："+DeviceInfo.getDeviceAdvertisingID(context));
        Log.i("Mr.XieXie","系统版本："+DeviceInfo.getDeviceAndroidVision());
        Log.i("Mr.XieXie", "Root："+String.valueOf(DeviceInfo.isDeviceRooted()));
        Log.i("Mr.XieXie", "安装支付宝："+String.valueOf(DeviceInfo.isInstalledAlipay(context)));
        Log.i("Mr.XieXie", "安装微信："+String.valueOf(DeviceInfo.isInstalledWechat(context)));
        Log.i("Mr.XieXie", "安装QQ："+String.valueOf(DeviceInfo.isInstalledQQ(context)));
        Log.i("Mr.XieXie","IMEI："+DeviceInfo.getDeviceIMEI(context));
        Log.i("Mr.XieXie","SERIAL："+DeviceInfo.getDeviceSerial());
    }
}
