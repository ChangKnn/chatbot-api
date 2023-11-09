package com.kunchang.chatbot.domain.ykt.model.vo;

import java.util.List;

public class Data
{
    private int count;

    private String previous;

    private List<Results> results;

    private String next;

    public void setCount(int count){
        this.count = count;
    }
    public int getCount(){
        return this.count;
    }
    public void setPrevious(String previous){
        this.previous = previous;
    }
    public String getPrevious(){
        return this.previous;
    }
    public void setResults(List<Results> results){
        this.results = results;
    }
    public List<Results> getResults(){
        return this.results;
    }
    public void setNext(String next){
        this.next = next;
    }
    public String getNext(){
        return this.next;
    }
}

