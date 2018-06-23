package com.example.xzt.pdfreader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class UserSettingActivity extends AppCompatActivity {

    private int pageturnKey;
    private int writeKey;
    private int settingKey;
    private int nightKey;
    private int eraserKey;
    private int highlightKey;

    TextView pageturnText;
    TextView writeText;
    TextView settingText;
    TextView nightText;
    TextView eraserText;
    TextView highlightText;

    final Data data=(Data)getApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);

        SharedPreferences pref=getSharedPreferences("data",MODE_PRIVATE);
        pageturnKey=pref.getInt("pageturnKey",2);
        writeKey=pref.getInt("writeKey",1);
        settingKey=pref.getInt("settingKey",0);
        nightKey=pref.getInt("nightKey",2);
        eraserKey=pref.getInt("eraserKey",1);
        highlightKey=pref.getInt("highlightKey",0);

        Button backButton=(Button)findViewById(R.id.title_back);
        Button saveButton=(Button)findViewById(R.id.title_save);
        Button pageturnEditButton=(Button)findViewById(R.id.setting_pageturn_button);
        Button writeEditButton=(Button)findViewById(R.id.setting_write_button);
        Button settingEditButton=(Button)findViewById(R.id.setting_setting_button);
        Button nightEditButton=(Button)findViewById(R.id.setting_night_button);
        Button eraserEditButton=(Button)findViewById(R.id.setting_eraser_button);
        Button highlightEditButton=(Button)findViewById(R.id.setting_highlight_button);

        pageturnText=(TextView)findViewById(R.id.setting_pageturn_text);
        writeText=(TextView)findViewById(R.id.setting_write_text);
        settingText=(TextView)findViewById(R.id.setting_setting_text);
        nightText=(TextView)findViewById(R.id.setting_night_text);
        eraserText=(TextView)findViewById(R.id.setting_eraser_text);
        highlightText=(TextView)findViewById(R.id.setting_highlight_text);

        pageturnText.setText(data.getFinger(pageturnKey));
        writeText.setText(data.getFinger(writeKey));
        settingText.setText(data.getFinger(settingKey));
        nightText.setText(data.getFinger(nightKey));
        eraserText.setText(data.getFinger(eraserKey));
        highlightText.setText(data.getFinger(highlightKey));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
                editor.putInt("pageturnKey",pageturnKey);
                editor.putInt("writeKey",writeKey);
                editor.putInt("settingKey",settingKey);
                editor.putInt("nightKey",nightKey);
                editor.putInt("eraserKey",eraserKey);
                editor.putInt("highlightKey",highlightKey);
                editor.commit();
                finish();
            }
        });

        pageturnEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserSettingActivity.this,SettingClickActivity.class);
                intent.putExtra("op","pageturn");
                startActivityForResult(intent,1);
            }
        });

        writeEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserSettingActivity.this,SettingClickActivity.class);
                intent.putExtra("op","write");
                startActivityForResult(intent,1);
            }
        });

        settingEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserSettingActivity.this,SettingClickActivity.class);
                intent.putExtra("op","setting");
                startActivityForResult(intent,1);
            }
        });

        nightEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserSettingActivity.this,SettingClickActivity.class);
                intent.putExtra("op","night");
                startActivityForResult(intent,1);
            }
        });

        eraserEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserSettingActivity.this,SettingClickActivity.class);
                intent.putExtra("op","eraser");
                startActivityForResult(intent,1);
            }
        });

        highlightEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserSettingActivity.this,SettingClickActivity.class);
                intent.putExtra("op","highlight");
                startActivityForResult(intent,1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch(requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    String op=intent.getStringExtra("op");
                    int key=intent.getIntExtra("key",0);
                    if(pageturnKey==key){
                        pageturnKey=0;
                    }
                    if(writeKey==key){
                        writeKey=0;
                    }
                    if(settingKey==key){
                        settingKey=0;
                    }
                    if(nightKey==key){
                        nightKey=0;
                    }
                    if(eraserKey==key){
                        eraserKey=0;
                    }
                    if(highlightKey==key){
                        highlightKey=0;
                    }


                    if(op.equals("pageturn")){
                        pageturnKey=key;
                    }
                    else if(op.equals("write")){
                        writeKey=key;
                    }
                    else if(op.equals("setting")){
                        settingKey=key;
                    }
                    else if(op.equals("night")){
                        nightKey=key;
                    }
                    else if(op.equals("eraser")){
                        eraserKey=key;
                    }
                    else if(op.equals("highlight")){
                        highlightKey=key;
                    }
                    pageturnText.setText(data.getFinger(pageturnKey));
                    writeText.setText(data.getFinger(writeKey));
                    settingText.setText(data.getFinger(settingKey));
                    nightText.setText(data.getFinger(nightKey));
                    eraserText.setText(data.getFinger(eraserKey));
                    highlightText.setText(data.getFinger(highlightKey));
                }
                break;
            default:
                return;
        }
    }
}
