package com.shivamprinters.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class AppProperties {

    private String name;
    private String tagline;
    private String uploadDir;
    private String baseUrl;
    private AdminProperties admin = new AdminProperties();
    private MailProperties mail = new MailProperties();

    @Getter
    @Setter
    public static class AdminProperties {
        private String email;
        private String password;
    }

    @Getter
    @Setter
    public static class MailProperties {
        private String from;
        private boolean enabled;
    }
}
