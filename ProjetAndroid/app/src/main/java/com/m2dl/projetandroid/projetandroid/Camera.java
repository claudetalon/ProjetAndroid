package com.m2dl.projetandroid.projetandroid;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by loic on 17/01/15.
 */
public class Camera extends ActionBarActivity {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    //private ImageView imageView;
    private Uri imageUri;
    CameraView cv;
    private Bitmap bitmap;

    //private ShapeDrawable mDrawable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_photo);
        //cv = new CameraView(this);
        //cv = (CameraView) findViewById(R.id.cameraView);
        //setContentView(cv);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        //mDrawable = new ShapeDrawable(new RectShape());

        //CrÃ©ation du fichier image
        File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);

        //On lance l'intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            //Si l'activité était une prise de photo
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getContentResolver().notifyChange(selectedImage, null);


                    cv = (CameraView) findViewById(R.id.cameraView);
                    setContentView(cv);

                    ContentResolver cr = getContentResolver();
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);

                        //imageView.setImageBitmap(bitmap);
                        cv.setImageBitmap(bitmap);


                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }

    public Context getContext() {
        return this;
    }
}
