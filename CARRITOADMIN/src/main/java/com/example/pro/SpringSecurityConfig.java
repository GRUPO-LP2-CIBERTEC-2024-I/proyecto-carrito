package com.example.pro;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

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
import com.google.common.net.HttpHeaders;

import jakarta.servlet.Filter;
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
		.requestMatchers("/", "/login","/Producto/list","/swagger-ui/**","/pago","/Cliente/add","/webhook").permitAll()
		.requestMatchers(HttpMethod.POST, "/pago/crear-preferencia").hasAnyRole("CLIENTE")
		.requestMatchers(HttpMethod.GET, "/Cliente/**").hasAnyRole("CLIENTE")
		.requestMatchers(HttpMethod.POST, "/Cliente/**").hasAnyRole("CLIENTE")
		.requestMatchers(HttpMethod.PUT, "/Cliente/**").hasAnyRole("CLIENTE")
		.requestMatchers(HttpMethod.GET, "/Venta/**").hasAnyRole("CLIENTE")
		.requestMatchers(HttpMethod.POST, "/Venta/**").hasAnyRole("CLIENTE")
		.requestMatchers(HttpMethod.PUT, "/Venta/**").hasAnyRole("CLIENTE")
		.anyRequest().authenticated())
		.cors(cors -> cors.configurationSource(configurationSource()))
    // Cambiamos la política de sesión a IF_REQUIRED para mayor compatibilidad
    .sessionManagement(session -> session
      .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
      // Configurando el cookie de sesión explícitamente
      .sessionFixation().migrateSession()
    )
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
	config.setAllowedOrigins(Arrays.asList("https://proyectocarritoantonitrejo.netlify.app",
						"http://localhost:3001",
						"http://localhost:3000"));
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

  @Bean
  public FilterRegistrationBean<Filter> sameSiteCookieFilter() {
    FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();

    registrationBean.setFilter((request, response, chain) -> {
      jakarta.servlet.http.HttpServletResponse httpResponse = (jakarta.servlet.http.HttpServletResponse) response;

      chain.doFilter(request, httpResponse);

      // Capturar y modificar todas las cookies después del filtrado
      Collection<String> headers = httpResponse.getHeaders("Set-Cookie");
      if (!headers.isEmpty()) {
        // fundamental limpiar las cabeceras de los set-cookie existentes
        httpResponse.setHeader("Set-Cookie", null);

        // Agregar las cabeceras modificadas
        for (String header : headers) {
          // Si la cookie ya contiene SameSite=None, no la modificamos amikos
          if (!header.contains("SameSite=None")) {
            if (header.contains("JSESSIONID")) {
              String newHeader = header;
              if (!newHeader.contains("Secure")) {
                newHeader += "; Secure";
              }
              newHeader += "; SameSite=None";
              httpResponse.addHeader("Set-Cookie", newHeader);
            } else {
              // Para otras cookies, mantenemos el encabezado original
              httpResponse.addHeader("Set-Cookie", header);
            }
          } else {
            // mantenemos la cookie que ya tiene SameSite=None
            httpResponse.addHeader("Set-Cookie", header);
          }
        }
      }
    });

    registrationBean.addUrlPatterns("/*");
    registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1); // Justo después del CORS
    return registrationBean;
  }

}
