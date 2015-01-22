package com.m2dl.projetandroid.projetandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.m2dl.projetandroid.projetandroid.SenderModule.ISenderModule;
import com.m2dl.projetandroid.projetandroid.SenderModule.SenderModuleGmail;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DataSeter extends ActionBarActivity {

    private String selectedNode = null;
    List<String> childrens;
    XMLPullParserHandler xmlPullParserHandler;
    Context context = this;
    Dialog dialog;
    LivingEntityData livingEntity;
    String userMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_seter);

        LinearLayout ll = (LinearLayout) this.findViewById(R.id.linearLayout);
        Date date = new Date();

        Bundle extras = getIntent().getExtras();
        livingEntity = new LivingEntityData();

        if(extras != null) {
            livingEntity.setRectCoordx1(extras.getInt("StartX"));
            livingEntity.setRectCoordx2(extras.getInt("EndX"));
            livingEntity.setRectCoordy1(extras.getInt("StartY"));
            livingEntity.setRectCoordy2(extras.getInt("EndY"));
            livingEntity.setImg(new File(extras.getString("PictureFile")));
            livingEntity.setDate(date);
            livingEntity.setGPSLatitude(extras.getDouble("latitude"));
            livingEntity.setGPSLongitude(extras.getDouble("longitude"));
            livingEntity.setUserMail(extras.getString("Mail"));

            File f = new File(extras.getString("PictureFile"));
            Bitmap myBitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
            ImageView myImage = (ImageView) findViewById(R.id.imageData);
            myImage.setImageBitmap(myBitmap);

            //Set date
            TextView textV = (TextView) findViewById(R.id.datetextview);
            textV.setText("Date : " + date.toString());

            //Set coord
            textV = (TextView) findViewById(R.id.coordtextview);
            textV.setText("Location : [ latitude = " + livingEntity.getGPSLatitude() + ", longitude = " + livingEntity.getGPSLongitude() + "]");

        }
    }

    public void caracterize(View v) throws Exception {
        dialog = new Dialog(this);

        dialog.setContentView(R.layout.popupview);

        Button val = (Button) dialog.findViewById(R.id.validerbutton);
        val.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(context, selectedNode, Toast.LENGTH_LONG).show();
            }
        });

        Button ann = (Button) dialog.findViewById(R.id.annulerbutton);
        ann.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectedNode = null;
                Toast.makeText(context, selectedNode, Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
        {
            @Override
            public void onCancel(DialogInterface dialog)
            {
                Toast.makeText(context, selectedNode, Toast.LENGTH_LONG).show();
                selectedNode = null;
            }
        });

        dialog.show();

        xmlPullParserHandler = new XMLPullParserHandler(getResources());

        String first = xmlPullParserHandler.getFirstNode();
        childrens = xmlPullParserHandler.getChildrenNodes(first);

        if (childrens.size() > 0) {
            final RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroup);

            for (int i = 0; i < childrens.size(); i++) {
                RadioButton b = new RadioButton(this);
                radioGroup.addView(b);
                b.setText(childrens.get(i));
            }

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    int count = group.getChildCount();

                    boolean stop = false;

                    for (int i = 0; i < count && !stop; i++) {
                        RadioButton o = (RadioButton) group.getChildAt(i);

                        if (checkedId == o.getId()) {
                            selectedNode = o.getText().toString();

                            //Toast.makeText(context, selectedNode, Toast.LENGTH_LONG).show();

                            try {
                                childrens = xmlPullParserHandler.getChildrenNodes(o.getText().toString());

                                if (childrens.size() > 0) {
                                    radioGroup.removeAllViews();
                                    for (int j = 0; j < childrens.size(); j++) {
                                        RadioButton b = new RadioButton(context);
                                        radioGroup.addView(b);
                                        b.setText(childrens.get(j));
                                    }
                                }
                                stop = true;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        }
    }

    public void valider(View v) {
        dialog.dismiss();
        //Toast.makeText(context, selectedNode, Toast.LENGTH_LONG).show();
    }

    public void annuler(View v) {
        selectedNode = null;
        dialog.dismiss();
        //Toast.makeText(context, selectedNode, Toast.LENGTH_LONG).show();
    }

    public void send(View v) {
        EditText textView = (EditText)  findViewById(R.id.nameET);
        livingEntity.setName(textView.getText().toString());
        textView = (EditText)  findViewById(R.id.comET);
        livingEntity.setComment(textView.getText().toString());
        livingEntity.setNode("Plant");

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            SendActivity send = new SendActivity();
            send.execute(livingEntity);
        }
        else showSettingsAlert();
    }

    //Fonction qui permet d'afficher une alerte et de rediriger vers le paramettrage du GPS dans le cas ou il n'est pas activé
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DataSeter.this);

        alertDialog.setTitle("WIFI Settings Dialog");

        alertDialog.setMessage("The WIFI is not enabled. Would you like to go to the settings menu?");

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                DataSeter.this.startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data_seter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getSelectedNode() {
        return selectedNode;
    }

    //Permet l'envoi d'un mail en arriere plan
    private class SendActivity extends AsyncTask<LivingEntityData, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Sending Mail...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(LivingEntityData... params) {
            LivingEntityData livingEntity = (LivingEntityData) params[0];
            File f = livingEntity.getImg();
            ISenderModule mailSender = new SenderModuleGmail();
            mailSender.sendData("Biodiversity App - " + livingEntity.getName(), livingEntity.getUserMail(), livingEntity.toString(), f);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(getApplicationContext(), "Mail sent successfully", Toast.LENGTH_LONG).show();
        }
    }

}