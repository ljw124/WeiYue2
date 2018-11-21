package com.dcdz.weiyue.ui.book.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dcdz.weiyue.R;
import com.dcdz.weiyue.been.BookBean;
import com.dcdz.weiyue.been.BookDetailBean;
import com.dcdz.weiyue.component.ApplicationComponent;
import com.dcdz.weiyue.component.DaggerHttpComponent;
import com.dcdz.weiyue.ui.base.BaseActivity;
import com.dcdz.weiyue.ui.book.contract.BookDetailContract;
import com.dcdz.weiyue.ui.book.presenter.BookDetailPresenter;
import com.dcdz.weiyue.utils.ShareUtils;
import com.dcdz.weiyue.webview.WebViewActivity;

import butterknife.BindView;

public class BookDetailActivity extends BaseActivity<BookDetailPresenter> implements BookDetailContract.View {

    private static final String ID = "id";
    private String id;

    @BindView(R.id.tv_book_summary)
    TextView tvSummary;
    @BindView(R.id.tv_book_intro)
    TextView tvIntro;
    @BindView(R.id.tv_book_directory)
    TextView tvDirectory;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.iv_share)
    ImageView ivShare;

    private String alt;
    private String title;

    public static void launch(Activity context, BookBean.BooksBean data){
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra(ID, data.getId());
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_book_detail;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {
        DaggerHttpComponent.builder()
                .applicationComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
//        setStatusBarColor(Color.parseColor("#64B6D1"), 30);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareText(BookDetailActivity.this, alt);
            }
        });
        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 WebViewActivity.loadUrl(BookDetailActivity.this, alt, title);
            }
        });
    }

    @Override
    public void initData() {
        if (getIntent().getExtras() == null) return;
        id = getIntent().getStringExtra(ID);
        mPresenter.getBookDetail(id);
    }

    @Override
    public void loadBookDetail(BookDetailBean bookDetailBean) {
        if (bookDetailBean != null){
            alt = bookDetailBean.getAlt();
            title = bookDetailBean.getTitle();
            tvSummary.setText(bookDetailBean.getSummary());
            tvIntro.setText(bookDetailBean.getAuthor_intro());
            tvDirectory.setText(bookDetailBean.getCatalog());
            tvTitle.setText(bookDetailBean.getTitle());
        }
    }

    @Override
    public void onRetry() {
        mPresenter.getBookDetail(id);
    }

}
