package com.i.information;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther: peng
 * @Date: 2021/12/13 0013 10:31
 * @Description:
 */
@SpringBootApplication
public class AppMain {

    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplication(AppMain.class);
        springApplication.run(args);
    }
}
