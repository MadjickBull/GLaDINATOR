package com.codeforall.online.gladinator.config.aiConfig;

import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.FactoryBean;


public class OpenAiApiFactory implements FactoryBean<OpenAiApi> {

    private String apiKey;

    @Override
    public OpenAiApi getObject() {
        return OpenAiApi.builder().apiKey(apiKey).build();
    }


    @Override
    public Class<?> getObjectType() {
        return OpenAiApi.class;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
