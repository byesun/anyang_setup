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

public class PersonalFragment extends ResumeFragment {

    ListView Personal_list;
    String ID;
    ArrayAdapter<String> adapter1;

    List<Integer> selectedItems = new ArrayList<>();

    private static List<String> selectedPersonalTexts = new ArrayList<>();



    public static ResumeFragment newInstance(Resume resume) {
        ResumeFragment fragment = new PersonalFragment();
        fragment.setResume(resume);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =
                inflater.inflate(R.layout.fragment_personal, container, false);
        Personal_list = root.findViewById(R.id.personal_list);
        final PersonalInfo personalInfo = getResume().personalInfo;
        adapter1 = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1);
        Personal_list.setAdapter(adapter1);
        ID = GlobalVariables.getGlobalVariable_id();

        executeAsyncTask(ID);
        Personal_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Check if the item is already selected
                if (selectedItems.contains(position)) {
                    // If selected, remove it from the list
                    selectedItems.remove(Integer.valueOf(position));
                } else {
                    // If not selected, add it to the list
                    selectedItems.add(position);
                }

                // Update the background color of the selected items in awards_list
                for (int i = 0; i < Personal_list.getChildCount(); i++) {
                    View listItem = Personal_list.getChildAt(i);
                    if (selectedItems.contains(i)) {
                        // Set the background color for selected items
                        listItem.setBackgroundColor(Color.GRAY);
                    } else {
                        // Set the background color for unselected items
                        listItem.setBackgroundColor(Color.TRANSPARENT);
                    }
                }

                // Print the text of the selected items to the console
                selectedPersonalTexts.clear();
                for (Integer selectedItem : selectedItems) {
                    selectedPersonalTexts.add(adapter1.getItem(selectedItem));
                }
                Log.d("Selected Items", selectedPersonalTexts.toString());
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    }

    private class UpdatePersonalListTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... arg0) {
            try {
                String id = arg0[0];

                OkHttpClient client = new OkHttpClient();
                String link = "http://qkrwodbs.dothome.co.kr/Select_personal.php";
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
                ArrayList<String> list_title = new ArrayList<>();
                ArrayList<String> list_personal = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String title = jsonObject.getString("title");
                    String personal = jsonObject.getString("personal");

                    String concatenatedString = title + ":" + personal;

                    if (concatenatedString != null) {
                        list.add(concatenatedString);
                        list_title.add(title);
                        list_personal.add(personal);
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

    // Example of how to execute the AsyncTask in your fragment
    private void executeAsyncTask(String id) {
        UpdatePersonalListTask task = new UpdatePersonalListTask();
        task.execute(id);
    }

    // 자기소개서 제목+내용
    public static List<String> getSelectedPersonal() {
        return selectedPersonalTexts;
    }

    // 자기소개서 제목
    public static List<String> getSelectedTitles() {
        List<String> titles = new ArrayList<>();

        for (String item : selectedPersonalTexts) {
            String[] parts = item.split(":", 2);

            if (parts.length == 2) {
                String title = parts[0].trim();
                titles.add(title);
            }
        }

        return titles;
    }
    // 자기소개서 내용
    public static List<String> getSelectedPersonals() {
        List<String> personals = new ArrayList<>();

        for (String item : selectedPersonalTexts) {
            String[] parts = item.split(":", 2);

            if (parts.length == 2) {
                String personal = parts[1].trim();
                personals.add(personal);
            }
        }

        return personals;
    }

}