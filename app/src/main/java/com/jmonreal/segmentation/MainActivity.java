package com.jmonreal.segmentation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Muestra la pantalla principal permitiendo tomar una fotografia o seleccionandola desde galeria
 */
public class MainActivity extends AppCompatActivity {

    private final int TOMAR_FOTO = 1;
    private final int SELECCIONAR_FOTO = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Prepara la camara para capturar una fotografia
     * @param view
     */
    public void tomarFoto(View view){
        // -->Abrimos la camara
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, TOMAR_FOTO);
        }
    }

    /**
     * Inicia el selector de imagenes para cargar una en la aplicación
     * @param view
     */
    public void seleccionarImg(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Selecciona una imagen"), SELECCIONAR_FOTO);
    }

    /**
     * Envia la imagen capturada o seleccionada a la sguiente vista
     * @param requestCode
     * @param resultCode
     * @param data Respuesta del Intent
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent verFoto = new Intent(MainActivity.this, VisualizarFoto.class);

        // --> Comprobamos si la imagen fue tomada o selecccionada
        if (requestCode == TOMAR_FOTO && resultCode == RESULT_OK) { // Si fue tomada...
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data"); // ... la almacena en un Bitmap ...
            VisualizarFoto.setImgRecibida(imgBitmap); // ...y la carga en la siguiente actividad.
        }
        else if (requestCode == SELECCIONAR_FOTO && resultCode == RESULT_OK) { // Si fue seleccionada
            Uri path = data.getData(); // ... obtiene el path de la imagen...
            VisualizarFoto.setImgSeleccionada(path); // ...y la carga en la siguiente actividad.
        }
        // <-- Comprobamos si la imagen fue tomada o selecccionada

        startActivity(verFoto); // Inicia la siguiente actividad
    }
}