package com.orange.guleh;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import org.w3c.dom.Text;

public class EditProductActivity extends Activity {

    SQLiteHelper dbHelper = new SQLiteHelper(this);
    Product pdt;
    TextView priceText, codeText, codeHeader;
    BootstrapButton submitBtn, cancelBtn;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setupUI(findViewById(R.id.rr));

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

        cancelBtn = (BootstrapButton) findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
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
                    hideSoftKeyboard(EditProductActivity.this);
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
