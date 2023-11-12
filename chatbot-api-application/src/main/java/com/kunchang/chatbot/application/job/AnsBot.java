package com.kunchang.chatbot.application.job;

import com.kunchang.chatbot.domain.chatgpt.IChatGPTApi;
import com.kunchang.chatbot.domain.ykt.IYktApi;
import com.kunchang.chatbot.domain.ykt.model.req.PostEntity;
import com.kunchang.chatbot.domain.ykt.model.vo.PostContent;
import com.kunchang.chatbot.domain.ykt.model.vo.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.List;

@EnableScheduling
@Configuration
@ComponentScan("com.kunchang.chatbot.domain")
public class AnsBot {

    private Logger logger = LoggerFactory.getLogger(AnsBot.class);

    @Value("${chat-bot.classId}")
    private String classId;
    @Value("${chat-bot.cookie}")
    private String cookie;

    @Autowired
    private IYktApi yktApi;
    @Autowired
    private IChatGPTApi chatGPTApi;

    @Scheduled(fixedDelay = Long.MAX_VALUE)
    public void run() throws IOException {
        String htmlRegex="<[^>]+>";
        logger.info("开始答题");
        try {
            List<Results> results = yktApi.queryQuestionList(classId, cookie).getData().getResults();

            if (results == null || results.isEmpty()) {
                logger.info("当前无问题");
                return;
            }

            for (Results r: results) {

                String text = r.getContent().getText().replaceAll(htmlRegex, "");
                logger.info("当前回答问题：{}", text);

                String ans = chatGPTApi.getGPTAns(text);
                logger.info("答案为：{}", ans);

                PostEntity entity = new PostEntity();
                entity.setTopic_id(r.getId());
                entity.setTo_user(r.getUser_id());
                entity.setContent(new PostContent(ans));

                boolean b = yktApi.answerQuestion(classId, cookie, entity);
                logger.info("回答状态：{}", b);

                Thread.sleep(60 * 1000);   // 间隔1分钟
            }

        } catch (Exception e) {
            logger.info("自动回答出错：", e);
        }
    }
}
