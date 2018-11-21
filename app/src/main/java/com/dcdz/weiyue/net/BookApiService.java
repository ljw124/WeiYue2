package com.dcdz.weiyue.net;

import com.dcdz.weiyue.been.BookBean;
import com.dcdz.weiyue.been.BookDetailBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by LJW on 2018/10/15.
 */
public interface BookApiService {

    /**
     * 根据tag获取图书
     *
     * @param tag   搜索关键字
     * @param count 一次请求的数目 最多100
     */
    @GET("v2/book/search")
    Observable<BookBean> getBook(@Query("tag") String tag, @Query("start") int start,
                             @Query("count") int count);

    /**
     * 根据图书id获取详细信息
     *
     * @param id
     * @return
     */
    @GET("v2/book/{id}")
    Observable<BookDetailBean> getBookDetail(@Path("id") String id);
}
