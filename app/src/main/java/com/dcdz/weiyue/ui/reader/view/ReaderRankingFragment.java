package com.dcdz.weiyue.ui.reader.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dcdz.weiyue.R;
import com.dcdz.weiyue.been.BillBookBean;
import com.dcdz.weiyue.been.BillBookPackage;
import com.dcdz.weiyue.component.ApplicationComponent;
import com.dcdz.weiyue.component.DaggerHttpComponent;
import com.dcdz.weiyue.ui.adapter.ReaderAdapter;
import com.dcdz.weiyue.ui.base.BaseFragment;
import com.dcdz.weiyue.ui.reader.contract.ReaderRankingContract;
import com.dcdz.weiyue.ui.reader.presenter.ReaderRankingPresenter;
import com.dcdz.weiyue.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class ReaderRankingFragment extends BaseFragment<ReaderRankingPresenter> implements ReaderRankingContract.View {

    public static final String TYPE = "ranking";

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private ReaderAdapter adapter;
    private String type;

    public static ReaderRankingFragment newInstance(String ranking) {
         Bundle args = new Bundle();
         args.putString(TYPE, ranking);
         ReaderRankingFragment fragment = new ReaderRankingFragment();
         fragment.setArguments(args);
         return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_reader_ranking;
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
        type = getArguments().getString(TYPE);
        mPresenter.getBillBook(type);
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ReaderAdapter(getContext(), null);
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.showShort(getContext(), "暂未开发");
            }
        });
    }

    @Override
    public void loadBillBookList(List<BillBookBean> billBookBeans) {
        if (billBookBeans != null){
            adapter.setNewData(billBookBeans);
            adapter.notifyDataSetChanged();
        }
    }
}
