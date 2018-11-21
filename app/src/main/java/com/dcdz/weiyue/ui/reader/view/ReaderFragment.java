package com.dcdz.weiyue.ui.reader.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.dcdz.weiyue.R;
import com.dcdz.weiyue.component.ApplicationComponent;
import com.dcdz.weiyue.net.ReaderApi;
import com.dcdz.weiyue.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ReaderFragment extends BaseFragment {

    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    List<String> tabs;
    private ReaderRankingAdapter adapter;

    public static ReaderFragment newInstance() {
        Bundle args = new Bundle();
        ReaderFragment fragment = new ReaderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_reader;
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
        tabs.add("周榜");
        tabs.add("月榜");
        tabs.add("总榜");
        adapter = new ReaderRankingAdapter(getChildFragmentManager(), tabs);
        mViewpager.setAdapter(adapter);
        mViewpager.setOffscreenPageLimit(2);
        mViewpager.setCurrentItem(0,false);
        mTabLayout.setupWithViewPager(mViewpager, true);
    }

    public class ReaderRankingAdapter extends FragmentStatePagerAdapter{

        private List<String> titles;

        public ReaderRankingAdapter(FragmentManager fm, List<String> titles) {
            super(fm);
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return ReaderRankingFragment.newInstance(ReaderApi.TYPE_WEEK);
                case 1:
                    return ReaderRankingFragment.newInstance(ReaderApi.TYPE_MONTH);
                case 2:
                    return ReaderRankingFragment.newInstance(ReaderApi.TYPE_TOTAL);
            }
            return null;
        }

        @Override
        public int getCount() {
            return titles.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
