package com.example.anyang_setup.EmploymentDocuments.SubActivity.Spec.SpecDB;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ExternalInsertRequest extends StringRequest {

    final static private String URL = "http://qkrwodbs.dothome.co.kr/externalActivities.php";
    private Map<String, String> map;

    public ExternalInsertRequest(String ID, String externalActivities,String externalDateStart,String externalDateEnd, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        // awards, certificate, externalActivities가 null이면 빈 문자열로 대체
        map.put("ID", ID);
        map.put("externalActivities", externalActivities);
        map.put("externalDateStart", externalDateStart);
        map.put("externalDateEnd", externalDateEnd);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
