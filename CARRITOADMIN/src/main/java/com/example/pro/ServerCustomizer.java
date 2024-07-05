package com.example.pro;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

@Component
public class ServerCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        // Configuración para permitir HTTP (sin SSL)
        factory.setContextPath(""); // Configurar el contexto raíz
        factory.addConnectorCustomizers(connector -> {
            connector.setScheme("http");
            connector.setSecure(false);
            connector.setRedirectPort(8081); // Puerto HTTPS (si es diferente de HTTP)
        });
    }
}

