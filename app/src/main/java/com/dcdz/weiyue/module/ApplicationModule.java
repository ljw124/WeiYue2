package com.dcdz.weiyue.module;

import android.content.Context;

import com.dcdz.weiyue.MyApp;

import dagger.Module;
import dagger.Provides;

/**
 * desc: Modules是提供依赖注入时所需对象实例的类
 * ps:依赖提供方
 * 我们有两种方式可以提供依赖，一个是注解了@Inject的构造方法，
 * 一个是在Module里提供的依赖, Module的优先级高于注解过的构造方法
 */
//@Module：把这个类标识为Dagger module
@Module
public class ApplicationModule {

    private Context mContext;

    public ApplicationModule(Context context) {
        this.mContext = context;
    }

    //@Providers：标识函数作为注入提供者，函数名并不重要，它只依赖于所提供的类类型
    //在modules中，我们定义的方法是用这个注解，以此来告诉Dagger我们想要构造对象并提供这些依赖
    @Provides
    MyApp provideApplication() {
        return (MyApp) mContext.getApplicationContext();
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
