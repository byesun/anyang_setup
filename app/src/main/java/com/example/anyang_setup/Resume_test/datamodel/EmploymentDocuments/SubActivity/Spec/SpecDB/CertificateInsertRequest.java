package com.example.anyang_setup.Resume_test.datamodel.EmploymentDocuments.SubActivity.Spec.SpecDB;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CertificateInsertRequest extends StringRequest {

    final static private String URL = "http://qkrwodbs.dothome.co.kr/certificate.php";
    private Map<String, String> map;

    public CertificateInsertRequest(String ID, String certificate,String acquisitionDate,  Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        // awards, certificate, externalActivities가 null이면 빈 문자열로 대체
        map.put("ID", ID);
        map.put("certificate", certificate);
        map.put("acquisitionDate", acquisitionDate);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}