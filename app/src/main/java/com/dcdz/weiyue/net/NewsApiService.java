package com.dcdz.weiyue.net;

import com.dcdz.weiyue.been.NewsArticleBean;
import com.dcdz.weiyue.been.NewsCmppVideoBean;
import com.dcdz.weiyue.been.NewsDetailBean;
import com.dcdz.weiyue.been.NewsImagesBean;
import com.dcdz.weiyue.been.VideoChannelBean;
import com.dcdz.weiyue.been.VideoDetailBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;


/**
 * desc: 新闻api接口,用于分发网络调用的方法
 * 统一接口数据
 * retrofit2
 * 注意，这里是interface不是class，所以我们是无法直接调用该方法，我们需要用Retrofit创建一个代理对象
 */
public interface NewsApiService {

    //原本需要这么多参数，现在都封装到RetrofitConfig中了
//    @GET("ClientNews")
//    Observable<List<NewsDetailBean>> getNewsDetail(@Query("id") String id,
//                                               @Query("action") String action,
//                                               @Query("pullNum") String pullNum,
//                                               @Query("uid") String uid,
//                                               @Query("devid") String devid,
//                                               @Query("proid") String proid,
//                                               @Query("vt") String vt,
//                                               @Query("publishid") String publishid,
//                                               @Query("screen") String screen,
//                                               @Query("os") String os,
//                                               @Query("df") String df,
//                                               @Query("nw") String nw);

    //@Query、@QueryMap：用于Http Get请求传递参数
    @GET("ClientNews")  //完整的地址应该是 baseUrl + 尾址（ClientNews）
    Observable<List<NewsDetailBean>> getNewsDetail(@Query("id") String id,
                                                   @Query("action") String action,
                                                   @Query("pullNum") int pullNum
    );

    @GET("api_vampire_article_detail")
    Observable<NewsArticleBean> getNewsArticleWithSub(@Query("aid") String aid);

    @GET //@Url，它允许我们直接传入一个请求的URL
    Observable<NewsArticleBean> getNewsArticleWithCmpp(@Url String url,
                                                       @Query("aid") String aid);

    @GET
    Observable<NewsImagesBean> getNewsImagesWithCmpp(@Url String url,
                                                     @Query("aid") String aid);

    @GET("NewRelativeVideoList")
    Observable<NewsCmppVideoBean> getNewsVideoWithCmpp(@Url String url,
                                                       @Query("guid") String guid);

    @GET("ifengvideoList")
    Observable<List<VideoChannelBean>> getVideoChannel(@Query("page") int page);

    @GET("ifengvideoList")
    Observable<List<VideoDetailBean>> getVideoDetail(@Query("page") int page,
                                                     @Query("listtype") String listtype,
                                                     @Query("typeid") String typeid);

}
