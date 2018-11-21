package com.dcdz.weiyue.ui.reader.presenter;

import com.dcdz.weiyue.been.BillBookBean;
import com.dcdz.weiyue.been.BillBookPackage;
import com.dcdz.weiyue.net.ReaderApi;
import com.dcdz.weiyue.ui.base.BasePresenter;
import com.dcdz.weiyue.ui.reader.contract.ReaderRankingContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by LJW on 2018/10/18.
 */
public class ReaderRankingPresenter extends BasePresenter<ReaderRankingContract.View> implements ReaderRankingContract.Presenter {

    private ReaderApi readerApi;

    @Inject
    ReaderRankingPresenter(ReaderApi readerApi){
        this.readerApi = readerApi;
    }

    @Override
    public void getBillBook(String rankingId) {
        readerApi.getBillBook(rankingId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.<List<BillBookBean>>bindToLife())
                .subscribe(new Observer<List<BillBookBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<BillBookBean> billBookBeans) {
                        mView.loadBillBookList(billBookBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showFaild();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
