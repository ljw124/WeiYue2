package com.dcdz.weiyue.component;

import android.content.Context;

import com.dcdz.weiyue.MyApp;
import com.dcdz.weiyue.module.ApplicationModule;
import com.dcdz.weiyue.module.HttpModule;
import com.dcdz.weiyue.net.BookApi;
import com.dcdz.weiyue.net.JanDanApi;
import com.dcdz.weiyue.net.KaiYanApi;
import com.dcdz.weiyue.net.NewsApi;
import com.dcdz.weiyue.net.ReaderApi;

import dagger.Component;

/**
 * @Component: Components从根本上来说就是一个注入器，把依赖注入到需要依赖的地方。
 * 被标注了Component的接口在编译时会产生相应的类的实例来作为提供依赖方和需要依赖方之间的桥梁，
 * 把相关依赖注入到其中，
 * 也可以说是@Inject和@Module的桥梁，它的主要作用就是连接这两个部分
 */

@Component(modules = {ApplicationModule.class, HttpModule.class}) //告诉Dagger2提供依赖的是ApplicationModule、HttpModule这两个类
public interface ApplicationComponent {

    Context getContext();

    NewsApi getNetEaseApi();

    JanDanApi getJanDanApi();

    BookApi getBookApi();

    ReaderApi getReaderApi();

    KaiYanApi getKaiYanApi();
}
