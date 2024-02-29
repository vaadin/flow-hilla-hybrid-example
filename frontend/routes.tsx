import { createBrowserRouter, RouteObject } from 'react-router-dom';
import { serverSideRoutes } from "Frontend/generated/flow/Flow";
import AboutView from "Frontend/views/about/AboutView";
import MainLayout from "Frontend/views/MainLayout";
import HillaView from "Frontend/views/hilla/HillaView";

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


export default createBrowserRouter([...routes], {basename: new URL(document.baseURI).pathname });
