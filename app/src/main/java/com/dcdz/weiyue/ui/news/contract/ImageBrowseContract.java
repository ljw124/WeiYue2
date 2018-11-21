package com.dcdz.weiyue.ui.news.contract;

import com.dcdz.weiyue.been.NewsArticleBean;
import com.dcdz.weiyue.ui.base.BaseContract;

public interface ImageBrowseContract {

    interface View extends BaseContract.BaseView{
        void loadData(NewsArticleBean newsArticleBean);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void getData(String aid, boolean isCmpp);
    }

}
