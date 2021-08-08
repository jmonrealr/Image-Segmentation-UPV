package com.jmonreal.segmentation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private final int TOMAR_FOTO = 1;
    private final int SELECCIONAR_FOTO = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void tomarFoto(View view){
        // -->Abrimos la camara
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, TOMAR_FOTO);
        }
    }

    public void seleccionarImg(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Selecciona una imagen"), SELECCIONAR_FOTO);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent verFoto = new Intent(MainActivity.this, VisualizarFoto.class);

        if (requestCode == TOMAR_FOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            VisualizarFoto.setImgRecibida(imgBitmap);
        }
        else if (requestCode == SELECCIONAR_FOTO && resultCode == RESULT_OK) {
            Uri path = data.getData();
            VisualizarFoto.setImgSeleccionada(path);
        }
        startActivity(verFoto);
    }
}