package com.jmonreal.segmentation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class VisualizarFoto extends AppCompatActivity {

    ImageView imgView;

    public static Bitmap imgRecibida = null;

    public static void setImgRecibida(Bitmap img){
        imgRecibida = img;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagen_seleccionada);
        imgView = findViewById(R.id.imgView);
        imgView.setImageBitmap(imgRecibida);
    }

    public void onClick(View view){
        Intent cambiarVista = null;

        System.out.println(view.getId());

        switch (view.getId()){
            case R.id.btnAtras:
                cambiarVista = new Intent(VisualizarFoto.this, MainActivity.class);
                break;
            case R.id.btnSiguiente:
                Resultados.setImgRecibida(this.imgRecibida);
                cambiarVista = new Intent(VisualizarFoto.this, Resultados.class);
                break;
        }
        startActivity(cambiarVista);
    }
}
