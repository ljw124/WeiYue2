package com.dcdz.weiyue.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dcdz.weiyue.R;
import com.dcdz.weiyue.been.FreshNewsBean;
import com.dcdz.weiyue.utils.ImageLoaderUtil;
import java.util.List;

/**
 * desc: 煎蛋——新鲜事
 */
public class JdFreshNewsAdapter extends BaseQuickAdapter<FreshNewsBean.PostsBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {
    private Context mContext;

    public JdFreshNewsAdapter(Context context, @Nullable List<FreshNewsBean.PostsBean> data) {
        super(R.layout.item_fresh_news, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, FreshNewsBean.PostsBean postsBean) {
        viewHolder.setText(R.id.tv_title, postsBean.getTitle())
                .setText(R.id.tv_info, postsBean.getAuthor().getName())
                .setText(R.id.tv_commnetsize, postsBean.getComment_count() + "评论");
        ImageLoaderUtil.LoadImage(mContext, postsBean.getCustom_fields().getThumb_c().get(0), (ImageView) viewHolder.getView(R.id.iv_logo));
       // setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//        View view1 = baseQuickAdapter.getViewByPosition(i,R.id.iv_logo);
//        ReadActivity.launch(mContext, (FreshNewsBean.PostsBean) baseQuickAdapter.getItem(i));
    }
}
