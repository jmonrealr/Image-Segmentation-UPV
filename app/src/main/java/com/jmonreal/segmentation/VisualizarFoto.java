package com.jmonreal.segmentation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.opencv.android.Utils;
import org.opencv.core.CvException;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

/**
 * Muestra una vista previa de la imagen cargada
 */
public class VisualizarFoto extends AppCompatActivity {

    private static int origen;
    private static final int TOMAR_FOTO = 1;
    private static final int SELECCIONAR_FOTO = 200;
    private ImageView imgView;
    private static Uri path;
    public static Bitmap imgRecibida = null;

    /**
     * Almacena la fotografia en esta actividad
     * @param img Fotografia
     */
    public static void setImgRecibida(Bitmap img){
        imgRecibida = img;
        origen = TOMAR_FOTO;
    }

    /**
     * Almacena la ruta de la imagen en esta actividad
     * @param p Path de imagen
     */
    public static void setImgSeleccionada(Uri p){
        path = p;
        origen = SELECCIONAR_FOTO;
    }

    /**
     * Carga la imagen/fotografia en un ImageView
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagen_seleccionada);
        imgView = findViewById(R.id.imgView);
        if(origen == TOMAR_FOTO) {
            imgView.setImageBitmap(imgRecibida);
        }
        else if (origen == SELECCIONAR_FOTO){
            imgView.setImageURI(path);
        }
    }

    /**
     * Permite continuar con el flujo de la aplicación o volver para seleccionar otra imagen
     * @param view
     */
    public void onClick(View view){
        Intent cambiarVista = null;

        System.out.println(view.getId());

        switch (view.getId()){
            case R.id.btnAtras:
                cambiarVista = new Intent(VisualizarFoto.this, MainActivity.class);
                break;
            case R.id.btnSiguiente:
                if(origen == TOMAR_FOTO) {
                    //Kmeans km = new Kmeans(this.imgRecibida);
                    Resultados.setImgRecibida(this.imgRecibida);
                }
                else if(origen == SELECCIONAR_FOTO){
                    Resultados.setImgSeleccionada(this.path);
                    //Kmeans km = new Kmeans(this.path); //Aca envio el path con la esperanza de que jale pero nop


                }
                cambiarVista = new Intent(VisualizarFoto.this, Resultados.class);
                break;
        }
        Kmeans km = new Kmeans(this.imgView); //Aca envio el imageView con la esperanza de que jale pero nop
        Mat resultado = km.compute();
        //Convirtiendo a bitmap
        Bitmap bmp = null;

        Bitmap bm = Bitmap.createBitmap(resultado.cols(), resultado.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(resultado, bm);

        Resultados.setResultado(bm);

        /*Mat rgb = new Mat();
        Imgproc.cvtColor(resultado, rgb, Imgproc.COLOR_BGR2RGB);*/

        /*try {
            bmp = Bitmap.createBitmap(rgb.cols(), rgb.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(rgb, bmp);
            Resultados.setResultado(bmp);
            //imgView.setImageBitmap(bmp);
        }
        catch (CvException e){
            Log.d("Exception",e.getMessage());
        }*/


        /*Mat tmp = new Mat (this.imgRecibida.getHeight(), this.imgRecibida.getWidth(), CvType.CV_8U, new Scalar(4));
        try {
            //Imgproc.cvtColor(seedsImage, tmp, Imgproc.COLOR_RGB2BGRA);
            Imgproc.cvtColor(resultado, tmp, Imgproc.COLOR_GRAY2RGBA, 3);
            bmp = Bitmap.createBitmap(tmp.cols(), tmp.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(tmp, bmp);
            imgView.setImageBitmap(bmp);
        }catch (CvException e){
            Log.d("Exception",e.getMessage());}*/



        /*try {
            //Imgproc.cvtColor(seedsImage, tmp, Imgproc.COLOR_RGB2BGRA);
            //Imgproc.cvtColor(seedsImage, resultado, Imgproc.COLOR_GRAY2RGBA, 4);
            bmp = Bitmap.createBitmap(resultado.cols(), resultado.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(resultado, bmp);
            imgView.setImageBitmap(bmp);
        }catch (CvException e){
            Log.d("Exception",e.getMessage());}*/


        startActivity(cambiarVista);
    }
}
