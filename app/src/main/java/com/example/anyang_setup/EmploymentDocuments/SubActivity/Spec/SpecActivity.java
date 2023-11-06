package com.example.anyang_setup.EmploymentDocuments.SubActivity.Spec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import com.example.anyang_setup.EmploymentDocuments.SubActivity.Spec.SpecDB.AwardsInsertRequest;
import com.example.anyang_setup.EmploymentDocuments.SubActivity.Spec.SpecDB.CertificateInsertRequest;
import com.example.anyang_setup.EmploymentDocuments.SubActivity.Spec.SpecDB.ExternalInsertRequest;
import com.example.anyang_setup.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class SpecActivity extends AppCompatActivity {

    private LinearLayout certificationTable;
    private Button addCertificationButton;
    private List<String> certificationDataList;

    private LinearLayout externalActivitiesTable;
    private Button addExternalActivitiesButton;
    private List<String> externalActivitiesDataList;

    private LinearLayout awardsTable;
    private Button addAwardsButton;
    private List<String> awardsDataList;

    private String certificate; // 자격증
    private String externalActivities; //대외활동
    private String awards; //수상경력
    private String ID; //학번
    private String userInfoStr;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spec);

        certificationTable = findViewById(R.id.certificationTable);
        addCertificationButton = findViewById(R.id.addCertificationButton);
        certificationDataList = new ArrayList<>();

        externalActivitiesTable = findViewById(R.id.external_activitiesTable);
        addExternalActivitiesButton = findViewById(R.id.add_external_activitiesButton);
        externalActivitiesDataList = new ArrayList<>();

        awardsTable = findViewById(R.id.awardsTable);
        addAwardsButton = findViewById(R.id.addAwardsButton);
        awardsDataList = new ArrayList<>();

        userInfoStr = getIntent().getStringExtra("userinfo");

        try{
            JSONObject jsonObject = new JSONObject(userInfoStr);
            JSONObject dataObj = jsonObject.getJSONObject("data");
            ID = dataObj.getString("stdId");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        addCertificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CertificateActivity를 호출하여 데이터를 입력받습니다.
                Intent intent = new Intent(SpecActivity.this, CertificateActivity.class);
                startActivityForResult(intent, 1); // requestCode
            }
        });

        addExternalActivitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CertificateActivity를 호출하여 데이터를 입력받습니다.
                Intent intent = new Intent(SpecActivity.this, ExternalActivitiesActivity.class);
                startActivityForResult(intent, 2); // requestCode
            }
        });

        addAwardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CertificateActivity를 호출하여 데이터를 입력받습니다.
                Intent intent = new Intent(SpecActivity.this, AwardsActivity.class);
                startActivityForResult(intent, 3);
            }
        });

        // 액티비티가 시작될 때 저장된 데이터를 표시합니다.
        for (String certification : certificationDataList) {
            addCertificationRow(certification);
        }

        for (String externalActivities : externalActivitiesDataList) {
            addExternalActivitiesRow(externalActivities);
        }

        for (String award : awardsDataList) {
            addAwardsRow(award);
        }

        new UpdateSpec_ExternalActivity().execute(ID);
        new UpdateSpec_certificate().execute(ID);
        new UpdateSpec_awards().execute(ID);

    }


    private void addCertificationRow(String text) {
        // TableRow를 생성합니다.
        TableRow newRow = new TableRow(this);

        // 새로운 TextView를 생성하고 텍스트를 설정합니다.
        TextView newCertificationTextView = new TextView(this);
        newCertificationTextView.setText(text);
        newCertificationTextView.setTextSize(20);

        // TextView의 텍스트를 가운데 정렬합니다.
        newCertificationTextView.setGravity(Gravity.CENTER); // 가운데 정렬

        // TextView를 TableRow에 추가합니다.
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        newRow.addView(newCertificationTextView, params);

        // TableRow를 certificationTable에 추가합니다.
        certificationTable.addView(newRow);

        executeServerInsertRequest(certificate);
    }

    private void addExternalActivitiesRow(String text) {
        // TableRow를 생성합니다.
        TableRow newRow = new TableRow(this);

        // 새로운 TextView를 생성하고 텍스트를 설정합니다.
        TextView newExternalActivitiesTextView = new TextView(this);
        newExternalActivitiesTextView.setText(text);
        newExternalActivitiesTextView.setTextSize(20);

        // TextView의 텍스트를 가운데 정렬합니다.
        newExternalActivitiesTextView.setGravity(Gravity.CENTER); // 가운데 정렬

        // TextView를 TableRow에 추가합니다.
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        newRow.addView(newExternalActivitiesTextView, params);
        // TableRow를 externalActivitiesTable에 추가합니다.
        externalActivitiesTable.addView(newRow);

        executeServerInsertRequest(externalActivities);
    }

    private void addAwardsRow(String text) {
        // TableRow를 생성합니다.
        TableRow newRow = new TableRow(this);

        // 새로운 TextView를 생성하고 텍스트를 설정합니다.
        TextView newAwardsTextView = new TextView(this);
        newAwardsTextView.setText(text);
        newAwardsTextView.setTextSize(20);

        // TextView의 텍스트를 가운데 정렬합니다.
        newAwardsTextView.setGravity(Gravity.CENTER); // 가운데 정렬

        // TextView를 TableRow에 추가합니다.
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        newRow.addView(newAwardsTextView, params);

        // TableRow를 awardsTable에 추가합니다.
        awardsTable.addView(newRow);

        executeServerInsertRequest(awards);
    }
    private void updateCertificationRow(String text) {
        // TableRow를 생성합니다.
        TableRow newRow = new TableRow(this);

        // 새로운 TextView를 생성하고 텍스트를 설정합니다.
        TextView newCertificationTextView = new TextView(this);
        newCertificationTextView.setText(text);
        newCertificationTextView.setTextSize(20);

        // TextView의 텍스트를 가운데 정렬합니다.
        newCertificationTextView.setGravity(Gravity.CENTER); // 가운데 정렬

        // TextView를 TableRow에 추가합니다.
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        newRow.addView(newCertificationTextView, params);

        // TableRow를 certificationTable에 추가합니다.
        certificationTable.addView(newRow);

    }

    private void updateExternalActivitiesRow(String text) {
        // TableRow를 생성합니다.
        TableRow newRow = new TableRow(this);

        // 새로운 TextView를 생성하고 텍스트를 설정합니다.
        TextView newExternalActivitiesTextView = new TextView(this);
        newExternalActivitiesTextView.setText(text);
        newExternalActivitiesTextView.setTextSize(20);

        // TextView의 텍스트를 가운데 정렬합니다.
        newExternalActivitiesTextView.setGravity(Gravity.CENTER); // 가운데 정렬

        // TextView를 TableRow에 추가합니다.
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        newRow.addView(newExternalActivitiesTextView, params);
        // TableRow를 externalActivitiesTable에 추가합니다.
        externalActivitiesTable.addView(newRow);


    }



    private void updateAwardsRow(String text) {
        // TableRow를 생성합니다.
        TableRow newRow = new TableRow(this);

        // 새로운 TextView를 생성하고 텍스트를 설정합니다.
        TextView newAwardsTextView = new TextView(this);
        newAwardsTextView.setText(text);
        newAwardsTextView.setTextSize(20);

        // TextView의 텍스트를 가운데 정렬합니다.
        newAwardsTextView.setGravity(Gravity.CENTER); // 가운데 정렬

        // TextView를 TableRow에 추가합니다.
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        newRow.addView(newAwardsTextView, params);

        // TableRow를 awardsTable에 추가합니다.
        awardsTable.addView(newRow);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            // CertificateActivity에서 전달된 데이터를 가져옵니다.
            certificate = data.getStringExtra("certification");
            externalActivities = data.getStringExtra("externalActivities");
            awards = data.getStringExtra("awards");
            // requestCode에 따라 데이터를 저장하고 표시합니다.
            if (requestCode == 1) {
                certificationDataList.add(certificate);
                addCertificationRow(certificate);

            } else if (requestCode == 2) {
                externalActivitiesDataList.add(externalActivities);
                addExternalActivitiesRow(externalActivities);
            }

            else if (requestCode == 3) {
                awardsDataList.add(awards);
                addAwardsRow(awards);
            }
        }
    }

    private void executeServerInsertRequest(String data) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");


                    if (success) {
                        Toast.makeText(getApplicationContext(), "데이터 삽입 성공", Toast.LENGTH_SHORT).show();
                        // Server request failed
                    } else {
                        Toast.makeText(getApplicationContext(), "데이터 삽입 실패", Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(SpecActivity.this);

        AwardsInsertRequest awardsRequest = new AwardsInsertRequest(ID, awards, responseListener);
        requestQueue.add(awardsRequest);

        CertificateInsertRequest certificateRequest = new CertificateInsertRequest(ID, certificate, responseListener);
        requestQueue.add(certificateRequest);

        ExternalInsertRequest externalRequest = new ExternalInsertRequest(ID, externalActivities, responseListener);
        requestQueue.add(externalRequest);

    }





    private class UpdateSpec_ExternalActivity extends AsyncTask<String, Void, List<String>> {
        @Override
        protected List<String> doInBackground(String... arg0) {
            List<String> resultList = new ArrayList<String>();
            try {
                String id = arg0[0];

                OkHttpClient client = new OkHttpClient();
                String link = "http://qkrwodbs.dothome.co.kr/Select_externalActivities.php";
                Request request = new Request.Builder()
                        .url(link + "?ID=" + id)
                        .build();
                okhttp3.Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String responseString = response.body().string();
                    // 여러 결과 행을 처리하는 방법에 따라서 responseString을 파싱하여 resultList에 추가합니다.
                    // 예를 들어, 각 행이 콤마로 구분된 경우:
                    String[] rows = responseString.split(";");
                    resultList.addAll(Arrays.asList(rows));
                }
            } catch (IOException e) {
                // 예외 처리
                e.printStackTrace();
            }
            return resultList;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            // result에는 여러 결과 행이 포함됩니다.
            for (String row : result) {
                updateExternalActivitiesRow(row);
            }
        }
    }




    private class UpdateSpec_certificate extends AsyncTask<String, Void, List<String>> {
        @Override
        protected List<String> doInBackground(String... arg0) {
            List<String> resultList = new ArrayList<String>();
            try {
                String id = arg0[0];

                OkHttpClient client = new OkHttpClient();
                String link = "http://qkrwodbs.dothome.co.kr/Select_certificate.php";
                Request request = new Request.Builder()
                        .url(link + "?ID=" + id)
                        .build();
                okhttp3.Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String responseString = response.body().string();
                    // 여러 결과 행을 처리하는 방법에 따라서 responseString을 파싱하여 resultList에 추가합니다.
                    // 예를 들어, 각 행이 콤마로 구분된 경우:
                    String[] rows = responseString.split(";");
                    resultList.addAll(Arrays.asList(rows));
                }
            } catch (IOException e) {
                // 예외 처리
                e.printStackTrace();
            }
            return resultList;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            // result에는 여러 결과 행이 포함됩니다.
            for (String row : result) {
                updateCertificationRow(row);
            }
        }
    }

    private class UpdateSpec_awards extends AsyncTask<String, Void, List<String>> {
        @Override
        protected List<String> doInBackground(String... arg0) {
            List<String> resultList = new ArrayList<String>();
            try {
                String id = arg0[0];

                OkHttpClient client = new OkHttpClient();
                String link = "http://qkrwodbs.dothome.co.kr/Select_awards.php";
                Request request = new Request.Builder()
                        .url(link + "?ID=" + id)
                        .build();
                okhttp3.Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String responseString = response.body().string();
                    // 여러 결과 행을 처리하는 방법에 따라서 responseString을 파싱하여 resultList에 추가합니다.
                    // 예를 들어, 각 행이 콤마로 구분된 경우:
                    String[] rows = responseString.split(";");
                    resultList.addAll(Arrays.asList(rows));
                }
            } catch (IOException e) {
                // 예외 처리
                e.printStackTrace();
            }
            return resultList;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            // result에는 여러 결과 행이 포함됩니다.
            for (String row : result) {
                updateAwardsRow(row);
            }
        }
    }

}