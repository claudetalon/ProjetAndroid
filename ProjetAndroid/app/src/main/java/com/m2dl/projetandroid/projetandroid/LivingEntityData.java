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
    private float GPSLatitude;
    private float GPSLongitude;
    private float rectCoordx1;
    private float rectCoordx2;
    private float rectCoordy1;

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

    private float rectCoordy2;
    private Date date;
    private String node;
    private String comment;


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

}
