package com.example.anyang_setup.EmploymentDocuments.SubActivity.Spec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.anyang_setup.EmploymentDocuments.SubActivity.Spec.SpecDB.AwardsInsertRequest;
import com.example.anyang_setup.EmploymentDocuments.SubActivity.Spec.SpecDB.CertificateInsertRequest;
import com.example.anyang_setup.EmploymentDocuments.SubActivity.Spec.SpecDB.ExternalInsertRequest;
import com.example.anyang_setup.GlobalVariables;
import com.example.anyang_setup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class SpecActivity extends AppCompatActivity {

    private LinearLayout certificationTable;
    private ImageButton addCertificationButton;
    private List<String> certificationDataList;


    private LinearLayout externalActivitiesTable;
    private ImageButton addExternalActivitiesButton;
    private List<String> externalActivitiesDataList;


    private LinearLayout awardsTable;
    private ImageButton addAwardsButton;
    private List<String> awardsDataList;


    private String certificate; // 자격증
    private String acquisitionDate; //자격증 취득일
    private String certificateFinal; //자격증 종합

    private String externalActivities; //대외활동
    private String externalDateStart; //대외활동 시작날짜
    private String externalDateEnd; //대외활동 종료날짜
    private String externalFinal; //대외활동 종합

    private String awards; //수상경력
    private String awardsDateStart; //수상경력 시작날짜
    private String awardsDateEnd; //수상경력 종료날짜
    private String awardsFinal; //수상경력 종합

    private String ID; //학번
    private String userInfoStr;
    private String certificateSelect;
    private String externalSelect;
    private String awardsSelect;

    private ImageButton deleteCertificationButton;
    private ImageButton deleteExternalActivitiesButton;
    private ImageButton deleteAwardsButton;


    private String lastCertificationRowData;




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


        deleteCertificationButton = findViewById(R.id.deleteCertificationButton);
        deleteExternalActivitiesButton = findViewById(R.id.delete_external_activitiesButton);
        deleteAwardsButton = findViewById(R.id.deleteAwardsButton);

        //GlobalVariables.setLastCertificationRowData(lastCertificationRowData);

        //lastCertificationRowData = getLastRowWithoutParentheses((TableLayout) certificationTable);


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

        //DB에서 불러오는 로직
        Update_certificate certificateTask = new Update_certificate();
        certificateTask.execute(ID);

        Update_externalActivities externalTask = new Update_externalActivities();
        externalTask.execute(ID);

        Update_awards awardsTask = new Update_awards();
        awardsTask.execute(ID);


        // Set onClick listeners for delete buttons
        deleteCertificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteLastRow((TableLayout) certificationTable);

                new delete_certificate().execute(ID, lastCertificationRowData);

                // Delete the last row from the table


                //Toast.makeText(getApplicationContext(), lastCertificationRowData, Toast.LENGTH_SHORT).show();

            }
        });



        deleteExternalActivitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteLastRow((TableLayout) externalActivitiesTable);
                new delete_external().execute(ID, lastCertificationRowData);


            }
        });

        deleteAwardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteLastRow((TableLayout) awardsTable);
                new delete_awards().execute(ID, lastCertificationRowData);

            }
        });

    }


    private void deleteLastRow(TableLayout table) {
        int rowCount = table.getChildCount();
        if (rowCount > 1) {
            // Get the last row data before removing
            TableRow lastRow = (TableRow) table.getChildAt(rowCount - 1);
            lastCertificationRowData = getRowData(lastRow);

            // Remove the last row from the table
            table.removeViewAt(rowCount - 1);


        } else {
            Toast.makeText(getApplicationContext(), "더 이상 삭제할 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getLastRowData(TableLayout table) {
        int rowCount = table.getChildCount();
        if (rowCount > 0) {
            TableRow lastRow = (TableRow) table.getChildAt(rowCount - 1);
            return getRowData(lastRow);
        }
        return "";
    }



    // Helper method to get the concatenated data of a TableRow
    private String getRowData(TableRow row) {
        StringBuilder rowData = new StringBuilder();
        int cellCount = row.getChildCount();

        for (int i = 0; i < cellCount; i++) {
            View cellView = row.getChildAt(i);

            if (cellView instanceof TextView) {
                String cellData = ((TextView) cellView).getText().toString();

                // Remove text inside parentheses and the parentheses themselves
                cellData = cellData.replaceAll("\\([^\\(]*\\)", "").trim();

                rowData.append(cellData);

                // If it's not the last cell, add a space
                if (i < cellCount - 1) {
                    rowData.append(" ");
                }
            }
        }

        return rowData.toString();
    }


    private void addCertificationRow(String text) {
        // TableRow를 생성합니다.
        TableRow newRow = new TableRow(this);

        // 새로운 TextView를 생성하고 텍스트를 설정합니다.
        TextView newCertificationTextView = new TextView(this);
        newCertificationTextView.setText(text);
        newCertificationTextView.setTextSize(14);

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


        //DB 삽입
        executeServerInsertRequest(certificate);
    }

    private void addExternalActivitiesRow(String text) {
        // TableRow를 생성합니다.
        TableRow newRow = new TableRow(this);

        // 새로운 TextView를 생성하고 텍스트를 설정합니다.
        TextView newExternalActivitiesTextView = new TextView(this);
        newExternalActivitiesTextView.setText(text);
        newExternalActivitiesTextView.setTextSize(14);

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

        //DB 삽입
        executeServerInsertRequest(externalActivities);
    }

    private void addAwardsRow(String text) {
        // TableRow를 생성합니다.
        TableRow newRow = new TableRow(this);

        // 새로운 TextView를 생성하고 텍스트를 설정합니다.
        TextView newAwardsTextView = new TextView(this);
        newAwardsTextView.setText(text);
        newAwardsTextView.setTextSize(14);

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

        //DB 삽입
        executeServerInsertRequest(awards);
    }


    private void updateCertificationRow(String text) {
        // TableRow를 생성합니다.
        TableRow newRow = new TableRow(this);

        // 새로운 TextView를 생성하고 텍스트를 설정합니다.
        TextView newCertificationTextView = new TextView(this);
        newCertificationTextView.setText(text);
        newCertificationTextView.setTextSize(14);

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
        newExternalActivitiesTextView.setTextSize(14);

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
        newAwardsTextView.setTextSize(14);

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
            acquisitionDate = data.getStringExtra("acquisitionDate");
            certificateFinal = certificate + "(" + acquisitionDate + ")";

            externalActivities = data.getStringExtra("externalActivities");
            externalDateStart = data.getStringExtra("externalDateStart");
            externalDateEnd = data.getStringExtra("externalDateEnd");
            externalFinal = externalActivities + "(" + externalDateStart + "~" + externalDateEnd + ")";

            awards = data.getStringExtra("awards");
            awardsDateStart = data.getStringExtra("awardsDateStart");
            awardsDateEnd = data.getStringExtra("awardsDateEnd");
            awardsFinal = awards + "(" + awardsDateStart + "~" + awardsDateEnd + ")";
            // requestCode에 따라 데이터를 저장하고 표시합니다.
            if (requestCode == 1) {
                certificationDataList.add(certificateFinal);
                addCertificationRow(certificateFinal);

            } else if (requestCode == 2) {
                externalActivitiesDataList.add(externalFinal);
                addExternalActivitiesRow(externalFinal);
            }

            else if (requestCode == 3) {
                awardsDataList.add(awardsFinal);
                addAwardsRow(awardsFinal);
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

        AwardsInsertRequest awardsRequest = new AwardsInsertRequest(ID, awards,awardsDateStart,awardsDateEnd, responseListener);
        requestQueue.add(awardsRequest);

        CertificateInsertRequest certificateRequest = new CertificateInsertRequest(ID, certificate,acquisitionDate, responseListener);
        requestQueue.add(certificateRequest);

        ExternalInsertRequest externalRequest = new ExternalInsertRequest(ID, externalActivities,externalDateStart,externalDateEnd, responseListener);
        requestQueue.add(externalRequest);

    }



    private class Update_certificate extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... arg0) {
            try {
                String id = arg0[0];

                OkHttpClient client = new OkHttpClient();
                String link = "http://qkrwodbs.dothome.co.kr/Select_certificate.php";
                Request request = new Request.Builder()
                        .url(link + "?ID=" + ID)
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
                JSONArray jsonArray = new JSONArray(result);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String certificate = jsonObject.getString("certificate");
                    String acquisitionDate = jsonObject.getString("acquisitionDate");


                    String concatenatedString = certificate + "(" + acquisitionDate + ")";
                    // Adding each certificate as a row to the table
                    updateCertificationRow(concatenatedString);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }






    private class Update_externalActivities extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... arg0) {
            try {
                String id = arg0[0];

                OkHttpClient client = new OkHttpClient();
                String link = "http://qkrwodbs.dothome.co.kr/Select_externalActivities.php";
                Request request = new Request.Builder()
                        .url(link + "?ID=" + ID)
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
                JSONArray jsonArray = new JSONArray(result);

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String externalActivities = jsonObject.getString("externalActivities");
                    String startDate = jsonObject.getString("startDate");
                    String endDate = jsonObject.getString("endDate");

                    String concatenatedString = externalActivities + "(" + startDate + "~" + endDate + ")";

                    // Adding each certificate as a row to the table
                    updateExternalActivitiesRow(concatenatedString);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    private class Update_awards extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... arg0) {
            try {
                String id = arg0[0];

                OkHttpClient client = new OkHttpClient();
                String link = "http://qkrwodbs.dothome.co.kr/Select_awards.php";
                Request request = new Request.Builder()
                        .url(link + "?ID=" + ID)
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
                JSONArray jsonArray = new JSONArray(result);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String awards = jsonObject.getString("awards");
                    String startDate = jsonObject.getString("startDate");
                    String endDate = jsonObject.getString("endDate");

                    String concatenatedString = awards + "(" + startDate + "~" + endDate + ")";

                    // Adding each concatenated string as a row to the table
                    updateAwardsRow(concatenatedString);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



    }
    /*
    private class Update_externalActivities extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... arg0) {
            try {
                String id = arg0[0];

                OkHttpClient client = new OkHttpClient();
                String link = "http://qkrwodbs.dothome.co.kr/Select_externalActivities.php";
                Request request = new Request.Builder()
                        .url(link + "?ID=" + ID)
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
                JSONArray jsonArray = new JSONArray(result);

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String externalActivities = jsonObject.getString("externalActivities");
                    String startDate = jsonObject.getString("startDate");
                    String endDate = jsonObject.getString("endDate");

                    String concatenatedString = externalActivities + "(" + startDate + "~" + endDate + ")";

                    // Adding each certificate as a row to the table
                    updateExternalActivitiesRow(concatenatedString);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

*/
    private class delete_certificate extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... arg0) {
            try {
                String id = arg0[0];
                String data = arg0[1];

                OkHttpClient client = new OkHttpClient();
                String link = "http://qkrwodbs.dothome.co.kr/delete_certificate.php";
                Request request = new Request.Builder()
                        .url(link + "?ID=" + id + "&lastCertificationRowData=" + data)
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
                JSONArray jsonArray = new JSONArray(result);

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String lastCertificationRowData = jsonObject.getString("lastCertificationRowData");
                    String ID = jsonObject.getString("ID");

                    // Adding each certificate as a row to the table
                    //updateExternalActivitiesRow(concatenatedString);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    private class delete_awards extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... arg0) {
            try {
                String id = arg0[0];
                String data = arg0[1];

                OkHttpClient client = new OkHttpClient();
                String link = "http://qkrwodbs.dothome.co.kr/delete_awards.php";
                Request request = new Request.Builder()
                        .url(link + "?ID=" + id + "&lastCertificationRowData=" + data)
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
                JSONArray jsonArray = new JSONArray(result);

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String lastCertificationRowData = jsonObject.getString("lastCertificationRowData");
                    String ID = jsonObject.getString("ID");

                    // Adding each certificate as a row to the table
                    //updateExternalActivitiesRow(concatenatedString);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    private class delete_external extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... arg0) {
            try {
                String id = arg0[0];
                String data = arg0[1];

                OkHttpClient client = new OkHttpClient();
                String link = "http://qkrwodbs.dothome.co.kr/delete_externalActivities.php";
                Request request = new Request.Builder()
                        .url(link + "?ID=" + id + "&lastCertificationRowData=" + data)
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
                JSONArray jsonArray = new JSONArray(result);

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String lastCertificationRowData = jsonObject.getString("lastCertificationRowData");
                    String ID = jsonObject.getString("ID");

                    // Adding each certificate as a row to the table
                    //updateExternalActivitiesRow(concatenatedString);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}