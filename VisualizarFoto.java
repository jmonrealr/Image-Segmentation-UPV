package com.jmonreal.segmentation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

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
        startActivity(cambiarVista);
    }
}
