package com.example.miracle.financehelp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import cn.bmob.v3.Bmob;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.CoreConfig.Builder;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;

import com.example.miracle.financehelp.entity.DaoMaster;
import com.example.miracle.financehelp.entity.DaoMaster.DevOpenHelper;
import com.example.miracle.financehelp.entity.DaoSession;
import com.example.miracle.financehelp.utils.GlideImageLoader;
import com.iflytek.cloud.SpeechUtility;

public class App extends Application {
    private static DaoSession daoSession;
    private static Context mContext;
    public static DaoSession getDaoSession() {
        return daoSession;
    }

    private void initGalleryFinal() {
        ThemeConfig localThemeConfig = new ThemeConfig.Builder().build();
        GlideImageLoader localGlideImageLoader = new GlideImageLoader();
        FunctionConfig localFunctionConfig = new FunctionConfig.Builder().setEnableCamera(true).setEnableEdit(true).setEnableCrop(true).build();
        GalleryFinal.init(new CoreConfig.Builder(this, localGlideImageLoader, localThemeConfig).setFunctionConfig(localFunctionConfig).setNoAnimcation(true).build());
    }

    public void onCreate() {
        super.onCreate();
        mContext =getApplicationContext();
        daoSession = new DaoMaster(new DaoMaster.DevOpenHelper(getApplicationContext(), "test.db", null).getWritableDatabase()).newSession();
        Bmob.initialize(this, "0fb665dc5321bdbba58e1c78ceaaa5f8");
        SpeechUtility s = SpeechUtility.createUtility(getApplicationContext(), "appid=59b392ef");
        Log.d("111", "onCreate: " + String.valueOf(s == null));
        initGalleryFinal();
    }
    public static Context getContext(){
        return mContext;
    }
}
