package com.dcdz.weiyue.net;

import com.dcdz.weiyue.been.FreshNewsArticleBean;
import com.dcdz.weiyue.been.FreshNewsBean;
import com.dcdz.weiyue.been.JdDetailBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * desc: 煎蛋api接口，用于分发网络调用的方法
 * retrofit2
 * 注意，这里是interface不是class，所以我们是无法直接调用该方法，我们需要用Retrofit创建一个代理对象
 */
public interface JanDanApiService {

    @GET
    Observable<FreshNewsBean> getFreshNews(@Url String url, @Query("oxwlxojflwblxbsapi") String oxwlxojflwblxbsapi,
                                           @Query("include") String include,
                                           @Query("page") int page,
                                           @Query("custom_fields") String custom_fields,
                                           @Query("dev") String dev
    );


    @GET
    Observable<JdDetailBean> getDetailData(@Url String url, @Query("oxwlxojflwblxbsapi") String oxwlxojflwblxbsapi,
                                           @Query("page") int page
    );

    @GET
    Observable<FreshNewsArticleBean> getFreshNewsArticle(@Url String url, @Query("oxwlxojflwblxbsapi") String oxwlxojflwblxbsapi,
                                                         @Query("include") String include,
                                                         @Query("id") int id
    );

}

