package com.example.anyang_setup.Resume_test.datamodel.EmploymentDocuments.SubActivity.Personal;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anyang_setup.GlobalVariables;
import com.example.anyang_setup.R;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class PersonalEditActivity extends AppCompatActivity {

    private String selectedText;
    private String ID;

    private EditText personal_edit;
    private EditText title;



    private String userInfoStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_edit);

        Intent intent = getIntent();

        ID = GlobalVariables.getGlobalVariable_id();
        selectedText = intent.getStringExtra("selectedText");


        //edit텍스트에 저장되어있던 자소서 본문을 넣어주는코드
        new update_personal().execute(ID,selectedText);


        Button Personal_Edit_button = findViewById(R.id.personal_out_button);
        Personal_Edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitDialog();
            }
        });

        Button Personal_Save_button = findViewById(R.id.personal_save_button);
        Personal_Save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savecheck();
            }
        });


    }

    //뒤로가기버튼 처리
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("수정한내역이 저장되지않았습니다. 나가시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(PersonalEditActivity.this, PersonalLockerActivity.class);
                        intent.putExtra("STID",ID);
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

    // 다이얼로그를 표시하는 메소드
    private void showExitDialog() {
        new AlertDialog.Builder(this)
                .setMessage("수정한내역이 저장되지않았습니다. 나가시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(PersonalEditActivity.this, PersonalLockerActivity.class);
                        intent.putExtra("STID",ID);
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

    // db에서 본문 가져와서 edittext에 띄우는 메소드
    private class update_personal extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... arg0) {
            try {
                String id = arg0[0];
                String title = arg0[1];

                OkHttpClient client = new OkHttpClient();
                String link = "http://qkrwodbs.dothome.co.kr/Update_personal.php";
                Request request = new Request.Builder()
                        .url(link + "?ID=" + ID + "&TITLE=" + title)
                        .build();
                okhttp3.Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    return response.body().string();
                } else {
                    return "HTTP 요청 실패";
                }
            } catch (IOException e) {
                return "예외 발생: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            personal_edit = findViewById(R.id.personal_edit);
            personal_edit.setText(result);
            title = findViewById(R.id.title_edit);
            title.setText(selectedText);
        }
    }

    //저장하기전 확인하는메소드
    private void savecheck() {
        new AlertDialog.Builder(this)
                .setMessage("저장하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        title = findViewById(R.id.title_edit);
                        String title_save = title.getText().toString();
                        personal_edit = findViewById(R.id.personal_edit);
                        String save = personal_edit.getText().toString();
                        new save_personal().execute(ID, selectedText, save, title_save);
                        Toast.makeText(PersonalEditActivity.this, "수정한 내용이 저장되었습니다", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PersonalEditActivity.this, PersonalLockerActivity.class);
                        intent.putExtra("STID",ID);
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


    // 수정한내역을 저장하는 메소드
    private class save_personal extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... arg0) {
            try {
                String id = arg0[0];
                String title = arg0[1];
                String personal = arg0[2];
                String title_save = arg0[3];

                OkHttpClient client = new OkHttpClient();
                String link = "http://qkrwodbs.dothome.co.kr/Save_personal.php";
                Request request = new Request.Builder()
                        .url(link + "?ID=" + ID + "&TITLE=" + title + "&PERSONAL=" + personal +"&TITLESAVE=" + title_save)
                        .build();
                okhttp3.Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    return response.body().string();
                } else {
                    return "HTTP 요청 실패";
                }
            } catch (IOException e) {
                return "예외 발생: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }

}