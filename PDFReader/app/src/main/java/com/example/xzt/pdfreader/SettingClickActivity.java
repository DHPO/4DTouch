package com.example.xzt.pdfreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
                    int res=0;
                    for(int i=0;i<5;i++) {
                        if(Data.isFingerPress[i]){
                            res|=(1<<i);
                        }
                    }
                    if(res!=0) {
                        long starttime = System.currentTimeMillis();
                        long curtime = System.currentTimeMillis();
                        while (curtime - starttime < 200) {
                            for (int i = 0; i < 5; i++) {
                                if (Data.isFingerPress[i]) {
                                    res |= (1 << i);
                                }
                            }
                            curtime=System.currentTimeMillis();
                        }
                    }
                    if(res!=0){
                        int fingercnt=0;
                        if((res&Data.THUMB)!=0){
                            fingercnt++;
                        }
                        if((res&Data.INDEX)!=0){
                            fingercnt++;
                        }
                        if((res&Data.MIDDLE)!=0){
                            fingercnt++;
                        }
                        if((res&Data.RING)!=0){
                            fingercnt++;
                        }
                        if((res&Data.LITTLE)!=0){
                            fingercnt++;
                        }
                        Intent intent=getIntent();
                        String op=intent.getStringExtra("op");
                        if((op.equals("write")||op.equals("eraser")||op.equals("highlight"))&&fingercnt>1){
                            Toast.makeText(getApplicationContext(), "This operation should be set to only one finger!", Toast.LENGTH_SHORT).show();
                            continue;
                        }
                        Intent intentBack=new Intent();
                        intentBack.putExtra("op",op);
                        intentBack.putExtra("key",res);
                        setResult(RESULT_OK,intentBack);
                        finish();
                        stopThread=true;
                    }
                }
            }
        });
        thread.start();



    }

}
