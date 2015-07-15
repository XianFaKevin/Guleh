package com.orange.guleh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import java.util.ArrayList;

public class SearchActivity extends Activity {

    BootstrapEditText search;
    ArrayList<Product> all, selected;
    SQLiteHelper dbHelper = new SQLiteHelper(this);
    BootstrapButton menuBtn, listBtn;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupUI(findViewById(R.id.rr));

        all = dbHelper.list();
        selected = new ArrayList<Product>();
        lv = (ListView) findViewById(R.id.lv);
        final SearchRowAdapter Adapter = new SearchRowAdapter(getApplicationContext(), selected);

        search = (BootstrapEditText) findViewById(R.id.searchText);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                selected.clear();
                if (s.length() != 0) {
                    for (Product p: all) {
                        if (p.getCode().contains(s)) {
                            selected.add(p);
                        }
                    }
                }
                Adapter.add(selected);
                lv.setAdapter(Adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(getApplicationContext(), DisplayProductActivity.class);
                        i.putExtra("Code", selected.get(position).getCode());
                        startActivity(i);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        listBtn = (BootstrapButton) findViewById(R.id.listBtn);
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(i);
            }
        });

        menuBtn = (BootstrapButton) findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(SearchActivity.this);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }
}
