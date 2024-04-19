import { AppLayout, Avatar, Button, DrawerToggle, SideNav, SideNavItem } from "@vaadin/react-components";
import Placeholder from 'Frontend/components/placeholder/Placeholder.js';
import { useRouteMetadata } from 'Frontend/util/routing.js';
import { Suspense, useEffect } from 'react';
import { Outlet, useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from "../auth";
import { createMenuItems } from '@vaadin/hilla-file-router/runtime.js';

export default function Layout() {
    const navigate = useNavigate();
    const location = useLocation();
    const currentTitle = useRouteMetadata()?.title ?? 'Hybrid Example With Stateful Auth';

    useEffect(() => {document.title = currentTitle;}, [currentTitle]);

    const { state, logout } = useAuth();

    // @ts-ignore
    const userName : string = state.user?.name;

    async function doLogout() {
        const isServerSideRoute = window.location.pathname === '/flow';
        await logout();
        if (isServerSideRoute) {
            // Workaround for https://github.com/vaadin/hilla/issues/2235
            window.location.reload();
        }
    }

    const flowIsInRole = state.user?.authorities?.includes(
        'ROLE_ADMIN');

    return (
        <AppLayout primarySection="drawer">
            <div slot="drawer" className="flex flex-col justify-between h-full p-m">
                <header className="flex flex-col gap-m">
                    <h1 className="text-l m-0">Hybrid Example With Stateful Auth</h1>
                    <SideNav
                        onNavigate={({ path }) => navigate(path!)}
                        location={location}>
                        {
                            createMenuItems().map(({ to, title }) => (
                                <SideNavItem path={to} key={to}>
                                    {title}
                                </SideNavItem>
                            ))
                        }
                        { flowIsInRole ? (
                            <SideNavItem path={'/flow'}>
                                Flow Admin
                            </SideNavItem>
                        ) : null}
                    </SideNav>
                </header>
                <footer className="flex flex-col gap-s">
                    {state.user ? (
                        <>
                            <div className="flex items-center gap-s">
                                <Avatar theme="xsmall" name={userName} />
                                {userName}
                            </div>
                            <Button onClick={async () => doLogout()}>Sign out</Button>
                        </>
                    ) : (
                        <a href="/login">
                          <Button className="w-full">Sign in</Button>
                        </a>
                    )}
                </footer>
            </div>

            <DrawerToggle slot="navbar" aria-label="Menu toggle"></DrawerToggle>

            <Suspense fallback={<Placeholder />}>
                <Outlet />
            </Suspense>
        </AppLayout>
    );
}
