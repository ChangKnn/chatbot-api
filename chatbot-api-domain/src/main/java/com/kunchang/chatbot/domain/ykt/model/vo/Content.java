package com.kunchang.chatbot.domain.ykt.model.vo;

import java.util.List;

public class Content
{
    private String text;

    private List<String> accessory_list;

    public void setText(String text){
        this.text = text;
    }
    public String getText(){
        return this.text;
    }
    public void setAccessory_list(List<String> accessory_list){
        this.accessory_list = accessory_list;
    }
    public List<String> getAccessory_list(){
        return this.accessory_list;
    }
}