package com.example.anyang_setup.EmploymentDocuments.SubActivity.Personal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.anyang_setup.EmploymentDocuments.SubActivity.Personal.PersonalDB.PersonalInsertRequest;
import com.example.anyang_setup.R;

import org.json.JSONException;
import org.json.JSONObject;


public class PersonalWriteActivity extends AppCompatActivity {


    private String userInfoStr;
    private String ID;
    private String personalTitle; // 제목
    private String personalText; //자기소개서


    private EditText personal_title;
    private EditText editText;
    private Button resetButton;
    private Button saveButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_write);

        personal_title = findViewById(R.id.personal_title);
        editText = findViewById(R.id.personal_text);
        resetButton = findViewById(R.id.personal_write_reset_button);
        saveButton = findViewById(R.id.personal_write_save_button);


        userInfoStr = getIntent().getStringExtra("userinfo");

        try{
            JSONObject jsonObject = new JSONObject(userInfoStr);
            JSONObject dataObj = jsonObject.getJSONObject("data");
            ID = dataObj.getString("stdId");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
                personal_title.setText("");
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personalTitle = personal_title.getText().toString();
                personalText = editText.getText().toString();


                executeServerInsertRequest(personalTitle,personalText);

                // Intent를 생성하여 PersonalLockerActivity로 전달
                Intent intent = new Intent(PersonalWriteActivity.this, PersonalMainActivity.class);

                // 데이터를 intent에 담아 전달
                intent.putExtra("personalTitle", personalTitle);
                intent.putExtra("personalText", personalText);
                startActivity(intent);

            }
        });
    }

    private void executeServerInsertRequest(String data, String data_2) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

                    if (success) {
                        Toast.makeText(getApplicationContext(), "데이터 삽입 성공", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "데이터 삽입 실패", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(PersonalWriteActivity.this);
        PersonalInsertRequest personalRequest = new PersonalInsertRequest(ID, personalTitle, personalText, responseListener);
        requestQueue.add(personalRequest);
    }



}
