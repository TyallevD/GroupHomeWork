//package ru.java413.grouphomework.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import ru.java413.grouphomework.services.DatabaseUserDetailsService;
//
//@Configuration
//@EnableWebSecurity
//public class securityConfi{
//
//    private final JwtFilter jwtFilter;
//    private final DatabaseUserDetailsService userDetailsService;
//
//    public securityConfi(JwtFilter jwtFilter, DatabaseUserDetailsService userDetailsService) {
//        this.jwtFilter = jwtFilter;
//        this.userDetailsService = userDetailsService;
//    }
//
//    // Кодировщик паролей
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    // AuthenticationManager (нужен для логина и JWT)
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//
//    // Основной конфиг Spring Security
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http
//                .csrf(csrf -> csrf.disable()) // Отключаем CSRF для API
//
//                .authorizeHttpRequests(auth -> auth
//                        // Разрешаем доступ без авторизации
//                        .requestMatchers(
//                                "/", "/home", "/login", "/register",
//                                "/css/**", "/js/**", "/images/**",
//                                "/api/auth/**" // регистрация/логин через API
//                        ).permitAll()
//                        // Всё остальное — только для авторизованных
//                        .anyRequest().authenticated()
//                )
//
//                .formLogin(form -> form
//                        .loginPage("/") // твоя форма логина
//                        .loginProcessingUrl("/login")
//                        .defaultSuccessUrl("/personpage", true)
//                        .failureUrl("/?error=true")
//                        .usernameParameter("username")
//                        .passwordParameter("password")
//                        .permitAll()
//                )
//
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/?logout=true")
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                        .permitAll()
//                )
//
//                // Настраиваем поведение для JWT (stateless)
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                )
//
//                .userDetailsService(userDetailsService);
//
//        // Добавляем JWT фильтр только для API
//        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//}
