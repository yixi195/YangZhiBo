package com.turing.framework.widget.imageloader;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.stream.StreamModelLoader;
import com.turing.framework.utils.NetWorkUtil;

import java.io.IOException;
import java.io.InputStream;

public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {

    @Override
    public void loadImage(Context context, ImageLoader imageLoader) {
        int strategy = imageLoader.getWifiStrategy();
        if (strategy == ImageLoaderUtil.LOAD_STRATEGY_NORMAL) {
            loadNormal(context, imageLoader);
            return;
        }

        if (NetWorkUtil.isWifiConnected(context) && strategy == ImageLoaderUtil.LOAD_STRATEGY_ONLY_WIFI) {
            loadNormal(context, imageLoader);
        } else {
            loadCache(context, imageLoader);
        }
    }

    /**
     * load image with Glide
     */
    private void loadNormal(Context context, ImageLoader imageLoader) {
        Glide.with(context).load(imageLoader.getUrl()).placeholder(imageLoader.getPlaceHolder()).into(imageLoader.getImgView());
    }

    /**
     * load cache image with Glide
     */
    private void loadCache(Context context, ImageLoader imageLoader) {

        //缓存策略解说：
        //all:缓存源资源和转换后的资源
        //none:不作任何磁盘缓存
        //source:缓存源资源
        //result：缓存转换后的资源
        Glide.with(context)
                .using(new StreamModelLoader<String>() {
                    @Override
                    public DataFetcher<InputStream> getResourceFetcher(final String model, int width, int height) {
                        return new DataFetcher<InputStream>() {
                            @Override
                            public InputStream loadData(Priority priority) throws Exception {
                                throw new IOException();
                            }

                            @Override
                            public void cleanup() {

                            }

                            @Override
                            public String getId() {
                                return model;
                            }

                            @Override
                            public void cancel() {

                            }
                        };
                    }
                }).load(imageLoader.getUrl()).placeholder(imageLoader.getPlaceHolder()).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageLoader.getImgView());
    }
}
