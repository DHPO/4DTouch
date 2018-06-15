package com.example.xzt.pdfreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class SettingClickActivity extends AppCompatActivity {
    boolean stopThread = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_click);
        Button backButton=(Button)findViewById(R.id.title_back);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Thread thread  = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopThread){
                    for(int i=0;i<5;i++) {
                        if(Data.isFingerPress[i]){
                            Intent intent=getIntent();
                            String op=intent.getStringExtra("op");
                            Intent intentBack=new Intent();
                            intentBack.putExtra("op",op);
                            intentBack.putExtra("key",i);
                            setResult(RESULT_OK,intentBack);
                            finish();
                            stopThread=true;
                            break;
                        }
                    }
                }
            }
        });
        thread.start();



    }

}
