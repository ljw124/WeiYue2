package com.dcdz.weiyue.ui.news.presenter;

import com.dcdz.weiyue.MyApp;
import com.dcdz.weiyue.R;
import com.dcdz.weiyue.been.Channel;
import com.dcdz.weiyue.database.ChannelDao;
import com.dcdz.weiyue.ui.base.BasePresenter;
import com.dcdz.weiyue.ui.news.contract.NewsContract;
import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

/**
 * presenter
 * 获取model层（ChannelDao）的数据，回调给view层
 * 获取频道，用来显示在tab上
 */
public class NewsPresenter extends BasePresenter<NewsContract.View> implements NewsContract.Presenter {

    //@Inject 注解构造方法来提供依赖
    @Inject
    public NewsPresenter() {
    }

    @Override
    public void getChannel() {
        List<Channel> channelList; //所有频道
        List<Channel> myChannels = new ArrayList<>(); //我的频道
        List<Channel> otherChannels = new ArrayList<>(); //推荐频道
        channelList = ChannelDao.getChannels(); //获取数据库中的频道
        if (channelList.size() < 1) { //数据库中没有保存频道
            List<String> channelName = Arrays.asList(MyApp.getContext().getResources()
                    .getStringArray(R.array.news_channel));
            List<String> channelId = Arrays.asList(MyApp.getContext().getResources()
                    .getStringArray(R.array.news_channel_id));
            List<Channel> channels = new ArrayList<>(); //所有的频道
            for (int i = 0; i < channelName.size(); i++) {
                Channel channel = new Channel();
                channel.setChannelId(channelId.get(i));
                channel.setChannelName(channelName.get(i));
                channel.setChannelType(i < 1 ? 1 : 0); //0 可移除，1不可移除
                channel.setChannelSelect(i < channelId.size() - 3); //在首页显示的频道
                if (i < channelId.size() - 3) {
                    myChannels.add(channel); //我的频道
                } else {
                    otherChannels.add(channel); //推荐频道
                }
                channels.add(channel);
            }

            //把所有的频道异步保存到数据库中
            DataSupport.saveAllAsync(channels).listen(new SaveCallback() {
                @Override
                public void onFinish(boolean success) {
                }
            });

            channelList = new ArrayList<>();
            channelList.addAll(channels);
        } else { //数据库中获取到频道
            channelList = ChannelDao.getChannels();
            Iterator<Channel> iterator = channelList.iterator();
            while (iterator.hasNext()) {
                Channel channel = iterator.next();
                if (!channel.isChannelSelect()) {
                    otherChannels.add(channel);
                    iterator.remove();
                }
            }
            myChannels.addAll(channelList);
        }
        //获取到数据之后，回调给DetailFragment页面
        mView.loadData(myChannels, otherChannels);
    }
}
