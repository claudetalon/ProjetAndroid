package com.m2dl.projetandroid.projetandroid;

import android.content.res.Resources;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Assyl on 21/01/2015.
 */
public class XMLPullParserHandler {

    XmlPullParserFactory _factory;
    XmlPullParser _xpp;
    Resources _resources;

    public XMLPullParserHandler(Resources resources) {
        try{
            _factory = XmlPullParserFactory.newInstance();
            _factory.setNamespaceAware(true);
            _xpp = _factory.newPullParser();
            _resources = resources;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFirstNode() throws XmlPullParserException, IOException {
        _xpp.setInput(new InputStreamReader(_resources.openRawResource(R.raw.caracterisationkeys)));
        int nbTagFound = 0;
        int eventType = _xpp.getEventType();

        if(eventType == XmlPullParser.END_DOCUMENT)
        {
            return "";
        }
        else
        {
            while(eventType != XmlPullParser.END_DOCUMENT || nbTagFound > 0)
            {
                if (eventType == XmlPullParser.START_TAG && nbTagFound == 0)
                {
                    nbTagFound++;
                    return _xpp.getName().toString();
                }
                _xpp.next();
            }

        }

        return  "";

    }

    public ArrayList<String> getChildrenNodes(String node) throws XmlPullParserException, IOException {
        _xpp.setInput(new InputStreamReader(_resources.openRawResource(R.raw.caracterisationkeys)));
        List<String> children= new ArrayList<>();
        int eventType = _xpp.getEventType();
        boolean inNode = false;

        while(eventType != XmlPullParser.END_DOCUMENT)
        {
            if (!inNode && eventType == XmlPullParser.START_TAG && _xpp.getName().toString().matches(node))
            {
                inNode = true;
            }
            else {
                if (inNode &&  eventType == XmlPullParser.START_TAG ) {
                    children.add(_xpp.getName().toString());
                }
                else if (eventType == XmlPullParser.END_TAG && _xpp.getName().toString().matches(node)) {
                    return  (ArrayList<String>) children;
                }
            }
            _xpp.next();
        }

        return  (ArrayList<String>) children;


    }

}