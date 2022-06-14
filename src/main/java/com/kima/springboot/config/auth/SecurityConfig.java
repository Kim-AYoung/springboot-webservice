package com.kima.springboot.config.auth;

import com.kima.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // h2-console 화면을 사용하기 위해
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    // url별 권한 관리를 설정하는 옵션의 시작점
                    .authorizeRequests()
                    // 권한 관리 대상 지정
                    // - http method, url별로 관리 가능
                    .antMatchers("/", "/css/**", "/image/**", "/js/**", "/h2-console/**", "/profile").permitAll() // 전체 열람 권한
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // USER 권한을 가진 사람만 허용
                    .anyRequest().authenticated() // 설정된 값들 이외의 나머지 URL은 인증된 사용자(로그인된 사용자)들에게만 허용
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    // OAuth2 로그인 기능에 대한 여러 설정의 진입점
                    .oauth2Login()
                        // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당
                        .userInfoEndpoint()
                            // 소셜 로그인 성공 시 후속 조치를 진행할 UserService interface의 구현체를 등록
                            // 리소스 서버(social service)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시
                            .userService(customOAuth2UserService);


    }


}
