import { RouteObject } from 'react-router-dom';
import { buildRoute as b, createRouter } from "Frontend/Flow";
import Layout from "Frontend/views/$layout";
import Hilla from "Frontend/views/hilla";
import About from "Frontend/views/about";
import Login from "Frontend/views/login";
import {protectRoutes} from "@vaadin/hilla-react-auth";
import Public from "Frontend/views/$index";

let routing = protectRoutes([
    {
        element: <Layout />,
        handle: { title: 'Main' },
        children: [
            { path: '/', element: <Public />, handle: { title: 'Public', requiresLogin: false } },
            { path: '/about', element: <About />, handle: { title: 'Welcome', requiresLogin: true } },
            { path: '/hilla', element: <Hilla />, handle: { title: 'Hilla', rolesAllowed: ['ROLE_USER'] } }
        ],
    },
    { path: '/login', element: <Login />, handle: { title: 'Login' } }
]) as RouteObject[];
export const routes = b(routing, routing[0].children);

export default createRouter(routes);