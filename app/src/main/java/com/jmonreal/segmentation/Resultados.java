package com.jmonreal.segmentation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

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
                grabar();
                break;
        }
    }



    public void grabar() throws IOException {
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


// Write to SD Card
        /*try {
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + "/KMeans");
            dir.mkdirs();

            System.out.println("ruta: "+sdCard.getAbsolutePath());
            String fileName = String.format("%d.jpg", System.currentTimeMillis());
            File outFile = new File(dir, fileName);

            outStream = new FileOutputStream(outFile);
            this.resultado.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();

            Log.d(TAG, "onPictureTaken - wrote to " + outFile.getAbsolutePath());

        } catch (FileNotFoundException e) {
            System.out.println("FNF");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }*/
    }
}