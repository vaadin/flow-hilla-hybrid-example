import { LoginOverlay } from '@vaadin/react-components/LoginOverlay.js';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from 'Frontend/auth';
import { ViewConfig } from "@vaadin/hilla-file-router/types.js";

export const config: ViewConfig = {
    menu: { exclude: true}
}

const NavigateAndReload = ({ to }) => {
    const navigate = useNavigate();

    useEffect(() => {
        navigate(to, { replace: true });
        // reload a page on log in to update the menu items
        window.location.reload();
    }, [navigate, to]);

    return null;
};

/**
 * Login views in Hilla
 */
export default function Login() {
    const { state, login } = useAuth();
    const [hasError, setError] = useState<boolean>();
    const [url, setUrl] = useState<string>();

    if (state.user && url) {
        const path = new URL(url, document.baseURI).pathname;
        return <NavigateAndReload to={path} />;
    }

    return (
        <LoginOverlay
            opened
            error={hasError}
            noForgotPassword
            title={'Hybrid App with Stateful Auth'}
            description={'User: user/user, Admin: admin/admin'}
            onLogin={async ({ detail: { username, password } }) => {
                const { defaultUrl, error, redirectUrl } = await login(username, password);

                if (error) {
                    setError(true);
                } else {
                    setUrl(redirectUrl ?? defaultUrl ?? '/');
                }
            }}
        />
    );
}
