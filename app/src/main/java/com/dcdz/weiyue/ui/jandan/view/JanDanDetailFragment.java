package com.dcdz.weiyue.ui.jandan.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dcdz.weiyue.R;
import com.dcdz.weiyue.been.FreshNewsBean;
import com.dcdz.weiyue.been.JdDetailBean;
import com.dcdz.weiyue.component.ApplicationComponent;
import com.dcdz.weiyue.component.DaggerHttpComponent;
import com.dcdz.weiyue.net.JanDanApi;
import com.dcdz.weiyue.ui.base.BaseFragment;
import com.dcdz.weiyue.ui.jandan.contract.JanDanContract;
import com.dcdz.weiyue.ui.jandan.presenter.JanDanPresenter;
import com.dcdz.weiyue.widget.CustomLoadMoreView;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 煎蛋——条目展示
 */
@SuppressLint("ValidFragment")
public class JanDanDetailFragment extends BaseFragment<JanDanPresenter> implements JanDanContract.View {

    public static final String TYPE = "type";

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mPtrFrameLayout)
    PtrFrameLayout mPtrFrameLayout;

    private BaseQuickAdapter mAdapter;
    private int pageNum = 1;
    private String type;

    public JanDanDetailFragment(BaseQuickAdapter adapter) {
        this.mAdapter = adapter;
    }

    //参数一：判断是煎蛋的哪个模块
    public static JanDanDetailFragment newInstance(String type, BaseQuickAdapter baseQuickAdapter) {

        Bundle args = new Bundle();
        args.putCharSequence(TYPE, type);

        JanDanDetailFragment fragment = new JanDanDetailFragment(baseQuickAdapter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_jan_dan_detail;
    }

    //注入依赖
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
        type = getArguments().getString(TYPE);
        mPresenter.getData(type, pageNum);
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        mPtrFrameLayout.disableWhenHorizontalMove(true);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mRecyclerView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNum = 1;
                mPresenter.getData(type, pageNum);
            }
        });
        //RecyclerView设置适配器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        //BaseQuickAdapter初始化
        mAdapter.setEnableLoadMore(true);
        mAdapter.setPreLoadNumber(1);
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        //BaseQuickAdapter设置加载更多的监听
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getData(type, pageNum);
            }
        }, mRecyclerView);
        //BaseQuickAdapter设置条目监听
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (type.equals(JanDanApi.TYPE_FRESH)){
                    ReadActivity.launch(getActivity(), (FreshNewsBean.PostsBean) adapter.getItem(position));
                }
            }
        });
    }

    @Override
    public void onRetry() {
        initData();
    }

    //下面四个方法是presenter获取数据之后回调回来的
    @Override
    public void loadFreshNews(FreshNewsBean freshNewsBean) {
        if (freshNewsBean == null) {
            mPtrFrameLayout.refreshComplete();
            showFaild();
        } else {
            pageNum++;
            mAdapter.setNewData(freshNewsBean.getPosts());
            mPtrFrameLayout.refreshComplete();
            showSuccess();
        }
    }

    @Override
    public void loadMoreFreshNews(FreshNewsBean freshNewsBean) {
        if (freshNewsBean == null) {
            mAdapter.loadMoreFail();
        } else {
            pageNum++;
            mAdapter.addData(freshNewsBean.getPosts());
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void loadDetailData(String type, JdDetailBean jdDetailBean) {
        if (jdDetailBean == null) {
            mPtrFrameLayout.refreshComplete();
            showFaild();
        } else {
            pageNum++;
            mAdapter.setNewData(jdDetailBean.getComments());
            mPtrFrameLayout.refreshComplete();
            showSuccess();
        }
    }

    @Override
    public void loadMoreDetailData(String type, JdDetailBean jdDetailBean) {
        if (jdDetailBean == null) {
            mAdapter.loadMoreFail();
        } else {
            pageNum++;
            mAdapter.addData(jdDetailBean.getComments());
            mAdapter.loadMoreComplete();
        }
    }
}
