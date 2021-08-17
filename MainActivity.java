package com.jmonreal.segmentation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.app.ActivityCompat;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import ru.bartwell.exfilepicker.ExFilePicker;
import ru.bartwell.exfilepicker.data.ExFilePickerResult;

/**
 * Muestra la pantalla principal permitiendo tomar una fotografia o seleccionandola desde galeria
 */
public class MainActivity extends AppCompatActivity {

    private final int TOMAR_FOTO = 1;
    private final int SELECCIONAR_FOTO = 200;
    private static final int EX_FILE_PICKER_RESULT = 0;
    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!checkPermissionForReadExtertalStorage()) {
            requestPermissionForReadExtertalStorage();
        }
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
        /*ExFilePicker exFilePicker = new ExFilePicker();
            exFilePicker.setCanChooseOnlyOneItem(true);
            exFilePicker.setShowOnlyExtensions("jpg", "jpeg");
            exFilePicker.setExceptExtensions("jpg");
            exFilePicker.setNewFolderButtonDisabled(true);
            exFilePicker.setSortButtonDisabled(true);
            exFilePicker.setQuitButtonEnabled(true);
            exFilePicker.setSortingType(ExFilePicker.SortingType.NAME_DESC);
            exFilePicker.setUseFirstItemAsUpEnabled(true);
            exFilePicker.setChoiceType(ExFilePicker.ChoiceType.FILES);
            exFilePicker.setChoiceType(ExFilePicker.ChoiceType.DIRECTORIES);
            exFilePicker.start(this, EX_FILE_PICKER_RESULT);*/


        /*ExFilePicker exFilePicker = new ExFilePicker();
        exFilePicker.start(this, EX_FILE_PICKER_RESULT);*/

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
            startActivity(verFoto); // Inicia la siguiente actividad
        }
        else if (requestCode == SELECCIONAR_FOTO && resultCode == RESULT_OK) { // Si fue seleccionada
            Uri path = data.getData(); // ... obtiene el path de la imagen...
            VisualizarFoto.setImgSeleccionada(path); // ...y la carga en la siguiente actividad.
            startActivity(verFoto); // Inicia la siguiente actividad

        }
        /*else if (requestCode == EX_FILE_PICKER_RESULT) {
            ExFilePickerResult result = ExFilePickerResult.getFromIntent(data);
            if (result != null && result.getCount() > 0) {
            // Here is object contains selected files names and path
            Log.i("folderLocation", result.getPath() + result.getNames().get(0));

            Mat srcMat1 = Imgcodecs.imread(result.getPath() + result.getNames().get(0));
            if (srcMat1.empty()) {
                return;
            }

            System.out.println("=====Imagen seleccionada usando: "+srcMat1.toString());

            }
        }*/


        // <-- Comprobamos si la imagen fue tomada o selecccionada
    }

    public boolean checkPermissionForReadExtertalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = getApplicationContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    public void requestPermissionForReadExtertalStorage() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                READ_STORAGE_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case READ_STORAGE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");

                    ExFilePicker exFilePicker = new ExFilePicker();
                    exFilePicker.start(this, EX_FILE_PICKER_RESULT);
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                    requestPermissionForReadExtertalStorage();
                }
                break;
        }
    }
}