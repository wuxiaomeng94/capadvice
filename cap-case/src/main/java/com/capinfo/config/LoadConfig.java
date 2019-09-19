package com.capinfo.config;

import com.capinfo.utils.LoadBeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadConfig {


    @Bean
    public LoadBeanUtils loadBean() {
        return new LoadBeanUtils();
    }


}
