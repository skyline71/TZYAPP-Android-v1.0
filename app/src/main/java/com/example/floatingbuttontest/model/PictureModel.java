package com.example.floatingbuttontest.model;

import android.graphics.Bitmap;

/**
 * Created by dell on 2015/5/31.
 */
public class PictureModel {
    private String title;
    private String time;
    private String goodTextView;
    private String badTextView;
    private String addressTextView;
    private Bitmap pictureBitmap;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGoodTextView() {
        return goodTextView;
    }

    public void setGoodTextView(String goodTextView) {
        this.goodTextView = goodTextView;
    }

    public String getBadTextView() {
        return badTextView;
    }

    public void setBadTextView(String badTextView) {
        this.badTextView = badTextView;
    }

    public String getAddressTextView() {
        return addressTextView;
    }

    public void setAddressTextView(String addressTextView) {
        this.addressTextView = addressTextView;
    }

    public Bitmap getPictureBitmap() {
        return pictureBitmap;
    }

    public void setPictureBitmap(Bitmap pictureBitmap) {
        this.pictureBitmap = pictureBitmap;
    }
}
