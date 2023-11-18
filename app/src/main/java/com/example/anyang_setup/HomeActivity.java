/*
package com.example.anyang_setup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.anyang_setup.Chatting.ChatActivity;
import com.example.anyang_setup.EmploymentDocuments.Chatting.ChatActivity;
import com.example.anyang_setup.EmploymentDocuments.EmploymentActivity;
import com.example.anyang_setup.Info.SubActivity.DiagnosisActivity;
import com.example.anyang_setup.Info.UserInfoActivity;
import com.example.anyang_setup.Setting.SettingActivity;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity{

    private NavigationBarView navigationBarView;
    private String userinfo;
    private JSONObject userInfoJson;

    private Button button;
    private TextView getScoreText, remainScoreText, majorScoreText, generalScoreText;
    private TextView stdNameText, stdIdText, majorText;
    private String userInfoStr;
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
            Intent intent = new Intent(HomeActivity.this, DiagnosisActivity.class);
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

        setContentView(R.layout.activity_home);

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
                        Intent intent = new Intent(HomeActivity.this, UserInfoActivity.class);
                        intent.putExtra("userinfo", userinfo);
                        startActivity(intent);
                        break;
                    }
                    case R.id.setting :
                    {
                        Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
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

                            Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
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
*/
