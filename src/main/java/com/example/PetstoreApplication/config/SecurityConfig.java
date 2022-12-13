package com.example.PetstoreApplication.config;

import com.example.PetstoreApplication.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
  private final PersonDetailsService personDetailsService;
  @Autowired
  public SecurityConfig(PersonDetailsService personDetailsService)
  {
    this.personDetailsService = personDetailsService;
  }

  // Настройка аутентификации
  @Bean
  public AuthenticationManager authenticationManager(
          HttpSecurity http,
          BCryptPasswordEncoder bCryptPasswordEncoder,
          UserDetailsService personDetailService) throws Exception
  {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(personDetailService)
            .passwordEncoder(bCryptPasswordEncoder)
            .and()
            .build();
  }

  @Bean
  public static BCryptPasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  // Конфигурация Spring Security

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

      // указываем что все страницы будут защищены процессом аутентификации
        http
              //  .csrf().disable()
                .authorizeHttpRequests()
            // указываем что страница /admin доступна пользователю с ролью ADMIN
            .requestMatchers("/admin").hasRole("ADMIN")
            // Указыаем что данные страницы доступна все пользователям
            .requestMatchers("/authentication/login",
                    "/authentication/registration","/error", "/index",
                    "/product/**", "/product/info/**", "/resources/static/**", "/resources/static/*",
                    "/img/**", "/css/**", "/css/*", "/js/**", "/resources" +
                            "/static/js" +
                            "/**", "/resources/static/css/**").permitAll()
             //   .requestMatchers("/resources/static/**", "/img/**")
                //   .permitAll()
            // Указываем что все остальные страницы доступны пользователям с ролью user и admin
           // .anyRequest().hasAnyRole("USER", "ADMIN")
                // Указываем что для всех остальных страниц необходимо вызывать метод authenticated(), который открывает форму аутентификации
            .anyRequest().authenticated()

            .and()
            .formLogin().loginPage("/authentication/login").permitAll()
            // указываем на какой url будут отправляться данные с формы аутентификации
            .loginProcessingUrl("/process_login")
            // Указываем на какой url необходимо направить пользователя после успешной аутентификации
            .defaultSuccessUrl("/user", true)
            // Указываем куда необходимо перейти при неверный аутентификации
            .failureUrl("/authentication/login?error")
            .and()
            .logout().logoutUrl("/logout").logoutSuccessUrl("/authentication/login");

    return http.build();
  }
}