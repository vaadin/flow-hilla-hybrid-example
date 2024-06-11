import HillaView from "Frontend/views/hilla/HillaView.js";
import { serverSideRoutes } from "Frontend/generated/flow/Flow";
import {createBrowserRouter,RouteObject} from "react-router-dom";
import MainLayout from "Frontend/views/MainLayout.js";
import AboutView from "Frontend/views/about/AboutView.js";

export const routes = [
    {
        element: <MainLayout />,
        handle: { title: 'Main' },
        children: [
            { path: '/', element: <AboutView />, handle: { title: 'Welcome' } },
            { path: '/hilla', element: <HillaView />, handle: { title: 'Hilla' } },
            ...serverSideRoutes
        ],
    },
] as RouteObject[];


export const router = createBrowserRouter([...routes], {basename: new URL(document.baseURI).pathname });

