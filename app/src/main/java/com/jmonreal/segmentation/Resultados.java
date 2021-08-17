package com.jmonreal.segmentation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Muestra la imagen segmentada por KMeans
 */
public class Resultados extends AppCompatActivity {

    private ImageView imgInput;
    private ImageView imgPrediccion;
    private static Uri path;
    private static Bitmap imgRecibida = null;
    private static Bitmap resultado = null;

    /**
     * Almacena la fotografia en esta actividad
     * @param img Fotografia
     */
    public static void setImgRecibida(Bitmap img){
        imgRecibida = img;
    }

    /**
     * Almacena la fotografia en esta actividad
     * @param img Fotografia
     */
    public static void setResultado(Bitmap img){
        resultado = img;
    }


    /**
     * Almacena la ruta de la imagen en esta actividad
     * @param p Path de imagen
     */
    public static void setImgSeleccionada(Uri p){
        path = p;
    }

    /**
     * Carga la imagen/fotografia en un ImageView
     * Carga la imagen preprocesada en un ImageView
     * Carga la imagen segmentada por KMeans en un ImageView
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultados);
        imgInput = findViewById(R.id.imgInput);
        if(imgRecibida != null) {
            imgInput.setImageBitmap(imgRecibida);
            imgInput.refreshDrawableState();
        }
        else{
            imgInput.setImageURI(path);
        }
        imgPrediccion = findViewById(R.id.imgPrediccion);
        imgPrediccion.setImageBitmap(resultado);
        imgPrediccion.refreshDrawableState();
    }

    /**
     * Permite almacenar la imagen segmentada y volver al inicio de la aplicación
     * @param view
     */
    public void onClick(View view){
        Intent cambiarVista = null;

        System.out.println(view.getId());

        switch (view.getId()){
            case R.id.btnAtras:
                cambiarVista = new Intent(Resultados.this, MainActivity.class);
                startActivity(cambiarVista);
                break;
            case R.id.btnSiguiente:
                imgPrediccion.setImageBitmap(imgRecibida);
                //imgPrediccion.setImageBitmap(resultado);
                imgPrediccion.refreshDrawableState();
                break;
        }
    }
}