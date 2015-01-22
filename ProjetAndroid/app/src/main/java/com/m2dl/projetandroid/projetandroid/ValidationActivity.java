package com.m2dl.projetandroid.projetandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.m2dl.projetandroid.projetandroid.SenderModule.ISenderModule;
import com.m2dl.projetandroid.projetandroid.SenderModule.SenderModuleGmail;

import java.io.File;
import java.util.ArrayList;


public class ValidationActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrationlayout);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int count = group.getChildCount();

                for (int i=0;i<count;i++) {
                    RadioButton o = (RadioButton) group.getChildAt(i);
                    if (checkedId != o.getId()) o.setChecked(false);
                    else Toast.makeText(getApplicationContext(), o.getText() , Toast.LENGTH_LONG).show();
                    
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_validation, menu);

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


    //Fonction qui permet d'afficher une alerte et de rediriger vers le paramettrage du GPS dans le cas ou il n'est pas activé

    public void sendMail(View v)
    {
        Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
        else showSettingsAlert();
    }


    //Fonction qui permet d'afficher une alerte et de rediriger vers le paramettrage du GPS dans le cas ou il n'est pas activé
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ValidationActivity.this);

        alertDialog.setTitle("WIFI Settings Dialog");

        alertDialog.setMessage("The WIFI is not enabled. Would you like to go to the settings menu?");

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                ValidationActivity.this.startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    private class SendActivity extends AsyncTask<Object, Void, Void> {

        @Override
        protected void onPreExecute() {
           super.onPreExecute();
           Toast.makeText(getApplicationContext(), "Sending Mail...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Object... params) {
            LivingEntityData livingEntity = (LivingEntityData) params[0];
            String mail = (String) params[1];
            File f = livingEntity.getImg();
            ISenderModule mailSender = new SenderModuleGmail();
            mailSender.sendData("Biodiversity App - " + livingEntity.getName(), mail, livingEntity.toString(), f);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(getApplicationContext(), "Mail sent successfully", Toast.LENGTH_LONG).show();
        }
    }

}


