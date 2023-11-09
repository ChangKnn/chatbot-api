package com.kunchang.chatbot.api;


import org.apache.http.HttpEntity;
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

public class ApiTest {
    @Test
    public void queryQuestionList() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://www.yuketang.cn/v/discussion/v2/topics/?limit=10&offset=0&cid=17556834&ol=8&rank=1&name=");
        get.addHeader("cookie", "csrftoken=7PgbSKQ0UpDlmpNYVfxJCypDDJ1IkpC1; sessionid=m0r4b29705aovbznr2sme6xdahxp2oxq; classroomId=17556834; classroom_id=17556834; django_language=zh-cn");
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
        post.addHeader("cookie", "csrftoken=7PgbSKQ0UpDlmpNYVfxJCypDDJ1IkpC1; sessionid=m0r4b29705aovbznr2sme6xdahxp2oxq; classroomId=17556834; classroom_id=17556834; django_language=zh-cn");
        post.addHeader("content-type", "application/json;charset=UTF-8");
        post.addHeader("referer", "https://www.yuketang.cn/v2/web/studentLog/17556834");
        post.addHeader("classroom-id", "17556834");
        post.addHeader("Uv-Id:", "3090");
        post.addHeader("X-Client", "web");
        post.addHeader("X-Csrftoken", "7PgbSKQ0UpDlmpNYVfxJCypDDJ1IkpC1");
        post.addHeader("Xt-Agent", "web");
        post.addHeader("Xtbz", "ykt");
        post.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36 Edg/119.0.0.0");

        String paramJson = "{\n" +
                "  \"to_user\": 25212695,\n" +
                "  \"topic_id\": 12756641,\n" +
                "  \"content\": {\n" +
                "    \"text\": \"论文的核心方法，创新点，关键词、参考文献，未来期望，论文的主要观点，实验方法以及给自己的启发\",\n" +
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
}
