package com.dcdz.weiyue.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dcdz.weiyue.R;
import com.dcdz.weiyue.been.BillBookBean;
import com.dcdz.weiyue.been.BillBookPackage;
import com.dcdz.weiyue.net.ApiConstants;
import com.dcdz.weiyue.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by LJW on 2018/10/18.
 */
public class ReaderAdapter extends BaseQuickAdapter<BillBookBean, BaseViewHolder> {

    private Context mContext;

    public ReaderAdapter(Context context, @Nullable List<BillBookBean> data) {
        super(R.layout.item_reader, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, BillBookBean item) {
        helper.setText(R.id.book_brief_tv_title, item.getTitle())
                .setText(R.id.book_brief_tv_author, item.getAuthor())
                .setText(R.id.book_brief_tv_brief, item.getShortIntro())
                .setText(R.id.book_brief_person, item.getLatelyFollower() + "人在追 |")
                .setText(R.id.book_brief_saved, item.getRetentionRatio() + "读者留存");
        ImageLoaderUtil.LoadImage(mContext, ApiConstants.sZhuiShuImg + item.getCover(), helper.getView(R.id.book_brief_iv_portrait));
    }
}
