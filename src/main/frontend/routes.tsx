import { createBrowserRouter, RouteObject } from 'react-router-dom';
import { buildRoute } from "Frontend/generated/flow/Flow";
import MainLayout from "Frontend/views/MainLayout";
import HillaView from "Frontend/views/HillaView";
import AboutView from "Frontend/views/AboutView";
import LoginView from "Frontend/views/LoginView";
import {protectRoutes} from "@vaadin/hilla-react-auth";
import PublicView from "Frontend/views/PublicView";

let routing = protectRoutes([
    {
        element: <MainLayout />,
        handle: { title: 'Main' },
        children: [
            { path: '/', element: <PublicView />, handle: { title: 'Public', requiresLogin: false } },
            { path: '/about', element: <AboutView />, handle: { title: 'Welcome', requiresLogin: true } },
            { path: '/hilla', element: <HillaView />, handle: { title: 'Hilla', rolesAllowed: ['ROLE_USER'] } }
        ],
    },
    { path: '/login', element: <LoginView />, handle: { title: 'Login' } }
]) as RouteObject[];
export const routes = buildRoute(routing, routing[0].children);

export default createBrowserRouter(routes);