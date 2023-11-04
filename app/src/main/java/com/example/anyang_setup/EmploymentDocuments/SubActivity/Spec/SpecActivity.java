package com.example.anyang_setup.EmploymentDocuments.SubActivity.Spec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.anyang_setup.EmploymentDocuments.SubActivity.Spec.SpecDB.AwardsSelectRequest;
import com.example.anyang_setup.EmploymentDocuments.SubActivity.Spec.SpecDB.CertificateInsertRequest;
import com.example.anyang_setup.EmploymentDocuments.SubActivity.Spec.SpecDB.CertificateSelectRequest;
import com.example.anyang_setup.EmploymentDocuments.SubActivity.Spec.SpecDB.ExternalInsertRequest;
import com.example.anyang_setup.EmploymentDocuments.SubActivity.Spec.SpecDB.ExternalSelectRequest;
import com.example.anyang_setup.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
        executeServerSelectRequest(ID);
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


    private void executeServerSelectRequest(String ID) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

                    if (success) {
                        awards = jsonObject.getString("awards");
                        certificate = jsonObject.getString("certificate");
                        externalActivities = jsonObject.getString("externalActivities");

                        // awardsDataList, certificationDataList, externalActivitiesDataList에 데이터 추가
                        if (!TextUtils.isEmpty(awards)) {
                            awardsDataList.add(awards);
                            addAwardsRow(awards);
                        }

                        if (!TextUtils.isEmpty(certificate)) {
                            certificationDataList.add(certificate);
                            addCertificationRow(certificate);
                        }

                        if (!TextUtils.isEmpty(externalActivities)) {
                            externalActivitiesDataList.add(externalActivities);
                            addExternalActivitiesRow(externalActivities);
                        }

                        Toast.makeText(getApplicationContext(), "데이터 가져오기 성공", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "데이터 가져오기 실패", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(SpecActivity.this);

        // Awards, Certificate, External Activities 데이터를 가져올 서버 요청을 보냅니다.
        AwardsSelectRequest awardsRequest = new AwardsSelectRequest(ID, responseListener);
        requestQueue.add(awardsRequest);

        CertificateSelectRequest certificateRequest = new CertificateSelectRequest(ID, responseListener);
        requestQueue.add(certificateRequest);

        ExternalSelectRequest externalRequest = new ExternalSelectRequest(ID, responseListener);
        requestQueue.add(externalRequest);
    }

}