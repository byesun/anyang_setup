package com.example.anyang_setup.EmploymentDocuments.SubActivity.Resume;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.anyang_setup.R;
import com.example.anyang_setup.test.MainActivity_start;

public class ResumeMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_main);


        findViewById(R.id.resume_write_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // PersonalWriteActivity로 이동하는 코드를 추가합니다.
                Intent intent = new Intent(ResumeMainActivity.this, MainActivity_start.class);
                startActivity(intent);
            }
        });



        findViewById(R.id.resume_loocker_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // PersonalWriteActivity로 이동하는 코드를 추가합니다.
                Intent intent = new Intent(ResumeMainActivity.this, ResumeLockerActivity.class);
                startActivity(intent);
            }
        });
    }
}
