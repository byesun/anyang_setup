package com.example.anyang_setup.Resume_test.datamodel.EmploymentDocuments.SubActivity.Personal;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.anyang_setup.Resume_test.datamodel.EmploymentDocuments.SubActivity.Personal.PersonalDB.PersonalInsertRequest;
import com.example.anyang_setup.GlobalVariables;
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


        ID = GlobalVariables.getGlobalVariable_id();





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
                showAlertDialog();

            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("저장되지않았습니다. 나가시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(PersonalWriteActivity.this, PersonalMainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })

                .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // "아니요" 버튼이 눌렸을 때 다이얼로그를 닫음
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("저장"); // 다이얼로그 제목
        builder.setMessage("저장하시겠습니까?"); // 다이얼로그 내용

        // "확인" 버튼을 눌렀을 때의 동작
        builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                personalTitle = personal_title.getText().toString();
                personalText = editText.getText().toString();
                executeServerInsertRequest(personalTitle, personalText);
                // Intent를 생성하여 PersonalLockerActivity로 전달
                Intent intent = new Intent(PersonalWriteActivity.this, PersonalMainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void executeServerInsertRequest(String data, String data_2) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

                    if (success) {
                        Toast.makeText(getApplicationContext(), "작성한 자기소개서가 저장되었습니다", Toast.LENGTH_SHORT).show();

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