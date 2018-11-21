package com.dcdz.weiyue.ui.jandan.view;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.dcdz.weiyue.R;
import com.dcdz.weiyue.component.ApplicationComponent;
import com.dcdz.weiyue.net.JanDanApi;
import com.dcdz.weiyue.ui.adapter.JdBoredPicAdapter;
import com.dcdz.weiyue.ui.adapter.JdFreshNewsAdapter;
import com.dcdz.weiyue.ui.adapter.JdJokesAdapter;
import com.dcdz.weiyue.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 煎蛋主页面
 * TabLayout + Viewpager
 */
public class JanDanFragment extends BaseFragment {

    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    private JanDanPagerAdapter mJanDanPagerAdapter;
    List<String> tabs;

    public static JanDanFragment newInstance() {

        Bundle args = new Bundle();

        JanDanFragment fragment = new JanDanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_jandan;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        tabs = new ArrayList<>();
        tabs.add("新鲜事");
        tabs.add("无聊图");
        tabs.add("妹子图");
        tabs.add("段子");
        mJanDanPagerAdapter = new JanDanPagerAdapter(getChildFragmentManager(), tabs);
        mViewpager.setAdapter(mJanDanPagerAdapter);
        mViewpager.setOffscreenPageLimit(1);
        mViewpager.setCurrentItem(0, false);
        //TabLayout和Viewpager关联
        mTabLayout.setupWithViewPager(mViewpager, true);
    }

    public class JanDanPagerAdapter extends FragmentStatePagerAdapter {
        private List<String> titles;

        public JanDanPagerAdapter(FragmentManager fm, List<String> titles) {
            super(fm);
            this.titles = titles;
        }

        @Override
        public BaseFragment getItem(int position) {
            switch (position) {
                case 0:
                    return JanDanDetailFragment.newInstance(JanDanApi.TYPE_FRESH, new JdFreshNewsAdapter(getActivity(),null));
                case 1:
                    return JanDanDetailFragment.newInstance(JanDanApi.TYPE_BORED, new JdBoredPicAdapter(getActivity(),null));
                case 2:
                    return JanDanDetailFragment.newInstance(JanDanApi.TYPE_GIRLS, new JdBoredPicAdapter(getActivity(),null));
                case 3:
                    return JanDanDetailFragment.newInstance(JanDanApi.TYPE_Duan, new JdJokesAdapter(null));
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }

}
