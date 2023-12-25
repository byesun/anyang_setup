package com.example.anyang_setup.Resume_test.datamodel.EmploymentDocuments.Chatting.SubActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.anyang_setup.Resume_test.datamodel.EmploymentDocuments.Chatting.ChatDTO;
import com.example.anyang_setup.Resume_test.datamodel.EmploymentDocuments.Chatting.ChatRef;
import com.example.anyang_setup.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNewChatActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText chatContextEditText;
    private EditText maxChatEditText;
    private EditText chatNameEditText;
    private Button createChatButton;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private Spinner spinnerCategories;
    private String selectedCategory = null;

    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_chat);
        userName = getIntent().getStringExtra("userinfo");

        chatContextEditText = findViewById(R.id.chatContextEditText);
        maxChatEditText = findViewById(R.id.maxChatEditText);
        chatNameEditText = findViewById(R.id.chatNameEditText);
        createChatButton = findViewById(R.id.createChatButton);

        createChatButton.setOnClickListener(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        // 스피너 설정
        spinnerCategories = findViewById(R.id.spinner_categories);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.spinner_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategories.setAdapter(adapter);
        spinnerCategories.setOnItemSelectedListener(this);
    }
    public boolean isInteger(String strValue) {
        try {
            Integer.parseInt(strValue);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedCategory = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selectedCategory = null;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.createChatButton :
            {
                if(chatNameEditText.getText().toString().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "채팅방 이름을 설정해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(maxChatEditText.getText().toString().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "채팅방 최대 인원을 설정해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(chatContextEditText.getText().toString().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "채팅방 정보를 입력해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!isInteger(maxChatEditText.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "채팅방 인원은 숫자만 입력 가능 합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Integer.parseInt(maxChatEditText.getText().toString()) < 0 || Integer.parseInt(maxChatEditText.getText().toString()) > 5)
                {
                    Toast.makeText(getApplicationContext(), "채팅방 인원이 잘못되었습니다. 1 ~ 4 까지 입력 가능합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(selectedCategory.equals(getString(R.string.category_prompt)) || selectedCategory.equals("- 전체 -"))
                {
                    Toast.makeText(this,"카테고리를 선택해주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }

                String chatRoomName = selectedCategory + " " + chatNameEditText.getText().toString().trim();

                ChatRef chatRef = new ChatRef(maxChatEditText.getText().toString(), userName, chatContextEditText.getText().toString(), userName);

                databaseReference.child("chat").child(selectedCategory + "  " + chatNameEditText.getText().toString()).child("chatRef").setValue(chatRef)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                ChatDTO chat = new ChatDTO("Notice", "어서오세요. "+userName+"님이 생성한 채팅방 입니다.", ""); //ChatDTO를 이용하여 데이터를 묶는다.

                                databaseReference.child("chat").child(selectedCategory + "  " +chatNameEditText.getText().toString()).child("chatHello").setValue(chat)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                finish();

                                                Toast.makeText(getApplicationContext(), "채팅방이 생성되었습니다.", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                finish();

                                                Toast.makeText(getApplicationContext(), "채팅방 생성에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                finish();

                                Toast.makeText(getApplicationContext(), "채팅방 생성에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });

                break;
            }
        }
    }
}