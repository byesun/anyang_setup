package com.example.anyang_setup.EmploymentDocuments.Chatting;

public class ChatDTO {

    private String userName;
    private String message;
    private String chatTime;
    private boolean isMine;

    public ChatDTO() {}
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
}