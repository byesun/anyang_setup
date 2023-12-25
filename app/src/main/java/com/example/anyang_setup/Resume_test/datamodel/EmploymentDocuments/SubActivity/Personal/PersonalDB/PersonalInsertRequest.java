package com.example.anyang_setup.Resume_test.datamodel.EmploymentDocuments.SubActivity.Personal.PersonalDB;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PersonalInsertRequest extends StringRequest {

    final static private String URL = "http://qkrwodbs.dothome.co.kr/personal.php";
    private Map<String, String> map;

    public PersonalInsertRequest(String ID,String personalTitle, String personalText, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("ID", ID);
        map.put("personalTitle", personalTitle);
        map.put("personalText", personalText);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
