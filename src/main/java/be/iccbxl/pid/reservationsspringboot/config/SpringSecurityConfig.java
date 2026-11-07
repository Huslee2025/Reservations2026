package be.iccbxl.pid.reservationsspringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import be.iccbxl.pid.reservationsspringboot.repository.UserRepository;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {

    return new BCryptPasswordEncoder(BCryptVersion.$2Y, 12);

  }

  @Bean
  public AuthenticationManager authenticationManager(
      final AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain configure(final HttpSecurity http, UserRepository userRepository) throws Exception {

    return http.cors(Customizer.withDefaults())
        .csrf(Customizer.withDefaults())
        .authorizeHttpRequests(auth -> { auth 
          //Pages accessibles à tous
          .requestMatchers("/").permitAll()
          .requestMatchers("/login","/login**","/css/**","/js/**","/forgot-password","/reset-password","/reset-success","/register").permitAll()

          //Création d'un artiste : ADMIN uniquement
          .requestMatchers("/artists/create").hasRole("ADMIN")

          //Consultation des artistes : publique
          .requestMatchers(HttpMethod.GET,"/artists","/artists/{id}").permitAll()

          //Toutes les autres opérations sur les artistes : ADMIN
          .requestMatchers("/artists","/artists/**").hasRole("ADMIN")

          //Accès réservé aux utilisateurs selon leur rôle
          .requestMatchers("/admin").hasRole("ADMIN")
          .requestMatchers("/user").hasRole("MEMBER")

          //Toutes les autres routes nécessitent une connexion
          .anyRequest().authenticated();
        })
        .formLogin(form -> form
            .loginPage("/login")
            .usernameParameter("login")
            .failureUrl("/login?loginError=true")
            .permitAll())
        .logout(logout -> logout
            .logoutSuccessUrl("/login?logoutSuccess=true")
            .deleteCookies("JSESSIONID")
            .permitAll())
        .exceptionHandling(exception -> exception
            .authenticationEntryPoint(
                new LoginUrlAuthenticationEntryPoint("/login?loginRequired=true")))
      .addFilterAfter(
         new DeletedAccountFilter(userRepository),
          AnonymousAuthenticationFilter.class)
        .build();
  }

}
