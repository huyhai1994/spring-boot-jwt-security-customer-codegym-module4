package com.example.demo.config;

import com.example.demo.config.jwt.CustomAccessDeniedHandler;
import com.example.demo.config.jwt.JwtAuthenticationTokenFilter;
import com.example.demo.config.jwt.RestAuthenticationEntryPoint;
import com.example.demo.config.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*TODO:
*  The @EnableWebSecurity annotation is used in Spring Security
*   to enable the web security configuration. It is a meta-annotation that imports the @Configuration classes required for web security.
    In the provided code snippet, @EnableWebSecurity is used to
    * enable the security configuration for the web application.
    *  The SecurityConfig class is annotated with @EnableWebSecurity,
    * which means that the security configuration defined
    * within this class will be applied to the web application.
    The @EnableWebSecurity annotation is typically used
    * in a configuration class, such as SecurityConfig,
    * to enable web security for the application.
    * It allows you to define authentication, authorization,
    *  and other security-related configurations.
    In the given code, @EnableWebSecurity is used to configure
    *  authentication providers, define security filters, and
    *  customize the security behavior of the web application
    * . The securityFilterChain method is also defined within the
    * SecurityConfig class, which creates and returns a
    *  SecurityFilterChain bean that applies the defined security configurations.
    Overall, the @EnableWebSecurity annotation is essential
    *  for enabling web security in a Spring Boot application.
    * It allows you to define authentication, authorization,
    *  and other security-related configurations.*/

/*TODO: The @EnableMethodSecurity annotation
    is used in Spring Security to enable method-level security.
     It allows you to define security rules for individual
      methods within your application.
      Method-level security allows you to define fine-grained
      access control for specific methods within your application.
       By using @EnableMethodSecurity, you can specify which
       methods should be secured and define the security rules for those methods.
       @EnableMethodSecurity is used to configure authentication providers,
        define security filters, and customize the security behavior
         of the web application. The securityFilterChain method
         is also defined within the SecurityConfig class,
         which creates and returns a SecurityFilterChain
         bean that applies the defined security configurations.
        Overall, the @EnableMethodSecurity annotation is
         essential for enabling method-level security in a
          Spring Boot application. It allows you to define
           security rules for individual methods within your application.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    /*TODO: this is like the contact between
     *       back end and security company*/
    @Autowired
    private UserService userService;

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable).addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class).authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll().requestMatchers("/api/auth/login**").permitAll().requestMatchers("/api/role").permitAll().requestMatchers(HttpMethod.GET, "/api/customers**").authenticated().requestMatchers("/api/customers**").hasAnyAuthority("ROLE_ADMIN").requestMatchers(HttpMethod.PUT, "/api/customers**").hasAnyAuthority("ROLE_ADMIN").requestMatchers(HttpMethod.POST, "/api/customers**").hasAnyAuthority("ROLE_ADMIN").requestMatchers(HttpMethod.DELETE, "/api/customers/**").hasAnyAuthority("ROLE_ADMIN")

        ).exceptionHandling(customizer -> customizer.accessDeniedHandler(customAccessDeniedHandler())).sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).httpBasic(Customizer.withDefaults()).build();
        /*TODO: session STATELESS -> not save state
         *       "each time an user authorized the security
         *       will double check again if the user want to
         *       re-entry*/
    }
}
