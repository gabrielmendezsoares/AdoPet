package com.adopet.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile("production")
public class SecurityConfigurationProduction {

  private final DataSource dataSource;

  @Value("${users.user.username}")
  private String userUsername;

  @Value("${users.user.password}")
  private String userPassword;

  @Value("${users.admin.username}")
  private String adminUsername;

  @Value("${users.admin.password}")
  private String adminPassword;

  public SecurityConfigurationProduction(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
            .requestMatchers("/home/**").authenticated()
            .anyRequest().permitAll())
        .httpBasic(Customizer.withDefaults())
        .formLogin(formLogin -> formLogin
            .loginPage("/login")
            .loginProcessingUrl("/authentication")
            .failureForwardUrl("/login?failure")
            .successForwardUrl("/home")
            .permitAll())
        .logout(logout -> logout
            .permitAll())
        .sessionManagement(sessionManagement -> sessionManagement
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

    return httpSecurity.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JdbcDaoImpl userDetailsService(JdbcTemplate jdbcTemplate) {
    JdbcDaoImpl jdbcDao = new JdbcDaoImpl();

    jdbcDao.setDataSource(dataSource);

    String insertUserQuery = "INSERT INTO users (username, password, enabled, roles) VALUES (?, ?, true, ?) "
        + "ON CONFLICT (username) DO NOTHING";

    jdbcTemplate.update(insertUserQuery, userUsername, passwordEncoder().encode(userPassword), "USER");
    jdbcTemplate.update(insertUserQuery, adminUsername, passwordEncoder().encode(adminPassword), "ADMIN");

    return jdbcDao;
  }
}
