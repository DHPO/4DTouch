package com.example.xzt.pdfreader;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);

        pageturnKey = Data.pageturnKey;
        writeKey=Data.writeKey;
        settingKey=Data.settingKey;

        Button backButton=(Button)findViewById(R.id.title_back);
        Button saveButton=(Button)findViewById(R.id.title_save);
        Button pageturnEditButton=(Button)findViewById(R.id.setting_pageturn_button);
        Button writeEditButton=(Button)findViewById(R.id.setting_write_button);
        Button settingEditButton=(Button)findViewById(R.id.setting_setting_button);

        TextView pageturnText=(TextView)findViewById(R.id.setting_pageturn_text);
        TextView writeText=(TextView)findViewById(R.id.setting_write_text);
        TextView settingText=(TextView)findViewById(R.id.setting_setting_text);

        final Data data=(Data)getApplication();
        pageturnText.setText(data.getFinger(pageturnKey));
        writeText.setText(data.getFinger(writeKey));
        settingText.setText(data.getFinger(settingKey));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data.pageturnKey=pageturnKey;
                Data.writeKey=writeKey;
                Data.settingKey=settingKey;
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    String op=data.getStringExtra("op");
                    int key=data.getIntExtra("key",0);
                    if(op.equals("pageturn")){
                        pageturnKey=key;
                    }
                    else if(op.equals("write")){
                        writeKey=key;
                    }
                    else if(op.equals("setting")){
                        settingKey=key;
                    }
                }
                break;
            default:
                return;
        }

    }

}
