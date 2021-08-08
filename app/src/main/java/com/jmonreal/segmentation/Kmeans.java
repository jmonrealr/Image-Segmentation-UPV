package com.jmonreal.segmentation;


import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import org.opencv.android.OpenCVLoader;

/**
 * Image segmentation using K-means clustering based on OpenCV
 * @see <a href="https://github.com/quickbirdstudios/opencv-android">OpenCV Android</a>
 */
public class Kmeans {

    /**
     * Initialize OpenCV
     */
    static {
        if (!OpenCVLoader.initDebug())
            Log.e("OpenCv", "Unable to load OpenCV");
        else
            Log.d("OpenCv", "OpenCV loaded");
    }

    private ImageView image;
    private Bitmap bitmap;

    public Kmeans() {
        this.image = null;
        this.bitmap = null;
    }

    public Kmeans(ImageView image) {
        this();
        this.image = image;
    }
    public Kmeans(Bitmap bitmap){
        this();
        this.bitmap = bitmap;
    }

}
