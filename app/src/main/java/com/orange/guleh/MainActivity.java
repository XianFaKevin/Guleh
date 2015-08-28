package com.orange.guleh;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;

import java.util.Random;


public class MainActivity extends Activity {

    BootstrapButton addBtn;
    BootstrapButton searchBtn;
    //BootstrapCircleThumbnail picture;
    Button settingsBtn;
    SQLiteHelper dbHelper = new SQLiteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addBtn = (BootstrapButton) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(i);
            }
        });

        searchBtn = (BootstrapButton) findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(i);
            }
        });

        settingsBtn = (Button) findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsDialog(MainActivity.this);
            }
        });

       // picture = (BootstrapCircleThumbnail) findViewById(R.id.bb);
        //setImage();
    }

    public void settingsDialog(final Activity context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context, 2);
        // set dialog message
        alertDialogBuilder
                .setTitle("确定清零资料库？")
                        //.setMessage("确定清零资料库？")
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setIcon(R.drawable.warning)
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dbHelper.reset();
                        Toast.makeText(getApplicationContext(), "资料库清零完毕", Toast.LENGTH_LONG).show();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    /*public void setImage() {
        int count = new Random().nextInt(9);
        if (count == 0) picture.setImage(R.drawable.a1);
        else if (count == 1) picture.setImage(R.drawable.a2);
        else if (count == 2) picture.setImage(R.drawable.a4);
        else if (count == 3) picture.setImage(R.drawable.a5);
        else if (count == 4) picture.setImage(R.drawable.a6);
        else if (count == 5) picture.setImage(R.drawable.a7);
        else if (count == 6) picture.setImage(R.drawable.a8);
        else if (count == 7) picture.setImage(R.drawable.a10);
        else picture.setImage(R.drawable.a12);
    }*/
}
