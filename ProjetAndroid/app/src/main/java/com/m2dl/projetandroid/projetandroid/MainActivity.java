package com.m2dl.projetandroid.projetandroid;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrationlayout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void register(View v)
    {
        EditText fullNameET = (EditText) findViewById(R.id.reg_fullname);
        EditText emailET = (EditText) findViewById(R.id.reg_email);


        if (fullNameET.getText().toString().matches(""))
        {
            Toast toast= Toast.makeText(getApplicationContext(), "\"Name\" field can't be empty!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
            View view = toast.getView();
            view.setBackgroundResource(android.R.color.holo_red_light);
            toast.show();
            return;
        }

        if(!isValidEmail(emailET.getText().toString()))
        {
            Toast toast= Toast.makeText(getApplicationContext(), "The e-mail adress you entered is incorrect!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
            View view = toast.getView();
            view.setBackgroundResource(android.R.color.holo_red_light);
            toast.show();
            return;
        }

        setContentView(R.layout.accueil);
    }
}
