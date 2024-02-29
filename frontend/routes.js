import { jsx as _jsx } from "react/jsx-runtime";
import { createBrowserRouter } from 'react-router-dom';
import { serverSideRoutes } from "Frontend/generated/flow/Flow";
import AboutView from "Frontend/views/about/AboutView";
import MainLayout from "Frontend/views/MainLayout";
import HillaView from "Frontend/views/hilla/HillaView";
export const routes = [
    {
        element: _jsx(MainLayout, {}),
        handle: { title: 'Main' },
        children: [
            { path: '/', element: _jsx(AboutView, {}), handle: { title: 'Welcome' } },
            { path: '/hilla', element: _jsx(HillaView, {}), handle: { title: 'Hilla' } },
            ...serverSideRoutes
        ],
    },
];
export default createBrowserRouter([...routes], { basename: new URL(document.baseURI).pathname });
//# sourceMappingURL=routes.js.map