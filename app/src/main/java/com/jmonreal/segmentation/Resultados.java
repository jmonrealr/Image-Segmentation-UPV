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
     * Permite almacenar la imagen segmentada y volver al inicio de la aplicación
     * @param view
     */
    public void onClick(View view) throws IOException {
        Intent cambiarVista = null;

        System.out.println("ID: "+view.getId());

        switch (view.getId()){
            case R.id.btnAtras:
                System.out.println("Clickkkk salirrr");
                cambiarVista = new Intent(Resultados.this, MainActivity.class);
                startActivity(cambiarVista);
                break;
            case R.id.btnGuardar:

                imgPrediccion.setImageBitmap(imgRecibida);
                imgPrediccion.refreshDrawableState();
                saveToGallery(resultado);
                //grabar();

                //saveToInternalStorage(resultado);
                break;
        }
    }



/*    public void grabar() throws IOException {
        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath());
        System.out.println("ruta: "+filepath.getAbsolutePath());

        File file = new File(dir, System.currentTimeMillis()+".jpg");
        System.out.println("Nombre: "+System.currentTimeMillis()+".jpg");

        try{
            outStream = new FileOutputStream(file);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        this.imgRecibida.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        outStream.flush();
        outStream.close();
    }*/

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        // Create imageDir
        File mypath=new File(directory,System.currentTimeMillis()+".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            System.out.println("GUARDADO: " +mypath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }


    private void saveToGallery(Bitmap bitmapImage){

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