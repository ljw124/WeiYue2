package com.dcdz.weiyue.ui.book.contract;

import com.dcdz.weiyue.been.BookBean;
import com.dcdz.weiyue.ui.base.BaseContract;

import java.util.List;

/**
 * Created by LJW on 2018/10/15.
 */
public interface BookContract {

    interface View extends BaseContract.BaseView{

        //数据刷新回调
        void loadBookList(BookBean bookBeans);

        //加载更多回调
        void loadMoreBookList(BookBean bookBeans);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{

        //获取接口数据
        void getBookList(String tag, int start, int count);
    }
}
