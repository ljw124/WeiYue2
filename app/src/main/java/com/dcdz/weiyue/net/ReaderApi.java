package com.dcdz.weiyue.net;

import android.support.annotation.StringDef;

import com.dcdz.weiyue.been.BillBookBean;
import com.dcdz.weiyue.been.BillBookPackage;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by LJW on 2018/10/18.
 */
public class ReaderApi {

    public static final String TYPE_WEEK = "54d42d92321052167dfb75e3"; //周榜
    public static final String TYPE_MONTH = "564d820bc319238a644fb408"; //月榜
    public static final String TYPE_TOTAL = "564d8494fe996c25652644d2"; //总榜

    @StringDef({TYPE_WEEK, TYPE_MONTH, TYPE_TOTAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {

    }

    public static ReaderApi mInstance;
    private ReaderApiService mService;

    public ReaderApi(ReaderApiService mService){
        this.mService = mService;
    }

    public static ReaderApi getInstance(ReaderApiService readerApiService){
        if (mInstance == null){
            mInstance = new ReaderApi(readerApiService);
        }
        return mInstance;
    }

    /**
     * 获取单一排行榜
     * 周榜：rankingId-> _id
     * 月榜：rankingId-> monthRank
     * 总榜：rankingId-> totalRank
     * @return
     */
    public Observable<List<BillBookBean>> getBillBook(String rankingId){
        return mService.getBillBookPackage(rankingId).map(bean -> bean.getRanking().getBooks());
    }
}
