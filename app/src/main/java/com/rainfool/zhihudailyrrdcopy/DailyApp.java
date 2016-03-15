package com.rainfool.zhihudailyrrdcopy;

import android.app.Application;
import android.preference.PreferenceManager;

import com.rainfool.zhihudailyrrdcopy.util.AppContextUtil;
import com.rainfool.zhihudailyrrdcopy.util.SpUtil;

/**
 * Created by rainfool on 2016/3/14.
 */
public class DailyApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        AppContextUtil.init(this);//application也是context的一种
        SpUtil.init(PreferenceManager.getDefaultSharedPreferences(this));
    }
}
