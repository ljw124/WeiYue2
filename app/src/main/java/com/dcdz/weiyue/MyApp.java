package com.dcdz.weiyue;


import com.dcdz.weiyue.component.ApplicationComponent;
import com.dcdz.weiyue.component.DaggerApplicationComponent;
import com.dcdz.weiyue.module.ApplicationModule;
import com.dcdz.weiyue.module.HttpModule;
import com.dcdz.weiyue.utils.ContextUtils;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;

public class MyApp extends LitePalApplication {

    private static MyApp sMyApp;

    public static int width = 0;

    public static int height = 0;

    //Application全局只有一个，它本身就已经是单例了，无需再用单例模式去为它做多重实例保护了
    private ApplicationComponent mApplicationComponent;

    public static MyApp getInstance() {
        return sMyApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sMyApp = this;
        //初始化滑动返回
        BGASwipeBackManager.getInstance().init(this);
        //初始化ApplicationComponent组件
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .httpModule(new HttpModule())
                .build();
        //初始化数据库LitePal
        LitePal.initialize(this);
        width = ContextUtils.getSreenWidth(MyApp.getContext());
        height = ContextUtils.getSreenHeight(MyApp.getContext());
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

}
