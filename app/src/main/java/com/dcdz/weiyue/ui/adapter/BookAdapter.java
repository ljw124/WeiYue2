package com.dcdz.weiyue.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dcdz.weiyue.R;
import com.dcdz.weiyue.been.BookBean;
import com.dcdz.weiyue.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by LJW on 2018/10/15.
 */
public class BookAdapter extends BaseQuickAdapter<BookBean.BooksBean, BaseViewHolder>{

    private Context mContext;

    public BookAdapter(Context context, @Nullable List<BookBean.BooksBean> data){
        super(R.layout.item_book, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, BookBean.BooksBean item) {
        helper.setText(R.id.tv_name, item.getTitle())
                .setText(R.id.tv_rate, item.getRating().getAverage());
        ImageLoaderUtil.LoadImage(mContext, item.getImages().getLarge(), (ImageView) helper.getView(R.id.iv_top_photo));
    }
}
