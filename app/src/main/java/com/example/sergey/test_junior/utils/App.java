package com.example.sergey.test_junior.utils;


import android.app.Application;

import com.example.sergey.test_junior.database.DataBaseHelper;
import com.example.sergey.test_junior.model.ListData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class App extends Application {

    private static App sInstance;
    private DataBaseHelper mDb;
    private static Gson sGson;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mDb = new DataBaseHelper(this);
        sGson = new Gson();
    }

    public synchronized static App getInstance() {
        return sInstance;
    }

    public DataBaseHelper getmDb() {
        return mDb;
    }

    public static String serialize(Object object) {
        return sGson.toJson(object);
    }

    public static ArrayList<ListData> deserialize(String json) {
        Type collectionType = new TypeToken<ArrayList<ListData>>() {
        }.getType();
        return sGson.fromJson(json, collectionType);
    }
}
