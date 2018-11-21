package com.dcdz.weiyue.ui.book.presenter;

import com.dcdz.weiyue.been.BookDetailBean;
import com.dcdz.weiyue.net.BookApi;
import com.dcdz.weiyue.ui.base.BasePresenter;
import com.dcdz.weiyue.ui.book.contract.BookDetailContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by LJW on 2018/10/15.
 */
public class BookDetailPresenter extends BasePresenter<BookDetailContract.View> implements BookDetailContract.Presenter {

    private BookApi bookApi;

    @Inject
    BookDetailPresenter(BookApi bookApi){
        this.bookApi = bookApi;
    }

    @Override
    public void getBookDetail(String id) {
        bookApi.getBookDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.<BookDetailBean>bindToLife())
                .subscribe(new Observer<BookDetailBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BookDetailBean bookDetailBean) {
                        mView.loadBookDetail(bookDetailBean);
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
