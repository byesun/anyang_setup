package com.example.anyang_setup.Resume.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.anyang_setup.GlobalVariables;
import com.example.anyang_setup.R;
import com.example.anyang_setup.Resume.datamodel.PersonalInfo;
import com.example.anyang_setup.Resume.datamodel.Resume;
import com.example.anyang_setup.Resume.helper.ResumeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ExternalActivitiesFragment extends ResumeFragment {

    ListView ExternalActivities_list;
    String ID;
    ArrayAdapter<String> adapter1;

    public static ResumeFragment newInstance(Resume resume) {
        ResumeFragment fragment = new ExternalActivitiesFragment();
        fragment.setResume(resume);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =
                inflater.inflate(R.layout.fragment_external_activities, container, false);
        ExternalActivities_list = root.findViewById(R.id.externalactivities_list);
        final PersonalInfo personalInfo = getResume().personalInfo;
        adapter1 = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1);
        ExternalActivities_list.setAdapter(adapter1);
        ID = GlobalVariables.getGlobalVariable_id();
        executeAsyncTask(ID);
        return root;
    }

    private class UpdateExternalActivitiesListTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... arg0) {
            try {
                String id = arg0[0];

                OkHttpClient client = new OkHttpClient();
                String link = "http://qkrwodbs.dothome.co.kr/Select_externalActivities.php";
                Request request = new Request.Builder()
                        .url(link + "?ID=" + id)
                        .build();
                Response response = client.newCall(request).execute();

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
                // Handle JSON parsing
                JSONArray jsonArray = new JSONArray(result);
                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String externalActivities = jsonObject.getString("externalActivities");
                    String startDate = jsonObject.getString("startDate");
                    String endDate = jsonObject.getString("endDate");

                    String concatenatedString = externalActivities + "(" + startDate + "~" + endDate + ")";

                    if (concatenatedString != null) {
                        list.add(concatenatedString);
                    }
                }

                // Update UI on the main thread
                requireActivity().runOnUiThread(() -> {
                    adapter1.clear();
                    adapter1.addAll(list);
                    adapter1.notifyDataSetChanged();
                });

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(requireContext(), "JSON 파싱 오류: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void executeAsyncTask(String id) {
        UpdateExternalActivitiesListTask task = new UpdateExternalActivitiesListTask();
        task.execute(id);
    }

}