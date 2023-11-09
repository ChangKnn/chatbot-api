package com.kunchang.chatbot.domain.ykt.model.aggregates;

import com.kunchang.chatbot.domain.ykt.model.res.ResData;

public class QueryQuestionList {
    private boolean success;
    private ResData data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ResData getData() {
        return data;
    }

    public void setData(ResData data) {
        this.data = data;
    }



}
