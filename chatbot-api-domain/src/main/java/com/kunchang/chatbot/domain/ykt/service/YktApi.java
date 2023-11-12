package com.kunchang.chatbot.domain.ykt.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.kunchang.chatbot.domain.ykt.IYktApi;
import com.kunchang.chatbot.domain.ykt.model.aggregates.QueryQuestionList;
import com.kunchang.chatbot.domain.ykt.model.req.AnsReq;
import com.kunchang.chatbot.domain.ykt.model.req.PostEntity;
import com.kunchang.chatbot.domain.ykt.model.req.ReqData;
import com.kunchang.chatbot.domain.ykt.model.res.AnsRes;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class YktApi implements IYktApi {

    private Logger logger = LoggerFactory.getLogger(YktApi.class);

    @Override
    public QueryQuestionList queryQuestionList(String classId, String cookie) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // limit = 100 取大值，拉取所有数据
        HttpGet get = new HttpGet("https://www.yuketang.cn/v/discussion/v2/topics/?limit=100&offset=0&cid="+ classId +"&ol=8&rank=1&name=");
        get.addHeader("cookie", cookie);
        get.addHeader("content-Type", "application/json");

        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("classid:{}, jsonStr:{}", classId, jsonStr);
            return JSON.parseObject(jsonStr, QueryQuestionList.class);
        } else {
            throw new RuntimeException("Error code :" + response.getStatusLine().getStatusCode());
        }
    }

    @Override
    public boolean answerQuestion(String classId, String cookie, PostEntity postEntity) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String CSRFToken = getCSRFToken(cookie);

        HttpPost post = getHttpPost(classId, cookie, CSRFToken);

        String paramJson = JSONObject.from(postEntity).toString();
        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("json:{}", jsonStr);
            AnsRes ansRes = JSON.parseObject(jsonStr, AnsRes.class);
            return ansRes.isSuccess();
        } else {
            throw new RuntimeException("Error code :" + response.getStatusLine().getStatusCode());
        }
    }

    private static HttpPost getHttpPost(String classId, String cookie, String CSRFToken) {
        HttpPost post = new HttpPost("https://www.yuketang.cn/v/discussion/v2/comment/");
        post.addHeader("cookie", cookie);
        post.addHeader("content-type", "application/json;charset=UTF-8");
        post.addHeader("referer", "https://www.yuketang.cn/v2/web/studentLog/" + classId);
        post.addHeader("classroom-id", classId);
        post.addHeader("Uv-Id:", "3090");
        post.addHeader("X-Client", "web");
        post.addHeader("X-Csrftoken", CSRFToken);
        post.addHeader("Xt-Agent", "web");
        post.addHeader("Xtbz", "ykt");
        post.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36 Edg/119.0.0.0");
        return post;
    }

    private String getCSRFToken(String cookie) {
        String pattern = "(?<=csrftoken=).*?(?=;)";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(cookie);

        if (matcher.find()) {
            return matcher.group(0);
        } else {
            return "获取失败";
        }
    }
}
