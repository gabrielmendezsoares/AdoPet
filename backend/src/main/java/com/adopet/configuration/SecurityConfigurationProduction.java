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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

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
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:4173"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration("/**", configuration);

    return source;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
            .requestMatchers("/animals/**").authenticated()
            .anyRequest().permitAll())
        .httpBasic(Customizer.withDefaults())
        .formLogin(formLogin -> formLogin
            .defaultSuccessUrl("/animals")
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
    jdbcDao.setUsersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?");
    jdbcDao.setAuthoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username = ?");

    String insertUserQuery = "INSERT INTO users (username, password, enabled) VALUES (?, ?, true) "
        + "ON CONFLICT (username) DO NOTHING";

    String insertAuthorityQuery = "INSERT INTO authorities (username, authority) VALUES (?, ?) "
        + "ON CONFLICT (username, authority) DO NOTHING";

    jdbcTemplate.update(insertUserQuery, userUsername, passwordEncoder().encode(userPassword));
    jdbcTemplate.update(insertUserQuery, adminUsername, passwordEncoder().encode(adminPassword));
    jdbcTemplate.update(insertAuthorityQuery, userUsername, "USER");
    jdbcTemplate.update(insertAuthorityQuery, adminUsername, "ADMIN");

    return jdbcDao;
  }
}
