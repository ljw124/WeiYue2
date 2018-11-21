package com.dcdz.weiyue.ui.reader.contract;

import com.dcdz.weiyue.been.BillBookBean;
import com.dcdz.weiyue.been.BillBookPackage;
import com.dcdz.weiyue.ui.base.BaseContract;

import java.util.List;

/**
 * Created by LJW on 2018/10/18.
 */
public interface ReaderRankingContract {

    interface View extends BaseContract.BaseView{
        //数据刷新回调
        void loadBillBookList(List<BillBookBean> billBookBeans);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{

        void getBillBook(String rankingId);
    }
}
