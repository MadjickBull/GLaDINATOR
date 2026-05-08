package com.codeforall.online.gladinator.config.aiConfig;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.FactoryBean;


public class OpenAiChatModelFactory implements FactoryBean<OpenAiChatModel> {

    private OpenAiApi openAiApi;
    private OpenAiChatOptions openAiChatOptions;


    @Override
    public OpenAiChatModel getObject() {
        return OpenAiChatModel
                .builder()
                .openAiApi(openAiApi)
                .defaultOptions(openAiChatOptions)
                .build();
    }

    @Override
    public Class<?> getObjectType() {
        return OpenAiChatModel.class;
    }


    public void setOpenAiApi(OpenAiApi openAiApi) {
        this.openAiApi = openAiApi;
    }


    public void setOpenAiChatOptions(OpenAiChatOptions openAiChatOptions) {
        this.openAiChatOptions = openAiChatOptions;
    }
}
