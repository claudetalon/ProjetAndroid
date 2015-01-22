package com.m2dl.projetandroid.projetandroid;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

            File f = new File(extras.getString("PictureFile"));
            Bitmap myBitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
            ImageView myImage = (ImageView) findViewById(R.id.imageData);
            myImage.setImageBitmap(myBitmap);

            //Set date
            TextView textV = (TextView) findViewById(R.id.datetextview);
            textV.setText("Date : " + date.toString());

        }
    }

    public void caracterize(View v) throws Exception {
        dialog = new Dialog(this);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
        {
            @Override
            public void onCancel(DialogInterface dialog)
            {
                selectedNode = null;
            }
        });

        dialog.setContentView(R.layout.popupview);
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
}