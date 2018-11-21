package com.dcdz.weiyue;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.dcdz.weiyue.component.ApplicationComponent;
import com.dcdz.weiyue.ui.base.BaseActivity;
import com.dcdz.weiyue.ui.base.SupportFragment;
import com.dcdz.weiyue.ui.jandan.view.JanDanFragment;
import com.dcdz.weiyue.ui.news.NewsFragment;
import com.dcdz.weiyue.ui.personal.PersonalFragment;
import com.dcdz.weiyue.ui.reader.view.ReaderFragment;
import com.dcdz.weiyue.ui.video.view.VideoFragment;
import com.dcdz.weiyue.ui.book.view.BookFragment;
import com.dcdz.weiyue.utils.StatusBarUtil;
import com.dcdz.weiyue.widget.BottomBar;
import com.dcdz.weiyue.widget.BottomBarTab;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class MainActivity extends BaseActivity {

    @BindView(R.id.contentContainer)
    FrameLayout mContentContainer;
    //主页面对应的四个tab
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;
    //四个tab对应的Fragment
    private SupportFragment[] mFragments = new SupportFragment[6];

    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, 0, null);
        if (savedInstanceState == null){
            mFragments[0] = NewsFragment.newInstance();
            mFragments[1] = VideoFragment.newInstance();
            mFragments[2] = JanDanFragment.newInstance();
            mFragments[3] = BookFragment.newInstance();
            mFragments[4] = ReaderFragment.newInstance();
            mFragments[5] = PersonalFragment.newInstance();
            getSupportDelegate().loadMultipleRootFragment(R.id.contentContainer, 0,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2],
                    mFragments[3],
                    mFragments[4],
                    mFragments[5]);
        } else {
            mFragments[0] = findFragment(NewsFragment.class);
            mFragments[1] = findFragment(VideoFragment.class);
            mFragments[2] = findFragment(JanDanFragment.class);
            mFragments[3] = findFragment(BookFragment.class);
            mFragments[4] = findFragment(ReaderFragment.class);
            mFragments[5] = findFragment(PersonalFragment.class);
        }
        mBottomBar.addItem(new BottomBarTab(this, R.drawable.ic_news, "新闻"))
                .addItem(new BottomBarTab(this, R.drawable.ic_video, "视频"))
                .addItem(new BottomBarTab(this, R.drawable.ic_jiandan, "煎蛋"))
                .addItem(new BottomBarTab(this, R.drawable.ic_my, "文学"))
                .addItem(new BottomBarTab(this, R.drawable.ic_my, "小说"))
                .addItem(new BottomBarTab(this, R.drawable.ic_my, "我的"));
        //给tab设置选择监听
        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                getSupportDelegate().showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    //是否支持滑动返回
    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    //Activity回退栈内Fragment的数量 小于等于1 时,默认finish Activity
    @Override
    public void onBackPressedSupport() {
        if (JCVideoPlayer.backPress()){
            return;
        }
        super.onBackPressedSupport();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //重试
    @Override
    public void onRetry() {

    }

    @Override
    public void initData() {

    }
}
