package com.m2dl.projetandroid.projetandroid;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Xml;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.InputStreamReader;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class MainActivity extends ActionBarActivity {

    String userName = "";
    String userMail = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(userName.matches("") || userMail.matches("")) setContentView(R.layout.registrationlayout);
        else setContentView(R.layout.activity_main);
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

    public void setUserSettings()
    {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(getResources().openRawResource(R.raw.userinfos));

            // Change the content of node
            Node nodes = doc.getElementsByTagName("userName").item(0);
            nodes.setTextContent(userName);
            nodes = doc.getElementsByTagName("userMail").item(0);
            nodes.setTextContent(userMail);


            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(
                    new File(getResources().getResourceName(R.raw.userinfos)));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        userName = fullNameET.getText().toString();
        userMail = emailET.getText().toString();

    }

}
