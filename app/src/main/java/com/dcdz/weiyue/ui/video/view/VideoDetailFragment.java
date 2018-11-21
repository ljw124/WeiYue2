package com.dcdz.weiyue.ui.video.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dcdz.weiyue.R;
import com.dcdz.weiyue.been.VideoChannelBean;
import com.dcdz.weiyue.been.VideoDetailBean;
import com.dcdz.weiyue.component.ApplicationComponent;
import com.dcdz.weiyue.component.DaggerHttpComponent;
import com.dcdz.weiyue.ui.adapter.VideoDetailAdapter;
import com.dcdz.weiyue.ui.base.BaseFragment;
import com.dcdz.weiyue.ui.video.contract.VideoContract;
import com.dcdz.weiyue.ui.video.presenter.VideoPresenter;
import com.dcdz.weiyue.widget.CustomLoadMoreView;

import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * desc: 视频详情页面
 */
public class VideoDetailFragment extends BaseFragment<VideoPresenter> implements VideoContract.View{

    public static final String TYPEID = "typeId";
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mPtrFrameLayout)
    PtrClassicFrameLayout mPtrFrameLayout;

    private VideoDetailBean videoDetailBean;
    private VideoDetailAdapter detailAdapter;
    private int pageNum = 1;
    private String typeId;

    public static VideoDetailFragment newInstance(String typeId) {

        Bundle args = new Bundle();
        args.putCharSequence(TYPEID, typeId);
        VideoDetailFragment fragment = new VideoDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_video_detail;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {
        DaggerHttpComponent.builder()
                .applicationComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initData() {
        if (getArguments() == null) return;
        typeId = getArguments().getString(TYPEID);
        if (mPresenter != null){
            mPresenter.getVideoDetails(pageNum, "list", typeId);
        }
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        mPtrFrameLayout.disableWhenHorizontalMove(true);
        //下拉刷新
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mRecyclerView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNum = 1;
                if (mPresenter != null){
                    mPresenter.getVideoDetails(pageNum, "list", typeId);
                }
            }
        });
        videoDetailBean = new VideoDetailBean();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        detailAdapter = new VideoDetailAdapter(getActivity(), R.layout.item_detail_video, videoDetailBean.getItem());
        mRecyclerView.setAdapter(detailAdapter);
        detailAdapter.setEnableLoadMore(true);
        detailAdapter.setLoadMoreView(new CustomLoadMoreView());
        detailAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        //上拉加载更多
        detailAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (mPresenter != null){
                    mPresenter.getVideoDetails(pageNum, "list", typeId);
                }
            }
        }, mRecyclerView);
    }

    @Override
    public void onRetry() {
        initData();
    }

    @Override
    public void loadVideoChannel(List<VideoChannelBean> channelBean) {

    }

    @Override
    public void loadVideoDetails(List<VideoDetailBean> detailBean) {
        if (detailBean == null){
            showFaild();
            return;
        }
        pageNum ++;
        detailAdapter.setNewData(detailBean.get(0).getItem());
        mPtrFrameLayout.refreshComplete();
        showSuccess();
    }

    @Override
    public void loadMoreVideoDetails(List<VideoDetailBean> detailBean) {
        if (detailBean == null){
            detailAdapter.loadMoreEnd();
            return;
        }
        pageNum ++;
        detailAdapter.addData(detailBean.get(0).getItem());
        detailAdapter.loadMoreComplete();
    }
}
