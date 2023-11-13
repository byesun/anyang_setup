package com.example.anyang_setup.EmploymentDocuments.SubActivity.Personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.anyang_setup.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PersonalMainActivity extends AppCompatActivity {
    private String userinfo;
    private String userInfoStr;

    private String ID;

    private String STID;

    private String personalTitle; // 자기소개서 제목
    private String personalText; //자기소개서 내용
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_main);


        //자기소개서
        Intent intent = getIntent();
        STID = intent.getStringExtra("STID");


        // personal_write_button에 대한 클릭 리스너를 설정합니다.
        findViewById(R.id.personal_write_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // PersonalWriteActivity로 이동하는 코드를 추가합니다.
                Intent intent = new Intent(PersonalMainActivity.this, PersonalWriteActivity.class);

                intent.putExtra("STID",STID);

                intent.putExtra("userinfo", userInfoStr);
                startActivity(intent);
            }
        });

        findViewById(R.id.personal_gpt_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // PersonalWriteActivity로 이동하는 코드를 추가합니다.
                Intent intent = new Intent(PersonalMainActivity.this, PersonalGptActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.personal_loocker_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // PersonalWriteActivity로 이동하는 코드를 추가합니다.
                Intent intent = new Intent(PersonalMainActivity.this, PersonalLockerActivity.class);

                intent.putExtra("STID",STID);

                intent.putExtra("userinfo", userInfoStr);
                intent.putExtra("personalTitle", personalTitle);
                intent.putExtra("personalText", personalText);
                startActivity(intent);
            }
        });
    }
}
