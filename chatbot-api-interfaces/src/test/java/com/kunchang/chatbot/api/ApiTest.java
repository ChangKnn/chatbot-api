package com.kunchang.chatbot.api;


import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.http.HttpStatusCode;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiTest {
    @Test
    public void queryQuestionList() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("");
        get.addHeader("cookie", "");
        get.addHeader("content-Type", "application/json");

        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString((HttpEntity) response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }
    @Test
    public void answerQuestion() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://www.yuketang.cn/v/discussion/v2/comment/");
        post.addHeader("cookie", "");
        post.addHeader("content-type", "application/json;charset=UTF-8");
        post.addHeader("referer", "https://www.yuketang.cn/v2/web/studentLog/17556834");
        post.addHeader("classroom-id", "17556834");
        post.addHeader("Uv-Id:", "3090");
        post.addHeader("X-Client", "web");
        post.addHeader("X-Csrftoken", "");
        post.addHeader("Xt-Agent", "web");
        post.addHeader("Xtbz", "ykt");
        post.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36 Edg/119.0.0.0");

        String paramJson = "{\n" +
                "  \"to_user\": ,\n" +
                "  \"topic_id\": ,\n" +
                "  \"content\": {\n" +
                "    \"text\": \"\",\n" +
                "    \"upload_images\": [],\n" +
                "    \"accessory_list\": []\n" +
                "  }\n" +
                "}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void chatGPTAPItest() throws IOException {

        String proxyHost = "127.0.0.1";
        int proxyPort = 7890;
        HttpHost httpProxy = new HttpHost(proxyHost, proxyPort);
        CloseableHttpClient client = HttpClientBuilder.create().setProxy(httpProxy).build();

        HttpPost post = new HttpPost("https://api.openai.com/v1/chat/completions");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer ");

        String jsonParam = "{\n" +
                "  \"model\": \"gpt-3.5-turbo\",\n" +
                "  \"messages\": [\n" +
                "    {\n" +
                "      \"role\": \"system\",\n" +
                "      \"content\": \"100字内回答以下问题\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"role\": \"user\",\n" +
                "      \"content\": \"简述ChatGPT\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"max_tokens\": 556\n" +
                "}";


        StringEntity stringEntity = new StringEntity(jsonParam, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = client.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String string = EntityUtils.toString(response.getEntity());
            System.out.println(string);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void getCSRFToken() {
        String cookie = "";
        String pattern = "(?<=csrftoken=).*?(?=;)";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(cookie);

        if (matcher.find()) {
            System.out.println(matcher.group(0));
            //return matcher.group(0);
        } else {
            System.out.println("获取失败");
        }
    }
}
