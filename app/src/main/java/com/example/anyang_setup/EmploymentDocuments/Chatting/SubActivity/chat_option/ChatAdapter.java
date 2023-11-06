package com.example.anyang_setup.EmploymentDocuments.Chatting.SubActivity.chat_option;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.anyang_setup.EmploymentDocuments.Chatting.ChatDTO;
import com.example.anyang_setup.R;
import java.util.List;

import com.example.anyang_setup.Info.UserInfoActivity;

import com.bumptech.glide.Glide;

public class ChatAdapter extends ArrayAdapter<ChatDTO> {

    private UserInfoActivity profileImageView;

    private static class ViewHolder {
        boolean isMine;
        TextView nameText;
        TextView messageText;
        TextView subItemText;
        ImageView profileImage;
        ImageView messageImage; // 추가된 이미지 뷰
    }

    public ChatAdapter(Context context, List<ChatDTO> messages) {
        super(context, 0, messages);
    }

    @SuppressLint("InflateParams")
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
            viewHolder.profileImage = convertView.findViewById(R.id.iv_profile); // 프로필 이미지 ID
            viewHolder.messageImage = convertView.findViewById(R.id.iv_message_image); // 메시지 이미지 ID
            viewHolder.isMine = message.isMine();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.nameText.setText(message.getUserName());
        viewHolder.messageText.setText(message.getMessage());
        viewHolder.subItemText.setText(message.getChatTime());

        // 프로필 이미지 로딩
        if (message.getProfileImageUrl() != null && !message.getProfileImageUrl().isEmpty()) {
            Glide.with(getContext())
                    .load(message.getProfileImageUrl())
                    .placeholder(R.drawable.ic_home) // 기본 이미지 설정
                    .error(R.drawable.ic_home) // 로딩 실패시 기본 이미지
                    .circleCrop() // 원형으로 크롭
                    .into(viewHolder.profileImage);
        } else {
            // 프로필 이미지가 없을 경우 기본 이미지 설정
            Glide.with(getContext())
                    .load(R.drawable.ic_home)
                    .circleCrop()
                    .into(viewHolder.profileImage);
        }

        // 메시지 이미지 로직
        if (message.getFileUrl() != null && !message.getFileUrl().isEmpty()) {
            Glide.with(getContext())
                    .load(message.getFileUrl())
                    .into(viewHolder.messageImage);
            viewHolder.messageImage.setVisibility(View.VISIBLE); // 이미지가 있으면 이미지 뷰를 보이게
            viewHolder.messageText.setVisibility(View.GONE); // 텍스트 뷰를 숨기기
        } else {
            viewHolder.messageImage.setVisibility(View.GONE); // 이미지가 없으면 이미지 뷰를 숨기기
            viewHolder.messageText.setVisibility(View.VISIBLE); // 텍스트 뷰를 보이게
        }
        return convertView;
    }
}
