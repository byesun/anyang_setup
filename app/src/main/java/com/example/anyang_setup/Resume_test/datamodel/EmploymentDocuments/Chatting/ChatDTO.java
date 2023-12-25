package com.example.anyang_setup.Resume_test.datamodel.EmploymentDocuments.Chatting;

public class ChatDTO {

    private String userName;
    private String message;
    private String chatTime;
    private boolean isMine;
    private String fileUrl;
    private String profileImageUrl; // 프로필 이미지 URL 필드 추가
    private int iconResourceId; // 카테고리 아이콘의 리소스 ID
    private String name; // 카테고리 이름
    private String chatRoom; // 채팅방 이름 필드 추가

    // 인자 없는 생성자 추가
    public ChatDTO() {
    }

    // 카테고리 생성자
    public ChatDTO(int iconResourceId, String name) {
        this.iconResourceId = iconResourceId;
        this.name = name;
    }

    // 채팅 메시지 생성자
    public ChatDTO(String userName, String message, String chatTime) {
        this.userName = userName;
        this.message = message;
        this.chatTime = chatTime;
    }

    // 프로필 이미지를 포함하는 채팅 메시지 생성자
    public ChatDTO(String userName, String message, String chatTime, String profileImageUrl) {
        this.userName = userName;
        this.message = message;
        this.chatTime = chatTime;
        this.profileImageUrl = profileImageUrl;
    }

    // 기본 getter 및 setter
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public String getMessage() {
        return message;
    }

    public String getChatTime() {
        return chatTime;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }

    public String getName() {
        return name;
    }

    public String getChatRoom() {
        return chatRoom;
    }

    // fileUrl getter 및 setter
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    // profileImageUrl getter 및 setter 추가
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
