package com.example.pro;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.example.pro.services.IUsuarioServices;
import com.example.pro.services.Impl.UsuarioServices;

import jakarta.servlet.http.HttpServletResponse;



@Configuration
public class SpringSecurityConfig {

//    @Value("${ORIGIN_ANGULAR}")
//    private String origenAngular;

    @Bean
    PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    }
    
    @Autowired
    private UsuarioServices usuarioServices;


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder
            .userDetailsService(usuarioServices)
            .passwordEncoder(passwordEncoder());
        return authBuilder.build();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	System.err.println(passwordEncoder().encode("admin"));
	return http.authorizeHttpRequests((authz) -> authz
		// TODOS LOS USUARIOS
		.requestMatchers("/", "/login","/Producto/list","/swagger-ui/**").permitAll()
		.requestMatchers(HttpMethod.POST, "/Producto/**").hasAnyRole("CLIENTE")
		.requestMatchers(HttpMethod.GET, "/Cliente/**").hasAnyRole("CLIENTE")		
		.requestMatchers(HttpMethod.POST, "/Cliente/**").hasAnyRole("CLIENTE")		
		.requestMatchers(HttpMethod.PUT, "/Cliente/**").hasAnyRole("CLIENTE")		
		.anyRequest().authenticated())
		.cors(cors -> cors.configurationSource(configurationSource()))	
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
		.csrf(csrf -> csrf.disable())
		.logout(logout -> logout
			    .logoutUrl("/logout")
			    .invalidateHttpSession(true)
			    .deleteCookies("JSESSIONID")
			    .logoutSuccessHandler((request, response, authentication) -> {
			        response.setStatus(HttpServletResponse.SC_OK);
			        response.getWriter().write("Sesión cerrada con éxito");
			    })
			)
		.build();
    }

    @Bean
    CorsConfigurationSource configurationSource() {
	CorsConfiguration config = new CorsConfiguration();
	config.setAllowedOriginPatterns(Arrays.asList("*"));
	config.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
	config.setAllowedHeaders(
		Arrays.asList("Authorization", "Content-Type"));
	config.setAllowCredentials(true);

	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	source.registerCorsConfiguration("/**", config);
	return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsfilter() {
	FilterRegistrationBean<CorsFilter> corsbean = new FilterRegistrationBean<>(
		new CorsFilter(this.configurationSource()));
	corsbean.setOrder(Ordered.HIGHEST_PRECEDENCE);
	return corsbean;
    }
}
