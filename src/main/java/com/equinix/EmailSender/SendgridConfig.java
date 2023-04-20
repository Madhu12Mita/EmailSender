package com.equinix.EmailSender;

import com.sendgrid.SendGrid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class SendgridConfig {

    @Value("${sendgrid.key}")
    private String key;

    @Bean
    public SendGrid getSendGrid()
    {
        return new SendGrid(key);
    }
}
