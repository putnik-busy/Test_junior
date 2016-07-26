package com.example.sergey.test_junior.model;


import java.io.Serializable;

public class ListData implements Serializable {

    private int mStringNumber;
    private float mStringPercent;

    public int getmStringNumber() {
        return mStringNumber;
    }

    public void setmStringNumber(int mStringNumber) {
        this.mStringNumber = mStringNumber;
    }

    public float getmStringPercent() {
        return mStringPercent;
    }

    public void setmStringPercent(float mStringPercent) {
        this.mStringPercent = mStringPercent;
    }
}
