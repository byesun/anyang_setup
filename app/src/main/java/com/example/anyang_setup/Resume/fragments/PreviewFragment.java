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
                "    .rounded-box { background-color: #C6C6C6; border-radius: 15px; padding: 20px; margin: 20px; }\n" + // 둥근 네모 박스
                "    .oval-box { background-color: #C6C6C6; border-radius: 50%; width: 200px; height: 100px; text-align: center; }\n" + // 타원형 박스
                "    body { font-family: Arial, sans-serif; font-size: 14px; color: #333; line-height: 1.6; background-color: #f8f8f8; }\n" +
                "    h1, h2, h3, p { color: #000; }\n" + // 검정색 텍스트
                "    img { width: 200px; height: auto; border-radius: 100%; }\n" + // 이미지 크기 변경
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
                "<body style=\"background-color:#EAE5DE\">\n" +
                "<div id=contents >\n" +
/*                "<style type=text/css>@import url('https://themes.googleusercontent.com/fonts/css?kit=xTOoZr6X-i3kNg7pYrzMsnEzyYBuwf3lO_Sc3Mw9RUVbV0WvE1cEyAoIq5yYZlSc');" +
                "c6{text-align: right;}\n" +
                "</style>\n" +*/
                "<table class=c23>\n" +
                "            <tbody>\n" +
                "               <p class=\"c6\"><span class=\"c24\"><hr>ㅡ</span></p>\n" +
                "                <tr class=\"c15\">\n" +
                "                    <td class=\"c26\" colspan=\"1\" rowspan=\"1\">\n" +
                "                        <p class=\"center-align\"><img src=\"" + imageUri + "\" style=\"width:133px; height:162px; color:box_1;\" onerror=\"this.src='https://via.placeholder.com/103x132.jpg'\"></p>" +
/*                "                        <p class=\"c6 c12 title\" id=\"h.4prkjmzco10w\"><span>%s</span></p>\n" +
                "                        <p class=\"c33 subtitle\" id=\"h.o2iwx3vdck7p\"><span class=\"c20\">%s</span></p>\n" +*/
                "                    </td>\n" +
                "                    <td class=\"c26\" colspan=\"1\" rowspan=\"1\">\n" +
                "                        <p class=\"c6\"><span style=\"overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 418.00px; height: 2.67px;\"><img alt=\"\" src=\"https://lh4.googleusercontent.com/j7t3_XjsJ1PHIrgcWuJOWmQ2fFs9q-TT_LNTDfAXGnVu49aapNgutWcfK1k7pPzGtsu9lOvPynvLW07b_KwpVV0ituspJAXOQ_IBZyN087cqGsXawUahO2qDRCQZ8-qq4esAcP7M\" style=\"width: 418.00px; height: 2.67px; margin-left: 0.00px; margin-top: 0.00px; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px);\" title=\"horizontal line\"></span></p>\n" +
                /*                "                        <hr><h1 class=\"c3\" id=\"h.lf5wiiqsu4ub\"><span>%s</span></h1>\n" +*/
                "                        <p class=\"c6\"><span class=\"label\">이름 &nbsp;&nbsp;&nbsp;&nbsp;</span><span class=\"data\">" + stdName + "</span></p>\n" +
                "                        <p class=\"c6\"><span class=\"label\">생년월일 &nbsp;&nbsp;&nbsp;&nbsp;</span><span class=\"data\">%s</span></p>\n" +
                "                        <p class=\"c6\"><span class=\"label\">연락처 &nbsp;&nbsp;&nbsp;&nbsp;</span><span class=\"data\">%s</span></p>\n" +
                "                        <p class=\"c6\"><span class=\"label\">주소 &nbsp;&nbsp;&nbsp;&nbsp;</span><span class=\"data\">%s</span></p>\n" +
                "                        <p class=\"c6\"><span class=\"label\">e_mail &nbsp;&nbsp;&nbsp;&nbsp;</span><span class=\"data\">%s</span></p>"+
                "                        <p class=\"c6\"><span class=\"label\">학과 &nbsp;&nbsp;&nbsp;&nbsp;</span><span class=\"data\">" + stdDepart + "</span></p>" +
                "                    </td>\n" +
                "               </p>\n", personalInfo.getBirthTitle(), personalInfo.getPhone(), personalInfo.getAddressLine1(), personalInfo.getEmail()));

//, personalInfo.getAddressLine2()
        if (!resume.skills.isEmpty()) {
            htmlContent.append(String.format("\n" +
                    "                <tr class=\"c27\">\n" +
                    "                    <td class=\"c26\" colspan=\"1\" rowspan=\"1\">\n" +
                    "                        <br><p class=\"c6\"><span class=\"c24\">ㅡ</span></p><br>\n" +
                    "                        <h1 class=\"c9\" id=\"h.61e3cm1p1fln\"><span class=\"c16\">"+getString(R.string.hint_skills)+"</span></h1></td>\n" +
                    "                    <td class=\"c4\" colspan=\"1\" rowspan=\"1\">\n" +
                    "                        <p class=\"c2\"><span style=\"overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 418.00px; height: 2.67px;\"><img alt=\"\" src=\"https://lh3.googleusercontent.com/n8bZfGajkthDbPpbjeiRJ4w7rNUmj1iFxdZKCHUOVnfH9FgHVt5EBo3vOYIIoE3augYQ_DCZJUzdlStyJ5RaldVrSG36sTE0CjIot2qaiJ3YRyr2i87bt9Y9d0ngdseS9PpG0HzM\" style=\"width: 418.00px; height: 2.67px; margin-left: 0.00px; margin-top: 0.00px; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px);\" title=\"horizontal line\"></span></p>\n" +
                    "                        <p class=\"c3\"><span class=\"c7\">%s</span></p>\n" +
                    "                    </td>\n" +
                    "                </tr>", resume.skills));
        }
        // HTML에 추가
        htmlContent.append(String.format("<p class='right-align'>수상목록 : %s </p>\n", awardsText));
        htmlContent.append(String.format("<p class='right-align'>자격증 : %s </p>\n", certificatesText));
        htmlContent.append(String.format("<p class='right-align'>대외활동 : %s </p>\n", externalText));
        if (!resume.languages.isEmpty()) {
            htmlContent.append(String.format("\n" +
                    "                <tr class=\"c27\">\n" +
                    "                    <td class=\"c26\" colspan=\"1\" rowspan=\"1\">\n" +
                    "                        <br><p class=\"c6\"><span class=\"c24\">ㅡ</span></p><br>\n" +
                    "                        <h1 class=\"c9\" id=\"h.61e3cm1p1fln\"><span class=\"c16\">"+getString(R.string.hint_languages)+"</span></h1></td>\n" +
                    "                    <td class=\"c4\" colspan=\"1\" rowspan=\"1\">\n" +
                    "                        <p class=\"c2\"><span style=\"overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 418.00px; height: 2.67px;\"><img alt=\"\" src=\"https://lh3.googleusercontent.com/n8bZfGajkthDbPpbjeiRJ4w7rNUmj1iFxdZKCHUOVnfH9FgHVt5EBo3vOYIIoE3augYQ_DCZJUzdlStyJ5RaldVrSG36sTE0CjIot2qaiJ3YRyr2i87bt9Y9d0ngdseS9PpG0HzM\" style=\"width: 418.00px; height: 2.67px; margin-left: 0.00px; margin-top: 0.00px; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px);\" title=\"horizontal line\"></span></p>\n" +
                    "                        <p class=\"c3\"><span class=\"c7\">%s</span></p>\n" +
                    "                    </td>\n" +
                    "                </tr>", resume.languages));
        }
        if (resume.schools.size() != 0) {
            htmlContent.append("\n" +
                    "                <tr class=\"c15\">\n" +
                    "                    <td class=\"c26\" colspan=\"1\" rowspan=\"1\">\n" +
                    "                        <br><p class=\"c6\"><span class=\"c24\">ㅡ</span></p><br>\n" +
                    "                        <h1 class=\"c9\" id=\"h.tk538brb1kdf\"><span class=\"c16\">"+getString(R.string.education)+"</span></h1></td>\n" +
                    "                    <td class=\"c4\" colspan=\"1\" rowspan=\"1\">\n");
            boolean first = true;
            for (School school : resume.schools) {
                htmlContent.append(String.format("<h2 class=\"%s\" id=\"h.u3uy0857ab2n\"><span class=\"c5\">%s </span><span class=\"c30 c5\">/ %s</span></h2>\n" +
                        "                        <h3 class=\"c2\" id=\"h.re1qtuma0rpm\"><span class=\"c1\">%s</span></h3>\n" +
                        "                        <p class=\"c32\"><span class=\"c7\">%s</span></p>\n", first ? "c3" : "c14", school.getSchoolName(), school.getDegree(), school.getLocation(), school.getDescription()));
                first = false;
            }
            htmlContent.append("</td>\n" +
                    "                </tr>");
        }
        if (resume.projects.size() != 0) {
            htmlContent.append("\n" +
                    "                <tr class=\"c15\">\n" +
                    "                    <td class=\"c26\" colspan=\"1\" rowspan=\"1\">\n" +
                    "                        <br><p class=\"c6\"><span class=\"c24\">ㅡ</span></p><br>\n" +
                    "                        <h3 class=\"c9\" id=\"h.tk538brb1kdf\"><span class=\"c16\">"+getString(R.string.hint_project_name)+"</span></h3></td>\n" +
                    "                    <td class=\"c4\" colspan=\"1\" rowspan=\"1\">\n");
            boolean first = true;
            for (Project project : resume.projects) {
                htmlContent.append(String.format("<h2 class=\"%s\" id=\"h.u3uy0857ab2n\"><span class=\"c5\">%s </span><span class=\"c30 c5\">/ %s</span></h2>\n" +
                        "                        <p class=\"c32\"><span class=\"c7\">%s</span></p>\n", first ? "c3" : "c14", project.getName(), project.getDetail(), project.getDescription()));
                first = false;
            }
            htmlContent.append("</td>\n" +
                    "                </tr>");
        }
        if (resume.experience.size() != 0) {
            htmlContent.append("\n" +
                    "                <tr class=\"c15\">\n" +
                    "                    <td class=\"c26\" colspan=\"1\" rowspan=\"1\">\n" +
                    "                        <br><p class=\"c6\"><span class=\"c24\">ㅡ</span></p><br>\n" +
                    "                        <h1 class=\"c9\" id=\"h.tk538brb1kdf\"><span class=\"c16\">"+getString(R.string.navigation_experience)+"</span></h1></td>\n" +
                    "                    <td class=\"c4\" colspan=\"1\" rowspan=\"1\">\n");
            boolean first = true;
            for (Experience experience : resume.experience) {
                htmlContent.append(String.format("<h2 class=\"%s\" id=\"h.u3uy0857ab2n\"><span class=\"c5\">%s </span><span class=\"c30 c5\">/ %s</span></h2>\n" +
                        "                        <h3 class=\"c2\" id=\"h.re1qtuma0rpm\"><span class=\"c1\">%s</span></h3>\n" +
                        "                        <p class=\"c32\"><span class=\"c7\">%s</span></p>\n", first ? "c3" : "c14", experience.getCompany(), experience.getLocation(), experience.getJobTitle(), experience.getDescription()));
                first = false;
            }
            htmlContent.append("</td>\n" +
                    "                </tr>");
        }
        htmlContent.append("</tbody>\n" +
                "</table>\n" +
                "<p class=\"c2 c11\"><span class=\"c30 c5\"></span></p>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
        htmlContent.append("<hr><p class='center-align'>본 이력서에 기재한 사항은 사실과 다름없음을 확인합니다.</p>\n");
        htmlContent.append("<p class='right-align'>작성일 : 2023 년 11월 29일</p>\n");
        htmlContent.append(String.format("<p class='right-align'>지원자 : " + stdName + " (인)</p>\n"));





        webView.loadDataWithBaseURL(null, htmlContent.toString(), "text/html", "utf-8", null);
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.print, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_print) {
            createWebPrintJob(webView);
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

    @Override
    public void onViewCreated(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    }
}