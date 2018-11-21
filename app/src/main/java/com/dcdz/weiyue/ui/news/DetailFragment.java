package com.dcdz.weiyue.ui.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.dcdz.weiyue.MyApp;
import com.dcdz.weiyue.R;
import com.dcdz.weiyue.been.NewsDetailBean;
import com.dcdz.weiyue.component.ApplicationComponent;
import com.dcdz.weiyue.component.DaggerHttpComponent;
import com.dcdz.weiyue.net.NewsApi;
import com.dcdz.weiyue.net.NewsUtils;
import com.dcdz.weiyue.ui.adapter.NewsDetailAdapter;
import com.dcdz.weiyue.ui.base.BaseFragment;
import com.dcdz.weiyue.ui.news.contract.DetailContract;
import com.dcdz.weiyue.ui.news.presenter.DetailPresenter;
import com.dcdz.weiyue.utils.ContextUtils;
import com.dcdz.weiyue.utils.ImageLoaderUtil;
import com.dcdz.weiyue.widget.CustomLoadMoreView;
import com.dcdz.weiyue.widget.NewsDelPop;
import com.flyco.animation.SlideEnter.SlideRightEnter;
import com.flyco.animation.SlideExit.SlideRightExit;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 展示新闻列表
 */
public class DetailFragment extends BaseFragment<DetailPresenter> implements DetailContract.View {

    private static final String TAG = "DetailFragment";
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mPtrFrameLayout)
    PtrFrameLayout mPtrFrameLayout; //下拉刷新控件
    @BindView(R.id.tv_toast)
    TextView mTvToast;
    @BindView(R.id.rl_top_toast)
    RelativeLayout mRlTopToast;

    private View view_Focus;//顶部banner
    private Banner mBanner;
    private NewsDelPop newsDelPop;//删除文章弹框
    private String newsid;
    private int position;
    private List<NewsDetailBean.ItemBean> beanList;
    private List<NewsDetailBean.ItemBean> mBannerList;
    private NewsDetailAdapter detailAdapter;
    private int upPullNum = 1;
    private int downPullNum = 1;
    private boolean isRemoveHeaderView = false;

    public static DetailFragment newInstance(String newsid, int position) {
        Bundle args = new Bundle();
        args.putString("newsid", newsid);
        args.putInt("position", position);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_detail;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {
        DaggerHttpComponent.builder()
                .applicationComponent(appComponent)
                .build()
                .inject(this);
    }

    //初始化，回调接口getData获取数据（ViewPager滑动加载新数据也会回调）
    @Override
    public void initData() {
        if (getArguments() == null) return;
        newsid = getArguments().getString("newsid");
        position = getArguments().getInt("position");
        mPresenter.getData(newsid, NewsApi.ACTION_DEFAULT, 1);
    }

    //IBase中定义的接口，用来初始化页面
    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        mPtrFrameLayout.disableWhenHorizontalMove(true); //避免与ViewPager滑动冲突
        //下拉刷新
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mRecyclerView, header);
            }

            ////在这里写自己下拉刷新数据的请求
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                Log.i(TAG, "onRefreshBegin: " + downPullNum);
                isRemoveHeaderView = true;
                mPresenter.getData(newsid, NewsApi.ACTION_DOWN, downPullNum);
            }
        });
        beanList = new ArrayList<>();
        mBannerList = new ArrayList<>();
        //给RecyclerView设置适配器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        detailAdapter = new NewsDetailAdapter(beanList, getActivity());
        mRecyclerView.setAdapter(detailAdapter);
        //适配器基础设置
        detailAdapter.setEnableLoadMore(true);
        detailAdapter.setLoadMoreView(new CustomLoadMoreView()); //设置加载更多的动画
        detailAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        //上拉加载更多
        detailAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                Log.i(TAG, "onLoadMoreRequested: " + upPullNum);
                mPresenter.getData(newsid, NewsApi.ACTION_UP, upPullNum);
            }
        }, mRecyclerView);
        //设置条目监听——展示文章详情
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                NewsDetailBean.ItemBean itemBean = (NewsDetailBean.ItemBean) adapter.getItem(position);
                toRead(itemBean);
            }
        });
        //设置条目内子view的监听——设置对删除按钮的监听
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                NewsDetailBean.ItemBean itemBean = (NewsDetailBean.ItemBean) adapter.getItem(position);
                switch (view.getId()){
                    case R.id.iv_close:
                        view.getHeight();
                        int[] location = new int[2];
                        view.getLocationInWindow(location);
                        Log.i("JdDetailFragment", "点击的item的高度:" + view.getHeight() + "x值:" + location[0] + "y值" + location[1]);
                        if (itemBean.getStyle() == null) return;
                        //获取点击条目的高度，如果高度小于80，则从view的上面弹出弹框；否则从下面弹出
                        if (ContextUtils.getSreenWidth(MyApp.getContext()) - 50 - location[1] < ContextUtils.dip2px(MyApp.getContext(), 80)) {
                            newsDelPop.anchorView(view)
                                    .gravity(Gravity.TOP)
                                    .setBackReason(itemBean.getStyle().getBackreason(), true, position)
                                    .show();
                        } else {
                            newsDelPop.anchorView(view)
                                    .gravity(Gravity.BOTTOM)
                                    .setBackReason(itemBean.getStyle().getBackreason(), false, position)
                                    .show();
                        }
                        break;
                }
            }
        });
        //顶部轮播图
        view_Focus = getView().inflate(getActivity(), R.layout.news_detail_headerview, null);
        mBanner = (Banner) view_Focus.findViewById(R.id.banner);
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        //Glide 加载图片简单用法
                        ImageLoaderUtil.LoadImage(getActivity(), path, imageView);
                    }
                })
                .setDelayTime(3000)
                .setIndicatorGravity(BannerConfig.RIGHT);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (mBannerList.size() < 1) return;
                bannerToRead(mBannerList.get(position));
            }
        });

        //删除弹框设置
        newsDelPop = new NewsDelPop(getActivity())
                .alignCenter(false)
                .widthScale(0.95f)
//                .showAnim(new FlipRightEnter())
//                .dismissAnim(new FlipHorizontalExit())
                .showAnim(new SlideRightEnter())
                .dismissAnim(new SlideRightExit())
                .offset(-100, 0)
                .dimEnabled(true);
        //给弹框设置点击回调
        newsDelPop.setClickListener(new NewsDelPop.onClickListener() {
            @Override
            public void onClick(int position) {
                newsDelPop.dismiss();
                detailAdapter.remove(position);
                showToast(0, false);
            }
        });
    }

    //presenter获取数据之后，会把数据回调给此方法
    @Override
    public void loadData(List<NewsDetailBean.ItemBean> itemBeanList) {
        if (itemBeanList == null || itemBeanList.size() == 0) {
            showFaild();
            mPtrFrameLayout.refreshComplete();
        } else {
            downPullNum++;
            if (isRemoveHeaderView) {
                detailAdapter.removeAllHeaderView();
            }
            detailAdapter.setNewData(itemBeanList);
            showToast(itemBeanList.size(), true);
            mPtrFrameLayout.refreshComplete();
            showSuccess();
            Log.i(TAG, "loadData: " + itemBeanList.toString());
        }
    }

    /**
     * 加载文章
     * @param itemBean
     */
    private void toRead(NewsDetailBean.ItemBean itemBean) {
        if (itemBean == null) {
            return;
        }
        switch (itemBean.getItemType()) {
            case NewsDetailBean.ItemBean.TYPE_DOC_TITLEIMG:
            case NewsDetailBean.ItemBean.TYPE_DOC_SLIDEIMG:
                Intent intent = new Intent(getActivity(), ArticleReadActivity.class);
                intent.putExtra("aid", itemBean.getDocumentId());
                startActivity(intent);
                break;
            case NewsDetailBean.ItemBean.TYPE_SLIDE:
                ImageBrowseActivity.launch(getActivity(), itemBean);
                break;
            case NewsDetailBean.ItemBean.TYPE_ADVERT_TITLEIMG:
            case NewsDetailBean.ItemBean.TYPE_ADVERT_SLIDEIMG:
            case NewsDetailBean.ItemBean.TYPE_ADVERT_LONGIMG:
                AdvertActivity.launch(getActivity(), itemBean.getLink().getWeburl());
                break;
            case NewsDetailBean.ItemBean.TYPE_PHVIDEO:
                T("TYPE_PHVIDEO");
                break;
        }
    }

    /**
     * 加载banner
     * @param itemBean
     */
    private void bannerToRead(NewsDetailBean.ItemBean itemBean) {
        if (itemBean == null) {
            return;
        }
        switch (itemBean.getType()) {
            case NewsUtils.TYPE_DOC:
                Intent intent = new Intent(getActivity(), ArticleReadActivity.class);
                intent.putExtra("aid", itemBean.getDocumentId());
                startActivity(intent);
                break;
            case NewsUtils.TYPE_SLIDE:
                ImageBrowseActivity.launch(getActivity(), itemBean);
                break;
            case NewsUtils.TYPE_ADVERT:
                AdvertActivity.launch(getActivity(), itemBean.getLink().getWeburl());
                break;
            case NewsUtils.TYPE_PHVIDEO:
                T("TYPE_PHVIDEO");
                break;
        }
    }

    /**
     * 更新文章提示
     * @param num
     * @param isRefresh
     */
    private void showToast(int num, boolean isRefresh) {
        if (isRefresh) {
            mTvToast.setText(String.format(getResources().getString(R.string.news_toast), num + ""));
        } else {
            mTvToast.setText("将为你减少此类内容");
        }
        mRlTopToast.setVisibility(View.VISIBLE);
        //动画
        ViewAnimator.animate(mRlTopToast)
                .newsPaper()
                .duration(1000)
                .start()
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        ViewAnimator.animate(mRlTopToast)
                                .bounceOut()
                                .duration(1000)
                                .start();
                    }
                });
    }

    //重新加载
    @Override
    public void onRetry() {
        initData();
    }

    //判断是否有Banner，有则轮询播放
    @Override
    public void loadBannerData(NewsDetailBean newsDetailBean) {
        Log.i(TAG, "loadBannerData: " + newsDetailBean.toString());
        List<String> mTitleList = new ArrayList<>();
        List<String> mUrlList = new ArrayList<>();
        mBannerList.clear();
        for (NewsDetailBean.ItemBean bean: newsDetailBean.getItem()) {
            if (!TextUtils.isEmpty(bean.getThumbnail())){ //Thumbnail:图片地址
                mTitleList.add(bean.getTitle());
                mBannerList.add(bean);
                mUrlList.add(bean.getThumbnail());
            }
        }
        if (mUrlList.size() > 0) {
            mBanner.setImages(mUrlList);
            mBanner.setBannerTitles(mTitleList);
            mBanner.start();
            if (detailAdapter.getHeaderLayoutCount() < 1) {
                detailAdapter.addHeaderView(view_Focus);
            }
        }
    }

    //加载更多的回调
    @Override
    public void loadMoreData(List<NewsDetailBean.ItemBean> itemBeanList) {
        if (itemBeanList == null || itemBeanList.size() == 0) {
            detailAdapter.loadMoreFail();
        } else {
            upPullNum++;
            detailAdapter.addData(itemBeanList);
            detailAdapter.loadMoreComplete();
            Log.i(TAG, "loadMoreData: " + itemBeanList.toString());
        }
    }

    @Override
    public void loadTopNewsData(NewsDetailBean newsDetailBean) {

    }

}
