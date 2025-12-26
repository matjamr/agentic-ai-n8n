package com.mat.jamr.aiagentoauthhelper;

import com.mat.jamr.aiagentoauthhelper.entity.User;
import com.mat.jamr.aiagentoauthhelper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AiAgentOauthHelperApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(AiAgentOauthHelperApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.save(new User("jamr.mat", "jamr.mat@gmail.com"));
        userRepository.save(new User("modkil265", "modkil265@gmail.com"));
    }
}
