package com.m2dl.projetandroid.projetandroid;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.media.ExifInterface;
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

        //Création du fichier image
        File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);

        //On lance l'intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    public static Bitmap rotateImage(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
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

                    ContentResolver cr = getContentResolver();
                    try {
                        bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, selectedImage);
//
//                        BitmapFactory.Options bounds = new BitmapFactory.Options();
//                        bounds.inJustDecodeBounds = true;
//                        BitmapFactory.decodeFile(imageUri.toString(), bounds);
//
//                        BitmapFactory.Options opts = new BitmapFactory.Options();
//                        Bitmap bm = BitmapFactory.decodeFile(imageUri.toString(), opts);
//                        ExifInterface exif = new ExifInterface(imageUri.toString());
//                        String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
//                        int orientation = orientString != null ? Integer.parseInt(orientString) :  ExifInterface.ORIENTATION_NORMAL;
//
//                        int rotationAngle = 0;
//                        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
//                        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
//                        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
//
//                        bitmap = rotateImage(bitmap, rotationAngle);

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
