package com.dcdz.weiyue.ui.inter;

/**
 * 拖拽频道对应的接口
 */
public interface OnChannelListener {
    void onItemMove(int starPos, int endPos);
    void onMoveToMyChannel(int starPos, int endPos);
    void onMoveToOtherChannel(int starPos, int endPos);
    void onFinish(String selectedChannelName);
}
