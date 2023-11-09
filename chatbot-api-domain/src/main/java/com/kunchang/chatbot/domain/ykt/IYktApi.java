package com.kunchang.chatbot.domain.ykt;

import com.kunchang.chatbot.domain.ykt.model.aggregates.QueryQuestionList;
import org.springframework.stereotype.Service;

import java.io.IOException;


public interface IYktApi {

    public QueryQuestionList queryQuestionList(String classId, String cookie) throws IOException;

    public boolean answerQuestion(String classId, String cookie, String token, String text) throws IOException;

}
