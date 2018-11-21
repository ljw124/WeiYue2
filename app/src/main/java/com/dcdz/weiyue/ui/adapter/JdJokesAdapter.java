package com.dcdz.weiyue.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dcdz.weiyue.R;
import com.dcdz.weiyue.been.JdDetailBean;
import com.dcdz.weiyue.utils.DateUtil;
import com.dcdz.weiyue.utils.ShareUtils;
import java.util.List;

/**
 * desc: 煎蛋——段子
 */
public class JdJokesAdapter extends BaseQuickAdapter<JdDetailBean.CommentsBean, BaseViewHolder> {

    public JdJokesAdapter(@Nullable List<JdDetailBean.CommentsBean> data) {
        super(R.layout.item_joke, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, final JdDetailBean.CommentsBean commentsBean) {
        viewHolder.setText(R.id.tv_author, commentsBean.getComment_author())
                .setText(R.id.tv_time, DateUtil.getTimestampString(DateUtil.string2Date(commentsBean.getComment_date(), "yyyy-MM-dd HH:mm:ss")))
                .setText(R.id.tv_content, commentsBean.getText_content())
                .setText(R.id.tv_like, commentsBean.getVote_negative())
                .setText(R.id.tv_unlike, commentsBean.getVote_positive())
                .setText(R.id.tv_comment_count, commentsBean.getSub_comment_count());

        viewHolder.getView(R.id.img_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareText(mContext, commentsBean.getText_content());
            }
        });
    }
}
