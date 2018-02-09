package com.turing.framework.widget.imageloader;

import android.content.Context;
import android.widget.ImageView;

/**
 * use this class to load image,single instance
 * 参考：https://github.com/CameloeAnthony/Ant
 */

public class ImageLoaderUtil {

    public static final int PIC_LARGE = 0;
    public static final int PIC_MEDIUM = 1;
    public static final int PIC_SMALL = 2;

    public static final int LOAD_STRATEGY_NORMAL = 0;       //普通加载图片
    public static final int LOAD_STRATEGY_ONLY_WIFI = 1;    //只在wifi网络下载加载图片

    private static ImageLoaderUtil mInstance;
    private BaseImageLoaderStrategy mStrategy;

    public ImageLoaderUtil() {
        mStrategy = new GlideImageLoaderStrategy();
    }

    public static ImageLoaderUtil getInstance(){
        if(mInstance ==null){
            synchronized (ImageLoaderUtil.class){
                if(mInstance == null){
                    mInstance = new ImageLoaderUtil();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }

    /**
     * 默认加载图片方法
     */
    public void loadImage(Context context, ImageView view, String url) {
        ImageLoader loader = new ImageLoader.Builder()
                .imgView(view)
                .url(url)
                .build();

        loadImage(context, loader);
    }


    /**
     * 自定义 ImageLoader 来加载图片
     */
    public void loadImage(Context context, ImageLoader img) {
        mStrategy.loadImage(context, img);
    }

}
