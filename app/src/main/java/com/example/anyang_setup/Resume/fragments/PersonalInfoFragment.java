package com.example.anyang_setup.Resume.fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.anyang_setup.GlobalVariables;
import com.example.anyang_setup.R;
import com.example.anyang_setup.Resume.datamodel.PersonalInfo;
import com.example.anyang_setup.Resume.datamodel.Resume;
import com.example.anyang_setup.Resume.helper.ResumeFragment;
import com.example.anyang_setup.Resume.helper.TextChangeListener;

public class PersonalInfoFragment extends ResumeFragment {

    private ImageView profileImageView;
    private static final int REQUEST_IMAGE_PICK = 1;

    public static ResumeFragment newInstance(Resume resume) {
        ResumeFragment fragment = new PersonalInfoFragment();
        fragment.setResume(resume);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =
                inflater.inflate(R.layout.fragment_personal_info, container, false);

        final PersonalInfo personalInfo = getResume().personalInfo;

        EditText MajorEditText = root.findViewById(R.id.input_Major);
        MajorEditText.setText(GlobalVariables.getGlobalVariable_Major());

        EditText nameEditText = root.findViewById(R.id.input_name);
        nameEditText.setText(GlobalVariables.getGlobalVariable_Name());
        //nameEditText.setText(personalInfo.getName());
        nameEditText.addTextChangedListener(new TextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                personalInfo.setName(s.toString());
            }
        });

        EditText jobTitleEditText = root.findViewById(R.id.input_job_title);
        jobTitleEditText.setText(personalInfo.getJobTitle());
        jobTitleEditText.addTextChangedListener(new TextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                personalInfo.setJobTitle(s.toString());
            }
        });

        EditText address1EditText = root.findViewById(R.id.input_address1);
        address1EditText.setText(personalInfo.getAddressLine1());
        address1EditText.addTextChangedListener(new TextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                personalInfo.setAddressLine1(s.toString());
            }
        });

        EditText address2EditText = root.findViewById(R.id.input_address2);
        address2EditText.setText(personalInfo.getAddressLine2());
        address2EditText.addTextChangedListener(new TextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                personalInfo.setAddressLine2(s.toString());
            }
        });

        EditText phoneEditText = root.findViewById(R.id.input_phone);
        phoneEditText.setText(personalInfo.getPhone());
        phoneEditText.addTextChangedListener(new TextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                personalInfo.setPhone(s.toString());
            }
        });

        EditText emailEditText = root.findViewById(R.id.input_email);
        emailEditText.setText(personalInfo.getEmail());
        emailEditText.addTextChangedListener(new TextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                personalInfo.setEmail(s.toString());
            }
        });

        profileImageView = root.findViewById(R.id.profile_image);

        Button selectImageButton = root.findViewById(R.id.select_image_button);
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이미지를 선택하기 위한 Intent 생성
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE_PICK);
            }
        });

        return root;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri selectedImageUri = data.getData();
                profileImageView.setImageURI(selectedImageUri);
                getResume().setImageUri(selectedImageUri.toString()); // Resume 데이터 모델에 이미지 URI 저장
            }
        }
    }
}
