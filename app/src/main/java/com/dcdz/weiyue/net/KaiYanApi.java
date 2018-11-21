package com.dcdz.weiyue.net;

import com.dcdz.weiyue.been.KaiYanBean;

import io.reactivex.Observable;

/**
 * Created by LJW on 2018/10/25.
 */
public class KaiYanApi {

    private static KaiYanApi sInstance;
    private KaiYanApiService mService;

    public KaiYanApi(KaiYanApiService kaiYanApiService){
        this.mService = kaiYanApiService;
    }

    public static KaiYanApi getInstance(KaiYanApiService service){
        if (sInstance == null)
            sInstance = new KaiYanApi(service);
        return sInstance;
    }

    /**
     * 获取首页banner数据
     */
    public Observable<KaiYanBean> getBannerData(int num){
        return mService.getBannerData(num);
    }

    /**
     * 获取首页数据
     */
    public Observable<KaiYanBean> getHomeData(String url){
        return mService.getHomeData(url);
    }
}
