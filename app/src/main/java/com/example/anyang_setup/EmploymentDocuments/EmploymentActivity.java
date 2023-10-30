package com.example.anyang_setup.EmploymentDocuments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.anyang_setup.EmploymentDocuments.SubActivity.Personal.PersonalMainActivity;
import com.example.anyang_setup.EmploymentDocuments.SubActivity.Spec.SpecActivity;
import com.example.anyang_setup.EmploymentDocuments.SubActivity.Resume.ResumeMainActivity;
import com.example.anyang_setup.R;
import com.example.anyang_setup.test.MainActivity_start;

public class EmploymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employment);

        // 버튼을 찾아서 클릭 리스너를 설정합니다.
        Button button11 = findViewById(R.id.personal_button); // 자기소개서 버튼
        Button button12 = findViewById(R.id.spec_button); // 스펙 버튼
        Button button13 = findViewById(R.id.resume_button); // 이력서 버튼
        Button employmentButton = findViewById(R.id.employment_button);

        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Personal Statement Activity로 이동하는 코드를 추가합니다.
                Intent intent = new Intent(EmploymentActivity.this, PersonalMainActivity.class);
                startActivity(intent);
            }
        });

        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SpecActivity로 이동하는 코드를 추가합니다.
                Intent intent = new Intent(EmploymentActivity.this, SpecActivity.class);
                startActivity(intent);
            }
        });

        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ResumeMainActivity로 이동하는 코드를 추가합니다.
                Intent intent = new Intent(EmploymentActivity.this, MainActivity_start.class);
                startActivity(intent);
            }
        });

        employmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent를 사용하여 브라우저 열기
                String url = "https://www.jobkorea.co.kr/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}
