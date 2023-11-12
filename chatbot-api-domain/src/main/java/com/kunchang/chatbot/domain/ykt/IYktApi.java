package com.kunchang.chatbot.domain.ykt;

import com.kunchang.chatbot.domain.ykt.model.aggregates.QueryQuestionList;
import com.kunchang.chatbot.domain.ykt.model.req.PostEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;


public interface IYktApi {

    public QueryQuestionList queryQuestionList(String classId, String cookie) throws IOException;

    public boolean answerQuestion(String classId, String cookie, PostEntity postEntity) throws IOException;

}
