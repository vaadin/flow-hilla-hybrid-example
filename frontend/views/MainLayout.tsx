import { AppLayout } from '@vaadin/react-components/AppLayout.js';
import { DrawerToggle } from '@vaadin/react-components/DrawerToggle.js';
import Placeholder from 'Frontend/components/placeholder/Placeholder.js';
import { useRouteMetadata } from 'Frontend/util/routing.js';
import { Suspense, useEffect } from 'react';
import { NavLink, Outlet } from 'react-router-dom';

const navLinkClasses = ({ isActive }: any) => {
    return `block rounded-m p-s ${isActive ? 'bg-primary-10 text-primary' : 'text-body'}`;
};

export default function MainLayout() {
    const currentTitle = useRouteMetadata()?.title ?? 'Hybrid example';

    useEffect(() => {document.title = currentTitle;}, [currentTitle]);

  return (

    <AppLayout primarySection="drawer">
      <div slot="drawer" className="flex flex-col justify-between h-full p-m">
        <header className="flex flex-col gap-m">
          <h1 className="text-l m-0">Hybrid example</h1>
          <nav>
            <NavLink className={navLinkClasses} to="/">About</NavLink>
            <NavLink className={navLinkClasses} to="/hilla">Hilla</NavLink>
            <NavLink to={'/flow'} className={navLinkClasses}>Flow</NavLink>
          </nav>
        </header>
      </div>

      <DrawerToggle slot="navbar" aria-label="Menu toggle"></DrawerToggle>

      <Suspense fallback={<Placeholder />}>
        <Outlet />
      </Suspense>
    </AppLayout>

  );
}
