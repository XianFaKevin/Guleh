package com.orange.guleh;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;


public class AddActivity extends Activity {

    // general variables
    TextView header;
    ViewFlipper vf;
    Button backBtn;
    Product pdt;

    // brand select page
    RadioGroup selectBrand;
    RadioButton existingBrand, newBrand;
    Button nextBtn;

    EditText editBrandName;
    Spinner chooseBrandName;

    // model select page
    RelativeLayout rl;
    RadioGroup selectModel;
    RadioButton existingModel, newModel;
    EditText editModelName;
    Spinner chooseModelName;
    EditText editCodeName;
    Spinner chooseCodeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        pdt = new Product();
        header = (TextView) findViewById(R.id.header);
        vf = (ViewFlipper) findViewById(R.id.vf);

        selectBrand = (RadioGroup) findViewById(R.id.selectBrand);
        editBrandName = (EditText) findViewById(R.id.editBrandName);
        chooseBrandName = (Spinner) findViewById(R.id.chooseBrandName);
        selectBrand.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.newBrand) {
                    editBrandName.setVisibility(View.VISIBLE);
                    chooseBrandName.setVisibility(View.GONE);
                } else {
                    editBrandName.setVisibility(View.GONE);
                    chooseBrandName.setVisibility(View.VISIBLE);
                }
            }
        });

        existingBrand = (RadioButton) findViewById(R.id.existingBrand);
        newBrand = (RadioButton) findViewById(R.id.newBrand);

        nextBtn = (Button) findViewById(R.id.nextBtn1);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = selectBrand.getCheckedRadioButtonId();

                if (selectedId == newBrand.getId()) {
                    displayCodePage(true);
                }  else {
                    displayCodePage(false);
                }
            }
        });

        /**
         * Code/Model select page
         */
        rl = (RelativeLayout) findViewById(R.id.newModelQuestionFrame);
        selectModel = (RadioGroup) findViewById(R.id.selectModel);
        editModelName = (EditText) findViewById(R.id.editModelName);
        chooseModelName = (Spinner) findViewById(R.id.chooseModelName);
        editCodeName = (EditText) findViewById(R.id.editCodeName);
        chooseCodeName = (Spinner) findViewById(R.id.chooseCodeName);

    }

    public void displayCodePage(boolean newBrand) {
        header.setText("选择车模式于code");
        vf.setDisplayedChild(1);
        if (newBrand) {
            pdt.setBrand(editBrandName.getText().toString());
            rl.setVisibility(View.GONE);
        } else {
            pdt.setBrand(chooseBrandName.getSelectedItem().toString());
            rl.setVisibility(View.VISIBLE);
        }
    }
}
