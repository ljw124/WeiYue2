package com.dcdz.weiyue.ui.news;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import com.flyco.tablayout.SlidingTabLayout;
import com.dcdz.weiyue.R;
import com.dcdz.weiyue.been.Channel;
import com.dcdz.weiyue.component.ApplicationComponent;
import com.dcdz.weiyue.component.DaggerHttpComponent;
import com.dcdz.weiyue.ui.adapter.ChannelPagerAdapter;
import com.dcdz.weiyue.ui.base.BaseFragment;
import com.dcdz.weiyue.ui.news.contract.NewsContract;
import com.dcdz.weiyue.ui.news.presenter.NewsPresenter;
import com.dcdz.weiyue.widget.ChannelDialogFragment;
import com.dcdz.weiyue.widget.CustomViewPager;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 新闻页面
 */
public class NewsFragment extends BaseFragment<NewsPresenter> implements NewsContract.View  {

    @BindView(R.id.viewpager)
    CustomViewPager mViewpager;
    @BindView(R.id.iv_edit)
    ImageView mIvEdit;
    @BindView(R.id.SlidingTabLayout)
    SlidingTabLayout SlidingTabLayout; //替代ActionBar

    private ChannelPagerAdapter mChannelPagerAdapter;
    private List<Channel> mSelectedDatas;
    private List<Channel> mUnSelectedDatas;

    private int selectedIndex;
    private String selectedChannel;

    public static NewsFragment newInstance() {

        Bundle args = new Bundle();

        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_news;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {
        //DaggerHttpComponent是HttpComponent在reBuild之后自动生成的
        DaggerHttpComponent.builder()
                .applicationComponent(appComponent)
                .build()
                .inject(this);
    }

    //IBase中定义的接口，用来初始化页面
    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        //给Viewpager设置监听，获取当前的页面及对应的频道
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedIndex = position;
                selectedChannel = mSelectedDatas.get(position).getChannelName();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //初始化，回调接口loadData获取数据
    @Override
    public void initData() {
        mSelectedDatas = new ArrayList<>(); //主页面要显示的频道——我的频道
        mUnSelectedDatas = new ArrayList<>(); //主页面不显示的频道——推荐频道
        mPresenter.getChannel();
    }

    //presenter获取数据之后，会把数据回调给此方法
    @Override
    public void loadData(List<Channel> channels, List<Channel> otherChannels) {
        if (channels != null){
            mSelectedDatas.clear();
            mSelectedDatas.addAll(channels);
            mUnSelectedDatas.clear();
            mUnSelectedDatas.addAll(otherChannels);
            //初始化适配器，并传值（目前只是tab上显示的频道）
            mChannelPagerAdapter = new ChannelPagerAdapter(getChildFragmentManager(), channels);
            mViewpager.setAdapter(mChannelPagerAdapter);
            mViewpager.setOffscreenPageLimit(2); //设置ViewPager的后台预加载页面个数
            mViewpager.setCurrentItem(0, false);
            //SlidingTabLayout和ViewPager绑定
            SlidingTabLayout.setViewPager(mViewpager);
        } else {
            T("数据异常");
        }
    }

    //编辑频道——目前此功能屏蔽掉了
    @OnClick(R.id.iv_edit)
    public void onViewClicked() {
        ChannelDialogFragment dialogFragment = ChannelDialogFragment.newInstance(mSelectedDatas, mUnSelectedDatas);
        dialogFragment.show(getChildFragmentManager(), "CHANNEL");
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

}
