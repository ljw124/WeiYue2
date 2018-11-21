package com.dcdz.weiyue.event;

/**
 * 当前选中的频道
 */
public class SelectChannelEvent {

    public String channelName;

    public SelectChannelEvent(String channelName) {
        this.channelName = channelName;
    }
}
