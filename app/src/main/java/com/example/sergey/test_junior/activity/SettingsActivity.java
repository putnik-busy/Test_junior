package com.example.sergey.test_junior.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.sergey.test_junior.R;
import com.example.sergey.test_junior.adapter.Adapter;
import com.example.sergey.test_junior.database.DataBaseHelper;
import com.example.sergey.test_junior.model.ListData;
import com.example.sergey.test_junior.utils.App;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView mListView;
    private List<ListData> mSettingsList;
    private EditText mPosition_row, mPercent_button;
    private Adapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third);
        init();
        mAdapter = new Adapter(SettingsActivity.this, mSettingsList);
        mListView.setAdapter(mAdapter);
        if (App.getInstance().getmDb().checkParameters()) {
            App.getInstance().getmDb().getSettingsListAsync(new DataBaseHelper.DatabaseHand<String>() {
                @Override
                public void onComplete(boolean success, String result) {
                    if (success) {
                        mSettingsList.addAll(App.deserialize(result));
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    private void init() {
        Button mSave = (Button) findViewById(R.id.save_settings);
        mPosition_row = (EditText) findViewById(R.id.position_row);
        mPercent_button = (EditText) findViewById(R.id.percent_button);
        mListView = (ListView) findViewById(R.id.list_set);
        mSettingsList = new ArrayList<>();
        if (mSave != null) {
            mSave.setOnClickListener(this);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.settings_item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_settings:
                addToList();
                if (mSettingsList.size() > 0) {
                    App.getInstance().getmDb().updateSettingsListAsync(App.serialize(mSettingsList),
                            new DataBaseHelper.DatabaseHand<Void>() {
                                @Override
                                public void onComplete(boolean success, Void result) {
                                }
                            });
                }
                break;
        }
    }

    private void addToList() {
        String sPos = mPosition_row.getText().toString();
        String sProc = mPercent_button.getText().toString();
        ListData listData = new ListData();
        listData.setmStringNumber(Integer.parseInt(sPos));
        listData.setmStringPercent(Float.parseFloat(sProc));
        mSettingsList.add(listData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, getIntent().putExtra("value", (Serializable) mSettingsList));
        super.onBackPressed();
    }
}