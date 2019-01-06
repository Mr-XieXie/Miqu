package com.pengpai.miqu.base.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogUtil {

    private ProgressDialog mDialog;

    public ProgressDialogUtil(Context context) {
        mDialog=new ProgressDialog(context);
    }

    public void showDialog(String message){

        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setMax(100);
        mDialog.setMessage(message);
        mDialog.show();
    }

    public void hideDialog(){

        mDialog.dismiss();
    }

}
