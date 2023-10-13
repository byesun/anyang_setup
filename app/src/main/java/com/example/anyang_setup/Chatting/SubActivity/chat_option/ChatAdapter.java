package com.example.anyang_setup.Chatting.SubActivity.chat_option;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.anyang_setup.Chatting.ChatDTO;
import com.example.anyang_setup.R;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<ChatDTO> {

    public ChatAdapter(Context context, List<ChatDTO> messages) {
        super(context, 0, messages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatDTO message = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_item2, parent, false);
        }

        TextView messageText = convertView.findViewById(R.id.message_text);
        TextView subItemText = convertView.findViewById(R.id.sub_item);

        messageText.setText(message.getUserName() + " : " + message.getMessage());
        subItemText.setText("(" + message.getChatTime() + ")");

        return convertView;
    }
}
