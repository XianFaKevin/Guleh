package com.orange.guleh;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.ArrayList;


public class SearchActivity extends Activity {

    SQLiteHelper dbHelper = new SQLiteHelper(this);
    ArrayList<String> brands;
    ArrayList<String> models;
    ArrayList<String> years;
    ArrayList<String> codes;
    Spinner chooseBrand;
    Spinner chooseModel;
    Spinner chooseYear;
    Spinner chooseCode;
    RelativeLayout modelFrame;
    RelativeLayout yearFrame;
    Product pdt;
    BootstrapButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        brands = new ArrayList<String>();
        models = new ArrayList<String>();
        years = new ArrayList<String>();
        pdt = new Product();
        btn = (BootstrapButton) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DisplayProductActivity.class);
                i.putExtra("Code", chooseCode.getSelectedItem().toString());
                startActivity(i);
            }
        });

        modelFrame = (RelativeLayout) findViewById(R.id.modelFrame);
        yearFrame = (RelativeLayout) findViewById(R.id.yearFrame);

        chooseBrand = (Spinner) findViewById(R.id.chooseBrand);
        chooseBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                modelEntry();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        chooseModel = (Spinner) findViewById(R.id.chooseModel);
        chooseModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yearEntry();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        chooseYear = (Spinner) findViewById(R.id.chooseYear);
        chooseYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                codeEntry();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        chooseCode = (Spinner) findViewById(R.id.chooseCode);
        chooseCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // do something
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // functions last
        brandEntry();
    }

    public void brandEntry() {
        brands = dbHelper.listBrands();
        ArrayAdapter dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, brands);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseBrand.setAdapter(dataAdapter);
    }

    public void modelEntry() {
        models = dbHelper.listModels(chooseBrand.getSelectedItem().toString());
        ArrayAdapter dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, models);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseModel.setAdapter(dataAdapter);
    }

    public void yearEntry() {
        years = dbHelper.listYears(chooseBrand.getSelectedItem().toString(), chooseModel.getSelectedItem().toString());
        ArrayAdapter dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, years);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseYear.setAdapter(dataAdapter);
    }

    public void codeEntry() {
        codes = dbHelper.listCodes(chooseBrand.getSelectedItem().toString(), chooseModel.getSelectedItem().toString(),
                chooseYear.getSelectedItem().toString());
        ArrayAdapter dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, codes);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseCode.setAdapter(dataAdapter);
    }

}
