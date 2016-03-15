package com.rainfool.zhihudailyrrdcopy.util;

import android.content.Context;

/**
 * 用来获取context的单例类，使用前需要init()
 */
public class AppContextUtil {

    private static Context mContext;

    //这里的单例模式之所以不将context传入，是因为如果context不一样，那就会直接创建不同的单例，
    //所以使用了一个init方法来传入context值，这样，context可能会变，但这个类只会有一个
    private AppContextUtil(){}
    public static Context getInstance() {
        if (mContext == null) {
            throw new NullPointerException("this context is null,please call init() method first!");
        }
        return mContext;
    }

    public static void init(Context context) {
        mContext = context;
    }


}
