package com.dcdz.weiyue.ui.news.presenter;

import android.util.Log;

import com.dcdz.weiyue.been.NewsDetailBean;
import com.dcdz.weiyue.net.BaseObserver;
import com.dcdz.weiyue.net.NewsApi;
import com.dcdz.weiyue.net.NewsUtils;
import com.dcdz.weiyue.net.RxSchedulers;
import com.dcdz.weiyue.ui.base.BasePresenter;
import com.dcdz.weiyue.ui.news.contract.DetailContract;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * presenter
 * 获取model层的数据，回调给view层
 * 获取新闻列表
 */
public class DetailPresenter extends BasePresenter<DetailContract.View> implements DetailContract.Presenter {

    private static final String TAG = "DetailPresenter";

    NewsApi mNewsApi;

    //@Inject 注解构造方法来提供依赖
    @Inject
    public DetailPresenter(NewsApi newsApi) {
        this.mNewsApi = newsApi;
    }

    @Override
    public void getData(final String id, final String action, int pullNum) {
        mNewsApi.getNewsDetail(id, action, pullNum)
                .compose(RxSchedulers.<List<NewsDetailBean>>applySchedulers())
                .map(new Function<List<NewsDetailBean>, NewsDetailBean>() {
                    @Override
                    public NewsDetailBean apply(List<NewsDetailBean> newsDetailBeans) throws Exception {
                        for (NewsDetailBean newsDetailBean : newsDetailBeans) {
                            //如果是轮播图或者是最上面的新闻，则回调给界面进行展示，否则继续向下传递
                            if (NewsUtils.isBannerNews(newsDetailBean)) {
                                mView.loadBannerData(newsDetailBean);
                            }
                            if (NewsUtils.isTopNews(newsDetailBean)) {
                                mView.loadTopNewsData(newsDetailBean);
                            }
                        }
                        return newsDetailBeans.get(0);
                    }
                })
                .map(new Function<NewsDetailBean, List<NewsDetailBean.ItemBean>>() {
                    @Override
                    public List<NewsDetailBean.ItemBean> apply(@NonNull NewsDetailBean newsDetailBean) throws Exception {
                        Iterator<NewsDetailBean.ItemBean> iterator = newsDetailBean.getItem().iterator();
                        while (iterator.hasNext()) {
                            try {
                                NewsDetailBean.ItemBean bean = iterator.next();
                                Log.i(TAG, bean.getType());
                                if (bean.getType().equals(NewsUtils.TYPE_DOC)) { //文章类型
                                    if (bean.getStyle().getView() != null) {
                                        Log.i(TAG, bean.getStyle().getView());
                                        if (bean.getStyle().getView().equals(NewsUtils.VIEW_TITLEIMG)) { //单图
                                            bean.itemType = NewsDetailBean.ItemBean.TYPE_DOC_TITLEIMG;
                                        } else { //多图
                                            bean.itemType = NewsDetailBean.ItemBean.TYPE_DOC_SLIDEIMG;
                                        }
                                    }
                                } else if (bean.getType().equals(NewsUtils.TYPE_ADVERT)) {
                                    if (bean.getStyle() != null) {
                                        Log.i(TAG, bean.getStyle().getView());
                                        if (bean.getStyle().getView().equals(NewsUtils.VIEW_TITLEIMG)) {
                                            bean.itemType = NewsDetailBean.ItemBean.TYPE_ADVERT_TITLEIMG;
                                        } else if (bean.getStyle().getView().equals(NewsUtils.VIEW_SLIDEIMG)) {
                                            bean.itemType = NewsDetailBean.ItemBean.TYPE_ADVERT_SLIDEIMG;
                                        } else {
                                            bean.itemType = NewsDetailBean.ItemBean.TYPE_ADVERT_LONGIMG;
                                        }
                                    } else {
                                        //bean.itemType = NewsDetailBean.ItemBean.TYPE_ADVERT_TITLEIMG;
                                        iterator.remove();
                                    }
                                } else if (bean.getType().equals(NewsUtils.TYPE_SLIDE)) {
                                    if (bean.getLink().getType().equals("doc")) {
                                        Log.i(TAG, bean.getStyle().getView());
                                        if (bean.getStyle().getView().equals(NewsUtils.VIEW_SLIDEIMG)) {
                                            bean.itemType = NewsDetailBean.ItemBean.TYPE_DOC_SLIDEIMG;
                                        } else {
                                            bean.itemType = NewsDetailBean.ItemBean.TYPE_DOC_TITLEIMG;
                                        }
                                    } else {
                                        bean.itemType = NewsDetailBean.ItemBean.TYPE_SLIDE;
                                    }
                                } else if (bean.getType().equals(NewsUtils.TYPE_PHVIDEO)) {
                                    bean.itemType = NewsDetailBean.ItemBean.TYPE_PHVIDEO;
                                } else {
                                    // 凤凰新闻 类型比较多，目前只处理能处理的类型
                                    iterator.remove();
                                }
                            } catch (Exception e) {
                                iterator.remove();
                                e.printStackTrace();
                            }
                        }
                        return newsDetailBean.getItem();
                    }
                })
                .compose(mView.<List<NewsDetailBean.ItemBean>>bindToLife())
                .subscribe(new BaseObserver<List<NewsDetailBean.ItemBean>>() {
                    @Override
                    public void onSuccess(List<NewsDetailBean.ItemBean> itemBeen) {
                        Log.i(TAG, itemBeen.get(0).getTitle());
                        if (!action.equals(NewsApi.ACTION_UP)) {
                            mView.loadData(itemBeen);
                        } else {
                            mView.loadMoreData(itemBeen);
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        Log.i(TAG, "onFail: " + e.getMessage().toString());
                        if (!action.equals(NewsApi.ACTION_UP)) {
                            mView.loadData(null);
                        } else {
                            mView.loadMoreData(null);
                        }
                    }
                });
    }
}
