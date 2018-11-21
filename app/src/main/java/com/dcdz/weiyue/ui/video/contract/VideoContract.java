package com.dcdz.weiyue.ui.video.contract;

import com.dcdz.weiyue.been.VideoChannelBean;
import com.dcdz.weiyue.been.VideoDetailBean;
import com.dcdz.weiyue.ui.base.BaseContract;

import java.util.List;

public interface VideoContract {

    //View层实现此接口，来处理返回得结果
    interface View extends BaseContract.BaseView{

        void loadVideoChannel(List<VideoChannelBean> channelBean);

        void loadVideoDetails(List<VideoDetailBean> detailBean);

        void loadMoreVideoDetails(List<VideoDetailBean> detailBean);
    }

    //Presenter层实现此接口，请求数据
    interface Presenter extends  BaseContract.BasePresenter<View>{
        /**
         * 获取视频频道列表
         */
        void getVideoChannel();

        /**
         * 获取视频列表
         *
         * @param page     页码
         * @param listType 默认list
         * @param typeId   频道id
         */
        void getVideoDetails(int page, String listType, String typeId);
    }
}
