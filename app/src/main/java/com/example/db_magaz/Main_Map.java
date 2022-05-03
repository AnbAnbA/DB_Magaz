package com.example.db_magaz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main_Map extends AppCompatActivity implements View.OnClickListener {
    Button btnProd,  btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);
        btnProd = findViewById(R.id.btnProd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnProd.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnProd:
                startActivity(new Intent(this, MainProd.class));
                break;

            case R.id.btnUpdate:
                startActivity(new Intent(this, MainP.class));
                break;

        }
    }
}