package com.example.anyang_setup.EmploymentDocuments.Chatting;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.app.AlertDialog;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.anyang_setup.EmploymentDocuments.Chatting.SubActivity.ChatMyActivity;
import com.example.anyang_setup.EmploymentDocuments.Chatting.SubActivity.ChatRoomActivity;
import com.example.anyang_setup.EmploymentDocuments.Chatting.SubActivity.ChatRoomInfoActivity;
import com.example.anyang_setup.EmploymentDocuments.Chatting.SubActivity.CreateNewChatActivity;
import com.example.anyang_setup.EmploymentDocuments.Chatting.SubActivity.chat_option.ChatAdapter;
import com.example.anyang_setup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView chat_list;
    private String userName;
    private String chatRoom;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button addChatRoomButton;
    private Button myButton;
    private TextView chattingStatusLabel;
    private ArrayAdapter<String> adapter;

    ChildEventListener defaultEventListener;
    ChildEventListener myChatEventListener;

    private SearchView searchView;
    private List<String> chatList; // 원래 채팅 목록이라고 가정
    private List<String> filteredChatList; // 필터링된 채팅 목록을 담을 리스트

    private ArrayList<String> myChatRooms = new ArrayList<>();
    private String userId = "user_id"; // 실제 사용자 ID로 대체



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        FirebaseApp.initializeApp(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        chat_list = (ListView) findViewById(R.id.chat_list);
        chattingStatusLabel = findViewById(R.id.chattingStatusLabel);
        FloatingActionButton myButton = (FloatingActionButton) findViewById(R.id.addChatRoomButton);

        myButton.setOnClickListener(this);
        chattingStatusLabel.setOnClickListener(this);
        userName = getIntent().getStringExtra("name");
        chatRoom = getIntent().getStringExtra("chatRoom");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        chat_list.setAdapter(adapter);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_my_chat_room);
        setSupportActionBar(toolbar);


        // 채팅방 검색 기능 설정 (옵션)
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 검색 실행
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 실시간 검색 필터링
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        chat_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView parent, View v, int position, long id){
                EditText edittext = new EditText(parent.getContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                String selected_item = (String)parent.getItemAtPosition(position);
                String[] splitedChatName = selected_item.split("\t");
                builder.setTitle("채팅방 삭제");
                builder.setMessage("*주의* 채팅방을 삭제할 경우 복구할 수 없습니다.");
                builder.setPositiveButton("삭제",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                databaseReference.child("chat").child(splitedChatName[0]).child("chatHello").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DataSnapshot dataSnapshot = task.getResult();
                                            if (dataSnapshot.exists()) {
                                                String chatOwner = dataSnapshot.child("message").getValue(String.class);
                                                if(chatOwner.contains(userName)) {
                                                    databaseReference.child("chat").child(splitedChatName[0]).removeValue();
                                                    Toast.makeText(getApplicationContext(), "채팅방이 정상 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "자신이 만든 채팅방 외에는 삭제할 수 없습니다.", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Log.e("Telechips", "Long Clicked not data");
                                            }
                                        } else {
                                            Log.e("Telechips", "Long Clicked Task Fail");
                                        }
                                    }
                                });
                            }
                        });
                builder.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();
                return true;
            }
        });

        chat_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String selected_item = (String)adapterView.getItemAtPosition(i);
                String[] splitedChatName = selected_item.split("\t");

                databaseReference.child("chat").child(splitedChatName[0]).child("chatRef").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            if (dataSnapshot.exists()) {
                                List<String> acceptUser = new ArrayList<>();
                                boolean joinFlag = false;
                                for (DataSnapshot tmp : dataSnapshot.child("acceptUser").getChildren()) {
                                    acceptUser.add(tmp.getValue(String.class));
                                }
                                String maximumUser = dataSnapshot.child("maximumUser").getValue(String.class);
                                String chatContext = dataSnapshot.child("chatContext").getValue(String.class);
                                String chatOwner = dataSnapshot.child("chatOwner").getValue(String.class);

                                if(acceptUser.contains(userName) || Integer.parseInt(maximumUser) > acceptUser.size()) {
                                    joinFlag = true;
                                }
                                if(joinFlag) {
                                    Map<String, Object> updateData = new HashMap<>();
                                    if(!acceptUser.contains(userName)) {
                                        acceptUser.add(userName);
                                    }
                                    updateData.put("acceptUser", acceptUser);

                                    databaseReference.child("chat").child(splitedChatName[0]).child("chatRef").updateChildren(updateData)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Intent intent = new Intent(ChatActivity.this, ChatRoomInfoActivity.class);

                                                    intent.putExtra("userName", userName);
                                                    intent.putExtra("chatRoom", splitedChatName[0]);
                                                    intent.putExtra("userinfo", maximumUser);
                                                    intent.putExtra("chatContext", chatContext);
                                                    if(acceptUser.contains(userName)) {
                                                        intent.putExtra("chatNowUser", Integer.toString(acceptUser.size()));
                                                    } else {
                                                        intent.putExtra("chatNowUser", Integer.toString(acceptUser.size() - 1));
                                                    }
                                                    startActivity(intent);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getApplicationContext(), "채팅방 입장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                } else {
                                    Toast.makeText(getApplicationContext(), "정원이 초과하여 입장할 수 없습니다.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.e("Telechips", "Join Data not exist");
                            }
                        } else {
                            Log.e("Telechips", "Clicked Task Fail");
                        }
                    }
                });
            }
        });
        setEventListener();
        showChatList();

        searchView = (SearchView) findViewById(R.id.searchView);
        chatList = new ArrayList<>(); // 채팅 목록 초기화
        filteredChatList = new ArrayList<>();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; // 검색 쿼리를 제출할 때 처리하려면 여기에 로직을 구현하세요.
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filteredChatList.clear();
                if (newText.isEmpty()) {
                    filteredChatList.addAll(chatList);
                } else {
                    for (String chatTitle : chatList) {
                        if (chatTitle.toLowerCase().contains(newText.toLowerCase())) {
                            filteredChatList.add(chatTitle);
                        }
                    }
                }
                adapter.clear();
                adapter.addAll(filteredChatList);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    public void setEventListener()
    {
        defaultEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                databaseReference.child("chat").child(snapshot.getKey()).child("chatRef").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            if (dataSnapshot.exists()) {
                                List<String> acceptUser = new ArrayList<>();
                                boolean joinFlag = false;

                                for (DataSnapshot tmp : dataSnapshot.child("acceptUser").getChildren()) {
                                    acceptUser.add(tmp.getValue(String.class));
                                }

                                String maximumUser = dataSnapshot.child("maximumUser").getValue(String.class);
                                String ChattingName = snapshot.getKey() + "\t(" + Integer.toString(acceptUser.size()) + "/" + maximumUser + ")";
                                adapter.add(ChattingName);
                            }
                        }
                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                for(int i = 0 ; i < adapter.getCount(); i++) {
                    if(adapter.getItem(i).contains(snapshot.getKey())) {
                        adapter.remove(adapter.getItem(i));
                        break;
                    }
                }
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };

        myChatEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                databaseReference.child("chat").child(snapshot.getKey()).child("chatHello").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            if (dataSnapshot.exists()) {
                                ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
                                if (chatDTO != null && chatDTO.getMessage() != null && chatDTO.getMessage().contains(userName)) {
                                    databaseReference.child("chat").child(snapshot.getKey()).child("chatRef").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DataSnapshot dataSnapshot = task.getResult();
                                                if (dataSnapshot.exists()) {
                                                    List<String> acceptUser = new ArrayList<>();
                                                    boolean joinFlag = false;

                                                    for (DataSnapshot tmp : dataSnapshot.child("acceptUser").getChildren()) {
                                                        String user = tmp.getValue(String.class);
                                                        if (user != null) {
                                                            acceptUser.add(user);
                                                        }
                                                    }

                                                    String maximumUser = dataSnapshot.child("maximumUser").getValue(String.class);
                                                    if (maximumUser != null) {
                                                        String ChattingName = snapshot.getKey() + "\t(" + Integer.toString(acceptUser.size()) + "/" + maximumUser + ")";
                                                        adapter.add(ChattingName);
                                                    }
                                                }
                                            }
                                        }
                                    });
                                }else {
                                    Log.e("Telechips", "chatHello 데이터가 존재하지 않거나 메시지가 null입니다.");
                                }
                            } else {
                                Log.e("Telechips", "chatHello 데이터가 존재하지 않습니다.");
                            }
                        } else {
                            Log.e("Telechips", "Firebase 조회 실패: " + task.getException().getMessage());
                        }
                    }
                });
            }

        @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                for(int i = 0 ; i < adapter.getCount(); i++)
                {
                    if(adapter.getItem(i).contains(snapshot.getKey()))
                    {
                        adapter.remove(adapter.getItem(i));
                        break;
                    }
                }
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.addChatRoomButton :
            {
                Intent intent = new Intent(ChatActivity.this, CreateNewChatActivity.class);
                intent.putExtra("userinfo", userName);
                startActivity(intent);

                break;
            }
            case R.id.chattingStatusLabel :
            {
                String nowStatus = chattingStatusLabel.getText().toString();

                Log.e("Telechips", nowStatus);

                if(nowStatus.contains("현재 개설된 채팅방"))
                {
                    chattingStatusLabel.setText("내가 개설한 채팅방");
                    adapter.clear();

                    databaseReference.child("chat").removeEventListener(defaultEventListener);
                    databaseReference.child("chat").addChildEventListener(myChatEventListener);
                }
                else if(nowStatus.contains("내가 개설한 채팅방"))
                {
                    chattingStatusLabel.setText("현재 개설된 채팅방");
                    adapter.clear();
                    databaseReference.child("chat").removeEventListener(myChatEventListener);
                    databaseReference.child("chat").addChildEventListener(defaultEventListener);
                }
                break;
            }
        }
    }
    private void showChatList() {
        // 리스트 어댑터 생성 및 세팅
        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("chat").addChildEventListener(defaultEventListener);

        databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String chatRoomTitle = snapshot.getKey();
                    chatList.add(chatRoomTitle);
                }
                filteredChatList.clear();
                filteredChatList.addAll(chatList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ... 에러 처리
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_chat_room, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mo_chat_option:
                // 채팅방 편집 관련 로직
                Intent editIntent = new Intent(this, ChatMyActivity.class);
                editIntent.putExtra("userName", userName);
                startActivity(editIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}