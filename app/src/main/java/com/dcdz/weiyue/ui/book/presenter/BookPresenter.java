package com.dcdz.weiyue.ui.book.presenter;

import com.dcdz.weiyue.been.BookBean;
import com.dcdz.weiyue.net.BookApi;
import com.dcdz.weiyue.ui.base.BasePresenter;
import com.dcdz.weiyue.ui.book.contract.BookContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by LJW on 2018/10/15.
 */
public class BookPresenter extends BasePresenter<BookContract.View> implements BookContract.Presenter{

    private BookApi bookApi;

    @Inject
    BookPresenter(BookApi bookApi){
        this.bookApi = bookApi;
    }

    @Override
    public void getBookList(String tag, int start, int count) {
        bookApi.getBookList(tag, start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.<BookBean>bindToLife())
                .subscribe(new Observer<BookBean>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BookBean bookBean) {
                        if (start > 0){ //加载更多
                            mView.loadMoreBookList(bookBean);
                        }else {
                            mView.loadBookList(bookBean);
                        }
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
