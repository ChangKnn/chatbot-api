package com.kunchang.chatbot.domain.chatgpt.model.service;

import com.alibaba.fastjson2.JSONObject;
import com.kunchang.chatbot.domain.chatgpt.IChatGPTApi;
import com.kunchang.chatbot.domain.chatgpt.model.aggregates.ReturnAns;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ChatGPTApi implements IChatGPTApi {

    private Logger logger = LoggerFactory.getLogger(ChatGPTApi.class);

    @Value("${chat-bot.proxyHost}")
    private String proxyHost;
    @Value("${chat-bot.proxyPort}")
    private int proxyPort;
    @Value("${chat-bot.openAIToken}")
    private String openAIToken;
    @Override
    public String getGPTAns(String question) throws IOException {

        HttpHost httpProxy = new HttpHost(proxyHost, proxyPort);
        CloseableHttpClient client = HttpClientBuilder.create().setProxy(httpProxy).build();

        HttpPost post = new HttpPost("https://api.openai.com/v1/chat/completions");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer " + openAIToken);

        String jsonParam = "{\n" +
                "  \"model\": \"gpt-3.5-turbo\",\n" +
                "  \"messages\": [\n" +
                "    {\n" +
                "      \"role\": \"system\",\n" +
                "      \"content\": \"100字内回答以下问题\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"role\": \"user\",\n" +
                "      \"content\": \"" + question + "\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";


        StringEntity stringEntity = new StringEntity(jsonParam, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = client.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            ReturnAns ans = JSONObject.parseObject(jsonStr, ReturnAns.class);
            logger.info("ans:{}", ans.getChoices().get(0).getMessage().getContent());
            return ans.getChoices().get(0).getMessage().getContent();
        } else {
            throw new RuntimeException("Error code:" + response.getStatusLine().getStatusCode());
        }
    }
}
