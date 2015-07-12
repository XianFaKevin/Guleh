package com.orange.guleh;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.BootstrapButton;


public class AddActivity extends Activity {

    // general variables
    TextView header;
    ViewFlipper vf;
    BootstrapButton backBtn;
    BootstrapButton nextBtn;
    SQLiteHelper dbHelper = new SQLiteHelper(this);
    ArrayList<String> brands;
    ArrayList<String> models;
    Product pdt;
    boolean gotItem;

    // brand select page
    RelativeLayout bQFrame;
    RadioGroup selectBrand;
    RadioButton existingBrand, newBrand;
    BootstrapEditText editBrandName;
    Spinner chooseBrandName;

    // model select page
    RelativeLayout mQFrame;
    RadioGroup selectModel;
    RadioButton existingModel, newModel;
    BootstrapEditText editModelName;
    Spinner chooseModelName;

    // product finalize page
    TextView carBrand;
    TextView carModel;
    BootstrapEditText carYear;
    BootstrapEditText pdtCode;
    BootstrapEditText pdtPrice;
    RelativeLayout errorFrame;
    TextView errorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        pdt = new Product();
        header = (TextView) findViewById(R.id.header);
        vf = (ViewFlipper) findViewById(R.id.vf);
        vf.setInAnimation(this, R.anim.abc_fade_in);
        vf.setOutAnimation(this, R.anim.abc_fade_out);
        //list = new ArrayList<Product>();
        //list = dbHelper.list();
        brands = new ArrayList<String>();
        models = new ArrayList<String>();
        brands = dbHelper.listBrands();
        if (brands.isEmpty()) gotItem = false;
        else gotItem = true;

        /**
         * Brand select page
         */
        bQFrame = (RelativeLayout) findViewById(R.id.newBrandQuestionFrame);
        selectBrand = (RadioGroup) findViewById(R.id.selectBrand);
        editBrandName = (BootstrapEditText) findViewById(R.id.editBrandName);
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

        /**
         * Model select page
         */
        mQFrame = (RelativeLayout) findViewById(R.id.newModelQuestionFrame);
        selectModel = (RadioGroup) findViewById(R.id.selectModel);
        editModelName = (BootstrapEditText) findViewById(R.id.editModelName);
        chooseModelName = (Spinner) findViewById(R.id.chooseModelName);
        newModel = (RadioButton) findViewById(R.id.newModel);
        existingModel = (RadioButton) findViewById(R.id.existingModel);
        selectModel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.newModel) {
                    editModelName.setVisibility(View.VISIBLE);
                    chooseModelName.setVisibility(View.GONE);
                } else {
                    editModelName.setVisibility(View.GONE);
                    chooseModelName.setVisibility(View.VISIBLE);
                }
            }
        });

        /**
         * Product finalize page
         */
        carBrand = (TextView) findViewById(R.id.carBrandText);
        carModel = (TextView) findViewById(R.id.carModelText);
        carYear = (BootstrapEditText) findViewById(R.id.carYearText);
        pdtCode = (BootstrapEditText) findViewById(R.id.productCodeText);
        pdtPrice = (BootstrapEditText) findViewById(R.id.productPriceText);
        errorFrame = (RelativeLayout) findViewById(R.id.errorFrame);
        errorMsg = (TextView) findViewById(R.id.errorMsg);

        // all functions to be init below after variables have been assigned
        gBrandForm();

        nextBtn = (BootstrapButton) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vf.getDisplayedChild() == 0) {
                    int selectedId = selectBrand.getCheckedRadioButtonId();

                    if (selectedId == newBrand.getId()) {
                        pdt.setBrand(editBrandName.getText().toString());
                    }
                    else {
                        pdt.setBrand(chooseBrandName.getSelectedItem().toString());
                    }
                    gModelForm();
                    carBrand.setText(pdt.getBrand());
                } else if (vf.getDisplayedChild() == 1) {
                    Log.d("selectedId2: ", String.valueOf(selectModel.getCheckedRadioButtonId()));
                    Log.d("new model id: ", String.valueOf(newModel.getId()));
                    int selectedId2 = selectModel.getCheckedRadioButtonId();
                    if (selectedId2 == newModel.getId()) {
                        pdt.setModel(editModelName.getText().toString());
                        gProductForm();
                    }
                    else {
                        pdt.setModel((chooseModelName.getSelectedItem().toString()));
                        gProductForm();
                    }
                    carModel.setText(pdt.getModel());
                } else if (vf.getDisplayedChild() == 2) {
                    completeForm();
                }

            }
        });

        backBtn = (BootstrapButton) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vf.getDisplayedChild() == 0) {
                    finish();
                } else if (vf.getDisplayedChild() == 1) {
                    gBrandForm();
                } else if (vf.getDisplayedChild() == 2) {
                    gModelForm();
                }
            }
        });
    }

    public void gBrandForm() {
        vf.setDisplayedChild(0);
        if (!gotItem) {
            selectBrand.check(R.id.newBrand);
            bQFrame.setVisibility(View.GONE);
            chooseBrandName.setVisibility(View.GONE);
            editBrandName.setVisibility(View.VISIBLE);
        } else {
            selectBrand.check(R.id.existingBrand);
            ArrayAdapter dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, brands);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            chooseBrandName.setAdapter(dataAdapter);
            chooseBrandName.setVisibility(View.VISIBLE);
            editBrandName.setVisibility(View.GONE);
        }
    }

    public void gModelForm() {
        boolean setNewBrand;
        if (selectBrand.getCheckedRadioButtonId() == R.id.newBrand) setNewBrand = true;
        else setNewBrand = false;
        header.setText("选择车款式");
        vf.setDisplayedChild(1);
        Log.d("Brand name: ", pdt.getBrand().toString());
        models = dbHelper.listModels(pdt.getBrand());
        Log.d("models size: ", String.valueOf(models.size()));
        if (setNewBrand) {
            selectModel.check(R.id.newModel);
            mQFrame.setVisibility(View.GONE);
            chooseModelName.setVisibility(View.GONE);
            editModelName.setVisibility(View.VISIBLE);
        } else {
            selectModel.check(R.id.existingModel);
            mQFrame.setVisibility(View.VISIBLE);
            ArrayAdapter dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, models);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            chooseModelName.setAdapter(dataAdapter);
            chooseModelName.setVisibility(View.VISIBLE);
            editModelName.setVisibility(View.GONE);
        }
    }

    public void gProductForm() {
        header.setText("Bodykit 资料");
        nextBtn.setText("完成");
        vf.setDisplayedChild(2);
    }

    public void completeForm() {
        // validate
        // insert to db
        if (validate()) {
            finish();
        } else {
            errorFrame.setVisibility(View.VISIBLE);
        }
    }

    public boolean validate() {
        boolean noErrors = true;
        String errorLog = "";
        String nl = System.getProperty("line.separator");
        pdt.setCode(pdtCode.getText().toString());
        if (pdt.getCode().isEmpty()) {
            noErrors = false;
            if (!errorLog.isEmpty()) errorLog += nl;
            errorLog += "号码不能是空";
        }
        try {
            pdt.setYear(Integer.parseInt(carYear.getText().toString()));
        } catch (NumberFormatException nfe) {
            noErrors = false;
            if (!errorLog.isEmpty()) errorLog += nl;
            errorLog += "年份输入错误";
        }
        try {
            Double p = Double.parseDouble(pdtPrice.getText().toString());
            p = p * 100;
            pdt.setPrice(p.intValue());
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
}
