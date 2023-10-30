package com.example.anyang_setup.EmploymentDocuments.Chatting.SubActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.anyang_setup.EmploymentDocuments.Chatting.ChatDTO;
import com.example.anyang_setup.EmploymentDocuments.Chatting.SubActivity.chat_option.ChatAdapter;
import com.example.anyang_setup.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatRoomActivity extends AppCompatActivity {

    private String CHAT_NAME;
    private String USER_NAME;

    private ListView chat_view;
    private EditText chat_edit;
    private ImageButton chat_send;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        // 위젯 ID 참조
        chat_view = (ListView) findViewById(R.id.chat_view);
        chat_edit = (EditText) findViewById(R.id.chat_edit);
        chat_send = (ImageButton) findViewById(R.id.chat_sent);
        chat_view.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        // 로그인 화면에서 받아온 채팅방 이름, 유저 이름 저장
        Intent intent = getIntent();
        CHAT_NAME = intent.getStringExtra("chatRoom");
        USER_NAME = intent.getStringExtra("userinfo");

        // 채팅 방 입장
        openChat(CHAT_NAME);

        chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chat_edit.getText().toString().equals(""))
                    return;

                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String getTime = sdf.format(date);

                ChatDTO chat = new ChatDTO(USER_NAME, chat_edit.getText().toString(), getTime); //ChatDTO를 이용하여 데이터를 묶는다.
                chat.setMine(true);// 이 메시지는 내가 보낸 것 true 설정
                databaseReference.child("chat").child(CHAT_NAME).push().setValue(chat); // 데이터 푸쉬
                chat_edit.setText(""); //입력창 초기화

            }
        });
    }
    private void addMessage(DataSnapshot dataSnapshot, ChatAdapter adapter) {
        try {
            ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
            if (chatDTO.getUserName().equals(USER_NAME)) {
                chatDTO.setMine(true);
            } else {
                chatDTO.setMine(false);
            } //좌우 설정 본인 오른쪽, 다른 왼쪽
            adapter.add(chatDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeMessage(DataSnapshot dataSnapshot, ChatAdapter adapter) {
        ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
        adapter.remove(chatDTO);
    }

    private void openChat(String chatName) {
        // 리스트 어댑터 생성 및 세팅
        final ChatAdapter adapter = new ChatAdapter(this, new ArrayList<ChatDTO>());
        chat_view.setAdapter(adapter);

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("chat").child(chatName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e("Telechips", dataSnapshot.getKey());

                if (dataSnapshot.getKey().contains("chatHello")) {
                    ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
                    adapter.insert(chatDTO, 0);
                } else if (dataSnapshot.getKey().contains("chatRef")) {
                    // do not work
                } else // Message
                {
                    addMessage(dataSnapshot, adapter);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                removeMessage(dataSnapshot, adapter);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}