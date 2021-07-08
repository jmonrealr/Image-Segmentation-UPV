package com.jmonreal.segmentation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class Resultados extends AppCompatActivity {

    ImageView imgInput;
    public static Bitmap imgRecibida = null;
    public static void setImgRecibida(Bitmap img){imgRecibida = img;}

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultados);
        imgInput = findViewById(R.id.imgInput);
        imgInput.setImageBitmap(imgRecibida);
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
