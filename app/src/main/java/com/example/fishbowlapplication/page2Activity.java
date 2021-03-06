package com.example.fishbowlapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.lakue.lakuepopupactivity.PopupActivity;
import com.lakue.lakuepopupactivity.PopupGravity;
import com.lakue.lakuepopupactivity.PopupType;

public class page2Activity extends AppCompatActivity {


    LinearLayout Mpage2_1,Mpage2_2;
    TextView dbtest;
    DBHelper dbHelper;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_page2);
        setTitle("먹이관리");

        Mpage2_1 = findViewById(R.id.Mpage2_1);
        Mpage2_2 = findViewById(R.id.Mpage2_2);
        dbtest = findViewById(R.id.dbtest);
        dbHelper = new DBHelper(page2Activity.this,1);
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        if(pref.getInt("auto",0) == 1) {
            Mpage2_2.setEnabled(false);
            Mpage2_2.setBackgroundColor(R.color.disable);
        }

        Mpage2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(page2Activity.this);
                if(pref.getInt("auto",0) != 1) {
                    dlg.setTitle("자동먹이주기");
                    dlg.setMessage("자동먹이주기를 시작하시겠습니까?");
                    dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            TimePickerDialog timesetdialog = new TimePickerDialog(page2Activity.this, new TimePickerDialog.OnTimeSetListener() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                    editor.putInt("setTime", timePicker.getHour());
                                    editor.putInt("setMinute", timePicker.getMinute());
                                    editor.putInt("auto", 1);
                                    editor.apply();
                                    pageClear();
                                }
                            }, 0, 0, true);
                            timesetdialog.show();
                        }
                    });

                    dlg.setNegativeButton("아니요", null);
                    dlg.show();
                }
                else {
                    dlg.setTitle("수동먹이주기");
                    dlg.setMessage("수동으로 전환하시겠습니까?");
                    dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        editor.putInt("auto", 0);
                        editor.apply();
                        pageClear();
                    }
                });

                dlg.setNegativeButton("아니요", null);
                dlg.show();
                }
            }
        });
        Mpage2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbtest.setText(String.valueOf(pref.getInt("setTime",0))+String.valueOf(pref.getInt("setMinute",0)));
                AlertDialog.Builder dlg = new AlertDialog.Builder(page2Activity.this);
                dlg.setTitle("먹이주기");
                dlg.setMessage("먹이를 주시겠습니까?");
                dlg.setPositiveButton("예",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                dlg.setNegativeButton("아니요",null);
                dlg.show();
            }
        });

    }
    void pageClear () {
        finish();
        overridePendingTransition(0, 0);
        Intent intent = getIntent();
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

}
