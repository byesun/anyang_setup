package com.example.anyang_setup.Resume.fragments;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AwardsFragment extends ResumeFragment {

    private ListView awards_list;
    private String ID;
    private ArrayAdapter<String> adapter1;
    private List<Integer> selectedItems = new ArrayList<>();

    private static List<String> selectedAwardsTexts = new ArrayList<>();


    public static ResumeFragment newInstance(Resume resume) {
        ResumeFragment fragment = new AwardsFragment();
        fragment.setResume(resume);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_awards, container, false);
        awards_list = root.findViewById(R.id.awards_list);
        final PersonalInfo personalInfo = getResume().personalInfo;

        adapter1 = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1);
        awards_list.setAdapter(adapter1);
        ID = GlobalVariables.getGlobalVariable_id();

        executeAsyncTask(ID);

        awards_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectedItems.contains(position)) {
                    selectedItems.remove(Integer.valueOf(position));
                } else {
                    selectedItems.add(position);
                }

                // Update the background color of the selected items in awards_list
                for (int i = 0; i < awards_list.getChildCount(); i++) {
                    View listItem = awards_list.getChildAt(i);
                    if (selectedItems.contains(i)) {
                        listItem.setBackgroundColor(Color.GRAY);
                    } else {
                        listItem.setBackgroundColor(Color.TRANSPARENT);
                    }
                }

                // Update the selectedTexts global variable
                selectedAwardsTexts.clear();
                for (Integer selectedItem : selectedItems) {
                    selectedAwardsTexts.add(adapter1.getItem(selectedItem));
                }
                Log.d("Selected Items", selectedAwardsTexts.toString());
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    }

    private class UpdateAwardsListTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... arg0) {
            try {
                String id = arg0[0];

                OkHttpClient client = new OkHttpClient();
                String link = "http://qkrwodbs.dothome.co.kr/Select_awards.php";
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
                    String awards = jsonObject.getString("awards");
                    String startDate = jsonObject.getString("startDate");
                    String endDate = jsonObject.getString("endDate");

                    String concatenatedString = awards + "(" + startDate + "~" + endDate + ")";
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
        UpdateAwardsListTask task = new UpdateAwardsListTask();
        task.execute(id);
    }

    public static List<String> getSelectedAwards() {
        return selectedAwardsTexts;
    }

}