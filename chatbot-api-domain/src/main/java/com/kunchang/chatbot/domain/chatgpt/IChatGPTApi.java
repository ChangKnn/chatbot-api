package com.kunchang.chatbot.domain.chatgpt;

import java.io.IOException;

public interface IChatGPTApi {

    public String getGPTAns(String question) throws IOException;
}
