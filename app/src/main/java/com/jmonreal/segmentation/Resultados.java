package com.jmonreal.segmentation;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

/**
 * Muestra la imagen segmentada por KMeans
 */
public class Resultados extends AppCompatActivity {

    private ImageView imgInput;
    private ImageView imgPrediccion;
    private static Uri path;
    private static Bitmap imgRecibida = null;
    private static Bitmap resultado = null;
    private static int TOMAR_FOTO;
    private static int SELECCIONAR_FOTO;
    FileOutputStream outStream = null;

    public static void setTomarFoto(){
        TOMAR_FOTO = 1;
        SELECCIONAR_FOTO = 0;
    }
    public static void setSeleccionarFoto(){
        SELECCIONAR_FOTO = 1;
        TOMAR_FOTO = 0;
    }
    /**
     * Store the picture in this activity
     * @param img picture
     */
    public static void setImgRecibida(Bitmap img){
        imgRecibida = img;
    }

    /**
     * Store the picture in this activity
     * @param img picture
     */
    public static void setResultado(Bitmap img){
        resultado = img;
    }


    /**
     * Stores the image path in this activity
     * @param p Image path
     */
    public static void setImgSeleccionada(Uri p){
        path = p;
    }

    /**
     * Load image/photograph into an ImageView
     * Loads the preprocessed image into an ImageView
     * Load the image segmented by KMeans into an ImageView
     * @param savedInstanceState Bundle savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        ActivityCompat.requestPermissions(Resultados.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(Resultados.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultados);
        imgInput = findViewById(R.id.imgInput);
        if(TOMAR_FOTO == 1) {
            imgInput.setImageBitmap(imgRecibida);
            imgInput.refreshDrawableState();
        }
        else if(SELECCIONAR_FOTO == 1){
            imgInput.setImageURI(path);
        }
        imgPrediccion = findViewById(R.id.imgPrediccion);
        imgPrediccion.setImageBitmap(resultado);
        imgPrediccion.refreshDrawableState();
    }

    /**
     * Allows you to store the segmented image and return to the start of the application.
     * @param view view
     */
    public void onClick(View view) throws IOException {
        Intent cambiarVista = null;
        switch (view.getId()){
            case R.id.btnAtras:
                cambiarVista = new Intent(Resultados.this, MainActivity.class);
                startActivity(cambiarVista);
                break;
            case R.id.btnGuardar:
                saveToGallery(resultado);
                break;
        }
    }

    /**
     * Saves the image on the storage as .png
     * @param bmp Bitmap of the image
     */
    private void saveToGallery(Bitmap bmp){
        Bitmap bitmapImage = bmp.copy(bmp.getConfig(), true);
        FileOutputStream outputStream = null;
        File file = Environment.getExternalStorageDirectory();
        File dir = new File(file.getAbsolutePath());
        String filename = String.format("%d.png",System.currentTimeMillis());
        File outFile = new File(dir,filename);
        try{
            outputStream = new FileOutputStream(outFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        bitmapImage.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        try{
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            outputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}