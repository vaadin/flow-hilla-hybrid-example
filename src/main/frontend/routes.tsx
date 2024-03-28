import { createBrowserRouter, RouteObject } from 'react-router-dom';
import { buildRoute } from "Frontend/generated/flow/Flow";
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
            { path: '/', element: <Public />, handle: { title: 'Public' } },
            { path: '/about', element: <About />, handle: { title: 'Welcome' } },
            { path: '/hilla', element: <Hilla />, handle: { title: 'Hilla' } }
        ],
    },
    { path: '/login', element: <Login />, handle: { title: 'Login' } }
]) as RouteObject[];
export const routes = buildRoute(routing, routing[0].children);

export default createBrowserRouter(routes);