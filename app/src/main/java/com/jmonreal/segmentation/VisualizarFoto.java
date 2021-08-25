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
 * Displays a preview of the loaded image
 */
public class VisualizarFoto extends AppCompatActivity {

    private static int origen;
    private static final int TOMAR_FOTO = 1;
    private static final int SELECCIONAR_FOTO = 200;
    private ImageView imgView;
    private static Uri path;
    public static Bitmap imgRecibida = null;

    /**
     * Store the picture in this activity
     * @param img Bitmap of the picture
     */
    public static void setImgRecibida(Bitmap img){
        imgRecibida = img;
        origen = TOMAR_FOTO;
    }

    /**
     * Stores the image path in this activity
     * @param p path of picture
     */
    public static void setImgSeleccionada(Uri p){
        path = p;
        origen = SELECCIONAR_FOTO;
    }

    /**
     * Load the picture into an ImageView
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
     * Allows you to continue with the application flow or return to select another image.
     * @param view viewOnClick
     */
    public void onClick(View view){
        Intent cambiarVista = null;
        switch (view.getId()){
            case R.id.btnAtras:
                cambiarVista = new Intent(VisualizarFoto.this, MainActivity.class);
                break;
            case R.id.btnSiguiente:
                if(origen == TOMAR_FOTO) {
                    Resultados.setTomarFoto();
                    Resultados.setImgRecibida(this.imgRecibida);
                }
                else if(origen == SELECCIONAR_FOTO){
                    Resultados.setSeleccionarFoto();
                    Resultados.setImgSeleccionada(this.path);
                }
                cambiarVista = new Intent(VisualizarFoto.this, Resultados.class);
                break;
        }

        Kmeans km = new Kmeans(this.imgView);
        Mat resultado = km.compute();

        //Convirtiendo a bitmap
        Bitmap bmp = null;
        Bitmap bm = Bitmap.createBitmap(resultado.cols(), resultado.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(resultado, bm);

        Resultados.setResultado(bm);
        startActivity(cambiarVista);
    }

}