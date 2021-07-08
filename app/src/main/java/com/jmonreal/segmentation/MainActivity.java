package com.jmonreal.segmentation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void tomarFoto(View view){
        // -->Abrimos la camara
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 1);

            /*// --> Cambia a la siguiente actividad
            Intent verFoto = new Intent(MainActivity.this, VisualizarFoto.class);
            startActivity(verFoto);*/
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent verFoto = new Intent(MainActivity.this, VisualizarFoto.class);
        startActivity(verFoto);
    }
}