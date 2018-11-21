package com.dcdz.weiyue.ui.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

public interface BaseContract {

    interface BasePresenter<T extends BaseContract.BaseView> {

        void attachView(T view);

        void detachView();
    }


    interface BaseView {

        //显示进度中
        void showLoading();

        //显示请求成功
        void showSuccess();

        //失败
        void showFaild();

        //显示当前网络不可用
        void showNoNet();

        //重试
        void onRetry();

        //绑定生命周期，及时取消对Observable订阅
        <T> LifecycleTransformer<T> bindToLife();

    }
}
