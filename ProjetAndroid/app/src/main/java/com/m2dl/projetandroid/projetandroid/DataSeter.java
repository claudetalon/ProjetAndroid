package com.m2dl.projetandroid.projetandroid;

import android.app.Dialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DataSeter extends ActionBarActivity {

    private String selectedNode = null;
    List<String> childrens;
    XMLPullParserHandler xmlPullParserHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_seter);



        LinearLayout ll = (LinearLayout) this.findViewById(R.id.linearLayout);
        // Array of choices
        String colors[] = {"Red","Blue","White","Yellow","Black", "Green","Purple","Orange","Grey"};

        // Selection of the spinner
        Spinner spinner = new Spinner(this);

        // Application of the Array to the Spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, colors);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);
        ll.addView(spinner);
        
    }

    public void caracterize(View v) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popupview);
        //TextView txt = (TextView)dialog.findViewById(R.id.textbox);
        //txt.setText(getString(R.string.message));
        dialog.show();

        xmlPullParserHandler = new XMLPullParserHandler(getResources());
        try {
            String first = xmlPullParserHandler.getFirstNode();
            //childrens = new ArrayList<String>();
            childrens = xmlPullParserHandler.getChildrenNodes(first);

            RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroup);
            RadioButton radioButton1 = (RadioButton) dialog.findViewById(R.id.keyradiobutton);
            RadioButton radioButton2 = (RadioButton) dialog.findViewById(R.id.keyradiobutton2);

            radioButton1.setText(childrens.get(0));
            radioButton2.setText(childrens.get(1));

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // checkedId is the RadioButton selected
                    //System.out.println(checkedId);
                    int count = radioGroup.getChildCount();

                    for (int i = 0; i < count; i++) {
                        RadioButton o = (RadioButton) group.getChildAt(i);
                        if (checkedId == o.getId()) {
                            try {
                                childrens = xmlPullParserHandler.getChildrenNodes(o.getText().toString());

                                if (childrens.size() > 0) {
                                    radioButton1.setText(childrens.get(0));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        XMLPullParserHandler xmlPullParserHandler = new XMLPullParserHandler(getResources());
        try {
            String firstNode = xmlPullParserHandler.getFirstNode();
            RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // checkedId is the RadioButton selected
                    System.out.println(checkedId);
                }
            });
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public void send(View v) {

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
}
