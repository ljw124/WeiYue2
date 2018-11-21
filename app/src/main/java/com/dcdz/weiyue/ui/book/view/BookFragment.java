package com.dcdz.weiyue.ui.book.view;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.dcdz.weiyue.R;
import com.dcdz.weiyue.been.BookBean;
import com.dcdz.weiyue.component.ApplicationComponent;
import com.dcdz.weiyue.component.DaggerHttpComponent;
import com.dcdz.weiyue.ui.adapter.BookAdapter;
import com.dcdz.weiyue.ui.base.BaseFragment;
import com.dcdz.weiyue.ui.book.contract.BookContract;
import com.dcdz.weiyue.ui.book.presenter.BookPresenter;
import com.dcdz.weiyue.webview.WebViewActivity;
import com.dcdz.weiyue.widget.CustomLoadMoreView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class BookFragment extends BaseFragment<BookPresenter> implements BookContract.View {

    private String mType = "心理学";

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mPtrFrameLayout)
    PtrClassicFrameLayout mPtrFrameLayout;
    @BindView(R.id.et_type_name)
    EditText etTypeName;
    @BindView(R.id.iv_go)
    ImageView ivGo;

    // 开始请求的位置
    private int mStart = 0;
    // 一次请求的数量
    private int mCount = 18;
    private BookAdapter adapter;
    private List<BookBean.BooksBean> booksBeans;

    public static BookFragment newInstance() {
         Bundle args = new Bundle();
         BookFragment fragment = new BookFragment();
         fragment.setArguments(args);
         return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_book;
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
        mPresenter.getBookList(mType, mStart, mCount);
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        //设置下拉刷新
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return  PtrDefaultHandler.checkContentCanBePulledDown(frame, mRecyclerView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mStart = 0; //刷新数据时，从头开始加载
                mPresenter.getBookList(mType, mStart, mCount);
            }
        });

        booksBeans = new ArrayList<>();
        adapter = new BookAdapter(getActivity(), booksBeans);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mRecyclerView.setAdapter(adapter);
        adapter.setLoadMoreView(new CustomLoadMoreView());
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        //设置上拉加载更多
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (mPresenter != null){
                    mPresenter.getBookList(mType, mStart, mCount);
                }
            }
        }, mRecyclerView);

        //处理键盘搜索键
        etTypeName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!etTypeName.getText().toString().trim().isEmpty()){
                        mStart = 0;
                        mType = etTypeName.getText().toString().trim();
                        booksBeans.clear();
                        mPresenter.getBookList(mType, mStart, mCount);
                    }
                }
                return false;
            }
        });

        //点击搜索按钮
        ivGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etTypeName.getText().toString().trim().isEmpty()){
                    mStart = 0;
                    mType = etTypeName.getText().toString().trim();
                    booksBeans.clear();
                    mPresenter.getBookList(mType, mStart, mCount);
                }
            }
        });

        //设置条目点击监听
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                BookBean.BooksBean book = (BookBean.BooksBean) adapter.getItem(position);
                BookDetailActivity.launch(getActivity(), book);
            }
        });
    }

    @Override
    public void loadBookList(BookBean bookBeans) {
        if (bookBeans != null){
            mStart += mCount;
            adapter.setNewData(bookBeans.getBooks());
            mPtrFrameLayout.refreshComplete();
            showSuccess();
        }else {
            showFaild();
            return;
        }
    }

    @Override
    public void loadMoreBookList(BookBean bookBeans) {
        if (bookBeans != null){
            mStart += mCount;
            adapter.addData(bookBeans.getBooks());
            adapter.loadMoreComplete();
        }else {
            adapter.loadMoreEnd(); //没有更多了
            return;
        }
    }
}
