package com.tianyuan.mvplearn.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YouCii on 2017/2/10.
 * android6.0以上对应用的权限控制易导致程序崩溃, 所以进入应用时需要对各个权限进行检测
 */

public class PermissionUtils {

    public final static int INTERNET = 0;
    public final static int LOCATION = 1;
    public final static int WAKE_LOCK = 2;
    public final static int READ_PHONE_STATE = 3;
    public final static int WRITE_EXTERNAL_STORAGE = 4;
    public final static int KILL_BACKGROUND_PROCESSES = 5;
    public final static int CAMERA = 6;
    public final static int VIBRATE = 7;

    public final static int ALL = 100;

    /**
     * 检查权限赋予情况
     *
     * @param permissions {@link #ALL, #INTERNET, #LOCATION ..}
     * @return true if no permission be refused;
     */
    public static boolean examinePermission(final Context context, int... permissions) {
        if (permissions == null || permissions.length == 0) return true;

        List<String> list = new ArrayList<>();
        if (permissions[0] == ALL)
            permissions = new int[]{INTERNET, LOCATION, WAKE_LOCK, READ_PHONE_STATE, WRITE_EXTERNAL_STORAGE, KILL_BACKGROUND_PROCESSES, CAMERA, VIBRATE};

        for (int permission : permissions) {
            switch (permission) {
                case INTERNET:
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
                        list.add("访问网络权限");
                    break;
                case LOCATION:
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                        list.add("定位权限");
                    break;
                case WAKE_LOCK:
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED)
                        list.add("保持屏幕唤醒权限");
                    break;
                case READ_PHONE_STATE:
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
                        list.add("读取手机状态权限");
                    break;
                case WRITE_EXTERNAL_STORAGE:
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                        list.add("存储权限");
                    break;
                case KILL_BACKGROUND_PROCESSES:
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.KILL_BACKGROUND_PROCESSES) != PackageManager.PERMISSION_GRANTED)
                        list.add("杀死后台进程权限");
                    break;
                case CAMERA:
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                        list.add("使用摄像头权限");
                    break;
                case VIBRATE:
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED)
                        list.add("震动权限");
                    break;
            }
        }

        if (list.size() != 0) {
            String[] strings = list.toArray(new String[list.size()]);
            new AlertDialog.Builder(context)
                    .setTitle("请在设置中允许以下权限, 或信任此应用, 否则程序不能运行: ")
                    .setItems(strings, null)
                    .setCancelable(false)
                    .setPositiveButton("现在设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent();
                            if (Build.VERSION.SDK_INT >= 9) {
                                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                intent.setData(Uri.fromParts("package", context.getPackageName(), null));
                            } else if (Build.VERSION.SDK_INT <= 8) {
                                intent.setAction(Intent.ACTION_VIEW);
                                intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                                intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
                            }
                            context.startActivity(intent);
                        }
                    })
                    .setNegativeButton("退出应用", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (context instanceof Activity) ((Activity) context).finish();
                            System.exit(0);
                        }
                    })
                    .show();
            return false;
        }

        return true;
    }

}
