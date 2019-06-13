package com.xxc.dev.image;

import com.bumptech.glide.request.RequestOptions;
import com.xxc.dev.image.glide.GlideImageOption;

public class ConfigFactory {

    private static final ConfigFactory INSTANCE = new ConfigFactory();

    public static ConfigFactory getInstance() {
        return INSTANCE;
    }

    private ConfigFactory() {
    }

    public ImageConfig getGlideConfig(RequestOptions options) {
        return new GlideImageOption().setOption(options);
    }

}
