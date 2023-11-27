package com.example.anyang_setup.Resume.fragments;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.anyang_setup.GlobalVariables;
import com.example.anyang_setup.R;
import com.example.anyang_setup.Resume.datamodel.Experience;
import com.example.anyang_setup.Resume.datamodel.PersonalInfo;
import com.example.anyang_setup.Resume.datamodel.Project;
import com.example.anyang_setup.Resume.datamodel.Resume;
import com.example.anyang_setup.Resume.datamodel.School;
import com.example.anyang_setup.Resume.helper.ResumeFragment;
import com.example.anyang_setup.Resume.fragments.PersonalInfoFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PreviewFragment extends ResumeFragment {
    WebView webView;

    private String userinfo;
    private String stdName;
    private String stdDepart;

    public static PreviewFragment newInstance(Resume resume, String userinfo) {
        PreviewFragment fragment = new PreviewFragment();
        Bundle args = new Bundle();
        args.putParcelable("resume", resume); // Resume 객체를 Bundle에 추가
        args.putString("userinfo", userinfo); // userinfo 문자열을 Bundle에 추가
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userinfo = getArguments().getString("userinfo");

            try {
                JSONObject jsonObject = new JSONObject(userinfo);
                JSONObject data = jsonObject.getJSONObject("data");
                this.stdName = data.getString("stdName"); // 클래스 멤버 변수에 할당
                this.stdDepart = data.getString("stdDepart"); // 클래스 멤버 변수에 할당

                Log.d("PreviewFragment", "Student Name: " + stdName);
                Log.d("PreviewFragment", "Student ID: " + stdDepart);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preview, container, false);
        webView = view.findViewById(R.id.webView);
        StringBuilder htmlContent = new StringBuilder();
        Resume resume = getResume();
        PersonalInfo personalInfo = resume.personalInfo;
        // Resume 데이터 모델에서 이미지 URI 가져오기
        String imageUri = getResume().getImageUri();

        String awardsText = "";


        List<String> selectedAwards = AwardsFragment.getSelectedAwards();

        // 선택된 어워드 목록을 개행으로 구분된 문자열로 변환
        StringBuilder awardsStringBuilder = new StringBuilder();
        for (String award : selectedAwards) {
            awardsStringBuilder.append(award).append("\n");
        }

        // awardsStringBuilder가 null이 아닌 경우에 awardsText에 할당
        if (awardsStringBuilder != null) {
            awardsText = awardsStringBuilder.toString();
        }


        String certificatesText = "";


        List<String> selectedCertificates = CertificatesFragment.getSelectedCertificates();

        // 선택된 어워드 목록을 개행으로 구분된 문자열로 변환
        StringBuilder certificatesStringBuilder = new StringBuilder();
        for (String certificates : selectedCertificates) {
            certificatesStringBuilder.append(certificates).append("\n");
        }

        // awardsStringBuilder가 null이 아닌 경우에 awardsText에 할당
        if (certificatesStringBuilder != null) {
            certificatesText = certificatesStringBuilder.toString();
        }

        String externalText = "";


        List<String> selectedExternal = ExternalActivitiesFragment.getSelectedExternal();

        // 선택된 어워드 목록을 개행으로 구분된 문자열로 변환
        StringBuilder externalStringBuilder = new StringBuilder();
        for (String external : selectedExternal) {
            externalStringBuilder.append(external).append("\n");
        }

        // awardsStringBuilder가 null이 아닌 경우에 awardsText에 할당
        if (externalStringBuilder != null) {
            externalText = externalStringBuilder.toString();
        }



        // CSS 클래스 정의
        htmlContent.append("<style type='text/css'>\n" +
                "    .right-align { text-align: right; }\n" +
                "    .left-align { text-align: left; }\n" +
                "    .center-align { text-align: center; }\n" +
                "    .rounded-box { background-color: #00ff0000; border-radius: 15px; padding: 20px; margin: 20px; }\n" + // 둥근 네모 박스
                "    .oval-box { background-color: #C6C6C6; border-radius: 50%; width: 200px; height: 100px; text-align: center; }\n" + // 타원형 박스
                "    body { font-family: Arial, sans-serif; font-size: 14px; color: #333; line-height: 1.6; background-color: #f8f8f8; }\n" +
                "    h1, h2, h3, p { color: #000; }\n" + // 검정색 텍스트
                "    img { width: 300px; height: auto; border-radius: 200%; }\n" + // 이미지 크기 변경
                "</style>");

        // 배경 이미지 및 꽉 차게 만드는 스타일 추가
        htmlContent.append("<style type='text/css'>\n" +
                "    body { background-image: url('https://i.pinimg.com/564x/0e/88/9c/0e889caac98a80ce1e8a265e8d923d3a.jpg'); background-size: cover; }\n" +
                "</style>");


/*        // HTML 컨텐츠 추가 (여기에 필요한 HTML을 추가합니다)
        htmlContent.append("<div class='rounded-box'>\n"); // 둥근 네모 박스 시작
        // 여기에 이력서의 내용을 추가합니다.
        htmlContent.append("</div>\n"); // 둥근 네모 박스 종료

        htmlContent.append("<div class='oval-box'>\n"); // 타원형 박스 시작
        // 타원형 박스 내용을 추가합니다.
        htmlContent.append("</div>\n"); // 타원형 박스 종료*/
        htmlContent.append(String.format("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<title>Resume</title>\n" +
                "<meta charset=UTF-8>\n" +
                "<link rel=\"shortcut icon\" href=https://ssl.gstatic.com/docs/documents/images/kix-favicon6.ico>\n" +
                "<style type=text/css>body{font-family:arial,sans,sans-serif;margin:0}iframe{border:0;frameborder:0;height:100%%;width:100%%}#header," +
                "#footer{background:#f0f0f0;padding:10px 10px}#header{border-bottom:1px #ccc solid}#footer{border-top:1px #ccc solid;border-bottom:1px #ccc solid;font-size:13}" +
                "#contents{margin:6px}.dash{padding:0 6px}</style>\n" +
                "</head>\n" +
                "<body style=\"background-image: url('https://i.pinimg.com/564x/30/31/99/3031991966978eab1c619a0247a0de67.jpg'); background-size: cover;\">\n" +
                "<div id=contents >\n" +
/*                "<style type=text/css>@import url('https://themes.googleusercontent.com/fonts/css?kit=xTOoZr6X-i3kNg7pYrzMsnEzyYBuwf3lO_Sc3Mw9RUVbV0WvE1cEyAoIq5yYZlSc');" +
                "c6{text-align: right;}\n" +
                "</style>\n" +*/
                        "<table class='c23'>\n" +
                        "    <tbody>\n" +
                        "            <tr>\n" +
                        "                <td class='rounded-box' colspan='2' rowspan='1'>\n" +
                        "                    <h3 class='c9'> </h3>\n" +
                        "                </td>\n" +
                        "            </tr>\n" +
                        // 프로필 사진과 개인 정보 부분
                        "        <tr>\n" +
                        "            <td rowspan='2' class='c26'>\n" +
                        "                <p class='center-align'><img src='" + imageUri + "' style='width:133px; height:162px; color:box_1;' onerror=\"this.src='https://via.placeholder.com/103x132.jpg'\"></p>\n" +
                        "            </td>\n" +
                        "            <td class='c26'>\n" +
                        "                <p class='c6'><h1>" + stdName + "</h1></p>\n" +
                        "                <p class='c6'><span class='label'>생년월일: </span><span class='data'>" + personalInfo.getBirthTitle() + "</span></p>\n" +
                        "                <p class='c6'><span class='label'>연락처: </span><span class='data'>" + personalInfo.getPhone() + "</span></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td class='c26'>\n" +
                        "                <p class='c6'><span class='label'>주소: </span><span class='data'>" + personalInfo.getAddressLine1() + "</span></p>\n" +
                        "                <p class='c6'><span class='label'>상세주소: </span><span class='data'>" + personalInfo.getAddressLine2() + "</span></p>\n" +
                        "                <p class='c6'><span class='label'>이메일: </span><span class='data'>" + personalInfo.getEmail() + "</span></p>\n" +
                        "                <p class='c6'><span class='label'>학과: </span><span class='data'>" + stdDepart + "</span></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n"));
                        // 기술과 언어 부분

        // 경계선 추가
        htmlContent.append(
                "            <tr>\n" +
                        "                <td class='rounded-box' colspan='4' rowspan='1'>\n" +
                        "                    <h4 class='c9'> </h4>\n" +
                        "                </td>\n" +
                        "            </tr>\n"
        );
/*        // 기술(skills) 항목 처리
        if (!resume.skills.isEmpty()) {
            htmlContent.append(String.format(
                            "            <td class='left-align' colspan='1'>\n" +
                            "                <h4>%s</h4>\n" +
                            "                <p>%s</p>\n" +
                            "            </td>\n", getString(R.string.hint_skills), resume.skills
            ));
        }
        // 언어(languages) 항목 처리
        if (!resume.languages.isEmpty()) {
            htmlContent.append(String.format(
                            "            <td class='left-align' colspan='2'>\n" +
                            "                <h4>%s</h4>\n" +
                            "                <p>%s</p>\n" +
                            "            </td>\n", getString(R.string.hint_languages), resume.languages
            ));
        }*/
        // 수상 내역 섹션
        if (!awardsText.isEmpty()) {
            htmlContent.append(String.format(
/*                    "            <tr>\n" +
                            "                <td class='rounded-box' colspan='4' rowspan='1'>\n" +
                            "                    <h3 class='c9'> </h3>\n" +
                            "                </td>\n" +
                            "            </tr>\n" +*/
                    "        <tr class='c27'>\n" +
                            "            <td class='c26' colspan='1' rowspan='1'>\n" +
                            "                <h4 class='c9'><span class='c16'>수상목록</span></h4>\n" +
                            "            </td>\n" +
                            "            <td class='c4' colspan='1' rowspan='1'>\n" +
                            "                <p class='c3'><span class='c7'>%s / </span></p>\n" +
                            "            </td>\n" +
                            "        </tr>\n", awardsText));
        }

        // 자격증 섹션
        if (!certificatesText.isEmpty()) {
            htmlContent.append(String.format(
/*                    "            <tr>\n" +
                            "                <td class='rounded-box' colspan='4' rowspan='1'>\n" +
                            "                    <h4 class='c9'> </h4>\n" +
                            "                </td>\n" +
                            "            </tr>\n" +*/
                    "        <tr class='c27'>\n" +
                            "            <td class='c26' colspan='1' rowspan='1'>\n" +
                            "                <hr>\n" +
                            "                <h4 class='c9'><span class='c16'>자격증</span></h4>\n" +
                            "            </td>\n" +
                            "            <td class='c4' colspan='1' rowspan='1'>\n" +
                            "                <p class='left-align'>%s / <br></p>\n" +
                            "            </td>\n" +
                            "        </tr>\n", certificatesText));
        }

        // 대외활동 섹션
        if (!externalText.isEmpty()) {
            htmlContent.append(String.format(
/*                    "            <tr>\n" +
                            "                <td class='rounded-box' colspan='4' rowspan='1'>\n" +
                            "                    <h5 class='c9'> </h5>\n" +
                            "                </td>\n" +
                            "            </tr>\n" +*/
                    "        <tr class='c27'>\n" +
                            "            <td class='c26' colspan='1' rowspan='1'>\n" +
                            "                <hr>\n" +
                            "                <h4 class='c9'><span class='c16'>대외활동</span></h4>\n" +
                            "            </td>\n" +
                            "            <td class='c4' colspan='1' rowspan='1'>\n" +
                            "                <p class='left-align'>%s / </p>\n" +
                            "            </td>\n" +
                            "        </tr>\n", externalText));
        }

        // 경계선 추가
        htmlContent.append(
                "            <tr>\n" +
                        "                <td class='rounded-box' colspan='4' rowspan='1'>\n" +
                        "                    <h3 class='c9'> </h3>\n" +
                        "                </td>\n" +
                        "            </tr>\n"
        );

//, personalInfo.getAddressLine2()

/*        // HTML에 추가
        htmlContent.append(String.format("<p class='left-align'>수상목록 : %s </p>\n", awardsText));
        htmlContent.append(String.format("<p class='right-align'>자격증 : %s </p>\n", certificatesText));
        htmlContent.append(String.format("<p class='right-align'>대외활동 : %s </p>\n", externalText));*/
/*        if (!resume.languages.isEmpty()) {
            htmlContent.append(String.format("\n" +
                    "                <tr class=\"c27\">\n" +
                    "                    <td class=\"c26\" colspan=\"1\" rowspan=\"1\">\n" +
                    "                        <br><p class=\"c6\"><span class=\"c24\">ㅡ</span></p><br>\n" +
                    "                        <h4 class=\"c9\" id=\"h.61e3cm1p1fln\"><span class=\"c16\">"+getString(R.string.hint_languages)+"</span></h4></td>\n" +
                    "                    <td class=\"c4\" colspan=\"1\" rowspan=\"1\">\n" +
                    "                        <p class=\"c3\"><span class=\"c7\">%s</span></p>\n" +
                    "                    </td>\n" +
                    "                </tr>", resume.languages));
        }*/
        // 학력 섹션
        if (!resume.schools.isEmpty()) {
            // '학력' 제목이 포함된 첫 번째 행 추가
            htmlContent.append("\n<tr class=\"c15\">\n" +
                    "    <th class=\"c26\"> <hr> <p>ㅡ").append(getString(R.string.navigation_education)).append("ㅡ</p></th>\n" +
                    "    <td class=\"c4\"></td>\n" +
                    "</tr>\n");

            // 각 학교의 세부사항을 새로운 행에 추가
            for (School school : resume.schools) {
                htmlContent.append(String.format(
                        "        <tr>\n" +
                                "            <td class=\"c26\"></td>\n" + // 첫 번째 열은 비워둠
                                "            <td class=\"c4\">\n" +
                                "                <h5>학교 이름 : %s / 학위 : %s</h5>\n" +
                                "                <h5> 입학 날짜 : %s / 좋업 날짜 : %s</h5>\n" +
                                "                <hr>\n" +
                                "            </td>\n" +
                                "        </tr>\n",
                        school.getSchoolName(), school.getDegree(), school.getLocation(), school.getDescription()));
            }
        }

        // 경력 섹션
        if (!resume.experience.isEmpty()) {
            // '경력' 제목이 포함된 첫 번째 행 추가
            htmlContent.append("\n<tr class=\"c15\">\n" +
                    "    <th class=\"c26\"> <hr> <p>ㅡ").append(getString(R.string.navigation_experience)).append("ㅡ</p></th>\n" +
                    "    <td class=\"c4\"></td>\n" +
                    "</tr>\n");

            // 각 경험의 세부사항을 새로운 행에 추가
            for (Experience experience : resume.experience) {
                htmlContent.append(String.format(
                        "        <tr>\n" +
                                "            <td class=\"c26\"></td>\n" + // 첫 번째 열은 비워둠
                                "            <td class=\"c4\">\n" +
                                "                <h5>회사 : %s  위치 : %s</h5>\n" +
                                "                <h5>직무 : %s  담당 업무 설명 : %s</h5>\n" +
                                "                <hr>\n" +
                                "            </td>\n" +
                                "        </tr>\n",
                        experience.getCompany(), experience.getLocation(), experience.getJobTitle(), experience.getDescription()));
            }
        }

        // 프로젝트 섹션
        if (!resume.projects.isEmpty()) {
            // '프로젝트' 제목이 포함된 첫 번째 행 추가
            htmlContent.append("\n<tr class=\"c15\">\n" +
                    "    <th class=\"c26\"> <hr> <p>ㅡ").append(getString(R.string.play_project)).append("ㅡ</p></th>\n" +
                    "    <td class=\"c4\"></td>\n" +
                    "</tr>\n");

            // 각 프로젝트의 세부사항을 새로운 행에 추가
            for (Project project : resume.projects) {
                htmlContent.append(String.format(
                        "        <tr>\n" +
                                "            <hr><td class=\"c26\"></td>\n" + // 첫 번째 열은 비워둠
                                "            <td class=\"c4\">\n" +
                                "                <h5>프로젝트 이름 : %s / 주제 : %s</h5>\n" +
                                "                <h5>%s 설명 : %s</h5>\n" +
                                "                <hr>\n" +
                                "            </td>\n" +
                                "        </tr>\n",
                        project.getName(), project.getDetail(),project.getName(), project.getDescription()));
            }
        }


        /*//자기소개서 제목---------------------------------------------
        htmlContent.append("<p class='right-align'>제목 : ");
        for (String title : titles) {
            htmlContent.append(title).append(", ");
        }
        // 마지막 쉼표와 공백 제거
        if (htmlContent.length() > 0) {
            htmlContent.setLength(htmlContent.length() - 2);
        }
        htmlContent.append("</p>\n");


        //자기소개서 내용---------------------------------------------
        htmlContent.append("<p class='right-align'>내용 : ");
        for (String personal : personals) {
            htmlContent.append(personal).append(", ");
        }
        // 마지막 쉼표와 공백 제거
        if (htmlContent.length() > 0) {
            htmlContent.setLength(htmlContent.length() - 2);
        }*/
/*        // 자기소개서 제목 및 내용 처리
        if (!titleText.isEmpty()) {
            htmlContent.append(String.format("<p class='left-align'>제목: %s </p>\n", titleText));
        }
        if (!personalsText.isEmpty()) {
            htmlContent.append(String.format("<p class='left-align'>내용: %s </p>\n", personalsText));
        }*/
        // 자기소개서 제목 및 내용 처리
        String titleText = getFormattedText(PersonalFragment.getSelectedTitles());
        String personalsText = getFormattedText(PersonalFragment.getSelectedPersonals());

        // 자기소개서 내용을 이력서 HTML 마지막 부분에 추가
        if (!titleText.isEmpty() || !personalsText.isEmpty()) {
            htmlContent.append("<tr><td colspan='2'><hr><h2>자기소개서</h2></tr>\n");
            if (!titleText.isEmpty()) {
                htmlContent.append(String.format("<hr><tr><td class=\"c26\"></td>\n" + // 첫 번째 열은 비워둠
                        "            <td class=\"c4\">\n" +
                        "                <h5>제목 : %s </h5>\n", titleText));
            }
            if (!personalsText.isEmpty()) {
                htmlContent.append(String.format("<p>내용: %s</p>\n" +
                        "</td>\n" +
                        "</tr>\n", personalsText));
            }
        }

/*        "        <tr>\n" +
                "            <hr><td class=\"c26\"></td>\n" + // 첫 번째 열은 비워둠
                "            <td class=\"c4\">\n" +
                "                <h5>프로젝트 이름 : %s / 주제 : %s</h5>\n" +
                "                <h5>%s 설명 : %s</h5>\n" +
                "                <hr>\n" +
                "            </td>\n" +
                "        </tr>\n",
                */


        htmlContent.append("</p>\n");
        htmlContent.append("</tbody>\n" +
                "</table>\n" +
                "<p class=\"c2 c11\"><span class=\"c30 c5\"></span></p>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");



/*        htmlContent.append("<hr><p class='center-align'>본 이력서에 기재한 사항은 사실과 다름없음을 확인합니다.</p>\n");*/
        htmlContent.append("<p class='right-align'>작성일 : 2023 년 11월 29일</p>\n");
        htmlContent.append((String.format("<p class='right-align'>안양대학교 " + stdDepart + "\n")));
        htmlContent.append(String.format("<p class='right-align'>작성인 : " + stdName + " (인)</p>\n"));






        webView.loadDataWithBaseURL(null, htmlContent.toString(), "text/html", "utf-8", null);
        return view;
    }
    // 선택된 항목 목록을 개행 문자로 구분된 문자열로 변환하는 메서드
    private String getFormattedText(List<String> items) {
        if (items == null || items.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String item : items) {
            stringBuilder.append(item).append("\n");
        }
        return stringBuilder.toString().trim();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.print, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_print:
                createWebPrintJob(webView);
                return true;
            case R.id.action_print_resume:
                createResumePrintJob();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createWebPrintJob(WebView webView) {

        // Get a PrintManager instanc


        PrintManager printManager = (PrintManager) getActivity()
                .getSystemService(Context.PRINT_SERVICE);

        // Get a print adapter instance
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();

        // Create a print job with name and adapter instance
        String jobName = getString(R.string.app_name) + " Document";
        PrintJob printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
    }

    // 인쇄 버튼 클릭 시 호출되는 메서드
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createResumePrintJob() {
        WebView printWebView = new WebView(getContext());
        printWebView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                // 웹뷰 로딩이 완료되면 인쇄 작업 생성 및 실행
                PrintDocumentAdapter printAdapter = printWebView.createPrintDocumentAdapter();
                String jobName = getString(R.string.app_name) + " Resume Document";
                PrintJob printJob = ((PrintManager) getActivity().getSystemService(Context.PRINT_SERVICE))
                        .print(jobName, printAdapter, new PrintAttributes.Builder().build());
            }
        });

        // 자기소개서 제목 및 내용만 포함된 HTML 콘텐츠 생성
        String resumeContentHtml = createResumeContentHtml();
        printWebView.loadDataWithBaseURL(null, resumeContentHtml, "text/html", "utf-8", null);
    }

    // 자기소개서 제목 및 내용만 포함된 HTML 콘텐츠 생성 메서드
    private String createResumeContentHtml() {
        StringBuilder htmlContent = new StringBuilder();
        // 자기소개서 제목

        String titleText = "";
        List<String> selectedTitle = PersonalFragment.getSelectedTitles();

        StringBuilder titleStringBuilder = new StringBuilder();
        for (String title : selectedTitle) {
            titleStringBuilder.append(title).append("\n");
        }

        // awardsStringBuilder가 null이 아닌 경우에 awardsText에 할당
        if (titleStringBuilder != null) {
            titleText = titleStringBuilder.toString();
        }

        // 자기소개서 내용

        String personalsText = "";
        List<String> selectedPersonals = PersonalFragment.getSelectedPersonals();

        StringBuilder persoanlsStringBuilder = new StringBuilder();
        for (String personals : selectedPersonals) {
            persoanlsStringBuilder.append(personals).append("\n");
        }

        // awardsStringBuilder가 null이 아닌 경우에 awardsText에 할당
        if (persoanlsStringBuilder != null) {
            personalsText = persoanlsStringBuilder.toString();
        }
        // 기본 CSS 스타일 및 HTML 구조를 추가
        htmlContent.append("<html><head><style type='text/css'>/* CSS 스타일 */</style></head><body style=\"background-image: url('https://i.pinimg.com/564x/30/31/99/3031991966978eab1c619a0247a0de67.jpg'); background-size: cover;\">\n");

        // 자기소개서 제목 및 내용 추가
        htmlContent.append("<br><br><hr><br><br>\n");
        htmlContent.append(String.format("<h2 class='ight-align'>&nbsp &nbsp &nbsp &nbsp 제목: %s </h2>\n", titleText));
        htmlContent.append("<br><hr><br>\n");
        htmlContent.append(String.format("<h5 class='right-align'>&nbsp &nbsp &nbsp &nbsp 내용: %s </h5>\n", personalsText));

        // HTML 닫기
        htmlContent.append("</body></html>");

        return htmlContent.toString();
    }

    @Override
    public void onViewCreated(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    }
}