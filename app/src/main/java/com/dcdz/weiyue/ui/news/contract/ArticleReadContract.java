package com.dcdz.weiyue.ui.news.contract;

import com.dcdz.weiyue.been.NewsArticleBean;
import com.dcdz.weiyue.ui.base.BaseContract;

public interface ArticleReadContract {

    //当presenter获取到数据后，通过下面的方法回调给view
    interface View extends BaseContract.BaseView{
        void loadData(NewsArticleBean articleBean);
    }

    //presenter实现下面的方法，并在该方法中获取对应的数据
    interface Presenter extends BaseContract.BasePresenter<View>{
        void getData(String aid);
    }

}
