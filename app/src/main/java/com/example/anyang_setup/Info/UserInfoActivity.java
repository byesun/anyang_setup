package com.example.anyang_setup.Info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.anyang_setup.EmploymentDocuments.SubActivity.Personal.PersonalMainActivity;
import com.example.anyang_setup.EmploymentDocuments.SubActivity.Spec.SpecActivity;
import com.example.anyang_setup.Info.SubActivity.DiagnosisActivity;
import com.example.anyang_setup.R;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.view.MenuItem;

import com.example.anyang_setup.EmploymentDocuments.Chatting.ChatActivity;
import com.example.anyang_setup.Setting.SettingActivity;
import com.example.anyang_setup.MakingResume.MainActivity_start;
import com.google.android.material.navigation.NavigationBarView;


public class UserInfoActivity extends AppCompatActivity {
    private Button button;
    private TextView getScoreText, remainScoreText, majorScoreText, generalScoreText;
    private TextView stdNameText, stdIdText, majorText;
    private TextView asd;
    private String userInfoStr;

    //homeactivity.java
    private NavigationBarView navigationBarView;

    private String userinfo;

    private JSONObject userInfoJson;

    @SuppressLint({"MissingInflatedId", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        userInfoStr = getIntent().getStringExtra("userinfo");
        button = findViewById(R.id.diagnosis);
        getScoreText = findViewById(R.id.Earned_Credits);
        remainScoreText = findViewById(R.id.Remaining_Credits);
        majorScoreText = findViewById(R.id.major_Credits);
        generalScoreText = findViewById(R.id.Liberal_Arts_Credits);
        stdNameText = findViewById(R.id.Name);
        stdIdText = findViewById(R.id.StudentID);
        majorText = findViewById(R.id.Major);

        // 버튼을 찾아서 클릭 리스너를 설정합니다.
        RelativeLayout button11 = findViewById(R.id.personal_button);// 자기소개서 버튼
        RelativeLayout button12 = findViewById(R.id.spec_button);// 스펙 버튼
        RelativeLayout button13 = findViewById(R.id.resume_button);// 이력서 버튼
        RelativeLayout employmentButton = findViewById(R.id.employment_button);// 채용공고

        button.setOnClickListener(view -> {
            Intent intent = new Intent(UserInfoActivity.this, DiagnosisActivity.class);
            intent.putExtra("userinfo", userInfoStr);
            startActivity(intent);
        });

        try {
            JSONObject jsonObject = new JSONObject(userInfoStr);
            JSONObject dataObj = jsonObject.getJSONObject("data");
            JSONObject creditStatus = dataObj.getJSONObject("creditStatus");
            getScoreText.setText(Integer.toString(creditStatus.getInt("total_current")));
            remainScoreText.setText(Integer.toString(creditStatus.getInt("total_remain")));
            majorScoreText.setText(Integer.toString(creditStatus.getInt("major_current")));
            generalScoreText.setText(Integer.toString(creditStatus.getInt("general_current")));
            stdNameText.setText(dataObj.getString("stdName"));
            stdIdText.setText(dataObj.getString("stdId"));
            majorText.setText(dataObj.getString("stdDepart"));
            asd.setText(dataObj.getString("stdsigan"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        userinfo = getIntent().getStringExtra("userinfo");

        try {
            userInfoJson = new JSONObject(userinfo);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Log.d("rkdqudtjs", "userinfo: " + userinfo);

        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Personal Statement Activity로 이동하는 코드를 추가합니다.
                Intent intent = new Intent(UserInfoActivity.this, PersonalMainActivity.class);
                startActivity(intent);
            }
        });

        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SpecActivity로 이동하는 코드를 추가합니다.
                Intent intent = new Intent(UserInfoActivity.this, SpecActivity.class);
                startActivity(intent);
            }
        });

        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ResumeMainActivity로 이동하는 코드를 추가합니다.
                Intent intent = new Intent(UserInfoActivity.this, MainActivity_start.class);
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

        navigationBarView = findViewById(R.id.bottom_navigationview);

        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener()
        {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.home :
                    {
                        Intent intent = new Intent(UserInfoActivity.this, UserInfoActivity.class);
                        intent.putExtra("userinfo", userinfo);
                        startActivity(intent);
                        break;
                    }
                    case R.id.setting :
                    {
                        Intent intent = new Intent(UserInfoActivity.this, SettingActivity.class);
                        intent.putExtra("userinfo", userinfo);
                        startActivity(intent);
                        break;
                    }
                    case R.id.info :
                    {
                        try {
                            JSONObject data = userInfoJson.getJSONObject("data");
                            String Name = data.getString("stdId");
                            Log.e("Telechips", Name);

                            Intent intent = new Intent(UserInfoActivity.this, ChatActivity.class);
                            intent.putExtra("name", Name);
                            startActivity(intent);
                        }
                        catch (JSONException e)
                        {
                            throw new RuntimeException(e);
                        }

                        break;
                    }
                }

                return true;
            }
        });
    }
}