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
                eventType = _xpp.getEventType();
            }

        }

        return  "";

    }

    public ArrayList<String> getChildrenNodes(String node) throws XmlPullParserException, IOException {
        _xpp.setInput(new InputStreamReader(_resources.openRawResource(R.raw.caracterisationkeys)));
        List<String> children= new ArrayList<>();
        int eventType = _xpp.getEventType();
        boolean inNode = false;
        String currentChild = "";

        while(eventType != XmlPullParser.END_DOCUMENT)
        {
            if (!inNode && eventType == XmlPullParser.START_TAG && _xpp.getName().toString().matches(node))
            {
                inNode = true;
            }
            else {
                if (inNode &&  eventType == XmlPullParser.START_TAG ) {
                    if (currentChild.matches(""))  {
                        children.add(_xpp.getName().toString());
                        currentChild = _xpp.getName().toString();
                    }
                }
                else if (inNode &&  eventType == XmlPullParser.END_TAG)
                {
                    if (_xpp.getName().toString().matches(node))  return  (ArrayList<String>) children;
                    else if (_xpp.getName().toString().matches(currentChild)) currentChild = "";
                }
            }
            _xpp.next();
            eventType = _xpp.getEventType();
        }

        return  (ArrayList<String>) children;


    }

}