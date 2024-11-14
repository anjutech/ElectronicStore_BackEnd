package com.ElectronicStore.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.ElectronicStore.Security.JwtAuthenticationEntryPoint;
import com.ElectronicStore.Security.JwtAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import com.ElectronicStore.service.impl.CustomUserDetailService;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    
    private final String[] PUBLIC_URLS= {
    		"/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/v2/api-docs/**",
            "/api/public/**",
            "/api/public/authenticate",
            "/actuator/*",
            "/swagger-ui/**",
            "/api-docs/**",
           "/swagger-ui/index.html",
    };
    
    
//    ----------Inmemory--Autehntication-----------------------------------------------------------------------
    @Bean
    public UserDetailsService userDetailsService() {
//
//        UserDetails normal = User.builder()
//                .username("anju")
//                .password(passwordEncoder().encode("anju"))
//                .roles("NORMAL")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("Durgesh")
//                .password(passwordEncoder().encode("durgesh"))
//                .roles("ADMIN")
//                .build();
//////    users create
//////    InMemoryUserDetailsManager- is implementation class of UserDetailService
//        return new InMemoryUserDetailsManager(normal, admin);
//    	---------------------------------------------------------------------------------------------
//              
    	return new CustomUserDetailService();   
    	
    	
//    	----------------------------------------------------------------------------------
//
//		UserDetails user = User.builder().username("anju").password(passwordEncoder().encode("anju")).roles("NORMAL")
//				.build();
//
//		return new InMemoryUserDetailsManager(user);

}

// we do 3 thing to make user login from direct database
//    first extend userdetails in user table
//    second make a CustomUserDetailService
//    third make a daoauthenticator
   
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//  filterchain -----------Form based authetication----------------------------------------------------------------------
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//
//        httpSecurity
//                .authorizeHttpRequests(auth-> auth
//                .anyRequest()
//                .authenticated())
//                .formLogin(login -> login
//                .loginPage("login.html")
//                .loginProcessingUrl("/dashboard")
//                .failureUrl("error"))
//                .logout(logout -> logout
//                        .logoutUrl("/logout"));
//    	
//    	return httpSecurity.build();
//    }
//
//
    
//    -----------------------Basic Authentication------------------------------------------
//    @Bean
//        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//			
//        	httpSecurity.csrf(csrf->csrf.disable())
//        	        .cors(cors->cors.disable())
//        	        .authorizeHttpRequests(auth->auth
//        			.anyRequest()
//        			.authenticated())
//        	        .httpBasic();
//        	
//        	return httpSecurity.build();
//    	
//    
//        }
//  -------------------------- JWt Authetication step -------------------------------------------
//    @Bean
// public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//			
//    	
//    	
//        	httpSecurity.csrf(csrf->csrf.disable())
//        	        .cors(cors->cors.disable())
//        	        .authorizeHttpRequests(auth->auth
//        	        .requestMatchers("/auth/login")
//                    .permitAll()
//        	        .requestMatchers(HttpMethod.POST,"/users")
//        	        .permitAll()
//        	        .requestMatchers(HttpMethod.DELETE,"/users/**").hasRole("ADMIN")
//        	        .requestMatchers(PUBLIC_URLS)
//        	        .permitAll()
//        	        .requestMatchers(HttpMethod.GET)
//        	        .permitAll()
//        			.anyRequest()
//        			.authenticated())
//        	        .exceptionHandling(ex->ex
//        	        .authenticationEntryPoint(jwtAuthenticationEntryPoint))
//        	        .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        	
//        	httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//        	
//        	return httpSecurity.build();
//    	
//    
//        }
//    ---------------------jwt Authetication with Cors Configuration---------------------------
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        
        httpSecurity
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
                config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type","*"));
                config.setAllowCredentials(true);
                return config;
            }))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers("/users/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/users")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/carts/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/categories/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/orders/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                .requestMatchers(PUBLIC_URLS).permitAll()
                .requestMatchers(HttpMethod.GET).permitAll()
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(jwtAuthenticationEntryPoint))
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return httpSecurity.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    	return authenticationConfiguration.getAuthenticationManager();
    }
 // CORS Configuration


    
}

//-------------------------------------------------------------------------------------------------------------------------------------------



    




