package com.jmonreal.segmentation;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
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
    private static Uri path;
    private int K = 5 ;
    Mat draw;

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
        compute();
    }

    public Kmeans(Uri p){
        this();
        this.path = p;
        compute();
    }


    /**
     * Start the Image Segmentation using K-means clustering
     */
    Mat compute(){
        System.out.println(System.getProperty("user.dir"));
        //String directory = System.getProperty("user.dir");
        String directory = "/storage/emulated/C81B-9CFA/";

        //Mat imagef = new Mat (this.bitmap.getHeight(), this.bitmap.getWidth(), CvType.CV_8UC1);

        //Mat imagef = Imgcodecs.imread(this.image.toString()); //Aca uso el imageView pero no jala
        //Mat imagef = Imgcodecs.imread(this.bitmap.toString());
        //Mat imagef = Imgcodecs.imread(this.path.getPath()); //Aca uso el path pero no jala 
        //System.out.println("Imagen seleccionada: "+ this.path.getPath());
        //rearrange data into a long vertical strip (to float, reshape channels into columns)
        //imagef.convertTo(imagef, CvType.CV_32F);


        this.image.invalidate();
        BitmapDrawable drawable = (BitmapDrawable) this.image.getDrawable();
        Bitmap btm = drawable.getBitmap();
        Mat imagef = new Mat (btm.getHeight(), btm.getWidth(), CvType.CV_32F);

        /*Mat imagef = new Mat();
        Bitmap bmp32 = btm.copy(Bitmap.Config.ARGB_8888, true);
        Utils.bitmapToMat(bmp32, imagef);*/


        Mat data = imagef.reshape(1, (int) imagef.total());
        if (!imagef.empty()){
            Mat label = new Mat();
            TermCriteria criteria = new TermCriteria();
            int attempts = 5;
            int flags = Core.KMEANS_PP_CENTERS;
            Mat centers = new Mat();
            double compactness = Core.kmeans(data, this.K, label, criteria, attempts, flags, centers);
            /*Mat*/ draw = new Mat( (int) imagef.total(), 1, CvType.CV_32FC3);
            //Mat colors = centers.reshape(3, this.K);
            for (int i = 0; i < K; i++) {
                Mat mask = new Mat(); // mas for each cluster label
                Core.compare(label, new Scalar(i), mask, Core.CMP_EQ);
                //Mat col = colors.row(i);
                //double d[] = col.get(0, 0);
                //draw.setTo(new Scalar(d[0], d[1], d[2]), mask);

            }
            draw = draw.reshape(3, imagef.rows());
            draw.convertTo(draw, CvType.CV_8U);
            Imgcodecs.imwrite(directory + "final" + ".jpg", draw);
            System.out.println("======Salida: "+directory + "final" + ".jpg");
        }else {
            throw new NullPointerException("IMAGE IS EMPTY!");
        }
        return draw;
    }

}
