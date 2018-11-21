package com.dcdz.weiyue.ui.jandan.contract;

import com.dcdz.weiyue.been.FreshNewsBean;
import com.dcdz.weiyue.been.JdDetailBean;
import com.dcdz.weiyue.ui.base.BaseContract;

/**
 * desc: 相当于MVP的module层，提供presenter获取数据的接口，并提供把数据回调给view的接口
 */
public interface JanDanContract {

    //当presenter获取到数据后，通过下面的方法回调给view
    interface View extends BaseContract.BaseView {

        void loadFreshNews(FreshNewsBean freshNewsBean);

        void loadMoreFreshNews(FreshNewsBean freshNewsBean);

        void loadDetailData(String type, JdDetailBean jdDetailBean);

        void loadMoreDetailData(String type, JdDetailBean jdDetailBean);
    }

    //presenter实现下面的方法，并在对应的方法中获取对应的数据
    interface Presenter extends BaseContract.BasePresenter<View>{

        void getData(String type, int page);

        void getFreshNews(int page);

        void getDetailData(String type, int page);
    }
}
