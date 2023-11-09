package com.kunchang.chatbot.domain.ykt.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.kunchang.chatbot.domain.ykt.IYktApi;
import com.kunchang.chatbot.domain.ykt.model.aggregates.QueryQuestionList;
import com.kunchang.chatbot.domain.ykt.model.req.AnsReq;
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
@Service
public class YktApi implements IYktApi {

    private Logger logger = LoggerFactory.getLogger(YktApi.class);

    @Override
    public QueryQuestionList queryQuestionList(String classId, String cookie) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://www.yuketang.cn/v/discussion/v2/topics/?limit=10&offset=0&cid="+ classId +"&ol=8&rank=1&name=");
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
    public boolean answerQuestion(String classId, String cookie, String token, String text) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://www.yuketang.cn/v/discussion/v2/comment/");
        post.addHeader("cookie", cookie);
        post.addHeader("content-type", "application/json;charset=UTF-8");
        post.addHeader("referer", "https://www.yuketang.cn/v2/web/studentLog/" + classId);
        post.addHeader("classroom-id", classId);
        post.addHeader("Uv-Id:", "3090");
        post.addHeader("X-Client", "web");
        post.addHeader("X-Csrftoken", token);
        post.addHeader("Xt-Agent", "web");
        post.addHeader("Xtbz", "ykt");
        post.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36 Edg/119.0.0.0");

//        String paramJson = "{\n" +
//                "  \"to_user\": 25212695,\n" +
//                "  \"topic_id\": 12756641,\n" +
//                "  \"content\": {\n" +
//                "    \"text\": \"论文的核心方法，创新点，关键词、参考文献，未来期望，论文的主要观点，实验方法以及给自己的启发\",\n" +
//                "    \"upload_images\": [],\n" +
//                "    \"accessory_list\": []\n" +
//                "  }\n" +
//                "}";

        AnsReq ansReq = new AnsReq(new ReqData(text));
        String paramJson = JSONObject.from(ansReq).toString();
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
}
