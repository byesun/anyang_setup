package com.example.anyang_setup.Info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.anyang_setup.EmploymentDocuments.EmploymentActivity;
import com.example.anyang_setup.Info.SubActivity.DiagnosisActivity;
import com.example.anyang_setup.R;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.view.MenuItem;

import com.example.anyang_setup.Chatting.ChatActivity;
import com.example.anyang_setup.Setting.SettingActivity;
import com.google.android.material.navigation.NavigationBarView;


public class UserInfoActivity extends AppCompatActivity {
    private Button button;
    private TextView getScoreText, remainScoreText, majorScoreText, generalScoreText;
    private TextView stdNameText, stdIdText, majorText;
    private String userInfoStr;

    //homeactivity.java
    private NavigationBarView navigationBarView;

    private String userinfo;

    private JSONObject userInfoJson;

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
        } catch (JSONException e) {
            e.printStackTrace();
        }

        navigationBarView = findViewById(R.id.bottom_navigationview);
        userinfo = getIntent().getStringExtra("userinfo");

        try {
            userInfoJson = new JSONObject(userinfo);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

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
                    case R.id.spec :
                    {
                        Intent intent = new Intent(UserInfoActivity.this, EmploymentActivity.class);
                        intent.putExtra("userinfo", userinfo);
                        startActivity(intent);
                        break;
                    }
                }

                return true;
            }
        });
    }
}