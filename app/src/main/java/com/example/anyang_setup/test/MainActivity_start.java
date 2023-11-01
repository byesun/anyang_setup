package com.example.anyang_setup.test;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.anyang_setup.test.DocumentContainer.D_bicycleSn;
import static com.example.anyang_setup.test.DocumentContainer.D_bike_info_1;
import static com.example.anyang_setup.test.DocumentContainer.D_bike_info_2;
import static com.example.anyang_setup.test.DocumentContainer.D_bike_info_3;
import static com.example.anyang_setup.test.DocumentContainer.D_bike_info_4;
import static com.example.anyang_setup.test.DocumentContainer.D_comp;
import static com.example.anyang_setup.test.DocumentContainer.D_loc;
import static com.example.anyang_setup.test.DocumentContainer.D_offenceD;
import static com.example.anyang_setup.test.DocumentContainer.D_offenceT;
import static com.example.anyang_setup.test.DocumentContainer.D_remarks;
import static com.example.anyang_setup.test.DocumentContainer.D_rnDate;
import static com.example.anyang_setup.test.DocumentContainer.D_rnOff;
import static com.example.anyang_setup.test.DocumentContainer.D_rnTime;
import static com.example.anyang_setup.test.DocumentContainer.D_rooOff;
import static com.example.anyang_setup.test.DocumentContainer.InputstreamBicyclePhotos1;
import static com.example.anyang_setup.test.DocumentContainer.InputstreamBicyclePhotos2;
import static com.example.anyang_setup.test.DocumentContainer.InputstreamFourhoursPhotos1;
import static com.example.anyang_setup.test.DocumentContainer.InputstreamFourhoursPhotos2;
import static com.example.anyang_setup.test.DocumentContainer.InputstreamFourhoursPhotos3;
import static com.example.anyang_setup.test.DocumentContainer.InputstreamFourhoursPhotos4;
import static com.example.anyang_setup.test.DocumentContainer.InputstreamGeneralPhotos1;
import static com.example.anyang_setup.test.DocumentContainer.InputstreamGeneralPhotos2;
import static com.example.anyang_setup.test.DocumentContainer.InputstreamGeneralPhotos3;
import static com.example.anyang_setup.test.DocumentContainer.InputstreamGeneralPhotos4;
import static com.example.anyang_setup.test.DocumentContainer.InputstreamGeneralPhotos5;
import static com.example.anyang_setup.test.DocumentContainer.InputstreamGeneralPhotos6;

import static com.example.anyang_setup.test.DocumentContainer.birth_date;
import static com.example.anyang_setup.test.DocumentContainer.snprListCompnies;
import static com.example.anyang_setup.test.DocumentContainer.snprListRnOfficers;
import static com.example.anyang_setup.test.DocumentContainer.snprListRooOfficers;


import static  com.example.anyang_setup.test.DocumentContainer.phonenumber;
import static  com.example.anyang_setup.test.DocumentContainer.email;
import static  com.example.anyang_setup.test.DocumentContainer.address;
import static  com.example.anyang_setup.test.DocumentContainer.highscholl;
import static  com.example.anyang_setup.test.DocumentContainer.graduate_date;
import static com.example.anyang_setup.test.DocumentContainer.graduate_date_univ;

import androidx.appcompat.app.AppCompatActivity;

import com.example.anyang_setup.EmploymentDocuments.EmploymentActivity;
import com.example.anyang_setup.MainActivity;
import com.example.anyang_setup.R;
import com.example.anyang_setup.test.DocumentContainer;
import com.example.anyang_setup.test.GeneralPhotosActivity;
import com.example.anyang_setup.test.HandleSnipersActivity;
import com.example.anyang_setup.test.MessageHelper;
import com.example.anyang_setup.test.PermissionsHelper;

/**
 * Created by Muhammad Abubakar on 11/11/2017.
 * This is the first activity where we select all the info and give all the info here
 *
 */

public class MainActivity_start extends AppCompatActivity {
    TextView txt_bicycle,txt_location,txt_offence_date,txt_offence_time,txt_time,txt_rn_date;

    TextView txt_PhoneNumber,txt_Email,txt_Address,txt_HighScholl,txt_graduate_date_univ,txt_graduate_date,txt_birth_date;
    Spinner snpr_roo,snpr_rn,snpr_compony;
    ProgressBar progressBar_main;

    Button Add_Basic_Info;


    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date,rnDate,birthdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_main);

        progressBar_main= findViewById(R.id.progressBar_main);

        txt_PhoneNumber = findViewById(R.id.txt_PhoneNumber);
        txt_Email = findViewById(R.id.txt_Email);
        txt_Address = findViewById(R.id.txt_Address);
        txt_HighScholl = findViewById(R.id.txt_HighScholl);
        txt_graduate_date = findViewById(R.id.txt_graduate_date);
        txt_graduate_date_univ = findViewById(R.id.txt_graduate_date_univ);
        Add_Basic_Info = findViewById(R.id.Add_Basic_Info);
        txt_birth_date = findViewById(R.id.txt_birth_date);


        //txt_bicycle = findViewById(R.id.txt_bicycle);
        //txt_location = findViewById(R.id.txt_location);
        //txt_offence_date = findViewById(R.id.txt_offence_date);
        //txt_offence_time = findViewById(R.id.txt_offence_time);
        //txt_time = findViewById(R.id.txt_time);
        //txt_rn_date= findViewById(R.id.txt_rn_date);

        snpr_compony = findViewById(R.id.snpr_company); // 주소
        snpr_rn = findViewById(R.id.spnr_rn_officer); // 이메일
        snpr_roo = findViewById(R.id.roo_snpr); //전화번호

        Add_Basic_Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 두 번째 액티비티로 전환
                Intent intent = new Intent(MainActivity_start.this, HandleSnipersActivity.class);
                finish();
                startActivity(intent);
            }
        });


        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // This is the date picker of offence date
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        rnDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // This is the date picker of rn date
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabe2();
            }

        };

        birthdate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // This is the date picker of rn date
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabe3();
            }

        };



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, DocumentContainer.get(this).getComponies());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snpr_compony.setAdapter(dataAdapter);
        snpr_compony.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //this is the combo box of company

                String item = adapterView.getItemAtPosition(i).toString();
                D_comp = item;
                //Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> rnAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, (DocumentContainer.get(this)).getRnOfficres());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snpr_rn.setAdapter(rnAdapter);
        snpr_rn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //this is the combo box of rn officer

                String item = adapterView.getItemAtPosition(i).toString();
                D_rnOff = item;
                //Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> rooAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, DocumentContainer.get(this).getRooOfficers());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snpr_roo.setAdapter(rooAdapter);
        snpr_roo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //this is the combo box of roo officer

                String item = adapterView.getItemAtPosition(i).toString();
                D_rooOff = item;
                //Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public void deleteReport(View view) {
        exit();
    }

    private void updateViews(){
        txt_PhoneNumber.setText(phonenumber);
        txt_Email.setText(email);
        txt_Address.setText(address);
        txt_HighScholl.setText(highscholl);
        txt_graduate_date.setText(graduate_date);
        txt_graduate_date_univ.setText(graduate_date_univ);
        txt_birth_date.setText(birth_date);


        //txt_bicycle.setText(D_bicycleSn);
        //txt_location.setText(D_loc);
        //txt_offence_date.setText(D_offenceD);
        //txt_offence_time.setText(D_offenceT);
        //txt_time.setText(D_rnTime);
        //txt_rn_date.setText(D_rnDate);


        for (int i=0;i<snprListCompnies.size();i++){
            if (snprListCompnies.get(i).equals(D_comp)){
                snpr_compony.setSelection(i);
            }
        }

        for (int i=0;i<snprListRooOfficers.size();i++){
            if (snprListRooOfficers.get(i).equals(D_rooOff)){
                snpr_roo.setSelection(i);
            }
        }

        for (int i=0;i<snprListRnOfficers.size();i++){
            if (snprListRnOfficers.get(i).equals(D_rnOff)){
                snpr_rn.setSelection(i);
            }
        }

    }

    //photo button clicks


    public void generalPhotos(View view) {

        //this is the button action of next from main activity

        progressBar_main.setVisibility(View.VISIBLE);

        if (txt_PhoneNumber.getText().length()>0){
            phonenumber = txt_PhoneNumber.getText().toString();
        }
        if (txt_Email.getText().length()>0){
            email = txt_Email.getText().toString();
        }
        if (txt_Address.getText().length()>0){
            address = txt_Address.getText().toString();
        }
        if (txt_HighScholl.getText().length()>0){
            highscholl = txt_HighScholl.getText().toString();
        }
        if (txt_graduate_date.getText().length()>0){
            graduate_date = txt_graduate_date.getText().toString();
        }
        if (txt_graduate_date_univ.getText().length()>0){
            graduate_date_univ = txt_graduate_date_univ.getText().toString();
        }
        if (txt_birth_date.getText().length()>0){
            birth_date = txt_birth_date.getText().toString();
        }



        //if (txt_bicycle.getText().length()>0){
        //D_bicycleSn = txt_bicycle.getText().toString();
        //}
        /*if (txt_location.getText().length()>0){
            D_loc = txt_location.getText().toString();
        }
        if (txt_offence_time.getText().length()>0){
            D_offenceT =txt_offence_time.getText().toString();
        }
        if (txt_offence_date.getText().length()>0){
            D_offenceD =txt_offence_date.getText().toString();
        }*/
        /*if (txt_time.getText().length()>0){
            D_rnTime =txt_time.getText().toString();
        }*/
        //if (txt_rn_date.getText().length()>0){
        //D_rnDate=txt_rn_date.getText().toString();
        //}

        Intent intent = new Intent(MainActivity_start.this,GenerateReportActivity.class);
        progressBar_main.setVisibility(View.GONE);
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.handle_snipers:
                //this is the action that is performed when box button is clicke to go to combo boxes activity
                Intent intent = new Intent(this,HandleSnipersActivity.class);
                finish();
                startActivity(intent);
                return true;
            case R.id.handle_old_doc:
                openDocumentFromFileManager();


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void exit(){
        Intent intent = new Intent(MainActivity_start.this, EmploymentActivity.class);
        finish();
        startActivity(intent);
    }

    private void openDocumentFromFileManager() {
        //this is the action to open doc file from file manager
        /*Intent i = new Intent();
        i.setType("application/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        if (PermissionsHelper.getPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, R.string.title_storage_permission
                , R.string.text_storage_permission, 1111)) {

            startActivityForResult(Intent.createChooser(i, "Select Document"), 111);

        }*/
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (resultCode==RESULT_OK){
                switch (requestCode){
                    case 111:
                        //this is action performed after openDocumentFromFileManager() when doc is selected
                        FileInputStream inputStream = (FileInputStream) getContentResolver().openInputStream(data.getData());
                        XWPFDocument docx = new XWPFDocument(inputStream);

                        extractImages(docx);

                        List<XWPFParagraph> paragraphList = docx.getParagraphs();
                        int paragrapthCount = 0;
                        for (XWPFParagraph paragraph:paragraphList){
                            if (paragraph.getText().contains("Photos showing the ")){
                                String paragrapthText = paragraph.getText();
                                if (paragrapthCount==0){
                                    ///"Photos showing the general views of the obstruction or inconvenience caused when "+DocumentContainer.D_rnOff +" arrived at "+DocumentContainer.D_loc +" on "+ D_rnDate +".RN was issued at "+ D_rnTime

                                    String rnof = paragrapthText.substring(paragrapthText.indexOf("when ")+5,paragrapthText.indexOf(" arrived at "));
                                    //String location = paragrapthText.substring(paragrapthText.indexOf(" arrived at ")+12,paragrapthText.indexOf(" on "));
                                    String rndate = paragrapthText.substring(paragrapthText.indexOf(" on ")+4,paragrapthText.indexOf(".RN"));
                                    String rntime = paragrapthText.substring(paragrapthText.indexOf("issued at ")+10,paragrapthText.length()-1);

                                    D_rnOff = rnof;
                                    D_rnDate = rndate;
                                    D_rnTime = rntime;

                                    System.out.println("Rn oof:"+rnof+rndate+rntime);
                                }
                                else if (paragrapthCount == 2){

                                    String company = paragrapthText.substring(paragrapthText.indexOf("Operator ")+9,paragrapthText.indexOf(" has failed"));

                                    D_comp = company;
                                    System.out.println(company);
                                }
                                paragrapthCount++;
                            }
                        }

                        List<XWPFTable> tableList = docx.getTables();

                        int count=0;
                        for (XWPFTable table:tableList){

                            for (int i=0;i<table.getNumberOfRows();i++){
                                String cell = table.getRows().get(i).getCell(0).getText();
                                switch (count){
                                    case 0:
                                        if (i==1){
                                            System.out.println("EO INFORMATION: "+cell);
                                            cell = cell.substring(cell.indexOf(": ")+2,cell.length());
                                            D_rooOff = cell;
                                        }
                                        if (i==2){
                                            System.out.println("EO INFORMATION: "+cell);
                                            cell = cell.substring(cell.indexOf(": ")+2,cell.length());
                                            D_bicycleSn = cell;
                                        }
                                        break;
                                    case 1:
                                        if (i==1){
                                            System.out.println("OFFENCE DETAILS: "+cell);  ///D_offenceD + " at " + D_offenceT
                                            cell = cell.substring(cell.indexOf(": ")+2,cell.length());
                                            D_offenceD = cell.substring(0,cell.indexOf(" at "));
                                            D_offenceT = cell.substring(cell.indexOf(" at ")+4,cell.length());

                                        }
                                        if (i==2){
                                            System.out.println("OFFENCE DETAILS: "+cell);
                                            cell = cell.substring(cell.indexOf(": ")+2,cell.length());
                                            D_loc = cell;
                                        }
                                        if (i==3){
                                            System.out.println("OFFENCE DETAILS: "+cell);
                                            D_remarks = cell;
                                        }
                                        break;
                                    case 2:
                                        if (i==1){
                                            System.out.println("BIKE SHARE OPERATOR INFORMATION: "+cell);
                                            D_bike_info_1 = cell;
                                        }
                                        if (i==2){
                                            System.out.println("BIKE SHARE OPERATOR INFORMATION: "+cell);
                                            D_bike_info_2 = cell;
                                        }
                                        if (i==3){
                                            System.out.println("BIKE SHARE OPERATOR INFORMATION: "+cell);
                                            D_bike_info_3 = cell;
                                        }
                                        if (i==4){
                                            System.out.println("BIKE SHARE OPERATOR INFORMATION: "+cell);
                                            D_bike_info_4 = cell;
                                        }
                                        break;
                                }
                            }
                            count++;
                        }
                        updateViews();

                        break;
                }
            }else {
                MessageHelper.showCustomToastError(this, getLayoutInflater(), "Your image is not loaded");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            MessageHelper.showCustomToastError(this, getLayoutInflater(), "SomeThing went wrong");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_snipers, menu);
        return true;
    }

    public void offenceDatePicker(View view) {
        //this is the offence date picker updateLabel() is also included in it
        new DatePickerDialog(MainActivity_start.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        txt_graduate_date.setText(sdf.format(myCalendar.getTime()));



        //txt_offence_date.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabe2() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        txt_graduate_date_univ.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabe3() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        txt_birth_date.setText(sdf.format(myCalendar.getTime()));
    }

    public void rnDatePicker(View view) {
        new DatePickerDialog(MainActivity_start.this, rnDate, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void birthDatePicker(View view) {
        new DatePickerDialog(MainActivity_start.this, birthdate, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public static void extractImages(XWPFDocument docx){
        try {

            //function to retrieve all the images from the doc file that have been selected
//
//            HWPFDocument wordDoc = new HWPFDocument(new FileInputStream(fileName));
//
//            XWPFWordExtractor extractor = new XWPFWordExtractor(docx);
            List<XWPFPictureData> picList = docx.getAllPackagePictures();

            int i = 1;

            for (XWPFPictureData pic : picList) {

                //System.out.print(pic.getPictureType());
                //System.out.print(pic.getData());
                System.out.println("Image Number: " + i + " " + pic.getFileName());
                switch (i){
                    case 1:
                        InputstreamGeneralPhotos1 = new ByteArrayInputStream(pic.getData());
                        break;
                    case 2:
                        InputstreamGeneralPhotos2 = new ByteArrayInputStream(pic.getData());
                        break;
                    case 3:
                        InputstreamGeneralPhotos3 = new ByteArrayInputStream(pic.getData());
                        break;
                    case 4:
                        InputstreamGeneralPhotos4 = new ByteArrayInputStream(pic.getData());
                        break;
                    case 5:
                        InputstreamGeneralPhotos5 = new ByteArrayInputStream(pic.getData());
                        break;
                    case 6:
                        InputstreamGeneralPhotos6 = new ByteArrayInputStream(pic.getData());
                        break;
                    case 7:
                        InputstreamBicyclePhotos1 = new ByteArrayInputStream(pic.getData());
                        break;
                    case 8:
                        InputstreamBicyclePhotos2 = new ByteArrayInputStream(pic.getData());
                        break;
                    case 9:
                        InputstreamFourhoursPhotos1 = new ByteArrayInputStream(pic.getData());
                        break;
                    case 10:
                        InputstreamFourhoursPhotos2 = new ByteArrayInputStream(pic.getData());
                        break;
                    case 11:
                        InputstreamFourhoursPhotos3 = new ByteArrayInputStream(pic.getData());
                        break;
                    case 12:
                        InputstreamFourhoursPhotos4 = new ByteArrayInputStream(pic.getData());
                        break;
                }
                i++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
