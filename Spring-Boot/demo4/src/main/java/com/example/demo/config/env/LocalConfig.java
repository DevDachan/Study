package com.example.demo.config.env;


import com.example.demo.config.ProfileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.beans.factory.annotation.Value;

@Profile("local")
@Configuration
public class LocalConfig implements EnvConfig{
  private final Logger LOGGER = LoggerFactory.getLogger(ProfileManager.class);

  @Value("${demo.loading.message}")
  private String message;

  @Override
  @Bean
  public String getMessage(){
    LOGGER.info("[getMessage] localConfiguration 입니다.");
    return message;
  }
}
