package com.dcdz.weiyue.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/**
 * desc: 分享
 */
public class ShareUtils {

    /**
     * 文字分享
     *
     * @param context 上下文
     * @param content 内容
     */
    public static void shareText(Context context, String content) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
       // shareIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        shareIntent.setType("text/plain");
        //设置分享列表的标题，并且每次都显示分享列表
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }


    //分享单张图片
    public static void shareSingleImage(Context context, String imageUrl) {
        //由文件得到uri
        Uri imageUri = Uri.parse(imageUrl);
        Log.d("share", "uri:" + imageUri);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }
}
