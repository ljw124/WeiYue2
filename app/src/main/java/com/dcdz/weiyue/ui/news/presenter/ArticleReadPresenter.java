package com.dcdz.weiyue.ui.news.presenter;

import com.dcdz.weiyue.been.NewsArticleBean;
import com.dcdz.weiyue.net.NewsApi;
import com.dcdz.weiyue.net.RxSchedulers;
import com.dcdz.weiyue.ui.base.BasePresenter;
import com.dcdz.weiyue.ui.news.contract.ArticleReadContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class ArticleReadPresenter extends BasePresenter<ArticleReadContract.View> implements ArticleReadContract.Presenter {

    NewsApi mNewsApi;

    //@Inject 注解构造方法来提供依赖
    @Inject
    public ArticleReadPresenter(NewsApi newsApi) {
        this.mNewsApi = newsApi;
    }

    @Override
    public void getData(String aid) {
        mNewsApi.getNewsArticle(aid)
                .compose(RxSchedulers.<NewsArticleBean>applySchedulers())
                .compose(mView.<NewsArticleBean>bindToLife())//绑定生命周期，防止内存泄露
                .subscribe(new Observer<NewsArticleBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull NewsArticleBean articleBean) {
                        mView.loadData(articleBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.showFaild();
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }
}
