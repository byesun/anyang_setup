package com.example.anyang_setup.EmploymentDocuments.Chatting.SubActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.anyang_setup.EmploymentDocuments.Chatting.ChatDTO;
import com.example.anyang_setup.EmploymentDocuments.Chatting.SubActivity.chat_option.ChatAdapter;
import com.example.anyang_setup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatRoomActivity extends AppCompatActivity {
    private static final int FILE_SELECT_CODE = 0;
    private String CHAT_NAME;
    private String USER_NAME;

    private ListView chat_view;
    private EditText chat_edit;
    private ImageButton chat_send;
    private ImageButton file_send; // Add a button for sending files

    private String userName;  // 사용자 이름
    private String chatRoom;  // 채팅방 이름

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        chat_view = findViewById(R.id.chat_view);
        chat_edit = findViewById(R.id.chat_edit);
        chat_send = findViewById(R.id.chat_sent);
        file_send = findViewById(R.id.file_send); // Reference the file send button
        chat_view.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        Intent intent = getIntent();
        CHAT_NAME = intent.getStringExtra("chatRoom");
        USER_NAME = intent.getStringExtra("userinfo");

        openChat(CHAT_NAME);

        // androidx.appcompat.widget.Toolbar로 import 확인
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_chat_room);
        setSupportActionBar(toolbar);

        chat_view = findViewById(R.id.chat_view);

        // 인텐트에서 채팅방 정보와 사용자 정보를 가져옵니다.
        userName = intent.getStringExtra("userinfo"); // 현재 로그인한 사용자 이름
        chatRoom = intent.getStringExtra("chatRoom"); // 채팅방 이름

        chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = chat_edit.getText().toString();
                if (!message.equals("")) {
                    sendMessage(message);
                    chat_edit.setText(""); // Clear the input box after sending a message
                }
            }
        });

        // 이미지를 선택하기 위한 Intent의 ACTION_GET_CONTENT 타입을 "image/*"로 변경합니다.
        file_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");  // 변경: 모든 이미지 타입
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                try {
                    startActivityForResult(Intent.createChooser(intent, "Select an Image"), FILE_SELECT_CODE);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ChatRoomActivity.this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendMessage(String message) {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String getTime = sdf.format(date);

        ChatDTO chat = new ChatDTO(USER_NAME, message, getTime);
        chat.setMine(true);
        databaseReference.child("chat").child(CHAT_NAME).push().setValue(chat);
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
            private void removeMessage(DataSnapshot dataSnapshot, ChatAdapter adapter) {
                ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
                adapter.remove(chatDTO);
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

    // onActivityResult 메서드에서 파일의 URI 받아와서 Firebase Storage에 업로드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                uploadImage(imageUri);
            }
        }
    }

    // 이미지 firebase sotrage 업로드 메서드
    private void uploadImage(Uri imageUri) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        // 'uploads/' 경로 아래에 타임스탬프와 함께 이미지 파일을 저장
        StorageReference imageRef = storageRef.child("uploads/" + System.currentTimeMillis() + "-image");

        imageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // 업로드가 성공하면 이미지의 URL 로드
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // 이미지 URL을 포함하는 채팅 메시지를 보냄
                                sendMessageWithImage(uri.toString());
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChatRoomActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    // 이미지 URL을 보내서 채팅방에 이미지 표시 메서드
    private void sendMessageWithImage(String imageUrl) {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String getTime = sdf.format(date);

        ChatDTO chat = new ChatDTO(USER_NAME, "이미지를 보냈습니다.", getTime);
        chat.setFileUrl(imageUrl);  // 이미지 URL 설정
        chat.setMine(true);

        databaseReference.child("chat").child(CHAT_NAME).push().setValue(chat);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat_room, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String chatRoomId = chatRoom; // 적절한 방법으로 채팅방 ID를 가져옵니다.
        String userId = userName; // 적절한 방법으로 사용자 ID를 가져옵니다.

        switch (item.getItemId()) {
            case R.id.action_leave:
                leaveChatRoom(chatRoomId, userId);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void leaveChatRoom(String chatRoomId, String userId) {
        DatabaseReference chatRef = databaseReference.child("chat").child(chatRoomId).child("chatRef");

        // 채팅방 참여자 목록을 가져와서 현재 사용자를 제거합니다.
        chatRef.child("acceptUser").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    if (dataSnapshot.exists()) {
                        List<String> acceptUserList = new ArrayList<>();
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            String user = userSnapshot.getValue(String.class);
                            if (user != null && !user.equals(userId)) {
                                acceptUserList.add(user);
                            }
                        }
                        // 변경된 목록을 다시 Firebase에 업데이트합니다.
                        chatRef.child("acceptUser").setValue(acceptUserList).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // UI 업데이트 또는 액티비티 종료 로직
                                Toast.makeText(ChatRoomActivity.this, "채팅방에서 나왔습니다.", Toast.LENGTH_SHORT).show();
                                finish(); // 채팅방 액티비티를 종료합니다.
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ChatRoomActivity.this, "채팅방을 나가는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(ChatRoomActivity.this, "이미 채팅방이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChatRoomActivity.this, "채팅방 정보를 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
