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

    private static class ViewHolder {
        public boolean isMine;
        TextView nameText;
        TextView messageText;
        TextView subItemText;
        ImageView pro;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatDTO message = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null || !(convertView.getTag() instanceof ViewHolder) || ((ViewHolder) convertView.getTag()).isMine != message.isMine()) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    message.isMine() ? R.layout.my_msgbox : R.layout.other_msgbox,
                    parent, false
            );
            viewHolder = new ViewHolder();
            viewHolder.nameText = convertView.findViewById(R.id.tv_name);
            viewHolder.messageText = convertView.findViewById(R.id.tv_msg);
            viewHolder.subItemText = convertView.findViewById(R.id.tv_time);
            viewHolder.pro = convertView.findViewById(R.id.iv);
            viewHolder.isMine = message.isMine();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.nameText.setText(message.getUserName());
        viewHolder.messageText.setText(message.getMessage());
        viewHolder.subItemText.setText(message.getChatTime());

        // 이미지 설정 예시
        // viewHolder.pro.setImageResource(R.drawable.image_resource_name);

        return convertView;
    }
}
