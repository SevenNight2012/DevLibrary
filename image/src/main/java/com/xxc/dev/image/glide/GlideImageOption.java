package com.xxc.dev.image.glide;

import com.bumptech.glide.request.RequestOptions;
import com.xxc.dev.image.ImageConfig;

public class GlideImageOption extends ImageConfig<RequestOptions> {

    public static RequestOptions defaultOption() {
        return new RequestOptions();
    }

}
