package com.pengxh.audodingding.utils;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Environment;

import com.pengxh.app.multilib.widget.EasyToast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: Pengxh
 * @email: 290677893@qq.com
 * @description: TODO
 * @date: 2019/12/25 13:13
 */
public class Utils {
    private static final String TAG = "Utils";
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public String sdCardDir = Environment.getExternalStorageDirectory() + "/ScreenShot/";

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context     上下文
     * @param packageName 应用包名
     */
    public static boolean isAppAvailable(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        List<String> packageNames = new ArrayList<>();
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        return packageNames.contains(packageName);
    }

    /**
     * 打开指定包名的apk
     *
     * @param context     上下文
     * @param packageName 应用包名
     */

    public static void openDingding(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageName);
        List<ResolveInfo> apps = packageManager.queryIntentActivities(resolveIntent, 0);
        ResolveInfo resolveInfo = apps.iterator().next();
        if (resolveInfo != null) {
            String className = resolveInfo.activityInfo.name;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName cn = new ComponentName(packageName, className);
            intent.setComponent(cn);
            context.startActivity(intent);
        }
    }

    /**
     * 时间戳转时间
     */
    public static String timestampToDate(long millSeconds) {
        return dateFormat.format(new Date(millSeconds));
    }

    /**
     * 时间转时间戳
     */
    public static long DateToTimestamp(String date) throws ParseException {
        return dateFormat.parse(date).getTime();
    }

    /**
     * 计算时间差
     *
     * @param fixedTime 结束时间
     */
    public static long deltaTime(long fixedTime) {
        long currentTime = (System.currentTimeMillis() / 1000);
        if (fixedTime > currentTime) {
            return (fixedTime - currentTime);
        } else {
            EasyToast.showToast("时间设置异常", EasyToast.WARING);
        }
        return 0L;
    }

//    private void startScreenShot() {
//        String shotCmd = "screencap -p " + sdCardDir + "temp.jpg" + " \n";
//        try {
//            Runtime.getRuntime().exec(shotCmd);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 保存文件
//     */
//
//    private void saveBitmap(Bitmap bitmap) {
//        try {
//            File dirFile = new File(sdCardDir);
//            if (!dirFile.exists()) {              //如果不存在，那就建立这个文件夹
//                dirFile.mkdirs();
//            }
//            File file = new File(sdCardDir, "temp.jpg");
//            FileOutputStream fos = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//            fos.flush();
//            fos.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // 把文件插入到系统图库
//        MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "temp.jpg", null);
//        // 通知图库更新
//        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/sdcard/namecard/")));
//    }
}
