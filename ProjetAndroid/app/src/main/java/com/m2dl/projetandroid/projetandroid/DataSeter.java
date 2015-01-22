package com.m2dl.projetandroid.projetandroid;

import android.app.Dialog;
import android.content.Intent;
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
import android.widget.Toast;

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

        Bundle extras = getIntent().getExtras();
        int startX = Integer.MIN_VALUE;

        if(extras != null) {
            startX= extras.getInt("StartX");
            Toast.makeText(getApplicationContext(),String.valueOf(startX), Toast.LENGTH_LONG).show();
        }

        
    }

    public void caracterize(View v) throws Exception {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popupview);
        //TextView txt = (TextView)dialog.findViewById(R.id.textbox);
        //txt.setText(getString(R.string.message));
        dialog.show();

        xmlPullParserHandler = new XMLPullParserHandler(getResources());

            String first = xmlPullParserHandler.getFirstNode();
            //childrens = new ArrayList<String>();
            childrens = xmlPullParserHandler.getChildrenNodes(first);

        System.out.println(childrens.size());

            if (childrens.size() > 0) {
                RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroup);
                RadioButton radioButton1 = (RadioButton) dialog.findViewById(R.id.keyradiobutton);
                RadioButton radioButton2 = (RadioButton) dialog.findViewById(R.id.keyradiobutton2);

                radioButton1.setText(childrens.get(0));
                radioButton2.setText(childrens.get(1));

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        int count = group.getChildCount();

                        for (int i = 0; i < count; i++) {
                            RadioButton o = (RadioButton) group.getChildAt(i);
                            if (checkedId == o.getId()) {
                                try {
                                    childrens = xmlPullParserHandler.getChildrenNodes(o.getText().toString());

                                    if (childrens.size() > 0) {
                                        ((RadioButton) group.getChildAt(0)).setText(childrens.get(0));
                                        ((RadioButton) group.getChildAt(1)).setText(childrens.get(1));
                                        o.setChecked(false);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
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
