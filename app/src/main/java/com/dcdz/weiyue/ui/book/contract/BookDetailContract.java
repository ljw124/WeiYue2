package com.dcdz.weiyue.ui.book.contract;

import com.dcdz.weiyue.been.BookDetailBean;
import com.dcdz.weiyue.ui.base.BaseContract;

/**
 * Created by LJW on 2018/10/15.
 */
public interface BookDetailContract {

    interface View extends BaseContract.BaseView{

        void loadBookDetail(BookDetailBean bookDetailBean);
    }

    interface Presenter extends BaseContract.BasePresenter<BookDetailContract.View>{

        void getBookDetail(String id);
    }
}
