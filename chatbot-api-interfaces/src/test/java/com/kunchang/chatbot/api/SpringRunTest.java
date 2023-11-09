package com.kunchang.chatbot.api;

import com.alibaba.fastjson2.JSON;
import com.kunchang.chatbot.domain.chatgpt.IChatGPTApi;
import com.kunchang.chatbot.domain.ykt.IYktApi;
import com.kunchang.chatbot.domain.ykt.model.aggregates.QueryQuestionList;
import com.kunchang.chatbot.domain.ykt.model.vo.Results;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringRunTest {
    private Logger logger = LoggerFactory.getLogger(SpringRunTest.class);

    @Value("${chat-bot.classId}")
    private String classId;

    @Value("${chat-bot.cookie}")
    private String cookie;
    @Value("${chat-bot.token}")
    private String token;
    @Value("${chat-bot.text}")
    private String text;


    @Autowired
    private IYktApi yktApi;
    @Autowired
    private IChatGPTApi chatGPTApi;
    @Test
    public void testIYkt() throws IOException {
        QueryQuestionList questionList = yktApi.queryQuestionList(classId, cookie);
        logger.info("JSON ANS :{}", JSON.toJSONString(questionList));
        List<Results> list = questionList.getData().getResults();
        for (Results res : list) {
            String text = res.getContent().getText();
            logger.info("Text:{}", text);
        }
    }

    @Test
    public void testChatGPT() throws IOException {
        System.out.println(chatGPTApi.getGPTAns("北京邮电大学纯脑瘫"));
    }
}
