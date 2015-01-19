package com.m2dl.projetandroid.projetandroid;

import android.media.Image;

import java.util.Date;

/**
 * Created by ctalon on 17/01/15.
 */
public class LivingEntity {


    private int id;
    private String name;
    private String img;
    private String GPScoordinates;
    private String rectangleCoordinates;
    private Date date;
    private String node;
    private String comment;


    public void setComment(String comment) {
        this.comment = comment;
    }


    public void setGPScoordinates(String GPScoordinates) {
        this.GPScoordinates = GPScoordinates;
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

    public String getGPScoordinates() {
        return GPScoordinates;
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

    public void setRectangleCoordinates(String rectangleCoordinates) {
        this.rectangleCoordinates = rectangleCoordinates;
    }

    public String getRectangleCoordinates() {
        return rectangleCoordinates;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }
}
