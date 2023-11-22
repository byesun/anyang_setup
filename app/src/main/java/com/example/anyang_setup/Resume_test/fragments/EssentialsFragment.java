package com.example.anyang_setup.Resume_test.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.anyang_setup.R;
import com.example.anyang_setup.Resume_test.datamodel.Resume;
import com.example.anyang_setup.Resume_test.helper.ResumeFragment;
import com.example.anyang_setup.Resume_test.helper.TextChangeListener;

public class EssentialsFragment extends ResumeFragment {

    public static ResumeFragment newInstance(Resume resume) {
        ResumeFragment fragment = new EssentialsFragment();
        fragment.setResume(resume);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =
                inflater.inflate(R.layout.fragment_essentials, container, false);

        final Resume resume = getResume();

        EditText skillsText = root.findViewById(R.id.input_skills);
        skillsText.setText(resume.skills);
        skillsText.addTextChangedListener(new TextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                resume.skills = s.toString();
            }
        });
        EditText languagesText = root.findViewById(R.id.input_languages);
        languagesText.setText(resume.languages);
        languagesText.addTextChangedListener(new TextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                resume.languages = s.toString();
            }
        });
        return root;
    }
}
