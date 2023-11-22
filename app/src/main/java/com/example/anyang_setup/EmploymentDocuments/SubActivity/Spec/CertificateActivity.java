package com.example.anyang_setup.EmploymentDocuments.SubActivity.Spec;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anyang_setup.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CertificateActivity extends AppCompatActivity {

    private EditText certificateText;
    private Button saveButton;
    private Button resetButton;

    private EditText acquisition_date;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener acquisitionDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate);
        certificateText = findViewById(R.id.certificate_text);
        saveButton = findViewById(R.id.certificate_write_save_button);
        resetButton = findViewById(R.id.certificate_write_reset_button);
        acquisition_date = findViewById(R.id.acquisition_date);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 사용자가 입력한 텍스트를 가져옵니다.
                String certification = certificateText.getText().toString();
                String acquisitionDate = acquisition_date.getText().toString();


                // 입력값이 공백인 경우 처리
                if (TextUtils.isEmpty(certification) || TextUtils.isEmpty(acquisitionDate) ) {
                    Toast.makeText(CertificateActivity.this, "값을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    // 인텐트를 생성하여 데이터를 SpecActivity로 전달합니다.
                    Intent intent = new Intent();
                    intent.putExtra("certification", certification);
                    intent.putExtra("acquisitionDate", acquisitionDate);
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

        myCalendar = Calendar.getInstance();
        acquisitionDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // This is the date picker of rn date
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }


            {
                myCalendar = Calendar.getInstance();
            }
        };



    }

    private void updateLabel1() {
        String myFormat = "yy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        acquisition_date.setText(sdf.format(myCalendar.getTime()));
    }


    public void acquisitionDate(View view) {
        new DatePickerDialog(CertificateActivity.this, acquisitionDate, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }


}
