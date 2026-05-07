package codeforall.com.online.gladinator.config;

import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiConfig {

    //criação de beam - opeanAiConfig
    //cria o cliente com a API Key
    @Bean
    public OpenAiConfig openAiConfig() {
        return OpenAiApi.builder()
            .apiKey(System.getenv("OPENAI_API_KEY"))
                .build();
    }

    //criação de bean - openAiChatOptions
    //define modelo e temperatura



    //criação de bean - openAiChatModel
    //Liga API às opções do chat



    //criação de bean - chatClient
    //objeto que o AiServiceImpl vai usar para fazer prompts

}


