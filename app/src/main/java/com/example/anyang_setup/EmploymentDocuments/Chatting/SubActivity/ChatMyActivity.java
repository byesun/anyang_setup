package com.example.anyang_setup.EmploymentDocuments.Chatting.SubActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.anyang_setup.EmploymentDocuments.Chatting.SubActivity.ChatRoomInfoActivity;
import com.example.anyang_setup.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatMyActivity extends AppCompatActivity {

    private ListView myChatRoomsList;
    private ArrayAdapter<String> adapter;
    private DatabaseReference databaseReference;
    private ArrayList<String> myChatRooms = new ArrayList<>();
    private String userName; // 실제 로그인한 사용자의 이름을 사용합니다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_chat_rooms);

        userName = getIntent().getStringExtra("userName");

        myChatRoomsList = findViewById(R.id.my_chat_list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myChatRooms);
        myChatRoomsList.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        ValueEventListener myChatRoomsListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myChatRooms.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Check if the chat room contains the userName under the "acceptUser" child
                    DataSnapshot acceptUserSnapshot = snapshot.child("chatRef").child("acceptUser");
                    for (DataSnapshot userSnapshot : acceptUserSnapshot.getChildren()) {
                        if (userName.equals(userSnapshot.getValue(String.class))) {
                            String chatRoomTitle = snapshot.getKey();
                            myChatRooms.add(chatRoomTitle);
                            break; // Break the inner loop as we found the userName
                        }
                    }
                }
                adapter.notifyDataSetChanged(); // Refresh the list view
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("ChatMyActivity", "loadMyChatRooms:onCancelled", databaseError.toException());
            }
        };
        // Attach the listener to the chat rooms' "chatRef" node
        databaseReference.child("chat").addListenerForSingleValueEvent(myChatRoomsListener);

        myChatRoomsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedRoom = adapter.getItem(position);
                Intent intent = new Intent(ChatMyActivity.this, ChatRoomInfoActivity.class);
                intent.putExtra("chatRoom", selectedRoom);
                intent.putExtra("userName", userName);
                startActivity(intent);
            }
        });

    }
}
