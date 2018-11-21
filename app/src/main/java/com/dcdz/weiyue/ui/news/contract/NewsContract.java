package com.dcdz.weiyue.ui.news.contract;

import com.dcdz.weiyue.been.Channel;
import com.dcdz.weiyue.ui.base.BaseContract;

import java.util.List;

/**
 * 获取频道，用来显示在tab上
 */
public interface NewsContract{

    //当presenter获取到数据后，通过下面的方法回调给view进行展示
    interface View extends BaseContract.BaseView{
        void loadData(List<Channel> channels, List<Channel> otherChannels);
    }

    //presenter实现下面的方法，并在该方法中获取对应的数据
    interface Presenter extends BaseContract.BasePresenter<View>{
        /**
         * 初始化频道
         */
        void getChannel();
    }

}
