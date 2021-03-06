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

import android.media.Image;

import java.io.File;
import java.util.Date;

/**
 * Created by ctalon on 17/01/15.
 */
public class LivingEntityData {


    private int id;
    private String name;
    private File img;
    private double GPSLatitude;

    public void setGPSLongitude(double GPSLongitude) {
        this.GPSLongitude = GPSLongitude;
    }

    public void setGPSLatitude(double GPSLatitude) {
        this.GPSLatitude = GPSLatitude;
    }

    private double GPSLongitude;
    private int rectCoordx1;
    private int rectCoordx2;
    private int rectCoordy1;
    private int rectCoordy2;
    private Date date;
    private String node;
    private String comment;
    private  String userMail;

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    @Override
    public String toString() {
        return "Metadatas {" +
                "id =" + id +
                ", name ='" + name + '\'' +
                ", Location (Latitude, Longitude) = (" + GPSLatitude + ", " + GPSLongitude + ")" +
                ", Interest Area (Rectange [x1, x2, y1, y2]) = [" + rectCoordx1 + ", "  + rectCoordx2 + ", "  + rectCoordy1 + ", "  + rectCoordy2 +"]"+
                ", date =" + date +
                ", node ='" + node + '\'' +
                ", comment ='" + comment + '\'' +
                '}';
    }

    public void setGPSLatitude(float GPSLatitude) {
        this.GPSLatitude = GPSLatitude;
    }

    public File getImg() {
        return img;
    }

    public void setImg(File img) {
        this.img = img;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public Date getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }

    public String getName() {
        return name;
    }

    public String getNode() {
        return node;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public double getGPSLatitude() {
        return GPSLatitude;
    }

    public double getGPSLongitude() {
        return GPSLongitude;
    }

    public float getRectCoordx1() {
        return rectCoordx1;
    }

    public void setRectCoordx1(int rectCoordx1) {
        this.rectCoordx1 = rectCoordx1;
    }

    public float getRectCoordx2() {
        return rectCoordx2;
    }

    public void setRectCoordx2(int rectCoordx2) {
        this.rectCoordx2 = rectCoordx2;
    }

    public float getRectCoordy1() {
        return rectCoordy1;
    }

    public void setRectCoordy1(int rectCoordy1) {
        this.rectCoordy1 = rectCoordy1;
    }

    public int getRectCoordy2() {
        return rectCoordy2;
    }

    public void setRectCoordy2(int rectCoordy2) {
        this.rectCoordy2 = rectCoordy2;
    }
}
