package com.dcdz.weiyue.ui.news.contract;

import com.dcdz.weiyue.been.NewsDetailBean;
import com.dcdz.weiyue.ui.base.BaseContract;

import java.util.List;

/**
 * 获取新闻列表
 */
public interface DetailContract {

    //当presenter获取到数据后，通过下面的方法回调给view
    interface View extends BaseContract.BaseView {

        /**
         * 加载顶部banner数据
         *
         * @param newsDetailBean
         */
        void loadBannerData(NewsDetailBean newsDetailBean);

        /**
         * 加载置顶新闻数据
         *
         * @param newsDetailBean
         */
        void loadTopNewsData(NewsDetailBean newsDetailBean);

        /**
         * 加载新闻数据
         *
         * @param itemBeanList
         */
        void loadData(List<NewsDetailBean.ItemBean> itemBeanList);

        /**
         * 加载更多新闻数据
         *
         * @param itemBeanList
         */
        void loadMoreData(List<NewsDetailBean.ItemBean> itemBeanList);
    }

    //presenter实现下面的方法，并在该方法中获取对应的数据
    interface Presenter extends BaseContract.BasePresenter<View> {

        /**
         * 获取新闻列表信息
         *
         * @param id      频道ID值
         * @param action  用户操作方式
         *                1：下拉 down
         *                2：上拉 up
         *                3：默认 default
         * @param pullNum 操作次数 累加
         */
        void getData(String id, String action, int pullNum);
    }
}
