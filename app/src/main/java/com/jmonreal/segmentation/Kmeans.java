package com.jmonreal.segmentation;


import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

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
            Log.d("OpenCv", "OpenCV loaded" + Core.VERSION);
    }

    private ImageView image;
    private Bitmap bitmap;
    private int K = 5 ;

    /**
     * Constructor initialize all properties
     */
    public Kmeans() {
        this.image = null;
        this.bitmap = null;
    }

    /**
     * Constructor initialize
     * @param image ImageView
     */
    public Kmeans(ImageView image) {
        this();
        this.image = image;
    }

    /**
     * Constructor initialize
     * @param bitmap Bitmap
     */
    public Kmeans(Bitmap bitmap){
        this();
        this.bitmap = bitmap;
    }

    /**
     * Start the Image Segmentation using K-means clustering
     */
    void compute(){
        System.out.println(System.getProperty("user.dir"));
        String directory = System.getProperty("user.dir");
        Mat image = Imgcodecs.imread(this.image.toString());
        //rearrange data into a long vertical strip (to float, reshape channels into columns)
        image.convertTo(image, CvType.CV_32F);
        Mat data = image.reshape(1, (int) image.total());
        if (!image.empty()){
            Mat label = new Mat();
            TermCriteria criteria = new TermCriteria();
            int attempts = 5;
            int flags = Core.KMEANS_PP_CENTERS;
            Mat centers = new Mat();
            double compactness = Core.kmeans(data, this.K, label, criteria, attempts, flags, centers);
            Mat draw = new Mat( (int) image.total(), 1, CvType.CV_32FC3);
            Mat colors = centers.reshape(3, this.K);
            for (int i = 0; i < K; i++) {
                Mat mask = new Mat(); // mas for each cluster label
                Core.compare(label, new Scalar(i), mask, Core.CMP_EQ);
                Mat col = colors.row(i);
                double d[] = col.get(0, 0);
                draw.setTo(new Scalar(d[0], d[1], d[2]), mask);

            }
            draw = draw.reshape(3, image.rows());
            draw.convertTo(draw, CvType.CV_8U);
            Imgcodecs.imwrite(directory + "final" + ".jpg", draw);

        }else {
            throw new NullPointerException("IMAGE IS EMPTY!");
        }
    }

}
