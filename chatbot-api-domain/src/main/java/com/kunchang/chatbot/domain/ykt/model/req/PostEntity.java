package com.kunchang.chatbot.domain.ykt.model.req;

import com.kunchang.chatbot.domain.ykt.model.vo.PostContent;

public class PostEntity {

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public int getTo_user() {
        return to_user;
    }

    public void setTo_user(int to_user) {
        this.to_user = to_user;
    }

    public PostContent getContent() {
        return content;
    }

    public void setContent(PostContent content) {
        this.content = content;
    }


    private int topic_id;

    private int to_user;

    private PostContent content;


}
