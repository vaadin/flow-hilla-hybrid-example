package org.vaadin.example.endpoints;

import org.vaadin.example.GreetService;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import dev.hilla.Nonnull;

@Endpoint
@AnonymousAllowed
public class HelloEndpoint {

    GreetService service = new GreetService();

    @Nonnull
    public String sayHello(@Nonnull String name) {
        return service.greet(name);
    }
}
