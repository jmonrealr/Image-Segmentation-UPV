package com.jmonreal.segmentation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import ru.bartwell.exfilepicker.ExFilePicker;

/**
 * Displays the main screen allowing you to take a picture or select it from the gallery.
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
     * Prepare the camera to take a picture
     * @param view view
     */
    public void tomarFoto(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, TOMAR_FOTO);
        }
    }

    /**
     * Start the image selector to load an image into the application.
     * @param view view
     */
    public void seleccionarImg(View view){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Selecciona una imagen"), SELECCIONAR_FOTO);
    }

    /**
     * Sends the captured or selected image to the following view
     * @param requestCode request type
     * @param resultCode result type
     * @param data response after intent
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
            Toast.makeText(this, String.valueOf(path), Toast.LENGTH_LONG).show();
            VisualizarFoto.setImgSeleccionada(path); // ...y la carga en la siguiente actividad.
            startActivity(verFoto); // Inicia la siguiente actividad

        }
        // <-- Comprobamos si la imagen fue tomada o selecccionada
    }

    /**
     *  Check if has permission for read external storage
     * @return Permission for Read External Storage
     */
    public boolean checkPermissionForReadExtertalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = getApplicationContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    /**
     * Request permission for read external Storage
     */
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