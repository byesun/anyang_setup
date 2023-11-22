package com.example.anyang_setup.Resume_test.adapters;


import androidx.annotation.NonNull;

import com.example.anyang_setup.Resume_test.datamodel.School;

import java.util.List;

/**
 * Created by ibrahim on 1/19/18.
 */

public class SchoolsAdapter extends ResumeEventAdapter<School> {

    public SchoolsAdapter(@NonNull List<School> list,
                          ResumeEventOnClickListener resumeEventOnClickListener) {
        super(list, resumeEventOnClickListener);
    }
}