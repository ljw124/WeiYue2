package com.dcdz.weiyue.net;

import com.dcdz.weiyue.been.FreshNewsBean;
import com.dcdz.weiyue.been.KaiYanBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by LJW on 2018/10/25.
 */
public interface KaiYanApiService {

    /**
     * 获取首页banner数据
     */
    @GET("v2/feed?")
    Observable<KaiYanBean> getBannerData(@Query("nu") int num);

    /**
     * 获取首页数据
     */
    @GET
    Observable<KaiYanBean> getHomeData(@Url String url);
}
