package com.example.anyang_setup.Resume_test.datamodel.EmploymentDocuments.SubActivity.Spec;

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

public class AwardsActivity extends AppCompatActivity {

    private EditText awardsText;
    private Button saveButton;
    private Button resetButton;

    private EditText date_end,date_start;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener dateEnd,dateStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awards);
        awardsText = findViewById(R.id.awards_text);
        saveButton = findViewById(R.id.awards_write_save_button);
        resetButton = findViewById(R.id.awards_write_reset_button);
        date_start = findViewById(R.id.date_start);
        date_end = findViewById(R.id.date_end);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 사용자가 입력한 텍스트를 가져옵니다.
                String awards = awardsText.getText().toString();
                String awardsDateStart = date_start.getText().toString();
                String awardsDateEnd = date_end.getText().toString();

                // 입력값이 공백인 경우 처리
                if (TextUtils.isEmpty(awards)) {
                    Toast.makeText(AwardsActivity.this, "값을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    // 인텐트를 생성하여 데이터를 SpecActivity로 전달합니다.
                    Intent intent = new Intent();
                    intent.putExtra("awards", awards);
                    intent.putExtra("awardsDateStart", awardsDateStart);
                    intent.putExtra("awardsDateEnd", awardsDateEnd);
                    setResult(RESULT_OK, intent);

                    // AwardsActivity를 종료합니다.
                    finish();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 입력한 텍스트를 초기화합니다.
                awardsText.setText("");
            }
        });


        myCalendar = Calendar.getInstance();
        dateStart = new DatePickerDialog.OnDateSetListener() {
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

        dateEnd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // This is the date picker of rn date
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();
            }
        };
    }

    private void updateLabel1() {
        String myFormat = "yy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date_start.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel2() {
        String myFormat = "yy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date_end.setText(sdf.format(myCalendar.getTime()));
    }

    public void dateStart(View view) {
        new DatePickerDialog(AwardsActivity.this, dateStart, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void dateEnd(View view) {
        new DatePickerDialog(AwardsActivity.this, dateEnd, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
