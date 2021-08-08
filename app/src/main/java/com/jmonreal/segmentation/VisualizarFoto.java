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

public class VisualizarFoto extends AppCompatActivity {

    private static int origen;
    private static final int TOMAR_FOTO = 1;
    private static final int SELECCIONAR_FOTO = 200;
    private ImageView imgView;
    private static Uri path;
    public static Bitmap imgRecibida = null;

    public static void setImgRecibida(Bitmap img){
        imgRecibida = img;
        origen = TOMAR_FOTO;
    }
    public static void setImgSeleccionada(Uri p){
        path = p;
        origen = SELECCIONAR_FOTO;
    }

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

    public void onClick(View view){
        Intent cambiarVista = null;

        System.out.println(view.getId());

        switch (view.getId()){
            case R.id.btnAtras:
                cambiarVista = new Intent(VisualizarFoto.this, MainActivity.class);
                break;
            case R.id.btnSiguiente:
                if(origen == TOMAR_FOTO) {
                    Resultados.setImgRecibida(this.imgRecibida);
                }
                else if(origen == SELECCIONAR_FOTO){
                    Resultados.setImgSeleccionada(this.path);
                }
                cambiarVista = new Intent(VisualizarFoto.this, Resultados.class);
                break;
        }
        startActivity(cambiarVista);
    }
}
