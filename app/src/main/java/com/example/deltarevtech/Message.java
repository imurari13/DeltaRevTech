package com.example.deltarevtech;

public class Message {
    public static String SENT_BY_USER = "user";
    public static String SENT_BY_AI = "delta";

    String message;
    String sentBy;

    public Message(String message, String sentBy) {
        this.message = message;
        this.sentBy = sentBy;
    }

    public String getMessage() { return message; }
    public String getSentBy() { return sentBy; }
}