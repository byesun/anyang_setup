package com.example.anyang_setup.EmploymentDocuments.SubActivity.Spec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anyang_setup.R;

public class CertificateActivity extends AppCompatActivity {

    private EditText certificateText;
    private Button saveButton;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate);
        certificateText = findViewById(R.id.certificate_text);
        saveButton = findViewById(R.id.certificate_write_save_button);
        resetButton = findViewById(R.id.certificate_write_reset_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 사용자가 입력한 텍스트를 가져옵니다.
                String certification = certificateText.getText().toString();

                // 입력값이 공백인 경우 처리
                if (TextUtils.isEmpty(certification)) {
                    Toast.makeText(CertificateActivity.this, "값을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    // 인텐트를 생성하여 데이터를 SpecActivity로 전달합니다.
                    Intent intent = new Intent();
                    intent.putExtra("certification", certification);
                    setResult(RESULT_OK, intent);

                    // CertificateActivity를 종료합니다.
                    finish();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 입력한 텍스트를 초기화합니다.
                certificateText.setText("");
            }
        });
    }
}
