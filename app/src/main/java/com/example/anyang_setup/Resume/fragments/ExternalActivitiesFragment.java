package com.example.anyang_setup.Resume.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.anyang_setup.R;
import com.example.anyang_setup.Resume.datamodel.Resume;
import com.example.anyang_setup.Resume.helper.ResumeFragment;

public class ExternalActivitiesFragment extends ResumeFragment {

    private ImageView profileImageView;
    private static final int REQUEST_IMAGE_PICK = 1;

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



        return root;
    }
}
