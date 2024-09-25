package com.kis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public PasswordEncoder encoder() {
                return new BCryptPasswordEncoder();
        }

        @Configuration
        @Order(1) // 優先度
        public static class ConfigrationAdapter {

                @Autowired
                private UserAccountDetailsService userService;

                @Autowired
                private PasswordEncoder encoder;

                @Order(1)
                @Bean
                public SecurityFilterChain SecurityFilterChain(HttpSecurity http)
                                throws Exception {
                        http.securityMatcher("/**")
                                        .authorizeHttpRequests(authz -> authz
                                                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll() // エラー画面は認証対象外
                                                        .requestMatchers(PathRequest.toStaticResources()
                                                                        .atCommonLocations())
                                                        .permitAll() // 静的リソースは認証対象外
                                                        .requestMatchers("/").permitAll()
                                                        .requestMatchers("/login/**").permitAll()
                                                        // .requestMatchers("/user/auth").permitAll()
                                                        .requestMatchers("/main/**").permitAll()
                                                        .requestMatchers("/equities-tse/**").permitAll()
                                                        .requestMatchers("/voca/**").permitAll()
                                                        // .requestMatchers("/news/**").permitAll()
                                                        .requestMatchers("/indices/**").permitAll()
                                                        // .requestMatchers("/screener/**").permitAll()
                                                        .requestMatchers("/user/account/**").permitAll()
                                                        // .requestMatchers("/user/**").hasRole("USER")
                                                        .anyRequest()
                                                        .authenticated() // その他は認証対象
                                        );
                        http.formLogin(login -> login
                                        .loginProcessingUrl("/user/auth") // 認証処理を起動するURL
                                        .loginPage("/user/login") // ログイン認証画面のURL
                                        .usernameParameter("username") // 認証リクエストのユーザパラメータのキー名の指定
                                        .passwordParameter("password") // 認証リクエストのパスワードパスワードのキー名の指定
                                        .defaultSuccessUrl("/main") // ログイン成功時のURL
                                        .failureUrl("/user/login") // ログイン失敗時のURL
                                        .permitAll() // ログイン画面は認証対象外
                        );
                        http.logout(logout -> logout
                                        .logoutUrl("/user/logout") // ログアウト処理をするURL
                                        .logoutSuccessUrl("/user/login") // ログアウト成功時のURL
                                        .invalidateHttpSession(true) // ログアウト時はセッションを破棄する
                                        .deleteCookies("JSESSIONID") // ログアウト時はクッキーを削除する
                                        .clearAuthentication(true) // ログアウト時は認証情報をクリアする
                                        .permitAll());

                        // 認証処理設定
                        AuthenticationManagerBuilder builder = http
                                        .getSharedObject(AuthenticationManagerBuilder.class);
                        builder.userDetailsService(userService).passwordEncoder(encoder);
                        http.authenticationManager(builder.build());

                        return http.build();
                }
        }
}