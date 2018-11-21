package com.dcdz.weiyue.net;

import com.dcdz.weiyue.been.BillBookPackage;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by LJW on 2018/10/18.
 */
public interface ReaderApiService {

    /**
     * 获取单一排行榜
     * 周榜：rankingId-> _id
     * 月榜：rankingId-> monthRank
     * 总榜：rankingId-> totalRank
     *
     * @return
     */
    @GET("/ranking/{rankingId}")
    Observable<BillBookPackage> getBillBookPackage(@Path("rankingId") String rankingId);

}
