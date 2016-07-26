package com.example.sergey.test_junior.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sergey.test_junior.R;
import com.example.sergey.test_junior.adapter.Adapter;
import com.example.sergey.test_junior.database.DataBaseHelper;
import com.example.sergey.test_junior.model.ListData;
import com.example.sergey.test_junior.utils.App;

import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener {
    private ListView mListView;
    private List<ListData> mList;
    private static final int TAG = 0;
    private Adapter mAdapter;
    protected static final String TABLE = "BUTTON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);
        init();
        if (checkExistTable()) {
            App.getInstance().getmDb().getMainListAsync(new DataBaseHelper.DatabaseHand<String>() {
                @Override
                public void onComplete(boolean success, String result) {
                    mList.addAll(App.deserialize(result));
                }
            });
        } else {
            putDataInList();
        }
        mAdapter = new Adapter(FirstActivity.this, mList);
        mListView.setAdapter(mAdapter);
    }

    private void init() {
        mListView = (ListView) findViewById(R.id.list);
        mList = new ArrayList<>();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title);
        }
        mListView.setOnItemClickListener(this);
    }

    private void putDataInList() {
        for (int i = 0; i < 100; i++) {
            ListData listData = new ListData();
            listData.setmStringNumber(i);
            mList.add(listData);
        }
        App.getInstance().getmDb().addMainListAsync(App.serialize(mList),
                new DataBaseHelper.DatabaseHand<Void>() {
                    @Override
                    public void onComplete(boolean success, Void result) {
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getBaseContext(), SecondActivity.class);
        intent.putExtra(SecondActivity.STRING_NAME, position);
        intent.putExtra(SecondActivity.STRING_PERCENT, mList.get(position).getmStringPercent());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(FirstActivity.this,
                        SettingsActivity.class);
                startActivityForResult(intent, TAG);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAG) {
            List<ListData> listAnswer = (List) data.getSerializableExtra("value");
            addingToList(listAnswer);
        }
    }

    public List<ListData> addingToList(List<ListData> listIns) {
        for (int i = 0; i < listIns.size(); i++) {
            for (int j = 0; j < mList.size(); j++) {
                if (mList.get(j).getmStringNumber() == (listIns.get(i).getmStringNumber())) {
                    mList.set(j, listIns.get(i));
                }
            }
        }
        App.getInstance().getmDb().updateMainListAsync(App.serialize(mList),
                new DataBaseHelper.DatabaseHand<Void>() {
                    @Override
                    public void onComplete(boolean success, Void result) {
                        if (success) {
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
        return mList;
    }

    public boolean checkExistTable() {
        SQLiteDatabase db = App.getInstance().getmDb().getReadableDatabase();
        return doesTableExist(db, TABLE);
    }

    public boolean doesTableExist(SQLiteDatabase db, String tableName) {
        if (tableName == null || db == null || !db.isOpen()) {
            return false;
        }
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + tableName, null);
        if (!cursor.moveToFirst()) {
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }
}
