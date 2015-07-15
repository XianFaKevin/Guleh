package com.orange.guleh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ListActivity extends Activity {

    SQLiteHelper dbHelper = new SQLiteHelper(this);
    ArrayList<Product> list;
    ListRowAdapter Adapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        list = dbHelper.list();

        lv = (ListView) findViewById(R.id.lv);
        Adapter = new ListRowAdapter(getApplicationContext(), list);
        display();
    }

    public void display() {
        lv.setAdapter(Adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), DisplayProductActivity.class);
                i.putExtra("Code", list.get(position).getCode());
                startActivity(i);
            }
        });
    }
}
