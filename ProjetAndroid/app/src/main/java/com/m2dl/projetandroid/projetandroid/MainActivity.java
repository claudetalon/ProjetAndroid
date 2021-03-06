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

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.m2dl.projetandroid.projetandroid.SenderModule.ISenderModule;
import com.m2dl.projetandroid.projetandroid.SenderModule.SenderModuleGmail;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;

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

        File f=getFileStreamPath("userinfos.xml");
        setContentView(R.layout.registrationlayout);

        if (f.exists()){
            getUserSettings();
        }

        if(userName.matches("") || userMail.matches("")) setContentView(R.layout.registrationlayout);
        else{
            Intent i = new Intent(MainActivity.this, Camera.class);
            i.putExtra("Mail", userMail);
            startActivity(i);
        }

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

    public void getUserSettings()
    {
        //Initialisation du parser
        XmlPullParserFactory factory = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(new FileInputStream(getFileStreamPath("userinfos.xml"))));

            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG) {
                    String balise = xpp.getName();
                    xpp.next();
                    eventType = xpp.getEventType();

                    if (balise.matches("userName") && eventType == xpp.TEXT)
                        userName = xpp.getText();
                    else if (balise.matches("userMail") && eventType == xpp.TEXT)
                        userMail = xpp.getText();
                }

                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String setUserSettings(String name, String mail)
    {
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(getResources().openRawResource(R.raw.userinfos));

            //Set user name
            NodeList nodes = doc.getElementsByTagName("userName");
            nodes.item(0).setTextContent(name);

            //Set user mail
            nodes = doc.getElementsByTagName("userMail");
            nodes.item(0).setTextContent(mail);

            //Mettre le résultat dans la chaine de caractere output

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
            return output;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void register (View v)throws XmlPullParserException, IOException
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
        writeSettings(setUserSettings(userName, userMail),"userinfos.xml");
        Intent i = new Intent(MainActivity.this, Camera.class);
        startActivity(i);
        //finish();


    }

    public void writeSettings(String data, String filepath){
        FileOutputStream fOut = null;
        OutputStreamWriter osw = null;

        try{
            fOut = openFileOutput(filepath,MODE_PRIVATE);
            osw = new OutputStreamWriter(fOut);
            osw.write(data);
            osw.flush();
            //popup surgissant pour le résultat
            //Toast.makeText(getApplicationContext(), "Data saved",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Data not saved",Toast.LENGTH_SHORT).show();
        }
        finally {
            try {
                osw.close();
                fOut.close();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Error : Data not saved",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String readSettings(String filepath){
        FileInputStream fIn = null;
        InputStreamReader isr = null;

        char[] inputBuffer = new char[255];
        String data = null;

        try{
            fIn = getApplicationContext().openFileInput(filepath);
            isr = new InputStreamReader(fIn);
            isr.read(inputBuffer);
            data = new String(inputBuffer);
            //affiche le contenu de mon fichier dans un popup surgissant
            //Toast.makeText(getApplicationContext(), " "+data,Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Settings not read",Toast.LENGTH_SHORT).show();
        }
        return data;
    }


}