import { jsx as _jsx, jsxs as _jsxs } from "react/jsx-runtime";
import { AppLayout } from '@vaadin/react-components/AppLayout.js';
import { DrawerToggle } from '@vaadin/react-components/DrawerToggle.js';
import Placeholder from 'Frontend/components/placeholder/Placeholder.js';
import { useRouteMetadata } from 'Frontend/util/routing.js';
import { Suspense, useEffect } from 'react';
import { NavLink, Outlet } from 'react-router-dom';
const navLinkClasses = ({ isActive }) => {
    return `block rounded-m p-s ${isActive ? 'bg-primary-10 text-primary' : 'text-body'}`;
};
export default function MainLayout() {
    const currentTitle = useRouteMetadata()?.title ?? 'Hybrid example';
    useEffect(() => { document.title = currentTitle; }, [currentTitle]);
    return (_jsxs(AppLayout, { primarySection: "drawer", children: [_jsx("div", { slot: "drawer", className: "flex flex-col justify-between h-full p-m", children: _jsxs("header", { className: "flex flex-col gap-m", children: [_jsx("h1", { className: "text-l m-0", children: "Hybrid example" }), _jsxs("nav", { children: [_jsx(NavLink, { className: navLinkClasses, to: "/", children: "About" }), _jsx(NavLink, { className: navLinkClasses, to: "/hilla", children: "Hilla" }), _jsx(NavLink, { to: '/flow', className: navLinkClasses, children: "Flow" })] })] }) }), _jsx(DrawerToggle, { slot: "navbar", "aria-label": "Menu toggle" }), _jsx(Suspense, { fallback: _jsx(Placeholder, {}), children: _jsx(Outlet, {}) })] }));
}
//# sourceMappingURL=MainLayout.js.map