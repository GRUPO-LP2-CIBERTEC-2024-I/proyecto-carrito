package com.example.pro;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
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

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;


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
      config.setAllowedOrigins(Arrays.asList(
        "https://proyectocarritoantonitrejo.netlify.app",
        "http://localhost:3001",
        "http://localhost:3000",
        // prueba con dominios de mercado pago aiudaaaaa
        "https://www.mercadopago.com",
        "https://www.mercadopago.com.pe",
        "https://api.mercadopago.com",
        "https://api.mercadolibre.com"
      ));
      config.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "OPTIONS"));
      config.setAllowedHeaders(Arrays.asList(
        "Authorization",
        "Content-Type",
        "X-Requested-With",
        "Accept",
        "Origin",
        "Access-Control-Request-Method",
        "Access-Control-Request-Headers"
      ));
      config.setExposedHeaders(Arrays.asList(
        "Access-Control-Allow-Origin",
        "Access-Control-Allow-Credentials",
        "Set-Cookie"
      ));
      config.setAllowCredentials(true);
      config.setMaxAge(3600L); // Cache preflight por 1 hora

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

  /*@Bean
  public FilterRegistrationBean<Filter> sameSiteCookieFilter() {
    FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();

    registrationBean.setFilter((request, response, chain) -> {
      jakarta.servlet.http.HttpServletResponse httpResponse = (jakarta.servlet.http.HttpServletResponse) response;
      jakarta.servlet.http.HttpServletRequest httpRequest = (jakarta.servlet.http.HttpServletRequest) request;

      // Completamos la cadena de filtros primero
      chain.doFilter(request, httpResponse);

      // Registramos información para diagnóstico
      System.out.println("URL solicitada: " + httpRequest.getRequestURL());

      // Capturar y modificar todas las cookies después del filtrado
      Collection<String> headers = httpResponse.getHeaders("Set-Cookie");
      if (!headers.isEmpty()) {
        System.out.println("Cookies antes de modificar: " + headers);

        // Fundamental limpiar las cabeceras de los set-cookie existentes
        httpResponse.setHeader("Set-Cookie", null);

        // Agregar las cabeceras modificadas
        for (String header : headers) {
          // Modificamos todas las cookies JSESSIONID
          if (header.contains("JSESSIONID")) {
            String newHeader = header;
            // Aseguramos que tenga Secure
            if (!newHeader.contains("Secure")) {
              newHeader += "; Secure";
            }
            // Aseguramos que tenga SameSite=None
            if (!newHeader.contains("SameSite=None")) {
              newHeader += "; SameSite=None";
            }
            httpResponse.addHeader("Set-Cookie", newHeader);
            System.out.println("Cookie JSESSIONID modificada: " + newHeader);
          } else {
            // Para otras cookies, mantenemos el encabezado original
            httpResponse.addHeader("Set-Cookie", header);
          }
        }
        System.out.println("Cookies después de modificar: " + httpResponse.getHeaders("Set-Cookie"));
      }
    });

    registrationBean.addUrlPatterns("/*");
    // Mayor prioridad para asegurar que se ejecute antes que otros filtros
    registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return registrationBean;
  }
  */

  /*@Bean
  public FilterRegistrationBean<Filter> sameSiteCookieFilter() {
    FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();

    registrationBean.setFilter((request, response, chain) -> {
      jakarta.servlet.http.HttpServletResponse httpResponse = (jakarta.servlet.http.HttpServletResponse) response;
      jakarta.servlet.http.HttpServletRequest httpRequest = (jakarta.servlet.http.HttpServletRequest) request;

      // Crear una respuesta personalizada para capturar y modificar las cookies
      jakarta.servlet.http.HttpServletResponseWrapper wrappedResponse =
        new jakarta.servlet.http.HttpServletResponseWrapper(httpResponse) {
          @Override
          public void addHeader(String name, String value) {
            // Solo interceptamos los headers Set-Cookie
            if (name.equalsIgnoreCase("Set-Cookie")) {
              // Solo modificamos las cookies de sesión
              if (value.contains("JSESSIONID")) {
                // Aseguramos que tenga SameSite=None y Secure
                if (!value.contains("SameSite=None")) {
                  StringBuilder modifiedCookie = new StringBuilder(value);
                  if (!value.contains("Secure")) {
                    modifiedCookie.append("; Secure");
                  }
                  modifiedCookie.append("; SameSite=None");
                  value = modifiedCookie.toString();
                  System.out.println("[FIXED] Cookie modificada: " + value);
                }
              }
            }
            // Añadimos el header (original o modificado)
            super.addHeader(name, value);
          }

          @Override
          public void setHeader(String name, String value) {
            // Similar al método addHeader
            if (name.equalsIgnoreCase("Set-Cookie") && value != null && value.contains("JSESSIONID")) {
              if (!value.contains("SameSite=None")) {
                StringBuilder modifiedCookie = new StringBuilder(value);
                if (!value.contains("Secure")) {
                  modifiedCookie.append("; Secure");
                }
                modifiedCookie.append("; SameSite=None");
                value = modifiedCookie.toString();
                System.out.println("[FIXED] Cookie modificada via setHeader: " + value);
              }
            }
            super.setHeader(name, value);
          }
        };

      // Log de diagnóstico
      System.out.println("Procesando URL: " + httpRequest.getRequestURL());

      // Procesar la solicitud con nuestra respuesta wrapper
      chain.doFilter(request, wrappedResponse);

      // Logs después del procesamiento
      System.out.println("Respuesta procesada para: " + httpRequest.getRequestURL());
    });

    registrationBean.addUrlPatterns("/*");
    registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return registrationBean;
  }*/

  @Bean
  public FilterRegistrationBean<Filter> sameSiteCookieFilter() {
    FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();

    registrationBean.setFilter(new Filter() {
      @Override
      public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Detectar si es una solicitud relacionada con el pago
        boolean isPaymentEndpoint = httpRequest.getRequestURI().contains("/pago") ||
          httpRequest.getRequestURI().contains("/crear-preferencia");

        if (isPaymentEndpoint) {
          System.out.println("⚠️ Procesando endpoint de pago: " + httpRequest.getRequestURI());
        }

        // Envolvemos la respuesta para interceptar cookies salientes
        SameSiteCookieHttpServletResponseWrapper wrappedResponse =
          new SameSiteCookieHttpServletResponseWrapper((HttpServletResponse) response, isPaymentEndpoint);

        // Continúa con la cadena de filtros
        chain.doFilter(request, wrappedResponse);

        // Si es endpoint de pago, también fuerza una regeneración de la cookie de sesión
        if (isPaymentEndpoint) {
          // Forzar que la sesión existente tenga SameSite=None
          if (httpRequest.getSession(false) != null) {
            // Crear una nueva cookie de sesión con SameSite=None
            String sessionId = httpRequest.getSession().getId();
            wrappedResponse.addHeader("Set-Cookie",
              "JSESSIONID=" + sessionId +
                "; Path=/; Secure; HttpOnly; SameSite=None");

            System.out.println("✅ Forzando cookie JSESSIONID con SameSite=None para pago");
          }
        }
      }
    });

    // Aplicar a todas las solicitudes, pero con alta prioridad
    registrationBean.setUrlPatterns(Collections.singletonList("/*"));
    registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    registrationBean.setName("sameSiteCookieFilter");

    return registrationBean;
  }

  // Clase interna para envolver la respuesta HTTP y modificar cookies
  private static class SameSiteCookieHttpServletResponseWrapper extends HttpServletResponseWrapper {
    private final boolean forceSecureSameSiteNone;

    public SameSiteCookieHttpServletResponseWrapper(HttpServletResponse response, boolean forceSecureSameSiteNone) {
      super(response);
      this.forceSecureSameSiteNone = forceSecureSameSiteNone;
    }

    @Override
    public void addHeader(String name, String value) {
      // Solo interceptar Set-Cookie
      if (name.equalsIgnoreCase("Set-Cookie")) {
        if (value.contains("JSESSIONID")) {
          // Asegurar que tenga SameSite=None
          StringBuilder modifiedCookie = new StringBuilder(value);

          // Asegurar que tenga Secure
          if (!value.contains("Secure")) {
            modifiedCookie.append("; Secure");
          }

          // Asegurar que tenga SameSite=None
          if (!value.contains("SameSite=None")) {
            modifiedCookie.append("; SameSite=None");
          }

          // Reemplazar el valor original con el modificado
          value = modifiedCookie.toString();
          System.out.println("🍪 Cookie JSESSIONID modificada: " + value);
        }
      }

      // Añadir la cabecera (original o modificada)
      super.addHeader(name, value);
    }

    @Override
    public void setHeader(String name, String value) {
      // Hacer lo mismo que en addHeader
      if (name.equalsIgnoreCase("Set-Cookie") && value != null && value.contains("JSESSIONID")) {
        if (!value.contains("SameSite=None")) {
          StringBuilder modifiedCookie = new StringBuilder(value);
          if (!value.contains("Secure")) {
            modifiedCookie.append("; Secure");
          }
          modifiedCookie.append("; SameSite=None");
          value = modifiedCookie.toString();
          System.out.println("🍪 Cookie JSESSIONID modificada en setHeader: " + value);
        }
      }
      super.setHeader(name, value);
    }
  }

  // Opcional: añadir un servlet específico para rutas de pago que asegure SameSite=None
  @Bean
  public FilterRegistrationBean<Filter> paymentRoutesFilter() {
    FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();

    registrationBean.setFilter(new Filter() {
      @Override
      public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Antes de procesar la solicitud
        if (httpRequest.getSession(false) != null) {
          // Forzar la creación de una cookie de sesión con SameSite=None
          String sessionId = httpRequest.getSession().getId();
          httpResponse.addHeader("Set-Cookie",
            "JSESSIONID=" + sessionId +
              "; Path=/; Secure; HttpOnly; SameSite=None");
        }

        // Continuar con la cadena de filtros
        chain.doFilter(request, httpResponse);
      }
    });

    // Aplicar solo a rutas de pago
    registrationBean.addUrlPatterns("/pago/*", "/pago", "/*/crear-preferencia");
    registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
    registrationBean.setName("paymentRoutesFilter");

    return registrationBean;
  }
}
