import Flow from 'Frontend/generated/flow/Flow';
import fileRoutes from 'Frontend/generated/file-routes';
import Login from "Frontend/views/login";
import {RouterConfigurationBuilder} from "@vaadin/hilla-file-router/runtime.js";

export const { routes, router} = new RouterConfigurationBuilder()
    .withFileRoutes(fileRoutes)
    .withReactRoutes(
        { path: '/login', element: <Login />, handle: { title: 'Login' } }
    )
    .withFallback(Flow)
    .protect('/login')
    .build();
