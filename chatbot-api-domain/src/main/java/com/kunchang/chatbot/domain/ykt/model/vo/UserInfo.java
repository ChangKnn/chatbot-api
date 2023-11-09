package com.kunchang.chatbot.domain.ykt.model.vo;

public class UserInfo
{
    private int user_id;

    private String name;

    private String school_number;

    private int role;

    private String avatar;

    private String nickname;

    public void setUser_id(int user_id){
        this.user_id = user_id;
    }
    public int getUser_id(){
        return this.user_id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setSchool_number(String school_number){
        this.school_number = school_number;
    }
    public String getSchool_number(){
        return this.school_number;
    }
    public void setRole(int role){
        this.role = role;
    }
    public int getRole(){
        return this.role;
    }
    public void setAvatar(String avatar){
        this.avatar = avatar;
    }
    public String getAvatar(){
        return this.avatar;
    }
    public void setNickname(String nickname){
        this.nickname = nickname;
    }
    public String getNickname(){
        return this.nickname;
    }
}