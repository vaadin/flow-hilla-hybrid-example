import { AppLayout } from '@vaadin/react-components/AppLayout.js';
import { Avatar } from '@vaadin/react-components/Avatar.js';
import { Button } from '@vaadin/react-components/Button.js';
import { DrawerToggle } from '@vaadin/react-components/DrawerToggle.js';
import Placeholder from 'Frontend/components/placeholder/Placeholder.js';
import { useRouteMetadata } from 'Frontend/util/routing.js';
import {Suspense, useEffect} from 'react';
import { NavLink, Outlet } from 'react-router-dom';
import {useAuth} from "../auth";

const navLinkClasses = ({ isActive }: any) => {
    return `block rounded-m p-s ${isActive ? 'bg-primary-10 text-primary' : 'text-body'}`;
};

export default function MainLayout() {
    const currentTitle = useRouteMetadata()?.title ?? 'Hybrid Example With Stateful Auth';

    useEffect(() => {document.title = currentTitle;}, [currentTitle]);

    const { state, logout } = useAuth();

    // @ts-ignore
    const userName : string = state.user?.name;

    return (
        <AppLayout primarySection="drawer">
            <div slot="drawer" className="flex flex-col justify-between h-full p-m">
                <header className="flex flex-col gap-m">
                    <h1 className="text-l m-0">Hybrid Example With Stateful Auth</h1>
                    <nav>
                        <NavLink className={navLinkClasses} to="/">
                            Hilla Public
                        </NavLink>
                        {state.user ? (
                            <NavLink className={navLinkClasses} to="/about">
                                Hilla Authenticated
                            </NavLink>
                        ) : null}
                        {state.user ? (
                            <NavLink className={navLinkClasses} to="/hilla">
                                Hilla User
                            </NavLink>
                        ) : null}
                        {state.user ? (
                            <NavLink to={'/flow'} className={navLinkClasses}>
                                Flow Admin
                            </NavLink>
                        ) : null}
                    </nav>
                </header>
                <footer className="flex flex-col gap-s">
                    {state.user ? (
                        <>
                            <div className="flex items-center gap-s">
                                <Avatar theme="xsmall" name={userName} />
                                {userName}
                            </div>
                            <Button onClick={async () => logout()}>Sign out</Button>
                        </>
                    ) : (
                        <a href="/login">Sign in</a>
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
