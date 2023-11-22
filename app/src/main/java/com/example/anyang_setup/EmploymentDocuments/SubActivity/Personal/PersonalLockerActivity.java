package com.example.anyang_setup.EmploymentDocuments.SubActivity.Personal;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.anyang_setup.GlobalVariables;
import com.example.anyang_setup.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class PersonalLockerActivity extends Activity {
    private ListView personalList;
    private int selectedItem = -1;
    private ArrayAdapter<String> adapter;
    private String id;
    private String personalTitle;
    private String personalText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_locker);

        // ListView 설정
        personalList = findViewById(R.id.personal_list);


        // 어뎁터 설정
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        personalList.setAdapter(adapter);

        id = GlobalVariables.getGlobalVariable_id();

        // 리스트에 자기소개서(제목) 가져오기
        new updatelist().execute(id);




        // ListView 아이템 클릭 리스너 설정
        personalList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 선택한 항목을 토글
                if (position == selectedItem) {
                    selectedItem = -1; // 같은 항목을 다시 클릭하면 선택 해제
                } else {
                    selectedItem = position;
                }

                // 모든 항목의 배경색 초기화
                for (int i = 0; i < personalList.getChildCount(); i++) {
                    View listItem = personalList.getChildAt(i);
                    if (i != selectedItem) {
                        listItem.setBackgroundColor(Color.TRANSPARENT);
                    } else {
                        listItem.setBackgroundColor(Color.GRAY);
                    }
                }
            }
        });



        Button Personal_Edit_button = findViewById(R.id.Personal_Edit_button);
        Personal_Edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedItem == -1) {
                    Toast.makeText(PersonalLockerActivity.this, "편집할 자기소개서를 선택해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    GoToEdit();
                }
            }
        });

        Button delete_list = findViewById(R.id.delete_list);
        delete_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedItem == -1) {
                    Toast.makeText(PersonalLockerActivity.this, "삭제할 자기소개서를 선택해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    delete_list();
                }
            }
        });
    }

    private void GoToEdit() {
        if (selectedItem != -1 && selectedItem < adapter.getCount()) {
            String selectedText = adapter.getItem(selectedItem);
            if (selectedText != null) {
                Intent intent = new Intent(PersonalLockerActivity.this, PersonalEditActivity.class);
                intent.putExtra("STID",id);
                intent.putExtra("selectedText", selectedText);
                startActivity(intent);
                finish();
            }
        } else {
            Toast.makeText(this, "리스트를 선택해주세요", Toast.LENGTH_SHORT).show();
        }
    }
    private void delete_list() {
        if (selectedItem != -1) {
            new AlertDialog.Builder(this)
                    .setMessage("정말 삭제하시겠습니까?")
                    .setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String selectedText = adapter.getItem(selectedItem);
                            new delete_personal().execute(id,selectedText);
                            adapter.remove(selectedText);
                            selectedItem = -1;
                        }
                    })
                    .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();

        } else {
            Toast.makeText(this, "리스트를 선택해주세요", Toast.LENGTH_SHORT).show();
        }
    }
    private class updatelist extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... arg0) {
            try {
                String id = arg0[0];

                OkHttpClient client = new OkHttpClient();
                String link = "http://qkrwodbs.dothome.co.kr/Select_personal_title.php";
                Request request = new Request.Builder()
                        .url(link + "?ID=" + id)
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
            try {
                // 결과가 비어있는지 확인
                if (result == null || result.isEmpty()) {
                    Toast.makeText(PersonalLockerActivity.this, "데이터가 없습니다", Toast.LENGTH_SHORT).show();
                    return;
                }

                JSONArray jsonArray = new JSONArray(result);
                ArrayList<String> List = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    String certificate = jsonArray.getString(i);
                    if (certificate != null) {
                        List.add(certificate);
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.clear();
                        adapter.addAll(List);
                        adapter.notifyDataSetChanged();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(PersonalLockerActivity.this, "JSON 파싱 오류: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }
    private class delete_personal extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... arg0) {
            try {
                String id = arg0[0];
                String title = arg0[1];

                OkHttpClient client = new OkHttpClient();
                String link = "http://qkrwodbs.dothome.co.kr/Delete_personal.php";
                Request request = new Request.Builder()
                        .url(link + "?ID=" + id + "&TITLE=" + title)
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
            // 삭제 후에 필요한 작업을 수행할 수 있습니다.
        }
    }
}