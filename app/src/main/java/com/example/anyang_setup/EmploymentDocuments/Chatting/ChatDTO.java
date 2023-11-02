package com.example.anyang_setup.EmploymentDocuments.Chatting;

public class ChatDTO {

    private String userName;
    private String message;
    private String chatTime;
    private boolean isMine;
    private int iconResourceId; // 카테고리 아이콘의 리소스 ID
    private String name; // 카테고리 이름
    private String chatRoom; // 채팅방 이름 필드 추가

    // 인자 없는 생성자 추가
    public ChatDTO() {
    }

    public ChatDTO(int iconResourceId, String name) {
        //카테고리 변수
        this.iconResourceId = iconResourceId;
        this.name = name;
    }
    public ChatDTO(String userName, String message, String chatTime) {
        this.userName = userName;
        this.message = message;
        this.chatTime = chatTime;
    }

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
}
