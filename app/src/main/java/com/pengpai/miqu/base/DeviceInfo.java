package com.pengpai.miqu.base;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.pengpai.miqu.base.utils.RootUtil;

import java.io.IOException;
import java.util.List;

public class DeviceInfo {

    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    public static String getDeviceModel() {
        return Build.MODEL;
    }

    public static String getDeviceAdvertisingID(final Context context) {

        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                AdvertisingIdClient.Info idInfo = null;
                try {
                    idInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
                    Log.e("AdvID",idInfo.toString());
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String advertId = null;
                try{
                    advertId = idInfo.getId();
                    Log.e("AdvID",advertId);
                }catch (NullPointerException e){
                    e.printStackTrace();
                }

                return advertId;
            }

            @Override
            protected void onPostExecute(String advertId) {
                //Toast.makeText(context, advertId, Toast.LENGTH_SHORT).show();
            }

        };
        task.execute();

        return null;
    }


    public static String getDeviceAndroidVision() {
        return Build.VERSION.RELEASE;
    }

    public static boolean isDeviceRooted() {
        return RootUtil.isDeviceRooted();
    }

    public static boolean isInstalledAlipay(Context context){
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);

        if (packageInfos !=null){
            for (int i=0;i<packageInfos.size();i++){
                String packageName = packageInfos.get(i).packageName;
                if ("com.eg.android.AlipayGphone".equals(packageName)){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isInstalledWechat(Context context){
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);

        if (packageInfos !=null){
            for (int i=0;i<packageInfos.size();i++){
                String packageName = packageInfos.get(i).packageName;
                if ("com.tencent.mm".equals(packageName)){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isInstalledQQ(Context context){
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);

        if (packageInfos !=null){
            for (int i=0;i<packageInfos.size();i++){
                String packageName = packageInfos.get(i).packageName;
                if ("com.tencent.mobileqq".equals(packageName) || "com.tencent.qqlite".equals(packageName) || "com.tencent.mobileqqi".equals(packageName)){
                    return true;
                }
            }
        }
        return false;
    }

    public static String getDeviceIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        return telephonyManager.getDeviceId();
    }

    public static String getDeviceSerial(){
        return Build.SERIAL;
    }
}
