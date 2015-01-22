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
    private int rectCoordx1;
    private int rectCoordx2;
    private int rectCoordy1;
    private int rectCoordy2;
    private Date date;
    private String node;
    private String comment;


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

    public void setGPSLongitude(float GPSLongitude) {
        this.GPSLongitude = GPSLongitude;
    }

    public float getGPSLatitude() {
        return GPSLatitude;
    }

    public float getGPSLongitude() {
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
