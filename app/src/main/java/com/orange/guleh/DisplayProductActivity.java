package com.orange.guleh;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class DisplayProductActivity extends Activity {

    SQLiteHelper dbHelper = new SQLiteHelper(this);
    Product pdt;
    TextView codeHead;
    TextView priceText;
    Button menuBtn;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_product);

        Intent i = getIntent();
        String code = i.getStringExtra("Code");
        pdt = dbHelper.get(code);

        codeHead = (TextView) findViewById(R.id.codeHeader);
        priceText = (TextView) findViewById(R.id.priceText);
        menuBtn = (Button) findViewById(R.id.menuBtn);
        backBtn = (Button) findViewById(R.id.backBtn);

        codeHead.setText(pdt.getCode());
        priceText.setText(String.valueOf(pdt.getPrice()));

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}
