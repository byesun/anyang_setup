package com.example.anyang_setup.EmploymentDocuments.Chatting.SubActivity.chat_option;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.anyang_setup.EmploymentDocuments.Chatting.ChatDTO;
import com.example.anyang_setup.R;
import java.util.List;

public class ChatAdapter extends ArrayAdapter<ChatDTO> {

    public ChatAdapter(Context context, List<ChatDTO> messages) {
        super(context, 0, messages);
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatDTO message = getItem(position);
        Log.d("ChatAdapter", "Position: " + position + ", isMine: " + message.isMine());

        if (convertView == null || !(convertView.getTag() instanceof Boolean) || (Boolean) convertView.getTag() != message.isMine()) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    message.isMine() ? R.layout.my_msgbox : R.layout.other_msgbox,
                    parent, false
            );
            convertView.setTag(Boolean.valueOf(message.isMine()));
        }

        TextView nameText = convertView.findViewById(R.id.tv_name);
        TextView messageText = convertView.findViewById(R.id.tv_msg);
        TextView subItemText = convertView.findViewById(R.id.tv_time);
        ImageView pro = convertView.findViewById(R.id.iv);

        nameText.setText(message.getUserName());
        messageText.setText(message.getMessage());
        subItemText.setText(message.getChatTime());

        // pro.setImageResource(R.id.ID_Picture);
        // 주석 처리된 부분은 필요에 따라 수정하시면 됩니다.

        return convertView;
    }
}
