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

public class CertificatesFragment extends ResumeFragment {

    ListView Certificates_list;
    String ID;
    ArrayAdapter<String> adapter1;

    List<Integer> selectedItems = new ArrayList<>();

    private static List<String> selectedCertificatesTexts = new ArrayList<>();
    public static ResumeFragment newInstance(Resume resume) {
        ResumeFragment fragment = new CertificatesFragment();
        fragment.setResume(resume);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =
                inflater.inflate(R.layout.fragment_certificates, container, false);
        Certificates_list = root.findViewById(R.id.certificates_list);
        final PersonalInfo personalInfo = getResume().personalInfo;
        adapter1 = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1);
        Certificates_list.setAdapter(adapter1);
        ID = GlobalVariables.getGlobalVariable_id();

        executeAsyncTask(ID);
        Certificates_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                for (int i = 0; i < Certificates_list.getChildCount(); i++) {
                    View listItem = Certificates_list.getChildAt(i);
                    if (selectedItems.contains(i)) {
                        // Set the background color for selected items
                        listItem.setBackgroundColor(Color.GRAY);
                    } else {
                        // Set the background color for unselected items
                        listItem.setBackgroundColor(Color.TRANSPARENT);
                    }
                }

                // Print the text of the selected items to the console
                selectedCertificatesTexts.clear();
                for (Integer selectedItem : selectedItems) {
                    selectedCertificatesTexts.add(adapter1.getItem(selectedItem));
                }
                Log.d("Selected Items", selectedCertificatesTexts.toString());
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    }

    private class UpdateCertificatesListTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... arg0) {
            try {
                String id = arg0[0];

                OkHttpClient client = new OkHttpClient();
                String link = "http://qkrwodbs.dothome.co.kr/Select_certificate.php";
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

                    String certificate = jsonObject.getString("certificate");
                    String acquisitionDate = jsonObject.getString("acquisitionDate");

                    String concatenatedString = certificate + "(" + acquisitionDate + ")";

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

    // Example of how to execute the AsyncTask in your fragment
    private void executeAsyncTask(String id) {
        UpdateCertificatesListTask task = new UpdateCertificatesListTask();
        task.execute(id);
    }

    public static List<String> getSelectedCertificates() {
        return selectedCertificatesTexts;
    }

}