package com.shop.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((authz) -> authz
            .anyRequest().authenticated()
        )
        .httpBasic(withDefaults());

    http.formLogin()
        .loginPage("/members/login")  // 로그인 페이지 URL 설정
        .defaultSuccessUrl("/")       // 로그인 성공 시 이동할 URL
        .usernameParameter("email")   // 로그인 시 사용할 파라미터 이름으로 email을 지정
        .failureUrl("/members/login/error")   // 로그인 실패 시 이동할 URL을 설정
        .and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) // 로그아웃 URL 설정
        .logoutSuccessUrl("/"); // 로그아웃 성공 시 이동할 URL

//    http.authorizeRequests()
//        .mvcMatchers("/css/**", "/js/**", "/img/**").permitAll()
//        .mvcMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
//        .mvcMatchers("/admin/**").hasRole("ADMIN")
//        .anyRequest().authenticated();

//    http.exceptionHandling()
//        .authenticationEntryPoint(new CustomAuthenticationEntryPoint());

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

  }
}
