package com.example.demo.config.env;

import com.example.demo.config.ProfileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Configuration
public class DevConfig implements EnvConfig{
  private final Logger LOGGER = LoggerFactory.getLogger(ProfileManager.class);


  @Value("## dev 환경으로 실행되었습니다. ##")
  private String message;

  @Override
  @Bean
  public String getMessage(){
    LOGGER.info("[getMessage] devConfiguration 입니다.");
    return message;
  }

}
