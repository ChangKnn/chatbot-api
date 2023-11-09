package com.kunchang.chatbot.domain.ykt.model.res;

import com.kunchang.chatbot.domain.ykt.model.vo.Results;

import java.util.List;

public class ResData {

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    private List<Results> results;
}
