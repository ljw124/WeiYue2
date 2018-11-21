package com.dcdz.weiyue.ui.video.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.dcdz.weiyue.R;
import com.dcdz.weiyue.been.VideoChannelBean;
import com.dcdz.weiyue.been.VideoDetailBean;
import com.dcdz.weiyue.component.ApplicationComponent;
import com.dcdz.weiyue.component.DaggerHttpComponent;
import com.dcdz.weiyue.ui.adapter.VideoPagerAdapter;
import com.dcdz.weiyue.ui.base.BaseFragment;
import com.dcdz.weiyue.ui.video.contract.VideoContract;
import com.dcdz.weiyue.ui.video.presenter.VideoPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * desc: 视频页面
 */
public class VideoFragment extends BaseFragment<VideoPresenter> implements VideoContract.View {

    private static final String TAG = "VideoFragment";
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    VideoPagerAdapter adapter;

    public static VideoFragment newInstance() {
        
        Bundle args = new Bundle();
        
        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_video;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {
        DaggerHttpComponent.builder()
                .applicationComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        mPresenter.getVideoChannel();
    }

    @Override
    public void loadVideoChannel(List<VideoChannelBean> channelBean) {
        Log.i(TAG, "loadVideoChannel: " + channelBean.toString());
        adapter = new VideoPagerAdapter(getChildFragmentManager(), channelBean.get(0));
        mViewpager.setAdapter(adapter);
        mViewpager.setOffscreenPageLimit(1);
        mViewpager.setCurrentItem(0, false);
        mTablayout.setupWithViewPager(mViewpager);
    }

    @Override
    public void loadVideoDetails(List<VideoDetailBean> detailBean) {

    }

    @Override
    public void loadMoreVideoDetails(List<VideoDetailBean> detailBean) {

    }
}
