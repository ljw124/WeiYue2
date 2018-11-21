package com.dcdz.weiyue.ui.personal;


import android.os.Bundle;
import android.view.View;

import com.dcdz.weiyue.R;
import com.dcdz.weiyue.component.ApplicationComponent;
import com.dcdz.weiyue.ui.base.BaseFragment;
import com.dcdz.weiyue.webview.WebViewActivity;

import butterknife.OnClick;

/**
 * “我的”页面
 */
public class PersonalFragment extends BaseFragment {

    public static PersonalFragment newInstance() {
        
        Bundle args = new Bundle();
        
        PersonalFragment fragment = new PersonalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_personal;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.ivAuthor, R.id.tvUrl, R.id.tvGithubUrl, R.id.tvEmailUrl})
    public void clickListen(View view){
        switch (view.getId()){
            case R.id.ivAuthor:
                WebViewActivity.loadUrl(getActivity(), "https://github.com/ljw124/meituan", "lyp");
                break;
            case R.id.tvUrl:
                WebViewActivity.loadUrl(getActivity(), "https://blog.csdn.net/ljw124213", "lyp");
                break;
            case R.id.tvGithubUrl:
                WebViewActivity.loadUrl(getActivity(), "https://github.com/ljw124/meituan", "lyp");
                break;
            case R.id.tvEmailUrl:
                WebViewActivity.loadUrl(getActivity(), "https://github.com/ljw124/meituan", "lyp");
                break;
        }
    }
}
