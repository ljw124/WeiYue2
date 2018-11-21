package com.dcdz.weiyue.module;

import com.dcdz.weiyue.MyApp;
import com.dcdz.weiyue.net.ApiConstants;
import com.dcdz.weiyue.net.BookApi;
import com.dcdz.weiyue.net.BookApiService;
import com.dcdz.weiyue.net.JanDanApi;
import com.dcdz.weiyue.net.JanDanApiService;
import com.dcdz.weiyue.net.KaiYanApi;
import com.dcdz.weiyue.net.KaiYanApiService;
import com.dcdz.weiyue.net.NewsApi;
import com.dcdz.weiyue.net.NewsApiService;
import com.dcdz.weiyue.net.ReaderApi;
import com.dcdz.weiyue.net.ReaderApiService;
import com.dcdz.weiyue.net.RetrofitConfig;
import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * desc: Modules是提供依赖注入时所需对象实例的类
 * ps:依赖提供方
 * Retrofit 负责请求的数据和请求的结果，使用接口的方式呈现，
 * OkHttp 负责请求的过程，
 * RxJava 负责异步，各种线程之间的切换
 */
//@Module：把这个类标识为Dagger module
@Module
public class HttpModule {

    //@Providers：标识函数作为注入提供者，函数名并不重要，它只依赖于所提供的类类型
    @Provides
    OkHttpClient.Builder provideOkHttpClient() {
        // 指定缓存路径,缓存大小100Mb
        Cache cache = new Cache(new File(MyApp.getContext().getCacheDir(), "HttpCache"),
                1024 * 1024 * 100);
        return new OkHttpClient().newBuilder().cache(cache)
                .retryOnConnectionFailure(true) //请求失败后将会重连
                .addInterceptor(RetrofitConfig.sLoggingInterceptor)
                //addInterceptor与addNetworkInterceptor都是增加OkHttp的网络请求拦截器，但是有一定区别的，
                //前者是添加在与服务器连接之前和之后，后者是添加在与服务器建立连接和发起请求的之间
                .addInterceptor(RetrofitConfig.sRewriteCacheControlInterceptor)
                .addNetworkInterceptor(RetrofitConfig.sRewriteCacheControlInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS);
    }

    //向外提供新闻的API
    @Provides
    NewsApi provideNetEaseApis(OkHttpClient.Builder builder) {
        builder.addInterceptor(RetrofitConfig.sQueryParameterInterceptor);

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create()) //Retrofit会使用Gson将ResponseBody转换我们想要的类型
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //支持RxJava
                .client(builder.build());

        return NewsApi.getInstance(retrofitBuilder
                .baseUrl(ApiConstants.sIFengApi) //指定service的base URL（通常情况下就是域名）
                .build().create(NewsApiService.class)); //Retrofit创建一个NewsApiService的代理对象
    }

    //向外提供煎蛋的API
    @Provides
    JanDanApi provideJanDanApis(OkHttpClient.Builder builder) {

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build());

        return JanDanApi.getInstance(retrofitBuilder
                .baseUrl(ApiConstants.sJanDanApi) //指定service的base URL（通常情况下就是域名）
                .build().create(JanDanApiService.class)); //Retrofit创建一个JanDanApiService的代理对象
    }

    //向外提供豆瓣的API
    @Provides
    BookApi provideBookApis(OkHttpClient.Builder builder){
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build());

        return BookApi.getInstance(retrofitBuilder
        .baseUrl(ApiConstants.sDouBanApi)
        .build()
        .create(BookApiService.class));
    }

    //向外提供追书的API
    @Provides
    ReaderApi provideReaderApis(OkHttpClient.Builder builder){
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build());

        return ReaderApi.getInstance(retrofitBuilder
        .baseUrl(ApiConstants.sZhuiShuApi)
        .build()
        .create(ReaderApiService.class));
    }

    //向外提供开眼的API
    @Provides
    KaiYanApi provideKaiYanApis(OkHttpClient.Builder builder){
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build());

        return  KaiYanApi.getInstance(retrofitBuilder
        .baseUrl(ApiConstants.sKaiYanApi)
        .build()
        .create(KaiYanApiService.class));
    }
}
