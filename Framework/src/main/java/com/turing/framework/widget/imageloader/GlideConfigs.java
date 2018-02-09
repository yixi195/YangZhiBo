package com.turing.framework.widget.imageloader;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.module.GlideModule;
import com.turing.framework.utils.Constants;

import java.io.File;

/**
 * Glide配置
 * Created by YSL on 2017/5/2.
 */
public class GlideConfigs implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(new DiskCache.Factory() {  //自定义外部缓存
            @Override
            public DiskCache build() {
                File sdDir = null;
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    sdDir = Environment.getExternalStorageDirectory();
                }
                File cacheDir = new File(sdDir + File.separator + Constants.IMAGE_CACHE_DIR);
                if (!cacheDir.exists()){
                    cacheDir.mkdir();
                }
                return DiskLruCacheWrapper.get(cacheDir, DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE);
            }
        });
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
