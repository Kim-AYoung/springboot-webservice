package com.kima.springboot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// * 프로젝트의 메인 클래스
// - 스프링 부트의 자동 설정
// - 스프링 Bean 읽기와 생성을 자동으로 설정
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // 내장 WAS 실행 -> 톰캣 설치 필요 없음
       SpringApplication.run(Application.class, args);

    }
}
