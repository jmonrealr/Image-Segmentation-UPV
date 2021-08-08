package com.jmonreal.segmentation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class Resultados extends AppCompatActivity {

    private ImageView imgInput;
    private static Uri path;
    private static Bitmap imgRecibida = null;

    public static void setImgRecibida(Bitmap img){
        imgRecibida = img;
    }
    public static void setImgSeleccionada(Uri p){
        path = p;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultados);
        imgInput = findViewById(R.id.imgInput);
        if(imgRecibida != null) {
            imgInput.setImageBitmap(imgRecibida);
        }
        else{
            imgInput.setImageURI(path);
        }
    }

    public void onClick(View view){
        Intent cambiarVista = null;

        System.out.println(view.getId());

        switch (view.getId()){
            case R.id.btnAtras:
                cambiarVista = new Intent(Resultados.this, MainActivity.class);
                startActivity(cambiarVista);
                break;
            case R.id.btnSiguiente:
                //cambiarVista = new Intent(VisualizarFoto.this, Resultados.class);
                break;
        }
        //startActivity(cambiarVista);
    }
}
