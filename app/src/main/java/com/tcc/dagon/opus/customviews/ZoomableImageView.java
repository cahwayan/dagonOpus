package com.tcc.dagon.opus.customviews;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by cahwayan on 17/03/2017.
 */

public class ZoomableImageView extends AppCompatImageView {

    PhotoViewAttacher photoViewAttacher;

    public ZoomableImageView(Context context) {
        super(context);
        this.adicionarZoom();
    }

    public void adicionarZoom() {
        photoViewAttacher = new PhotoViewAttacher(this);
        photoViewAttacher.update();
    }


}
