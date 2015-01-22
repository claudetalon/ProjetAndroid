/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    File photo;
    String userMail = "";

    //private ShapeDrawable mDrawable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_photo);
        takePhoto();
        Bundle extras = getIntent().getExtras();
        userMail = extras.getString("Mail");
    }

    private void takePhoto() {
        //cv = new CameraView(this);
        //cv = (CameraView) findViewById(R.id.cameraView);
        //setContentView(cv);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        //mDrawable = new ShapeDrawable(new RectShape());

        //CrÃ©ation du fichier image
        photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
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
            //Si l'activitÃ© Ã©tait une prise de photo
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getContentResolver().notifyChange(selectedImage, null);


                    cv = (CameraView) findViewById(R.id.cameraView);

                    ContentResolver cr = getContentResolver();
                    try {
                        bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, selectedImage);

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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_photo, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.photo:
                takePhoto();
                return true;
            case R.id.validate:
                Intent i = new Intent(Camera.this, DataSeter.class);
                GPSTracker gpsTracker = new GPSTracker(Camera.this);


                    if (gpsTracker.isGpsActive())
                    {
                        i.putExtra("longitude", gpsTracker.getLongitude());
                        i.putExtra("latitude", gpsTracker.getLatitude());
                        i.putExtra("StartX", cv.getStartX());
                        i.putExtra("StartY", cv.getStartY());
                        i.putExtra("EndX", cv.getEndX());
                        i.putExtra("EndY", cv.getEndY());
                        i.putExtra("PictureFile", photo.getPath());
                        i.putExtra("Mail", userMail);
                        startActivity(i);
                    }
                else
                    {
                        gpsTracker.showSettingsAlert();
                    }
                return true;
        }
        return false;
    }

    public Context getContext() {
        return this;
    }
}
