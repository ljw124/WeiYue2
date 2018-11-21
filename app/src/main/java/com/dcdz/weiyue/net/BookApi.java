package com.dcdz.weiyue.net;

import com.dcdz.weiyue.been.BookBean;
import com.dcdz.weiyue.been.BookDetailBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by LJW on 2018/10/15.
 */
public class BookApi {

    public static  BookApi sInstance;
    private BookApiService mService;

    public BookApi(BookApiService bookApiService){
        this.mService = bookApiService;
    }

    public static BookApi getInstance(BookApiService bookApiService){
        if (sInstance == null)
            sInstance = new BookApi(bookApiService);
        return sInstance;
    }

    /**
     * 根据tag获取图书
     */
    public Observable<BookBean> getBookList(String tag, int start, int count){
        return mService.getBook(tag, start, count);
    }

    /**
     * 根据图书id获取详细信息
     */
    public Observable<BookDetailBean> getBookDetail(String id){
        return mService.getBookDetail(id);
    }

}
