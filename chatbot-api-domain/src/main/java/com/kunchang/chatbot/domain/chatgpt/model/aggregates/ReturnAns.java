package com.kunchang.chatbot.domain.chatgpt.model.aggregates;

import com.kunchang.chatbot.domain.chatgpt.model.vo.Choices;

import java.util.List;


public class ReturnAns {

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Choices> getChoices() {
        return choices;
    }

    public void setChoices(List<Choices> choices) {
        this.choices = choices;
    }

    private String model;

    private List<Choices> choices;
}
