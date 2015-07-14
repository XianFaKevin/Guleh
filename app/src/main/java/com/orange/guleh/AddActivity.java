package com.orange.guleh;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.BootstrapButton;


public class AddActivity extends Activity {

    BootstrapButton backBtn;
    BootstrapButton nextBtn;
    SQLiteHelper dbHelper = new SQLiteHelper(this);
    Product pdt;
    RelativeLayout errorFrame;
    TextView errorMsg;
    BootstrapEditText codeText, priceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setupUI(findViewById(R.id.rr));

        pdt = new Product();
        errorFrame = (RelativeLayout) findViewById(R.id.errorFrame);
        errorMsg = (TextView) findViewById(R.id.errorMsg);
        codeText = (BootstrapEditText) findViewById(R.id.codeText);
        priceText = (BootstrapEditText) findViewById(R.id.priceText);

        nextBtn = (BootstrapButton) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Toast.makeText(getApplicationContext(), "号码成功输入到资料库！", Toast.LENGTH_SHORT).show();
                } else {
                    errorFrame.setVisibility(View.VISIBLE);
                }
            }
        });

        backBtn = (BootstrapButton) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public boolean validate() {
        boolean noErrors = true;
        String errorLog = "";
        String nl = System.getProperty("line.separator");
        pdt.setCode(codeText.getText().toString().toLowerCase());
        if (pdt.getCode().isEmpty()) {
            noErrors = false;
            if (!errorLog.isEmpty()) errorLog += nl;
            errorLog += "号码不能是空";
        }
        try {
            Double p = Double.parseDouble(priceText.getText().toString());
            pdt.setPrice(p);
        } catch (NumberFormatException nfe) {
            noErrors = false;
            if (!errorLog.isEmpty()) errorLog += nl;
            errorLog += "价钱输入错误";
        }
        if (noErrors) {
            if (dbHelper.add(pdt) == -1) {
                noErrors = false;
                if (!errorLog.isEmpty()) errorLog += nl;
                errorLog += "资料库已有相同的号码";
            }
        }
        errorMsg.setText(errorLog);
        return noErrors;
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
                    hideSoftKeyboard(AddActivity.this);
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
