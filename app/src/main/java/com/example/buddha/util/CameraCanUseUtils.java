package com.example.buddha.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.ActivityCompat;


public class CameraCanUseUtils {

    /**
     * 测试当前摄像头能否被使用
     *
     * @return
     */
    public static boolean isCameraCanUse() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open(0);
            mCamera.setDisplayOrientation(90);
        } catch (Exception e) {
            canUse = false;
        }
        if (canUse) {
            mCamera.release();
            mCamera = null;
        }
        //Timber.v("isCameraCanuse="+canUse);
        return canUse;
    }

    /**
     * 相机权限设置
     * 跳转至设置页面
     */
    public static void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
    }

    /**
     * 申请权限
     */
    public static void requestCameraPermission(Context context) {
        // 相机权限未被授予，需要申请！
        // 第一次申请，就直接申请
        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                0);
    }


}
