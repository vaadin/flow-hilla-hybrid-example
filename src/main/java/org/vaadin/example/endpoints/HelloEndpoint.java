package org.vaadin.example.endpoints;

import jakarta.annotation.security.PermitAll;
import org.vaadin.example.GreetService;

import com.vaadin.hilla.Endpoint;
import com.vaadin.hilla.Nonnull;

@Endpoint
@PermitAll
public class HelloEndpoint {

    GreetService service = new GreetService();

    @Nonnull
    public String sayHello(@Nonnull String name) {
        return service.greet(name);
    }
}
