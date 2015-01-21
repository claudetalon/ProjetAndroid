package com.m2dl.projetandroid.projetandroid;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.m2dl.projetandroid.projetandroid.SenderModule.ISenderModule;
import com.m2dl.projetandroid.projetandroid.SenderModule.SenderModuleGmail;

import java.io.File;


public class ValidationActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.validation);
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

    public void sendMail(View v)
    {

       SendActivity sendActivity = new SendActivity();
        sendActivity.execute();
    }

    private class SendActivity extends AsyncTask<Object, Void, Void> {

        @Override
        protected void onPreExecute() {
           super.onPreExecute();
           Toast.makeText(getApplicationContext(), "Pre Execute", Toast.LENGTH_LONG).show();
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
            Toast.makeText(getApplicationContext(), "Post Execute", Toast.LENGTH_LONG).show();
        }
    }
}


