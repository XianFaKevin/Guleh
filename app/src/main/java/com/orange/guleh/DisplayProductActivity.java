package com.orange.guleh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;


public class DisplayProductActivity extends Activity {

    SQLiteHelper dbHelper = new SQLiteHelper(this);
    Product pdt;
    TextView codeHead;
    TextView priceText, priceLabel;
    BootstrapButton menuBtn, backBtn, editBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_product);

        Intent i = getIntent();
        String code = i.getStringExtra("Code");
        pdt = dbHelper.get(code);

        codeHead = (TextView) findViewById(R.id.codeHeader);
        priceText = (TextView) findViewById(R.id.priceText);
        priceLabel = (TextView) findViewById(R.id.priceLabel);
        menuBtn = (BootstrapButton) findViewById(R.id.menuBtn);
        backBtn = (BootstrapButton) findViewById(R.id.backBtn);
        editBtn = (BootstrapButton) findViewById(R.id.editBtn);

        if (pdt == null) {
            codeHead.setText("没这号码");
            priceText.setVisibility(View.INVISIBLE);
            priceLabel.setVisibility(View.INVISIBLE);
        } else {
            codeHead.setText(pdt.getCode());
            priceText.setText(String.valueOf(pdt.getPrice()));
        }

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

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EditProductActivity.class);
                i.putExtra("code", pdt.getCode());
                i.putExtra("price", pdt.getPrice());
                startActivity(i);
            }
        });

    }


}
