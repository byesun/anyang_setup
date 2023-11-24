package com.example.anyang_setup.Resume.adapters;


import androidx.annotation.NonNull;

import com.example.anyang_setup.Resume.datamodel.Experience;

import java.util.List;

/**
 * Created by ibrahim on 1/19/18.
 */

public class ExperienceAdapter extends ResumeEventAdapter<Experience> {

    public ExperienceAdapter(@NonNull List<Experience> list,
                             ResumeEventOnClickListener resumeEventOnClickListener) {
        super(list, resumeEventOnClickListener);
    }
}
