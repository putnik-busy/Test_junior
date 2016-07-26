package com.example.sergey.test_junior.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.sergey.test_junior.R;
import com.example.sergey.test_junior.utils.ProgressButton;

public class SecondActivity extends AppCompatActivity {

    protected static String STRING_NAME = "NAME";
    protected static String STRING_PERCENT = "PERCENT";
    private TextView mStringTextView;
    private ProgressButton mStringButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        init();
        getData();
    }

    private void getData() {
        int mStringName = getIntent().getIntExtra(STRING_NAME, 0);
        float mPercent = getIntent().getFloatExtra(STRING_PERCENT, 1);
        mStringTextView.setText(String.valueOf(mStringName));
        mStringButton.setRatio(mPercent);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Row № " + mStringName);
        }
    }

    private void init() {
        mStringTextView = (TextView) findViewById(R.id.textViewSecond);
        mStringButton = (ProgressButton) findViewById(R.id.buttonViewSecond);
    }
}
