package com.example.anyang_setup.Resume;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.anyang_setup.R;
import com.example.anyang_setup.Resume.datamodel.Resume;
import com.example.anyang_setup.Resume.fragments.EducationFragment;
import com.example.anyang_setup.Resume.fragments.EssentialsFragment;
import com.example.anyang_setup.Resume.fragments.ExperienceFragment;
import com.example.anyang_setup.Resume.fragments.PersonalFragment;
import com.example.anyang_setup.Resume.fragments.PersonalInfoFragment;
import com.example.anyang_setup.Resume.fragments.AwardsFragment;
import com.example.anyang_setup.Resume.fragments.ExternalActivitiesFragment;
import com.example.anyang_setup.Resume.fragments.CertificatesFragment;
import com.example.anyang_setup.Resume.fragments.PreviewFragment;
import com.example.anyang_setup.Resume.fragments.ProjectsFragment;
import com.example.anyang_setup.Resume.helper.ResumeFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

public class Resume_MainActivity extends AppCompatActivity {
    private Resume resume;
    private String currentTitle;
    private String STATE_CURRENT_TITLE = "current title";
    private String userinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resume_activity_main);

        // userinfo 데이터 받기
        userinfo = getIntent().getStringExtra("userinfo");
        // 로그로 userinfo 데이터 확인
        Log.d("Resume_MainActivity", "Received userinfo: " + userinfo);

        setupLayout();

        Gson gson = new Gson();
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String json = mPrefs.getString("SerializableObject", "");
        if (json.isEmpty())
            resume = Resume.createNewResume();
        else
            resume = gson.fromJson(json, Resume.class);

        if (savedInstanceState == null) {
            openFragment(PersonalInfoFragment.newInstance(resume));
            currentTitle = getString(R.string.navigation_personal_info);
        } else
            currentTitle = savedInstanceState.getString(STATE_CURRENT_TITLE);

        getSupportActionBar().setTitle(currentTitle);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(resume);
        prefsEditor.putString("SerializableObject", json);
        prefsEditor.apply();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_CURRENT_TITLE, currentTitle);
    }

    private void setupLayout() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        drawerLayout.closeDrawers();
                        currentTitle = item.getTitle().toString();
                        getSupportActionBar().setTitle(currentTitle);
                        return handleMenuItem(item);
                    }
                });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.content_description_open_drawer,
                R.string.content_description_close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(getString(R.string.steps));
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                getSupportActionBar().setTitle(currentTitle);
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private boolean handleMenuItem(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_personal_info:
                openFragment(PersonalInfoFragment.newInstance(resume));
                break;
            case R.id.action_essentials:
                openFragment(EssentialsFragment.newInstance(resume));
                break;
            case R.id.action_projects:
                openFragment(ProjectsFragment.newInstance(resume));
                break;
            case R.id.action_education:
                openFragment(EducationFragment.newInstance(resume));
                break;
            case R.id.action_experience:
                openFragment(ExperienceFragment.newInstance(resume));
                break;
            case R.id.action_preview:
                PreviewFragment previewFragment = PreviewFragment.newInstance(resume, userinfo);
                openFragment(previewFragment);
                break;
            case R.id.pick_awards:
                openFragment(AwardsFragment.newInstance(resume));
                break;
            case R.id. pick_external_activities:
                openFragment(ExternalActivitiesFragment.newInstance(resume));
                break;
            case R.id. pick_certificates:
                openFragment(CertificatesFragment.newInstance(resume));
                break;
            case R.id. pick_personal:
                openFragment(PersonalFragment.newInstance(resume));
                break;
            default:
                return false;
        }
        return true;
    }

    private void openFragment(ResumeFragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }
}
