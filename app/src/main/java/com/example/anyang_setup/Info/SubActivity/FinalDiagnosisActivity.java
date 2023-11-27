package com.example.anyang_setup.Info.SubActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.anyang_setup.GlobalVariables;
import com.example.anyang_setup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FinalDiagnosisActivity extends AppCompatActivity {
    TextView titleText, resultText, resultTitleView;
    Button homeButton, curriculumButton;
    ImageView imageView;
    String userinfoStr;
    String Major;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_final_diagnosis);
        titleText = findViewById(R.id.titleView);
        resultTitleView = findViewById(R.id.resultTitleView);
        resultText = findViewById(R.id.resultView);
        homeButton = findViewById(R.id.homeButton);
        curriculumButton = findViewById(R.id.curriculumButton);
        imageView = findViewById(R.id.imageView);

        curriculumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Major = GlobalVariables.getGlobalVariable_Major();
                if (Major != null) {
                    Log.d("Major Value", Major);
                    // 나머지 로직 실행
                } else {
                    Log.e("Major Value", "Major is null");
                }

                if (Major.contains("신학")) {
                    String url = "https://www.anyang.ac.kr/theology/information/two-body-system-of-majors.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("기독교교육")) {
                    String url = "https://www.anyang.ac.kr/christian/information/major-system-chart-track.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("국어국문")) {
                    String url = "https://www.anyang.ac.kr/korean/information/composition-of-the-subject.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("영미")) {
                    String url = "https://www.anyang.ac.kr/english/information/composition-of-the-subject.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("러시아")) {
                    String url = "https://www.anyang.ac.kr/russian/admissions/curriculum.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("중국")) {
                    String url = "https://www.anyang.ac.kr/chinese/activities/course-of-study.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("유아")) {
                    String url = "https://www.anyang.ac.kr/childhood/information/composition-of-the-subject.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("공연")) {
                    String url = "https://www.anyang.ac.kr/perform/information/composition-of-the-subject.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("음악")) {
                    String url = "https://www.anyang.ac.kr/music/information/composition-of-the-subject.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("디지털미디어")) {
                    String url = "https://www.anyang.ac.kr/dmdadmin/information/composition-of-the-subject.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("화장품")) {
                    String url = "https://www.anyang.ac.kr/cosmetics/information/composition-of-the-subject.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("뷰티")) {
                    String url = "https://www.anyang.ac.kr/beauty/information/composition-of-the-subject001.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("게임")) {
                    String url = "https://www.anyang.ac.kr/game/information/composition-of-the-subject.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("스포츠")) {
                    String url = "https://www.anyang.ac.kr/sports/information/curriculum.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("실용음악")) {
                    String url = "https://www.anyang.ac.kr/practical/information/composition-of-the-subject.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("체육")) {
                    String url = "https://www.anyang.ac.kr/physical/activities/course-of-study.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("글로벌")) {
                    String url = "https://www.anyang.ac.kr/business/information/composition-of-the-subject.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("행정")) {
                    String url = "https://www.anyang.ac.kr/public/information/curriculum.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("관광경영")) {
                    String url = "https://www.anyang.ac.kr/tourismm/information/curriculum.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("공공행정")) {
                    String url = "https://www.anyang.ac.kr/government/information/composition-of-the-subject.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("관광학")) {
                    String url = "https://www.anyang.ac.kr/tour/information/composition-of-the-subject.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("식품")) {
                    String url = "https://www.anyang.ac.kr/food/information/course-of-study.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("컴퓨터")) {
                    String url = "https://www.anyang.ac.kr/computer/jpg.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("정보전기")) {
                    String url = "https://www.anyang.ac.kr/electronic/information/composition-of-the-subject.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("통계데이터")) {
                    String url = "https://www.anyang.ac.kr/statistics/information/add.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("소프트")) {
                    String url = "https://www.anyang.ac.kr/software/activities/subject_flow.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("도시정보")) {
                    String url = "https://www.anyang.ac.kr/urban/information/curriculum.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("환경에너지")) {
                    String url = "https://www.anyang.ac.kr/energy/information/curriculum-system002.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("AI")) {
                    String url = "https://www.anyang.ac.kr/ai/information/curriculum.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("스마트시티")) {
                    String url = "http://ayusmartcity.kr/bbs/content.php?co_id=curriculum";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("해양바이오")) {
                    String url = "https://www.anyang.ac.kr/marine/information/composition-of-the-subject.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (Major.contains("융합소프트")) {
                    String url = "https://www.anyang.ac.kr/convergenc/information/composition-of-the-subject.do";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else {
                    String url = "www.naver.com";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
            }
        });

        try {
            userinfoStr = getIntent().getStringExtra("userinfo");
            JSONObject jsonObject = new JSONObject(userinfoStr);
            JSONObject userData = jsonObject.getJSONObject("data");


            // 졸업을 통과하지 못한 경우 userData.getString("graduateResult").compareTo("미통과") == 0
            if (userData.getString("graduateResult").compareTo("미통과") == 0) {
                imageView = (ImageView) findViewById(R.id.imageView); //GIF ImageView연결
                Glide.with(this).load(R.raw.loading).into(imageView); //R.raw.loading GIF파일 load
                homeButton.setVisibility(View.INVISIBLE);

                // 1. 부족 학점
                JSONObject creditStatus = userData.getJSONObject("creditStatus");
                String totalRemainText = ("현재 " + creditStatus.getInt("total_remain") + "학점이 부족합니다.");

                // 2. 필수과목
                String requiredSubjectText;
                JSONArray requiredSubjectsArray = userData.getJSONArray("requiredSubjects");
                List<String> needSubjectList = new ArrayList<>();
                for (int row = 0; row < requiredSubjectsArray.length(); row++) {
                    JSONArray requiredSubjectsRow = requiredSubjectsArray.getJSONArray(row);
                    for (int col = 0; col < requiredSubjectsRow.length(); col++) {
                        if (col == 2 && requiredSubjectsRow.getString(col).compareTo("이수") != 0) {
                            needSubjectList.add(requiredSubjectsRow.getString(1));
                        }
                    }
                }
                if (needSubjectList.size() == 0) {
                    requiredSubjectText = "모든 필수 과목을 이수하셨습니다.";
                } else {
                    String tempStr = "아래의 과목을 이수 하셔야 합니다.";
                    for (String subject : needSubjectList) {
                        tempStr += "\n-" + subject;
                    }
                    requiredSubjectText = tempStr;
                }

                // 3. 역량
                String generalSubjectResult = userData.getString("generalSubjectResult");

                // 4. 채플
                JSONObject chapelStatus = userData.getJSONObject("chapel");
                JSONArray chapelStatusCompleterArray = chapelStatus.getJSONArray("complete");
                String chapelResultText;
                if (chapelStatusCompleterArray.getInt(0) - chapelStatusCompleterArray.getInt(1) <= 0) {
                    chapelResultText = "채플을 모두 수강하셨습니다.";
                } else {
                    chapelResultText = "채플을 " + (chapelStatusCompleterArray.getInt(0) - chapelStatusCompleterArray.getInt(1)) + "학기 더 수강하셔야 합니다.";
                }

                String totalResultStr = totalRemainText + "\n" + requiredSubjectText + "\n" + generalSubjectResult + "\n" + chapelResultText;
                resultText.setText(totalResultStr);

            }
            // 졸업인 경우
            else {
                imageView = (ImageView) findViewById(R.id.imageView); //GIF ImageView연결
                Glide.with(this).load(R.raw.loading2).into(imageView); //R.raw.loading GIF파일 load
                curriculumButton.setVisibility(View.INVISIBLE);
                resultTitleView.setVisibility(View.INVISIBLE);
                resultText.setText("졸업을 진심으로 축하드립니다!");

            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}