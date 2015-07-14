package com.orange.guleh;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import org.w3c.dom.Text;

public class EditActivity extends Activity {

    SQLiteHelper dbHelper = new SQLiteHelper(this);
    Product pdt;
    TextView priceText, codeText, codeHeader;
    BootstrapButton submitBtn;
    BootstrapButton cancelBtn;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent i = getIntent();
        code = i.getStringExtra("code");
        double price = i.getDoubleExtra("price", -1);

        codeHeader = (TextView) findViewById(R.id.codeHeader);
        codeText = (BootstrapEditText) findViewById(R.id.codeText);
        priceText = (BootstrapEditText) findViewById(R.id.priceText);

        codeHeader.setText("改" + code);
        codeText.setText(code);
        priceText.setText(String.valueOf(price));

        submitBtn = (BootstrapButton) findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.update(code, codeText.getText().toString().toLowerCase(), priceText.getText().toString().toLowerCase());
                Intent i = new Intent(getApplicationContext(), DisplayProductActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("Code", codeText.getText().toString().toLowerCase());
                startActivity(i);
                finish();
            }
        });
    }
}
