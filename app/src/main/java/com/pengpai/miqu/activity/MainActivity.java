package com.pengpai.miqu.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.cmic.sso.sdk.auth.AuthnHelper;
import com.cmic.sso.sdk.auth.TokenListener;
import com.pengpai.miqu.R;
import com.pengpai.miqu.base.DeviceInfo;
import com.pengpai.miqu.base.utils.Constant;
import com.pengpai.miqu.base.utils.ProgressDialogUtil;
import com.pengpai.miqu.base.utils.SharePersist;
import com.pengpai.miqu.validate.Request;
import com.pengpai.miqu.validate.RequestCallback;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity{
    protected String TAG = "Verification";

    private AuthnHelper mAuthnHelper;
    private ProgressDialogUtil mPDialogUtil;
    private TokenListener mListener;
    private String mAccessToken;
    private Context mContext;
    private Timer timer;
    private String mToken;
    private static final int FORCE_TIMEOUT_TIME = 10000;

    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE_PRE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mAuthnHelper = AuthnHelper.getInstance(this);

        requestPermission();
        getToken();
        printLog(mContext);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSIONS_REQUEST_READ_PHONE_STATE_PRE:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // permission was granted
                    getToken();
                }else {
                    // permission denied
                    //dialog here
                    finish();
                }
                break;
        }
    }

    private void getToken() {
        if(null==mPDialogUtil){
            mPDialogUtil=new ProgressDialogUtil(this);
        }
        timer = new Timer();
        startOverdueTimer();
        mPDialogUtil.showDialog("请求数据中...");

        mAuthnHelper.umcLoginByType(Constant.APP_ID, Constant.APP_KEY, 12000, new TokenListener() {
            @Override
            public void onGetTokenComplete(JSONObject jsonObject) {
                //回调接收数据
                Log.e(TAG,"收到回调");
                Log.e(TAG,jsonObject.toString());
                mPDialogUtil.hideDialog();

                timer.cancel();

                if(jsonObject!=null){
                    if(jsonObject.has("token")){
                        Log.e(TAG,jsonObject.optString("token"));
                        //TODO 无法传给全局变量
                        mToken = jsonObject.optString("token");
                    }
                }

            }
        });

        phoneValidate(Constant.APP_ID,Constant.APP_KEY,mToken,mListener);
    }

    private void phoneValidate() {
        if(null==mPDialogUtil){
            mPDialogUtil=new ProgressDialogUtil(this);
        }
        mPDialogUtil.showDialog("请求数据中...");
        SharePersist.getInstance().putLong(mContext, "phonetimes", System.currentTimeMillis());
        mListener = new TokenListener() {
            @Override
            public void onGetTokenComplete(JSONObject jObj) {
                try {
                    timer.cancel();
                    if (jObj != null) {
                        String resCode=new JSONObject(jObj.optString("header")).optString("resultCode");
                        mAccessToken=new JSONObject(jObj.optString("body")).optString("accessToken");
//                        Log.i(TAG,"resCode："+ resCode);

                        /*if(resCode.equals("001")&&TextUtils.isEmpty(mAccessToken)){
                            mResultDialog.setNote("未在开发者社区勾选，无法调用短验辅助功能!");
                            mResultDialog.setOkBtnManager(new ResultDialog.OkBtnManager() {
                                @Override
                                public void okBtnBack() {
                                    mResultDialog.cancelNote();
                                }
                            });
                        }
                        mResultString = jObj.toString();
                        mHandler.sendEmptyMessage(RESULT);*/

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        phoneValidate(Constant.APP_ID, Constant.APP_KEY, mToken, mListener);
    }

    private void phoneValidate(final String appID, final String appKey, final String token, final TokenListener listener) {
        Bundle values = new Bundle();
        values.putString("appkey", appKey);
        values.putString("appid", appID);
        values.putString("token", token);

        Request.getInstance(mContext).phoneValidate(values, new RequestCallback() {
            @Override
            public void onRequestComplete(String resultCode, String resultDes, JSONObject jsonobj) {
                Log.e(TAG,jsonobj.toString());
                mPDialogUtil.hideDialog();
                listener.onGetTokenComplete(jsonobj);
            }
        });
    }

    private void startOverdueTimer() {
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                Log.e(TAG, "超时了");
                mAuthnHelper.cancel();
                if(null!=mPDialogUtil){
                    mPDialogUtil.hideDialog();
                }
            }

        }, FORCE_TIMEOUT_TIME);
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE_PRE);
            } else {
                //TODO
            }
        }
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
