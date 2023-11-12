package com.kunchang.chatbot.domain.ykt.model.vo;

public class PostContent {

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public PostContent(String text) {
        this.text = text;
    }

    private String text;

}
