package com.codeforall.online.gladinator.config.gameConfig;

import com.codeforall.online.gladinator.model.session.GameConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameBeansConfig {

    @Bean
    public GameConfig gameConfig() {
        return new GameConfig(3,4);
    }

}
