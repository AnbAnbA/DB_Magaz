package com.example.db_magaz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainProd extends AppCompatActivity implements View.OnClickListener{
    Button btnAdd, btnClear;
    EditText Grup, Nazv, Cena;

    DBHelper dbHelper;
    SQLiteDatabase database;
    ContentValues contentValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_prod);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);


        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        Grup = findViewById(R.id.Grup);
         Nazv= findViewById(R.id.Nazv);
        Cena = findViewById(R.id.Cena);

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        Grup.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                Grup.setHint("");
            else
                Grup.setHint("Группа");
        });

        Nazv.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                Nazv.setHint("");
            else
                Nazv.setHint("Наименование");
        });

        Cena.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                Cena.setHint("");
            else
                Cena.setHint("Цена");
        });
        UpdateTable();
    }
    public void UpdateTable() {
        Cursor cursor = database.query(DBHelper.TABLE_PROD, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int grupIndex = cursor.getColumnIndex(DBHelper.KEY_GRUP);
            int nazvIndex = cursor.getColumnIndex(DBHelper.KEY_NAZV);
            int cenaIndex = cursor.getColumnIndex(DBHelper.KEY_CENA);

            TableLayout dbOutput = findViewById(R.id.dbOutput);
            dbOutput.removeAllViews();
            do {
                TableRow dbOutputRow = new TableRow(this);
                dbOutputRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

                TextView outputID = new TextView(this);
                params.weight = 1.0f;
                outputID.setLayoutParams(params);
                outputID.setText(cursor.getString(idIndex));
                dbOutputRow.addView(outputID);

                TextView outputGrup = new TextView(this);
                params.weight = 3.0f;
                outputGrup.setLayoutParams(params);
                outputGrup.setText(cursor.getString(grupIndex));
                dbOutputRow.addView(outputGrup);

                TextView outputNazv = new TextView(this);
                params.weight = 3.0f;
                outputNazv.setLayoutParams(params);
                outputNazv.setText(cursor.getString(nazvIndex));
                dbOutputRow.addView(outputNazv);

                TextView outputCena = new TextView(this);
                params.weight = 3.0f;
                outputCena.setLayoutParams(params);
                outputCena.setText(cursor.getString(cenaIndex));
                dbOutputRow.addView(outputCena);

                Button deleteBtn = new Button(this);
                deleteBtn.setOnClickListener(this);
                params.weight=1.0f;
                deleteBtn.setLayoutParams(params);
                deleteBtn.setText("Удалить запись");
                deleteBtn.setId(cursor.getInt(idIndex));
                dbOutputRow.addView(deleteBtn);

                dbOutput.addView(dbOutputRow);

            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnAdd:
                String grup = Grup.getText().toString();
                String nazv = Grup.getText().toString();
                int cena = Integer.parseInt(Cena.getText().toString());


                contentValues = new ContentValues();
                contentValues.put(DBHelper.KEY_CENA, cena);
                contentValues.put(DBHelper.KEY_NAZV, nazv);
                contentValues.put(DBHelper.KEY_GRUP, grup);
                database.insert(DBHelper.TABLE_PROD, null, contentValues);
                UpdateTable();
                Cena.setText("");
                Nazv.setText("");
                Grup.setText("");

                break;

            case R.id.btnClear:
                database.delete(DBHelper.TABLE_PROD, null, null);
                TableLayout dbOutput = findViewById(R.id.dbOutput);
                dbOutput.removeAllViews();
                UpdateTable();
                break;

            default:
                View outputDBRow = (View) view.getParent();
                ViewGroup outputDB = (ViewGroup) outputDBRow.getParent();
                outputDB.removeView(outputDBRow);
                outputDB.invalidate();

                database.delete(DBHelper.TABLE_PROD, DBHelper.KEY_ID+" = ?", new String[]{String.valueOf((view.getId()))});
                contentValues = new ContentValues();
                Cursor cursorUpdater = database.query(DBHelper.TABLE_PROD, null, null, null, null, null, null);
                if(cursorUpdater.moveToFirst()) {
                    int idIndex = cursorUpdater.getColumnIndex(DBHelper.KEY_ID);
                    int nazvIndex = cursorUpdater.getColumnIndex(DBHelper.KEY_NAZV);
                    int cenaIndex = cursorUpdater.getColumnIndex(DBHelper.KEY_CENA);
                    int grupIndex = cursorUpdater.getColumnIndex(DBHelper.KEY_GRUP);

                    int realID = 1;
                    do {
                        if (cursorUpdater.getInt(idIndex) > realID) {
                            contentValues.put(DBHelper.KEY_ID, realID);
                            contentValues.put(DBHelper.KEY_GRUP, cursorUpdater.getString(grupIndex));
                            contentValues.put(DBHelper.KEY_CENA, cursorUpdater.getString(cenaIndex));
                            contentValues.put(DBHelper.KEY_NAZV, cursorUpdater.getString(nazvIndex));


                            database.replace(DBHelper.TABLE_PROD, null, contentValues);
                        }
                        realID++;
                    } while (cursorUpdater.moveToNext());
                    if (cursorUpdater.moveToLast()) {
                        database.delete(DBHelper.TABLE_PROD, DBHelper.KEY_ID + " = ?", new String[]{cursorUpdater.getString(idIndex)});
                    }
                    UpdateTable();

                }
                cursorUpdater.close();
                break;

        }
    }

}
