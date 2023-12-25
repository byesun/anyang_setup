/*
package com.example.anyang_setup.EmploymentDocuments.SubActivity.Resume;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.anyang_setup.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class ResumeWriteActivity extends AppCompatActivity {

    private static final int FILE_SELECT_CODE = 0;

    private boolean isFabOpen = false;
    private FloatingActionButton fabMain, fabUpload, fabDelete;
    private ArrayList<String> uploadedFiles = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ArrayList<Integer> selectedFileIndices = new ArrayList<>(); // 선택한 파일의 인덱스를 저장하는 ArrayList

    private boolean isFileSelectionEnabled = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_write);

        fabMain = findViewById(R.id.fab_main);
        fabUpload = findViewById(R.id.fab_upload);
        fabDelete = findViewById(R.id.fab_delete);

        ListView fileListView = findViewById(R.id.file_list_view);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, uploadedFiles);
        fileListView.setAdapter(adapter);

        // 파일 목록에서 항목을 클릭하면 선택 상태를 토글하도록 설정
        fileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (isFabOpen) {
                    toggleFileSelection(view, position);
                } else {
                    // ***************파일을 클릭했을 때 파일 열기 로직 추가 **********************************
                    openFile(uploadedFiles.get(position));
                }
            }
        });

        Uri uri;
        ImageView imageView;

        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFab();
            }
        });
        fabUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileSelector();
            }
        });

       /* // 삭제 버튼 클릭 시 선택한 항목을 삭제
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFileSelectionEnabled) {
                    if (selectedFileIndices.isEmpty()) {
                        Toast.makeText(ResumeWriteActivity.this, "파일을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        // 선택한 항목을 삭제합니다.
                        Collections.sort(selectedFileIndices, Collections.reverseOrder());
                        for (Integer index : selectedFileIndices) {
                            uploadedFiles.remove(index.intValue());
                        }
                        adapter.notifyDataSetChanged();
                        selectedFileIndices.clear();
                        Toast.makeText(ResumeWriteActivity.this, "선택한 파일이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ResumeWriteActivity.this, "삭제 버튼을 사용하려면 파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
/*
        // 파일 목록에서 항목을 클릭하면 선택 상태를 토글하도록 설정
        fileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (isFileSelectionEnabled) {
                    toggleFileSelection(view, position);
                }
            }
        });

        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFileSelectionEnabled();
            }
        });
    }


    private void toggleFileSelectionEnabled() {

        ListView fileListView = findViewById(R.id.file_list_view);

        isFileSelectionEnabled = !isFileSelectionEnabled;

        if (!isFileSelectionEnabled) {
            // 선택 상태가 비활성화된 경우 삭제 버튼이 눌린 것으로 간주하여 파일 삭제 작업을 수행
            if (selectedFileIndices.isEmpty()) {
                Toast.makeText(ResumeWriteActivity.this, "파일을 선택해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                // 선택한 항목을 삭제합니다.
                Collections.sort(selectedFileIndices, Collections.reverseOrder());
                for (Integer index : selectedFileIndices) {
                    uploadedFiles.remove(index.intValue());
                }
                adapter.notifyDataSetChanged();
                selectedFileIndices.clear();
                Toast.makeText(ResumeWriteActivity.this, "선택한 파일이 삭제되었습니다.", Toast.LENGTH_SHORT).show();

                // 선택된 파일 삭제 후, 남은 파일들의 배경색을 투명으로 변경
                for (int i = 0; i < fileListView.getChildCount(); i++) {
                    View item = fileListView.getChildAt(i);
                    item.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }
            }
        }
    }

    private void openFileSelector() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "파일 선택"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "파일 관리자 앱을 설치해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    String fileName = getFileName(uri); // 파일 이름 가져오기
                    if (fileName != null) {
                        uploadedFiles.add(fileName);
                        adapter.notifyDataSetChanged(); // 어댑터에 변경 사항을 알립니다.
                    }
                }
            }
        }
    }

    private void toggleFileSelection(View view, int position) {
        if (selectedFileIndices.contains(position)) {
            // 이미 선택한 항목인 경우 선택 취소
            selectedFileIndices.remove(Integer.valueOf(position));
            view.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        } else {
            // 새로 선택한 항목인 경우 선택
            selectedFileIndices.add(position);
            view.setBackgroundColor(getResources().getColor(R.color.gray));
        }
    }
    private void openFile(String fileName) {
        // 파일 열기 로직을 여기에 추가
        Toast.makeText(ResumeWriteActivity.this, "파일 열기: " + fileName, Toast.LENGTH_SHORT).show();
    }


    // 파일 Uri에서 파일 이름 가져오기
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex != -1) {
                        result = cursor.getString(nameIndex);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    private void toggleFab() {
        //Toast.makeText(this, "메인 플로팅 버튼 클릭", Toast.LENGTH_SHORT).show();

        if (isFabOpen) {
            ObjectAnimator.ofFloat(fabDelete, "translationY", 0f).start();
            ObjectAnimator.ofFloat(fabUpload, "translationY", 0f).start();
            fabMain.setImageResource(R.drawable.baseline_add_24);
        } else {
            ObjectAnimator.ofFloat(fabDelete, "translationY", -200f).start();
            ObjectAnimator.ofFloat(fabUpload, "translationY", -400f).start();
            fabMain.setImageResource(R.drawable.baseline_clear_24);
        }

        isFabOpen = !isFabOpen;
    }

}
*/